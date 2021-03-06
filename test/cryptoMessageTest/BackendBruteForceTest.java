/**
 * File Name: BackendBruteForceTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves to test the backend functionality of the
 * brute force part of the application
 */
package cryptoMessageTest;

import cryptoMessage.CryptoMessageBackend;

public class BackendBruteForceTest extends TestCase {
    /**
     * Internal copy of the backend
     */
    public CryptoMessageBackend backEnd;
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
    public BackendBruteForceTest(byte[] encryptedText, String expectedResult, String algorithm) {
        super("BackendBruteForce", "Tests the backend functionality by " +
              "sending an encrypted text to the backend and expecting " +
              "a decrypted result within a reasonable(1 minute) amount " +
              "of time.", algorithm);
        this.encryptedText = encryptedText;
        this.expectedResult = expectedResult;
    }

    /**
     * Performs a test of the back end
     */
    protected void runTest() {
        // Create the backend
        try {
            backEnd = new CryptoMessageBackend();
            backEnd.init();
        } catch(Exception e) {
            errorMessage = "Failed to create backend from initializer.";
            return;
        }
        // pass back our encrypted file contents
        // TODO:start a thread that expires after 5 minutes
        String result;
        try {
            result = backEnd.bruteForce(encryptedText, algorithm);
        } catch(Exception e) {
            errorMessage = "An exception was thrown while brute forcing.";
            return;
        }
        // validate that the expected result is returned
        if(result.compareTo(expectedResult) == 0) {
            successful = true;
        } else {
            errorMessage = String.format("Result was not the expected result.  " +
                    "Expected: %s, Received: %s\nError message: %s",
                    expectedResult, result, backEnd.errorMessage);
        }
    }
}