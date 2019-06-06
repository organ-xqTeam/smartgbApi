package com.smart.socket.common;

import java.util.HashSet;
import java.util.Set;

import com.smart.socket.bean.domain.GarbageBinInfo;

public class ConstantForMessage {

	public static final int GARBAGE_MSG_HEAD = 4;

	public static final int GARBAGE_MSG_END = 1;
	
	public static final String GARBAGE_MSG_MARK = "RN";
	
	public static final String GARBAGE_BEAT_MARK = "$$$";
	
	public static final String GARBAGE_MSG_GOOD = "A";
	
	public static final int GARBAGE_TOTAL_MANY = 200;
	
	public static final String GARBAGE_ID = "dtu_id";
	
	public static final String CONNECT_STATE_OFF = "断开";
	
	public static void main(String[] args) {

		/*String ss = "{RN:123456,011,A,4141.8190,N,12325.8120,E,00,s}";

		//String ss = "{RN:123456,023,V,,,,,00,5}";
		ByteBuf bb = Unpooled.copiedBuffer(ss.getBytes());

		byte[] byteArray = new byte[bb.capacity()];
		bb.readBytes(byteArray);
		String result = new String(byteArray);
		String[] beans = result.substring(4,ss.length()-1).split(",");
		for(String str : beans){
			if(0==str.length()){
				System.out.println("为空");
			}else{
				System.out.println(str);
			}
		}
		int garbageMany = ConstantForMessage.GARBAGE_TOTAL_MANY - Integer.valueOf(beans[1]);
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(0);//小数点取后N位
		System.out.println("值：" + String.valueOf(numberFormat.format((float)garbageMany/(float)ConstantForMessage.GARBAGE_TOTAL_MANY*100)));
		*/
		Set<GarbageBinInfo> infoSet = new HashSet<GarbageBinInfo>();
		
		GarbageBinInfo info1 = new GarbageBinInfo();
		info1.setDtu_id("123456");
		info1.setConnect_state("第一个插入");
		GarbageBinInfo info2 = new GarbageBinInfo();
		info2.setDtu_id("123457");
		info2.setConnect_state("第二个插入");
		GarbageBinInfo info3 = new GarbageBinInfo();
		info3.setDtu_id("123458");
		info3.setConnect_state("第三个插入");
		GarbageBinInfo info4 = new GarbageBinInfo();
		info4.setDtu_id("123456");
		info4.setConnect_state("第四个插入");
		infoSet.add(info1);
		infoSet.add(info2);
		infoSet.add(info3);
		infoSet.add(info4);
		for(GarbageBinInfo info : infoSet){
			System.out.println(info.toString());
		}
	}
}
