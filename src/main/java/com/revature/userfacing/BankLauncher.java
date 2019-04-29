package com.revature.userfacing;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.bankdao.BankDaoImpl;
import com.revature.util.ConnectionFactory;

public class BankLauncher {
	
	public static void main(String[] args) {
		
		Connection connection = ConnectionFactory.getConnection();
		BankDaoImpl data = new BankDaoImpl();
		Logger logger = LogManager.getLogger(BankLauncher.class);
		data.getAllAcounts();
		data.getAllUsers();
		BankScreenRun.RunApplication();
		try {
			connection.close();
		} catch (SQLException e) {
			
			logger.catching(e);
		}
		
	}
}
