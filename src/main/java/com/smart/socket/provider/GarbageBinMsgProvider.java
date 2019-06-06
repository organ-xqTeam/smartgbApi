package com.smart.socket.provider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.smart.socket.bean.domain.GarbageBinMsg;

public class GarbageBinMsgProvider {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String batchUpdateState(Map map) {
		List<GarbageBinMsg> gbMsgList = (List<GarbageBinMsg>) map.get("list");
		StringBuilder sb = new StringBuilder();
		sb.append("update SMART_GARBAGEBIN_MSG set deal_state=1 , update_date=now() ");
		sb.append("where id in (");
		MessageFormat mf = new MessageFormat("#'{'list[{0}].id}");
		for (int i = 0; i < gbMsgList.size(); i++) {
			sb.append(mf.format(new Object[]{i}));
			if (i < gbMsgList.size() - 1) {
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}

}
