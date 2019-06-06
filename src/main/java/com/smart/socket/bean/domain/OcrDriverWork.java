package com.smart.socket.bean.domain;

import java.util.Date;

public class OcrDriverWork {

	private Long id;
	
	private String name;
	
	private String login_name;
	
	private String sex;
	
	private String phone;
	
	private String id_num;
	
	private String drive_num;
	
	private String position;
	
	private Date on_work;
	
	private Date off_work;
	
	private int del_flag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId_num() {
		return id_num;
	}

	public void setId_num(String id_num) {
		this.id_num = id_num;
	}

	public String getDrive_num() {
		return drive_num;
	}

	public void setDrive_num(String drive_num) {
		this.drive_num = drive_num;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getOn_work() {
		return on_work;
	}

	public void setOn_work(Date on_work) {
		this.on_work = on_work;
	}

	public Date getOff_work() {
		return off_work;
	}

	public void setOff_work(Date off_work) {
		this.off_work = off_work;
	}

	public int getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(int del_flag) {
		this.del_flag = del_flag;
	}
	
}
