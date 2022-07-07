package com.payconiq.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Response object which hold the errors
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ErrorResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	List<ErrorMessage> errorMsgList;
	String timestamp;
	
	public ErrorResponse(){
		super();
	}
	
	public ErrorResponse(String timestamp){
		super();
		this.timestamp = timestamp;
	}
	
	public void addErrorMessages(ErrorMessage msg) {
		if(CollectionUtils.isEmpty(errorMsgList)) {
			errorMsgList = new ArrayList<ErrorMessage>();
		}
		this.errorMsgList.add(msg);
	}
}
