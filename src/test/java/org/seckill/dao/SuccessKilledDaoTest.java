package org.seckill.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	@Resource
	private SuccessKilledDao successKilledDao;
	@Test
	public void testInsertSucessKilled() {
		int i = successKilledDao.insertSuccessKilled(3, 1555703);
		System.out.println(i);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		SuccessKilled su = successKilledDao.queryByIdWithSeckill(1,1555705);
		System.out.println(su);
	}

}
