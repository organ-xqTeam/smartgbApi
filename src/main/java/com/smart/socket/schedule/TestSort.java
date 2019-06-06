package com.smart.socket.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.smart.socket.bean.domain.CarOilInfo;

public abstract class TestSort {

	public static void main(String[] args) throws Exception {
		
		String s1 = "50.67";
		String s2 = "23.66";
		
		Double d1 = Double.valueOf(s1);
		Double d2 = Double.valueOf(s2);
		
		if(d1 > d2) {
			System.out.println(d1+d2);
		}
		
		List<CarOilInfo> list = new ArrayList<CarOilInfo>();
		CarOilInfo info1 = new CarOilInfo();
		Date now1 = new Date();
		info1.setCar_id("1");
		info1.setCar_time(now1);
		info1.setCreate_time(now1);
		Thread.sleep(50000);
		CarOilInfo info2 = new CarOilInfo();
		Date now2 = new Date();
		info2.setCar_id("2");
		info2.setCar_time(now2);
		info2.setCreate_time(now2);
		Thread.sleep(50000);
		CarOilInfo info3 = new CarOilInfo();
		Date now3 = new Date();
		info3.setCar_id("3");
		info3.setCar_time(now3);
		info3.setCreate_time(now3);
		list.add(info3);
		list.add(info1);
		list.add(info2);
		list.sort((h1, h2) -> h1.getCreate_time().compareTo(h2.getCreate_time()));
		for(CarOilInfo oil : list) {
			System.out.println(oil.toString());
		}
	}

}
