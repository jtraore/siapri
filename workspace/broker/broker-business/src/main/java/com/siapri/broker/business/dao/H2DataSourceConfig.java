package com.siapri.broker.business.dao;

import java.util.Properties;

import javax.sql.DataSource;

import org.h2.Driver;
import org.hibernate.dialect.H2Dialect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Profile("dev")
public class H2DataSourceConfig implements IDataSourceConfig {

	@Override
	public DataSource getDataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(Driver.class.getName());
		dataSource.setUrl("jdbc:h2:~/siapri_db");
		dataSource.setUsername("admin");
		dataSource.setPassword("admin");
		return dataSource;
	}
	
	@Override
	public Properties getServerConnectionProperties() {
		final Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", H2Dialect.class.getName());
		properties.setProperty("hibernate.ejb.resource_scanner", ResourceScanner.class.getName());
		// properties.setProperty("hibernate.hbm2ddl.import_files", "init.sql");
		return properties;
	}
}
