/**
 * 
 */
package com.finance.domain.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

///product
@Configuration
@PropertySource({"classpath:mysql-oracle.properties"})
@EnableJpaRepositories(
  basePackages = "com.finance.domain.oracleRepo", 
  entityManagerFactoryRef = "oracleEntityManager", 
  transactionManagerRef = "oracleTransactionManager")
public class OracleConfiguration {
   
	@Autowired
	Environment environment;
	
	@Primary
    public DataSource userDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(ApplicationConstant.ORACLE_DIVER_CLASS_NAME);
		dataSourceBuilder.url(ApplicationConstant.ORACLE_DATABASE_URL);
		dataSourceBuilder.username(environment.getProperty(ApplicationConstant.ORACLE_DATABASE_USERNAME));
		dataSourceBuilder.password(environment.getProperty(ApplicationConstant.ORACLE_DATABASE_PASSWORD));
        return dataSourceBuilder.build();
    }
	
	@Bean(name= "oracleJDBCTemplate")
	public JdbcTemplate oracleJdbcTemplate() {
		return new JdbcTemplate();
	}
	
	@Bean(name="oracleJDBCTemplate")
	@Primary
	public LocalContainerEntityManagerFactoryBean oracleEntityManager() {

		LocalContainerEntityManagerFactoryBean oracle = new LocalContainerEntityManagerFactoryBean();
		oracle.setDataSource(userDataSource());
		oracle.setPackagesToScan(new String[] {"com.finance.domain.Entity"});
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		oracle.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		oracle.setJpaPropertyMap(properties);
		return oracle;
	}
	
	@Primary
	@Bean
	public PlatformTransactionManager oracleTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(oracleEntityManager().getObject());
		
		return transactionManager;
	}
}
