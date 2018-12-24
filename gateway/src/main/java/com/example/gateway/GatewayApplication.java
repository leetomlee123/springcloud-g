package com.example.gateway;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author lee
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@Slf4j
public class GatewayApplication {
    @Autowired
    private LoadBalancerClient loadBalancer;
    @Autowired
    private DiscoveryClient discoveryClient;


    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        log.info(" Start APIGatewayApplication Done");

    }


    @GetMapping("/fallback")
    public ResponseEntity fallback() {
        return ResponseEntity.ok(ResponseEntity.status(404).body("服务中断"));
    }

    /**
     * 获取所有服务
     */
    @RequestMapping("/services/{serviceId}")
    public Object services(@PathVariable(value = "serviceId") String serviceId) {

        return discoveryClient.getInstances(serviceId);
    }


    /**
     * 从所有服务中选择一个服务（轮询）
     */
    @RequestMapping("/call")
    public ResponseEntity discover() {
        ServiceInstance choose = loadBalancer.choose("service-user");
        System.out.println("服务地址：" + choose.getUri());
        System.out.println("服务名称：" + choose.getServiceId());
        String url = choose.getUri().toString() + "/users/auth";
        HttpHeaders headers = new HttpHeaders();
//  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("username", Collections.singletonList("lx"));
        params.put("password", Collections.singletonList("123456789"));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        return new RestTemplate().postForEntity(url, requestEntity, String.class);
    }

    @RequestMapping("/miui")
    public ResponseEntity miui() {
        ServiceInstance choose = loadBalancer.choose("service-invoke");
        System.out.println("服务地址：" + choose.getUri());
        System.out.println("服务名称：" + choose.getServiceId());
        String url = choose.getUri().toString() + "/miuis/100/100";
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity discover = discover();
        Object parse = JSON.parse(discover.getBody().toString());
        String token = ((JSONObject) parse).get("token").toString();
        RestTemplate restTemplate = new RestTemplate();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        return ResponseEntity.ok(restTemplate.exchange(url, HttpMethod.GET, request, String.class));
    }

    @RequestMapping("/error")
    public List<String> error() {
        return Arrays.asList("sorry, something went wrong.");
    }

}
