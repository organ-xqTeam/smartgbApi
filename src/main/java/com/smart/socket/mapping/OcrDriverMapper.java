package com.smart.socket.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.smart.socket.bean.domain.OcrDriver;

public interface OcrDriverMapper {

	@Select("select count(1) from ocr_driver where login_name=#{name} and password=#{pwd}")
	int selDriverLogin(@Param("name") String name,@Param("pwd") String pwd);
	
	@Select("select * from ocr_driver where login_name=#{name} and password=#{pwd}")
	OcrDriver selDriverByLogin(@Param("name") String name,@Param("pwd") String pwd);
	
	@Select("select * from ocr_driver where login_name=#{name}")
	OcrDriver selDriverByName(@Param("name") String name);
	
	
}