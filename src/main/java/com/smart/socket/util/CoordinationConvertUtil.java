package com.smart.socket.util;

import java.text.NumberFormat;

/**
 * GPS坐标转GCJ坐标;
 *
 * @author ybc
 * @since 2018-6-15
 */
public class CoordinationConvertUtil {

	private static final double a = 6378245.0;

	private static final double ee = 0.00669342162296594323;

	// 圆周率 GCJ_02_To_WGS_84
	private static double PI = 3.14159265358979324;

	/**
	 * GPS坐标转火星坐标
	 * 
	 * @param wgLoc
	 * @return
	 */
	public static LatLng transformFromWGSToGCJ(LatLng wgLoc) {

		// 如果在国外，则默认不进行转换
		/*
		 * if (outOfChina(wgLoc.latitude, wgLoc.longitude)) { return new
		 * LatLng(wgLoc.latitude, wgLoc.longitude); }
		 */
		double dLat = transformLatWGSToGCJ(wgLoc.longitude - 105.0, wgLoc.latitude - 35.0);
		double dLon = transformLonWGSToGCJ(wgLoc.longitude - 105.0, wgLoc.latitude - 35.0);
		double radLat = wgLoc.latitude / 180.0 * Math.PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);

		return new LatLng(wgLoc.latitude + dLat, wgLoc.longitude + dLon);
	}

	private static double transformLatWGSToGCJ(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(x > 0 ? x : -x);
		ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	private static double transformLonWGSToGCJ(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(x > 0 ? x : -x);
		ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0 * Math.PI)) * 2.0 / 3.0;
		return ret;
	}

	/*
	 * public static boolean outOfChina(double lat, double lon) { if (lon <
	 * 72.004 || lon > 137.8347) return true; if (lat < 0.8293 || lat > 55.8271)
	 * return true; return false; }
	 */

	/**
	 * @author 作者:
	 *         方法描述:方法可以将高德地图SDK获取到的GPS经纬度转换为真实的经纬度，可以用于解决安卓系统使用高德SDK获取经纬度的转换问题。
	 * @param 需要转换的经纬度
	 * @return 转换为真实GPS坐标后的经纬度
	 * @throws <异常类型>
	 *             {@inheritDoc} 异常描述
	 */
	public static LatLng transformFromGCJToWGS(double lat, double lon) {
		double dLat = transformLatGCJToWGS(lon - 105.0, lat - 35.0);
		double dLon = transformLonGCJToWGS(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);

		return new LatLng(lat - dLat, lon - dLon);
	}

	// 转换经度
	private static double transformLonGCJToWGS(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
		return ret;
	}

	// 转换纬度
	private static double transformLatGCJToWGS(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	public static class LatLng {

		public LatLng(double latitude, double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;
		}

		private double latitude;

		private double longitude;

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		@Override
		public String toString() {
			return "LatLng [latitude=" + latitude + ", longitude=" + longitude + "]";
		}

	}

	/**
	 * 将车机上传的经纬度信息转换成标准格式
	 * 
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public static LatLng transformMessageCoordinationToGPSCoordination(int latitude, int longitude) {
		double latitudeD = new Double(latitude);
		double longitudeD = new Double(longitude);
		return new LatLng(latitudeD / 1800000.00, longitudeD / 1800000.00);
	}

	/*
	 * 将硬件传的GPRMC数据转为GPS
	 */
	public static LatLng transformGPRMCToGPS(String latitude, String longitude) {
		String latPre = latitude.substring(0,2);
		String lngPre = longitude.substring(0,3);
		String latAft = latitude.substring(2,latitude.length());
		String lngAft = longitude.substring(3,longitude.length());
		Double lat = Double.valueOf(latPre)+Double.valueOf(latAft)/60;
		Double lng = Double.valueOf(lngPre)+Double.valueOf(lngAft)/60;
		LatLng latlng = new LatLng(lat,lng);
		return latlng;
	}
	
	/*
	 * 将硬件传的GPRMC数据转为GPS并保留小数位6位
	 */
	public static LatLng dealGPSToMap(String latitude, String longitude){
		LatLng ll = transformGPRMCToGPS(latitude,longitude);
		LatLng la = transformFromWGSToGCJ(ll);
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(6);//小数点取后N位
		Double lat = Double.valueOf(numberFormat.format(la.getLatitude()));
		Double lng = Double.valueOf(numberFormat.format(la.getLongitude()));
		return new LatLng(lat,lng);
	}
	
}
