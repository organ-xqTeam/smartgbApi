package com.smart.socket.bean.domain;

import java.io.Serializable;

/**
 * Created by ytg on 2017/8/18.
 */

public class LocationModel extends PackageData implements Serializable {

	private static final long serialVersionUID = 1L;

	private int flag;// 报警标志

	private int status;// 状态

	private int longitude;// 经度

	private int latitude;// 纬度

	private int height;// 高度

	private int speed;// 速度

	private int direction;// 方向

	private String time;
	
	private String oilnum;//油量 1/10L,对应车上油量表读数
	
	private String lat;//实际纬度

	private String lgt;//实际经度

	public LocationModel() {}

	public LocationModel(PackageData packageData) {
		this();
		this.channel = packageData.getChannel();
		this.checkSum = packageData.getCheckSum();
		this.msgBodyBytes = packageData.getMsgBodyBytes();
		this.msgHeader = packageData.getMsgHeader();
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getOilnum() {
		return oilnum;
	}

	public void setOilnum(String oilnum) {
		this.oilnum = oilnum;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLgt() {
		return lgt;
	}

	public void setLgt(String lgt) {
		this.lgt = lgt;
	}

	@Override
	public String toString() {
		return "LocationModel [flag=" + flag + ", status=" + status + ", longitude=" + longitude + ", latitude="
				+ latitude + ", height=" + height + ", speed=" + speed + ", direction=" + direction + ", time=" + time
				+ ", oilnum=" + oilnum + ", lat=" + lat + ", lgt=" + lgt + "]";
	}

}
