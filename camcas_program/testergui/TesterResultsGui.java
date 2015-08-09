package testergui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import accounts.Account;

/**
 * The TesterResultsGui is to display the students statistics on a
 * text area after the student submits the test taken. The statistics
 * show the question that the student has answered, if it was correct 
 * or incorrect and if incorrect the correct answer is shown.
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class TesterResultsGui {
	// Main Window
	private JFrame frame;
	private Account student;
	private boolean tutorTester;
	
	/**
	 * Contructs a TesterResultsGui where the students results can 
	 * be displayed.
	 * 
	 * @param statistics	the students results.
	 * @param student	the students account.
	 * @param tutorTester	true if the current user is a student or
	 * 						false if the user is a teacher.
	 */
	public TesterResultsGui(String statistics, Account student, boolean tutorTester){
		showResults(statistics);
		this.student = student;
		this.tutorTester = tutorTester;
	}
	
	/**
	 * Shows the students results from the test that they 
	 * have taken. Also the questions that the student 
	 * have answered is also shown.
	 * 
	 * @param statistics	the students statistics.
	 */
	private void showResults(String statistics) {
		frame = new JFrame("Quiz Results");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JTextArea results = new JTextArea();
		//results.setPreferredSize(new Dimension(100, 200));

		JScrollPane resultsPane = new JScrollPane(results);
		resultsPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		resultsPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		resultsPane.setPreferredSize(new Dimension(250, 250));
		
		results.setEditable(false);
		results.setText(statistics);
		
		JButton closeQuiz = new JButton("Close Quiz");
		
		closeQuiz.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent e) throws IndexOutOfBoundsException {
				closeWindow();
				if(!tutorTester) {
					new StudentMenuGui(student);
				}
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(closeQuiz);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(resultsPane, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Closes the current student results window.
	 */
	public void closeWindow() {
		frame.dispose();
	}

}