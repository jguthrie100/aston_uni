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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import tests.QuestionArchive;

/**
 * The ArchiveGui is used to show and delete the questions that have 
 * been added to the archive through use of the test generator.
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class ArchiveGui {
	
	private JPanel archivePanel;
	private ButtonGroup but;
	private JButton prev;
	private JButton next;
	private JButton searchDatabase;
	private JButton delete;
	private JLabel qa;
	private JLabel questionnum;
	private JLabel question;
	private JLabel totalQuestions;
	private JTextField ans1;
	private JTextField ans2;
	private JTextField ans3;
	private JTextField ans4;
	private JTextField ques;
	private JTextField numquest;
	private JTextField totalQuestionsNum;
	private JRadioButton answer1;
	private JRadioButton answer2;
	private JRadioButton answer3;
	private JRadioButton answer4;
	private JRadioButton blank;
	private QuestionArchive archive;
	private int questionNo = 1;
	private int noOfQuestions;
	private JFrame searchArchiveFrame;
	private JList archivedQuestionsList;
	
	/**
	 * Contructs the ArchiveGui where the questions
	 * from the archive file are loaded and stored
	 * in the ArchiveGui.
	 * 
	 * @throws IOException	thrown if there is a problem accessing
	 * 						the file.
	 * @throws ClassNotFoundException	thrown if the class is not
	 * 									found.
	 */
	public ArchiveGui() throws IOException, ClassNotFoundException {
		this.archive = new QuestionArchive();
	}
	
	/**
	 * Creates the display of the archive section.
	 * 
	 * @return	the panel making up the archive section.
	 */
	public JComponent runArchive() {	
		archivePanel = new JPanel();
		prev 		= new JButton();
		next 		= new JButton();
		searchDatabase = new JButton("Search Archive");
		delete = new JButton("Delete");
		
		answer1 = new JRadioButton();
		answer1.setEnabled(false);
		answer2 = new JRadioButton();
		answer2.setEnabled(false);
		answer3 = new JRadioButton();
		answer3.setEnabled(false);
		answer4 = new JRadioButton();
		answer4.setEnabled(false);
		blank = new JRadioButton();	
		
		but = new ButtonGroup();
		but.add(answer1);
		but.add(answer2);
		but.add(answer3);
		but.add(answer4);
		but.add(blank);
		
		Font font 	= new Font("Dialog" , Font.BOLD, 14);
		
		qa 			= new JLabel("QUESTIONS & ANSWERS ARCHIVE");
		questionnum = new JLabel("Question No: ");
		question 	= new JLabel("Question: ");
		totalQuestions 		= new JLabel("                  Total Questions: ");
		
		ans1 		= new JTextField();
		ans1.setEditable(false);
		ans2 		= new JTextField();
		ans2.setEditable(false);
		ans3 		= new JTextField();
		ans3.setEditable(false);
		ans4 		= new JTextField();
		ans4.setEditable(false);
		ques 		= new JTextField();
		ques.setEditable(false);
		ques.setColumns(25);
		numquest 	= new JTextField("1");
		totalQuestionsNum = new JTextField();
		totalQuestionsNum.setColumns(2);
		totalQuestionsNum.setEditable(false);
		
		// Set up variables
		next.setText("Next");
		next.setToolTipText("Next Question");
		prev.setText("Previous");
		prev.setToolTipText("Previous Question");
		
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
		numquest.setColumns(2);
		numquest.setFont(font);
		numquest.setEnabled(false);
		numquest.setEditable(false);
		// Top left bottom right
		answer1.setBorder(new EmptyBorder(0,20,0,0));
		answer2.setBorder(new EmptyBorder(0,20,0,0));
		answer3.setBorder(new EmptyBorder(0,20,0,0));
		answer4.setBorder(new EmptyBorder(0,20,0,0));
		
		JPanel topPanel  	= new JPanel();
		JPanel titlePanel  	= new JPanel(new FlowLayout());
		JPanel questionNumPanel  	= new JPanel(new FlowLayout());
		JPanel questionTextPanel   = new JPanel(new FlowLayout());
		
		JPanel testGen = new JPanel();
		testGen.setLocation(225, 325);
		testGen.setLayout(new BorderLayout());
		
		testGen.setBorder(new EmptyBorder(6, 6, 6, 6));
		
		topPanel.setLayout(new BorderLayout());
		titlePanel.setLayout(new FlowLayout());
		questionTextPanel.setLayout(new GridLayout(2, 1, 0, 10));
		
		// Add components to panels
		titlePanel.add(qa);
		questionNumPanel.add(questionnum);
		questionNumPanel.add(numquest);
		questionNumPanel.add(totalQuestions);
		questionNumPanel.add(totalQuestionsNum);
		questionTextPanel .add(question);
		questionTextPanel .add(ques);
		
		topPanel.add(titlePanel, BorderLayout.NORTH);
		topPanel.add(questionNumPanel, BorderLayout.CENTER);
		topPanel.add(questionTextPanel, BorderLayout.SOUTH);
		
		JPanel questionOne = new JPanel(new FlowLayout());
		questionOne.add(ans1);
		questionOne.add(answer1);
		JPanel questionTwo = new JPanel(new FlowLayout());
		questionTwo.add(ans2);
		questionTwo.add(answer2);
		JPanel questionThree = new JPanel(new FlowLayout());
		questionThree.add(ans3);
		questionThree.add(answer3);
		JPanel questionFour = new JPanel(new FlowLayout());
		questionFour.add(ans4);
		questionFour.add(answer4);
		
		JPanel questionPanel = new JPanel(new GridLayout(4, 1));
		questionPanel.add(questionOne);
		questionPanel.add(questionTwo);
		questionPanel.add(questionThree);
		questionPanel.add(questionFour);
		
		JPanel topButtonPanel = new JPanel(new FlowLayout());
		topButtonPanel.add(prev);
		topButtonPanel.add(next);
		
		JPanel bottomButtonPanel = new JPanel(new FlowLayout());
		bottomButtonPanel.add(searchDatabase);
		bottomButtonPanel.add(delete);
		
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(topButtonPanel, BorderLayout.NORTH);
		buttonPanel.add(bottomButtonPanel, BorderLayout.CENTER);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(questionPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		archivePanel.add(mainPanel);
		
		if(archive.noOfArchivedQuestions() > 0) {
			delete.setEnabled(true);
		}
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				questionNo = Integer.parseInt(numquest.getText());
				if (questionNo < archive.noOfArchivedQuestions() - 1)
				{
					questionNo++;
					updateFields();
				}
			}
		});
		
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				questionNo = Integer.parseInt(numquest.getText());
				
				if (questionNo > 1)
				{
					questionNo--;
					updateFields();
				}
			}
		});
		
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				int n = JOptionPane.showConfirmDialog(
						searchArchiveFrame,
				    "This Will Delete The Current Question\nAre You Sure??","CONFIRM",
				    JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION){
			    	questionNo = Integer.parseInt(numquest.getText());
			    	archive.removeQuestion(questionNo);
					changeNumQuestions(archive.noOfArchivedQuestions());
					try {
						archive.updateFile();
					} catch(Exception f) {
						JOptionPane.showMessageDialog(searchArchiveFrame,
								"Problem saving changes to File",
								"",
								JOptionPane.PLAIN_MESSAGE);
					}
					if(questionNo == archive.noOfArchivedQuestions()) {
						questionNo = questionNo - 1;
						updateFields();
					}
					if(archive.noOfArchivedQuestions() <= 1) {
						delete.setEnabled(false);
						updateFields();
					} else {
						updateFields();
					}
			    }
			}
		});
		
		searchDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchArchiveFrame = new JFrame("Search Database");
				searchArchiveFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				searchArchiveFrame.setPreferredSize(new Dimension(320, 300));
				
				DefaultListModel listModel = new DefaultListModel();
				for(int questionNum = 1; questionNum < archive.noOfArchivedQuestions(); questionNum++) {
					listModel.addElement(archive.getQuestionText(questionNum));
				}
				
				archivedQuestionsList = new JList(listModel);
				archivedQuestionsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
				archivedQuestionsList.setLayoutOrientation(JList.VERTICAL);
				JScrollPane listScroller = new JScrollPane(archivedQuestionsList);
				listScroller.setPreferredSize(new Dimension(220, 205));
				
				JButton showQuestion = new JButton("Show Question");
				
				showQuestion.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							changeCurrentQuestionNum(archivedQuestionsList.getSelectedIndex() + 1);
							updateFields();
							searchArchiveFrame.dispose();
						} catch(Exception n) {
							JOptionPane.showMessageDialog(searchArchiveFrame,
									"Select a qeuestion before showing it",
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
				
				searchArchiveFrame.add(mainPanel);
				
				searchArchiveFrame.pack();
				searchArchiveFrame.setVisible(true);
			}
		});
		
		try {
			updateFields();
		} catch(Exception e) {
		}
		return archivePanel;
	}
	
	/**
	 * Shows the next question of the archive.
	 */
	public void nextQuestion() {
		if (questionNo < noOfQuestions)
		{
			questionNo++;
			updateFields();
		}
	}
	
	/**
	 * Changes the total number of questions in the
	 * archive.
	 * 
	 * @param newNum	the new total number of questions.
	 */
	public void changeNumQuestions(int newNum) {
		noOfQuestions = newNum;
	}
	
	/**
	 * Updates the fields when the user selects 
	 * another question.
	 */
	private void updateFields() {
		if(archive.noOfArchivedQuestions() != 1) {
			numquest.setText("" + questionNo);
		} else {
			numquest.setText("");
		}
		totalQuestionsNum.setText(Integer.toString(archive.noOfArchivedQuestions() - 1));
		
		if(archive.noOfArchivedQuestions() > 1) {
			int numAnswers = archive.getNumAnswers(questionNo);
			ques.setText(archive.getQuestionText(questionNo));
			ans1.setText(archive.getAnswerText(questionNo, 1));
			ans2.setText(archive.getAnswerText(questionNo, 2));
			answer1.setSelected(archive.isAnswerCorrect(questionNo, 1));
			answer2.setSelected(archive.isAnswerCorrect(questionNo, 2));;
			
			if(numAnswers >= 3) {
				ans3.setText(archive.getAnswerText(questionNo, 3));
				answer3.setSelected(archive.isAnswerCorrect(questionNo, 3));
			} 
			if(numAnswers == 4) {
				answer4.setSelected(archive.isAnswerCorrect(questionNo, 4));
				ans4.setText(archive.getAnswerText(questionNo, 4));
			}
		} else {
			/*
			 * Reset all the question boxes
			 */
			ques.setText("");
			ans1.setText("");
			ans2.setText("");
			ans3.setText("");
			ans4.setText("");
			answer1.setSelected(false);
			answer2.setSelected(false);
			answer3.setSelected(false);
			answer4.setSelected(false);
			blank.setSelected(true);
		}

		if(questionNo < (archive.noOfArchivedQuestions() - 1)) 
			next.setEnabled(true);
		else
			next.setEnabled(false);
		
		if(questionNo <= 1) 
			prev.setEnabled(false);
		else
			prev.setEnabled(true);
	}
	
	/**
	 * Changes the current question number.
	 * 
	 * @param currentQuestion	the question number.
	 */
    public void changeCurrentQuestionNum(int currentQuestion) {
    	questionNo = currentQuestion;
    }
	
}
