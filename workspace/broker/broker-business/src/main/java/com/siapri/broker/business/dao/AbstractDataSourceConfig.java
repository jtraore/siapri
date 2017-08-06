package com.siapri.broker.business.dao;

import org.springframework.security.crypto.codec.Base64;

public abstract class AbstractDataSourceConfig implements IDataSourceConfig {
	
	protected static String encode(String token) {
		byte[] encodedBytes = Base64.encode(token.getBytes());
		return new String(encodedBytes);
	}

	protected static String decode(String token) {
		byte[] decodedBytes = Base64.decode(token.getBytes());
		return new String(decodedBytes);
	}
}
