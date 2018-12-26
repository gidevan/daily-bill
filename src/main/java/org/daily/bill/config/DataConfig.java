package org.daily.bill.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/**
 * Created by vano on 4.8.16.
 */
@Configuration
public class DataConfig implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {



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
