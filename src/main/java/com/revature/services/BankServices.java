package com.revature.services;

import com.revature.models.User;

public interface BankServices {
	
	boolean createUser(String username, String password, String name);
	
	User loginCheck(String username, String password);

}
