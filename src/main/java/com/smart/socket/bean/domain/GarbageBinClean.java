package com.smart.socket.bean.domain;

import java.util.Date;

/**
 * 
 * @author ybc
 * @since 2018-6-4
 */
public class GarbageBinClean {

	private Long id;
	
	//设备编码6*BYTE
	private String dtu_id;
	
	//垃圾量
	private String garbage_many;
	
	//位置
	private String position;
	
	//清理时间
	private Date clean_date;
	
	//创建时间
	private Date create_date;
	
	//是否删除
	private int del_flag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDtu_id() {
		return dtu_id;
	}

	public void setDtu_id(String dtu_id) {
		this.dtu_id = dtu_id;
	}

	public String getGarbage_many() {
		return garbage_many;
	}

	public void setGarbage_many(String garbage_many) {
		this.garbage_many = garbage_many;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getClean_date() {
		return clean_date;
	}

	public void setClean_date(Date clean_date) {
		this.clean_date = clean_date;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public int getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(int del_flag) {
		this.del_flag = del_flag;
	}

}
