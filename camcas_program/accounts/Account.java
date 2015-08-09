package accounts;

import java.io.Serializable;
import java.util.ArrayList;

import tests.Test;

/**
 * The Account class is used to store students and tutors details
 * and allows them to login to the program as their details are 
 * checked with whats in their account.
 * 
 * @author 		Mesh, Tom
 * @version		1.0	
 */
public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private AccountType accountType;
	private ArrayList<Test> quizList;
	
	/**
	 * Contructs a new account assigning details specific to a user.
	 * 
	 * @param name	the name of the user.
	 * @param password	the password for this account.
	 * @param accountType	the type of account which is either student
	 * 						or teacher.
	 */
	public Account(String name, String password, AccountType accountType) {
		this.name = name;
		this.password = password;
		this.accountType = accountType;
		quizList = new ArrayList<Test>();
	}
	
	/**
	 * Gets the name of the user from assigned to this account.
	 * 
	 * @return	the users name for their account.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets all the users details. 
	 * 
	 * @return	a text showing the account holders username, password and account type.
	 */
	public String getAccountDetails() {
		return " Account Details\n" + "\n Username: " + name + "\n Password: " + 
		password + "\n Account Type: " + accountType;
	}
	
	/**
	 * Changes the password this particular account.
	 * 
	 * @param old	text representing the accounts current password.
	 * @param password	text representing the accounts new password.
	 * @return	true if the password has been changed or false if otherwise.
	 */
	public boolean changePassword(String old, String password){
		if (this.password.equals(old)) {
			this.password = password;
			return true;
		}
		else return false;
	}
	
	/**
	 * Checks an accounts current password.
	 * 
	 * @param password	the password for this account.
	 * @return	true if both passwords match or false if otherwise.
	 */
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}
	
	/**
	 * Gets the account type of this account.
	 * 
	 * @return	either student or teacher.
	 */
	public AccountType getAccountType()
	{
		return accountType;
	}
	
	/**
	 * Gets the accountholders name.
	 * 
	 * @return	text representing the username.
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * Gets all the tests stored on the account.
	 * 
	 * @return	all the tests for the account.
	 */
	public Test[] getQuizList() {
		Test[] quizArray = new Test[quizList.size()];
		for(int quizNum = 0; quizNum < quizList.size(); quizNum++) {
			quizArray[quizNum] = quizList.get(quizNum);
		}
		return quizArray;
	}
	
	/**
	 * Gets a specified test from this account.
	 * 
	 * @param quizNum	the number of the test.
	 * @return	the test specified.
	 */
	public Test getQuiz(int quizNum) {
		return quizList.get(quizNum);
	}
	
	/**
	 * Gets the size of this account.
	 * 
	 * @return	the number of tests stored in this account.
	 */
	public int getSize() {
		return quizList.size();
	}
	
	/**
	 * Adds a new test to this account.
	 * 
	 * @param quiz	the test to be added.
	 */
	public void addQuiz(Test quiz) {
		quizList.add(quiz);
	}
	
	/**
	 * Removes a test from this account.
	 * 
	 * @param quizNum	the number of the test.
	 */
	public void removeQuiz(int quizNum) {
		quizList.remove(quizNum);
	}
	
	/**
	 * Gets the test from this account.
	 * 
	 * @param testNum	the number of the test.
	 * @return	the specified test.
	 */
	public Test getTest(int testNum) {
		return quizList.get(testNum);
	}
	
	/**
	 * Gets the statistics of a specified test.
	 * 
	 * @param testNum	the number of a test.
	 * @return	a text rep
	 */
	public String getTestStatistics(int testNum) {
		Test test = quizList.get(testNum);
		return test.getStudentStatistics();
	}
	
}
