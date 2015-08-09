package tests;

import java.io.IOException;
import java.io.Serializable;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
 
import file.ObjectED;

/**
 * The QuestionArchive is used to store a whole collection of
 * questions from different topics that have been added from 
 * using the test generator.
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class QuestionArchive implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String FILENAME = "ARCHIVE.QDB";
	private ArrayList<Question> archivedQuestions;

	/**
	 * Creates the archive with the archived questions loaded 
	 * in it.
	 */
	public QuestionArchive()  {
		loadArchive();
		if(archivedQuestions == null || archivedQuestions.size() <= 0) 
			archivedQuestions = new ArrayList<Question>();
	}

	/**
	 * Loads the archive database from the file, if there is no
	 * file found then a new archive database is created to save 
	 * questions.
	 */
	@SuppressWarnings("unchecked")
	public void loadArchive() 
	{
		try {
			archivedQuestions = (ArrayList<Question>) ObjectED.decryptObject(FILENAME);
		} catch(Exception we) {
			archivedQuestions = new ArrayList<Question>();
		}
		if(archivedQuestions == null)
			archivedQuestions = new ArrayList<Question>();
	}

	/**
	 * Saves the archive database to a file.
	 * 
	 * @throws IOException	thrown if the file cannot be read.
	 * @throws KeyStoreException	thrown if the key hasent been encrypted.
	 * @throws CertificateException	thrown if the key is null or not a public key.
	 * @throws ClassNotFoundException	if there is no class found.
	 */
	public void updateFile() throws IOException, KeyStoreException, CertificateException, ClassNotFoundException
	{
		ObjectED.encryptObject(archivedQuestions, FILENAME);
	}

	/**
	 * Sets text to a specified question.
	 * 
	 * @param questionNo	the number of the question.
	 * @param text	the text to be added to the question.
	 */
	public void setQuestionText(int questionNo, String text) {
		Question question = archivedQuestions.get(questionNo - 1);
		question.setQuestion(text);
	}

	/**
	 * Sets text to a specified answer.
	 * 
	 * @param questionNo	the number of the question.
	 * @param answerNo	the number of the answer in the question.
	 * @param text	the text to be added to the answer.
	 */
	public void setAnswerText(int questionNo, int answerNo, String text) {
		Question currentQuestion = archivedQuestions.get(questionNo-1);
		currentQuestion.setAnswerText(answerNo - 1, text);
	}

	/**
	 * Sets an answer to be correct or incorrect.
	 * 
	 * @param questionNo	the number of the question.
	 * @param answerNo	the number of the answer in the question.
	 * @param correct	true if the answer is correct or false if 
	 * 					the answer is incorrect.
	 */
	public void setIsAnswerCorrect(int questionNo, int answerNo, boolean correct) {
		Question currentQuestion = archivedQuestions.get(questionNo - 1);
		currentQuestion.setIsAnswerCorrect(answerNo - 1, correct);
	}

	/**
	 * Sets the number of answers to a specified question.
	 * 
	 * @param questionNo	the number of the question.
	 * @param numAnswers	the number of answers for a question.
	 */
	public void setNumAnswers(int questionNo, int numAnswers) {
		Question question = archivedQuestions.get(questionNo - 1);
		question.setNumAnswers(numAnswers);
	}

	/**
	 * Gets the total number of answers to a specified question. 
	 * 
	 * @param questionNo	the number of the question.
	 * @return	the number of answers to the question.
	 */
	public int getNumAnswers(int questionNo) {
		Question question = archivedQuestions.get(questionNo - 1);
		return question.getNoOfAnswers();
	}

	/**
	 * Gets the number of questions stored in the archive database.
	 * 
	 * @return the total number of questions.
	 */
	public int noOfArchivedQuestions() {
		return archivedQuestions.size() + 1;
	}

	/**
	 * Gets the text representation of a specfied answer in a 
	 * question.
	 * 
	 * @param questionNo	the number of the question.
	 * @param answerNo	the number of the answer to the question.
	 * @return	the text of the answer.
	 */
	public String getAnswerText(int questionNo, int answerNo) {
		Question currentQuestion = archivedQuestions.get(questionNo-1);
		return currentQuestion.getAnswerText(answerNo-1);
	}
	
	public String toString() {
		return archivedQuestions.toString();
	}

	/**
	 * Gets the text representation of a specified question.
	 * 
	 * @param questionNo	the number of the question.
	 * @return	the text of the question.	
	 */
	public String getQuestionText(int questionNo) {
		Question currentQuestion = archivedQuestions.get(questionNo-1);
		return currentQuestion.getQuestion();
	}

	/**
	 * Gets a boolean showing if the answer is correct or not.
	 * 
	 * @param questionNo	the number of the question.
	 * @param answerNo	the number of the answer to the question.
	 * @return	true if the answer is correct or false if the answer 
	 * 			is incorrect.
	 */
	public boolean isAnswerCorrect(int questionNo, int answerNo) {
		Question currentQuestion = archivedQuestions.get(questionNo-1);
		return currentQuestion.isAnswerCorrect(answerNo-1);
	}

	/**
	 * Removes a question from the archive.
	 * 
	 * @param questionNum	the number of a question.
	 */
	public void removeQuestion(int questionNum) {
		archivedQuestions.remove(questionNum - 1);
		
	}

	/**
	 * Adds a question to the archive.
	 * 
	 * @param questionText	the text representing the question.
	 * @param answer1Text	the text representing the first answer.
	 * @param answer2Text	the text representing the second answer.
	 * @param answer3Text	the text representing the third answer.
	 * @param answer4Text	the text representing the forth answer.
	 * @param correctAnswer		a number representing the correct answer.
	 */
	public void addQuestion(String questionText, String answer1Text, String answer2Text,String answer3Text,String answer4Text, int correctAnswer)
	{
		Question question = new Question();
		question.setQuestion(questionText);
		question.setAnswerText(0, answer1Text);
		question.setAnswerText(1, answer2Text);
		question.setAnswerText(2, answer3Text);
		question.setAnswerText(3, answer4Text);
		question.setIsAnswerCorrect(0, (correctAnswer == 1));
		question.setIsAnswerCorrect(1, (correctAnswer == 2));
		question.setIsAnswerCorrect(2, (correctAnswer == 3));
		question.setIsAnswerCorrect(3, (correctAnswer == 4));
		archivedQuestions.add(question);
	}

}
