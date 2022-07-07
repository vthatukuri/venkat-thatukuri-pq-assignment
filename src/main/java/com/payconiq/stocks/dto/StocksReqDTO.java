package com.payconiq.stocks.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Request payload for Stocks")
public class StocksReqDTO {

	@ApiModelProperty(name="Stock Name", example="Payconiq", required = true)
	@NotNull
	@Size(min=2,max = 100)
	private String name;
	
	@ApiModelProperty(value = "Current Price", example = "100.00", required = true)
	@NotNull
	private BigDecimal currentPrice;
}
