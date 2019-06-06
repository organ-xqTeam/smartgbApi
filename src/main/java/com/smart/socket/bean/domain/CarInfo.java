package com.smart.socket.bean.domain;

import java.util.Date;

/**
 * 汽车信息
 * @author ybc
 *
 */
public class CarInfo {

	private Long id;
	
	private String car_band;
	
	private String plate_num;
	
	private String terminal_phone;
	
	private String is_delete;
	
	private Date create_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCar_band() {
		return car_band;
	}

	public void setCar_band(String car_band) {
		this.car_band = car_band;
	}

	public String getPlate_num() {
		return plate_num;
	}

	public void setPlate_num(String plate_num) {
		this.plate_num = plate_num;
	}

	public String getTerminal_phone() {
		return terminal_phone;
	}

	public void setTerminal_phone(String terminal_phone) {
		this.terminal_phone = terminal_phone;
	}

	public String getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
}
