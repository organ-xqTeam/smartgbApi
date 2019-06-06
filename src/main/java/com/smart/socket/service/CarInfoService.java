package com.smart.socket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.socket.bean.domain.CarInfo;
import com.smart.socket.mapping.CarInfoMapper;

@Service
public class CarInfoService {
	
	@Autowired
	private CarInfoMapper infoMapper;
	
	public CarInfo findCarInfo(String carId){
		return infoMapper.getCarInfoByCarId(carId);
	}
}
