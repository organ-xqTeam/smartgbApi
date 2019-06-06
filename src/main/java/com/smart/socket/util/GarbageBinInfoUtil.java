package com.smart.socket.util;

import java.util.Date;

import com.smart.socket.bean.domain.GarbageBinMsg;
import com.smart.socket.common.ConstantForMessage;
import com.smart.socket.util.CoordinationConvertUtil.LatLng;

/**
 * 对象转换工具类--单例模式
 * @author ybc
 * @since 2018-6-8
 */
public class GarbageBinInfoUtil {
	
	private GarbageBinInfoUtil(){}

	private static class SingletonHolder{
		private static GarbageBinInfoUtil instance=new GarbageBinInfoUtil();
	}
	
	public static GarbageBinInfoUtil getInstance(){
		return SingletonHolder.instance;
	}
	
	//处理请求信息为消息内容
	public GarbageBinMsg dealStr2Msg(String requestStr){
		GarbageBinMsg info = new GarbageBinMsg();
		String strs[] = requestStr.substring(ConstantForMessage.GARBAGE_MSG_HEAD,requestStr.length()-ConstantForMessage.GARBAGE_MSG_END).split(",");
		info.setDtu_id(strs[0]);
		info.setGarbage_state(strs[1]);
		info.setPosition_state(strs[2]);
		info.setLatitude(strs[3]);
		info.setHemisphere_lat(strs[4]);
		info.setLongitude(strs[5]);
		info.setHemisphere_lgt(strs[6]);
		info.setErr(strs[7]);
		info.setByte_sum(strs[8]);
		info.setCreate_date(new Date());
		return info;
	}
	
	public static void main(String[] args) {
		String lat = "4148.8035";
		String lgt = "12318.9349";
		LatLng lg = new LatLng(Double.valueOf(lat),Double.valueOf(lgt));
		LatLng latlng = CoordinationConvertUtil.transformFromWGSToGCJ(lg);
		System.out.println(latlng.getLatitude() + "\t" +latlng.getLongitude());
		
	}
}
