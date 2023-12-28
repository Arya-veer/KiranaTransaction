package com.example.kirana.transaction;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.*;

@ToString
class CurrencyConversion {
	public Boolean success;
	public String terms;
	public String privacy;
	public Long timestamp;
	public Date date;
	public String base;
	public Map<String, Double> rates;
	
	public static Double getCurrencyConversionRate() throws IOException, InterruptedException {		
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
		    .uri(URI.create("https://api.fxratesapi.com/latest"))
		    .GET()
		    .setHeader("user-agent", "KiranaStoreJava")
		    .build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		ObjectMapper mapper = new ObjectMapper();
		CurrencyConversion res = mapper.readValue(response.body(), CurrencyConversion.class);
		return res.rates.get("INR");
	}
}

@Entity
@Table(name="transaction")

public class Transaction {
	
	
	@Id
	@Column(name = "transaction_id")
	private UUID transactionId;
	
	
	// We can use amount in USD. and convert to INR if required in getter
	@Column(name = "transaction_amount_usd")
	private Double transactionAmountUSD;
	
	@Column(name = "transaction_currency")
	private String transactionCurrency;
	
	@Column(name = "transaction_date")
	private LocalDate transactionDate;
	
	@Column(name = "transaction_time")
	private LocalTime transactionTime;
	
	@Column(name = "transaction_type")
	private String transactionType;
	
	public Transaction() {
		super();
	}
	
	public Transaction(Double transactionAmount,String transactionCurrency,String transactionType) {
		this.transactionCurrency = transactionCurrency;
		if (transactionCurrency.equals("INR")) {
			try {
				this.transactionAmountUSD = transactionAmount/CurrencyConversion.getCurrencyConversionRate();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		else {
			this.transactionAmountUSD = transactionAmount;
		}
		
		this.transactionDate = LocalDate.now();
		this.transactionTime = LocalTime.now();
		this.transactionId = UUID.randomUUID();
		this.transactionType = new String(transactionType);
	}
	
	
	public UUID getTransactionId() {
		return transactionId;
	}

	public Double getTransactionAmountUSD() {
		if(this.transactionCurrency.equals("INR")) {
			try {
				return Math.round(this.transactionAmountUSD * CurrencyConversion.getCurrencyConversionRate()*100.00)/100.00;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.transactionAmountUSD;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	
	public LocalTime getTransactionTime() {
		return this.transactionTime;
	}

	public String getTransactionType() {
		return transactionType;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", transactionAmountUSD=" + transactionAmountUSD
				+ ", transactionCurrency=" + transactionCurrency + ", transactionDate=" + transactionDate
				+ ", transactionType=" + transactionType + "]";
	}
	
	
}
