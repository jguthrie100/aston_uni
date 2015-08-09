package tests;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import file.ObjectED;

/**
 * The QuizDatabase class holds all the question databases
 * where each holds questions for a particular topic.
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class QuizDatabase {
	
	private static final String FILENAME = "QUIZES.QDB";
	private ArrayList<QuestionDatabase> quizDatabase;
	
	/**
	 * Contructs a quiz database adding the question databases loaded from 
	 * the file. If there are no question databases a new quiz database is 
	 * created to allow new question databaes to be saved.
	 * 
	 * @throws ClassNotFoundException	thrown if theres no class detected. 
	 * @throws IOException 	thrown if theres a problem reading the file.
	 */
	public QuizDatabase () throws IOException, ClassNotFoundException {
		try {
			loadQuizes();
		} catch (IOException e) {
			quizDatabase = new ArrayList<QuestionDatabase>();
		} catch (ClassNotFoundException e) {
			quizDatabase = new ArrayList<QuestionDatabase>();
		}
	}
	
	/**
	 * Loads all the question databases from the file, if not then a 
	 * new quiz database is created.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void loadQuizes() throws IOException, ClassNotFoundException
	{
		try {
			quizDatabase = (ArrayList<QuestionDatabase>) ObjectED.decryptObject(FILENAME);
		} catch(Exception we) {
			quizDatabase = new ArrayList<QuestionDatabase>();
		}
		if(quizDatabase == null)
			quizDatabase = new ArrayList<QuestionDatabase>();
	}
	
	/**
	 * Saves the quiz database to a file.
	 * 
	 * @throws IOException	thrown if the file cannot be read.
	 * @throws KeyStoreException	thrown if the key hasent been encrypted.
	 * @throws CertificateException	thrown if the key is null or not a public key.
	 * @throws ClassNotFoundException	if there is no class found.
	 */
	public void updateQuizes() throws IOException, KeyStoreException, CertificateException, ClassNotFoundException
	{
		ObjectED.encryptObject(quizDatabase, FILENAME);
	}
	
	/**
	 * Adds a question database to the quiz database.
	 * 
	 * @param quiz	the question database to be added.
	 */
	public void addQuiz(QuestionDatabase quiz) {
		QuestionDatabase alteredQuiz = null;
		for(QuestionDatabase questionDatabase : quizDatabase) {
			if(quiz.equals(questionDatabase)) {
				alteredQuiz = quiz;
				questionDatabase = alteredQuiz;
				break;
			}
		}
		if(alteredQuiz == null) {
			quizDatabase.add(quiz);
		}	
	}
	
	/**
	 * Gets a question database from the quiz database.
	 * 
	 * @param topic		the topic of a particular question database.
	 * @return	the question database matching the topic.
	 */
	public QuestionDatabase getQuiz(String topic) {
		QuestionDatabase quiz = null;
		for(QuestionDatabase currentQuiz : quizDatabase) {
			if(topic.equals(currentQuiz.toString())) {
				quiz = currentQuiz;
				break;
			}
		}
		return quiz;
	}
	
	/**
	 * Gets a question database from the quiz database.
	 * 
	 * @param quizNum	the number of the question database.
	 * @return	the question database.
	 */
	public QuestionDatabase getQuiz(int quizNum) {
		return quizDatabase.get(quizNum);
	}
	
	/**
	 * Deletes a specified question database.
	 * 
	 * @param quizNum	the number of the question database.
	 */
	public void deleteQuiz(int quizNum) {
		quizDatabase.remove(quizNum);
	}
	
	/**
	 * Gets the size of the quiz database.
	 * 
	 * @return the number of question databases.
	 */
	public int getSize() {
		return quizDatabase.size();
	}
	
	/**
	 * Gets the all of question databases in the quiz database.
	 * 
	 * @return	the ArrayList of question databases.
	 */
	public ArrayList<QuestionDatabase> getQuizDatabase() {
		return quizDatabase;
	}

}
