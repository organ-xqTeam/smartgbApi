package com.smart.socket.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.socket.bean.domain.GarbageBinMsg;
import com.smart.socket.bean.domain.LocationModel;
import com.smart.socket.bean.domain.PackageData;
import com.smart.socket.bean.domain.PackageData.MsgHeader;
import com.smart.socket.bean.domain.TerminalAuthenticationMsg;
import com.smart.socket.bean.domain.TerminalRegisterMsg;
import com.smart.socket.common.ConstantForMessage;
import com.smart.socket.common.TPMSConsts;
import com.smart.socket.service.GarbageBinInfoService;
import com.smart.socket.service.GarbageBinMsgService;
import com.smart.socket.service.TerminalMsgProcessService;
import com.smart.socket.thread.CarOilTask;
import com.smart.socket.thread.InitGbIntoTask;
import com.smart.socket.thread.UpdateGbInfoTask;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;

/**
 * 处理请求数据
 * 
 * @author ybc
 * @since 2018-6-1
 */
@Service
@Sharable
public class GarbageBinApiServerHandler extends ChannelHandlerAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(GarbageBinApiServerHandler.class);
	
	@Autowired
	private GarbageBinMsgService gbMsgService;
	
	@Autowired
	private InitGbIntoTask initGbIntoTask;
	
	@Autowired
	private UpdateGbInfoTask updateGbInfoTask;
	
	@Autowired
	private GarbageBinInfoService infoService;
	
	@Autowired
	private CarOilTask carOilTask;
	
	@Autowired
	private TerminalMsgProcessService msgProcessService;
	
	private final MsgDecoder decoder = new MsgDecoder();
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info("GarbageBinApiServerHandler channelRead :" + msg.toString() + "\t classname \t" +this);
		if(null!=msg && msg instanceof GarbageBinMsg){
			GarbageBinMsg gbMsg = (GarbageBinMsg)msg;
			int dealState = 0 ;
			//if("A".equalsIgnoreCase(gbMsg.getPosition_state())&&!"".equals(gbMsg.getDtu_id())){
			if(!"".equals(gbMsg.getDtu_id())){
				//垃圾箱注册
				initGbIntoTask.initGbInfo(gbMsg);
				//垃圾箱更新状态
				updateGbInfoTask.updateGbInfo(gbMsg);
			}
			gbMsg.setDeal_state(dealState);
			gbMsgService.saveGbMsg(gbMsg);
			logger.info("GarbageBinMsg channelRead save suceess " + msg.toString());
			ctx.writeAndFlush("ok");
		}
		
		if(null!=msg && msg instanceof PackageData){
			PackageData pd = (PackageData)msg;
			logger.info("PackageData channelRead save suceess " + pd.toString());
			this.processPackageData(pd);
		}
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx){
		String dtuId = (String)ctx.channel().attr(AttributeKey.valueOf(ConstantForMessage.GARBAGE_ID)).get();
		//更新数据库中的连接状态
		infoService.updateGbInfoConnectState(dtuId,ConstantForMessage.CONNECT_STATE_OFF);
		logger.info("断开连接！！！！garbageId : " + dtuId);
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		logger.info("userEventTriggered client " + ctx.channel().remoteAddress());
		if (evt instanceof IdleStateEvent){
			IdleStateEvent event = (IdleStateEvent)evt;
			if (event.state() == IdleState.READER_IDLE){
				String dtuId = (String)ctx.channel().attr(AttributeKey.valueOf(ConstantForMessage.GARBAGE_ID)).get();
				logger.info("服务端关闭这个不活跃通道！ garbageId : " + dtuId);
				ctx.channel().close().sync();
			}
		}else {
			super.userEventTriggered(ctx,evt);
		}
	}
	
	/**
	 * 
	 * 车载设备处理业务逻辑
	 * 
	 */
	private void processPackageData(PackageData packageData) {
		final MsgHeader header = packageData.getMsgHeader();
		System.out.println("接受数据:{}"+header.getMsgId());
		// 1. 终端心跳-消息体为空 ==> 平台通用应答
		if (TPMSConsts.msg_id_terminal_heart_beat == header.getMsgId()) {
			logger.info(">>>>>[终端心跳],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			try {
				this.msgProcessService.processTerminalHeartBeatMsg(packageData);
				logger.info("<<<<<[终端心跳],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			} catch (Exception e) {
				logger.error("<<<<<[终端心跳]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),e.getMessage());
			}
		}else if (TPMSConsts.msg_id_terminal_location_info_upload == header.getMsgId()) {//上传位置信息
			logger.info(">>>>>[位置信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			LocationModel msg = this.decoder.toTerminalLocMsg(packageData);
			logger.info(">>>>>[位置信息],phone={},flowid={},LocationModel={}", header.getTerminalPhone(), header.getFlowId(),msg.toString());
			carOilTask.initGbInfo(header.getTerminalPhone(), msg);
			try {
				this.msgProcessService.processTerminalHeartBeatMsg(packageData);
			} catch (Exception e) {
				logger.error("<<<<<[位置信息]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),e.getMessage());
				e.printStackTrace();
			}
		}
		// 5. 终端鉴权 ==> 平台通用应答
		else if (TPMSConsts.msg_id_terminal_authentication == header.getMsgId()) {
			logger.info(">>>>>[终端鉴权],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			try {
				TerminalAuthenticationMsg authenticationMsg = new TerminalAuthenticationMsg(packageData);
				this.msgProcessService.processAuthMsg(authenticationMsg);
				logger.info("<<<<<[终端鉴权],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			} catch (Exception e) {
				logger.error("<<<<<[终端鉴权]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),e.getMessage());
				e.printStackTrace();
			}
		}
		// 6. 终端注册 ==> 终端注册应答
		else if (TPMSConsts.msg_id_terminal_register == header.getMsgId()) {
			logger.info(">>>>>[终端注册],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			try {
				TerminalRegisterMsg msg = this.decoder.toTerminalRegisterMsg(packageData);
				this.msgProcessService.processRegisterMsg(msg);
				logger.info("<<<<<[终端注册],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			} catch (Exception e) {
				logger.error("<<<<<[终端注册]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),e.getMessage());
				e.printStackTrace();
			}
		}
		// 7. 终端注销(终端注销数据消息体为空) ==> 平台通用应答
		else if (TPMSConsts.msg_id_terminal_log_out == header.getMsgId()) {
			logger.info(">>>>>[终端注销],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			try {
				this.msgProcessService.processTerminalLogoutMsg(packageData);
				logger.info("<<<<<[终端注销],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			} catch (Exception e) {
				logger.error("<<<<<[终端注销]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),e.getMessage());
				e.printStackTrace();
			}
		}
		// 其他情况
		else {
			logger.error(">>>>>>[未知消息类型],phone={},msgId={},package={}", header.getTerminalPhone(), header.getMsgId(),packageData);
		}
	}
}
