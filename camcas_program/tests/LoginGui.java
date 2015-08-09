package tests;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import accounts.Account;
import accounts.AccountHandler;
import accounts.AccountType;
import testergui.*;
import testgeneratorgui.*;

/**
 * The Login class is used to allow the students to login
 * to their accounts and tutors to login in to the 
 * tutor control section.
 * 
 * @author 		Mesh, Dane
 * @version		1.0	
 */
public class LoginGui {

	private JFrame frame;
	private AccountHandler accounts;
	private String password;
	private JRadioButton tutorButton;
	private JTextField user;
	private JTextField pass;

	/**
	 * Creates the login interface.
	 */
	public LoginGui() {
		
		// Loads accounts
		try {
			accounts = new AccountHandler();
		} catch(Exception e) {
			showMessage("An Error Has Occured Loading The Accounts.");
		}
		
		frame = new JFrame("Quiz Generator");
		frame.setLocation(225, 325);
		frame.getContentPane().setLayout(new BorderLayout());
		((JPanel)frame.getContentPane()).setBorder(new EmptyBorder(6, 6, 6, 6));

		JPanel mainPanel 	= new JPanel();
		mainPanel.setLayout(new BorderLayout());
		JPanel titlePanel	= new JPanel();
		JPanel panel2	= new JPanel();
		JPanel panel3	= new JPanel();
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		JPanel passwordPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		Font font 	= new Font("Dialog" , Font.BOLD, 14);

		// initialise labels.
		JLabel systemTitle1 			= new JLabel("Computer Assisted Multiple");
		JLabel systemTitle2			= new JLabel("Choice Assessment System");
		JLabel access 			= new JLabel("Please Login:");
		JLabel ulogin 			= new JLabel("Username:");
		JLabel plogin 			= new JLabel("Password:");

		// initalise username and password fields.
		user 		= new JTextField();
		user.setText("tutor");
		pass 		= new JPasswordField(20);
		pass.setText("tutor");
		pass.setActionCommand(password);
		user.setColumns(15);
		user.setFont(font);
		pass.setColumns(15);
		pass.setFont(font);

		// Create radio buttons and add them to radio group "group" 
		// and place them onto radioPanel.
		JButton login = new JButton("Login");
		JRadioButton studentButton = new JRadioButton("Student");
		tutorButton = new JRadioButton("Tutor");
		tutorButton.setSelected(true);
		ButtonGroup group = new ButtonGroup();
		group.add(studentButton);
		group.add(tutorButton);
		JPanel radioPanel = new JPanel();
		radioPanel.add(studentButton);
		radioPanel.add(tutorButton);

		// Add components to panels
		titlePanel.add(systemTitle1);
		titlePanel.add(systemTitle2);
		panel2.add(access);
		panel3.add(ulogin);
		panel3.add(user);
		passwordPanel.add(plogin);
		passwordPanel.add(pass);

		/**
		 * Allows user to login to either the TutorControlGui
		 * or StudentMenuGui depending on what the users
		 * account type.
		 */
		login.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				//Get which type of account user is attempting to logon to
				AccountType accountType = AccountType.STUDENT;
				if(tutorButton.isSelected()) 
					accountType = AccountType.TEACHER;
				//attempt to login (null returned if login is not successful)
				Account account = accounts.login(user.getText(), 
						pass.getText(), accountType);
				//login is accepted
				if(account != null) {
					frame.dispose();
					if (account.getAccountType() == AccountType.TEACHER)
						new TutorControlGui();
					else
						new StudentMenuGui(account);
				} else
					//login not accepted
					showMessage("Username and Password Not Accepted");			
			}
		});

		/**
		 * Allows user to close the program.
		 */
		JButton quit = new JButton("Exit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		buttonPanel.add(login);
		buttonPanel.add(quit);

		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(panel2, BorderLayout.CENTER);
		mainPanel.add(panel3, BorderLayout.SOUTH);

		bottomPanel.add(passwordPanel, BorderLayout.NORTH);
		bottomPanel.add(radioPanel, BorderLayout.CENTER);
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

		frame.getContentPane().add(mainPanel, BorderLayout.NORTH);
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true); 
	}

	/**
	 * This is used to produce a pop up window that shows
	 * a message thats passed in as msg.
	 * 
	 * @param msg	represents the message shown on window 
	 * 				that appears.
	 */
	private void showMessage(String msg)
	{
		JOptionPane.showMessageDialog(frame,
				msg,
				"",
				JOptionPane.PLAIN_MESSAGE);
	}
	
	/**
	 * Runs the loginGui.
	 */
	public static void main(String[] args) {
		new LoginGui();
	}

}