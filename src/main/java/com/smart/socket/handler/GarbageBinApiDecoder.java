package com.smart.socket.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart.socket.bean.domain.GarbageBinMsg;
import com.smart.socket.bean.domain.PackageData;
import com.smart.socket.common.ConstantForMessage;
import com.smart.socket.service.GarbageBinLogService;
import com.smart.socket.util.GarbageBinInfoUtil;
import com.smart.socket.util.JT808ProtocolUtils;
import com.smart.socket.util.SpringContextHolder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;

/**
 * 二进制流转换对象
 * 
 * @author ybc
 * @since 2018-6-1
 */
public class GarbageBinApiDecoder extends ByteToMessageDecoder {

	private static Logger log = LoggerFactory.getLogger(GarbageBinApiDecoder.class);
	
	private GarbageBinLogService logService = SpringContextHolder.getBean(GarbageBinLogService.class);
	
	private final MsgDecoder decoder = new MsgDecoder();
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		log.info("报文进入Decoder:\t" + in.toString());
		NioSocketChannel nsc = (NioSocketChannel) ctx.channel();
		// 垃圾桶处理消息体
		if(nsc.localAddress().getPort() == 9091){
			dealGbWithBody(ctx, in, out);
		}else{
			dealJtWithBody(ctx, in, out);
		}
		// 车载设备
		
	}

	private void dealGbWithBody(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		in.markReaderIndex();
		byte[] byteArray = new byte[in.readableBytes()];
		in.readBytes(byteArray);
		String requestStr = new String(byteArray,"UTF-8");
		log.info("gb gets request params :"+requestStr+" length:"+requestStr.length());
		logService.saveLogMsg(requestStr);
		//判断消息是否为智能垃圾桶
		if(-1 != requestStr.indexOf(ConstantForMessage.GARBAGE_MSG_MARK)){
			GarbageBinMsg gbMsg = GarbageBinInfoUtil.getInstance().dealStr2Msg(requestStr);
			if(null != gbMsg){
				ctx.channel().attr(AttributeKey.valueOf(ConstantForMessage.GARBAGE_ID)).set(gbMsg.getDtu_id());
				out.add(gbMsg);
			}
		}
		//判断消息是否为智能垃圾桶的心跳消息
		if(-1 != requestStr.indexOf(ConstantForMessage.GARBAGE_BEAT_MARK)){
			log.info("gb heart beat message , DTU_ID : \t" + requestStr);
			return;
		}
	}

	private void dealJtWithBody(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		JT808ProtocolUtils jt808Utils = new JT808ProtocolUtils();
		in.markReaderIndex();
		byte[] oldByte = new byte[in.readableBytes()];
		byte[] byteArray = jt808Utils.doEscape4Receive(oldByte, 0, oldByte.length);
		in.readBytes(byteArray);
		String requestStr = new String(byteArray,"UTF-8");
		log.info("car gets request params :"+requestStr+" length:"+requestStr.length());
		logService.saveLogMsg(requestStr);
		PackageData pkg = this.decoder.bytes2PackageData(byteArray);
		if(null != pkg){
			out.add(pkg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		log.error(cause.getMessage(), cause);
	}
}
