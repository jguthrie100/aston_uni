package testgeneratorgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import testergui.TesterGui;
import tests.Question;
import tests.QuestionDatabase;
import tests.QuizDatabase;
import tests.Test;

import accounts.Account;
import accounts.AccountHandler;
import accounts.AccountType;

/**
 * The QuizGui is used to show the students accounts and 
 * the tests that are assigned to each student account
 * that is selected. The tests can be added and removed 
 * from the students account as well as running the tests.
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class QuizGui implements ListSelectionListener {

	private JFrame frame;
	private JPanel quizDisplay;
	private JTextArea quizDetails;
	private JList quizList;
	private JList studentList;
	private DefaultListModel listModel;
	private AccountHandler studentAccounts;
	private QuizDatabase quizDatabase;
	private JList studentQuizList;
	private JLabel numQuestionsLabel;
	private JFrame addQuizFrame;
	private DefaultListModel quizListModel;
	private JFrame removeQuizFrame;
	private JButton removeQuiz;
	private Account addQuizStudent;
	private JList quizAddList;
	private JFrame viewResultsframe;
	private JTextArea quizDetailsText;
	private JTextField numQuestionsText;
	private JLabel maxQuestionsLabel;
	private JLabel maxNumQuestionsLabel;
	private Account studentRemoveQuiz;

	/**
	 * Contructs a new QuizGui.
	 * 
	 * @throws IOException	thrown if there is a problem reading the file.
	 * @throws ClassNotFoundException	thrown if there is no class found.
	 */
	public QuizGui() throws IOException, ClassNotFoundException  {
		createManager();
	}

	/**
	 * Contructs the quiz section used to manage tests for 
	 * each student account shown in the section.
	 * 
	 * @return	the quiz section.
	 * @throws IOException	thrown when there is a problem accessing the
	 * 						file.	
	 * @throws ClassNotFoundException	thrown when the class is not found.
	 */
	public JComponent createManager() throws IOException, ClassNotFoundException {

		studentAccounts = new AccountHandler();
		this.quizDatabase = new QuizDatabase();

		frame = new JFrame();
		frame.setLocation(225, 325);

		((JPanel)frame.getContentPane()).setBorder(new EmptyBorder(6, 6, 6, 6));

		// List Model
		listModel = new DefaultListModel();
		for(int aNum = 0; aNum < studentAccounts.getSize(); aNum++) {
			Account currentAccount = studentAccounts.getAccount(aNum);
			if(currentAccount.getAccountType()==AccountType.STUDENT) {
				listModel.addElement(currentAccount);
			}
		}

		// LIST SHOWING STUDENTS
		studentList = new JList(listModel);
		studentList.setSelectionMode(
				ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		studentList.addListSelectionListener(this);

		studentList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		studentList.setLayoutOrientation(JList.VERTICAL);

		JScrollPane listScroller = new JScrollPane(studentList);
		listScroller.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScroller.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScroller.setPreferredSize(new Dimension(210, 280));

		JPanel listScrollerPanel = new JPanel();
		listScrollerPanel.add(listScroller);

		// Quiz Details
		quizDetails = new JTextArea("");
		quizDetails.setPreferredSize(new Dimension(180, 120));
		quizDetails.setEditable(false);
		Border grayline = BorderFactory.createLineBorder(Color.GRAY);
		quizDetails.setBorder(grayline);

		JPanel quizDetailsPanel = new JPanel(new BorderLayout());
		quizDetailsPanel.setPreferredSize(new Dimension(180, 130));
		quizDetailsPanel.add(quizDetails, BorderLayout.NORTH);

		studentQuizList = new JList();
		studentQuizList.setSelectionMode(
				ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		studentQuizList.addListSelectionListener(this);

		studentQuizList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		studentQuizList.setLayoutOrientation(JList.VERTICAL);
		studentQuizList.setPreferredSize(new Dimension(180, 120));

		JScrollPane studentQuizListScroller = new JScrollPane(studentQuizList);
		studentQuizListScroller.setPreferredSize(new Dimension(280, 120));

		JPanel studentQuizListPanel = new JPanel(new BorderLayout());
		studentQuizListPanel.add(studentQuizList, BorderLayout.NORTH);

		// Control Buttons

		JButton addQuiz = new JButton("Add/Run Quiz");
		JButton viewResults = new JButton("View Results");
		JButton refreshQuiz = new JButton("Refresh List");
		JButton removeQuiz = new JButton("Remove Quiz");

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout( 4, 1, 10, 10));
		buttonPanel.add(addQuiz);
		buttonPanel.add(viewResults);
		buttonPanel.add(refreshQuiz);
		buttonPanel.add(removeQuiz);

		// Adds students quizes and quizes to a Panel

		JPanel quizListsPanel = new JPanel();
		quizListsPanel.setLayout(new GridLayout( 2, 1, 0, 5));
		quizListsPanel.add(quizDetailsPanel);
		quizListsPanel.add(buttonPanel);

		// ADDS ALL LISTS TO PANEL
		quizDisplay = new JPanel();
		quizDisplay.setLayout(new FlowLayout());
		quizDisplay.add(listScrollerPanel);
		quizDisplay.add(quizListsPanel);

		addQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!studentList.isSelectionEmpty()) {
					Account selectedStudent = (Account) studentList.getSelectedValue();
					addQuizSection(selectedStudent);
					refreshAccounts();
				} else {
					showMessage("A Student Must Be Selected Before a Quiz Can Be Added or Run.");
				}
			}
		});

		refreshQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshAccounts();
			}
		});

		removeQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!studentList.isSelectionEmpty()) {
					Account selectedStudent = (Account) studentList.getSelectedValue();
					removeQuiz(selectedStudent);
					refreshAccounts();
				} else {
					showMessage("A Student Must Be Selected Before a Quiz Can Be Removed.");

				}
			}
		});

		viewResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!studentList.isSelectionEmpty()) {
					Account selectedStudent = (Account) studentList.getSelectedValue();

					if (hasResultsToDisplay(selectedStudent))
						viewResults(selectedStudent);
					else
						showMessage("Selected Student has not marked any Quizes as finished");
				} else {
					showMessage("A Student Must Be Selected Before the Students Results Can Be Viewed");
				}
			}
		});

		// Main Panel
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.setPreferredSize(new Dimension(400, 260));
		main.add(quizDisplay, BorderLayout.NORTH);

		frame.getContentPane().add(main);
		frame.pack();

		return main;
	}


	/**
	 * Checks the that there are results to be displayed.
	 * 
	 * @param student	the students account
	 * @returna		true if student has finished a test 
	 * 				false otherwise.
	 */
	public boolean hasResultsToDisplay(Account student)
	{
		for(int aNum = 0; aNum < student.getSize(); aNum++)
			if (student.getQuiz(aNum).isFinished())
			{
				return true;
			}
		return false;
	}

	/**
	 * Saves the students accounts to the file.
	 */
	public void refreshAccounts() {
		try {
			studentAccounts.updateFile();
		} catch(Exception f) {
			showMessage("Accounts has not been updated.");
		}
		getListOfQuizes();
	}

	/**
	 * Converts the ArrayList of students accounts to an Array.
	 * 
	 * @return	the array of student accounts.
	 */
	public Account[] changeToArray(){
		Account[] accounts = new Account[studentAccounts.getSize()];
		for(int accountNum = 0; accountNum < accounts.length; accountNum++) {
			accounts[accountNum] = studentAccounts.getAccount(accountNum);
		}
		return accounts;
	}

	/**
	 * Used to change the list of tests when a different
	 * student is selected.
	 */
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			getListOfQuizes();
		}
	}

	/**
	 * Used to change the list of tests when a different
	 * student is selected.
	 */
	public void getListOfQuizes() {
		if (studentList.getSelectedIndex() != -1) {
			Account currentStudent = (Account) listModel.getElementAt(studentList.getSelectedIndex());
			String quizList = "";
			for(int aNum = 0; aNum < currentStudent.getSize(); aNum++) {
				Test test = currentStudent.getQuiz(aNum);
				quizList += test.toString() + "\n";
			}
			quizDetails.setText(" Students Quizes\n\n " + quizList);
		}
		else showMessage("A Student Must Be Selected There List Of Quizes Can Be Refreshed");
	}

	/**
	 * Shows an error message whenver an error occurs.
	 * 
	 * @param msg	the message shown when theres an exception.
	 */
	public void showMessage(String msg)
	{
		JOptionPane.showMessageDialog(frame,
				msg,
				"",
				JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Creates the add quiz window used to add and run tests.
	 * 
	 * @param selectedStudent	the selected students account.
	 */
	public void addQuizSection(Account selectedStudent) {

		addQuizStudent = selectedStudent;

		// Accounts modifications
		try {
			quizDatabase = new QuizDatabase();
		} catch(Exception e) {
			showMessage("Accounts could not be loaded");
		}

		addQuizFrame = new JFrame("Quiz Generator");
		addQuizFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addQuizFrame.setLocation(225, 325);
		addQuizFrame.getContentPane().setLayout(new BorderLayout());

		((JPanel)addQuizFrame.getContentPane()).setBorder(new EmptyBorder(6, 6, 6, 6));

		JPanel mainPanel 	= new JPanel();

		JButton addQuiz = new JButton("Add Quiz");
		JButton runQuiz = new JButton("Run Quiz");
		JButton quit = new JButton("Cancel");

		JPanel bottomPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addQuiz);
		buttonPanel.add(runQuiz);
		buttonPanel.add(quit);

		// LIST SHOWING AVAILABLE QUIZES

		quizListModel = new DefaultListModel();
		for(int aNum = 0; aNum < quizDatabase.getSize(); aNum++) {
			quizListModel.addElement(quizDatabase.getQuiz(aNum));
		}

		quizAddList = new JList(quizListModel);
		quizAddList.setSelectionMode(
				ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		quizAddList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		quizAddList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					QuestionDatabase selectedQuiz = (QuestionDatabase) quizListModel.getElementAt(quizAddList.getSelectedIndex());
					String questionCount = Integer.toString(selectedQuiz.noOfQuestionsForTest());
					maxNumQuestionsLabel.setText(questionCount);
				}
			}
		});
		quizAddList.setLayoutOrientation(JList.VERTICAL);
		quizAddList.setPreferredSize(new Dimension(250, 150));

		JScrollPane quizListScroller = new JScrollPane(quizAddList);
		quizListScroller.setPreferredSize(new Dimension(300, 250));

		JPanel quizListPanel = new JPanel();
		quizListPanel.add(quizListScroller);

		// Adds label and text field to show number of questions
		// in a selected quiz.
		maxQuestionsLabel = new JLabel("No of Questions from Database:");
		maxNumQuestionsLabel = new JLabel();

		JPanel maxQuizQuestions = new JPanel();
		maxQuizQuestions.setLayout(new FlowLayout());
		maxQuizQuestions.add(maxQuestionsLabel);
		maxQuizQuestions.add(maxNumQuestionsLabel);

		// Creates label and text field to allow number of questions 
		// to be set for a test
		numQuestionsLabel = new JLabel("No of Questions for Test: ");
		numQuestionsText = new JTextField();
		numQuestionsText.setColumns(3);

		JPanel numQuestions = new JPanel();
		numQuestions.setLayout(new FlowLayout());
		numQuestions.add(numQuestionsLabel);
		numQuestions.add(numQuestionsText);

		mainPanel.setLayout(new BorderLayout());
		bottomPanel.setLayout(new BorderLayout());

		JPanel addQuizOptions = new JPanel(new BorderLayout());
		addQuizOptions.add(maxQuizQuestions, BorderLayout.NORTH);
		addQuizOptions.add(numQuestions, BorderLayout.CENTER);
		addQuizOptions.add(buttonPanel, BorderLayout.SOUTH);

		addQuizFrame.getContentPane().add(quizListPanel, BorderLayout.NORTH);
		addQuizFrame.getContentPane().add(addQuizOptions, BorderLayout.SOUTH);

		// Add Action Listeners
		addQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(quizAddList.isSelectionEmpty()) {
					showMessage("A quiz must be selected first");
				} else {
					if(numQuestionsText.getText().equals("")) {
						showMessage("A number must be entered");
					} else {
						try {
							addQuiz();
						} catch (Exception s) {
							showMessage("Invalid data format please enter in a number");
						}
					}
				}
			}
		});

		runQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(quizAddList.isSelectionEmpty()) {
					showMessage("A quiz must be selected first");
				} else {
					if(numQuestionsText.getText().equals("")) {
						showMessage("A number must be entered");
					} else {
						try {
							runTest();
						} catch (Exception s) {
							showMessage("Invalid data format please enter in a number");
						}
					}
				}
			}
		});

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addQuizFrame.dispose();
			}
		});

		addQuizFrame.pack();
		addQuizFrame.setVisible(true); 
	}

	/**
	 * Adds a seected quiz to the student account.
	 */
	public void addQuiz() {
		// no of questions for new quiz
		int noQuestions = Integer.parseInt(numQuestionsText.getText());
		// no of questions in question database
		int noTestQuestions = Integer.parseInt(maxNumQuestionsLabel.getText());
		//checks range
		if(noQuestions >= 10 && noQuestions < noTestQuestions) {
			// Get questions
			QuestionDatabase selectedTopic = (QuestionDatabase) quizAddList.getSelectedValue();
			// Sets the total number of questions in the database to a variable
			int currentQuestionCount = selectedTopic.noOfQuestionsForTest();
			selectedTopic.setNoOfQuestionsForTest(noQuestions);
			// Copies database to another ArrayList
			try {
				if(!isStudentAllocatedQuiz(selectedTopic)) {
					Test test = new Test(selectedTopic.toString(), selectedTopic.getTestQuestions());
					addQuizStudent.addQuiz(test);
					addQuizFrame.dispose();
					refreshAccounts();
				} else {
					showMessage("This Quiz Is Already Assigned To This Student");
				}
			} catch(Exception r) {
				showMessage("Problem adding the test.");
			}
			// Sets the total number of questions back to QuestionDatabase
			selectedTopic.setNoOfQuestionsForTest(currentQuestionCount);
		} else {
			showMessage("The number of questions must be 10 or above and below the number of " +
			"questions in the selected questions database.");
		}
	}

	/**
	 * Loops through all students allocated quizes to check if quiz with a given
	 * topic is already alocated to the student.
	 * 
	 * @param topic the topic to check for
	 * @return true if the student has already been allocated a quiz of the given topic, false otherwise
	 */
	public boolean isStudentAllocatedQuiz(QuestionDatabase topic) {
		boolean allocatedQuiz = false;
		for(int quizNum = 0; quizNum < addQuizStudent.getSize(); quizNum++) {
			Test alreadyAllocatedTest = addQuizStudent.getQuiz(quizNum);
			//student already allocated quiz on given topic
			if(alreadyAllocatedTest.toString().equals(topic.toString())) {
				allocatedQuiz = true;
				break;
			}
		}
		return allocatedQuiz;
	}

	/**
	 * Copys a current database.
	 * 
	 * @param noQuestions	the ArrayList of questions.
	 * @return	the database that has been copied.
	 */
	public ArrayList<Question> copyDatabase(ArrayList<Question> noQuestions) {
		ArrayList<Question> copiedDatabase = new ArrayList<Question>();
		for(Question currentQuestion : noQuestions) {
			copiedDatabase.add(currentQuestion);
		}
		return copiedDatabase;
	}

	/**
	 * Runs a selected test.
	 */
	public void runTest() {
		//no of questions for new quiz
		int noQuestions = Integer.parseInt(numQuestionsText.getText());
		// no of questions in question database
		int noTestQuestions = Integer.parseInt(maxNumQuestionsLabel.getText());
		//checks range
		if(noQuestions >= 10 && noQuestions < noTestQuestions) {
			QuestionDatabase selectedTopic = (QuestionDatabase) quizAddList.getSelectedValue();
			selectedTopic.setNoOfQuestionsForTest(noQuestions);
			// Copies database to another ArrayList
			try {
				Test test = new Test(selectedTopic.toString(), selectedTopic.getTestQuestions());
				new TesterGui("TUTOR", test, true);
			} catch(Exception m) {
			}
		} else {
			showMessage("The number of questions must be 10 or above and below the number of " +
			"questions in the selected questions database.");
		}
	}

	/**
	 * Removes a specified test from the students account.
	 * 
	 * @param selectedStudent	the students account.
	 */
	public void removeQuiz(Account selectedStudent) {

		studentRemoveQuiz = selectedStudent;

		// Accounts modifications
		removeQuiz = new JButton("Remove Quiz");
		JButton quit = new JButton("Cancel");

		// Set up variables
		removeQuiz.setToolTipText("Create a Quiz");

		removeQuizFrame = new JFrame("Quiz Generator");
		removeQuizFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel mainPanel 	= new JPanel();
		JPanel bottomPanel = new JPanel();
		JPanel bottomPanel1 = new JPanel();
		JPanel buttonPanel = new JPanel();

		// LIST SHOWING AVAILABLE QUIZES

		DefaultListModel quizListModel = new DefaultListModel();
		for(int aNum = 0; aNum < studentRemoveQuiz.getSize(); aNum++) {
			quizListModel.addElement(selectedStudent.getQuiz(aNum));
		}

		quizList = new JList(quizListModel);
		quizList.setSelectionMode(
				ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		quizList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		quizList.setLayoutOrientation(JList.VERTICAL);
		quizList.setPreferredSize(new Dimension(250, 150));

		JScrollPane quizListScroller = new JScrollPane(quizList);
		quizListScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		quizListScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		quizListScroller.setPreferredSize(new Dimension(250, 150));

		removeQuizFrame.setLocation(225, 325);
		removeQuizFrame.getContentPane().setLayout(new BorderLayout());

		((JPanel)removeQuizFrame.getContentPane()).setBorder(new EmptyBorder(6, 6, 6, 6));

		mainPanel.setLayout(new BorderLayout());
		bottomPanel.setLayout(new BorderLayout());

		// Add components to panels

		bottomPanel1.add(quizListScroller);
		buttonPanel.add(removeQuiz);
		buttonPanel.add(quit);

		bottomPanel.add(bottomPanel1, BorderLayout.NORTH);
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

		//frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		removeQuizFrame.getContentPane().add(mainPanel, BorderLayout.NORTH);
		removeQuizFrame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		// Add Action Listeners
		removeQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Removes the quiz from the list.
				if(quizList.getSelectedIndex() != -1) {
					studentRemoveQuiz.removeQuiz(quizList.getSelectedIndex());
					//Test selectedQuiz = (Test) quizList.getSelectedValue();

					// Adds the list of quizes to the listModel
					DefaultListModel quizListModel = new DefaultListModel();
					for(int aNum = 0; aNum < studentRemoveQuiz.getSize(); aNum++) {
						quizListModel.addElement(studentRemoveQuiz.getQuiz(aNum));
					}

					quizList.setModel(quizListModel);
				} else {
					JOptionPane.showMessageDialog(removeQuizFrame,
							"No Quiz Is Selected Please Try Again",
							"",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeQuizFrame.dispose();
				refreshAccounts();
			}
		});

		removeQuizFrame.pack();
		removeQuizFrame.setVisible(true); 
	}

	/**
	 * Contructs the view results window where students test 
	 * results can be viewed.
	 * 
	 * @param selectedStudent	the selected students account.
	 */
	public void viewResults(Account selectedStudent) {


		JButton viewResults = new JButton("View Results");
		JButton quit = new JButton("Cancel");

		viewResultsframe = new JFrame("Students Quiz Results");

		viewResultsframe.setLocation(225, 325);
		viewResultsframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		viewResultsframe.getContentPane().setLayout(new BorderLayout());

		((JPanel)viewResultsframe.getContentPane()).setBorder(new EmptyBorder(6, 6, 6, 6));

		JPanel mainPanel 	= new JPanel();
		JPanel bottomPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		// LIST SHOWING AVAILABLE QUIZES
		DefaultListModel quizListModel = new DefaultListModel();
		for(int aNum = 0; aNum < selectedStudent.getSize(); aNum++) {
			if (selectedStudent.getQuiz(aNum).isFinished())
				quizListModel.addElement(selectedStudent.getQuiz(aNum));
		}

		quizList = new JList(quizListModel);
		quizList.setSelectionMode(
				ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		quizList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		quizList.setLayoutOrientation(JList.VERTICAL);
		quizList.setPreferredSize(new Dimension(150, 210));

		JScrollPane quizListScroller = new JScrollPane(quizList);
		quizListScroller.setPreferredSize(new Dimension(150, 210));
		quizListScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		quizListScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		mainPanel.setLayout(new FlowLayout());
		bottomPanel.setLayout(new BorderLayout());

		buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(viewResults);
		buttonPanel.add(quit);

		// Adds Quiz List and View Results button to a Panel.
		JPanel quizDisplayPanel = new JPanel(new BorderLayout());
		quizDisplayPanel.add(quizListScroller, BorderLayout.NORTH);
		quizDisplayPanel.add(buttonPanel, BorderLayout.SOUTH);

		// Creates TextArea showing results.
		quizDetailsText = new JTextArea();
		quizDetailsText.setColumns(20);

		// Adds a scroll pane to text area.
		JScrollPane resultsScrollPane = new JScrollPane(quizDetailsText);
		resultsScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		resultsScrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		resultsScrollPane.setPreferredSize(new Dimension(250, 250));

		mainPanel.add(quizDisplayPanel);
		mainPanel.add(resultsScrollPane);

		viewResultsframe.getContentPane().add(mainPanel, BorderLayout.CENTER);

		// Add Action Listeners
		viewResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(quizList.getSelectedValue() != null) {
					Test selectedQuiz = (Test) quizList.getSelectedValue();
					quizDetailsText.setText(selectedQuiz.getStudentStatistics());
				} else {
					showMessage("A Quiz Must Be Selected Before Its Results Can Be Viewed");
				}
			}
		});

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewResultsframe.dispose();
			}
		});


		viewResultsframe.pack();
		viewResultsframe.setVisible(true); 
	}

}
