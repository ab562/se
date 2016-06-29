package org.seckill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.junit.experimental.theories.Theories;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

@Service("seckillService")
public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String slat = "ddfsdf";
	@Resource
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	@Resource
	private RedisDao redisDao;

	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		// 优化点:缓存优化 超时的基础上维护一致性
		// 1.访问redis
		Seckill seckill = redisDao.getSeckill(seckillId);

		if (seckill == null) {
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false, seckillId);
			} else {
				redisDao.putSeckill(seckill);
			}
		}

		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if (nowTime.getTime() > endTime.getTime() || nowTime.getTime() < startTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}

		// 转化特定字符串的过程,不可逆
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);

	}

	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) {
		if (StringUtils.isEmpty(md5) || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException(SeckillStatEnum.DATA_REWRITE.getStateInfo());
		}
		// 执行秒杀逻辑:1.减库存.2.记录购买行为
		Date nowTime = new Date();
		try {

			// 记录购买行为
			int inserCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);

			if (inserCount <= 0) {
				// 重复秒杀
				throw new RepeatKillException(SeckillStatEnum.REPEAT_KILL.getStateInfo());
			} else {
				// 减库存 热点商品竞争
				int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
				if (updateCount <= 0) {
					// rollback
					throw new SeckillCloseException(SeckillStatEnum.END.getStateInfo());
				} else {
					// 秒杀成功 commit
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
				}
			}

		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage());
			// 所有的编译期异常转化为运行期异常,spring的声明式事务做rollback
			throw new SeckillException("seckill inner error: " + e.getMessage());
		}

	}

	private String getMD5(long seckillId) {

		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		logger.info("_________________________________md5: " + md5);
		return md5;
	}

	@Override
	public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {

	if (md5==null||!md5.equals(getMD5(seckillId))) {
		return new SeckillExecution(seckillId, SeckillStatEnum.DATA_REWRITE);
	}
		// 执行秒杀逻辑:1.减库存.2.记录购买行为
		Date nowTime = new Date();
		Map<String, Object> map = new HashMap<>();
		map.put("seckillId", seckillId);
		map.put("phone", userPhone);
		map.put("killTime", nowTime);
		map.put("r_result", null);
		try {
			seckillDao.killProcedure(map);
			Integer result = (Integer) map.get("r_result");
			logger.info("r_result", map.get("r_result"));
			if (result == 1) {
				SuccessKilled sk = successKilledDao.queryByIdWithSeckill(userPhone, seckillId);
				return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, sk);
			} else {
				return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));

			}
		} catch (Exception e) {
			logger.error(e.getMessage());    
			// 所有的编译期异常转化为运行期异常,spring的声明式事务做rollback
			return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
		}
	}

}
