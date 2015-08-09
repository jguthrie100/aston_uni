package tests;

import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;

/**
 * The QuestionDatabase class is used to store all the required  
 * questions and a topic name that the questions represent.
 * 
 * @author 		Mesh, Tom
 * @version		1.0	
 */
public class QuestionDatabase implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<Question> questions;
	private String topic;
	private int noOfQuestionsForTest;
	
	/**
	 * Contructs a new database of questions and assigns a topic.
	 * 
	 * @param topic		the name of the topic.
	 * @param noOfQuestions		the total number of questions.
	 */
	public QuestionDatabase(String topic,int noOfQuestions) {
		questions = new ArrayList<Question>();
		this.topic = topic;
		this.noOfQuestionsForTest = noOfQuestions;
		for (int n = 0; n < noOfQuestions; n++)
			questions.add(new Question());
	}
	
	/**
	 * Gets the name of the topic.
	 * 
	 * @return String 		the topic name.
	 */
	public String toString() {
		return topic;
	}
	
	/**
	 * Sets the name of the topic.
	 * 
	 * @param topic 		the topic name.
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	/**
	 * Gets the text of the question from the arraylist.
	 * 
	 * @param questionNo 	the number of the question
	 * @return String 		the text of the question.
	 */
	public String getQuestionText(int questionNo) {
		Question currentQuestion = questions.get(questionNo-1);
		return currentQuestion.getQuestion();
	}
	
	/**
	 * Sets the text of the question after getting it from 
	 * the arraylist.
	 * 
	 * @param questionNo 	the number of the question.
	 * @param text		 	the text of the question.
	 */
	public void setQuestionText(int questionNo, String text) {
		Question question = questions.get(questionNo-1);
		question.setQuestion(text);
	}
	
	/**
	 * Gets the text of the answer from the question that 
	 * is returned from the arraylist.
	 * 
	 * @param questionNo 	the number of the question.
	 * @param answerNo		the number of the answer.
	 * @return String 		the text of the answer.
	 */
	public String getAnswerText(int questionNo, int answerNo) {
		Question currentQuestion = questions.get(questionNo-1);
		return currentQuestion.getAnswerText(answerNo-1);
	}
	
	/**
	 * Sets the text of the answer from the question that 
	 * is returned from the arraylist.
	 * 
	 * @param questionNo 	the number of the question.
	 * @param answerNo		the number of the answer.
	 * @param text		 	the text of the question.
	 */
	public void setAnswerText(int questionNo, int answerNo, String text) {
		Question currentQuestion = questions.get(questionNo-1);
		currentQuestion.setAnswerText(answerNo-1, text);
	}
	
	/**
	 * Gets a boolean that specifies weather the current 
	 * answer is correct or incorrect.
	 * 
	 * @param questionNo 	the number of the question.
	 * @param answerNo		the number of the answer.
	 * @return boolean 		true if the answer is correct or
	 * 						false if the answer is incorrect.
	 */
	public boolean isAnswerCorrect(int questionNo, int answerNo) {
		Question currentQuestion = questions.get(questionNo-1);
		return currentQuestion.isAnswerCorrect(answerNo-1);
	}
	
	/**
	 * Sets a boolean that sets the specified current 
	 * answer to be correct or incorrect.
	 * 
	 * @param questionNo 	the number of the question.
	 * @param answerNo		the number of the answer.
	 * @param correct		true if the answer is correct or
	 * 						false if the answer is incorrect.
	 */
	public void setIsAnswerCorrect(int questionNo, int answerNo, boolean correct) {
		Question currentQuestion = questions.get(questionNo - 1);
		currentQuestion.setIsAnswerCorrect(answerNo - 1, correct);
	}
	
	/**
	 * Gets the number of questions associated with this 
	 * test.
	 * 
	 * @return int 			the number of questions.
	 */
	public int noOfQuestionsForTest() {
		return noOfQuestionsForTest;
	}
	
	/**
	 * Sets the number of questions for this test.
	 * 
	 * @param noOfQuestions 	the number of questions.
	 */
	public void setNoOfQuestionsForTest(int noOfQuestions) {
		noOfQuestionsForTest = noOfQuestions;
	}
	
	/**
	 * Adds a question to the question database.
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
		Question q = new Question();
		q.setQuestion(questionText);
		q.setAnswerText(0, answer1Text);
		q.setAnswerText(1, answer2Text);
		q.setAnswerText(2, answer3Text);
		q.setAnswerText(3, answer4Text);
		q.setIsAnswerCorrect(0, (correctAnswer==1));
		q.setIsAnswerCorrect(1, (correctAnswer==2));
		q.setIsAnswerCorrect(2, (correctAnswer==3));
		q.setIsAnswerCorrect(3, (correctAnswer==4));
		questions.add(q);
	}
	
	/**
	 * Gets a list of all the questions from this QuestionDatabase.
	 * 
	 * @return questionList 	the questions from the database.
	 */
	
	public ArrayList<Question> getTestQuestions() {
		ArrayList<Question> questionList = new ArrayList<Question>();
		Random rnd = new Random();
			for(int currentQ = 0; currentQ < noOfQuestionsForTest; currentQ++) {
				questionList.add(questions.get(rnd.nextInt(questions.size())));
			}
		return questionList;
	}
	
	/**
	 * Removes a specified question from the database.
	 * 
	 * @param questionNo	the number of the question.
	 */
	public void removeQuestion(int questionNo)
	{
		questions.remove(questionNo-1);
		setNoOfQuestionsForTest(questions.size());
	}

	/**
	 * Checks if the questions and answers of the database have 
	 * been completed.
	 * 
	 * @return	true if all the questions and answers arent complete 
	 * 			or false if there is text assigned to the questions 
	 * 			and answers.
	 */
	public boolean incomplete(){
		for (Question question : questions)
		{
			if (question.incomplete())
				return true;
		}
		return false;
	}
	
	/**
	 * Gets a string representation of the questions and answers that 
	 * havent been completed.
	 * 
	 * @return	a String showing the a list of the questions and answers 
	 * 			that have no text representation to them.
	 */
	public String problems(){
		String toReturn = "\n";
		int n = 0;
		for (Question question : questions)
		{
			if (question.incomplete())
				toReturn += "Question["+(n + 1)+"] --->" + question.problems() + '\n';
			n++;
		}
		return toReturn + "Is/Are Missing\n\n" +
				"Please complete the Database before attempting to save it.";
	}
	
	/**
	 * Adds more questions to the database.
	 * 
	 * @param questionsNum	the number of more questions to be added.
	 */
	public void addMoreQuestions(int questionsNum) {
		int extraQuestions = noOfQuestionsForTest - questionsNum;
		extraQuestions = Math.abs(extraQuestions);
		for (int n = 0; n < extraQuestions; n++) {
			questions.add(new Question());
		}
		setNoOfQuestionsForTest(questions.size());
	}
	
	/**
	 * Sets the number of answers to a particular question.
	 * 
	 * @param questionNo	the number of the question.
	 * @param numAnswers	the number of answers for the question.
	 */
	public void setNumAnswers(int questionNo, int numAnswers) {
		Question question = questions.get(questionNo - 1);
		question.setNumAnswers(numAnswers);
	}
	
	/**
	 * Gets the number of answers to a particular question.
	 * 
	 * @param questionNo	the number of the question.
	 * @return	a number representing the amount of answers to 
	 * 			the question.
	 */
	public int getNumAnswers(int questionNo) {
		Question question = questions.get(questionNo - 1);
		return question.getNoOfAnswers();
	}
	
}
