package com.jdbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class jdbcConfiguration {

    // 1. 数据源对象，带有配置参数
    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/studb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSource.setUsername("studbuser");
        dataSource.setPassword("123test123");

        return dataSource;
    }

    // 2. 带有命名参数的 jdbcTemplate 对象，能够进行带有命名参数的查询
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate()
    {
        NamedParameterJdbcTemplate retBean = new NamedParameterJdbcTemplate(dataSource());
        return retBean;
    }

    // 3. 事物管理器类
    @Bean
    public DataSourceTransactionManager txnManager()
    {
        DataSourceTransactionManager txnManager = new DataSourceTransactionManager(dataSource());
        return txnManager;
    }
}
