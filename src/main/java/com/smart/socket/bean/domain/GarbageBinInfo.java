package com.smart.socket.bean.domain;

import java.util.Date;

/**
 * 
 * @author ybc
 * @since 2018-6-4
 */
public class GarbageBinInfo {

	//设备编码6*BYTE
	private String dtu_id;
	
	//垃圾量
	private String garbage_many;
	
	//垃圾深度(默认为200cm)
	private Integer garbage_total;
	
	//位置
	private String position;
	
	//纬度
	private String latitude;
	
	//经度
	private String longitude;
	
	//连接状态
	private String connect_state;
	
	//监测状态
	private String monitor_state;
	
	//GPS状态
	private String gps_state;
	
	//是否删除
	private Integer del_flag;
	
	//创建时间
	private Date create_date;
	
	//更新时间
	private Date update_date;

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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getConnect_state() {
		return connect_state;
	}

	public void setConnect_state(String connect_state) {
		this.connect_state = connect_state;
	}

	public String getGps_state() {
		return gps_state;
	}

	public void setGps_state(String gps_state) {
		this.gps_state = gps_state;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public Integer getGarbage_total() {
		return garbage_total;
	}

	public void setGarbage_total(Integer garbage_total) {
		this.garbage_total = garbage_total;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public String getMonitor_state() {
		return monitor_state;
	}

	public void setMonitor_state(String monitor_state) {
		this.monitor_state = monitor_state;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Integer getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(Integer del_flag) {
		this.del_flag = del_flag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dtu_id == null) ? 0 : dtu_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GarbageBinInfo other = (GarbageBinInfo) obj;
		if (dtu_id == null) {
			if (other.dtu_id != null)
				return false;
		} else if (!dtu_id.equals(other.dtu_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GarbageBinInfo [dtu_id=" + dtu_id + ", garbage_many=" + garbage_many + ", garbage_total="
				+ garbage_total + ", position=" + position + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", connect_state=" + connect_state + ", monitor_state=" + monitor_state + ", gps_state=" + gps_state
				+ ", del_flag=" + del_flag + ", create_date=" + create_date + ", update_date=" + update_date + "]";
	}

}
