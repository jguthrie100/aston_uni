package tests;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;

/**
 * The TestGenerator class is used to create, edit or delete
 * questions as well as archiving questions by sending it
 * to the archive database.
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class TestGenerator {
	
	private QuizDatabase quizDatabase;
	private QuestionDatabase questionDatabase;
	private QuestionArchive archiveDatabase;
	
	/**
	 * Contructs a test geneartor where a topic is assigned.
	 * 
	 * @param topic		the topic for the questions.
	 * @throws IOException		thrown if theres a problem reading the 
	 * 							questions in the quiz database.
	 * @throws ClassNotFoundException	thrown if there is no class 
	 * 									found in the quiz database.
	 */
	public TestGenerator(String topic) throws IOException, ClassNotFoundException {
		quizDatabase = new QuizDatabase();
		archiveDatabase = new QuestionArchive();
	}

	/**
	 * Adds a new question to the question database.
	 * 
	 * @param questionText	the text representation of the new question.
	 * @param answer1Text	the text representation of the first new answer.
	 * @param answer2Text	the text representation of the second new answer.
	 * @param answer3Text	the text representation of the third new answer.
	 * @param answer4Text	the text representation of the forth new answer.
	 * @param correctAnswer	the number representing the correct answer.
	 */
	public void addQuestion(String questionText, String answer1Text, String answer2Text,String answer3Text,String answer4Text, int correctAnswer) {
		questionDatabase.addQuestion(questionText, answer1Text, answer2Text, answer3Text, answer4Text, correctAnswer);
	}
	
	/**
	 * Gets the text of the specified question.
	 * 
	 * @param questionNo	the number of the question.
	 * @return	the text representation of the question.
	 */
	public String getQuestionText(int questionNo)
	{
		if (questionDatabase!=null)
			return questionDatabase.getQuestionText(questionNo);
		else return null;
	}
	
	/**
	 * Sets text to a specified question.
	 * 
	 * @param questionNo	the number of the question.
	 * @param text	the text to be set to the question.
	 */
	public void setQuestionText(int questionNo, String text)
	{
		if (questionDatabase!=null)
			questionDatabase.setQuestionText(questionNo,text);
	}
	
	/**
	 * Gets the text of an answer from a specified question.
	 * 
	 * @param questionNo	the number of the question.
	 * @param answerNo	the number of the answer from the question.
	 * @return the text of the answer or null if there is no question 
	 * 		   database.
	 */
	public String getAnswerText(int questionNo, int answerNo)
	{
		if (questionDatabase != null)
			return questionDatabase.getAnswerText(questionNo, answerNo);
		else return null;
	}
	
	/**
	 * Sets the text to an answer of a specific question.
	 * 
	 * @param questionNo	the number of the question.
	 * @param answerNo	the number of the answer from the question.
	 * @param text	the text to be set to the answer.
	 */
	public void setAnswerText(int questionNo, int answerNo, String text)
	{
		if (questionDatabase != null)
			questionDatabase.setAnswerText(questionNo, answerNo, text);
	}
	
	/**
	 * Checks if an answer from a specific question is correct or not.
	 * 
	 * @param questionNo	the number of the question.
	 * @param answerNo	the number of the answer from the question.
	 * @return	true if the answer is correct or false if otherwise.
	 */
	public Boolean isAnswerCorrect(int questionNo, int answerNo)
	{
		if (questionDatabase != null)
			return questionDatabase.isAnswerCorrect(questionNo, answerNo);
		else return null;
	}
	
	/**
	 * Sets the answer from a specific question to be correct or incorrect.
	 * 
	 * @param questionNo	the number of the question.
	 * @param answerNo	the number of the answer from the question.
	 * @param correct	true if the answer is to be correct or false if 
	 * 					it is to be incorrect.
	 */
	public void setIsAnswerCorrect(int questionNo, int answerNo, boolean correct)
	{
		if (questionDatabase != null)
			questionDatabase.setIsAnswerCorrect(questionNo, answerNo, correct);
	}

	/**
	 * Gets the number of questions for a test.
	 * 
	 * @return	a number representing the total number of questions.
	 */
	public Integer noOfQuestionsForTest()
	{
		if (questionDatabase != null)
			return questionDatabase.noOfQuestionsForTest();
		else return null;
	}
	
	/**
	 * Sets the number of questions that there is to be for a test.
	 * 
	 * @param noOfQuestions	the number of question for the test
	 */
	public void setNoOfQuestionsForTest(int noOfQuestions)
	{
		if (questionDatabase != null)
			questionDatabase.setNoOfQuestionsForTest(noOfQuestions);
	}
	
	/**
	 * Gets a question database from the quiz database.
	 * 
	 * @param topic	the topic name for the question database.
	 * @throws IOException	thrown when the file cannot be found.
	 * @throws ClassNotFoundException	thrown when the class is not found.
	 */
	public void openQuestionDatabase(String topic) throws IOException, ClassNotFoundException
	{
		questionDatabase = quizDatabase.getQuiz(topic);
	}
	
	/**
	 * Add the question database to the quiz database and saves it to a file.
	 * 
	 * @throws IOException	thrown when the file cant be read.
	 * @throws IncompleteDatabaseException	thrown when all the questions havent 
	 * 										been completed.
	 */
	public void saveQuestionDatabase() throws IOException, IncompleteDatabaseException
	{
		if (questionDatabase.incomplete())
			throw new IncompleteDatabaseException(questionDatabase.problems());
		try {
			quizDatabase.addQuiz(questionDatabase);
			quizDatabase.updateQuizes();
		} catch(Exception q) {
			System.out.println("TestGenerator: saveQuestionDatabase() quiz not saved");
		}
	}
	
	/**
	 * Creates a new question database with the topic and number
	 * of questions assigned to it.
	 * 
	 * @param topic		the name of the topic for the questions database.
	 * @param noOfQuestions		the number of questions for the database.
	 */
	public void createNewQuestionDatabase(String topic,  int noOfQuestions)
	{   
		questionDatabase = new QuestionDatabase(topic, noOfQuestions);
	}

	/**
	 * Removes a question from the question database.
	 * 
	 * @param questionNo	the number of a question.
	 */
	public void removeQuestion(int questionNo)
	{   
		questionDatabase.removeQuestion(questionNo);
	}
	
	/**
	 * Assigns a new QuestionArchive object to the current one.
	 */
	public void reset() {
		archiveDatabase = new QuestionArchive();
	}
	
	/**
	 * Adds more questions to the question database.
	 * 
	 * @param numQuestions	the number of question to add to the database.
	 */
	public void addMoreQuestions(int numQuestions) {
		questionDatabase.addMoreQuestions(numQuestions);
	}
	
	/**
	 * Adds a question to the archive database.
	 * 
	 * @param questionText	the text representing the question.
	 * @param answer1Text	the text representing the first answer.
	 * @param answer2Text	the text representing the second answer.
	 * @param answer3Text	the text representing the third answer.
	 * @param answer4Text	the text representing the forth answer.
	 * @param correctAnswer		a number representing the correct answer.
	 */
	public void archiveQuestion(String questionText, String answer1Text, String answer2Text,String answer3Text,String answer4Text, int correctAnswer) {
		archiveDatabase.addQuestion(questionText, answer1Text, answer2Text, answer3Text, answer4Text, correctAnswer);
	}
	
	/**
	 * Saves the archive database to file.
	 * 
	 * @throws IOException	thrown if the file cannot be read.
	 * @throws KeyStoreException	thrown if the key hasent been encrypted.
	 * @throws CertificateException	thrown if the key is null or not a public key.
	 * @throws ClassNotFoundException	if there is no class found.
	 */
	public void updateArchiveDatabase() throws KeyStoreException, CertificateException, IOException, ClassNotFoundException {
		archiveDatabase.updateFile();
	}
	
	/**
	 * Sets the number of answers to a specific question.
	 * 
	 * @param questionNo	the number of the question.
	 * @param numAnswers	the number of answers for the question.
	 */
	public void setNumAnswers(int questionNo, int numAnswers) {
		questionDatabase.setNumAnswers(questionNo, numAnswers);
	}
	
	/**
	 * Gets the number of answers to a specific question
	 * 
	 * @param questionNo	the number of the question.
	 * @return	the total number of answers for the question.
	 */
	public int getNumAnswers(int questionNo) {
		return questionDatabase.getNumAnswers(questionNo);
	}
	
}
