package com.example.invoke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
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

        SpringApplication.run(InvokeApplication.class);
    }

}
