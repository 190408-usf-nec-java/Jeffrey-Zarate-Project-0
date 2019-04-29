package com.revature.bankdao;

import java.sql.PreparedStatement;

public interface BankDao {
	
	void getAllAcounts();
	
	void getAllUsers();
	
	void updateDatabase(PreparedStatement statement);

}
