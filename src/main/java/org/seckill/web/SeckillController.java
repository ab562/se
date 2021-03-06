package org.seckill.web;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/seckill")
public class SeckillController {
	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	@Resource
	private SeckillService seckillService;
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model){
	List<Seckill> list = seckillService.getSeckillList();
		model.addAttribute("list", list);
		return "list";
	}
	@RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
	public String detail(@PathVariable("seckillId")Long seckillId,Model model){
		if(seckillId==null){
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if(seckill==null){
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
		
	}
	@RequestMapping(value="/{seckillId}/exposer" ,method=RequestMethod.POST
			,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public SeckillResult<Exposer> exporter(@PathVariable("seckillId")Long seckillId){
		 SeckillResult<Exposer> seckillResult;
		try {
			Exposer exposer =seckillService.exportSeckillUrl(seckillId);
			seckillResult=new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			seckillResult=new SeckillResult<Exposer>(false, e.getMessage());
		}
		return seckillResult;
	}
	@RequestMapping(value="/{seckillId}/{md5}/excution" ,method=RequestMethod.POST
			,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId")Long seckillId
			,@PathVariable("md5")String md5,@CookieValue(value="killPhone",required=false)Long killPhone){
		if(killPhone==null){
			return new SeckillResult<SeckillExecution>(false, "未註冊");
		}
		SeckillResult<SeckillExecution> result;
		try {
			 SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, killPhone, md5);
			 return new  SeckillResult<>(true,execution);
		}  catch (SeckillCloseException e1) {
	        SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
	        return new   SeckillResult<>(false,seckillExecution);
		} catch (RepeatKillException e2) {
			 SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.END);
		        return new   SeckillResult<>(false,seckillExecution);
	     } catch (Exception e) {
	    	 SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
		        return new   SeckillResult<>(false,seckillExecution);
	     }
	}
	 @RequestMapping(value = "/time/now", method = RequestMethod.GET)
	    @ResponseBody
	    public SeckillResult<Long> time() {
	        Date now = new Date();
	        return new SeckillResult(true, now.getTime());
	    }
}
