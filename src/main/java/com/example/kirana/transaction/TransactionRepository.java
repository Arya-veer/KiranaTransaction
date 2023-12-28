package com.example.kirana.transaction;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
	@Query(value = "SELECT * FROM transaction WHERE transaction_date = :date", nativeQuery = true)
	List<Transaction> getByDate(@Param("date") LocalDate date);
	
	@Query(value = "SELECT * FROM transaction WHERE transaction_type = 'CREDIT'", nativeQuery = true)
	List<Transaction> getAllCredit();
	
	@Query(value = "SELECT * FROM transaction WHERE transaction_type = 'DEBIT'", nativeQuery = true)
	List<Transaction> getAllDebit();
	
}
