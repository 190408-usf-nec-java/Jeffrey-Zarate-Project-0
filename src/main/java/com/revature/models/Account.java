package com.revature.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.bankdao.Bank;
import com.revature.util.ConnectionFactory;

public class Account {
	private static int accountNumberGenerator;
	private String accountNumber;
	private double balance;
	
	public Account() {
		super();
		Connection connection = ConnectionFactory.getConnection();
		String query = "select account_number from accounts";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet results = statement.executeQuery();
			int latest =0;
			while(results.next())
			{
				int currentNumber =Integer.parseInt(results.getString("account_number"));
				if(currentNumber>latest)
				{
					latest = currentNumber;
				}
			}
			accountNumberGenerator=latest;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		accountNumberGenerator++;
		this.accountNumber = Integer.toString(accountNumberGenerator);
		Bank.getAccounts().add(this);
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account Number: " + accountNumber + "\tBalance: "+balance+"\n";
	}	
}
