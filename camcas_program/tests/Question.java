package tests;

import java.io.Serializable;

/**
 * The Question class is used to hold four answers and used 
 * in tests where questions are displayed for students to 
 * select the correct answer for the question.
 * 
 * @author 		Mesh, Tom
 * @version		1.0	
 */
public class Question implements Serializable{

	private static final long serialVersionUID = 1L;
	private String text;
	private Answer[] answers;

	/**
	 * Creates a blank question holding an arraylist of 
	 * answers.
	 */
	public Question() {
		text = "";
		answers = new Answer[4];
		for (int n = 0; n < answers.length; n++)
			answers[n] = new Answer();
	}

	/**
	 * Gets the text making up the question.
	 * 
	 * @return text 	a particular question.
	 */
	public String getQuestion() {
		return text;
	}

	/**
	 * Assigns a question to the text variable.
	 * 
	 * @param question 	text used for the question.
	 */
	public void setQuestion(String question) {
		text = question;
	}

	/**
	 * Gets a specified answer from the list of answers 
	 * and gets the text representing that particular 
	 * answer.
	 * 
	 * @return String	 a String making up an answer.
	 */
	public String getAnswerText(int answerNo) {
		return answers[answerNo].getAnswer();
	}

	/**
	 * Gets a specified answer from the list of answers 
	 * and sets a text.
	 * 
	 * @param answerNo 	the answer number
	 * @param text	 	text used for the answer.
	 */
	public void setAnswerText(int answerNo, String text) {
		answers[answerNo] = new Answer();
		answers[answerNo].setAnswer(text);
	}

	/**
	 * Gets the number of answers for this question.
	 * 
	 * @return int	 	the total number of answers in the 
	 * 					arraylist.
	 */
	public int getNoOfAnswers() {
		return answers.length;
	}

	/**
	 * Gets a boolean showing if the specified answer is 
	 * correct or incorrect.
	 * 
	 * @param answerNo 	the answer number
	 * @return 	 		true is the answer is to be correct 
	 * 					or false if the answer is to be 
	 * 					incorrect.
	 */
	public boolean isAnswerCorrect(int answerNo) {
		return answers[answerNo].isCorrect();
	}

	/**
	 * Sets a specified answer to be correct or incorrect.
	 * 
	 * @param answerNo 	the answer number
	 * @param correct	true is the answer is to be correct 
	 * 					or false if the answer is to be 
	 * 					incorrect.
	 */
	public void setIsAnswerCorrect(int answerNo, boolean correct) {
		answers[answerNo].setIsCorrect(correct);
	}

	/**
	 * Sets the correct answer text.
	 * 
	 * @return String 	returns the string representing the
	 * 					correct answer or null if there is no
	 * 					correct answer.
	 */
	public String getCorrectAnswerText()
	{
		for (int n = 0; n < answers.length; n++)
			if (answers[n].isCorrect())
				return answers[n].getAnswer();
		return null;
	}

	/**
	 * Checks if a String has been added to the question and 
	 * answers.
	 * 
	 * @return boolean 	returns true if the question or answer hasent
	 * 					been completed otherwise it returns false.
	 */
	public boolean incomplete(){
		for (int n = 0; n < answers.length; n++)
			if (answers[n].getAnswer() == null || answers[n].getAnswer().equals(""))
				return true;
		if (text == null || text.equals("")) return true;
		return false;
	}

	/**
	 * Shows a list of the questions and answers that have not 
	 * been completed.
	 * 
	 * @return toReturn 	Contains a list of the question or answer 
	 * 						numbers that do not contain text.
	 */
	public String problems() {
		String toReturn = "";
		if (text == null || text.equals("")) 
			toReturn += "Text ";
		for (int n = 0; n < answers.length; n++)
			if (answers[n].getAnswer() == null || answers[n].getAnswer().equals(""))
				toReturn += "Answer[" + (n+1) + "] ";
		return toReturn;
	}

	/**
	 * Changes the number of answers that are available in this 
	 * question.
	 * 
	 * @param numAnswers 	the number of answers for this question.
	 */
	public void setNumAnswers(int numAnswers) {
		answers = new Answer[numAnswers];
		for (int n = 0; n < answers.length; n++) {
			answers[n] = new Answer();
		}
	}

}
