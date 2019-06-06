package com.smart.socket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.socket.bean.domain.CarOilMsg;
import com.smart.socket.mapping.CarOilMsgMapper;

@Service
public class CarOilMsgService {
	
	@Autowired
	private CarOilMsgMapper msgMapper;
	
	@Transactional
	public int saveCarInfo(CarOilMsg oilMsg) {
		if (oilMsg != null) {
			return msgMapper.insertOilMsg(oilMsg);
		}else{
			return 0;
		}
	}
}
