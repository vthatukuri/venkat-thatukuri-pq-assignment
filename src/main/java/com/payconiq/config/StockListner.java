package com.payconiq.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.payconiq.stocks.dto.StocksReqDTO;
import com.payconiq.stocks.entity.Stocks;
import com.payconiq.stocks.mapper.StocksMapper;
import com.payconiq.stocks.repository.StocksRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * StartUp listener to make stock entries at initial stage
 */
@Slf4j
@Component
public class StockListner implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	StocksRepository stocksRepository;
	
	@Autowired
	StocksMapper stocksMapper;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("Stock record insertion started!");
		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream= Stocks.class.getResourceAsStream("/intial-stocks.json");
			CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, StocksReqDTO.class);
			List<StocksReqDTO> stocksReqDtos = mapper.readValue(inputStream, collectionType);
			List<Stocks> saveStocks = stocksReqDtos.stream().map(stockRequest -> stocksMapper.prepareStocksDmnFromRequest(stockRequest)).collect(Collectors.toList());
			stocksRepository.saveAll(saveStocks);
		} catch (FileNotFoundException fileNotFoundExcep) {
			log.error("File Not found! ",fileNotFoundExcep);
		}catch(Exception excep) {
			log.error("Exception occured while inserting intial scripts : {}",excep);
		}
		log.info("Stock record inserted successfully!");
	}

}
