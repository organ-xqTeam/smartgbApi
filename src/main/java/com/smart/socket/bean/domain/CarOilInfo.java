package com.smart.socket.bean.domain;

import java.util.Date;

/**
 * 记录汽车行驶记录
 * @author ybc
 *
 */
public class CarOilInfo {

	private Long id;
	
	private String car_id;
	
	private Date car_time;
	
	private String longitude;
	
	private String latitude;
	
	private String oil;
	
	private Date create_time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public Date getCar_time() {
		return car_time;
	}

	public void setCar_time(Date car_time) {
		this.car_time = car_time;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getOil() {
		return oil;
	}

	public void setOil(String oil) {
		this.oil = oil;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "CarOilInfo [id=" + id + ", car_id=" + car_id + ", car_time=" + car_time + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", oil=" + oil + ", create_time=" + create_time + "]";
	}
	
}
