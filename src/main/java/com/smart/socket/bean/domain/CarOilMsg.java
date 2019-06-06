package com.smart.socket.bean.domain;

import java.util.Date;

/**
 * 汽车每日油耗信息
 * @author ybc
 *
 */
public class CarOilMsg {

	private Long id;
	
	private String car_id;
	
	private String plate_num;
	
	private Date go_date;
	
	private Date arrive_date;
	
	private String car_band;
	
	private String oil_num;
	
	private Date create_date;

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

	public String getPlate_num() {
		return plate_num;
	}

	public void setPlate_num(String plate_num) {
		this.plate_num = plate_num;
	}

	public Date getGo_date() {
		return go_date;
	}

	public void setGo_date(Date go_date) {
		this.go_date = go_date;
	}

	public Date getArrive_date() {
		return arrive_date;
	}

	public void setArrive_date(Date arrive_date) {
		this.arrive_date = arrive_date;
	}

	public String getCar_band() {
		return car_band;
	}

	public void setCar_band(String car_band) {
		this.car_band = car_band;
	}

	public String getOil_num() {
		return oil_num;
	}

	public void setOil_num(String oil_num) {
		this.oil_num = oil_num;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

}
