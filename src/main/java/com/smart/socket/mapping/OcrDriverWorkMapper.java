package com.smart.socket.mapping;

import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.smart.socket.bean.domain.OcrDriverWork;

public interface OcrDriverWorkMapper {

	@Insert("insert into ocr_driver_work(name,login_name,sex,phone,id_num,drive_num,position,on_work,del_flag)"
			+ " values(#{name},#{login_name},#{sex},#{phone},#{id_num},#{drive_num},#{position},#{on_work},#{del_flag})")
	int saveDriverWork(OcrDriverWork ocrDriverWork);
	
	@Update("update ocr_driver_work set off_work=#{offDate} where id=#{id}")
	int updateDriverWorkOff(@Param("offDate")Date offDate,@Param("id")Long id);
	
	@Update("update ocr_driver_work set del_flag=1 where id=#{id}")
	void delDriverWork(@Param("id")String id);
	
	@Select("select * from ocr_driver_work where login_name=#{loginName} and on_work>#{onDate}")
	OcrDriverWork selDriverWorkByLogin(@Param("onDate")Date onDate,@Param("loginName")String loginName);
	
}