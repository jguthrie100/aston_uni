package testgeneratorgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import accounts.Account;
import accounts.AccountHandler;
import accounts.AccountType;
 
/**
 * The AccountGui is used to show a list of student and teacher accounts 
 * to the user. This also allows control over those accounts by allowing 
 * the password for a specific account to be changed. Accounts can also be 
 * created and removed from the list.
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class AccountGui {
	
	private JFrame changePassFrame;
	private JFrame createAccountFrame;
	private JList accountList;
	private AccountHandler accountDatabase;
	private JTextArea accountDetails;
	private DefaultListModel listModel;
	private JTabbedPane tabbedPane;
	private JPanel textAreaPanel;
	private JPanel listScrollerPanel;
	private JScrollPane listScroller;
	private JComboBox listOptionsBox;
	private ActionListener accountTypeAction;

	/**
	 * Contructs the Account section of the test generator, the accounts 
	 * are loaded from file.
	 * 
	 * @param tabbedPane	the panel holding all the sections of the 
	 * 						Tutor Control.
	 * @throws IOException	thrown when there is a problem accessing the file.
	 * @throws ClassNotFoundException	thrown when there is no class found.
	 */
	public AccountGui(JTabbedPane tabbedPane) throws IOException, ClassNotFoundException {
		this.tabbedPane = tabbedPane;
		accountDatabase = new AccountHandler();
	}
	
	/**
	 * Used by accounts panel gets all accounts and 
	 * adds them to the listbox.
	 */
	public void createAccountList() {
		if(accountDatabase != null) {
			listModel = new DefaultListModel();
			for(int aNum = 0; aNum < accountDatabase.getSize(); aNum++) {
				listModel.addElement(accountDatabase.getAccount(aNum));
			}
			accountList = new JList(listModel);
		} else {
			accountList = new JList();
		}
		accountList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		accountList.setLayoutOrientation(JList.VERTICAL);
		accountList.addListSelectionListener(new ListSelectionListener() { 
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					Account selectedStudent = (Account) listModel.getElementAt(accountList.getSelectedIndex());
					accountDetails.setText(selectedStudent.getAccountDetails());
				}	
			}
		});

		listScroller = new JScrollPane(accountList);
		listScroller.setPreferredSize(new Dimension(220, 305));
		accountDetails = new JTextArea();
		accountDetails.setPreferredSize(new Dimension(180, 90));
		accountDetails.setEditable(false);
		Border grayline = BorderFactory.createLineBorder(Color.GRAY);
		accountDetails.setBorder(grayline);;

		listScrollerPanel = new JPanel();
		listScrollerPanel.add(listScroller);

		textAreaPanel = new JPanel();
		textAreaPanel.setPreferredSize(new Dimension(180, 110));
		textAreaPanel.add(accountDetails);
	}
	
	/**
	 * This is the actual account section where the accounts
	 * are added to a list to be displayed.
	 * 
	 * @return	the account section.
	 */
	public JComponent runAccounts() {
		// Accounts list
		createAccountList();
		//sets up create account button
		JButton createAccountButton = new JButton("Create Account");
		createAccountButton.setPreferredSize(new Dimension(140, 20));
		createAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAccount();
			}
		});

		//sets up change password button
		JButton changePasswordButton = new JButton("Change Password");
		changePasswordButton.setPreferredSize(new Dimension(140, 20));
		changePasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Account currentAccount = (Account) accountList.getSelectedValue();
				if(currentAccount != null) {
					changePassword();
				} else {
					showMessage("An Account Must Be Selected Before The Password Can Be Changed!");
				}
			}
		});

		//sets up refresh button
		JButton refreshButton = new JButton("Refresh List");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshAccounts();
			}

		});

		//sets up delete button
		JButton deleteButton = new JButton("Delete Account");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!accountList.isSelectionEmpty()) {
					accountDatabase.removeAccount(accountList.getSelectedIndex());
					try {
						accountDatabase.updateFile();
						updateQuiz();
						refreshAccounts();
					} catch(Exception w) {
						System.out.println("Refresh button: list not updated!");
					}
				} else {
					showMessage("An Account Must Be Selected Before It Can Be Deleted.");
				}
			}
		});

		JPanel bottomButtonsPanel = new JPanel();
		bottomButtonsPanel.setLayout(new FlowLayout());
		bottomButtonsPanel.add(refreshButton);
		bottomButtonsPanel.add(deleteButton);

		//sets up list box to select filter for accounts
		//list box has action listener which refreshes the 
		//list of accounts based on users selection
		JLabel comboBoxLabel = new JLabel("  Select Accounts Type For List");
		String[] listOptions = { "All Account Types", "Students", "Teachers" };
		listOptionsBox = new JComboBox(listOptions);
		listOptionsBox.setPreferredSize(new Dimension(150, 25));
		listOptionsBox.setSelectedIndex(0);
		listOptionsBox.addActionListener(accountTypeAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshAccounts();
				JComboBox comboBox = (JComboBox)e.getSource();
				int choice = comboBox.getSelectedIndex();
				listOptionsBox.removeActionListener(accountTypeAction);
				//filters list of account based on selection
				if(choice == 1) {//user selected to show student accounts
					//listOptionsBox.setSelectedItem("Students");
					showAccountsType(AccountType.STUDENT);
					listOptionsBox.setSelectedIndex(1);
				} else if (choice == 2) {//user selected to show teacher accounts
					comboBox.setSelectedIndex(2);
					listOptionsBox.setSelectedIndex(2);
					showAccountsType(AccountType.TEACHER);
				//else user has selected to show all account types
				} else {
					showAccountsType(null);
					listOptionsBox.setSelectedIndex(0);
				}
				listOptionsBox.addActionListener(accountTypeAction);
			}
		});

		JPanel comboPanel = new JPanel();
		comboPanel.add(listOptionsBox);

		// Adds text area and buttons
		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new GridLayout(5, 1, 10, 10));
		controlsPanel.add(createAccountButton);
		controlsPanel.add(changePasswordButton);
		controlsPanel.add(refreshButton);
		controlsPanel.add(deleteButton);
		controlsPanel.add(comboBoxLabel);

		JPanel rightSidePanel = new JPanel();
		rightSidePanel.setLayout(new BorderLayout());
		rightSidePanel.add(textAreaPanel, BorderLayout.NORTH);
		rightSidePanel.add(controlsPanel, BorderLayout.CENTER);
		rightSidePanel.add(comboPanel, BorderLayout.SOUTH);

		// Adds list and details to panel
		JPanel accountsPanel = new JPanel();
		accountsPanel.setLayout(new FlowLayout());
		accountsPanel.add(listScrollerPanel);
		accountsPanel.add(rightSidePanel);

		return accountsPanel;
	}
	
	/**
	 * creates new panel for accounts and
	 * adds it to tab and removes old accounts panel.
	 */
	public void refreshAccounts() {
		try {
			accountDatabase.updateFile();
			accountDatabase.loadAccounts();
			JComponent accountPanel = runAccounts();

			Icon blankIcon = null;
			tabbedPane.insertTab("Account", blankIcon, accountPanel, "", 0);
			tabbedPane.remove(1);
			tabbedPane.setSelectedIndex(0);
		} catch(Exception w) {
			showMessage("There was a problem updating the accounts.");
		}
	}

	/**
	 * Shows accounts filtering if an account type to show is specified.
	 * 
	 * @param accountType if equal to null then shows all account type otherwise shows only
	 * the account types specified.
	 */
	public void showAccountsType(AccountType accountType) {
		try {
			listModel.removeAllElements();
			if(accountType == null) {
				for(int aNum = 0; aNum < accountDatabase.getSize(); aNum++) {
					listModel.addElement(accountDatabase.getAccount(aNum));
				}
			} else {
				for(int aNum = 0; aNum < accountDatabase.getSize(); aNum++) {
					Account currentAccount = accountDatabase.getAccount(aNum);
					if(accountType == currentAccount.getAccountType()) {
						listModel.addElement(currentAccount);
					}
				}
			}
		} catch(Exception w) {
			showMessage("There was a showing the list of the selected account type.");
		}
	}

	/**
	 * Displays frame for creating an account.
	 */
	public void createAccount() {	
		createAccountFrame = new JFrame("Create New Account");
		createAccountFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//sets up name and password labels and fields
		JLabel nameLabel = new JLabel("Name");
		final JTextField nameText = new JTextField();
		nameText.setColumns(20);
		JLabel passwordLabel = new JLabel("Password");
		final JTextField passwordText = new JTextField();
		passwordText.setColumns(18);

		//sets up account type section
		JLabel accountTypeLabel = new JLabel("Account Type");
		accountTypeLabel.setPreferredSize(new Dimension(118, 15));
		final JRadioButton student = new JRadioButton("Student");
		final JRadioButton teacher = new JRadioButton("Teacher");
		ButtonGroup accountButtons = new ButtonGroup();
		accountButtons.add(student);
		accountButtons.add(teacher);

		//sets up panels for name, password and account type 
		//and adds these to main panel
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout());
		namePanel.add(nameLabel);
		namePanel.add(nameText);
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new FlowLayout());
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordText);
		JPanel accountTypePanel = new JPanel();
		accountTypePanel.setLayout(new FlowLayout());
		accountTypePanel.add(accountTypeLabel);
		accountTypePanel.add(student);
		accountTypePanel.add(teacher);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(namePanel, BorderLayout.NORTH);
		mainPanel.add(passwordPanel, BorderLayout.CENTER);
		mainPanel.add(accountTypePanel, BorderLayout.SOUTH);

		//Sets up submit button which attempts to add user to database
		JButton createAccount = new JButton("Submit");
		
		createAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AccountType accountType = null;
				if(teacher.isSelected()) {
					accountType = AccountType.TEACHER;
				} else if(student.isSelected()) {
					accountType = AccountType.STUDENT;
				}
				if(nameText.getText().equals("") || passwordText.getText().equals("") || 
						accountType == null) {
					showMessage("All details are required to be filled in to create an account");
				} else {
					boolean isAccountAdded = accountDatabase.addAccount(nameText.getText(), 
							passwordText.getText(), accountType);
					if(!isAccountAdded) {
						showMessage("Username Has Already Been Used For An Account");
					} else {
						try{
							accountDatabase.updateFile();
							accountDatabase.loadAccounts();
							listModel.addElement(accountDatabase.getAccount(nameText.getText()));
							closeCreateAccount();
							updateQuiz();
							showMessage("Account added");
						} catch (Exception x) {
							showMessage("There was a problem updating the accounts.");
						}
					}
				}
			}
		});
		
		JButton cancelCreateAccount = new JButton("Cancel");
		
		cancelCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					closeCreateAccount();
				}
			});
		

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(createAccount);
		buttonPanel.add(cancelCreateAccount);
		
		JPanel accountPanel = new JPanel();
		accountPanel.setLayout(new BorderLayout());
		accountPanel.add(mainPanel, BorderLayout.NORTH);
		accountPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		createAccountFrame.add(accountPanel);
		createAccountFrame.pack();
		createAccountFrame.setVisible(true);
	}
	
	/**
	 * Closes the Create Account window.
	 */
	public void closeCreateAccount() {
		createAccountFrame.dispose();
	}

	/**
	 * Closes the Change Password window.
	 */
	public void closeChangePassword() {
		changePassFrame.dispose();
	}
	
	/**
	 * Brings up window allowing user to change password.
	 */
	public void changePassword() {
		changePassFrame = new JFrame("Change Password");

		JLabel nameLabel = new JLabel("Name");
		JLabel oldPassLabel = new JLabel("Old Password");
		JLabel newPassLabel = new JLabel("New Password");

		final JTextField nameText = new JTextField();
		nameText.setColumns(20);

		final JTextField oldPassText = new JTextField();
		oldPassText.setColumns(16);

		final JTextField newPassText = new JTextField();
		newPassText.setColumns(15);

		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout());
		namePanel.add(nameLabel);
		namePanel.add(nameText);

		JPanel oldPassPanel = new JPanel();
		oldPassPanel.setLayout(new FlowLayout());
		oldPassPanel.add(oldPassLabel);
		oldPassPanel.add(oldPassText);

		JPanel newPassPanel = new JPanel();
		newPassPanel.setLayout(new FlowLayout());
		newPassPanel.add(newPassLabel);
		newPassPanel.add(newPassText);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(oldPassPanel, BorderLayout.CENTER);
		mainPanel.add(newPassPanel, BorderLayout.SOUTH);

		JButton changePassword = new JButton("Submit");

		changePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Account currentAccount = (Account) accountList.getSelectedValue();
				boolean isPasswordChanged = currentAccount.changePassword(oldPassText.getText(), newPassText.getText());
				if(!isPasswordChanged) {
					showMessage("The Old Password Doesn't Match the Existing Password In The Selected Account");
				} else {
					showMessage("Account Password Changed");
					closeChangePassword();
					refreshAccounts();
				}
			}
		});
		
		JButton cancelChangePassword = new JButton("Cancel");
		
		cancelChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeChangePassword();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(changePassword);
		buttonPanel.add(cancelChangePassword);

		JPanel accountPanel = new JPanel();
		accountPanel.setLayout(new BorderLayout());
		accountPanel.add(mainPanel, BorderLayout.NORTH);
		accountPanel.add(buttonPanel, BorderLayout.SOUTH);

		changePassFrame.add(accountPanel);
		changePassFrame.pack();
		changePassFrame.setVisible(true);
	}
	
	/**
	 * Updates the list of student accounts in the Quiz section.
	 */
	public void updateQuiz() {
		try { 
			QuizGui quizGui = new QuizGui();
			JComponent quizPanel = quizGui.createManager();
			Icon blankIcon = null;
			tabbedPane.insertTab("Quiz", blankIcon, quizPanel, "", 3);
			tabbedPane.remove(2);
			tabbedPane.setSelectedIndex(0);
		} catch(Exception o) {
			showMessage("There was a problem updating the quiz");
		}
	}
	
	/**
	 * Shows a message when an error occurs.
	 * 
	 * @param msg	the message shown to the user on the error
	 * message window.
	 */
	private void showMessage(String msg) {
		JOptionPane.showMessageDialog(changePassFrame,
				msg,
				"",
				JOptionPane.PLAIN_MESSAGE);
	}
	
}
