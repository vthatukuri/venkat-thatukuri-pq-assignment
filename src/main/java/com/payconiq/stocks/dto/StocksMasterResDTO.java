package com.payconiq.stocks.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Parent response which consist list of stocks and pagination details")
@JsonInclude(Include.NON_NULL)
public class StocksMasterResDTO {
	
	@ApiModelProperty(name="stocks", value="stocks list")
	List<StocksResDTO> stocks;
	
	@ApiModelProperty(name ="pageNumber", value="Current Page Number")
	private int pageNo;
	
	@ApiModelProperty(name="pageSize", value="Page size of current page number")
    private int pageSize;
	
	@ApiModelProperty(value="Total elements")
    private long totalElements;
	
	@ApiModelProperty(name="totalPages", value="Total pages for result")
    private int totalPages;
	
	@ApiModelProperty(name ="last", value="Is this last page")
    private boolean last;
}
