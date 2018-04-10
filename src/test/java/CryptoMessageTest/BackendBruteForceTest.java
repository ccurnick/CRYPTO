/**
 * File Name: BackendBruteForceTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves to test the backend functionality of the
 * brute force part of the application
 */
package CryptoMessageTest;

import CryptoMessage.CryptoMessageGUI;

public class BackendBruteForceTest extends TestCase {
    /**
     * Internal copy of the backend
     */
    public CryptoMessageBackend backEnd;
    /**
     * Initializes the test case
     */
    public BackendBruteForceTest() {
        super("BackendBruteForce", "Tests the backend functionality by " +
              "sending an encrypted text to the backend and expecting " +
              "a decrypted result within a reasonable(1 minute) amount " +
              "of time.");
    }

    /**
     * Performs a test of the back end
     */
    public runTest() {
        backEnd = new CryptoMessageBackend();
        // pass back our encrypted file contents
        // start a thread that expires after 1 minute
        // validate that the expected result is returned
    }
}