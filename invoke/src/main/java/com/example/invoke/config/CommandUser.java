package com.example.invoke.config;

import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Function:用户服务
 *
 * @author crossoverJie
 * Date: 2018/7/28 16:43
 * @since JDK 1.8
 */
public class CommandUser extends HystrixCommand<String> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommandUser.class);

    private String userName;

    public CommandUser(String userName) {


        super(Setter.withGroupKey(
                //服务分组
                HystrixCommandGroupKey.Factory.asKey("UserGroup"))
                //线程分组
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("UserPool"))

                //线程池配置
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)
                        .withKeepAliveTimeMinutes(5)
                        .withMaxQueueSize(10)
                        .withQueueSizeRejectionThreshold(10000))

                //线程池隔离
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD))
        )
        ;
        this.userName = userName;
    }


    @Override
    public String run() {

        LOGGER.info("userName=[{}]", userName);

        return "userName=" + userName;
    }

    public static void main(String[] args) {
        com.example.invoke.config.CommandUser commandUser = new CommandUser("lee");
        commandUser.execute();
    }

}