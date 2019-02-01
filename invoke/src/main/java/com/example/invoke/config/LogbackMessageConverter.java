package com.example.invoke.config;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.leetomlee.cloud.common.util.DateUtil;

import java.util.Date;

/**
 * @Author: lx
 * @Date: 2019/2/1 15:21
 */
public class LogbackMessageConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
//        DateUtil.getFormatDateTime(new Date())+
//                "|"+uri.getHost()+
//                "|"+exchange.getRequest().getRemoteAddress().getHostString()+"|"+uri.toString()+"|"+uri.getPath()+"|"+exchange.getRequest().getHeaders().get("User-Agent").get(0)
//

        return iLoggingEvent.getFormattedMessage();
    }
}
