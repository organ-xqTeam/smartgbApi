package com.smart.socket.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.socket.bean.domain.OcrDriver;
import com.smart.socket.bean.domain.OcrDriverWork;
import com.smart.socket.mapping.OcrDriverMapper;
import com.smart.socket.mapping.OcrDriverWorkMapper;
import com.smart.socket.util.MD5Util;

@Service
public class OcrDriverService {
	
	@Autowired
	private OcrDriverMapper ocrDriverMapper;
	
	@Autowired
	private OcrDriverWorkMapper ocrDriverWorkMapper;
	
	public boolean driverLogin(String name,String pwd){
		
		String pwdMD5 = MD5Util.string2MD5(pwd);
		
		int res = ocrDriverMapper.selDriverLogin(name, pwdMD5);
		
		return res == 0 ? false : true;
	}

	
	public OcrDriver getDriverByLogin(String name,String pwd){
		String pwdMD5 = MD5Util.string2MD5(pwd);
		OcrDriver ocrDriver = ocrDriverMapper.selDriverByLogin(name, pwdMD5);
		return ocrDriver;
	}
	
	public OcrDriver getDriverByName(String name){
		OcrDriver ocrDriver = ocrDriverMapper.selDriverByName(name);
		return ocrDriver;
	}
	
	public int saveDriverOnWork(OcrDriver ocrDriver){
		OcrDriverWork driverWork = new OcrDriverWork();
		driverWork.setName(ocrDriver.getReal_name());
		driverWork.setLogin_name(ocrDriver.getLogin_name());
		driverWork.setSex(ocrDriver.getSex());
		driverWork.setPhone(ocrDriver.getPhone());
		driverWork.setId_num(ocrDriver.getCard_number());
		driverWork.setDrive_num(ocrDriver.getJob_number());
		driverWork.setPosition(ocrDriver.getArea_id());
		driverWork.setOn_work(new Date());
		driverWork.setDel_flag(0);
		return ocrDriverWorkMapper.saveDriverWork(driverWork);
	}
	
	public int updateDriverOffWork(String loginName) throws Exception{
			Date nowDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String timeStr = format.format(nowDate);
			SimpleDateFormat transfer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date queryDate = transfer.parse(timeStr+" 00:00:00");
			OcrDriverWork driverWork = ocrDriverWorkMapper.selDriverWorkByLogin(queryDate, loginName);
			if(null != driverWork){
				return ocrDriverWorkMapper.updateDriverWorkOff(nowDate,driverWork.getId());
			}
			return 0;
	}
	
}
