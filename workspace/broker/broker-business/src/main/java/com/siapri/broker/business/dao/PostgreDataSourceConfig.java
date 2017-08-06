package com.siapri.broker.business.dao;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.siapri.broker.business.Config;

@Configuration
@Profile("prod")
public class PostgreDataSourceConfig extends AbstractDataSourceConfig{
	
	@Autowired
	private Config config;
	
	@Override
	public DataSource getDataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(config.getDataSource().getDriver());
		dataSource.setUrl(config.getDataSource().getUrl());
		dataSource.setUsername(config.getDataSource().getUserName());
		dataSource.setPassword(config.getDataSource().getPassword());
		return dataSource;
	}

	@Override
	public Properties getServerConnectionProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", config.getDataSource().getSchemaStrategy());
		properties.setProperty("hibernate.dialect", config.getDataSource().getDialect());
		properties.setProperty("hibernate.show_sql", config.getDataSource().getShowSql());
		return properties;
	}

}
