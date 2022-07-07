package com.payconiq.stocks.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.payconiq.config.CurrencySerializer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Request payload for Stocks")
@JsonInclude(Include.NON_NULL)
public class StocksResDTO {
	
	@ApiModelProperty(name="Stock Id", example="1")
	@JsonProperty(value = "StockId")
	private Integer id;
	
	@ApiModelProperty(name="Stock Name", example="Payconiq")
	@JsonProperty(value = "StockName")
	private String name;
	
	@JsonSerialize(using = CurrencySerializer.class)
	@ApiModelProperty(value = "Current Price", example = "100.00")
	private BigDecimal currentPrice;
	
	@ApiModelProperty(value = "Share Purchased Date", example = "2020-06-11")
	@JsonFormat(pattern="MM-dd-yyyy HH:mm:ss")
	private LocalDateTime lstUpdatedDtm;
	
	private String activeCd;
}
