package com.payconiq.stocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import com.payconiq.StocksApplication;
import com.payconiq.stocks.dto.StocksMasterResDTO;
import com.payconiq.stocks.dto.StocksReqDTO;
import com.payconiq.stocks.dto.StocksResDTO;

/**
 * Stocks API integration test cases
 */
@SpringBootTest(classes = StocksApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class StocksApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private final String BASEHOSTURL = "http://localhost:";
	
	@BeforeTestExecution
	public void setup() {
	    restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
	}
	
	@Disabled("Enable to test only getallstocks service. Else covered in GetStock case")
	@Test
	public void testAllStocks() {
		assertTrue(getStockResults().size() > 1);
	}

	@Test
	public void testAddStock() {
		StocksReqDTO stock = new StocksReqDTO("TestStock1", new BigDecimal(8000.90));
		ResponseEntity<StocksResDTO> responseEntity = this.restTemplate
				.postForEntity(BASEHOSTURL + port + "/api/stocks", stock, StocksResDTO.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
	}
	
	@Test
	public void testStock() {
		List<StocksResDTO> stockList = getStockResults();
		assertNotNull(stockList);
		assertTrue(stockList.size()>1);
		StocksResDTO stockRes = stockList.get(0);
		StocksResDTO response = this.restTemplate.getForObject(BASEHOSTURL + port + "/api/stocks/"+stockRes.getId(), StocksResDTO.class);
		assertNotNull(response);
		assertNotNull(response.getId());
	}

	@Test
	public void testUpdateStock() {
		List<StocksResDTO> stockList = getStockResults();
		assertNotNull(stockList);
		assertTrue(stockList.size()>1);
		StocksResDTO stockRes = stockList.get(0);
		
		StocksReqDTO stock = new StocksReqDTO();
		stock.setCurrentPrice(new BigDecimal(111.00));
		StocksResDTO response = this.restTemplate.patchForObject(BASEHOSTURL + port + "/api/stocks/{id}",stock, StocksResDTO.class,stockRes.getId());
		assertNotNull(response);
		assertTrue(response.getCurrentPrice().compareTo(new BigDecimal(111.0)) == 0 );
	}
	
	@Test
	public void testDeleteStock() {
		List<StocksResDTO> stockList = getStockResults();
		assertNotNull(stockList);
		assertTrue(stockList.size()>1);
		StocksResDTO stockRes = stockList.get(0);
		this.restTemplate.delete(BASEHOSTURL + port + "/api/stocks/"+stockRes.getId(), String.class);
		StocksResDTO response = this.restTemplate.getForObject(BASEHOSTURL + port + "/api/stocks/"+stockRes.getId(), StocksResDTO.class);
		assertNull(response.getId());
	}
	
	private List<StocksResDTO> getStockResults(){
		return this.restTemplate.getForObject(BASEHOSTURL + port + "/api/stocks", StocksMasterResDTO.class).getStocks();
	}
}
