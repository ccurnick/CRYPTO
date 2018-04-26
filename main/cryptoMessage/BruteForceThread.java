package cryptoMessage;

import javax.crypto.*;
import java.security.*;
import java.util.Arrays;

public class BruteForceThread extends Thread {
    private int stepSize;
    private byte[] encryptedText;
    private byte[] currentSecretKey;
    static public String result;
    static private Object finishedLock = new Object();
    static private boolean finished = false;
    static private Object reportingLock = new Object();
    static private int numberOfValues = 1;
    static private int maxHighestValue = 1;
    private CryptoMessageBackend backend;
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
    public static void reset() {
        synchronized(finishedLock) {
            finished = false;
        }
        synchronized(reportingLock) {
            numberOfValues = 1;
            maxHighestValue = 1;
        }
    }
    public void run() {
        while(true) {
            synchronized(finishedLock) {
                if(finished) {
                    return;
                }
            }

            try {
                String possibleResult = backend.doDecrypt(encryptedText, new String(currentSecretKey, backend.ASCII_CHARSET));
                if(possibleResult.length() != 0 && backend.ASCII_ENCODER.canEncode(possibleResult)) {
                    result = possibleResult;
                    synchronized(finishedLock) {
                        finished = true;
                    }
                }
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
        synchronized(reportingLock) {
            if(arr.length > numberOfValues) {
                System.err.println(String.format("Expanding brute force size to %d characters", arr.length));
                numberOfValues = arr.length;
            }
        }
		return arr;
	}
}