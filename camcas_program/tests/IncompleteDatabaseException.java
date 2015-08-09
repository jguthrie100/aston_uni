package tests;

/**
 * The Answer class is used to store a particular answer 
 * which will be used as part of a question in the quiz. 
 * Theres also an identifier as weather this particular 
 * answer is correct or in correct.
 * 
 * @author 		Tom
 * @version		1.0	
 */
public class IncompleteDatabaseException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates an IncompleteDatabaseException where msg
	 * is shown in the message whenever the exception is 
	 * thrown.
	 */	
	public IncompleteDatabaseException(String msg)
	{
		super(msg);
	}

}
