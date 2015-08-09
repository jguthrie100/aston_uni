package tests;

import java.io.Serializable;

/**
 * The Answer class is used to store a particular answer 
 * which will be used as part of a question in the quiz. 
 * Theres also an identifier as weather this particular 
 * answer is correct or in correct.
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class Answer implements Serializable{

	private static final long serialVersionUID = 1L;
	private String text;
	private boolean correct;

	/**
	 * Creates a blank answer where correct is set to false.
	 */
	public Answer() {
		text = "";
		correct = false;
	}

	/**
	 * Allows an answer to be assigned to the text variable.
	 * 
	 * @param answer 	text used for the answer.
	 */
	public void setAnswer(String answer) {
		text = answer;
	}

	/**
	 * Gets the text making up the answer.
	 * 
	 * @return text 	the answer for a particular question.
	 */
	public String getAnswer() {
		return text;
	}

	/**
	 * This allows the answer to be identified as either 
	 * being right or wrong.
	 * 
	 * @param correct	identifies the answer as being right
	 * 					if true or wrong if false.
	 */
	public void setIsCorrect(boolean correct) {
		this.correct = correct;
	}

	/**
	 * Returns a boolean determining weather this answer is 
	 * a correct answer in the quiz or not.
	 * 
	 * @return true 	if this answer is correct or 
	 *                  false if its incorrect.
	 */
	public boolean isCorrect() {
		return correct;
	}

}
