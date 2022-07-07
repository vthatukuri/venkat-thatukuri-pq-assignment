package com.payconiq.stocks.service;

import org.springframework.stereotype.Service;

import com.payconiq.stocks.dto.StocksMasterResDTO;
import com.payconiq.stocks.dto.StocksReqDTO;
import com.payconiq.stocks.dto.StocksResDTO;

/**
 * Carries complete business logic. Acts as intermediate layer between controller and data access repositories.
 */
@Service
public interface StocksService {
	
	/**
	 * Retrieve a stock details
	 * @param stockId
	 * @return respective stock details.
	 */
	public StocksResDTO getStock(Integer stockId);
	
	/**
	 * Retrieve all stock details according to pagination parameters
	 * @return respective stock details.
	 */
	public StocksMasterResDTO getStocks(int pageSize,int pageNo);
	
	/**
	 * Create an entry for new Stock
	 * @param StocksReqDTO as request
	 * @return saved Stock entry
	 */
	public StocksResDTO createStock(StocksReqDTO request) ;
	
	/**
	 * Update the data of a stock
	 * @param StocksReqDTO: request
	 * @return StocksResDTO: consists updated stock details
	 */
	public StocksResDTO updateStock(Integer stockId,StocksReqDTO request);
	
	/**
	 * Delete the stock
	 * @param stockId
	 * @param request
	 * @return
	 */
	public void deleteStock(Integer stockId);
}
