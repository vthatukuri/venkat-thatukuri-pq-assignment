package com.payconiq.stocks.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.payconiq.stocks.dto.StocksReqDTO;
import com.payconiq.stocks.dto.StocksResDTO;
import com.payconiq.stocks.entity.Stocks;

/**
 * Mapper class to convert DO to DTO's and vice-versa
 *
 */
@Mapper(imports = {LocalDateTime.class},componentModel = "spring")
public interface StocksMapper {

	@Mappings({
		  @Mapping(target = "id", ignore = true),
		  @Mapping(target="name", source = "request.name"),
		  @Mapping(target="currentPrice", source = "request.currentPrice"),
		  @Mapping(target="lstUpdatedDtm", expression = "java(LocalDateTime.now())", dateFormat="MM-dd-yyyy HH:mm:ss"),
		  @Mapping(target="activeCd", constant = "Y"),
		  @Mapping(target="createdDtm", expression = "java(LocalDateTime.now())", dateFormat="MM-dd-yyyy HH:mm:ss")
	})
	Stocks prepareStocksDmnFromRequest(StocksReqDTO request);
	
	StocksResDTO map(Stocks stocks);
}
