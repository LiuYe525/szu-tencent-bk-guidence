package com.szuse.feign.client.config;

import com.szuse.common.constant.CommonConstant;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.loadbalancer.blocking.client.BlockingLoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 *  重写BlockingLoadBalancerClient的reconstructURI方法适配蓝鲸
 * @author Whitence
 * @date 2023/5/8 19:01
 * @version 1.0
 */
@Component
public class BlockingLoadBalancerClient2 extends BlockingLoadBalancerClient {

    public BlockingLoadBalancerClient2(ReactiveLoadBalancer.Factory<ServiceInstance> loadBalancerClientFactory) {
        super(loadBalancerClientFactory);
    }

    @Override
    public URI reconstructURI(ServiceInstance serviceInstance, URI original) {
        if (serviceInstance == null) {
            throw new IllegalArgumentException("Service Instance cannot be null.");
        }
        // 获取重定向url
        String accessUrl = serviceInstance.getMetadata().get(CommonConstant.BK_ACCESS_URL);
        // 若不为空，则使用accessUrl替换原先的
        if(StringUtils.hasText(accessUrl)){
            return UriComponentsBuilder
                    .fromUriString(accessUrl+original.getPath())
                    .query(original.getQuery())
                    .build()
                    .toUri();
        }
        return super.reconstructURI(serviceInstance, original);
    }
}

