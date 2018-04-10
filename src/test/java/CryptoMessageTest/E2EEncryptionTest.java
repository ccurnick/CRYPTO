/**
 * File Name: E2EEncryptionTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves to test the end-to-end functionality of the
 * encryption part of the application
 */
package CryptoMessageTest;

import CryptoMessage.CryptoMessageGUI;

public class E2EEncryptionTest extends TestCase {
    /**
     * Internal copy of the front end
     */
    public CryptoMessageGUI frontEnd;
    /**
     * Initializes the test case
     */
    public E2EEncryptionTest() {
        super("E2EEncryption", "Tests the end-to-end functionality by " +
              "the actual window and simulates clicking the different " +
              "components.");
    }

    /**
     * Performs a test of the front end
     */
    public runTest() {
        frontEnd = new CryptoMessageGUI();
        // relink the buttons
        // populate the passphrase with "abc"
        // click the encrypt button
        // pass back our file
        // validate that the expected result is in the text field
    }
}