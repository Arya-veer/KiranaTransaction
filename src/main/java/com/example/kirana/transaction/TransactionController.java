package com.example.kirana.transaction;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;


class AddTransactionRequestSerializer {
	public String currency;
	public Double amount;
	public String type;
}


@RestController
@RequestMapping(path="/transaction")
@ResponseBody
public class TransactionController {

	@Autowired
	private TransactionRepository transactionRepo;
	
	
	/*
	 * Creates a new Transaction
	 * @Params
	 * 	currency
	 *	amount
	 * 	type
	 */
	@PostMapping(value = "/create") 
	public Map<String,String> addTransaction(@RequestBody AddTransactionRequestSerializer request) 
	{
		Transaction tr = new Transaction(request.amount,request.currency,request.type);
		transactionRepo.save(tr);
		Map<String,String> response = new HashMap<>();
		response.put("message","Created");
		return response;
	}
	
	
	/*
	 * Lists all Transactions
	 */
	@GetMapping(path = "/list")
	public @ResponseBody List<Transaction> allTransactions(){
		return transactionRepo.findAll();
	}
	
	
	/*
	 * Lists all transactions by given date
	 * @Params
	 * 	date
	 */
	@GetMapping(path = "/byDate")
	public @ResponseBody List<Transaction> getTransactionsByDate(@RequestParam String date){
		return transactionRepo.getByDate(LocalDate.parse(date));
	}
	
	/*
	 * Lists all debit Transactions
	 */
	@GetMapping(path = "/debit")
	public @ResponseBody List<Transaction> getAllDebit(){
		return transactionRepo.getAllDebit();
	}
	
	/*
	 * Lists all credit Transactions
	 */
	@GetMapping(path = "/credit")
	public @ResponseBody List<Transaction> getAllCredit(){
		return transactionRepo.getAllCredit();
	}
	
	/*
	 * Deletes transaction by ID
	 */
	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody Map<String,String> deleteTransaction(@PathVariable UUID id){
		transactionRepo.deleteById(id);
		Map<String,String> response = new HashMap<String,String>();
		response.put("message","Deleted");
		return response;
	}

}
