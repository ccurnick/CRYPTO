/**
 * File Name: CyptoMessageTest.java
 * Date: 9 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class tests the CryptoMessage GUI and backend using three
 * evaluations on each component.
 */
package CryptoMessageTest;

public class CryptoMessageTest {
    /**
     * List of test cases that are to be evaluated
     */
    ArrayList<TestCase> tests;
    /**
     * Simply calls the constructor which performs all of the heavy lifting
     */
    public static void main(String[] args) {
        new CryptoMessageTest();
    }

    /**
     * Main loop for the program
     */
    private CryptoMessageTest() {
        tests.append(new BackendBruteForceTest());
        tests.append(new BackendEncryptionTest());
        tests.append(new BackendDecryptionTest());
        tests.append(new E2EBruteForceTest());
        tests.append(new E2EEncryptionTest());
        tests.append(new E2EDecryptionTest());

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

        System.out.println("Total tests: {} of {} passed".format(successfulTests, tests.length));
        System.out.println("Total evaluation time: {} ms".format(evaluationTimeInMS));
    }
    private runTest(TestCase testCase) {
        System.out.println("=======BEGIN TEST======");
        System.out.println("Test Name: {}".format(testCase.getTitle()));
        System.out.println("");
        System.out.println(testCase.getDescription());
        System.out.println("");
        String result = testCase.evaluateTest();
        long evaluationTimeInMS = TimeUnit.MILLISECONDS.convert(testCase.getEvaluationTime(), TimeUnit.NANOSECONDS);
        System.out.println("Test result: {}".format(result));
        System.out.println("Test success: {}".format(testCase.isSuccessful()));
        System.out.println("Test evaluation time: {} ms".format(evaluationTimeInMS));
        System.out.println("======END TEST======");
    }
}