package com.smart.socket.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 信息转换为二进制流
 * 
 * @author ybc
 * @since 2018-6-1
 */
public class GarbageBinApiEncoder extends MessageToByteEncoder<Object> {

	private static Logger log = LoggerFactory.getLogger(GarbageBinApiEncoder.class);
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		byte[] body = msg.toString().getBytes(); // 将对象转换为byte
		log.info("server do encoder : " + msg.toString());
		out.writeBytes(body); // 消息体中包含我们要发送的数据
	}

}
