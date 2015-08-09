package accounts;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import tests.Test;
import file.ObjectED;

/**
 * The AccountHandler class is used to store Student and 
 * Teacher Accounts.
 * 
 * @author 		Mesh, Tom
 * @version		1.0	
 */
public class AccountHandler {

	private static final String FILENAME = "ACCOUNTS.ACC";
	private ArrayList<Account> accounts;
	private static final String DefaultUsername = "tutor";
	private static final String DefaultPassword = "tutor";
	private static final AccountType DefaultAccountType = AccountType.TEACHER;

	/**
	 * Constructs an AccountHandler and loads all the accounts, if no
	 * accounts available a new ArrayList of Accounts is created.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public AccountHandler () throws IOException, ClassNotFoundException {
		try {
			loadAccounts();
			addDefaultAccount();
		} catch (IOException e) {
			accounts = new ArrayList<Account>();
			addDefaultAccount();
		} catch (ClassNotFoundException e) {
			accounts = new ArrayList<Account>();
			addDefaultAccount();
		}
	}
	
	/**
	 * Adds a default teacher account if none exist in the accounts database at all.
	 */
	public void addDefaultAccount() {
		if(!isTutorAccountStored()) {
			// Create a Teacher Account if there isnt any in the account
			Account account = new Account(DefaultUsername, DefaultPassword, DefaultAccountType);
			accounts.add(account);
		}
	}
	
	/**
	 * checks to see if the currect teacher account is stored.
	 * 
	 * @return true if the account exists or false if otherwise.
	 */
	public boolean isTutorAccountStored() {
		for(Account account : accounts) {
			if(AccountType.TEACHER.equals(account.getAccountType()))
				return true;
		}
		return false;
	}
	
	/**
	 * Gets a specified account.
	 * 
	 * @param accountNum	the number of the account.
	 * @return	the account from the given number.
	 */
	public Account getAccount(int accountNum) {
		return accounts.get(accountNum);
	}

	/**
	 * Loads the accounts, if the accounts is null then a new ArrayList
	 * of accounts is created.
	 * 
	 * @throws IOException	thrown if theres a problem accessing the file.
	 * @throws ClassNotFoundException	thrown if the class is not found in the file.
	 */
	@SuppressWarnings("unchecked")
	public void loadAccounts() throws IOException, ClassNotFoundException
	{
		try {
			accounts = (ArrayList<Account>) ObjectED.decryptObject(FILENAME);
		} catch(Exception we) {
			accounts = new ArrayList<Account>();
		}
		if(accounts == null)
			accounts = new ArrayList<Account>();
	}

	/**
	 * Checks account details with the list of current accounts.
	 * 
	 * @param username	the username contained in the account.
	 * @param password	the password contained in the account.
	 * @param accountType	the type of account either being student or
	 * 						teacher.
	 * @return	the account if the details given match or null if there 
	 * 			is no such account.
	 */
	public Account login(String username, String password, AccountType accountType)
	{
		Account account = null;
		for (Account currentAccount : accounts) {
			if (currentAccount.getName().equalsIgnoreCase(username) 
					&& currentAccount.checkPassword(password)
					&& currentAccount.getAccountType().equals(accountType)) 
				account = currentAccount;
		}
		return account;
	}

	/**
	 * Adds a new account to the list of accounts.
	 * 
	 * @param username	the username for the new account.
	 * @param password	the password for the new account.
	 * @param accountType	the account type for the new account
	 * 						being either student or teacher.
	 * @return	true if the acount has been added or false if the
	 * 			new account details match an existing account in the 	
	 * 			list.
	 */
	public boolean addAccount(String username, String password, AccountType accountType)
	{
		for (Account account : accounts)
		{
			if (account.getName().equalsIgnoreCase(username)) 
				return false;
		}

		accounts.add(new Account(username,password,accountType));
		return true;
	}
	
	/**
	 * Adds a test to a specified account.
	 * 
	 * @param account	the account to have a test added.
	 * @param test	the test.
	 */
	public void addQuiz(Account account, Test test) {
		for(Account currentAccount : accounts) {
			if(currentAccount.equals(account)) {
				currentAccount.addQuiz(test);
				break;
			}
		}
	}
	
	/**
	 * Removes an account specified.
	 * 
	 * @param index	the number of the account.
	 */
	public void removeAccount(int index){
		accounts.remove(index);
	}
	
	/**
	 * Gets the size of the accounts list.
	 * 
	 * @return	the number of accounts.
	 */
	public int getSize() {
		return accounts.size();
	}

	/**
	 * Saves the list of accounts to a file.
	 * 
	 * @throws IOException	thrown if the file cannot be read.
	 * @throws KeyStoreException	thrown if the key hasent been encrypted.
	 * @throws CertificateException	thrown if the key is null or not a public key.
	 * @throws ClassNotFoundException	if there is no class found.
	 */
	public void updateFile() throws IOException, KeyStoreException, CertificateException, ClassNotFoundException
	{
		ObjectED.encryptObject(accounts, FILENAME);
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public List getList() {
		return accounts;
	}
	
	/**
	 * Gets an account from specifying the username.
	 * 	
	 * @param username	the username of the account.
	 * @return	the account matching the username or false
	 * 			if the username didnt match any accounts.
	 */
	public Account getAccount(String username) {
		Account studentAccount = null;
		for(Account account : accounts) {
			if(account.getName().equals(username)) {
				studentAccount = account;
			}
		}
		return studentAccount;
	}

}

