package com.smart.socket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.socket.bean.domain.GarbageBinInfo;
import com.smart.socket.mapping.GarbageBinInfoMapper;

@Service
public class GarbageBinInfoService {
	
	@Autowired
	private GarbageBinInfoMapper infoMapper;
	
	@Transactional
	public int saveGbInfo(GarbageBinInfo garbageBinInfo) {
		if (garbageBinInfo != null) {
			return infoMapper.insert(garbageBinInfo);
		}else{
			return 0;
		}
	}
	
	@Transactional
	public void batchSaveGbInfo(List<GarbageBinInfo> garbageBinInfos) {
		if (garbageBinInfos != null && garbageBinInfos.size() > 0) {
			infoMapper.batchInsert(garbageBinInfos);
		}
	}
	
	public List<GarbageBinInfo> findAllGbInfo(){
		return infoMapper.selGbInfoAll();
	}
	
	public List<String> findAllGbInfoId(){
		return infoMapper.selGbInfoAllId();
	}
	
	@Transactional
	public void batchUpdateGbInfo(List<GarbageBinInfo> garbageBinInfos){
		infoMapper.updateGbInfoAll2(garbageBinInfos);
	}
	
	public GarbageBinInfo getGbInfoByDtuId(String dtuId){
		return infoMapper.getGbInfoByDtuId(dtuId);
	}
	
	@Transactional
	public int updateGbInfoByDtu(GarbageBinInfo gbInfo){
		return infoMapper.updateGbInfoByDtu(gbInfo);
	}
	
	//更新设备连接状态
	public void updateGbInfoConnectState(String dtuId,String connectState){
		infoMapper.updateGbInfoConnectState(dtuId,connectState);
	}
}
