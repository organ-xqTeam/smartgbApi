package com.smart.socket.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smart.socket.bean.domain.OcrDriver;
import com.smart.socket.service.OcrDriverService;

@RestController
@RequestMapping("/driver")
public class OcrDriverController {
	
	private static Logger log = LoggerFactory.getLogger(OcrDriverController.class);
	
	@Autowired
	private OcrDriverService driverService;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestBody Map<String, Object> loginObj) {
		String name = (String) loginObj.get("name");
		String pwd = (String) loginObj.get("pwd");
		log.info("driver do login by name :" + name);
		OcrDriver driver = driverService.getDriverByLogin(name, pwd);
		if(null != driver){
			if(1 == driverService.saveDriverOnWork(driver)){
				return "success";
			}
		}
		return "fail";
	}
	
	@RequestMapping(value="/logout/{name}",method=RequestMethod.GET)
	public String logout(@PathVariable String name){
		log.info("driver do logout by LoginName :" + name);
		try{
			if(1 == driverService.updateDriverOffWork(name)){
				return "success";
			}
		}catch(Exception e){
			log.error("OcrDriverController logout", e);
		}
		return "fail";
	}
}
