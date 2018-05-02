/**
 * File Name: CyptoMessageGUI.java
 * Date: 2 May 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class builds the CryptoMessage GUI with a default constructor and 
 * contains the Main that displays the GUI.
 */

package cryptoMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/*
* Class for building the CryptoMessage GUI.
*/
public class CryptoMessageGUI extends JFrame{
	
	/*
	*  GLobal objects for the GUI
	*/
	private JFileChooser fc;
	public File openFile;
	public JFrame frame = new JFrame("Crypto Message Maker 1.0");
	public CryptoPanel encryptPanel = new CryptoPanel("Encrypt", "Encrypt", true, false);
	public CryptoPanel decryptPanel = new CryptoPanel("Decrypt", "Decrypt", false, false);
	public CryptoPanel brutePanel = new CryptoPanel("Brute Force", "Brute Force", false, true);
	private CryptoMessageBackend cryptoBackend = new CryptoMessageBackend();

	/*
	 * Default constructor
	 */
    public CryptoMessageGUI() {

    	/*
    	 * Creating Frame, Tabbed Pane, and adding panels
    	 */
		JTabbedPane tabbedPane = new JTabbedPane();
		frame.add(tabbedPane);
    	tabbedPane.addTab("Encrypt Message", encryptPanel);
		tabbedPane.addTab("Decrypt Message", decryptPanel);
		tabbedPane.addTab("Brute Force Decrypt Message", brutePanel);	
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
		results = cryptoBackend.encrypt(encryptPanel.textDisplay.getText(), encryptPanel.keyText.getText(), encryptPanel.cryptoDropBox.getSelectedItem().toString());
		try (FileOutputStream fos = new FileOutputStream(openFile.getAbsolutePath())){
			fos.write(results);
		} catch (IOException e) {
			System.out.print(e);
		}
	} //END encryptText() Method

	/*
	* Internal method for opening a file for decryption
	*/
    private void openFile(JLabel field){
		//Creating new filechooser and setting file filters
		fc = new JFileChooser();
        fc .setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle("Select Data File");           		   			
			
		//Displaying Path to selected file.
    	if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			openFile = fc.getSelectedFile();
			field.setText("Current File: " + fc.getSelectedFile().getName());
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
		decryptPanel.textDisplay.setText(cryptoBackend.decrypt(array, decryptPanel.keyText.getText(), decryptPanel.cryptoDropBox.getSelectedItem().toString()));
	}

	/*
	 * Internal method for calling bruteForce() in backend after button press
	 * Reads the selected file into a byte array to pass it to backend.
	 */
	public void bruteForceText(){
		byte[] array = null;
		Path filePath = Paths.get(openFile.getAbsolutePath());
		try{
		array = Files.readAllBytes(filePath);
		} catch (IOException e) {
			System.out.print(e);
		} 
		System.out.println(array.length);
		brutePanel.textDisplay.setText(cryptoBackend.bruteForce(array, brutePanel.cryptoDropBox.getSelectedItem().toString()));
	}	
	
	public static void main(String[] args) {

		//Creating CryptoMessage Object from CryptoMessage Class
		CryptoMessageGUI sp = new CryptoMessageGUI();	
		sp.frame.setVisible(true);

					
		/*
		* Listeners for button presses and other event sources
		*/
	   sp.decryptPanel.openButton.addActionListener(e -> sp.openFile(sp.decryptPanel.openText));
	   sp.encryptPanel.openButton.addActionListener(e -> sp.openFile(sp.encryptPanel.openText));
	   sp.brutePanel.openButton.addActionListener(e -> sp.openFile(sp.brutePanel.openText));
	   sp.encryptPanel.actionButton.addActionListener(e -> sp.selectFileAndEncryptText());
	   sp.decryptPanel.actionButton.addActionListener(e -> sp.decryptText());
	   sp.brutePanel.actionButton.addActionListener(e -> sp.bruteForceText()); 
	} //END MAIN

} // END CryptoMessage Class
