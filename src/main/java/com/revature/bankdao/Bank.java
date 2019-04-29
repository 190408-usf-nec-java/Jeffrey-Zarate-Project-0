package com.revature.bankdao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.models.Account;
import com.revature.models.User;

public class Bank {
	
	private static List<User> users = new ArrayList<User>();
	private static Set<Account> accounts = new HashSet<Account>();

	private Bank() {
		super();
	}

	public static List<User> getUsers() {
		return users;
	}

	public static void setUsers(List<User> users) {
		Bank.users = users;
	}

	public static Set<Account> getAccounts() {
		return accounts;
	}

	public static void setAccounts(Set<Account> accounts) {
		Bank.accounts = accounts;
	}
	
	
	
	
}
