package cryptoMessage;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.security.spec.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.*;

public class CryptoMessageBackend {
	private byte[] salt = "CryptoMessageMakeSalt".getBytes();
	private SecretKeyFactory factory;
	private Cipher cipher;
	private final String characterSetName = "US-ASCII";
	private final int PBEPERMUTATIONS = 128;
	private final int POOLSIZE = 10;
	protected final Charset ASCII_CHARSET = Charset.forName(characterSetName);
	protected final CharsetEncoder ASCII_ENCODER = ASCII_CHARSET.newEncoder();
	private SecureRandom rng;
	private IvParameterSpec iv;
	private int pbekeysize;
	private String cipherName;
	public String errorMessage;
	public CryptoMessageBackend() {
		// Blank constructor
	}
	public void init() {
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		} catch(NoSuchAlgorithmException e) {
			errorMessage = e.getMessage();
		}
		rng = new SecureRandom(new byte[] {0,1,2,3,4,5});
	}

	/**
	 * Initializes a cipher for use by adding the appropriate padding mechanism and setting the object
	 * Should support the following ciphers: AES, DES, DESede
	 * 
	 * @param cipherName				Name of cipher to use
	 * @throws NoSuchAlgorithmException	Thrown from Cipher.getInstance
	 * @throws NoSuchPaddingException	Thrown from Cipher.getInstance
	 */
	public void initializeCipher(String cipherName) throws NoSuchAlgorithmException, NoSuchPaddingException {
		cipher = Cipher.getInstance(cipherName + "/CBC/PKCS5Padding");
		this.cipherName = cipherName;
		int cipherSize = 0;
		switch(cipherName) {
			case "AES":
				cipherSize = 16;
				pbekeysize = 128;
				break;
			case "DES":
				cipherSize = 8;
				pbekeysize = 64;
				break;
			case "DESede":
				cipherSize = 8;
				pbekeysize = 192;
				break;
			default:
				throw new NoSuchAlgorithmException(); // This should never be reached as it should be caught by Cipher.getInstance
		}
		byte[] ivbytes = new byte[cipherSize];
		for(int i = 0; i < cipherSize; i++) {
			ivbytes[i] = 1;
		}
		iv = new IvParameterSpec(ivbytes);
	}
	
	/**
	 * Performs the actual decryption of encrypted text
	 * 
	 * @param message		Bytes to decrypt
	 * @param secret		Secret to use to decrypt the text
	 * @return Decrypted text on success, blank otherwise
	 */
	protected String doDecrypt(byte[] message, String secret) {
		/* Derive the key, given password and salt. */
		KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt, PBEPERMUTATIONS, pbekeysize);
		SecretKey tmp;
		try {
			tmp = factory.generateSecret(spec);
		} catch(InvalidKeySpecException e) {
			errorMessage = "IKS:" + e.getMessage();
			return "";
		}
		SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), cipherName);
		try {
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv, rng);
		} catch(InvalidKeyException e) {
			errorMessage = "IK:" + e.getMessage();
			return "";
		} catch(InvalidAlgorithmParameterException e) {
			errorMessage = "IAP:" + e.getMessage();
			return "";
		}
		try {
			return new String(cipher.doFinal(message), ASCII_CHARSET);
		} catch(Exception e) {
			errorMessage = "Final:" + e.getMessage();
		}
		return "";
	}
	/**
	 * External interface to perform a decryption of an encrypted text
	 * 
	 * @param message		Bytes to decrypt
	 * @param secret		Secret to use to decrypt the text
	 * @param cipherName	Name of the cipher to use (AES)
	 * @return Decrypted text on success, blank otherwise
	 */
	public String decrypt(byte[] message, String secret, String cipherName) {
		try {
			initializeCipher(cipherName);
		} catch(NoSuchAlgorithmException e) {
			errorMessage = "NSA:" + e.getMessage();
			return "";
		} catch(NoSuchPaddingException e) {
			errorMessage = "NSP:" + e.getMessage();
			return "";
		}
		return doDecrypt(message, secret);
	}
	
	/**
	 * External interface to perform an encryption of an encrypted text
	 * 
	 * @param message		Bytes to encrypt
	 * @param secret		Secret to use to encrypt the text
	 * @param cipherName	Name of the cipher to use (AES)
	 * @return Encrypted text on success, blank otherwise
	 */
	public byte[] encrypt(String message, String secret, String cipherName) { 
		try {
			initializeCipher(cipherName);
		} catch(NoSuchAlgorithmException e) {
			errorMessage = "NSA:" + e.getMessage();
			return "".getBytes();
		} catch(NoSuchPaddingException e) {
			errorMessage = "NSP:" + e.getMessage();
			return "".getBytes();
		}
		/* Derive the key, given password and salt. */
		KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt, PBEPERMUTATIONS, pbekeysize);
		SecretKey tmp;
		try {
			tmp = factory.generateSecret(spec);
		} catch(InvalidKeySpecException e) {
			errorMessage = "IKS:" + e.getMessage();
			return "".getBytes();
		}
		SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), cipherName);
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv, rng);
		} catch(/*InvalidKey*/Exception e) {
			errorMessage = "Init:" + e.getMessage();
			return "".getBytes();
		}
		try {
			return cipher.doFinal(message.getBytes(characterSetName));
		} catch(Exception e) {
			errorMessage = "Final:" + e.getMessage();
		}
		return "".getBytes();
	}

	/**
	 * External interface to perform a brute force of an encrypted text
	 * 
	 * @param message		Bytes to decrypt
	 * @param cipherName	Name of the cipher to use (AES)
	 * @return Encrypted text on success, blank on bad cipher
	 */
	public String bruteForce(byte[] message, String cipherName) {
		try {
			initializeCipher(cipherName);
		} catch(NoSuchAlgorithmException e) {
			errorMessage = "NSA:" + e.getMessage();
			return "";
		} catch(NoSuchPaddingException e) {
			errorMessage = "NSP:" + e.getMessage();
			return "";
		}
		// Ensure the brute force threads are in a clean state
		BruteForceThread.reset();
		// Initialize our current secret key
		// Create threads for performing the brute forcing
		List<BruteForceThread> threads = new ArrayList<BruteForceThread>();
		for(int i = 0; i < POOLSIZE; i++) {
			BruteForceThread thread;
			try {
				thread = new BruteForceThread(POOLSIZE, message, i, cipherName);
			} catch(NoSuchAlgorithmException e) {
				errorMessage = "NSA:" + e.getMessage();
				return "";
			} catch(NoSuchPaddingException e) {
				errorMessage = "NSP:" + e.getMessage();
				return "";
			}
			threads.add(thread);
			thread.start();
		}
		for(int i = 0; i < POOLSIZE; i++) {
			try {
				threads.get(i).join();
			} catch(InterruptedException e) {
				//silently continue
			}
		}

		return BruteForceThread.result;
	}
}