package com.smart.socket.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.smart.socket.bean.domain.PushMessage;

public interface PushMessageMapper {

	@Insert("insert into ocr_message(id,title,content,receive_side,status,"
			+ "create_by,create_date,remarks,del_flag) values(#{id},"
			+ "#{title},#{content},#{receive_side},#{status},#{create_by},#{create_date},"
			+ "#{remarks},#{del_flag})")
	int insert(PushMessage pushMsg);
	
	@Select("select * from ocr_message where del_flag=0 order by create_date desc")
	List<PushMessage> selPushMsgAll();
	
}