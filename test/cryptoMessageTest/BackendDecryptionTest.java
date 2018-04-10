/**
 * File Name: BackendDecryptionTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves to test the backend functionality of the
 * decryption part of the application
 */
package cryptoMessageTest;

import cryptoMessage.CryptoMessageBackend;

public class BackendDecryptionTest extends TestCase {
    /**
     * Internal copy of the backend
     */
    public CryptoMessageBackend backEnd;
    /**
     * Encrypted text to test
     */
    private final String encryptedText;
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
    public BackendDecryptionTest(String encryptedText, String expectedResult,
                                 String passphrase) {
        super("BackendDecryptionTest", "Tests the backend functionality by " +
              "sending a ciphertext and password to the backend and " +
              "expecting a plain test block back.");
        this.encryptedText = encryptedText;
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
        // pass back our encrypted file contents
        String result;
        try {
            result = backEnd.decrypt(encryptedText, passphrase);
        } catch(Exception e) {
            errorMessage = "An exception was thrown while decrypting.";
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