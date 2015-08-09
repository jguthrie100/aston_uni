package testgeneratorgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import tests.QuestionDatabase;
import tests.QuizDatabase;

/**
 * The DatabaseGui allows a new database of questions to be created.
 * Existing databases are shown in a list and can also be edited or
 * removed. 
 * 
 * @author 		Mesh, Dane
 * @version		1.0	
 */
public class DatabaseGui {

	private JFrame databaseFrame;
	private JTabbedPane tabbedPane;
	private JFrame tutorControlFrame;
	private QuizDatabase quizDatabase;
	private JList quizList;
	private final static int MinQuestionLimit = 20;
	private final static int MaxQuestionLimit = 1000;

	/**
	 * Contructs a new DatabaseGui object where quizzes are
	 * loaded from the file and are displayed.
	 * 
	 * @param tabbedPane	the panel containing all the sections 
	 * 						of the tutor control.
	 * @param frame			the frame of the tutor control.
	 */
	public DatabaseGui(JTabbedPane tabbedPane, JFrame frame) {
		this.tabbedPane = tabbedPane;
		tutorControlFrame = frame;
		try{ 
			this.quizDatabase = new QuizDatabase();
		} catch(Exception e) {

		}
	}

	/**
	 * Contructs the database section.
	 * 
	 * @return	the database section.
	 */
	public JComponent database() {

		databaseFrame = new JFrame();

		// CREATE DATABASE
		JPanel titlePanel = new JPanel();
		JPanel topicPanel = new JPanel();
		JPanel filePanel = new JPanel();

		JLabel createDatabaseLabel = new JLabel("Create Database");

		JLabel topicLabel = new JLabel("Topic Name");
		final JTextField topicText = new JTextField();
		topicText.setColumns(20);

		JLabel numQuestionsLabel= new JLabel("No of Questions");
		final JTextField numQuestionsText = new JTextField();
		numQuestionsText.setColumns(11);

		JButton createButton = new JButton("Create");

		// Add Action Listeners
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String topicName = topicText.getText();
				String numQ = numQuestionsText.getText();
				if(topicName.equals("") || numQ.equals("")) {
					showMessage("Make Sure The Topic And Number Of Questions Has Been Entered Before Creating a Database");
				} else {
					try {
					
							int numQuestions = Integer.parseInt(numQ);
						
							if(numQuestions >= MinQuestionLimit && numQuestions <= MaxQuestionLimit) {
								TestGeneratorGui gen = new TestGeneratorGui(true, tutorControlFrame, tabbedPane);
								JComponent genPanel = gen.runGenerator(topicName, numQuestions);
								tabbedPane.insertTab("Database", null, genPanel, "", 1);
								tabbedPane.remove(2);
								tabbedPane.setSelectedIndex(1);
							} else {
								showMessage("There must between " + MinQuestionLimit + 
										" and " + MaxQuestionLimit + " questions for a database.");
							}
					} catch(Exception ex) {
						showMessage("Only a Number Can Be Entered For The Number Of Questions");
					}
				}
			}
		});


		// add components
		titlePanel.add(createDatabaseLabel);
		topicPanel.add(topicLabel);
		topicPanel.add(topicText);

		filePanel.add(numQuestionsLabel);
		filePanel.add(numQuestionsText);
		filePanel.add(createButton);

		// add panels to create database panel

		JPanel createDatabasePanel = new JPanel();
		createDatabasePanel.setLayout(new BorderLayout());

		createDatabasePanel.add(titlePanel, BorderLayout.NORTH);
		createDatabasePanel.add(topicPanel, BorderLayout.CENTER);
		createDatabasePanel.add(filePanel, BorderLayout.SOUTH);

		// EDIT DATABASE 
		if(changeQuizToArray() != null) {
			quizList = new JList(changeQuizToArray());
		} else {
			quizList = new JList();
		}
		quizList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		quizList.setLayoutOrientation(JList.VERTICAL);

		JScrollPane listScroller = new JScrollPane(quizList);
		listScroller.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScroller.setPreferredSize(new Dimension(200, 180));

		JPanel quizListPanel = new JPanel();
		quizListPanel.setLayout(new FlowLayout());
		quizListPanel.add(listScroller);

		JButton editDatabase = new JButton("Edit");
		JButton refresh = new JButton("Refresh");
		JButton remove = new JButton("Remove");

		editDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!quizList.isSelectionEmpty()) {
					TestGeneratorGui gen = new TestGeneratorGui(false, tutorControlFrame, tabbedPane);
					QuestionDatabase selectedQuiz = (QuestionDatabase) quizList.getSelectedValue();
					int numQuestions = selectedQuiz.noOfQuestionsForTest();

					JComponent genPanel = gen.runGenerator(selectedQuiz.toString(), numQuestions);

					Icon blankIcon = null;
					tabbedPane.insertTab("Database", blankIcon, genPanel, "", 1);
					tabbedPane.remove(2);
					tabbedPane.setSelectedIndex(1);
				} else {
					showMessage("A Database Must Be Selected Before It Can Be Edited.");
				}
			}
		});

		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshAccounts();
			}
		});

		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!quizList.isSelectionEmpty()) {
					quizDatabase.deleteQuiz(quizList.getSelectedIndex());
					refreshAccounts();
				} else {
					showMessage("A Database Must Be Selected Before It Can Be Deleted.");
				}
			}
		});


		JLabel data = new JLabel("Select Database to Edit or Remove");

		JTextField database = new JTextField();

		// Set up variables
		editDatabase.setToolTipText("Edit a database");

		database.setColumns(20);

		JPanel editDatabasePanel = new JPanel();
		JPanel bottomPanel1 = new JPanel();
		JPanel bottomPanel2 = new JPanel();
		bottomPanel2.setLayout(new FlowLayout());

		editDatabasePanel.setLayout(new BorderLayout());

		// bottomPanel
		JPanel editButtonPanel = new JPanel();
		editButtonPanel.setLayout(new GridLayout(3, 1, 10, 15));
		editButtonPanel.add(editDatabase);
		editButtonPanel.add(refresh);
		editButtonPanel.add(remove);

		// Add edit components to panels

		bottomPanel1.add(data);

		bottomPanel2.setLayout(new FlowLayout());
		bottomPanel2.add(quizListPanel);
		bottomPanel2.add(editButtonPanel);

		editDatabasePanel.add(bottomPanel1, BorderLayout.NORTH);
		editDatabasePanel.add(bottomPanel2, BorderLayout.CENTER);

		// Add create and edit panels together
		JPanel databasePanel = new JPanel();
		databasePanel.setLayout(new BorderLayout());

		databasePanel.add(createDatabasePanel, BorderLayout.NORTH);
		databasePanel.add(editDatabasePanel, BorderLayout.CENTER);

		return databasePanel;
	}

	/**
	 * Opens the test generator when ever a new database is to 
	 * be created or an existing one edited.
	 * 
	 * @param frame		the frame containing the tutor control.
	 * @param topicName		the topic name for the database.
	 * @param numQuestions	the total number of questions for the 
	 * 						database.
	 */
	public void openTestGenerator(JFrame frame, String topicName, int numQuestions) {
		TestGeneratorGui gen = new TestGeneratorGui(true, tutorControlFrame, tabbedPane);

		JComponent genPanel = gen.runGenerator(topicName, numQuestions);

		Icon blankIcon = null;
		tabbedPane.insertTab("Database", blankIcon, genPanel, "", 1);
		tabbedPane.remove(2);
		tabbedPane.setSelectedIndex(1);
	}

	/**
	 * Shows a message if an error occurs.
	 * 
	 * @param msg	the error message shown when an 
	 * 				exception is thrown.
	 */
	private void showMessage(String msg) {
		JOptionPane.showMessageDialog(databaseFrame,
				msg,
				"",
				JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Saves and then loads the databases.
	 */
	public void refreshAccounts() {
		try{ 
			quizDatabase.updateQuizes();
			quizDatabase.loadQuizes();
			JComponent databasePanel = database();
			Icon blankIcon = null;
			tabbedPane.insertTab("Database", blankIcon, databasePanel, "", 1);
			tabbedPane.remove(2);
			tabbedPane.setSelectedIndex(1);
		} catch(Exception e) {
			showMessage("Problem loading quiz list");
		}
	}

	/**
	 * Used to convert an ArrayList of questions to an array.
	 * 
	 * @return	the array of questions.
	 */
	public QuestionDatabase[] changeQuizToArray(){
		QuestionDatabase[] quizArray = null;
		if(quizDatabase != null) {
			if(quizDatabase.getSize() > 0) {
				quizArray = new QuestionDatabase[quizDatabase.getSize()];
				for(int quizNum = 0; quizNum < quizArray.length; quizNum++) {
					quizArray[quizNum] = quizDatabase.getQuiz(quizNum);
				}
			}
		}
		return quizArray;
	}

}
