package com.revature.bankdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class BankDaoImpl implements BankDao{

	private Connection connection = ConnectionFactory.getConnection();
	Logger logger = LogManager.getLogger(BankDaoImpl.class);
	
	@Override
	public void getAllAcounts() {

		String query = "select * from accounts";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Account account =new Account();
				account.setAccountNumber(result.getString("account_number"));
				account.setBalance(result.getDouble("balance"));
				Bank.getAccounts().add(account);
			}
		} catch (SQLException e) {
			logger.catching(e);
		}
	}

	@Override
	public void getAllUsers() {
		String query = "select * from users";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				User user = new User();
				user.setUserName(result.getString("username"));
				user.setPassword(result.getString("my_password"));
				user.setName(result.getString("account_name"));
				
				String query2 = "select account from user_accounts where user_name = ?";
				PreparedStatement statement2 = connection.prepareStatement(query2);
				statement2.setString(1,user.getUserName());
				ResultSet result2 = statement2.executeQuery();
				
				while(result2.next())
				{
					String accountNumber = result2.getString("account");
					for(Account account: Bank.getAccounts())
					{
						if (account.getAccountNumber().equals(accountNumber))
						{
							if((user.getAccounts().contains(account))==false)
							{
								user.getAccounts().add(account);
							}		
						}
					}					
				}
				Bank.getUsers().add(user);
			}
		} catch (SQLException e) {
			logger.catching(e);
		}
		
	}

	@Override
	public void updateDatabase(PreparedStatement statement) {

		try {
			statement.executeUpdate();
			
		} catch (SQLException e) {
			
			logger.catching(e);
		}
		
		
	}

}
