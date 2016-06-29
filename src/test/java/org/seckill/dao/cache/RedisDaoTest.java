package org.seckill.dao.cache;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
	@Resource
	private RedisDao redisDao;
	@Resource
	private SeckillDao seckillDao;
	@Test
	public void testGetSeckill() {
		Seckill seckill = redisDao.getSeckill(1);
		System.out.println(seckill.getName());
	}

	@Test
	public void testPutSeckill() {
		Seckill seckill =seckillDao.queryById(1);
		String s = redisDao.putSeckill(seckill );
		System.out.println(s);
	}

}
