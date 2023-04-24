package com.szuse.szubkguidance.constants;

/**
 * @author LiuYe
 * @description 消息队列topic,切勿随意修改
 * @version 1.0
 * @date 28/3/2023 下午8:37
 */
public final class MqConstant {
    private MqConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String EXCHANGE_NAME = "bk.test";

    public static final String QUEUE_NAME = "bk.test.queue";
}
