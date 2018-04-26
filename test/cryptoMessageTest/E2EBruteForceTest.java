/**
 * File Name: E2EBruteForceTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves to test the end-to-end functionality of the
 * brute force part of the application
 */
package cryptoMessageTest;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import cryptoMessage.CryptoMessageGUI;

public class E2EBruteForceTest extends TestCase {
    /**
     * Internal copy of the front end
     */
    public CryptoMessageGUI frontEnd;
    /**
     * Encrypted text to test
     */
    private final byte[] encryptedText;
    /**
     * Expected result from the brute force
     */
    private final String expectedResult;
    /**
     * Initializes the test case
     */
    public E2EBruteForceTest(byte[] encryptedText, String expectedResult, String algorithm) {
        super("E2EBruteForce", "Tests the end-to-end functionality by " +
                "the actual window and simulates clicking the different " +
                "components.", algorithm);
	    this.encryptedText = encryptedText;
	    this.expectedResult = expectedResult;
    }

    /**
     * Performs a test of the front end
     */
    protected void runTest() {
        // Create the frontend
        frontEnd = new CryptoMessageGUI();
        // relink the buttons
        frontEnd.bruteForceButton.addActionListener(e -> frontEnd.bruteForceText());
        // Select the appropriate algorithm
        frontEnd.bruteDropBox.setSelectedItem(algorithm);
        // pass back our file
        File tempFile;
        try {
            tempFile = File.createTempFile("GUI-test-", ".tmp");
        } catch(IOException e) {
            errorMessage = e.getMessage();
            return;
        }
        try (FileOutputStream fos = new FileOutputStream(tempFile.getAbsolutePath())){
            fos.write(encryptedText);
        } catch (IOException e) {
            errorMessage = e.getMessage();
            return;
        }
        frontEnd.openFile = tempFile;
        // Simulate clicking the decrypt button
        frontEnd.bruteForceButton.doClick();
        // Pull the result from the text field
        String result = frontEnd.bruteTextDisplay.getText();
        // validate that the expected result is in the text field
        if(result.compareTo(expectedResult) == 0) {
            successful = true;
        } else {
            errorMessage = String.format("Result was not the expected result.  " +
                    "Expected: %s, Received: %s",
                    expectedResult, result);
        }
        tempFile.delete();
    }
}