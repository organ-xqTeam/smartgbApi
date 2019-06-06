package com.smart.socket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.socket.bean.domain.PushMessage;
import com.smart.socket.mapping.PushMessageMapper;

@Service
public class PushMessageService {
	
	@Autowired
	private PushMessageMapper msgMapper;
	
	@Transactional
	public void saveGbMsg(PushMessage pushMsg) {
		if (pushMsg != null) {
			msgMapper.insert(pushMsg);
		}
	}
	
	//获取dtu维度最新的信息
	public List<PushMessage> findPushMsgAll(){
		return msgMapper.selPushMsgAll();
	}
	
}
