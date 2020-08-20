package com.example.multipledatabaseconnect.config.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.example.multipledatabaseconnect")
@PropertySource("classpath:database.properties")
@EnableJpaRepositories(
        basePackages = "com.example.multipledatabaseconnect.repository.dbo",
        entityManagerFactoryRef = "dboEntityManagerFactory",
        transactionManagerRef = "dboTransactionManager")
public class DBOConfiguration {

    @Resource
    private Environment env;

    @Bean(name = "dboDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(env.getProperty("dbo.datasource.url"))
                .driverClassName(env.getProperty("dbo.datasource.driverClassName"))
                .username(env.getProperty("dbo.datasource.username"))
                .password(env.getProperty("dbo.datasource.password"))
                .build();
    }

    @Bean(name = "dboEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dboDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.example.multipledatabaseconnect.entity.dbo");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", env.getRequiredProperty("spring.jpa.database-platform"));
        jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("spring.jpa.show-sql"));

        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }

    @Bean(name = "dboTransactionManager")
    public PlatformTransactionManager transactionManager (@Qualifier("dboEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
