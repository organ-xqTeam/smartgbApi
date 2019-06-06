package com.smart.socket.thread;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.smart.socket.bean.domain.GarbageBinClean;
import com.smart.socket.bean.domain.GarbageBinInfo;
import com.smart.socket.bean.domain.GarbageBinMsg;
import com.smart.socket.service.GarbageBinCleanService;

/**
 * 垃圾清理记录线程
 * @author ybc
 * @since 2018-06-08
 */
@Component
public class DealGbCleanTask {
	
	private static Logger log = LoggerFactory.getLogger(DealGbCleanTask.class);
	
	@Autowired
	private GarbageBinCleanService cleanService;
	
	//消息内容存入库中
	@Async("initTaskAsyncPool")
	public void doDtuMsgTask(GarbageBinMsg gbMsg,GarbageBinInfo gbInfo) {
		if(0 < Integer.valueOf(gbInfo.getGarbage_many())){
			log.info("THREAD : DealGbCleanTask doDtuMsgTask will do " + gbMsg.toString());
			Date now = new Date();
			GarbageBinClean gbClean = new GarbageBinClean();
			gbClean.setDtu_id(gbMsg.getDtu_id());
			gbClean.setClean_date(now);
			gbClean.setCreate_date(now);
			gbClean.setGarbage_many(gbInfo.getGarbage_many());//获取原始记录垃圾量的值
			gbClean.setPosition(gbInfo.getPosition());
			gbClean.setDel_flag(0);
			cleanService.saveGbClean(gbClean);
			log.info("THREAD : DealGbCleanTask doDtuMsgTask do saved " + gbClean.toString());
		}
	}
}
