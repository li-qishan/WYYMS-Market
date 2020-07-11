package com.buf.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
*Created by Liangzhu on 2018/5/21.
 */
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = {"com.buf.data.dao"}, sqlSessionFactoryRef = MasterDataSourceConfig.NAME + "SqlSessionFactory")
public class MasterDataSourceConfig {

    //xml目录
    private static final String mapperLocation1 = "classpath:com/buf/*/mapper/*.xml";
    private static final String[] mapperLocations = { mapperLocation1};

    //全局名字前缀
    static final String NAME = "master";
    //数据源
    @Bean(name = NAME + "DataSource")
    @ConfigurationProperties("spring.datasource.druid.master")
    @Primary
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
    //事务管理器
    @Bean(name = NAME + "TransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    //工厂
    @Bean(name = NAME + "SqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier(NAME + "DataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(MyUtils.resolveMapperLocations(mapperLocations));
        return sessionFactory.getObject();
    }
}
