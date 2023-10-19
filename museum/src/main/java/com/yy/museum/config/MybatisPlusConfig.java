package com.yy.museum.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yao
 * @version 1.0
 */

@Configuration
@MapperScan("com.yy.museum.mapper")
public class MybatisPlusConfig {

//    @Bean
//    public PaginationInterceptor paginationInterceptor(){
//        PaginationInterceptor page = new PaginationInterceptor();
//        return page;
//    }

    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor interceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        mybatisPlusInterceptor.addInnerInterceptor(interceptor);
        return mybatisPlusInterceptor;
    }

}
