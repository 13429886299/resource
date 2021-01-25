package com.zmlh.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName Config
 * @Description TODO
 * @Author tyh
 * @Date 2020-12-04 16:40
 * @Version 1.0
 **/
@Configuration
public class Config {
    @Bean
    public PaginationInterceptor paginationInterceptor () {
        return new PaginationInterceptor();
    }
}
