/**
 * File Name: E2EBruteForceTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves to test the end-to-end functionality of the
 * brute force part of the application
 */
package cryptoMessageTest;

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
        frontEnd = new CryptoMessageGUI();
        // relink the buttons
        // click the brute force button
        // pass back our file
        // start a thread that expires after 1 minute
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