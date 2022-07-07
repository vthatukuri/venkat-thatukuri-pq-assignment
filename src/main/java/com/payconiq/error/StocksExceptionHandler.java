package com.payconiq.error;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class StocksExceptionHandler extends ResponseEntityExceptionHandler{

	/**
	 * To handle module(stock) specific exception
	 * @param stockExcp
	 * @return
	 */
	@ExceptionHandler(StockException.class)
	protected ResponseEntity<Object> handleStockException(StockException stockExcp){
		ErrorResponse errorResponse = new ErrorResponse(getCurrentFormatedDate());
		List<String> msgList = stockExcp.getErrorMsgList();
		if(!CollectionUtils.isEmpty(msgList)) {
			msgList.stream().forEach(msg->{
				ErrorMessage errorMsg = new ErrorMessage();
				errorMsg.setError("Stock Service failed!");
				errorMsg.setMessage(stockExcp.getErrorMsg());
				errorResponse.addErrorMessages(errorMsg);
			});
			return new ResponseEntity<Object>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		ErrorMessage errorMsg = new ErrorMessage();
		errorMsg.setError("Stock Service failed!");
		errorMsg.setMessage(stockExcp.getErrorMsg());
		errorResponse.addErrorMessages(errorMsg);
		return new ResponseEntity<Object>(errorResponse,HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * To handle Generic Exception
	 * @param exception
	 * @return ResponseEntity
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleGenericException(Exception exception){
		ErrorResponse errorResponse = new ErrorResponse(getCurrentFormatedDate());
		ErrorMessage errorMsg = new ErrorMessage();
		errorMsg.setError("Somethng went wrong in Service!");
		errorMsg.setMessage(exception.getLocalizedMessage());
		errorResponse.addErrorMessages(errorMsg);
		return new ResponseEntity<Object>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * To Handle Validation exception like Request validation
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(getCurrentFormatedDate());
		if (!CollectionUtils.isEmpty(ex.getBindingResult().getFieldErrors())) {
			for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
				ErrorMessage errorMessage = new ErrorMessage();
				errorMessage.setError("Validation failed!");
				errorMessage.setCode(fieldError.getCode());
				errorMessage.setMessage(fieldError.getDefaultMessage());
				errorMessage.setField(fieldError.getField());
				errorResponse.addErrorMessages(errorMessage);
			}
		}
		return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
	}


	
	/**
	 * @return current date time in formated way. Can be moved to date util class to be more flexible.
	 */
	private String getCurrentFormatedDate() {
		LocalDateTime currentLocalDateTime = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return currentLocalDateTime.format(dateTimeFormatter);
	}
}
