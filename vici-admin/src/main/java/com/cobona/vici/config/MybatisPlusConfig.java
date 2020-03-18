package com.cobona.vici.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.cobona.vici.common.constant.DSEnum;
import com.cobona.vici.core.datascope.DataScopeInterceptor;
import com.cobona.vici.core.datasource.DruidProperties;
import com.cobona.vici.core.mutidatasource.DynamicDataSource;
import com.cobona.vici.core.mutidatasource.config.MutiDataSourceProperties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * MybatisPlus配置
 *
 * @author cobona
 * @Date 2017/5/20 21:58
 */
@Configuration
@EnableTransactionManagement(order = 2)//由于引入多数据源，所以让spring事务的aop要在多数据源切换aop的后面
@MapperScan(basePackages = {"com.cobona.vici.modular.*.dao", "com.cobona.vici.common.persistence.dao"})
public class MybatisPlusConfig {

    @Autowired
    DruidProperties druidProperties;

    @Autowired
    MutiDataSourceProperties mutiDataSourceProperties;


    /**
     * 另一个数据源
     */
    private DruidDataSource bizDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        mutiDataSourceProperties.config(dataSource);
        return dataSource;
    }

    /**
     * vici的数据源
     */
    private DruidDataSource dataSourceVici(){
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 单数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "vici", name = "muti-datasource-open", havingValue = "false")
    public DruidDataSource singleDatasource() {
        return dataSourceVici();
    }

    /**
     * 多数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "vici", name = "muti-datasource-open", havingValue = "true")
    public DynamicDataSource mutiDataSource() {

        DruidDataSource dataSourceVici = dataSourceVici();
        DruidDataSource bizDataSource = bizDataSource();

        try {
            dataSourceVici.init();
            bizDataSource.init();
        }catch (SQLException sql){
            sql.printStackTrace();
        }

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        HashMap<Object, Object> hashMap = new HashMap();
        hashMap.put(DSEnum.DATA_SOURCE_VICI, dataSourceVici);
        hashMap.put(DSEnum.DATA_SOURCE_BIZ, bizDataSource);
        dynamicDataSource.setTargetDataSources(hashMap);
        dynamicDataSource.setDefaultTargetDataSource(dataSourceVici);
        return dynamicDataSource;
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 数据范围mybatis插件
     */
    @Bean
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }
}
