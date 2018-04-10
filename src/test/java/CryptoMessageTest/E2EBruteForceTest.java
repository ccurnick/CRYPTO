/**
 * File Name: E2EBruteForceTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves to test the end-to-end functionality of the
 * brute force part of the application
 */
package CryptoMessageTest;

import CryptoMessage.CryptoMessageGUI;

public class E2EBruteForceTest extends TestCase {
    /**
     * Internal copy of the front end
     */
    public CryptoMessageGUI frontEnd;
    /**
     * Initializes the test case
     */
    public E2EBruteForceTest() {
        super("E2EBruteForce", "Tests the end-to-end functionality by " +
              "the actual window and simulates clicking the different " +
              "components.");
    }

    /**
     * Performs a test of the front end
     */
    public runTest() {
        frontEnd = new CryptoMessageGUI();
        // relink the buttons
        // click the brute force button
        // pass back our file
        // start a thread that expires after 1 minute
        // validate that the expected result is in the text field
    }
}