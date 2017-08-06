package com.siapri.broker.business;

public class Config {

	private DataSource dataSource;
	
	public DataSource getDataSource(){
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
}
