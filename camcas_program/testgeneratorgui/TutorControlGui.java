package testgeneratorgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import tests.LoginGui;

/**
 * The TutorControlGui holds all the other sections used 
 * to manage accounts, quizzes, question databases and the 
 * archive.
 * 
 * @author 		Mesh
 * @version		1.0	
 */
public class TutorControlGui {

	private JFrame tutorControlFrame;
	private JTabbedPane tabbedPane;
	private JMenuBar menuBar;
	private JComponent databasePanel;

	/**
	 * Contructs the TutorControlGui where all sections
	 * are created and the accounts, question databases,
	 * archive questions and tests are loaded.
	 */
	public TutorControlGui() {
		tutorControlFrame = new JFrame("Quiz Generator");
		tutorControlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		try {
			//initialises account and quiz control classes
			//adds file menu with close option that exits program
			menuBar = new JMenuBar();
			JMenu optionsMenu = new JMenu("Options");
			menuBar.add(optionsMenu);
			JMenuItem logoffTutorControl = new JMenuItem("Logoff");
			JMenuItem closeTutorControl = new JMenuItem("Close");
			optionsMenu.add(logoffTutorControl);
			optionsMenu.add(closeTutorControl);
			logoffTutorControl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					closeTutorControl();
				}
			});
			closeTutorControl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});

			//adds tabs to page and adds the account panel which is defined below, 
			//database panel, and quiz panels
			tabbedPane = new JTabbedPane();	
			tabbedPane.setPreferredSize(new Dimension(550, 430));
			JComponent accountPanel = account();
			tabbedPane.addTab("Account", accountPanel);
			tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
			databasePanel = database(tabbedPane);
			tabbedPane.addTab("Database", databasePanel);
			tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
			JComponent quizPanel = quiz();
			tabbedPane.addTab("Quiz", quizPanel);
			tabbedPane.setMnemonicAt(0, KeyEvent.VK_3);
			JComponent archivePanel = archive();
			tabbedPane.addTab("Archive", archivePanel);
			tabbedPane.setMnemonicAt(0, KeyEvent.VK_4);

			//adds tabbed pane and file menu to tutorpanel and adds this to main contentpane
			JPanel tutorPanel = new JPanel();
			tutorPanel.setLayout(new BorderLayout());
			tutorPanel.add(menuBar, BorderLayout.NORTH);
			tutorPanel.add(tabbedPane, BorderLayout.CENTER);
			tutorControlFrame.getContentPane().add(tutorPanel, BorderLayout.NORTH);

			tutorControlFrame.pack();
			tutorControlFrame.setVisible(true); 

		} catch (IOException e) {} catch (ClassNotFoundException e) {
			System.out.println("PROBLEM LOADING FILES");
		}
	}

	/**
	 * Contructs the accounts section that is added to a tab in
	 * the tutor control interface.
	 * 
	 * @return	the account section.
	 * @throws IOException	thrown if there is a problem accessing the file.
	 * @throws ClassNotFoundException	thrown if there is no class found.
	 */
	public JComponent account() throws IOException, ClassNotFoundException {	
		AccountGui accountGui = new AccountGui(tabbedPane);
		return accountGui.runAccounts();
	}

	/**
	 * Contructs the database section that is added to a tab in
	 * the tutor control interface.
	 * 
	 * @param tabbedPane	the panel holding all the sections.
	 * @return the database section.
	 */
	public JComponent database(JTabbedPane tabbedPane) {
		DatabaseGui database = new DatabaseGui(tabbedPane, tutorControlFrame); 
		return database.database();
	}

	/**
	 * Contructs the quiz section that is added to a tab in
	 * the tutor control interface.
	 * 
	 * @return	the quiz section.
	 * @throws IOException	thrown if there is a problem accessing the file.
	 * @throws ClassNotFoundException	thrown if the class couldnt be found.
	 */
	public JComponent quiz() throws IOException, ClassNotFoundException {
		QuizGui quiz = new QuizGui();
		return quiz.createManager();
	}
	
	/**
	 * Contructs the archive section that is added to a tab in
	 * the tutor control interface.
	 * 
	 * @return	the archive section.
	 * @throws IOException	thrown if there is a problem accessing the file.
	 * @throws ClassNotFoundException	thrown if the class couldnt be found.
	 */
	public JComponent archive() throws IOException, ClassNotFoundException {
		ArchiveGui archiveGui = new ArchiveGui();
		return archiveGui.runArchive();
	}

	/**
	 * Used to set the Tutor Control Gui to show the database section.
	 */
	public void showDatabase() {
		tabbedPane.setSelectedIndex(1);
	}

	/**
	 * Helper method to ensure consistency in leaving application.
	 */ 
	public void closeTutorControl() {
		tutorControlFrame.dispose();
		new LoginGui();
	}

}