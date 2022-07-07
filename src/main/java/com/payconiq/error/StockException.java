package com.payconiq.error;

import java.util.List;

import lombok.Data;

/**
 * Application based specific exception
 *
 */
@Data
public class StockException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorMsg;
	private Integer errorCode;
	private List<String> errorMsgList;
	
	public StockException(String errorMsg,Integer errorCode) {
		super();
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
	}
	
	public StockException(List<String> errorMsgList) {
		super();
		this.errorMsgList = errorMsgList;
	}
}
