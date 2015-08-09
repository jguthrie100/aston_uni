package tests;

import java.util.ArrayList;
import java.util.Random;

/**
 * The RandomGenerator class takes in questions, uses a 
 * random generator to change the order of those questions
 * and them return them.
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class RandomGenerator {
	
	private int questionLimit;
	private ArrayList<Question> questionDatabase;
	private Random numGen;
	private ArrayList<Question> randomisedQuestions;

	/**
	 * Contructs a random generator that stores an Arraylist of 
	 * questions and the limit to those questions.
	 * 
	 * @param questionLimit
	 * @param questionDatabase
	 */
	public RandomGenerator(int questionLimit, ArrayList<Question> questionDatabase) {
		this.questionLimit = questionLimit;
		this.questionDatabase = questionDatabase;
		randomisedQuestions = new ArrayList<Question>();
		numGen = new Random();
	}
	
	/**
	 * Randomises all the questions and returns them.
	 * 
	 * @return	the questions in a random order.
	 */
	public ArrayList<Question> randomiseQuestions() {
		for(int quesNum = 0; quesNum < questionLimit; quesNum++) {
			int randomNum = numGen.nextInt(questionDatabase.size());
			randomisedQuestions.add(questionDatabase.get(randomNum));
			questionDatabase.remove(randomNum);
		}
		return randomisedQuestions;
	}

}
