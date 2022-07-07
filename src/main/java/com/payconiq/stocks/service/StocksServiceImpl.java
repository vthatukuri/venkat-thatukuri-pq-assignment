package com.payconiq.stocks.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.payconiq.error.StockException;
import com.payconiq.stocks.dto.StocksMasterResDTO;
import com.payconiq.stocks.dto.StocksReqDTO;
import com.payconiq.stocks.dto.StocksResDTO;
import com.payconiq.stocks.entity.Stocks;
import com.payconiq.stocks.mapper.StocksMapper;
import com.payconiq.stocks.repository.StocksRepository;

/**
 * Service Implementation layer of stock services.
 */
@Service
public class StocksServiceImpl implements StocksService{
	
	@Autowired
	StocksRepository stocksRepository;
	
	@Autowired
	StocksMapper stocksMapper;
	
	/**
	 * Implementation to retrieve all stock details according to pagination parameters
	 * @param pageNo, pageSize
	 * @return all stock details.
	 */
	@Override
	public StocksMasterResDTO getStocks(int pageSize, int pageNo){
		StocksMasterResDTO response =null;
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Stocks> pageStocks = stocksRepository.findAll(pageable);
		List<Stocks> stocksList= pageStocks.getContent();
		if(!CollectionUtils.isEmpty(stocksList)) {
			response = new StocksMasterResDTO();
			List<StocksResDTO> finalStocks = new ArrayList<>();
			stocksList.stream().forEach(stock->{
				finalStocks.add(stocksMapper.map(stock));
			});
			response.setStocks(finalStocks);
			response.setPageNo(pageStocks.getNumber());
			response.setPageSize(pageStocks.getSize());
			response.setTotalElements(pageStocks.getTotalElements());
			response.setTotalPages(pageStocks.getTotalPages());
			response.setLast(pageStocks.isLast());
		}else {
			throw new StockException("No stock found!",HttpStatus.NO_CONTENT.value());
		}
		return response;
	}
	
	
	/**
	 * Implementation to Create an entry for new Stock
	 * @param StocksReqDTO as request
	 * @return saved Stock entry
	 */
	@Transactional
	@Override
	public StocksResDTO createStock(StocksReqDTO request) {
		Stocks stocksDmn = stocksMapper.prepareStocksDmnFromRequest(request);
		return saveAndReturnDTO(stocksDmn);
	}
	
	/**
	 * Implementation to retrieve a stock details based on StockId
	 * @param pageNo, pageSize
	 * @return stock details.
	 */
	@Override
	public StocksResDTO getStock(Integer stockId) {
		Optional<Stocks> stock = stocksRepository.findById(stockId);
		if (stock.isPresent()) {
			return stocksMapper.map(stock.get());
		}else {
			throw new StockException("No stock found with Id :"+stockId,HttpStatus.NO_CONTENT.value());
		}
	}
	
	/**
	 * Implementation to Update stock details
	 * @param StocksReqDTO: request
	 * @return StocksResDTO: consists updated stock details
	 */
	@Override
	public StocksResDTO updateStock(Integer stockId,StocksReqDTO request) {
		Optional<Stocks> stock = stocksRepository.findById(stockId);
		if(stock.isPresent()) {
			Stocks updatableStock = stock.get();
			updatableStock.setCurrentPrice(request.getCurrentPrice());
			return saveAndReturnDTO(updatableStock);
		}else {
			throw new StockException("No stock found with Id :"+stockId,HttpStatus.NO_CONTENT.value());
		}
	}
	
	/**
	 * Implementation to delete the stock details by stockId
	 * @param StockId
	 */
	@Override
	public void deleteStock(Integer stockId) {
		Optional<Stocks> stock = stocksRepository.findById(stockId);
		if(stock.isPresent()) {
			stocksRepository.deleteById(stockId);
		}else {
			throw new StockException("No stock found with Id :"+stockId,HttpStatus.NO_CONTENT.value());
		}
	}
	
	private StocksResDTO saveAndReturnDTO(Stocks stocksDmn) {
		Stocks savedStock = stocksRepository.save(stocksDmn);
		return stocksMapper.map(savedStock);
	}
	
}
