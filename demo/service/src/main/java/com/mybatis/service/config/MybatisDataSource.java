package com.mybatis.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration("mybatisDataSourceConfig")
public class MybatisDataSource {

    @Bean(name = "mybatisDataSoutce")
    @ConfigurationProperties(prefix = "mybatis.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

}
