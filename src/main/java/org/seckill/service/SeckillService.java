package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

public interface SeckillService {
	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	/**查询单个秒杀记录
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 输出秒杀接口的地址
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀
	 * @param seckillId
	 * @param userIphone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId , long userPhone,String md5)
	throws SeckillException,RepeatKillException,SeckillCloseException;
	/**
	 * 执行秒杀
	 * @param seckillId
	 * @param userIphone
	 * @param md5
	 */
	SeckillExecution executeSeckillProcedure(long seckillId , long userPhone,String md5)
			throws SeckillException,RepeatKillException,SeckillCloseException;
}
