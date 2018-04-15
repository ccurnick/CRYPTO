/**
 * File Name: E2EDecryptionTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves to test the end-to-end functionality of the
 * decryption part of the application
 */
package cryptoMessageTest;

import cryptoMessage.CryptoMessageGUI;

public class E2EDecryptionTest extends TestCase {
    /**
     * Internal copy of the front end
     */
    public CryptoMessageGUI frontEnd;
    /**
     * Encrypted text to test
     */
    private final byte[] encryptedText;
    /**
     * Passphrase to use to decrypt
     */
    private final String passphrase;
    /**
     * Expected result from the decryption
     */
    private final String expectedResult;
    /**
     * Initializes the test case
     */
    public E2EDecryptionTest(byte[] encryptedText, String expectedResult,
            String passphrase) {
        super("E2EDecryption", "Tests the end-to-end functionality by " +
              "the actual window and simulates clicking the different " +
              "components.");
        this.encryptedText = encryptedText;
        this.expectedResult = expectedResult;
        this.passphrase = passphrase;
    }

    /**
     * Performs a test of the front end
     */
    protected void runTest() {
        frontEnd = new CryptoMessageGUI();
        // relink the buttons
        // populate the passphrase with "abc"
        // click the decrypt button
        // pass back our file
        String result = "";
        // TODO: pull the result from the text field
        // validate that the expected result is in the text field
        if(result == expectedResult) {
            successful = true;
        } else {
            errorMessage = String.format("Result was not the expected result.  " +
                    "Expected: %s, Received: %s",
                    expectedResult, result);
        }
    }
}