package com.siapri.broker.business.service.exception;

public class BusinessException extends Exception{

	private static final long serialVersionUID = 1L;
	private final ExceptionType type;
	public BusinessException(ExceptionType type, String message, Throwable cause){
		super(message, cause);
		this.type = type;
	}
	
	public BusinessException(ExceptionType type, String message){
		super(message);
		this.type = type;
	}
	
	public BusinessException(ExceptionType type, Throwable cause){
		super(cause);
		this.type = type;
	}

	public ExceptionType getType() {
		return type;
	}
	
	
}
