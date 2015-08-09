package tests;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Test class is used to store questions and a 
 * topic name that the questions represent. These
 * questions are then used to produce the results
 * and set the test as taken.
 * 
 * @author 		Mesh, Tom
 * @version		1.0	
 */
public class Test implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<Question> questions;
	private String topic;
	private int[] answersGiven;
	private int result;
	private boolean finished;

	/**
	 * Contructs a test with a topic name and questions 
	 * allocated to it.
	 * 
	 * @param topic
	 * @param questions
	 */
	public Test(String topic, ArrayList<Question> questions)
	{
		this.topic = topic;
		this.questions = questions;
		finished = false;
		//initialize array
		answersGiven = new int[questions.size()];
	}
	
	/**
	 * Sets the answer number to the array of answer numbers selected 
	 * from the student who has taken the test.
	 * 
	 *@param questionNo		the question number.
	 *@param answerNo		the answer number.
	 */
	public void setAnswerGiven(int questionNo, int answerNo) {
		answersGiven[questionNo - 1] = answerNo;
	}
	
	/**
	 * Works out the statistics from the answers given for 
	 * each question.
	 * 
	 * @return	the questions answered, correct answers and result.
	 */
	public String getStudentStatistics() {
		String statistics = "TOPIC : " + topic + "\n";
		if (finished)
		{		
			result = 0;
			for (int n = 1; n <= getNoOfQuestions(); n++)
			{
				statistics += "\nQ" + n + ": " + getQuestionText(n)+'\n';
				if(answersGiven[n - 1] == 0) {
					// Used if no answer has been given for a question.
					statistics += "Answer Not Given\n";
					statistics += "Correct Answer: " + getCorrectAnswerText(n) + '\n';
				} else if (isCorrect(n, answersGiven[n - 1])) {
						statistics += "Answered Correctly\n";
						result++;
				} else {
					int ans = answersGiven[n - 1];
					statistics += "Student Chose: " + getAnswerText(n, ans)+'\n';
					statistics += "Correct Answer: " + getCorrectAnswerText(n) + '\n';
				}
			}
			statistics += "\nAnswers Correct: " + result;
		}
		else 
			statistics += "\nTest Not Taken";

		return statistics;
	}
	
	/**
	 * Gets a boolean showing if this test is finished.
	 * 
	 * @return boolean		true if the test is finished or
	 * 						false if its not.
	 */
	public boolean isFinished() {
		return finished;
	}
	
	/**
	 * Sets a boolean to show if this test is finished or not.
	 * 
	 *@param isFinished		true if the test is finished or
	 * 						false if its not.
	 */
	public void setIsFinished(boolean isFinished) {
		finished = isFinished;
	}
	
	/**
	 * Gets the answer given from the user.
	 * 
	 * @param questionNo	the number of the question.
	 * @return	the users answer to the question.
	 */
	public int getAnswerGiven(int questionNo)
	{
		return answersGiven[questionNo-1];
	}
	
	/**
	 * Checks if the there have been answers given from the 
	 * user for all the questions.
	 * 
	 * @return	true if the test has been completly answered or
	 * 			false if it has not.
	 */
	public boolean isAnswered()
	{
		for (int n = 0; n < answersGiven.length; n++)
			if (answersGiven[n] == 0)
				return false;
		return true;
	}
	
	/**
	 * Checks if all the questions have been answered, if not then
	 * the questions that havent been answered are added to a String.
	 * 
	 * @return	the questions that have no answer assigned to it.
	 */
	public String warnings()
	{
		String toReturn="";
		for (int n = 0; n < answersGiven.length; n++)
		{
			if (answersGiven[n] == 0)
				toReturn += "Answer not given for question " + (n + 1) + '\n';
		}
		return toReturn;
	}

	/**
	 * Gets a question from the ArrayList and returns its
	 * text.
	 * 
	 * @param questionNo 	the number for the question.
	 * @return		 		a String making up the question.
	 */
	public String getQuestionText(int questionNo) {
		Question currentQuestion = questions.get(questionNo - 1);
		return currentQuestion.getQuestion();
	}
	
	/**
	 * Gets a question from the ArrayList and gets the 
	 * answer from the ArrayList in the question and 
	 * gets the answers text.
	 * 
	 * @param questionNo 	the number for the question.
	 * @param answerNo 		the number for the answer.
	 * @return		 		a String making up the answer.
	 */
	public String getAnswerText(int questionNo, int answerNo) {
		Question currentQuestion = questions.get(questionNo-1);
		return currentQuestion.getAnswerText(answerNo - 1);
	}
	
	/**
	 * Gets the total number of questions in the arraylist.
	 * 
	 * @return int	 	the number of questions.
	 */
	public int getNoOfQuestions() {
		return questions.size();
	}
	
	/**
	 * Gets the total number of answers in a specified
	 * question.
	 * 
	 * @return int	 	the number of answers.
	 */
	public int getNoOfAnswers(int questionNo) {
		Question currentQuestion = questions.get(questionNo-1);
		return currentQuestion.getNoOfAnswers();
	}
	
	/**
	 * Gets a boolean showing if the specified answer is 
	 * correct or incorrect from the answer that has been 
	 * returned from the question.
	 * 
	 * @param questionNo 	the question number
	 * @param answerNo 		the answer number
	 * @return 	 			true is the answer is to be correct 
	 * 						or false if the answer is to be 
	 * 						incorrect.
	 */
	public boolean isCorrect(int questionNo, int answerNo) {
		Question currentQuestion = questions.get(questionNo - 1);
		return currentQuestion.isAnswerCorrect(answerNo - 1);
	}
	
	/**
	 * Gets the topic name.
	 * 
	 * @return String	 		the topic name. 
	 */
	public String toString() {
		return topic;
	}
	
	/**
	 * Gets the text representation of a correct answer for a specified 
	 * question.
	 * 
	 * @param questionNo	the number of the question.
	 * @return	the text of the correct answer.
	 */
	public String getCorrectAnswerText(int questionNo){
		return (questions.get(questionNo - 1)).getCorrectAnswerText();
	}
	
	/**
	 * Sets the result of this test.
	 * 
	 * @param result	the number of questions answered correctly.
	 */
	public void setResult(int result) {
		this.result = result;
	}
	
	/**
	 * Gets the result of this test.
	 * 
	 * @return	the number of questions answered correctly.
	 */
	public int getResult() {
		getStudentStatistics();
		return result;
	}
	
}
