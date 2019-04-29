package com.revature.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.bankdao.Bank;
import com.revature.bankdao.BankDaoImpl;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class UserServicesImpl implements UserServices{
	
	BankDaoImpl update = new BankDaoImpl();

	public void createAccount(User user) {
		Account account = new Account();
		Logger logger = LogManager.getLogger(UserServicesImpl.class);
		
		try {
			String query = "insert into accounts (account_number,balance) values (?,?)";
			PreparedStatement statement= ConnectionFactory.getConnection().prepareStatement(query);
			statement.setString(1,account.getAccountNumber());
			statement.setDouble(2, account.getBalance());
			update.updateDatabase(statement);
		} catch (SQLException e) {
			
			logger.catching(e);
		}
		try
		{
			String query2 = "insert into user_accounts (user_name, account) values (?,?)";
			PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(query2);
			statement.setString(1, user.getUserName());
			statement.setString(2, account.getAccountNumber());
			update.updateDatabase(statement);
			
		}
		catch(SQLException e)
		{
			logger.catching(e);
		}		
		user.getAccounts().add(account);
	}

	public void getAllAccounts(User user) {
		if(user.getAccounts().isEmpty())
		{
			System.out.println("You do not have an account yet.");
		}
		else
		{
			int accountCounter=0;
			for(Account account: user.getAccounts())
			{
				accountCounter+=1;
				System.out.println(accountCounter+" - "+account.toString());
			}
		}
		
	}

	public Account selectAccount(User user, int number) {
		
		return user.getAccounts().get(number-1);
	}

	public void deposit(Account account, double amount) {
		
		Logger logger = LogManager.getLogger(UserServicesImpl.class);
		
		if(amount<=0)
		{
			System.out.println("Please enter a valid amount");
		}
		else
		{	
			account.setBalance(account.getBalance()+amount);
			try {
				String query = "update accounts set balance = ? where account_number = ?";
				PreparedStatement statement= ConnectionFactory.getConnection().prepareStatement(query);
				statement.setString(2,account.getAccountNumber());
				statement.setDouble(1, account.getBalance());
				update.updateDatabase(statement);
			} catch (SQLException e) {
				
				logger.catching(e);
			}
			System.out.println("You have succesfully deposited "+amount+" dollars to your account.");
			System.out.println("Your balance is "+account.getBalance()+" dollars.");
		}
		
	}

	public void withdraw(Account account, double amount) {
		
		Logger logger = LogManager.getLogger(UserServicesImpl.class);
		
		if(amount> account.getBalance() || amount<=0)
		{
			System.out.println("Please enter a valid amount");
		}
		
		else
		{
			account.setBalance(account.getBalance()-amount);
			try {
				String query = "update accounts set balance = ? where account_number = ?";
				PreparedStatement statement= ConnectionFactory.getConnection().prepareStatement(query);
				statement.setString(2,account.getAccountNumber());
				statement.setDouble(1, account.getBalance());
				update.updateDatabase(statement);
			} catch (SQLException e) {
				
				logger.catching(e);
			}
			System.out.println("You have succesfully withdrawn "+amount+" dollars to your account.");
			System.out.println("Your balance is "+account.getBalance()+" dollars.");
		}
		
	}

	public void transfer(Account account1, String transfer, double amount) {
		
		Logger logger = LogManager.getLogger(UserServicesImpl.class);
		Account otheraccount;
		boolean accountFound= false;
		for(Account account: Bank.getAccounts())
		{
			
			if(account.getAccountNumber().equals(transfer))
			{
				otheraccount=account;
				accountFound=true;
				if(amount> account1.getBalance() || amount<=0)
				{
					System.out.println("Please enter a valid amount");
				}
				
				else
				{
					account1.setBalance(account1.getBalance()-amount);
					otheraccount.setBalance(otheraccount.getBalance()+amount);
					try {
						Connection connection = ConnectionFactory.getConnection();
						connection.setAutoCommit(false);
						connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
						String query = "update accounts set balance = ? where account_number = ?";
						PreparedStatement statement= connection.prepareStatement(query);
						statement.setString(2,account1.getAccountNumber());
						statement.setDouble(1, account1.getBalance());
						update.updateDatabase(statement);
						String query2 = "update accounts set balance = ? where account_number = ?";
						PreparedStatement statement2= connection.prepareStatement(query2);
						statement2.setString(2,otheraccount.getAccountNumber());
						statement2.setDouble(1, otheraccount.getBalance());
						update.updateDatabase(statement2);
						connection.commit();
					} catch (SQLException e) {
						
						logger.catching(e);
					}
					
					System.out.println("You have successfully transferred "+amount+" dollars to account number "+otheraccount.getAccountNumber()+".");
					System.out.println("Your balance is "+account1.getBalance()+" dollars.");
					break;
				}
			}		
			
		}
		if(accountFound==false)
		{
			System.out.println("The account you are trying to transfer to does not exist.");
		}
		
		
	}

	public void joinAccounts(String userName, Account account) {
		
		Logger logger = LogManager.getLogger(UserServicesImpl.class);
		boolean userFound = false;
		for(User user: Bank.getUsers())
		{
			if(user.getUserName().equals(userName))
			{
				userFound = true;
				if (user.getAccounts().contains(account))
				{
					System.out.println("The account is already shared with the user");
					break;
				}
				else
				{
					user.getAccounts().add(account);
					try
					{
						String query2 = "insert into user_accounts (user_name, account) values (?,?)";
						PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(query2);
						statement.setString(1, user.getUserName());
						statement.setString(2, account.getAccountNumber());
						update.updateDatabase(statement);
						
					}
					catch(SQLException e)
					{
						logger.catching(e);
					}		
					System.out.println("You have succesfully shared account number "+account.getAccountNumber()+" with "+user.getName());
					break;
				}
			}
		}
		
		if(userFound==false)
		{
			System.out.println("User not found.");
		}
	}	
}
