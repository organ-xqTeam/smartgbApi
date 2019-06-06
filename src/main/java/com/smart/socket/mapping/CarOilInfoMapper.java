package com.smart.socket.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.smart.socket.bean.domain.CarOilInfo;

public interface CarOilInfoMapper {

	@Insert("insert into ocr_driver_travel(car_id,car_time,longitude,latitude,direction,create_time,oil) "
			+ "values(#{car_id},#{car_time},#{longitude},#{latitude},0,#{create_time},#{oil})")
	int insert(CarOilInfo carInfo);
	
	@Select("SELECT a.id,a.car_id,a.car_time,a.longitude,a.latitude,a.oil,a.create_time FROM ocr_driver_travel a "
			+ "where a.car_time >= #{startDate} and a.car_time <= #{endDate}")
	List<CarOilInfo> findWillDealOilMsg(@Param("startDate") String startDate,@Param("endDate") String endDate);
	
}