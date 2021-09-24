package com.example.test.global.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TranscationManagerConfig {

    //https://linked2ev.github.io/gitlog/2019/10/02/springboot-mvc-15-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-MVC-Transaction-%EC%84%A4%EC%A0%95/

    @Bean
    public DataSourceTransactionManager txcofig(DataSource dataSource){
        DataSourceTransactionManager dstm = new DataSourceTransactionManager();
        dstm.setDataSource(dataSource);
        return dstm;

    }
}
