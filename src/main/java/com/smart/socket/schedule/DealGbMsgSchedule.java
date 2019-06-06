package com.smart.socket.schedule;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smart.socket.bean.domain.GarbageBinInfo;
import com.smart.socket.bean.domain.GarbageBinMsg;
import com.smart.socket.common.ConstantForMessage;
import com.smart.socket.http.AmapHttpServer;
import com.smart.socket.service.GarbageBinInfoService;
import com.smart.socket.service.GarbageBinMsgService;

@Component
public class DealGbMsgSchedule {

	public final static long THREE_MINUTE = 1 * 60 * 1000;
	
	private static Logger log = LoggerFactory.getLogger(DealGbMsgSchedule.class);
	
	@Autowired
	private GarbageBinMsgService msgService;
	
	@Autowired
	private GarbageBinInfoService infoService;
	
	@Autowired
	private AmapHttpServer amapHttpServer;
	
	/**
	 * 将请求信息处理为有效信息
	 */
	/*@SuppressWarnings("static-access")
	@Scheduled(fixedDelay = THREE_MINUTE)
	@Async("initSchedulingTaskExecutor")*/
	public void gbMsgToInfoJob() {
		log.info("Schedule run gbMsgToInfoJob by THREE_MINUTE , and it is begin...");
		Date now = new Date();
		List<GarbageBinMsg> willDealMsgs = msgService.findWillDealGbMsg(now);//获取需要处理的MSG
		List<String> inDBGbInfoIds = infoService.findAllGbInfoId();//获取数据库中数据ID
		if(0 == willDealMsgs.size()){
			log.info("Schedule run gbMsgToInfoJob , there is not data to deal !");
			return;
		}
		long start = System.currentTimeMillis();
		Set<GarbageBinInfo> newGbInfoSet = new HashSet<GarbageBinInfo>();//需要新增的INFO
		Set<GarbageBinInfo> upGbInfoSet = new HashSet<GarbageBinInfo>();//需要更新的INFO
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(0);//小数点取后N位
		for(GarbageBinMsg gbMsg : willDealMsgs){
			gbMsg.setUpdate_date(now);
			GarbageBinInfo gbInfo = new GarbageBinInfo();
			gbInfo.setDtu_id(gbMsg.getDtu_id());
			gbInfo.setLatitude(gbMsg.getLatitude());
			gbInfo.setLongitude(gbMsg.getLongitude());
			gbInfo.setDel_flag(0);
			gbInfo.setCreate_date(now);
			gbInfo.setGarbage_total(ConstantForMessage.GARBAGE_TOTAL_MANY);
			gbInfo.setConnect_state("正常");
			int gbMsgMany = Integer.valueOf(gbMsg.getGarbage_state());
			if(gbMsgMany > ConstantForMessage.GARBAGE_TOTAL_MANY || gbMsgMany < 0){
				continue;
			}
			int garbageMany = ConstantForMessage.GARBAGE_TOTAL_MANY - gbMsgMany;
			int fullMany = 0 ;
			boolean isUpGbInfo = false;//是否为更新数据
			if(inDBGbInfoIds.contains(gbInfo.getDtu_id())){
				isUpGbInfo = true;
			}
			//判断垃圾箱满度是否为0
			if(0 == garbageMany){
				gbInfo.setGarbage_many("0");
			}else{
				fullMany = Integer.valueOf(numberFormat.format((float)garbageMany/(float)ConstantForMessage.GARBAGE_TOTAL_MANY*100));
				gbInfo.setGarbage_many(String.valueOf(fullMany));
			}
			//判断是否更新数据,更新数据需要若垃圾量为0则记录清理
			if(isUpGbInfo){
				gbInfo.setUpdate_date(now);
			}
			
			if(("").equals(gbMsg.getLatitude())||("").equals(gbMsg.getLongitude())){
				gbInfo.setGps_state("未绑定");
			}else{
				gbInfo.setGps_state("已绑定");
			}
			if(fullMany>79){
				gbInfo.setMonitor_state("预警");
				//TODO 预警消息推送
			}else{
				gbInfo.setMonitor_state("正常");
			}
			if(!isUpGbInfo){
				String position = amapHttpServer.getPositionByLatLgt(gbMsg.getLatitude(),gbMsg.getLongitude());
				gbInfo.setPosition(position);
				newGbInfoSet.add(gbInfo);
			}else{
				upGbInfoSet.add(gbInfo);
			}
		}
		
		if(0 < newGbInfoSet.size()){
			infoService.batchSaveGbInfo(new ArrayList<GarbageBinInfo>(newGbInfoSet));
			log.info("新增INFO信息: "+newGbInfoSet.size());
		}
		if(0 < upGbInfoSet.size()){
			infoService.batchUpdateGbInfo(new ArrayList<GarbageBinInfo>(upGbInfoSet));
			log.info("更新INFO信息: "+upGbInfoSet.size());
		}
		//更新msg中处理状态
		if(newGbInfoSet.size()>0||upGbInfoSet.size()>0){
			msgService.updateGbMsgDealStateAll(willDealMsgs);
			log.info("更新MSG信息: "+willDealMsgs.size());
		}
		long end = System.currentTimeMillis();
		log.info("Schedule run gbMsgToInfoJob by THREE_MINUTE , and it is end ! it costs time :\t" + (end-start)/1000 + "s");
	}
	
}
