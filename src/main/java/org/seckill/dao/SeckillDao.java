package org.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

public interface SeckillDao {
	/**
	 * 减库存
	 * @param seckillId
	 * @param killTime
	 * @return
	 */
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	
	/**
	 * 根据Id查询描述对象
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(long seckillId);
	List<Seckill> queryAll( @Param("offset")int offset,@Param("limit")int limit);

	void killProcedure(Map<String, ?> map);
}
