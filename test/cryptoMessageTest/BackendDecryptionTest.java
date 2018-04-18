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
    public BackendDecryptionTest(byte[] encryptedText, String expectedResult,
                                 String passphrase, String algorithm) {
        super("BackendDecryptionTest", "Tests the backend functionality by " +
              "sending a ciphertext and password to the backend and " +
              "expecting a plain test block back.", algorithm);
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
            result = backEnd.decrypt(encryptedText, passphrase, algorithm);
        } catch(Exception e) {
            errorMessage = "An exception was thrown while decrypting.";
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