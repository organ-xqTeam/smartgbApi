package com.smart.socket.mapping;

import org.apache.ibatis.annotations.Insert;

import com.smart.socket.bean.domain.CarOilMsg;

public interface CarOilMsgMapper {

	@Insert("insert into ocr_driver_record(car_id,plate_num,go_date,arrive_date,car_band,oil_num,create_date) values(#{car_id},#{plate_num},#{go_date},"
			+ "#{arrive_date},#{car_band},#{oil_num},#{create_date})")
	int insertOilMsg(CarOilMsg oilMsg);

}