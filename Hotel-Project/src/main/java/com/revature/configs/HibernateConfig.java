package com.revature.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.revature.models.Staff;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	@Bean
	public LocalSessionFactoryBean getSessionFactory(@Autowired DataSource dataSource) {
		System.out.println("Configuring session factory");
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setAnnotatedClasses(Staff.class);
		factoryBean.setDataSource(dataSource);
		return factoryBean;

	}


	@Bean
	public HibernateTransactionManager getTransactionManager(@Autowired LocalSessionFactoryBean sessionFactory) {
		System.out.println("Configuring Transaction Manager... (This typically takes 30-60 seconds)");
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory.getObject());
		return transactionManager;
	}
}