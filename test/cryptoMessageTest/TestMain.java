/**
 * File Name: CyptoMessageTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class tests the CryptoMessage GUI and backend using three
 * evaluations on each component.
 */
package cryptoMessageTest;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TestMain {
    /**
     * List of test cases that are to be evaluated
     */
    ArrayList<TestCase> tests;
    
    /**
     * Simply calls the constructor which performs all of the heavy lifting
     */
    public static void main(String[] args) {
        new TestMain();
    }

    /**
     * Main loop for the program
     */
    private TestMain() {
    	String testPassphrase = "123";
    	String testPlainText = "The quick brown fox jumped over the lazy dog.";
    	byte[] testCipherText = new byte[] {
    			-12,-60,-121,-100,126,-111,70,82,-88,-23,63,108,97,19,-20,-26,
    			42,120,89,-7,33,12,-54,54,-32,-99,8,-12,118,-36,-49,118,89,44,
    			-87,77,69,71,-9,46,10,-90,20,124,51,119,-117,-121
    	};
        // Populate our list of tests with each type
    	tests = new ArrayList<TestCase>();
        tests.add(new BackendBruteForceTest(testCipherText, testPlainText));
        tests.add(new BackendEncryptionTest(testPlainText, testCipherText, testPassphrase));
        tests.add(new BackendDecryptionTest(testCipherText, testPlainText, testPassphrase));
        tests.add(new E2EBruteForceTest(testCipherText, testPlainText));
        tests.add(new E2EEncryptionTest(testPlainText, testCipherText, testPassphrase));
        tests.add(new E2EDecryptionTest(testCipherText, testPlainText, testPassphrase));

        // Loop through and run each test
        System.out.println("Beginning evaluation of tests...");
        for (TestCase testCase : tests) {
            runTest(testCase);
        }
        System.out.println("Completed evaluation of all tests!");

        // Count the number of successful tests and how long was spent on
        // the actual tests
        int successfulTests = 0;
        long totalEvaluationTime = 0;
        for(TestCase testCase : tests) {
            if(testCase.isSuccessful()) {
                successfulTests += 1;
            }
            totalEvaluationTime += testCase.getEvaluationTime();
        }
        long evaluationTimeInMS = TimeUnit.MILLISECONDS.convert(totalEvaluationTime, TimeUnit.NANOSECONDS);

        // Summarize some output
        System.out.format("Total tests: %d of %d passed", successfulTests, tests.size());
        System.out.println("");
        System.out.format("Total evaluation time: %d ms", evaluationTimeInMS);
        System.out.println("");
    }

    /**
     * Performs a single test given a test case to run
     */
    private void runTest(TestCase testCase) {
        // Header information printed before the test is run
        System.out.println("=======BEGIN TEST======");
        System.out.format("Test Name: %s", testCase.getTitle());
        System.out.println("");
        System.out.println("");
        System.out.println(testCase.getDescription());
        System.out.println("");

        // Run the actual test and get back the results
        String result = testCase.evaluateTest();
        long evaluationTimeInMS = TimeUnit.MILLISECONDS.convert(testCase.getEvaluationTime(), TimeUnit.NANOSECONDS);

        // Print the results of the test
        System.out.format("Test result: %s", result);
        System.out.println("");
        System.out.format("Test success: %b", testCase.isSuccessful());
        System.out.println("");
        System.out.format("Test evaluation time: %d ms", evaluationTimeInMS);
        System.out.println("");
        System.out.println("======END TEST======");
    }
}