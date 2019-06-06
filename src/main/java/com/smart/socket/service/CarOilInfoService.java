package com.smart.socket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.socket.bean.domain.CarOilInfo;
import com.smart.socket.mapping.CarOilInfoMapper;

@Service
public class CarOilInfoService {
	
	@Autowired
	private CarOilInfoMapper infoMapper;
	
	@Transactional
	public int saveCarInfo(CarOilInfo carInfo) {
		if (carInfo != null) {
			return infoMapper.insert(carInfo);
		}else{
			return 0;
		}
	}
	
	public List<CarOilInfo> findWillDealOilInfo(String startDate,String endDate){
		return infoMapper.findWillDealOilMsg(startDate, endDate);
	}
}
