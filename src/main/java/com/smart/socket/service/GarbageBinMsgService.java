package com.smart.socket.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.socket.bean.domain.GarbageBinMsg;
import com.smart.socket.mapping.GarbageBinMsgMapper;

@Service
public class GarbageBinMsgService {
	
	@Autowired
	private GarbageBinMsgMapper msgMapper;
	
	@Transactional
	public void saveGbMsg(GarbageBinMsg garbageBinMsg) {
		if (garbageBinMsg != null) {
			msgMapper.insertGbMsg(garbageBinMsg);
		}
	}
	
	//获取dtu维度最新的信息
	public List<GarbageBinMsg> findWillDealGbMsg(Date nowDate){
		return msgMapper.findWillDealGbMsg(selectNeedDate(nowDate));
	}
	
	@Transactional
	public void updateGbMsgDealStateAll(List<GarbageBinMsg> gbMsgList){
		msgMapper.updateGbMsgDealStateAll(gbMsgList);
	}

	//获取dtu维度垃圾箱出现0的记录，将其记为垃圾清理记录
	//TODO 还需要比较此垃圾桶之前的数据不是200
	public List<GarbageBinMsg> findDealGbCleanMsg(Date nowDate) {
		return msgMapper.findDealGbCleanMsg(selectNeedDate(nowDate));
	}
	
	@Transactional
	public void updateGbMsgByDtu(GarbageBinMsg gbMsg){
		msgMapper.updateGbMsgByDtu(gbMsg);
	}
	
	//处理查询时需要的时间
	private String selectNeedDate(Date nowDate){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.MINUTE, -60);
		return formatter.format(calendar.getTime());
	}
	
}
