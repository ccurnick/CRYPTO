/**
 * File Name: E2EEncryptionTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves to test the end-to-end functionality of the
 * encryption part of the application
 */
package cryptoMessageTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import cryptoMessage.CryptoMessageGUI;

public class E2EEncryptionTest extends TestCase {
    /**
     * Internal copy of the front end
     */
    public CryptoMessageGUI frontEnd;
    /**
     * Plain text to test
     */
    private final String plainText;
    /**
     * Passphrase to use to encrypt
     */
    private final String passphrase;
    /**
     * Expected result from the brute force
     */
    private final byte[] expectedResult;
    /**
     * Initializes the test case
     */
    public E2EEncryptionTest(String plainText, byte[] expectedResult,
            String passphrase, String algorithm) {
        super("E2EEncryption", "Tests the end-to-end functionality by " +
              "the actual window and simulates clicking the different " +
              "components.", algorithm);
        this.plainText = plainText;
        this.expectedResult = expectedResult;
        this.passphrase = passphrase;
    }

    /**
     * Performs a test of the front end
     */
    protected void runTest() {
        // Create the frontend
        frontEnd = new CryptoMessageGUI();
        // relink the buttons (bypassing the file selection)
        frontEnd.encryptButton.addActionListener(e -> frontEnd.encryptText());
        // Select the appropriate algorithm
        frontEnd.cryptoDropBox.setSelectedItem(algorithm);
        // populate the passphrase
        frontEnd.keyText.setText(passphrase);
        // Populate the text to encrypt appropriately
        frontEnd.encryptTextDisplay.setText(plainText);
        // pass back our file
        File tempFile;
        try {
            tempFile = File.createTempFile("GUI-test-", ".tmp");
        } catch(IOException e) {
            errorMessage = e.getMessage();
            return;
        }
        frontEnd.openFile = tempFile;
        // Simulate clicking the decrypt button
        frontEnd.encryptButton.doClick();
        // Pull the result from the text field
		Path filePath = Paths.get(tempFile.getAbsolutePath());
		byte[] result = null;
		try{
		    result = Files.readAllBytes(filePath);
		} catch (IOException e) {
            errorMessage = e.getMessage();
            return;
		}
        // validate that the expected result is in the text field
        if(Arrays.equals(result,expectedResult)) {
            successful = true;
        } else {
            errorMessage = String.format("Result was not the expected result.  " +
                    "Expected: %s, Received: %s",
                    expectedResult, result);
        }
        tempFile.delete();
    }
}