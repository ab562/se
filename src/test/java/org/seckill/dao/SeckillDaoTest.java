package org.seckill.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	@Resource
	private SeckillDao seckillDao;
	@Test
	public void testReduceNumber() {
		int se = seckillDao.reduceNumber(1L, new Date());
		System.out.println(se);
	}

	@Test
	public void testQueryById() {
		Seckill s = seckillDao.queryById(1);
		System.out.println(s);
	}

	@Test
	public void testQueryAll() {
		List<Seckill> ss = seckillDao.queryAll(0, 100);
		System.out.println(ss);
	}

}
