package com.smart.socket.provider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.smart.socket.bean.domain.GarbageBinInfo;

public class GarbageBinInfoProvider {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String insertAll(Map map) {
		List<GarbageBinInfo> gbInfoList = (List<GarbageBinInfo>) map.get("list");
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO SMART_GARBAGEBIN_INFO");
		sb.append("(dtu_id,garbage_many,garbage_total,position,latitude,longitude,connect_state,monitor_state,gps_state,del_flag,create_date) ");
		sb.append("VALUES ");
		MessageFormat mf = new MessageFormat("(#'{'list[{0}].dtu_id}, #'{'list[{0}].garbage_many},#'{'list[{0}].garbage_total},#'{'list[{0}].position},"
				+ "#'{'list[{0}].latitude},#'{'list[{0}].longitude},#'{'list[{0}].connect_state},#'{'list[{0}].monitor_state},#'{'list[{0}].gps_state},"
				+ "#'{'list[{0}].del_flag},#'{'list[{0}].create_date})");
		for (int i = 0; i < gbInfoList.size(); i++) {
			sb.append(mf.format(new Object[]{i}));
			if (i < gbInfoList.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String updateAll(Map map){
		List<GarbageBinInfo> gbInfoList = (List<GarbageBinInfo>) map.get("list");
		StringBuilder sb = new StringBuilder();
		MessageFormat mf = new MessageFormat("update SMART_GARBAGEBIN_INFO set garbage_many=#'{'list[{0}].garbage_many},"
				+ "connect_state=#'{'list[{0}].connect_state},monitor_state=#'{'list[{0}].monitor_state},"
				+ "gps_state=#'{'list[{0}].gps_state},update_date=#'{'list[{0}].update_date}  where dtu_id=#'{'list[{0}].dtu_id}");
		for (int i = 0; i < gbInfoList.size(); i++) {
			sb.append(mf.format(new Object[]{i}));
			if (i < gbInfoList.size() - 1) {
				sb.append(";");
			}
		}
		sb.append(";");
		return sb.toString();
	}
}
