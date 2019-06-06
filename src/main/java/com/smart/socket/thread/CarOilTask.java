package com.smart.socket.thread;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.smart.socket.bean.domain.CarOilInfo;
import com.smart.socket.bean.domain.LocationModel;
import com.smart.socket.service.CarOilInfoService;

/**
 * 车载设备定时发送的油耗及位置信息处理入库
 * @author ybc
 * @since 2018-9-2
 */
@Component
public class CarOilTask {

	private static Logger log = LoggerFactory.getLogger(CarOilTask.class);
	
	@Autowired
	private CarOilInfoService infoService;
	
	@Async("initTaskAsyncPool")
	public void initGbInfo(String carId,LocationModel lm) {
		CarOilInfo carInfo = new CarOilInfo();
		Date now = new Date();
		carInfo.setCar_time(now);
		carInfo.setCar_id(carId);//车载设备手机号
		carInfo.setCreate_time(now);
		carInfo.setLatitude(lm.getLat());
		carInfo.setLongitude(lm.getLgt());
		carInfo.setOil(lm.getOilnum());
		infoService.saveCarInfo(carInfo);
		log.info("initGbInfo save ..." + carInfo.toString());
	}
	
}
