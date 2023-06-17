package com.szuse.gateway.filter;

import com.szuse.common.constant.CommonConstant;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 *  重写ReactiveLoadBalancerClientFilter的reconstructURI适配蓝鲸
 * @author Whitence
 * @date 2023/6/6 10:21
 * @version 1.0
 */
@Component
public class ReactiveLoadBalancerClientFilter2 extends ReactiveLoadBalancerClientFilter {

    public ReactiveLoadBalancerClientFilter2(LoadBalancerClientFactory clientFactory, GatewayLoadBalancerProperties properties) {
        super(clientFactory, properties);
    }

    @Override
    protected URI reconstructURI(ServiceInstance serviceInstance, URI original) {

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
