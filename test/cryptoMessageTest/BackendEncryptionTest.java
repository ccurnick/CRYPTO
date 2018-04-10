/**
 * File Name: BackendEncryptionTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves to test the backend functionality of the
 * encryption part of the application
 */
package cryptoMessageTest;

import cryptoMessage.CryptoMessageBackend;

public class BackendEncryptionTest extends TestCase {
    /**
     * Internal copy of the backend
     */
    public CryptoMessageBackend backEnd;
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
    private final String expectedResult;
    /**
     * Initializes the test case
     */
    public BackendEncryptionTest(String plainText, String expectedResult,
                                 String passphrase) {
        super("BackendEncryptionTest", "Tests the backend functionality by " +
              "sending a plain text and password to the backend and " +
              "expecting an encrypted test block back.");
        this.plainText = plainText;
        this.expectedResult = expectedResult;
        this.passphrase = passphrase;
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
        // pass back our plain text file contents
        String result;
        try {
            result = backEnd.encrypt(plainText, passphrase);
        } catch(Exception e) {
            errorMessage = "An exception was thrown while encrypting.";
            return;
        }
        // validate that the expected result is returned
        if(result == expectedResult) {
            successful = true;
        } else {
            errorMessage = String.format("Result was not the expected result.  " +
                    "Expected: %s, Received: %s",
                    expectedResult, result);
        }
    }
}