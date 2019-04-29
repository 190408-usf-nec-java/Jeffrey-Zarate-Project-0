package com.revature.services;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.bankdao.Bank;
import com.revature.bankdao.BankDaoImpl;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class BankServicesImpl implements BankServices{
	
	BankDaoImpl update = new BankDaoImpl();

	public boolean createUser(String username, String password, String name) {
		
		boolean userNameValid=true;
		boolean passwordValid=false;
		Logger logger = LogManager.getLogger(BankServicesImpl.class);
		
		for(User user: Bank.getUsers())
		{
			if(user.getUserName().equals(username)||username.contains(" ")==true||username.length()>20||username.length()<1)
			{
				System.out.println("Username already taken or is not valid, please change the username.");
				userNameValid=false;
				break;
			}
		}
		
		if(password.contains(" ") || password.length()<8 || password.length()>16)
		{
			System.out.println("Password should be 8-16 characters long and it cannot contain spaces.");
		}
		else
		{
			passwordValid = true;
		}
		if(passwordValid && userNameValid)
		{
			String query = "insert into users(username,my_password,account_name) values (?,?,?)";
			PreparedStatement statement;
			try {
				statement = ConnectionFactory.getConnection().prepareStatement(query);
				statement.setString(1,username);
				statement.setString(2, password);
				statement.setString(3, name);
				update.updateDatabase(statement);
			} catch (SQLException e) {
				
				logger.catching(e);
			}
			
			Bank.getUsers().add(new User(username,password,name));
			return true;
		}
		else
		{
			return false;
		}
		
	}
	

	public User loginCheck(String username, String password) {
		User currentUser = null;
		for(User user: Bank.getUsers())
		{
			if(user.getUserName().equals(username) && user.getPassword().equals(password))
			{
				currentUser = user;
				break;
			}
		}
		if(currentUser==null)
		{
				System.out.println("Username or password invalid.");
		}
		return currentUser;
	}
}
