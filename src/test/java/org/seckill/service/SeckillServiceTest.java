package org.seckill.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
	"classpath:spring/spring-server.xml"})
public class SeckillServiceTest {
	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	@Resource
	private SeckillService seckillService;
	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}",list);
	}
	@Test
	public void executeSeckillProcedureTest(){
		Exposer es = seckillService.exportSeckillUrl(1);
		String md5=null ;
		if(es.isExposed()){
			md5=es.getMd5();
			
			SeckillExecution ex = seckillService.executeSeckillProcedure(1, 13344433434l, md5);
		}
		
	}
	@Test
	public void testGetById() {
		Seckill s = seckillService.getById(1);
		logger.info("seckill={}",s);
	}

	@Test
	public void testSeckillLogic() {
		Exposer exposer = seckillService.exportSeckillUrl(1);
		if(exposer.isExposed()){
			
			logger.info("Exposer={}",exposer);
		}
		long phone=33444;
		String md5="a1057b3f78264014320ce97976ee672f";
		try {
			SeckillExecution s = seckillService.executeSeckill(1, phone, md5);
			logger.info("SeckillExecution={}",s);
		} catch (RepeatKillException e) {
			e.printStackTrace();
		} catch (SeckillCloseException e) {
			e.printStackTrace();
		} 
	}

@Test
public void testName() throws Exception {
	System.out.println(2/3);
}
}
