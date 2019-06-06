package com.smart.socket.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smart.socket.bean.domain.PushMessage;
import com.smart.socket.service.PushMessageService;

@RestController
@RequestMapping("/pushMsg")
public class PushMessageController {
	
	private static Logger log = LoggerFactory.getLogger(PushMessageController.class);
	
	@Autowired
	private PushMessageService pushMessageService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public List<PushMessage> listMsg() {
		log.info("PushMessageController listMsg...");
		List<PushMessage> pushMsgList = pushMessageService.findPushMsgAll();
		return pushMsgList;
	}
	
}
