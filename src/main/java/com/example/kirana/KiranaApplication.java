package com.example.kirana;

import org.springframework.boot.SpringApplication;

import com.example.kirana.transaction.Transaction;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KiranaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiranaApplication.class, args);
//		Transaction transaction = new Transaction(100.0,"INR","DEBIT");
//		System.out.println(transaction.getTransactionAmount());
	}

}
