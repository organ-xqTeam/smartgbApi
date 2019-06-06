package com.smart.socket.bean.domain;

import java.util.Date;

/**
 * 
 * @author ybc
 * @since 2018-6-4
 */
public class GarbageBinMsg {
	
	private Long id;

	//设备编码6*BYTE
	private String dtu_id;
	
	//垃圾桶状态3*BYTE(垃圾箱深度)
	private String garbage_state;
	
	//定位状态1*BYTE
	private String position_state;
	
	//纬度9*BYTE
	private String latitude;
	
	//纬度半球N:北半球,S:南半球
	private String hemisphere_lat;
	
	//经度9*BYTE
	private String longitude;
	
	//经度半球E:东半球,W:西半球
	private String hemisphere_lgt;
	
	//ERR码(2*BYTE)00:正常,01:传感器通讯失败,02:dtu信号弱,03:dtu读取数据失败
	private String err;
	
	//处理状态
	private Integer deal_state;
	
	//累加和(1*BYTE)
	private String byte_sum;
	
	//创建时间
	private Date create_date;
	
	private Date update_date;

	public String getDtu_id() {
		return dtu_id;
	}

	public void setDtu_id(String dtu_id) {
		this.dtu_id = dtu_id;
	}

	public String getGarbage_state() {
		return garbage_state;
	}

	public void setGarbage_state(String garbage_state) {
		this.garbage_state = garbage_state;
	}

	public String getPosition_state() {
		return position_state;
	}

	public void setPosition_state(String position_state) {
		this.position_state = position_state;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getHemisphere_lat() {
		return hemisphere_lat;
	}

	public void setHemisphere_lat(String hemisphere_lat) {
		this.hemisphere_lat = hemisphere_lat;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getHemisphere_lgt() {
		return hemisphere_lgt;
	}

	public void setHemisphere_lgt(String hemisphere_lgt) {
		this.hemisphere_lgt = hemisphere_lgt;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getByte_sum() {
		return byte_sum;
	}

	public void setByte_sum(String byte_sum) {
		this.byte_sum = byte_sum;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDeal_state() {
		return deal_state;
	}

	public void setDeal_state(Integer deal_state) {
		this.deal_state = deal_state;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	@Override
	public String toString() {
		return "GarbageBinMsg [id=" + id + ", dtu_id=" + dtu_id + ", garbage_state=" + garbage_state
				+ ", position_state=" + position_state + ", latitude=" + latitude + ", hemisphere_lat=" + hemisphere_lat
				+ ", longitude=" + longitude + ", hemisphere_lgt=" + hemisphere_lgt + ", err=" + err + ", deal_state="
				+ deal_state + ", byte_sum=" + byte_sum + ", create_date=" + create_date + ", update_date="
				+ update_date + "]";
	}

}
