package com.smart.socket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.socket.mapping.GarbageBinLogMapper;

@Service
public class GarbageBinLogService {
	
	@Autowired
	private GarbageBinLogMapper logMapper;
	
	@Transactional
	public void saveLogMsg(String log) {
		logMapper.insert(log);
	}
	
}
