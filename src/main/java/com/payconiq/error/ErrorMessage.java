package com.payconiq.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Message class which holds the error messages
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ErrorMessage {
	private String error;
	private String message;
	private String code;
	private String field;
}
