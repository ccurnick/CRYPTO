package cryptoMessage;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.security.spec.*;
import java.util.Arrays;
import java.math.BigInteger;
import java.nio.charset.Charset;


public class CryptoMessageBackend {
	private byte[] salt = "CryptoMessageMakeSalt".getBytes();
	private SecretKeyFactory factory;
	private Cipher cipher;
	private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	private SecureRandom rng;
	private IvParameterSpec iv;
	public CryptoMessageBackend() {
		// Blank constructor
	}
	public void init() {
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		} catch(NoSuchAlgorithmException e) {
			// Handle no factory error
		}
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch(NoSuchAlgorithmException e) {
			// Handle no algorithm error
		} catch(NoSuchPaddingException e) {
			// Handle no padding error
		}
		rng = new SecureRandom(new byte[] {0,1,2,3,4,5});
		byte[] ivbytes = new byte[16];
		for(int i = 0; i < 16; i++) {
			ivbytes[i] = 1;
		}
		iv = new IvParameterSpec(ivbytes);
	}
	public String decrypt(byte[] message, String secret) {
		/* Derive the key, given password and salt. */
		KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt, 65536, 256);
		SecretKey tmp;
		try {
			tmp = factory.generateSecret(spec);
		} catch(InvalidKeySpecException e) {
			// More error messaging maybe?
			return "";
		}
		SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
		try {
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv, rng);
		} catch(InvalidKeyException e) {
			return "";
		} catch(InvalidAlgorithmParameterException e) {
			return "";
		}
		try {
			return new String(cipher.doFinal(message), UTF8_CHARSET);
		} catch(Exception e) {
			// Fall through
		}
		return "";
	}
	public byte[] encrypt(String message, String secret) { 
		/* Derive the key, given password and salt. */
		KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt, 65536, 256);
		SecretKey tmp;
		try {
			tmp = factory.generateSecret(spec);
		} catch(InvalidKeySpecException e) {
			// More error messaging maybe?
			return "".getBytes();
		}
		SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv, rng);
	        final AlgorithmParameters params = cipher.getParameters();
	        System.out.println(params.getAlgorithm());
		} catch(/*InvalidKey*/Exception e) {
			return "".getBytes();
		}
		try {
			return cipher.doFinal(message.getBytes("UTF-8"));
		} catch(Exception e) {
			// Fall through
		}
		return "".getBytes();
	}

	public String bruteForce(byte[] message) {
		// Initialize our current secret key
		byte[] currentSecretKey = incrementBruteForce(new byte[1]);
		String result = decrypt(message, new String(currentSecretKey, UTF8_CHARSET));
		while(result.length() == 0) {
			currentSecretKey = incrementBruteForce(currentSecretKey);
			result = decrypt(message, new String(currentSecretKey, UTF8_CHARSET));
		}
		return result;
	}

	private byte[] incrementBruteForce(byte[] arr) {
		// Loop through each character, starting with the final character
		for(int i = arr.length - 1; i >= 0; i--) {
			// If the value is 126 (max), reset it to 32 (min) and go on to the previous index
			if(arr[i] == 126) {
				arr[i] = 32;
			} else {
				// If the value isn't max then increment it and return out
				arr[i] = (byte)(arr[i] + 1);
				return arr;
			}
		}
		// If we made it to here then every character was incremented
		// This means an additional character needs to be added
		// Example: ZZ + 1 = AAA (not exactly but it explains it)
		arr = new byte[arr.length + 1];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = 32;
		}
		System.err.println(String.format("Expanding brute force size to %d characters", arr.length));
		return arr;
	}
}