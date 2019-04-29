package com.revature.services;
import com.revature.models.Account;
import com.revature.models.User;

public interface UserServices {
	
	void createAccount(User user);
	
	void getAllAccounts(User user);
	
	Account selectAccount(User user,int number);
	
	void deposit(Account account, double amount);
	
	void withdraw(Account account, double amount);
	
	void transfer(Account account, String tranferAccountNumber, double amount);
	
	void joinAccounts(String user, Account account);

}
