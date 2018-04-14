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
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.w3c.dom.ls.LSException;

/*
* Class for building the CryptoMessage GUI.
*/
public class CryptoMessageGUI extends JFrame{
	
	/*
	*  GLobal objects for the GUI
	*/
	private JFileChooser fc = null;
	private JTextArea encryptTextDisplay = new JTextArea();
	private JTextArea decryptTextDisplay = new JTextArea();
    private JLabel openText = new JLabel("**File Name Appears Here**              ");
	private JTextField keyText = new JTextField();
	
	/*
	 * Default constructor
	 */
    public CryptoMessageGUI() {

    		/*
    		 * Create Button, and Labels for data input
    		 */
        JFrame frame = new JFrame("Crypto Message Maker 0.1");
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel encryptPanel = new JPanel(new BorderLayout());
		JPanel encryptControlPanel = new JPanel(new GridBagLayout());
		JPanel encryptTextPanel = new JPanel(new BorderLayout());
		JPanel decryptPanel = new JPanel(new BorderLayout());
		JPanel decryptControlPanel = new JPanel(new GridBagLayout());
		JPanel decryptTextPanel = new JPanel(new BorderLayout());
        JButton encryptButton = new JButton("Encrypt");
        JLabel keyLabel = new JLabel("Secret Key");
        JButton openButton = new JButton("Open File");
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

			/*
    		 * Add text panels to JScrollPanes for Encrypt Display and Decrypt Display
    		 */

			JScrollPane encryptScroll = new JScrollPane(encryptTextDisplay); 
			JScrollPane decryptScroll = new JScrollPane(decryptTextDisplay); 

			encryptTextPanel.add(encryptScroll, BorderLayout.CENTER);
			decryptTextPanel.add(decryptScroll, BorderLayout.CENTER);
			
    		/*
    		 * Add Components to control panels using addComp Method for 
    		 * GridBagLayout manager (***Technique from Derek Banas Java Tutorial***)
			 * ***Method for adding listed below***
    		 */
    		addComp(encryptControlPanel, keyLabel, 0, 1, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
    		addComp(encryptControlPanel, keyText, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
    		addComp(encryptControlPanel, encryptButton, 2, 1, 1, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);   		
    		addComp(decryptControlPanel, openButton, 0, 0, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
			addComp(decryptControlPanel, openText, 1, 0, 1, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);			

			/*
			* Adding all elements to the encrypt and decrypt panels.
			*/

			encryptPanel.add(encryptControlPanel, BorderLayout.NORTH);
			encryptPanel.add(encryptTextPanel, BorderLayout.CENTER);
			decryptPanel.add(decryptControlPanel, BorderLayout.NORTH);
			decryptPanel.add(decryptTextPanel, BorderLayout.CENTER);
			
			/*
			* Adding the encrypt and decrypt panels to the tabbed panel.
			*/
    		tabbedPane.addTab("Encrypt Message", encryptPanel);
			tabbedPane.addTab("Decrypt Message", decryptPanel);

            /*
            * Adjusting size and opacity of text boxes
            */
			encryptTextDisplay.setColumns(30);
			encryptTextDisplay.setRows(10);
			decryptTextDisplay.setRows(10);
			decryptTextDisplay.setColumns(30);

			keyText.setColumns(15);
			openText.setOpaque(true);
				
    		
         	/*
    		 * Listeners for button presses and other event sources
    		 */
        	openButton.addActionListener(e -> openFile());

        
    		
    		/*
    		 * Setting frame size, visible and close operation
    		 */
			encryptPanel.validate();
			decryptPanel.validate();
			frame.pack();
    		frame.setMinimumSize(tabbedPane.getSize());
    		frame.setResizable(true);
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		frame.setVisible(true);
    		
    } //END CryptoMessageGUI Constructor

	/*
	* Method for opening a file and displaying its contents in the display
	*/
    private void openFile(){
        		if (fc == null){
        			fc = new JFileChooser();
            		fc .setCurrentDirectory(new java.io.File("."));
            		fc.setDialogTitle("Select Data File");
            		
        			FileNameExtensionFilter filter = new FileNameExtensionFilter("txt Files", "txt", "text");
        			fc.setFileFilter(filter);
        			fc.setAcceptAllFileFilterUsed(false);      			
        		}

        		//Show the dialog window
				int returnVal = fc.showOpenDialog(null);
				//Process the results.
    			if (returnVal == JFileChooser.APPROVE_OPTION) {
					/*----------------------------------
					Need to add parser to read file
					input to display text in JText Area
					in the Try block.
					------------------------------------*/
					
					//Setting dataDisply window.
							openText.setText("Current File: " + fc.getSelectedFile().getName());
				}

        		//Resets FileChooser
        		fc.setSelectedFile(null);
        } //END openFile() Method
 
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
	} //END MAIN

} // END CryptoMessage Class
