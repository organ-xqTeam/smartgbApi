package com.smart.socket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.socket.bean.domain.GarbageBinClean;
import com.smart.socket.mapping.GarbageBinCleanMapper;

@Service
public class GarbageBinCleanService {
	
	@Autowired
	private GarbageBinCleanMapper cleanMapper;
	
	@Transactional
	public void saveGbClean(GarbageBinClean garbageBinClean) {
		if (garbageBinClean != null) {
			cleanMapper.insertGbClean(garbageBinClean);
		}
	}

	@Transactional
	public void saveAllGbClean(List<GarbageBinClean> garbageBinCleans) {
		if (garbageBinCleans != null && garbageBinCleans.size() > 0) {
			cleanMapper.batchInsert(garbageBinCleans);
		}
	}
}
