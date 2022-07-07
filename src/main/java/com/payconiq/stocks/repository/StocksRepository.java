package com.payconiq.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payconiq.stocks.entity.Stocks;

/**
 * Stocks repository
 *
 */
@Repository
public interface StocksRepository extends JpaRepository<Stocks, Integer> {

}
