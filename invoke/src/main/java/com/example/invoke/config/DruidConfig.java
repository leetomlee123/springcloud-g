package com.example.invoke.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //ip allow
//        servletRegistrationBean.addInitParameter("allow", "192.168.10.131,127.0.0.1");
        //ip deny deny >allow
//        servletRegistrationBean.addInitParameter("deny", "192.168.0.1");
        servletRegistrationBean.addInitParameter("loginUsername", "druid");
        servletRegistrationBean.addInitParameter("loginPassword", "1234");
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;

    }

    @Bean
    public FilterRegistrationBean statFileter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //add filter rule
        filterRegistrationBean.addUrlPatterns("/*");
        //ignore style
        filterRegistrationBean.addInitParameter("exclusions", "*.jpg,*.js,*.css,*.ico,*.gif,*.png,/druid/*");
        return filterRegistrationBean;
    }
}
