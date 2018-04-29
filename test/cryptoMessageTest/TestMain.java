/**
 * File Name: CyptoMessageTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class tests the CryptoMessage GUI and backend using three
 * evaluations on each component.
 */
package cryptoMessageTest;

import java.util.ArrayList;
import java.util.HashMap;
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
    	byte[] testAESCipherText = new byte[] {
    			-67,-89,-110,-90,112,-105,64,-8,-62,-34,121,112,-84,-29,-84,
    			8,23,13,5,101,-20,-11,110,62,-111,91,13,-63,92,-110,-101,114,
    			42,-33,38,88,119,53,119,-118,-31,-34,101,87,0,-117,-58,45
    	};
    	byte[] testDESCipherText = new byte[] {
    			43,-11,-19,-13,117,-88,43,-73,-86,-90,-108,117,-22,91,70,45,
    			112,-16,110,-113,-1,100,77,87,69,-28,-120,-2,78,-33,54,24,
    			-111,86,-72,-94,-128,121,-95,57,-100,-7,112,-77,-123,94,-47,
    			-54
    	};
    	byte[] testDESedeCipherText = new byte[] {
    			-51,-73,8,75,-50,-27,-56,51,38,21,-43,94,-25,42,-62,-95,
    			-66,29,-75,-50,-53,-19,94,121,5,113,-77,103,-82,91,-121,
    			-123,53,58,66,17,123,-85,-39,116,16,3,126,89,39,-117,77,1
    	};
        String invalidTestPassphrase = "124";
        String invalidTestCipherText = "";
    	HashMap<String, byte[]> ciphers = new HashMap<String, byte[]>();
    	ciphers.put("AES", testAESCipherText);
    	ciphers.put("DES", testDESCipherText);
    	ciphers.put("DESede", testDESedeCipherText);
        // Populate our list of tests with each type
    	tests = new ArrayList<TestCase>();
    	for(String algorithm : ciphers.keySet()) {
            tests.add(new BackendBruteForceTest(ciphers.get(algorithm), testPlainText, algorithm));
            tests.add(new BackendEncryptionTest(testPlainText, ciphers.get(algorithm), testPassphrase, algorithm));
            tests.add(new BackendDecryptionTest(ciphers.get(algorithm), testPlainText, testPassphrase, algorithm));
            tests.add(new E2EBruteForceTest(ciphers.get(algorithm), testPlainText, algorithm));
            tests.add(new E2EEncryptionTest(testPlainText, ciphers.get(algorithm), testPassphrase, algorithm));
            tests.add(new E2EDecryptionTest(ciphers.get(algorithm), testPlainText, testPassphrase, algorithm));
            tests.add(new BackendDecryptionTest(ciphers.get(algorithm), invalidTestCipherText, invalidTestPassphrase, algorithm));
            tests.add(new E2EDecryptionTest(ciphers.get(algorithm), invalidTestCipherText, invalidTestPassphrase, algorithm));
    	}

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
        System.out.format("Test Name: %s\n", testCase.getTitle());
        System.out.format("Test algorithm: %s\n", testCase.getAlgorithm());
        System.out.println("");
        System.out.println(testCase.getDescription());
        System.out.println("");

        // Run the actual test and get back the results
        String result = testCase.evaluateTest();
        long evaluationTimeInMS = TimeUnit.MILLISECONDS.convert(testCase.getEvaluationTime(), TimeUnit.NANOSECONDS);

        // Print the results of the test
        System.out.format("Test result: %s\n", result);
        System.out.format("Test success: %b\n", testCase.isSuccessful());
        System.out.format("Test evaluation time: %d ms", evaluationTimeInMS);
        System.out.println("");
        System.out.println("======END TEST======");
    }
}