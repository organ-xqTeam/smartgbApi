package com.smart.socket.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.smart.socket.bean.domain.CarInfo;

public interface CarInfoMapper {

	@Select("select * from ocr_car_info where is_delete=0 and terminal_phone=#{carId}")
	CarInfo getCarInfoByCarId(@Param("carId") String carId);

}