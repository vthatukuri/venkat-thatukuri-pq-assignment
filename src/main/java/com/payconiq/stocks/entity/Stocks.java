package com.payconiq.stocks.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 *  Stock Entity
 *
 */
@Setter
@Getter
@Entity
public class Stocks {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, precision = 8, scale = 2)
	private BigDecimal currentPrice;
	
	private LocalDateTime lstUpdatedDtm;
	
	@Column(length = 1)
	private String activeCd;
	
	private LocalDateTime createdDtm;
}
