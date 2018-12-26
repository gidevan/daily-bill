package org.daily.bill.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.PriorityOrdered;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "org.daily.bill")
//@MapperScan("org.daily.bill.dao.mapper")
public class Config {

    @Value("${db.username}")
    protected String dbUserName;
    @Value("${db.password}")
    protected String dbPassword;
    @Value("${db.url}")
    protected String dbUrl;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUsername(dbUserName);
        dataSource.setUrl(dbUrl);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager createTransactionManager(@Qualifier(value = "dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
