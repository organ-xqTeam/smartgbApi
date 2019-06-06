package com.smart.socket.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smart.socket.bean.domain.GarbageBinInfo;
import com.smart.socket.service.GarbageBinInfoService;

@RestController
@RequestMapping("/garbagebin")
public class GarbageBinController {
	
	private static Logger log = LoggerFactory.getLogger(GarbageBinController.class);
	
	@Autowired
	private GarbageBinInfoService gbInfoService;
	
	@RequestMapping(value="/monitor",method=RequestMethod.GET)
	public List<GarbageBinInfo> monitor() {
		log.info("GarbageBinController monitor...");
		List<GarbageBinInfo> gbInfoList = gbInfoService.findAllGbInfo();
		return gbInfoList;
	}
	
	@RequestMapping(value="/testAll",method=RequestMethod.GET)
	public int testAll() {
		List<GarbageBinInfo> gbInfoList = new ArrayList<GarbageBinInfo>();
		for(int i=0;i<10;i++){
			GarbageBinInfo gbInfo = new GarbageBinInfo();
			gbInfo.setDtu_id("12345"+i);
			gbInfo.setGarbage_many("8"+i);
			gbInfo.setGarbage_total(200);
			gbInfo.setPosition("成都");
			gbInfo.setLatitude("1236.5697");
			gbInfo.setLongitude("7894.1234");
			gbInfo.setConnect_state("正常");
			gbInfo.setMonitor_state("预警");
			gbInfo.setGps_state("已绑定");
			gbInfo.setCreate_date(new Date());
			gbInfoList.add(gbInfo);
		}
		gbInfoService.batchSaveGbInfo(gbInfoList);
		return gbInfoList.size();
	}
	
	@RequestMapping(value="/testUpdate",method=RequestMethod.GET)
	public int testUpdate() {
		List<GarbageBinInfo> gbInfoList = gbInfoService.findAllGbInfo();
		List<GarbageBinInfo> willUpdate = new ArrayList<GarbageBinInfo>();
		for(int i=0;i<gbInfoList.size();i++){
			GarbageBinInfo updateInfo = new GarbageBinInfo();
			updateInfo.setDtu_id(gbInfoList.get(i).getDtu_id());
			updateInfo.setGarbage_many("88");
			updateInfo.setGps_state("绑定");
			updateInfo.setMonitor_state("不正常");
			updateInfo.setConnect_state("不正常");
			updateInfo.setUpdate_date(new Date());
			willUpdate.add(updateInfo);
		}
		gbInfoService.batchUpdateGbInfo(willUpdate);
		return gbInfoList.size();
	}
}
