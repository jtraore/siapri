package com.siapri.broker.business.dao;

import java.util.Properties;

import javax.sql.DataSource;

public interface IDataSourceConfig {
	
	DataSource getDataSource();
	
	Properties getServerConnectionProperties();
}
