package org.seckill.web;

import java.util.ArrayList;
import java.util.List;

import org.seckill.entity.Seckill;
import org.seckill.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller    
@RequestMapping("/test")
public class TestController {
	@RequestMapping("/save")
	public String save (User user){
		System.out.println(user);
		
		return null;
		
	}
}
