/**
 * File Name: CyptoPanelBuilder.java
 * Date: 1 MAY 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class builds the JPanels for the tabs in the GUI 
 */

package cryptoMessage;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/*
* Class for building the CryptoMessage GUI.
*/
public class CryptoPanel extends JPanel{

	public JPanel controlPanel = new JPanel(new GridBagLayout());
	public JPanel textPanel = new JPanel(new BorderLayout());
	public JButton openButton = new JButton("Open File");
	public JLabel openText = new JLabel("**File Name Appears Here**");
	public JLabel keyLabel = new JLabel("Secret Key");
	public JTextField keyText = new JTextField();
	public String[] cryptoChoice = {"AES", "DES", "DESede"};
	public JComboBox<String> cryptoDropBox = new JComboBox<String>(cryptoChoice);
	public JButton actionButton = new JButton();
	public JTextArea textDisplay = new JTextArea();
	public Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

	public CryptoPanel(String buttonText, String titleBorder,Boolean encrypt, Boolean brute){
		super(new BorderLayout());
		actionButton.setText(buttonText);

		/*
    	 * Setting borders and titles for the panels
    	 */
		actionButton.setText(buttonText);
		super.setBorder(BorderFactory.createTitledBorder(loweredetched, titleBorder));
		textPanel.setBorder(BorderFactory.createTitledBorder(loweredetched));

		/*
    	 * Add text panels to JScrollPanes for Encrypt Display and Decrypt Display
    	 */
		JScrollPane baseScroll = new JScrollPane(textDisplay); 
		textPanel.add(baseScroll, BorderLayout.CENTER);

 		/*
    	* Add Components to control panels using addComp Method for 
    	* GridBagLayout manager (***Technique from Derek Banas Java Tutorial***)
		* ***Method for adding listed below***
    	*/

		if(!encrypt){
		addComp(controlPanel, openButton, 0, 0, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
		addComp(controlPanel, openText, 1, 0, 1, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
		}
		if (!brute){
		addComp(controlPanel, keyLabel, 0, 1, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
		addComp(controlPanel, keyText, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
		};
		addComp(controlPanel, cryptoDropBox, 2, 1, 1, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
		addComp(controlPanel, actionButton, 3, 1, 1, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);

		/*
		* Adding all elements to the base panel.
		*/
		super.add(controlPanel, BorderLayout.NORTH);
		super.add(textPanel, BorderLayout.CENTER);

		/*
         * Adjusting size and opacity of text boxes
         */
		textDisplay.setColumns(30);
		textDisplay.setRows(10);
		keyText.setColumns(15);
		openText.setOpaque(true);
		super.setVisible(true);
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

} // END CryptoPanel Class
