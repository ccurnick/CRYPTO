/**
 * File Name: E2EEncryptionTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves to test the end-to-end functionality of the
 * encryption part of the application
 */
package cryptoMessageTest;

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
            String passphrase) {
        super("E2EEncryption", "Tests the end-to-end functionality by " +
              "the actual window and simulates clicking the different " +
              "components.");
        this.plainText = plainText;
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
        // click the encrypt button
        byte[] result = new byte[1];
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