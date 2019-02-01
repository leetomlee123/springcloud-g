package com.example.invoke.config;

import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class DbLoggerAppender extends UnsynchronizedAppenderBase<LoggingEvent> {

	@Override
	protected void append(LoggingEvent le) {

		try {
			/*
			这个和配置是一一对应的,贴上logback.xml的代码
			<!--读取application.properties中的属性  一会代码用的到
             test.logUrl=http://localhost:8098/-->
    		<springProperty scope="context" name="logUrl" source="test.logUrl"/>*/
			String logUrl = context.getProperty("logUrl") + "saveprogramlog";

			String content = le.getFormattedMessage();
			//异常内容,就是log.error("出错", e);的内容
			//如果没有就是以下内容了,例如(贴多点,方便大家理解)
			//{dataSource-11} init error
			//uriSessionMapFullCount is full
			//session ip change too many
			//Application run failed
			/*
			***************************
			APPLICATION FAILED TO START
			***************************
			Description:
			Web server failed to start. Port 8088 was already in use.
			Action:
			Identify and stop the process that's listening on port 8088 or configure this application to listen on another port.
			*/

			Map<String, Object> log = new HashMap<String, Object>();
			log.put("timestamp", le.getTimeStamp());
			log.put("level", le.getLevel().levelStr);
			log.put("content", content);
			log.put("serviceName", "WEB");//给这个服务起个别名 Spring boot可是多服务的...
			log.put("serviceIP", InetAddress.getLocalHost().getHostAddress());//主机IP

			StringBuilder builder = new StringBuilder();
			//这个builder 得到的内容就是密密麻麻的报错信息  有缩进和换行的那种
			//具体实现我也不懂..希望有大拿评论贴链接或者剖析..
			IThrowableProxy thProxy = le.getThrowableProxy();
			while (thProxy != null) {
				builder.append(thProxy.getClassName() + ": " + thProxy.getMessage());
				builder.append(CoreConstants.LINE_SEPARATOR);

				for (StackTraceElementProxy step : le.getThrowableProxy().getStackTraceElementProxyArray()) {
					String string = step.toString();
					builder.append(CoreConstants.TAB).append(string);
					ThrowableProxyUtil.subjoinPackagingData(builder, step);
					builder.append(CoreConstants.LINE_SEPARATOR);
				}

				thProxy = thProxy.getCause();
			}

			log.put("cause", builder.toString());
			//json序列化
			ObjectMapper om = new ObjectMapper();
			String logJson = om.writeValueAsString(log);

			HttpHeaders headers = new HttpHeaders();
			// 提交方式，大部分的情况下，提交方式都是表单提交
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("log", logJson);

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForEntity(logUrl, request, String.class);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


}