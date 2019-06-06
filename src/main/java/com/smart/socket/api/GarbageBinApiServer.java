package com.smart.socket.api;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.smart.socket.handler.GarbageBinApiDecoder;
import com.smart.socket.handler.GarbageBinApiEncoder;
import com.smart.socket.handler.GarbageBinApiServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 垃圾桶Api核心类
 * 
 * @author ybc
 * @since 2018-6-1
 */
@Component
public class GarbageBinApiServer implements ApplicationListener<ApplicationReadyEvent> {

	private static Logger log = LoggerFactory.getLogger(GarbageBinApiServer.class);

	@Value("${garbage.socketserver.port}")
	private int garbageport;
	
	@Value("${car.socketserver.port}")
	private int carport;
	
	@Autowired
	private GarbageBinApiServerHandler garbageBinApiServerHandler;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			gbApiRun();
		} catch (Exception e) {
			log.error("GarbageBinApiServer onApplicationEvent ERROR", e);
		}
	}

	public void gbApiRun() throws Exception{
		log.info("Socket server starting ··· ");
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel sc) throws Exception {
							//sc.pipeline().addLast(garbageBinApiDecoder,new GarbageBinApiEncoder(), new IdleStateHandler(2100,0,0,TimeUnit.SECONDS),garbageBinApiServerHandler);
							//sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(new byte[] { 0x7e })));
							sc.pipeline().addLast(new GarbageBinApiDecoder(),new GarbageBinApiEncoder(), new IdleStateHandler(60,0,0,TimeUnit.SECONDS),garbageBinApiServerHandler);
						}
					})
					// 标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
					.option(ChannelOption.SO_BACKLOG, 1024)
					// 是否启用心跳保活机制。在双方TCP套接字建立连接后（即都进入ESTABLISHED状态）并且在两个小时左右上层没有任何数据传输的情况下，这套机制才会被激活。
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			// Bind and start to accept incoming connections.
			ChannelFuture garbagecf = bootstrap.bind(garbageport).sync();
			ChannelFuture carcf = bootstrap.bind(carport).sync();
			garbagecf.channel().closeFuture().sync();
			carcf.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
}
