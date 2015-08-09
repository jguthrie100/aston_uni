package testergui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import accounts.Account;
import accounts.AccountHandler;
import tests.Test;
import tests.Tester;

/**
 * The Tester Gui is used for the student to take the quiz, the TesterGui
 * displays the test questions and answers. This allows the student to take the
 * test by scrolling through and selecting an answer for each question.
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class TesterGui {
	
	// Main Window
	private JFrame frame;
	private JButton prev;
	private JButton next;
	private JButton finish;
	private JButton exit;
	private JRadioButton answer1;
	private JRadioButton answer2;
	private JRadioButton answer3;
	private JRadioButton answer4;
	private JRadioButton blank;
	private JLabel questionText;
	private JLabel questionNumber;
	private Tester tester;
	private int questionNum = 1;
	private Account account;
	private boolean tutorTester;
	private AccountHandler accountHandler;

	/**
	 * Contructs the TesterGui and uses the students current account to get 
	 * the test selected in the menu. This contructor is used for the 
	 * Student.
	 * 
	 * @param account account of user taking test. if teacher is taking the test then will
	 * be null
	 * @param test	the test stored in the students account.
	 * @throws IOException	thrown if the file holding the accounts cant be read.
	 * @throws ClassNotFoundException	thrown if the class cant be found.
	 */
	public TesterGui(String username, int testNum, boolean student) throws IOException, ClassNotFoundException {
		this.tutorTester = student;
		accountHandler = new AccountHandler();
		account = accountHandler.getAccount(username);
		tester = new Tester(username, account.getTest(testNum));
		runTester();
	}
	
	/**
	 * This constructor is used for the teacher. This one doesnt use a
	 * accountsHandler as the quiz results arent to be stored to the account.
	 * 
	 * @param username	the teachers username for the account.
	 * @param test	the test the teacher is to take.
	 * @param tutor	true if the user is a teacher or false if its a student.
	 */
	public TesterGui(String username, Test test, boolean tutor) {
		this.tutorTester = tutor;
		accountHandler = null;
		account = null;
		tester = new Tester(username, test);
		runTester();
	}

	/**
	 * Runs the tester by contructing the gui screen with all the 
	 * questions shown from a test.
	 */
	public void runTester() {

		frame = new JFrame("GUI Operator");
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		frame.setPreferredSize(new Dimension(600, 350));

		// sets up answer radio buttons and adds them to group
		// answergroup and adds them to answerPanel
		answer1 = new JRadioButton();
		answer1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		answer2 = new JRadioButton();
		answer2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		answer3 = new JRadioButton();
		answer3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				save();
			}
		});
		answer4 = new JRadioButton();
		answer4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		answer1.setPreferredSize(new Dimension(300, 23));
		answer2.setPreferredSize(new Dimension(300, 23));
		answer3.setPreferredSize(new Dimension(300, 23));
		answer4.setPreferredSize(new Dimension(300, 23));
		// Top left bottom right
		answer1.setBorder(new EmptyBorder(0,20,0,0));
		answer2.setBorder(new EmptyBorder(0,20,0,0));
		answer3.setBorder(new EmptyBorder(0,20,0,0));
		answer4.setBorder(new EmptyBorder(0,20,0,0));
		blank = new JRadioButton();
		ButtonGroup answerGroup = new ButtonGroup();
		answerGroup.add(answer1);
		answerGroup.add(answer2);
		answerGroup.add(answer3);
		answerGroup.add(answer4);
		answerGroup.add(blank);
		JPanel answerPanel = new JPanel(new GridLayout(4,1));
		answerPanel.add(answer1);
		answerPanel.add(answer2);
		answerPanel.add(answer3);
		answerPanel.add(answer4);

		//sets up buttons
		prev 		= new JButton("Previous");
		next 		= new JButton("Next");
		finish 		= new JButton("Finish");
		exit		= new JButton("Exit");
		next.setToolTipText("Next Question");
		prev.setToolTipText("Previous Question");
		finish.setToolTipText("Finish Test");
		exit.setToolTipText("Exit Quiz Without Saving");
		
		// Sets up TopPanel which holds the question number
		// and text
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		questionNumber = new JLabel("Question " + questionNum);
		questionText = new JLabel(); 
		topPanel.add(questionNumber, BorderLayout.NORTH);
		topPanel.add(questionText, BorderLayout.SOUTH);

		// BOTTOM PANEL creates two panels for buttons and adds them both
		// to the bottom Panel
		JPanel topButtonPanel = new JPanel(new FlowLayout());
		topButtonPanel.add(prev);
		topButtonPanel.add(next);
		JPanel bottomButtonPanel = new JPanel(new FlowLayout());
		bottomButtonPanel.add(finish);
		bottomButtonPanel.add(exit);
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(topButtonPanel, BorderLayout.NORTH);
		bottomPanel.add(bottomButtonPanel, BorderLayout.SOUTH);

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(answerPanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);

		prev.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					questionNum--;
					updateFields();
			}
		});

		next.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent e) throws IndexOutOfBoundsException {
					questionNum++;
					updateFields();
			}
		});

		finish.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!tester.isAnswered()) {
					showMessage(tester.warnings());
				}
				int n = JOptionPane.showConfirmDialog(
						frame,
						"Are You Sure You Want To Submit??","CONFIRM",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION){
					if(!tutorTester)
						showMessage("Quiz Submitted");
					frame.dispose();
					saveQuiz();
				}
			}
		});

		exit.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(
						frame,
						"Are You Sure Exit?\n\n" +
						"All information will be lost","CONFIRM",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION){
					frame.dispose();
					if(!tutorTester)
						new StudentMenuGui(account);
				}
			}
		});
		
		//loads answers user has specified (if any)
		updateFields();
		frame.pack();
		frame.setVisible(true); 
	}

	/**
	 * Saves the answer the student has selected for a question.
	 */
	private void save() {
		int answerNum = 0;
		if(answer1.isSelected()) answerNum = 1;
		else if(answer2.isSelected()) answerNum = 2;
		else if(answer3.isSelected()) answerNum = 3;
		else if(answer4.isSelected()) answerNum = 4;
		//do not save if no answer has been specified
		if (answerNum != 0)
			tester.setAnswerGiven(questionNum, answerNum);
	}

	/**
	 * Shows a message when an error occurs.
	 * 
	 * @param msg	the message shown when an exception 
	 * 				is thrown.
	 */
	private void showMessage(String msg)
	{
		JOptionPane.showMessageDialog(frame,
				msg,
				"",
				JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Updates the fields when the student switchs between questions.
	 */
	public void updateFields()
	{
		resetAnswers();
		questionNumber.setText("Question " + questionNum);
		questionText.setText(tester.getQuestionText(questionNum));
		int answerNum = tester.getNumAnswers(questionNum);

		answer1.setText(tester.getAnswerText(questionNum, 1));
		answer1.setSelected(tester.getAnswerGiven(questionNum) == 1);
		answer2.setText(tester.getAnswerText(questionNum, 2));
		answer2.setSelected(tester.getAnswerGiven(questionNum) == 2);
		answer3.setVisible(false);
		answer4.setVisible(false);

		if(answerNum >= 3) {
			answer3.setVisible(true);
			answer3.setText(tester.getAnswerText(questionNum, 3));
			answer3.setSelected(tester.getAnswerGiven(questionNum) == 3);
		}

		if(answerNum == 4) {
			answer4.setVisible(true);
			answer4.setSelected(tester.getAnswerGiven(questionNum) == 4);
			answer4.setText(tester.getAnswerText(questionNum, 4));
		}
		
		if(questionNum < tester.getSize()) 
			next.setEnabled(true);
		else
			next.setEnabled(false);
		
		if(questionNum == 1) 
			prev.setEnabled(false);
		else
			prev.setEnabled(true);
		
	}

	/**
	 * Clears all the answer radio buttons after one question has 
	 * been answered.
	 */
	public void resetAnswers() {
		answer1.setSelected(false);
		answer2.setSelected(false);
		answer3.setSelected(false);
		answer4.setSelected(false);
		blank.setSelected(true);
	}
	
	/**
	 * Saves the current test and works out the students score.
	 */
	public void saveQuiz() {
		try {
			tester.setIsFinished(true);
			if (!tutorTester) {
				//update accounts file with newly finished test
				accountHandler.updateFile();
			}
			new TesterResultsGui(tester.getStudentStatistics(), account, tutorTester);
		} catch(Exception s) {
			showMessage("Results wasnt saved");
		}
	}

}