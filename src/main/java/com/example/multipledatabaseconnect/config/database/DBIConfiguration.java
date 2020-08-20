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
        basePackages = "com.example.multipledatabaseconnect.repository.dbi",
        entityManagerFactoryRef = "dbiEntityManagerFactory",
        transactionManagerRef = "dbiTransactionManager")
public class DBIConfiguration {

    @Resource
    private Environment env;

    @Bean(name = "dbiDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(env.getProperty("dbi.datasource.url"))
                .driverClassName(env.getProperty("dbi.datasource.driverClassName"))
                .username(env.getProperty("dbi.datasource.username"))
                .password(env.getProperty("dbi.datasource.password"))
                .build();
    }

    @Bean(name = "dbiEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dbiDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.example.multipledatabaseconnect.entity.dbi");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", env.getRequiredProperty("spring.jpa.database-platform"));
        jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("spring.jpa.show-sql"));

        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }

    @Bean(name = "dbiTransactionManager")
    public PlatformTransactionManager transactionManager (@Qualifier("dbiEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
