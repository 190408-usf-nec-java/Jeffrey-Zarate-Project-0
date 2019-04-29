package com.revature.userfacing;

import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.BankServicesImpl;
import com.revature.services.UserServicesImpl;

public class BankScreenRun {
	
	public static void RunApplication()
	{
		BankServicesImpl bankServer = new BankServicesImpl();
		UserServicesImpl transactionProcessor = new UserServicesImpl();
		Scanner scanner = new Scanner(System.in);
		
		Screens currentScreen = Screens.StartScreen;
		String command;
		boolean running = true;
		User currentUser = null;
		Account currentAccount = null;
		while(running)
		{
			switch(currentScreen)
			{
			case StartScreen:
				System.out.println("\n\n\n");
				System.out.println("Welcome to IronBank.");
				System.out.println("1 - Login\n2 - Signup\n3 - Exit");
				command =scanner.nextLine();
				switch(command)
				{
				case "1":
					currentScreen=Screens.LoginScreen;
					
					break;
				case "2":
					currentScreen=Screens.SignupScreen;
					break;
				case "3":
					running = false;
					break;
				default:
					System.out.println("Invalid input.");
					currentScreen = Screens.StartScreen;
					break;
				}
				break;
				
			case SignupScreen:
				System.out.println("\n\n\n");
				boolean validUser = false;
				String username2, password2, name2;
				System.out.println("Set your username: ");
				username2= scanner.nextLine();
				System.out.println("Set your password: ");
				password2 = scanner.nextLine();
				System.out.println("Set a name for your account: ");
				name2 = scanner.nextLine();
				validUser = bankServer.createUser(username2, password2, name2);
				currentScreen = Screens.StartScreen;
				if(validUser)
				{
					System.out.println("You have succesfully created your account.");
				}
				else
				{
					System.out.println("Please try again.");
				}
				
				break;
				
			case LoginScreen:
				System.out.println("\n\n\n");
				String username, password;
				System.out.println("Username: ");
				username= scanner.nextLine();
				System.out.println("Password: ");
				password = scanner.nextLine();
				currentUser=bankServer.loginCheck(username, password);
				if(currentUser==null)
				{
					currentScreen = Screens.StartScreen;
				}
				else
				{
					currentScreen = Screens.MainMenu;
				}
				break;
				
			case MainMenu:
				System.out.println("\n\n\n");
				currentAccount=null;
				System.out.println("Welcome " + currentUser.getName()+"!");
				System.out.println("Accounts:");
				if(currentUser.getAccounts().isEmpty())
				{
					System.out.println("You do not have accounts yet.");
				}
				else
				{
					System.out.println(currentUser.toString());
				}
				System.out.println("1 - Transactions\n2 - Create Account \n3 - Log out");
				String command2;
				command2 = scanner.nextLine();
				switch(command2)
				{
				case "1":
					currentScreen= Screens.ChooseAccountScreen;
					break;
				case "2":
					currentScreen = Screens.CreateAccountScreen;
					break;
				case "3":
					currentUser = null;
					currentScreen = Screens.StartScreen;
					break;
				default:
					System.out.println("Invalid input.");
					break;
					
				}
				break;
			
			case CreateAccountScreen:
				System.out.println("\n\n\n");
				transactionProcessor.createAccount(currentUser);
				System.out.println("You have succesfully created an account!");

				currentScreen = Screens.MainMenu;
				break;
				
			case ChooseAccountScreen:
				System.out.println("\n\n\n");
				transactionProcessor.getAllAccounts(currentUser);
				System.out.print("Select account to do transactions with : ");
				String command3;
				int index=0;
				command3 = scanner.nextLine();
				try
				{
					index=Integer.parseInt(command3);
					currentAccount = transactionProcessor.selectAccount(currentUser, index);
					currentScreen = Screens.TransactionsScreen;
				}
				catch(Exception e)
				{
					System.out.println("Invalid command.");
					currentScreen = Screens.MainMenu;
				}
				break;
				
			case TransactionsScreen:
				System.out.println("\n\n\n");
				System.out.println("1 - Deposit funds\n2 - Withdraw funds\n3 - Transfer funds\n4 - Join account with another user\n5 - Choose another account\n6- Main Menu");
				String command4;
				command4 = scanner.nextLine();
				switch(command4)
				{
				case "1":
					currentScreen = Screens.DepositScreen;
					break;
				case "2":
					currentScreen = Screens.WithdrawScreen;
					break;
				case "3":
					currentScreen = Screens.TransferScreen;
					break;
				case "4":
					currentScreen = Screens.JoinScreen;
					break;
				case "5":
					currentScreen = Screens.ChooseAccountScreen;
					break;
				case "6":
					currentScreen = Screens.MainMenu;
					break;
				default:
					System.out.println("Invalid command");
					break;
				}
				break;
				
			case DepositScreen:
				System.out.println("\n\n\n");
				System.out.print("Enter amount to deposit: ");
				double depositAmount =0;
				try
				{
					depositAmount = Double.parseDouble(scanner.nextLine());
					transactionProcessor.deposit(currentAccount, depositAmount);
				}
				catch(Exception e)
				{
					System.out.println("Please enter valid amount.");
				}
				currentScreen = Screens.TransactionsScreen;
				break;
				
			case WithdrawScreen:
				System.out.println("\n\n\n");
				System.out.print("Enter amount to withdraw: ");
				double withdrawAmount =0;
				try
				{
					withdrawAmount = Double.parseDouble(scanner.nextLine());
					transactionProcessor.withdraw(currentAccount, withdrawAmount);
				}
				catch(Exception e)
				{
					System.out.println("Please enter valid amount.");
				}
				currentScreen = Screens.TransactionsScreen;
				break;
				
			case TransferScreen:
				System.out.println("\n\n\n");
				System.out.print("Enter amount to transfer: ");
				double transferAmount =0;
				String transferAccount;
				try
				{
					transferAmount = Double.parseDouble(scanner.nextLine());
					System.out.print("Enter account number to transfer to: ");
					transferAccount = scanner.nextLine();
					transactionProcessor.transfer(currentAccount,transferAccount, transferAmount);
				}
				catch(Exception e)
				{
					System.out.println("Please enter valid amount.");
				}
				currentScreen = Screens.TransactionsScreen;
				break;
				
			case JoinScreen:
				System.out.println("\n\n\n");
				System.out.print("Enter username of user to join this acount with: ");
				String userName;
				userName = scanner.nextLine();
				transactionProcessor.joinAccounts(userName, currentAccount);
				currentScreen = Screens.TransactionsScreen;
				break;
				
			default:
				break;
				
			}
		}
		scanner.close();
	}

}
