/**
 * File Name: BackendEncryptionTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves to test the backend functionality of the
 * encryption part of the application
 */
package CryptoMessageTest;

import CryptoMessage.CryptoMessageGUI;

public class BackendEncryptionTest extends TestCase {
    /**
     * Internal copy of the backend
     */
    public CryptoMessageBackend backEnd;
    /**
     * Initializes the test case
     */
    public BackendEncryptionTest() {
        super("BackendEncryptionTest", "Tests the backend functionality by " +
              "sending a plain text and password to the backend and " +
              "expecting an encrypted test block back.");
    }

    /**
     * Performs a test of the front end
     */
    public runTest() {
        backEnd = new CryptoMessageBackend();
        // pass back our plain text and password
        // validate that the expected result is returned
    }
}