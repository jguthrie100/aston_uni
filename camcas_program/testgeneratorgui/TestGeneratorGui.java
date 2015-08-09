package testgeneratorgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import tests.IncompleteDatabaseException;
import tests.TestGenerator;

/**
 * The TestGeneratorGui is used to create and edit question databases
 * that are loaded from a file. There are many options available like 
 * adding the question to an archive, adding more questions, and search 
 * options for the database.
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class TestGeneratorGui implements ActionListener {

	private JFrame frame;
	private boolean create;
	private boolean userChanged;
	private ButtonGroup but;
	private JButton save;
	private JButton finish;
	private JButton exitButton;
	private JButton delete;
	private JButton changeNumQuestions;
	private JButton archiveQuestion;
	private JButton prevQuestion;
	private JButton nextQuestion;
	private JLabel qa;
	private JLabel questionnum;
	private JLabel question;
	private JLabel answer;
	private JLabel correct;
	private JTextField ans1;
	private JTextField ans2;
	private JTextField ans3;
	private JTextField ans4;
	private JTextField ques;
	private JLabel totalQuestionsNum;
	private JRadioButton answer1;
	private JRadioButton answer2;
	private JRadioButton answer3;
	private JRadioButton answer4;
	private JList questionsList;
	private TestGenerator testGenerator;
	private int questionNo = 1;
	private int noOfQuestions;
	private JComboBox answerChoiceList;
	private boolean newQuestions;
	private JFrame tutorControlFrame;
	private JFrame addMoreQuestionsframe; 
	private JFrame searchDatabaseFrame;
	private JTabbedPane tutorControl;
	private JComboBox questionNoList;
	private final static int MaxQuestionLimit = 1000;
	private JPanel questionNumPanel;
	private JLabel totalQuestions;
	
	/**
	 * Contructs the TestGeneratorGui that is used to manage the
	 * questions and answers of a database.
	 * 
	 * @param create	true if this database is a new one or false if
	 * 					its an existing database.
	 * @param frame		the frame of the TutorControlGui.
	 * @param tabbedPane	the panel containing all the sections of the
	 * 						TutorControlGui.
	 */
	public TestGeneratorGui(boolean create, JFrame frame, JTabbedPane tabbedPane) {
		this.create = create;
		tutorControlFrame = frame;
		tutorControl = tabbedPane;
	}

	/**
	 * Contructs the TestGenerator section showing the options to
	 * create, edit or remove questions and answers.
	 * 
	 * @param topic		the name of a particular topic.
	 * @param noOfQuestions		the number of questions for the
	 * 							database.
	 * @return	the test generator section.
	 */
	public JComponent runGenerator(String topic, int noOfQuestions) {

		this.noOfQuestions = noOfQuestions;
		userChanged = false;
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		save 		= new JButton();
		finish 		= new JButton();
		delete 		= new JButton();

		changeNumQuestions = new JButton("Add Questions");
		archiveQuestion = new JButton("Archive Question");

		answer1 = new JRadioButton();
		answer1.setSelected(true);
		answer2 = new JRadioButton();
		answer3 = new JRadioButton();
		answer4 = new JRadioButton();

		but = new ButtonGroup();
		but.add(answer1);
		but.add(answer2);
		but.add(answer3);
		but.add(answer4);

		Font font 	= new Font("Dialog" , Font.BOLD, 12);

		qa 			= new JLabel("QUESTION DATABASE GENERATOR");
		questionnum = new JLabel("Question No: ");
		question 	= new JLabel("Enter Question: ");
		answer 		= new JLabel("Enter Answers:   ");
		correct 	= new JLabel("         Correct Answer:");

		ans1 		= new JTextField();
		ans2 		= new JTextField();
		ans3 		= new JTextField();
		ans4 		= new JTextField();
		ques 		= new JTextField();

		//set up list box with question numbers
		String[] questionNos = new String[noOfQuestions];
		for (int n = 0; n < noOfQuestions; n++)
			questionNos[n]= "" + (n + 1);
		questionNoList = new JComboBox(questionNos);
		questionNoList.setSelectedIndex(0);
		questionNoList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userChanged){
					saveQuestion();
				}
				userChanged = true;
				JComboBox cb = (JComboBox)e.getSource();
				questionNo = Integer.parseInt((String)cb.getSelectedItem());
				updateFields();
			}
		});
		
		// Set up variables
		save.setText("Save Database");
		save.setToolTipText("Save Database");
		finish.setText("Finish");
		finish.setToolTipText("Finish Test");
		delete.setText("Delete Question");
		delete.setToolTipText("Delete a Question");
		
		exitButton = new JButton("Exit");
		exitButton.setToolTipText("Exit the system");

		ans1.setColumns(27);
		ans1.setFont(font);
		ans2.setColumns(27);
		ans2.setFont(font);
		ans3.setColumns(27);
		ans3.setFont(font);
		ans4.setColumns(27);
		ans4.setFont(font);
		ques.setColumns(45);

		ques.setFont(font);

		// Top left bottom right
		answer1.setBorder(new EmptyBorder(0,20,0,0));
		answer2.setBorder(new EmptyBorder(0,20,0,0));
		answer3.setBorder(new EmptyBorder(0,20,0,0));
		answer4.setBorder(new EmptyBorder(0,20,0,0));

		JPanel mainPanel 	= new JPanel();
		JPanel testGen = new JPanel();
		testGen.setLocation(225, 325);
		testGen.setLayout(new BorderLayout());

		testGen.setBorder(new EmptyBorder(6, 6, 6, 6));

		totalQuestions = new JLabel("           Number Of Questions: ");
		totalQuestionsNum = new JLabel(Integer.toString(noOfQuestions));

		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titlePanel.setPreferredSize(new Dimension(200, 25));
		titlePanel.add(qa);

		questionNumPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		questionNumPanel.add(questionnum);
		questionNumPanel.add(questionNoList);
		questionNumPanel.add(totalQuestions);
		questionNumPanel.add(totalQuestionsNum);

		JPanel enterQuestionPanel = new JPanel(new GridLayout(2, 1, 0, 5));
		enterQuestionPanel.add(question);
		enterQuestionPanel.add(ques);

		JPanel answerText = new JPanel(new FlowLayout(FlowLayout.LEFT));
		answerText.add(answer);
		answerText.add(correct);

		JPanel questionOnePanel = new JPanel(new FlowLayout());
		questionOnePanel.add(ans1);
		questionOnePanel.add(answer1);

		JPanel questionTwoPanel = new JPanel(new FlowLayout());
		questionTwoPanel.add(ans2);
		questionTwoPanel.add(answer2);

		JPanel questionThreePanel = new JPanel(new FlowLayout());
		questionThreePanel.add(ans3);
		questionThreePanel.add(answer3);

		JPanel questionFourPanel = new JPanel (new FlowLayout());
		questionFourPanel.add(ans4);
		questionFourPanel.add(answer4);

		// Used to change number of alternative answers	
		String[] numChoices = { "2", "3", "4" };

		answerChoiceList = new JComboBox(numChoices);
		answerChoiceList.setSelectedIndex(2);
		answerChoiceList.addActionListener(this);

		JLabel comboBoxLabel = new JLabel("Number Of Answer Choices: ");
		JPanel comboBoxPanel = new JPanel(new FlowLayout());
		comboBoxPanel.add(comboBoxLabel);
		comboBoxPanel.add(answerChoiceList);

		//JButton saveQuestion = new JButton("Save");
		prevQuestion = new JButton("Previous");
		prevQuestion.setEnabled(false);
		nextQuestion = new JButton("Next");

		JPanel questionOptionPanel = new JPanel(new FlowLayout());
		questionOptionPanel.add(prevQuestion);
		questionOptionPanel.add(nextQuestion);

		JPanel questionPanel = new JPanel(new GridLayout(6, 1));
		questionPanel.add(questionOnePanel);
		questionPanel.add(questionTwoPanel);
		questionPanel.add(questionThreePanel);
		questionPanel.add(questionFourPanel);
		questionPanel.add(questionOptionPanel);
		questionPanel.add(comboBoxPanel);

		final JButton searchDatabase = new JButton("Search Database");

		JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));
		buttonPanel.add(save);
		buttonPanel.add(delete);
		buttonPanel.add(changeNumQuestions);
		buttonPanel.add(searchDatabase);
		buttonPanel.add(archiveQuestion);
		buttonPanel.add(exitButton);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(answerText, BorderLayout.NORTH);
		mainPanel.add(questionPanel, BorderLayout.WEST);
		mainPanel.add(buttonPanel, BorderLayout.EAST);

		JPanel databaseDetailsPanel = new JPanel(new BorderLayout());
		databaseDetailsPanel.add(questionNumPanel, BorderLayout.NORTH);
		databaseDetailsPanel.add(enterQuestionPanel, BorderLayout.SOUTH);

		testGen.add(titlePanel, BorderLayout.NORTH);
		testGen.add(databaseDetailsPanel, BorderLayout.CENTER);
		testGen.add(mainPanel, BorderLayout.SOUTH);
		
		

		nextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveQuestion();
				if (questionNo < testGenerator.noOfQuestionsForTest())
				{
					userChanged = false;
					questionNo++;
					updateFields();
					setNumNewQuestions();
				}
				searchDatabase.setEnabled(true);
			}
		});

		prevQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveQuestion();
				if (questionNo > 1)
				{
					userChanged = false;
					questionNo--;
					updateFields();
					setNumNewQuestions();
				}
			}
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveQuestion();
				try {
					testGenerator.saveQuestionDatabase();

					JOptionPane.showMessageDialog(frame,
							"File Saved",
							"",
							JOptionPane.PLAIN_MESSAGE);
				} 
				catch (IncompleteDatabaseException e1) {
					displayError(e1);
				} catch (IOException e1) {
					displayError(e1);
				}
			}
		});

		searchDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveQuestion();
				searchDatabaseFrame = new JFrame("Search Database");
				searchDatabaseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				searchDatabaseFrame.setPreferredSize(new Dimension(320, 300));

				DefaultListModel listModel = new DefaultListModel();
				for(int questionNum = 1; questionNum <= getNumQuestions(); questionNum++) {
					if(!testGenerator.getQuestionText(questionNum).equals(""))
					listModel.addElement(testGenerator.getQuestionText(questionNum));
				}

				questionsList = new JList(listModel);
				questionsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
				questionsList.setLayoutOrientation(JList.VERTICAL);
				JScrollPane listScroller = new JScrollPane(questionsList);
				listScroller.setPreferredSize(new Dimension(220, 205));

				JButton showQuestion = new JButton("Show Question");

				showQuestion.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							changeCurrentQuestionNum(questionsList.getSelectedIndex() + 1);
							

							
							System.out.println(questionsList.getSelectedIndex() + 1);
							int numAnswers = testGenerator.getNumAnswers(questionNo);
							ques.setText(testGenerator.getQuestionText(questionNo));
							ans1.setText(testGenerator.getAnswerText(questionNo, 1));
							ans2.setText(testGenerator.getAnswerText(questionNo, 2));
							answer1.setSelected(testGenerator.isAnswerCorrect(questionNo, 1));
							answer2.setSelected(testGenerator.isAnswerCorrect(questionNo, 2));
							disableThreeAndFour();

							if(numAnswers >= 3) {
								ans3.setText(testGenerator.getAnswerText(questionNo, 3));
								answer3.setSelected(testGenerator.isAnswerCorrect(questionNo, 3));
								enableThreeDisableFour();
							} 
							if(numAnswers == 4) {
								answer4.setSelected(testGenerator.isAnswerCorrect(questionNo, 4));
								ans4.setText(testGenerator.getAnswerText(questionNo, 4));
								enableThreeAndFour();
							}

							if(questionNo < getNumQuestions()) 
								nextQuestion.setEnabled(true);
							else
								nextQuestion.setEnabled(false);

							if(questionNo == 1) 
								prevQuestion.setEnabled(false);
							else
								prevQuestion.setEnabled(true);
							
							setNumComboBox();
							questionNoList.setSelectedIndex(questionNo - 1);
							
							searchDatabaseFrame.dispose();
						} catch(Exception n) {
							JOptionPane.showMessageDialog(searchDatabaseFrame,
									"Select a question before showing it",
									"",
									JOptionPane.PLAIN_MESSAGE);
						}
					}
				});

				JPanel buttonPanel = new JPanel(new FlowLayout());
				buttonPanel.add(showQuestion);

				JPanel mainPanel = new JPanel(new BorderLayout());
				mainPanel.add(listScroller, BorderLayout.NORTH);
				mainPanel.add(buttonPanel, BorderLayout.CENTER);

				searchDatabaseFrame.add(mainPanel);
				searchDatabaseFrame.pack();
				searchDatabaseFrame.setVisible(true);
			}
		});

		archiveQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveQuestion();
				int numAnswers = testGenerator.getNumAnswers(questionNo);
				if(numAnswers == 2) {
					if((ques.getText().equals("") || (ans1.getText().equals("")) || (ans2.getText().equals(""))))
					{
						showMessage("All fields must be filled in before a question" +
								"can be sent to the archive.");
					} else {
						archiveQuestion();
					}
				} 
				if(numAnswers == 3) {
					if((ques.getText().equals("") || ans1.getText().equals("")) || (ans2.getText().equals("")) || 
							(ans3.getText().equals(""))) 
					{
						showMessage("All fields must be filled in before a question" +
								"can be sent to the archive.");
					} else {
						archiveQuestion();
					}
				} 
				if(numAnswers == 4) {
					if((ques.getText().equals("") || (ans1.getText().equals("")) || (ans2.getText().equals("")) || 
							(ans3.getText().equals("")) || (ans4.getText().equals("")))) 
					{
						showMessage("All fields must be filled in before a question" +
								"can be sent to the archive.");
					} else {
						archiveQuestion();
					}
				}
			}
		});

		changeNumQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveQuestion();
				addQuestionsToDatabase();
			}
		});

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(
						frame,
						"Are You Sure You Wish To Exit??","CONFIRM",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION){
					closeTestGenerator();
				}
			}
		});

		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				saveQuestion();
				if(getNumQuestions() == 20) {
					showMessage("A Question Database Must Have 20 Questions");
				} else {
				int n = JOptionPane.showConfirmDialog(
						frame,
						"This Will Delete The Current Question\nAre You Sure??","CONFIRM",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION){
					testGenerator.removeQuestion(questionNo);
					ques.setText("");
					ans1.setText("");
					ans2.setText("");
					ans3.setText("");
					ans4.setText("");
					changeNumQuestions(testGenerator.noOfQuestionsForTest());
					questionNumPanel.remove(1);

					String[] questionNos = new String[getNumQuestions()];
					for (int count = 0; count < getNumQuestions(); count++) {
						questionNos[count] = "" + (count + 1);
						questionNoList.addItem(questionNos[count]);
					}
					
					questionNoList = new JComboBox(questionNos);
					questionNoList.setSelectedIndex(0);
					questionNoList.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(userChanged){
								saveQuestion();
							}
							userChanged = true;
							JComboBox cb = (JComboBox)e.getSource();
							questionNo = Integer.parseInt((String)cb.getSelectedItem());
							updateFields();
						}
					});
					questionNumPanel.add(questionNoList, 1);
					totalQuestionsNum.setText(Integer.toString(getNumQuestions()));
					questionNoList.setSelectedIndex(0);
					updateFields();
				}
				}
			}
		});


		try {
			testGenerator = new TestGenerator(topic);
		} catch(Exception tg) {
			showMessage("Test Generator could not be loaded");
		}

		// Creates a new database or retreives a current daatabase
		if(!create) {
			try {
				testGenerator.openQuestionDatabase(topic);
				this.noOfQuestions = testGenerator.noOfQuestionsForTest();
				updateFields();
			} catch (IOException e2) {
				displayError(e2);
			} catch (ClassNotFoundException e2) {
				displayError(e2);
			}
		} else {
			testGenerator.createNewQuestionDatabase(topic, noOfQuestions);
			searchDatabase.setEnabled(false);
		}

		return testGen;
	}

	/**
	 * Changes the current question.
	 * 
	 * @param currentQuestion	the new question number.
	 */
	public void changeCurrentQuestionNum(int currentQuestion) {
		questionNo = currentQuestion;
	}

	/**
	 * Changes the size of question database.
	 * 
	 * @param newNum	the new total number of questions.
	 */
	public void changeNumQuestions(int newNum) {
		noOfQuestions = newNum;
	}

	/**
	 * Checks to find the correct answer in the current question
	 */
	public int searchCorrectAnswer() {
		int correctAnswerNum = 0; 
		boolean correctAnswer = false;
		for(int answerNum = 1; answerNum < 5; answerNum++) {
			correctAnswer = testGenerator.isAnswerCorrect(questionNo, answerNum);
			if(correctAnswer) {
				correctAnswerNum = answerNum;
				break;
			}
		}
		return correctAnswerNum;
	}

	/**
	 * Contructs a window to allow more questions to be added to 
	 * the database.
	 */
	public void addQuestionsToDatabase() {
		addMoreQuestionsframe = new JFrame("Add Questions");

		// Shows maximum number of questions
		JLabel maxQuestions = new JLabel("Total number of questions: ");
		JLabel maxQuestionsText = new JLabel(Integer.toString(testGenerator.noOfQuestionsForTest()));

		JPanel maxQuestionsPanel = new JPanel();
		maxQuestionsPanel.setLayout(new FlowLayout());
		maxQuestionsPanel.add(maxQuestions);
		maxQuestionsPanel.add(maxQuestionsText);

		// Allows user to specify the number of questions to be added
		JLabel numQuestions = new JLabel("Number of Questions to be added: ");
		final JTextField numQuestionsText = new JTextField();
		numQuestionsText.setColumns(3);

		JPanel numQuestionsPanel = new JPanel();
		numQuestionsPanel.setLayout(new FlowLayout());
		numQuestionsPanel.add(numQuestions);
		numQuestionsPanel.add(numQuestionsText);

		// Adds a button for user to submit the number entered
		JButton addQuestions = new JButton("Add Questions");
		JButton cancel = new JButton("Cancel");

		addQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numQuestions = Integer.parseInt(numQuestionsText.getText());
				if(numQuestions < 0) {
					showMessage("A number above zero has to be entered");
				} else {
					if(((noOfQuestions + numQuestions) <= MaxQuestionLimit)) {
						int previousTotal = noOfQuestions;
						noOfQuestions += numQuestions;
						testGenerator.addMoreQuestions(noOfQuestions);
						updateTotalQuestions();
						updateQuestionComboBox(previousTotal);
						updateFields();
						addMoreQuestionsframe.dispose();
					} else {
						showMessage("There cant be over " + MaxQuestionLimit + 
								" questions in a database");
					}
				}
			}
		});

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addMoreQuestionsframe.dispose();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(addQuestions);
		buttonPanel.add(cancel);

		// Adds all components to a panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(maxQuestionsPanel, BorderLayout.NORTH);
		mainPanel.add(numQuestionsPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		addMoreQuestionsframe.getContentPane().add(mainPanel, BorderLayout.CENTER);

		addMoreQuestionsframe.pack();
		addMoreQuestionsframe.setVisible(true);
	}

	/**
	 * Gets the size of the question database.
	 * 
	 * @return	the total number of questions.
	 */
	public int getNumQuestions() {
		return noOfQuestions;
	}

	/**
	 * Updates the fields when the user selects another question. 
	 */
	private void updateFields() {
		setNumComboBox();
		questionNoList.setSelectedIndex(questionNo - 1);

		int numAnswers = testGenerator.getNumAnswers(questionNo);
		ques.setText(testGenerator.getQuestionText(questionNo));
		ans1.setText(testGenerator.getAnswerText(questionNo, 1));
		ans2.setText(testGenerator.getAnswerText(questionNo, 2));
		answer1.setSelected(testGenerator.isAnswerCorrect(questionNo, 1));
		answer2.setSelected(testGenerator.isAnswerCorrect(questionNo, 2));
		disableThreeAndFour();

		if(numAnswers >= 3) {
			ans3.setText(testGenerator.getAnswerText(questionNo, 3));
			answer3.setSelected(testGenerator.isAnswerCorrect(questionNo, 3));
			enableThreeDisableFour();
		} 
		if(numAnswers == 4) {
			answer4.setSelected(testGenerator.isAnswerCorrect(questionNo, 4));
			ans4.setText(testGenerator.getAnswerText(questionNo, 4));
			enableThreeAndFour();
		}

		if(questionNo < getNumQuestions()) 
			nextQuestion.setEnabled(true);
		else
			nextQuestion.setEnabled(false);

		if(questionNo == 1) 
			prevQuestion.setEnabled(false);
		else
			prevQuestion.setEnabled(true);
	}

	/**
	 * Saves a question and its answers to the database.
	 */
	public void saveQuestion(){

		int numAnswers = testGenerator.getNumAnswers(questionNo);
		String setAnswerBox = Integer.toString(numAnswers);

		testGenerator.setQuestionText(questionNo, ques.getText());
		testGenerator.setAnswerText(questionNo, 1, ans1.getText());
		testGenerator.setAnswerText(questionNo, 2, ans2.getText());

		testGenerator.setIsAnswerCorrect(questionNo, 1, answer1.isSelected());
		testGenerator.setIsAnswerCorrect(questionNo, 2, answer2.isSelected());

		if((setAnswerBox.equals("3")) || setAnswerBox.equals("4")) {
			testGenerator.setAnswerText(questionNo, 3, ans3.getText()); 	
			testGenerator.setIsAnswerCorrect(questionNo, 3, answer3.isSelected());
		}

		if(setAnswerBox.equals("4")) {
			testGenerator.setAnswerText(questionNo, 4, ans4.getText()); 	
			testGenerator.setIsAnswerCorrect(questionNo, 4, answer4.isSelected());
		}
	}

	/**
	 * Updates the total of questions in the database.
	 */
	public void updateTotalQuestions() {
		totalQuestionsNum.setText(Integer.toString(noOfQuestions));
	}

	/**
	 * Sets a new database question to select the 
	 * first option as the answer.
	 */
	public void setNumNewQuestions() {
		if(newQuestions && checkNewQuestions().equals("")) {
			answerChoiceList.setSelectedIndex(2);
		}
		if(newQuestions && questionNo == noOfQuestions) {
			newQuestions = false;
		}
	}

	/**
	 * Gets the question specified.
	 * 
	 * @return	the question details specified from the user.
	 */
	public String checkNewQuestions() {
		int numAnswers = testGenerator.getNumAnswers(questionNo);
		String question = "";

		question += testGenerator.getQuestionText(questionNo);
		question += testGenerator.getAnswerText(questionNo, 1);
		question += testGenerator.getAnswerText(questionNo, 2);

		if(numAnswers >= 3) {
			question += testGenerator.getAnswerText(questionNo, 3);
		} 
		if(numAnswers == 4) {
			question += testGenerator.getAnswerText(questionNo, 4);
		}
		return question;
	}

	/**
	 * Changes the option of the change request 
	 * combo box.
	 * 
	 * @param numAnswers	the number of answers set to a 
	 * 						question.
	 */
	public void changeComboOption(int numAnswers) {
		if(numAnswers == 2)
			answerChoiceList.setSelectedIndex(0);
		else if(numAnswers == 3)
			answerChoiceList.setSelectedIndex(1);
		else if(numAnswers == 4)
			answerChoiceList.setSelectedIndex(2);
	}

	/**
	 * Used to show an error message.
	 * 
	 * @param e	the exception shown on the error message.
	 */
	private void displayError(Exception e)
	{
		JOptionPane.showMessageDialog(frame,
				e.toString(),
				"EXCEPTION",
				JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Helper method to ensure consistency in leaving application.
	 */
	public void closeTestGenerator() {
		try {
			tutorControlFrame.dispose();
			TutorControlGui tutorControl = new TutorControlGui();
			tutorControl.showDatabase();
		} catch(Exception e) {
			showMessage("Could not update Databases.");
		}
	}

	/**
	 * Stores how many answers there are for given question.
	 */
	public void actionPerformed(ActionEvent e) {

		JComboBox numChoices = (JComboBox)e.getSource();
		int numAnswerChoices = Integer.parseInt((String) numChoices.getSelectedItem());

		testGenerator.setNumAnswers(questionNo, numAnswerChoices);

		answer1.setSelected(true);

		if(numAnswerChoices == 2) {
			disableThreeAndFour();
		} else if(numAnswerChoices == 3) {
			enableThreeDisableFour();
		} else if(numAnswerChoices == 4) {
			enableThreeAndFour();
		}
		saveQuestion();
	}

	/**
	 * Sets the current option shown on the change request
	 * combo box.
	 */
	public void setNumComboBox() {
		int numAnswers = testGenerator.getNumAnswers(questionNo);
		answerChoiceList.removeActionListener(this);
		if(numAnswers == 2) {
			answerChoiceList.setSelectedIndex(0);
		} else if(numAnswers == 3) {
			answerChoiceList.setSelectedIndex(1);
		} else if(numAnswers == 4) {
			answerChoiceList.setSelectedIndex(2);
		}
		answerChoiceList.addActionListener(this);
	}

	/**
	 * Used to disable the text field and radio 
	 * button for questions three and four. 
	 */
	public void disableThreeAndFour() {
		ans3.setEnabled(false);
		ans4.setEnabled(false);
		ans3.setText("");
		ans4.setText("");
		answer3.setEnabled(false);
		answer4.setEnabled(false);
	}

	/**
	 * Used to enable the text field and radio 
	 * button for question three and disabled
	 * question four.
	 */
	public void enableThreeDisableFour() {
		ans3.setEnabled(true);
		ans4.setEnabled(false);
		ans4.setText("");
		answer3.setEnabled(true);
		answer4.setEnabled(false);
	}

	/**
	 * Used to enable the text field and radio 
	 * button for questions three and four.
	 */
	public void enableThreeAndFour() {
		ans3.setEnabled(true);
		ans4.setEnabled(true);
		answer3.setEnabled(true);
		answer4.setEnabled(true);
	}
	
	/**
	 * Updates the question combo box.
	 * 
	 * @param previousTotal	the previous total before adding
	 *						more questions to the database.
	 */
	public void updateQuestionComboBox(int previousTotal) {
		//set up list box with question numbers
		for (int n = previousTotal; n < noOfQuestions; n++) {
			questionNoList.addItem("" + (n + 1));
		}
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
	
	public void archiveQuestion() {
		int n = JOptionPane.showConfirmDialog(
				frame,
				"This Will Add The Current Question To The Archive Database\nAre You Sure??","CONFIRM",
				JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.YES_OPTION){
			// Adds question to the archive database
			try {
				testGenerator.reset();
				testGenerator.archiveQuestion(ques.getText(), ans1.getText(), ans2.getText(), ans3.getText(), ans4.getText(), searchCorrectAnswer());
				testGenerator.updateArchiveDatabase();
				ArchiveGui archiveGui = new ArchiveGui();
				JComponent archivePanel = archiveGui.runArchive();
				tutorControl.insertTab("Archive", null, archivePanel, "", 4);
				tutorControl.remove(3);
			} catch(Exception a) {
				showMessage("Question must be saved before adding to archive");
			}
			changeNumQuestions(testGenerator.noOfQuestionsForTest());
			updateFields();
		}
	}
		
}