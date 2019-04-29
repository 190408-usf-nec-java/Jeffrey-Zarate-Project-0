package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.userfacing.BankLauncher;

public class ConnectionFactory {

	private static String username;
	private static String password;
	private static String url;
	private static Connection connection;
	
	
	public static Connection getConnection()
	{
		Logger logger = LogManager.getLogger(ConnectionFactory.class);
		username = System.getenv("User_name");
		password = System.getenv("Password");
		url = System.getenv("URL");
		
		if(connection==null)
		{
			try
			{
				connection = DriverManager.getConnection(url,username,password);
			}
			catch(SQLException e)
			{
				System.out.println("Could not connect to the database");
				logger.catching(e);
			}
			
		}
		
		return connection;
		
	}
	
	
}
