package com.payconiq.stocks.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payconiq.stocks.dto.StocksMasterResDTO;
import com.payconiq.stocks.dto.StocksReqDTO;
import com.payconiq.stocks.dto.StocksResDTO;
import com.payconiq.stocks.service.StocksService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * Resource class for stock API's
 *
 */
@RestController
@Slf4j
@Api("Stocks")
@RequestMapping("/stocks")
public class StocksResource {

	@Autowired
	StocksService stockService;
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully returned stock details."),
			@ApiResponse(responseCode = "400", description = "Invalid Input."),
			@ApiResponse(responseCode = "204", description = "No stocks found."),
			@ApiResponse(responseCode = "500", description = "Internal Server Error.")})
	@Operation(summary="Get all stock details")
	@GetMapping
	public ResponseEntity<StocksMasterResDTO> getAllStocks(
			@ApiParam(name = "PageNo", type = "int", value = "Offset", example = "0", required = false) @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@ApiParam(name = "PageSize", type = "int", value = "Page Size", example = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		log.info("Fetch stock details with: PageNo{} , PageSize:{}", pageNo,pageSize);
		StocksMasterResDTO response = stockService.getStocks(pageSize,pageNo);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "Stock created successfully."),
			@ApiResponse(responseCode = "400", description = "Invalid Input."),
			@ApiResponse(description = "Internal Server Error.", responseCode = "500") })
	@Operation(summary = "Create new stock")
	@PostMapping
	public ResponseEntity<StocksResDTO> createStock(@Valid @RequestBody StocksReqDTO request) {
		log.info("Create stock Request: {}", request);
		StocksResDTO response = stockService.createStock(request);
		return new ResponseEntity<StocksResDTO>(response,HttpStatus.CREATED);
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully returned stock details."),
			@ApiResponse(responseCode = "400", description = "Invalid Input."),
			@ApiResponse(responseCode = "204", description = "No stock found with the Id."),
			@ApiResponse(responseCode = "500", description = "Internal Server Error.")})
	@Operation(summary="Get stock details based on ID")
	@GetMapping("/{id}")
	public ResponseEntity<StocksResDTO> getStock(@PathVariable("id") Integer stockId) {
		log.info("Fetch stock details with ID: {}", stockId);
		StocksResDTO response = stockService.getStock(stockId);
		return new ResponseEntity<StocksResDTO>(response, HttpStatus.OK);
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Stock updated successfully."),
			@ApiResponse(responseCode = "400", description = "Invalid Input."),
			@ApiResponse(responseCode = "204", description = "No stock found with the Id."),
			@ApiResponse(responseCode = "500", description = "Internal Server Error.")})
	@Operation(summary="Update stock details based on ID")
	@PatchMapping("/{id}")
    public ResponseEntity<StocksResDTO> updateStock(@PathVariable(value = "id") Integer stockId, @RequestBody StocksReqDTO request){
		log.info("Update Stock details for ID : {}", stockId);
		StocksResDTO response = stockService.updateStock(stockId,request);
		return new ResponseEntity<StocksResDTO>(response,HttpStatus.OK);
	}
    
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Stock deleted successfully."),
			@ApiResponse(responseCode = "204", description = "No stock found with the Id."),
			@ApiResponse(responseCode = "500", description = "Internal Server Error.")})
	@Operation(summary="Get stock details based on ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStock(@PathVariable(value = "id") Integer stockId){
		log.info("Delete stock with ID : {}", stockId);
		stockService.deleteStock(stockId);
		return new ResponseEntity<String>("Stock deleted successfully!",HttpStatus.OK);
	}
}
