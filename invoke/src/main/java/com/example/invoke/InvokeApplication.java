package com.example.invoke;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * 排除数据源自动配置
 */

/**
 * @author lee
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@EnableDiscoveryClient
public class InvokeApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(InvokeApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

}
