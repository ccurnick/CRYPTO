/**
 * File Name: CyptoMessageGUI.java
 * Date: 5 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class builds the CryptoMessage GUI with a default constructor and 
 * contains the Main that displays the GUI.
 */

package cryptoMessage;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
* Class for building the CryptoMessage GUI.
*/
public class CryptoMessageGUI extends JFrame{
	
	/*
	*  GLobal objects for the GUI
	*/
	private JFileChooser fc;
	public JTextArea encryptTextDisplay = new JTextArea();
	public JTextArea decryptTextDisplay = new JTextArea();
	public JTextArea bruteTextDisplay = new JTextArea();
	private JLabel openText = new JLabel("**File Name Appears Here**");
	private JLabel openBruteText = new JLabel("**File Name Appears Here**");
	public JTextField keyText = new JTextField();
	public JTextField decryptKeyText = new JTextField();
	public JFrame frame = new JFrame("Crypto Message Maker 0.5");
	public JButton openButton = new JButton("Open File");
	public JButton openBruteButton = new JButton("Open File");
	public JButton encryptButton = new JButton("Encrypt");
	public JButton decryptButton = new JButton("Decrypt");
	public JButton bruteForceButton = new JButton("Brute Force");
	private String[] cryptoChoice = {"AES", "DES", "DESede"};
	public JComboBox<String> cryptoDropBox = new JComboBox<String>(cryptoChoice);
	public JComboBox<String> decryptDropBox = new JComboBox<String>(cryptoChoice);
	public JComboBox<String> bruteDropBox = new JComboBox<String>(cryptoChoice);
	private CryptoMessageBackend cryptoBackend = new CryptoMessageBackend();
	public File openFile;

	/*
	 * Default constructor
	 */
    public CryptoMessageGUI() {

    	/*
    	 * Create Panels and labels for data input
    	 */
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel encryptPanel = new JPanel(new BorderLayout());
		JPanel encryptControlPanel = new JPanel(new GridBagLayout());
		JPanel encryptTextPanel = new JPanel(new BorderLayout());
		JPanel brutePanel = new JPanel(new BorderLayout());
		JPanel bruteControlPanel = new JPanel(new GridBagLayout());
		JPanel bruteTextPanel = new JPanel(new BorderLayout());
		JPanel decryptPanel = new JPanel(new BorderLayout());
		JPanel decryptControlPanel = new JPanel(new GridBagLayout());
		JPanel decryptTextPanel = new JPanel(new BorderLayout());
		JLabel keyLabel = new JLabel("Secret Key");
		JLabel decryptKeyLabel = new JLabel("Secret Key");
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		
		/*
         * Building the main Tabbed Panel
         */
    		frame.add(tabbedPane);
    		
    		/*
    		 * Setting borders and titles for the panels
    		 */
			encryptPanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "Message to Encrypt"));
			encryptTextPanel.setBorder(BorderFactory.createTitledBorder(loweredetched));
			decryptPanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "Decrypted Message"));
			decryptTextPanel.setBorder(BorderFactory.createTitledBorder(loweredetched));
			brutePanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "Brute Force Decrypted Message"));
			bruteTextPanel.setBorder(BorderFactory.createTitledBorder(loweredetched));

			/*
    		 * Add text panels to JScrollPanes for Encrypt Display and Decrypt Display
    		 */
			JScrollPane encryptScroll = new JScrollPane(encryptTextDisplay); 
			JScrollPane decryptScroll = new JScrollPane(decryptTextDisplay); 
			JScrollPane bruteScroll = new JScrollPane(bruteTextDisplay); 
			encryptTextPanel.add(encryptScroll, BorderLayout.CENTER);
			decryptTextPanel.add(decryptScroll, BorderLayout.CENTER);
			bruteTextPanel.add(bruteScroll, BorderLayout.CENTER);
			
    		/*
    		 * Add Components to control panels using addComp Method for 
    		 * GridBagLayout manager (***Technique from Derek Banas Java Tutorial***)
			 * ***Method for adding listed below***
    		 */
    		addComp(encryptControlPanel, keyLabel, 0, 0, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
    		addComp(encryptControlPanel, keyText, 1, 0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
			addComp(encryptControlPanel, cryptoDropBox, 2, 0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
			addComp(encryptControlPanel, encryptButton, 3, 0, 1, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);   		
    		addComp(decryptControlPanel, openButton, 0, 0, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
			addComp(decryptControlPanel, openText, 1, 0, 1, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
			addComp(decryptControlPanel, decryptKeyLabel, 0, 1, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
    		addComp(decryptControlPanel, decryptKeyText, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
			addComp(decryptControlPanel, decryptDropBox, 2, 1, 1, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
			addComp(decryptControlPanel, decryptButton, 3, 1, 1, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
			addComp(bruteControlPanel, openBruteButton, 0, 0, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
			addComp(bruteControlPanel, openBruteText, 1, 0, 1, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
			addComp(bruteControlPanel, bruteDropBox, 2, 1, 1, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
			addComp(bruteControlPanel, bruteForceButton, 3, 1, 1, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);   				

			/*
			* Adding all elements to the encrypt and decrypt panels.
			*/
			encryptPanel.add(encryptControlPanel, BorderLayout.NORTH);
			encryptPanel.add(encryptTextPanel, BorderLayout.CENTER);
			decryptPanel.add(decryptControlPanel, BorderLayout.NORTH);
			decryptPanel.add(decryptTextPanel, BorderLayout.CENTER);
			brutePanel.add(bruteControlPanel, BorderLayout.NORTH);
			brutePanel.add(bruteTextPanel, BorderLayout.CENTER);
			
			/*
			* Adding the encrypt and decrypt panels to the tabbed panel.
			*/
    		tabbedPane.addTab("Encrypt Message", encryptPanel);
			tabbedPane.addTab("Decrypt Message", decryptPanel);
			tabbedPane.addTab("Brute Force Decrypt Message", brutePanel);

            /*
            * Adjusting size and opacity of text boxes
            */
			encryptTextDisplay.setColumns(30);
			encryptTextDisplay.setRows(10);
			decryptTextDisplay.setRows(10);
			decryptTextDisplay.setColumns(30);
			bruteTextDisplay.setColumns(30);
			bruteTextDisplay.setRows(10);
			keyText.setColumns(15);
			decryptKeyText.setColumns(15);
			openText.setOpaque(true);
	
    		/*
    		 * Setting frame size, visible and close operation
    		 */
			encryptPanel.validate();
			decryptPanel.validate();
			brutePanel.validate();
			frame.pack();
    		frame.setMinimumSize(tabbedPane.getSize());
    		frame.setResizable(true);
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			/*
			 * Initializing CryptoBackend
			 */
			cryptoBackend.init();

	} //END CryptoMessageGUI Constructor
	
	/**
	 * Internal method for selecting an output file for encryption the encrypted
	 * the text field to that file
	 */
	private void selectFileAndEncryptText() {
		//Creating new filechooser
		fc = new JFileChooser();
		fc .setCurrentDirectory(new java.io.File("."));
		fc.setDialogTitle("Save results as...");           		     			
					
		//Encrypting text and writing it to file.
		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			openFile = fc.getSelectedFile();
			encryptText();
		}
	}

	/*
	 * Internal method for calling encrypt() in backend after button press
	 */
	public void encryptText(){
		byte[] results;
		results = cryptoBackend.encrypt(encryptTextDisplay.getText(), keyText.getText(), cryptoDropBox.getSelectedItem().toString());
		try (FileOutputStream fos = new FileOutputStream(openFile.getAbsolutePath())){
			fos.write(results);
		} catch (IOException e) {
			System.out.print(e);
		}
	} //END encryptText() Method

	/*
	* Internal method for opening a file for decryption
	*/
    private void openFile(){
		//Creating new filechooser and setting file filters
		fc = new JFileChooser();
        fc .setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle("Select Data File");           		   			
			
		//Displaying Path to selected file.
    	if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			openFile = fc.getSelectedFile();
			openText.setText("Current File: " + fc.getSelectedFile().getName());
		}
    } //END openFile() Method
	
	/*
	 * Internal method for calling decrypt() in backend after button press
	 * Reads the selected file into a byte array to pass it to backend.
	 */
	public void decryptText(){
		byte[] array = null;
		Path filePath = Paths.get(openFile.getAbsolutePath());
		try{
		array = Files.readAllBytes(filePath);
		} catch (IOException e) {
			System.out.print(e);
		} 
		System.out.println(array.length);
		decryptTextDisplay.setText(cryptoBackend.decrypt(array, decryptKeyText.getText(), decryptDropBox.getSelectedItem().toString()));
	}

	/*
	 * Internal method for calling bruteForce() in backend after button press
	 * Reads the selected file into a byte array to pass it to backend.
	 */
	private void bruteForceText(){
		byte[] array = null;
		Path filePath = Paths.get(openFile.getAbsolutePath());
		try{
		array = Files.readAllBytes(filePath);
		} catch (IOException e) {
			System.out.print(e);
		} 
		System.out.println(array.length);
		bruteTextDisplay.setText(cryptoBackend.bruteForce(array, bruteDropBox.getSelectedItem().toString()));
	}	

    /*
     * Method for adding Components to JPanels using GridBagLayout. 
     * (***Technique copied from Derek Banas Java Tutorial on YouTube***)
     */
    private void addComp(JPanel mainPanel, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place, int stretch){
		
    	GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = xPos;
		gbc.gridy = yPos;
		gbc.gridwidth = compWidth;
		gbc.gridheight = compHeight;
		gbc.insets = new Insets (5,5,5,5);
		gbc.anchor = place;
		gbc.fill = stretch;

		mainPanel.add(comp, gbc);
    } //END addComp Method
	
	public static void main(String[] args) {

		//Creating CryptoMessage Object from CryptoMessage Class
	    CryptoMessageGUI sp = new CryptoMessageGUI();	
		sp.frame.setVisible(true);	
					
		/*
		* Listeners for button presses and other event sources
		*/
	   sp.openButton.addActionListener(e -> sp.openFile());	
	   sp.encryptButton.addActionListener(e -> sp.selectFileAndEncryptText());
	   sp.decryptButton.addActionListener(e -> sp.decryptText());
	   sp.openBruteButton.addActionListener(e -> sp.openFile()); 
	   sp.bruteForceButton.addActionListener(e -> sp.bruteForceText()); 
	} //END MAIN

} // END CryptoMessage Class
