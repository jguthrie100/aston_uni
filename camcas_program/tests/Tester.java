package tests;

/**
 * The Tester class is used to assign whatever the user
 * inputs to the test and show the details of this test 
 * to the user. 
 * 
 * @author 		Mesh, Tom
 * @version		1.0	
 */
public class Tester {
	
	private Test test;
	
	/**
	 * Contructs a tester where a test is assigned to it.
	 * 
	 * @param studentID
	 * @param test
	 */
	public Tester(String studentID, Test test) {
		this.test = test;
	}
	
	/**
	 * Gets the text of a specified question.
	 * 
	 * @param questionNo	the number of a question.
	 * @return	the text representation of the question.
	 */
	public String getQuestionText(int questionNo) {
		return test.getQuestionText(questionNo);
	}
	
	/**
	 * Gets the text of an answer from a specified question.
	 * 
	 * @param questionNo	the number of a question.
	 * @param answerNo	the number of an answer from the question.
	 * @return	the text representation of the answer.
	 */
	public String getAnswerText(int questionNo, int answerNo) {
		return test.getAnswerText(questionNo, answerNo);
	}
	
	/**
	 * Sets a answer given from a user to a specified question.
	 * 
	 * @param questionNo	the number of a question.
	 * @param answerNo	the number of an answer from the question.
	 */
	public void setAnswerGiven(int questionNo, int answerNo) {
		test.setAnswerGiven(questionNo, answerNo);
	}
	
	/**
	 * Gets the answer given from a user for a specified question.
	 * 
	 * @param questionNo	the number of a question.
	 * @return	the answer given from the user.
	 */
	public int getAnswerGiven(int questionNo) {
		return test.getAnswerGiven(questionNo);
	}
	
	/**
	 * Gets the statistics of the test.
	 * 
	 * @return a text representing the statistics.
	 */
	public String getStudentStatistics() {
		return test.getStudentStatistics();
	}
	
	/**
	 * Get the size of the test.
	 * 
	 * @return	the number of questions in the test.
	 */
	public int getSize() {
		return test.getNoOfQuestions();
	}
	
	/**
	 * Checks if the test has been taken already.
	 * 
	 * @return	true if the test is taken or false 
	 * 			otherwise.
	 */
	public boolean isAnswered()
	{
		return test.isAnswered();
	}
	
	/**
	 * Gets a list of the questions that havent been
	 * answered by the user.
	 * 
	 * @return	the questions from the test.
	 */
	public String warnings()
	{
		return test.warnings();
	}
	
	/**
	 * Gets the current test from the tester.
	 * 
	 * @return	the test in this tester.
	 */
	public Test getTest() {
		return test;
	}
	
	/**
	 * Gets the number of answers to a question.
	 * 
	 * @param questionNo	the number of the question.
	 * @return	the number of answers to the question.
	 */
	public int getNumAnswers(int questionNo) {
		return test.getNoOfAnswers(questionNo);
	}
	
	/**
	 * Sets the test to be finished.
	 * 
	 * @param isFinished	true if the test is finished 
	 * 						or false if otherwise.
	 */
	public void setIsFinished(boolean isFinished) {
		test.setIsFinished(isFinished);
	}
	
}
