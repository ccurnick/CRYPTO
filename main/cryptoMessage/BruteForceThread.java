/**
 * File Name: BruteForceThread.java
 * Date: 30 APR 2018
 * Author: Team Crypto 2018! 
 * Purpose: This class serves to represent a single thread as part of the
 *          bruteforcing process
 * 
 * The brute forcing implementation involves sharing the load between X
 * number of threads that are specified by the stepSize.  Depending on what
 * the stepSize is set to, each thread will jump a certain number of
 * characters to ensure complete coverage.
 * 
 * For example, if the stepSize is set to 3, and the first 18 letters are to
 * be checked, then the load will be shared by:
 * 
 * A - 1        B - 2       C - 3       D - 1       E - 2       F - 3
 * G - 1        H - 2       I - 3       J - 1       K - 2       L - 3
 * M - 1        N - 2       O - 3       P - 1       Q - 2       R - 3
 */
package cryptoMessage;

import javax.crypto.*;
import java.security.*;
import java.util.Arrays;

public class BruteForceThread extends Thread {
    // Size to iterate the desired key by
    private int stepSize;
    // Text that is to be decrypted
    private byte[] encryptedText;
    // The current key that is being attempted
    private byte[] currentSecretKey;
    // Result of the decryption process, when it is successful
    static public String result;
    // Lock object for all brute force threads to notify when a pass was successful
    static private Object finishedLock = new Object();
    // Set to true when a thread has a successful decryption
    static private boolean finished = false;
    // Lock object for printing to stderr the brute force progress
    static private Object reportingLock = new Object();
    // Number of characters that currently exist in the brute force algorithm
    static private int numberOfValues = 1;
    // Highest value in the big-endian byte (in 'ABCD', this will be 'A')
    static private int maxHighestValue = 1;
    // Internal instance of the backend used to attempt the decrypting with
    private CryptoMessageBackend backend;
    /**
     * Constructor
     * Sets several values internally as well as setting the currentSecretKey
     * based off the offset appropriately
     * 
     * @param stepSize Size to increment the test key by
     * @param encryptedText Text that will be decrypted
     * @param offset Offset of this specific thread in the pool
     * @param cipherName Name of the cipher to use
     */
    public BruteForceThread(int stepSize, byte[] encryptedText, int offset, String cipherName) throws NoSuchAlgorithmException, NoSuchPaddingException {
        super();
        // Initialize private members
        this.stepSize = stepSize;
        this.encryptedText = encryptedText;
        // Manually increment the offset
        currentSecretKey = new byte[1];
        currentSecretKey[0] = (byte)(32 + offset);
        backend = new CryptoMessageBackend();
        backend.init();
        backend.initializeCipher(cipherName);
    }
    /**
     * Resets the states of all threads to a clean state
     */
    public static void reset() {
        synchronized(finishedLock) {
            finished = false;
        }
        synchronized(reportingLock) {
            numberOfValues = 1;
            maxHighestValue = 1;
        }
    }
    /**
     * Continuously attempts to brute force the encrypted text until a single
     * thread reports as completed
     */
    public void run() {
        // Loop forever
        while(true) {
            // Check if another thread(or this one) reported as being finished
            // If it has, then return out and end this thread
            synchronized(finishedLock) {
                if(finished) {
                    return;
                }
            }

            try {
                // Attempt to decrypt the encrpyted text with the current key
                String possibleResult = backend.doDecrypt(encryptedText, new String(currentSecretKey, backend.ASCII_CHARSET));
                // If the decryption was successful, then set the result and
                // finished status so the other threads can end
                if(possibleResult.length() != 0 && backend.ASCII_ENCODER.canEncode(possibleResult)) {
                    result = possibleResult;
                    synchronized(finishedLock) {
                        finished = true;
                    }
                }
                // Calculate the next secret key to use
                currentSecretKey = incrementBruteForce(currentSecretKey);
            } catch(Exception e) {
                //wait
            }
        }
    }

	/**
	 * Increments a possible brute force password value
	 * Increases from ABC -> ABD or from ZZZ -> AAAA
	 * 
	 * @param arr	Bytes to increase value of
	 * @return 		New password value to attempt
	 */
	private byte[] incrementBruteForce(byte[] arr) {
        // Loop through each character, starting with the final character
        final int lastIdx = arr.length - 1;
        byte remainder;
        if(arr[lastIdx] + stepSize >= 126) {
            remainder = (byte)(stepSize - 126 + arr[lastIdx]);
            arr[lastIdx] = (byte)(remainder + 32);
            for(int i = arr.length - 2; i >= 0; i--) {
                // If the value is 126 (max), reset it to 32 (min) and go on to the previous index
                if(arr[i] == 126) {
                    arr[i] = 32;
                } else {
                    // If the value isn't max then increment it and return out
                    arr[i] += 1;
                    // If the first character has increased, and no other thread has
                    // reported it, then report it out to stderr
                    if(i == 0 && arr.length > 2) {
                        synchronized(reportingLock) {
                            if(arr[i] > maxHighestValue) {
                                System.err.println("Incrementing first value, new value is: <" + Arrays.toString(arr) + ">");
                                maxHighestValue = arr[i];
                            }
                        }
                    }
                    return arr;
                }
            }
        } else {
            arr[lastIdx] += stepSize;
            return arr;
        }
		// If we made it to here then every character was incremented
		// This means an additional character needs to be added
        // Example: ZZ + 1 = AAA (not exactly but it explains it)
		arr = new byte[arr.length + 1];
		for(int i = 0; i < arr.length - 1; i++) {
			arr[i] = 32;
        }
        arr[arr.length - 1] = (byte)(32 + remainder);
        // If the character set size has increased, and no other thread has
        // reported it, then report it out to stderr
        synchronized(reportingLock) {
            if(arr.length > numberOfValues) {
                System.err.println(String.format("Expanding brute force size to %d characters", arr.length));
                numberOfValues = arr.length;
            }
        }
		return arr;
	}
}