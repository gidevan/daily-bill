package org.daily.bill.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

/**
 * Created by vano on 18.8.16.
 * implementation of BeanDefinitionRegistryPostProcessor, PriorityOrdered is necessary to config mappers.
 * See https://jira.spring.io/browse/SPR-7868
 * https://stackoverflow.com/questions/8999597/mybatis-spring-configuration-cant-autowire-mapper-beans
 */
@Configuration
@ComponentScan(basePackages = "org.daily.bill")
public class TestDataConfig implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {
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
        //dataSource.setUsername(dbUserName);
        //dataSource.setUrl(dbUrl);
        //dataSource.setPassword(dbPassword);
        //TODO: add properties
        dataSource.setUsername("postgres");
        dataSource.setUrl("db.url=jdbc:postgresql://localhost:5432/daily-bill-test");
        dataSource.setPassword("postgres");
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager createTransactionManager(@Qualifier(value = "dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name="sessionFactory")
    public SqlSessionFactory createSessionFactory(@Qualifier(value = "dataSource") DataSource dataSource) throws Exception{
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage("org.daily.bill.domain");
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return sessionFactory.getObject();
    }

    @Bean(name = "sqlSession")
    public SqlSession createSqlSession(@Qualifier(value = "sessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
