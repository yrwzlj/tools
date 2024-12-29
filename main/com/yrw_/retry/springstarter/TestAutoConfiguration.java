package com.yrw_.retry.springstarter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

//@Configuration
// 业务类注入
@ConditionalOnClass(BeanTest.class)
// 配置注入
@EnableConfigurationProperties(TestConfigProperties.class)
public class TestAutoConfiguration {

    @Bean
    public BeanTest getBeanTest() {
        System.out.println("制作beanTest");
        return new BeanTest();
    }
}
