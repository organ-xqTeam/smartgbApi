package com.smart.socket.thread;

import java.text.NumberFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.smart.socket.bean.domain.GarbageBinInfo;
import com.smart.socket.bean.domain.GarbageBinMsg;
import com.smart.socket.common.ConstantForMessage;
import com.smart.socket.http.AmapHttpServer;
import com.smart.socket.service.GarbageBinInfoService;
import com.smart.socket.util.CoordinationConvertUtil;
import com.smart.socket.util.CoordinationConvertUtil.LatLng;

/**
 * 垃圾箱初始化线程
 * @author ybc
 * @since 2018-06-16
 */
@Component
public class InitGbIntoTask {
	
	private static Logger log = LoggerFactory.getLogger(InitGbIntoTask.class);
	
	@Autowired
	private GarbageBinInfoService infoService;
	
	@Autowired
	private AmapHttpServer amapHttpServer;
	
	//消息内容存入库中
	@Async("initTaskAsyncPool")
	public void initGbInfo(GarbageBinMsg gbMsg) {
		//若数据库中已存在此垃圾箱，则不用初始化
		if(infoService.getGbInfoByDtuId(gbMsg.getDtu_id())!=null){
			log.info("THREAD : InitGbIntoTask initGbInfo 数据库已存在此数据 " + gbMsg.toString());
			return;
		}
		GarbageBinInfo gbInfo = new GarbageBinInfo();
		log.info("THREAD : InitGbIntoTask initGbInfo will do " + gbMsg.toString());
		Date now = new Date();
		LatLng latLng = CoordinationConvertUtil.dealGPSToMap(gbMsg.getLatitude(),gbMsg.getLongitude());
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(0);//小数点取后N位
		int fullMany = 0 ;
		gbInfo.setDtu_id(gbMsg.getDtu_id());
		int gbMsgMany = Integer.valueOf(gbMsg.getGarbage_state());
		int garbageMany = ConstantForMessage.GARBAGE_TOTAL_MANY - gbMsgMany;
		//判断垃圾箱满度是否为0
		if(0 == garbageMany){
			gbInfo.setGarbage_many("0");
		}else{
			fullMany = Integer.valueOf(numberFormat.format((float)garbageMany/(float)ConstantForMessage.GARBAGE_TOTAL_MANY*100));
			gbInfo.setGarbage_many(String.valueOf(fullMany));
		}
		gbInfo.setGarbage_total(ConstantForMessage.GARBAGE_TOTAL_MANY);
		gbInfo.setLatitude(String.valueOf(latLng.getLatitude()));
		gbInfo.setLongitude(String.valueOf(latLng.getLongitude()));
		gbInfo.setDel_flag(0);
		gbInfo.setCreate_date(now);
		gbInfo.setConnect_state("正常");
		gbInfo.setGps_state("已绑定");
		if(fullMany>79){
			gbInfo.setMonitor_state("预警");
			//TODO 预警消息推送
		}else{
			gbInfo.setMonitor_state("正常");
		}
		String position = amapHttpServer.getPositionByLatLgt(String.valueOf(latLng.getLatitude()),String.valueOf(latLng.getLongitude()));
		gbInfo.setPosition(position);
		infoService.saveGbInfo(gbInfo);
		log.info("THREAD : InitGbIntoTask initGbInfo do end " + gbInfo.toString());
	}
	
	public static void main(String[] args) {
		LatLng latLng = CoordinationConvertUtil.dealGPSToMap("4148.8495","12318.8743");
		AmapHttpServer amapHttpServer = new AmapHttpServer();
		System.out.println(amapHttpServer.getPositionByLatLgt(String.valueOf(latLng.getLatitude()),String.valueOf(latLng.getLongitude())));
	}
}
