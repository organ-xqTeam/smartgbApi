package com.smart.socket.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smart.socket.bean.domain.CarInfo;
import com.smart.socket.bean.domain.CarOilInfo;
import com.smart.socket.bean.domain.CarOilMsg;
import com.smart.socket.service.CarInfoService;
import com.smart.socket.service.CarOilInfoService;
import com.smart.socket.service.CarOilMsgService;

/**
 * 处理每日的油耗信息
 * @author ybc
 * @since 2018-09-08
 */
@Component
public class DealCarOilSchedule {

	private static Logger log = LoggerFactory.getLogger(DealCarOilSchedule.class);
	
	@Autowired
	private CarOilInfoService oilInfoService;
	
	@Autowired
	private CarOilMsgService oilMsgService;
	
	@Autowired
	private CarInfoService carInfoService;
	
	@SuppressWarnings("static-access")
	//@Scheduled(cron = "0 1 0 * * ? ")
	@Scheduled(cron = "0 44 20 * * ? ")
	@Async("initSchedulingTaskExecutor")
	public void carOilMsgJob() throws ParseException {
		log.info("Schedule run carOilMsgJob by THREE_MINUTE , and it is begin...");
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String startDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(now);
		long start = System.currentTimeMillis();
		List<CarOilInfo> oilInfos = oilInfoService.findWillDealOilInfo(startDate,endDate);
		if(0 < oilInfos.size() ) {
			List<String> carIdList = new ArrayList<String>();
			Map<String,ArrayList<CarOilInfo>> dealMap = new HashMap<String,ArrayList<CarOilInfo>>();
			for(CarOilInfo oilInfo : oilInfos){
				if(carIdList.contains(oilInfo.getCar_id())) {
					List<CarOilInfo> old = dealMap.get(oilInfo.getCar_id());
					old.add(oilInfo);
				}else {
					ArrayList<CarOilInfo> list = new ArrayList<CarOilInfo>();
					list.add(oilInfo);
					dealMap.put(oilInfo.getCar_id(), list);
					carIdList.add(oilInfo.getCar_id());
				}
			}
			if(!dealMap.isEmpty()) {
				for(String carId : carIdList) {
					ArrayList<CarOilInfo> carOilList = dealMap.get(carId);
					carOilList.sort((carOil1, carOil2) -> carOil1.getCar_time().compareTo(carOil2.getCar_time()));
					String oilTotal = this.dealMsg2OilNum(carOilList);
					CarInfo car = carInfoService.findCarInfo(carId);
					CarOilMsg carOilMsg = new CarOilMsg();
					carOilMsg.setCar_id(carId);
					carOilMsg.setCreate_date(now);
					carOilMsg.setGo_date(dealMsg2StartDate(carOilList));
					carOilMsg.setArrive_date(dealMsg2EndDate(carOilList));
					carOilMsg.setOil_num(oilTotal);
					carOilMsg.setCar_band(car.getCar_band());
					carOilMsg.setPlate_num(car.getPlate_num());
					oilMsgService.saveCarInfo(carOilMsg);
					log.info("save oilTotalMsg success : " + carOilMsg.toString());
				}
			}
		}
		long end = System.currentTimeMillis();
		log.info("Schedule run carOilMsgJob by THREE_MINUTE , and it is end ! it costs time :\t" + (end-start)/1000 + "s");
	}
	
	//处理油耗总数
	private String dealMsg2OilNum(List<CarOilInfo> oilInfos) {
		Double oilTotal = new Double(0);
		Double startOilNum = null;
		for(CarOilInfo oilInfo : oilInfos) {
			Double currentOil = Double.valueOf(oilInfo.getOil());
			if(null == startOilNum) {
				startOilNum = currentOil;
				continue;
			}else {
				//比较当前油表读数与上一个油表读数大小关系
				if(currentOil < startOilNum) {
					oilTotal += startOilNum - currentOil;
				}
				startOilNum = currentOil;
			}
		}
		return String.valueOf(oilTotal);
	}
	
	//获取最早出发时间
	private Date dealMsg2StartDate(List<CarOilInfo> carOilList) {
		return carOilList.get(0).getCar_time();
	}
	
	//获取最晚达到时间
	private Date dealMsg2EndDate(ArrayList<CarOilInfo> carOilList) {
		return carOilList.get(carOilList.size()-1).getCar_time();
	}
}
