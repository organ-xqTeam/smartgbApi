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
import com.smart.socket.service.GarbageBinInfoService;

/**
 * 更新垃圾箱状态值
 * @author ybc
 * @since 2018-06-08
 */
@Component
public class UpdateGbInfoTask {
	
	private static Logger log = LoggerFactory.getLogger(UpdateGbInfoTask.class);
	
	@Autowired
	private GarbageBinInfoService infoService;
	
	@Autowired
	private DealGbCleanTask gbCleanTask;
	
	@Async("initTaskAsyncPool")
	public void updateGbInfo(GarbageBinMsg gbMsg) {
		//更新值必须保证数据库中已经存在此垃圾箱
		GarbageBinInfo inDBgbInfo = infoService.getGbInfoByDtuId(gbMsg.getDtu_id());
		if(inDBgbInfo!=null){
			//如果垃圾量为0则进入垃圾清理线程处理
			if(Integer.valueOf(gbMsg.getGarbage_state())>=ConstantForMessage.GARBAGE_TOTAL_MANY){
				gbCleanTask.doDtuMsgTask(gbMsg,inDBgbInfo);
			}
			log.info("UpdateGbInfoTask updateGbInfo will update gbInfo " + gbMsg.toString());
			Date now = new Date();
			NumberFormat numberFormat = NumberFormat.getInstance();
			numberFormat.setMaximumFractionDigits(0);//小数点取后N位
			GarbageBinInfo gbInfo = new GarbageBinInfo();
			gbInfo.setDtu_id(gbMsg.getDtu_id());
			gbInfo.setUpdate_date(now);
			int gbMsgMany = Integer.valueOf(gbMsg.getGarbage_state());
			int garbageMany = ConstantForMessage.GARBAGE_TOTAL_MANY-gbMsgMany>0 ? ConstantForMessage.GARBAGE_TOTAL_MANY-gbMsgMany : 0;
			int fullMany = 0 ;
			if(0 == garbageMany){
				gbInfo.setGarbage_many("0");
			}else{
				fullMany = Integer.valueOf(numberFormat.format((float)garbageMany/(float)ConstantForMessage.GARBAGE_TOTAL_MANY*100));
				gbInfo.setGarbage_many(String.valueOf(fullMany));
			}
			if(fullMany>79){
				gbInfo.setMonitor_state("预警");
				//TODO 预警消息推送
			}else{
				gbInfo.setMonitor_state("正常");
			}
			gbInfo.setConnect_state("正常");
			infoService.updateGbInfoByDtu(gbInfo);
			log.info("UpdateGbInfoTask updateGbInfo will update gbInfo end " + gbInfo.toString());
		}
	}
}
