package com.mybatis.service.config;

import com.mybatis.service.util.MybatisUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration("mybatisConfig")
@AutoConfigureAfter(MybatisDataSource.class)
public class MybatisConfig {

    @Bean("mybatisA")
    public PlatformTransactionManager transactionManager(@Qualifier("mybatisDataSoutce")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("mybatisSqlSessionFactory")
    @Qualifier("mybatisSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("mybatisDataSoutce")DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath*:/mapper/mybatis/*.xml"));
            sqlSessionFactoryBean.setPlugins(new Interceptor[]{MybatisUtils.resetPageHelper("mysql")});
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException("数据源配置错误");
        }
    }

    @Bean("mybatisSqlTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("mybatisSqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("mybatisMapperScan")
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setSqlSessionFactoryBeanName("mybatisSqlSessionFactory");
        scannerConfigurer.setBasePackage("com.mybatis.**.mapper.mybatis");
        return scannerConfigurer;
    }

}
