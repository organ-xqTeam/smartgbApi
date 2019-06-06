package com.smart.socket.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.smart.socket.bean.domain.PushMessage;

public interface GarbageBinLogMapper {

	@Insert("insert into smart_garbagebin_log(create_date,msg) values(now(),#{log})")
	int insert(String log);
	
	@Select("select * from smart_garbagebin_log order by create_date desc")
	List<PushMessage> selLogAll();
	
}