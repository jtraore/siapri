package com.siapri.broker.business;

public class DataSource {
	private String driver;
	private String dialect;
	private String url;
	private String userName;
	private String password;
	private String schemaStrategy;
	private String showSql;
	
	public final String getDriver() {
		return driver;
	}
	public final void setDriver(String driver) {
		this.driver = driver;
	}
	public final String getDialect() {
		return dialect;
	}
	public final void setDialect(String dialect) {
		this.dialect = dialect;
	}
	public final String getUrl() {
		return url;
	}
	public final void setUrl(String url) {
		this.url = url;
	}
	public final String getUserName() {
		return userName;
	}
	public final void setUserName(String userName) {
		this.userName = userName;
	}
	public final String getPassword() {
		return password;
	}
	public final void setPassword(String password) {
		this.password = password;
	}
	public final String getSchemaStrategy() {
		return schemaStrategy;
	}
	public final void setSchemaStrategy(String schemaStrategy) {
		this.schemaStrategy = schemaStrategy;
	}
	public final String getShowSql() {
		return showSql;
	}
	public final void setShowSql(String showSql) {
		this.showSql = showSql;
	}
	
	
	

}
