package com.smart.socket.provider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.smart.socket.bean.domain.GarbageBinClean;

public class GarbageBinCleanProvider {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String insertAll(Map map) {
		List<GarbageBinClean> users = (List<GarbageBinClean>) map.get("list");
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO SMART_GARBAGEBIN_CLEAN");
		sb.append("(dtu_id,garbage_many,position,clean_date,create_date) ");
		sb.append("VALUES ");
		MessageFormat mf = new MessageFormat("(#'{'list[{0}].dtu_id}, #'{'list[{0}].garbage_many},#'{'list[{0}].position},#'{'list[{0}].clean_date},"
				+ "#'{'list[{0}].create_date})");
		for (int i = 0; i < users.size(); i++) {
			sb.append(mf.format(new Object[]{i}));
			if (i < users.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

}
