package com.smart.socket.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 高德地图服务
 * @author ybc
 * @since 2018-6-13
 */
@Service
public class AmapHttpServer {
	
	private static Logger log = LoggerFactory.getLogger(AmapHttpServer.class);

	private final static String AMAP_KEY = "30f6422226853d3257207b2c99a70fcc";
	
	private final static String AMAP_POSITION_LAT_LGT = "http://restapi.amap.com/v3/geocode/regeo?key=";
	
	public synchronized String getPositionByLatLgt(String lat,String lgt){
		StringBuffer sb = new StringBuffer();
		sb.append("&location=");
		sb.append(lgt);
		sb.append(",");
		sb.append(lat);
		try{
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(AMAP_POSITION_LAT_LGT+AMAP_KEY+sb.toString());
			CloseableHttpResponse response = httpclient.execute(httpget);
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String retSrc = EntityUtils.toString(entity);
					JSONObject jsonObject = JSON.parseObject(retSrc);
					if("OK".equals(jsonObject.getString("info"))){
						JSONObject positionObj = jsonObject.getJSONObject("regeocode");
						String position = positionObj.getString("formatted_address");
						return position;
					}
				}
			}
		}catch(Exception e){
			log.error("AmapHttpServer getPositionByLatLgt", e);
		}
		return null;
	}
	
}
