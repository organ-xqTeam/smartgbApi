package com.smart.socket.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.*;

public class JPushDriverUtil {

	public static Log LOG = LogFactory.getLog(JPushDriverUtil.class);

	private final static String appKey = "22b0b8222d3566bcaacc96e1";

	private final static String masterSecret = "50324f32e33f5e7fb239a590";

	/**
	 * alert 消息主体 id 订单编号 type消息类型 100位message，101 为状态变更提醒 alias 用户的loginName
	 */
	private static JPushClient jPushClient = new JPushClient(masterSecret, appKey);

	public static void sentMessage(String alert, String id, String type, String alias) {

		PushPayload payload = buildPushObjectAlert(alert, "{\"id\":\"" + id + "\",\"type\":\"" + type + "\"}", alias);
		try {
			PushResult result = jPushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		} catch (APIConnectionException e) {
			// Connection error, should retry later
			LOG.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			LOG.error("Should review the error, and fix the request", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
		}

	}

	public static PushPayload buildPushObjectAlert(String alert, String extrasparam, String alias) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.alias(alias))
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(AndroidNotification.newBuilder().setAlert(alert).setTitle("佳诚约车")
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtra("key", extrasparam).build())
						.addPlatformNotification(IosNotification.newBuilder()
								// 传一个IosAlert对象，指定apns title、title、subtitle等
								.setAlert(alert)
								// 直接传alert
								// 此项是指定此推送的badge自动加1
								.incrBadge(1)
								// 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
								// 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
								.setSound("sound.caf")
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtra("key", extrasparam)
								// 此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
								// .setContentAvailable(true)

								.build())
						.build())
				// Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
				// sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
				// [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
				.setMessage(Message.newBuilder().setMsgContent("消息内容").setTitle("messagetitle")
						.addExtra("message extras key", extrasparam).build())

				.setOptions(Options.newBuilder()
						// 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
						.setApnsProduction(false)
						// 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
						.setSendno(1)
						// 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
						.setTimeToLive(86400).build())
				.build();

	}

	public static void sentMessageAll(String alert, String title, String id, String type, String alias) {

		PushPayload payload = buildPushObjectAlertAll(alert, title, "{\"id\":\"" + id + "\",\"type\":\"" + type + "\"}",
				alias);
		try {
			PushResult result = jPushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		} catch (APIConnectionException e) {
			// Connection error, should retry later
			LOG.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			LOG.error("Should review the error, and fix the request", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
		}

	}

	public static PushPayload buildPushObjectAlertAll(String alert, String title, String extrasparam, String alias) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.all())
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(AndroidNotification.newBuilder().setAlert(alert).setTitle(title)
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtra("key", extrasparam).build())
						.addPlatformNotification(IosNotification.newBuilder()
								// 传一个IosAlert对象，指定apns title、title、subtitle等
								.setAlert(alert)
								// 直接传alert
								// 此项是指定此推送的badge自动加1
								.incrBadge(1)
								// 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
								// 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
								.setSound("sound.caf")
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtra("key", extrasparam)
								// 此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
								// .setContentAvailable(true)

								.build())
						.build())
				// Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
				// sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
				// [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
				.setMessage(Message.newBuilder().setMsgContent("消息内容").setTitle("messagetitle")
						.addExtra("message extras key", extrasparam).build())

				.setOptions(Options.newBuilder()
						// 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
						.setApnsProduction(false)
						// 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
						.setSendno(1)
						// 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
						.setTimeToLive(86400).build())
				.build();

	}
}
