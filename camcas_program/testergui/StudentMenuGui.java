package testergui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import accounts.Account;
import tests.LoginGui;
import tests.Test;

/**
 * The StudentMenuGui is the secton that is shown whenever a student logs in
 * to their account. This part of the GUI is used to show a list of quizzes 
 * that are currently assigned to that student where they can start the quiz
 * or view the results.
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class StudentMenuGui implements ListSelectionListener {

	private JFrame frame;
	private Account studentAccount;
	private JPanel quizDisplay;
	private JTextArea quizDetails;
	private JList quizList;
	private DefaultListModel listModel;
	private JButton startQuiz;

	/**
	 * Creates the Student account menu and passes in that
	 * students account so their tests can be shown.
	 * 
	 * @param currentStudent	the current students account.
	 */
	public StudentMenuGui(Account currentStudent)  {
		
		studentAccount = currentStudent;
		
		frame = new JFrame("Quiz Menu");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(225, 325);

		((JPanel)frame.getContentPane()).setBorder(new EmptyBorder(6, 6, 6, 6));
		
		// List Model
		listModel = new DefaultListModel();
		for(int quizNum = 0; quizNum < studentAccount.getSize(); quizNum++) {
			listModel.addElement(studentAccount.getQuiz(quizNum));
		}
		
		quizList = new JList(listModel);
        quizList.setSelectionMode(
                ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		quizList.addListSelectionListener(this);
		// add array to list
		
		quizList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		//quizList.addListSelectionListener(this);
		quizList.setLayoutOrientation(JList.VERTICAL);
		quizList.setPreferredSize(new Dimension(200, 200));
		
		JScrollPane listScroller = new JScrollPane(quizList);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		listScroller.setPreferredSize(new Dimension(200, 200));
		
		// Quiz Details
		quizDetails = new JTextArea(" Quiz Details");
		quizDetails.setPreferredSize(new Dimension(130, 120));
		Border grayline = BorderFactory.createLineBorder(Color.GRAY);
		quizDetails.setBorder(grayline);;
		
		JPanel textAreaPanel = new JPanel();
		textAreaPanel.add(quizDetails);
		
		quizDisplay = new JPanel();
		quizDisplay.setLayout(new FlowLayout());
		quizDisplay.add(listScroller);
		
		// Buttons
		startQuiz = new JButton("Start Quiz");
		JButton logOff = new JButton("LogOff");
		
		startQuiz.setPreferredSize(new Dimension(130, 50));
		logOff.setPreferredSize(new Dimension(130, 50));
		
		startQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				try{
					Test currentTest = (Test) listModel.getElementAt(quizList.getSelectedIndex());
					int testNum = quizList.getSelectedIndex();
					if(currentTest.isFinished()) {
						new TesterResultsGui(currentTest.getStudentStatistics(), studentAccount, true);
					} else {
						try {
							new TesterGui(studentAccount.getName(), testNum, false);
							closeWindow();
						} catch(Exception o) {
						}
					}
				} catch(Exception l) {
					showMessage("Select a quiz before it can be taken or results viewed");
				}
			}
		});
		
		logOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				closeWindow();
				new LoginGui();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1, 5, 10));
		buttonPanel.add(startQuiz);
		buttonPanel.add(logOff);
		
		// Main Panel
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BorderLayout());
		sidePanel.setPreferredSize(new Dimension(130, 205));
		sidePanel.add(textAreaPanel, BorderLayout.NORTH);
		sidePanel.add(buttonPanel, BorderLayout.CENTER);
		
		JPanel main = new JPanel();
		main.setLayout(new FlowLayout());
		main.add(quizDisplay);
		main.add(sidePanel);
		
		frame.getContentPane().add(main);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Shows the details of a currently selected test. 
	 */
    public void valueChanged(ListSelectionEvent e) {
    //	ListSelectionModel quiz = (ListSelectionModel)e.getSource();
        if (e.getValueIsAdjusting() == false) {
        	Test selectedTest = (Test) listModel.getElementAt(quizList.getSelectedIndex());
        	if(selectedTest.isFinished()) {
        		startQuiz.setText("View Results");
        		quizDetails.setText(" Quiz Details\n\n\n Result: " + selectedTest.getResult() +
        				" \n No Of Questions: " + selectedTest.getNoOfQuestions());
        	} else {
        		startQuiz.setText("Start Quiz");
        		quizDetails.setText(" Quiz Details:\n Quiz Not Taken");
        	}
        }
    }
    
    /**
     * Closes the frame used for the student menu.
     */
	public void closeWindow() {
		frame.dispose();
	}
    
	/**
	 * Shows a handled error message informing the 
	 * user that a problem has occured.
	 * 
	 * @param msg	the message shown to the user
	 */
	private void showMessage(String msg)
	{
		JOptionPane.showMessageDialog(frame,
				msg,
				"",
				JOptionPane.PLAIN_MESSAGE);
	}
	
}
