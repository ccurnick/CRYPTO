/**
 * File Name: TestCase.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves as a template for all tests to inherit from
 */
package cryptoMessageTest;

import java.nio.charset.Charset;

public class TestCase {
    /**
     * Contains the result of the test
     * True if successful, false otherwise
     */
    protected boolean successful = false;
    /**
     * If an error occurred during the testing, this contains the message
     * If no error occurred this will be blank
     */
    protected String errorMessage;
    /**
     * The amount of time it takes for this test to evaluate
     * Measured from the start to end of the call to runTest()
     */
    private long evaluationTime;
    /**
     * The title for this test, used during the presentation of the results
     */
    protected String title;
    /**
     * A description for this test, detailing what entails
     */
    protected String description;
    /**
     * The algorithm to use for this test (AES/DES/DESede)
     */
    protected String algorithm;
    
    /**
     * Character set for UTF8
     */
	protected final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    
    
    /**
     * Standard constructor that assigns a title and a description
     * [String]title The title of the case
     * [String]description The description for the case
     */
    protected TestCase(String title, String description, String algorithm) {
        this.title = title;
        this.description = description;
        this.algorithm = algorithm;
    }

    /**
     * Placeholder function that is to be unique to children clsses and called
     * via evaluateTest
     */
    protected void runTest() {
        // Purposefully blank
    }

    /**
     * Performs an actual evaluation of this test and returns the error message, if set
     * After this is called, the evaluation time, error message and success state are all set
     */
    public String evaluateTest() {
        // Start a timer before we run the test
        long startTime = System.nanoTime();
        // Run the actual test
        try {
            runTest();
        } catch(Exception e) {
            // If an exception is thrown catch it here so that it doesn't bubble out of the test
            successful = false;
            errorMessage = "Uncaught exception occurred: " + e.getLocalizedMessage();
        }
        // Measure how long the test took to run
        long endTime = System.nanoTime();
        this.evaluationTime = endTime - startTime;
        // Return the error message that was set (if any)
        return errorMessage;
    }

    /**
     * Gets successuful state
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * Gets error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Gets evaluation time
     */
    public long getEvaluationTime() {
        return evaluationTime;
    }

    /**
     * Gets title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets algorithm
     */
    public String getAlgorithm() {
        return algorithm;
    }
}