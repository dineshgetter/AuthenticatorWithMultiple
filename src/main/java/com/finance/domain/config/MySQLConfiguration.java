/**
 * 
 */
package com.finance.domain.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.finance.domain.util.ApplicationConstant;

/**
 * @author Dinesh Singh
 *
 */
@Configuration
@PropertySource({"classpath:mysql-oracle.properties"})
@EnableJpaRepositories(
  basePackages = "com.finance.domain.sqlRepo",
  entityManagerFactoryRef = "sqlEntityManager",
  transactionManagerRef = "sqlTransactionManager")
public class MySQLConfiguration {

	@Autowired
	Environment environment;
	
	@Primary
    public DataSource userDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(ApplicationConstant.SQL_DIVER_CLASS_NAME);
		dataSourceBuilder.url(ApplicationConstant.SQL_DATABASE_URL);
		dataSourceBuilder.username(environment.getProperty(ApplicationConstant.SQL_DATABASE_USERNAME));
		dataSourceBuilder.password(environment.getProperty(ApplicationConstant.SQL_DATABASE_PASSWORD));
        return dataSourceBuilder.build();
    }
	
	@Bean(name= "sqlJDBCTemplate")
	public JdbcTemplate sqlJdbcTemplate() {
		return new JdbcTemplate();
	}
	
	@Bean(name="sqlJDBCTemplate")
	@Primary
	public LocalContainerEntityManagerFactoryBean sqlEntityManager() {

		LocalContainerEntityManagerFactoryBean sql = new LocalContainerEntityManagerFactoryBean();
		sql.setDataSource(userDataSource());
		sql.setPackagesToScan(new String[] {"com.finance.domain.Entity"});
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		sql.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		sql.setJpaPropertyMap(properties);
		return sql;
	}
	
	@Primary
	@Bean
	public PlatformTransactionManager sqlTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(sqlEntityManager().getObject());
		
		return transactionManager;
	}

}
