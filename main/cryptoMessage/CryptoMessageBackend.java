package cryptoMessage;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.security.spec.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;


public class CryptoMessageBackend {
	private byte[] salt = "CryptoMessageMakeSalt".getBytes();
	private SecretKeyFactory factory;
	private Cipher cipher;
	private final String characterSetName = "US-ASCII";
	private final int PBEPERMUTATIONS = 128;
	private final int PBEKEYSIZE = 256;
	private final Charset ASCII_CHARSET = Charset.forName(characterSetName);
	private final CharsetEncoder ASCII_ENCODER = ASCII_CHARSET.newEncoder();
	private SecureRandom rng;
	private IvParameterSpec iv;
	private String cipherName;
	public CryptoMessageBackend() {
		// Blank constructor
	}
	public void init() {
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		} catch(NoSuchAlgorithmException e) {
			// Handle no factory error
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
	private void initializeCipher(String cipherName) throws NoSuchAlgorithmException, NoSuchPaddingException {
		cipher = Cipher.getInstance(cipherName + "/CBC/PKCS5Padding");
		this.cipherName = cipherName;
		int cipherSize = 0;
		switch(cipherName) {
			case "AES":
				cipherSize = 16;
				break;
			case "DES":
				cipherSize = 7;
				break;
			case "DESede":
				cipherSize = 21;
				break;
			default:
				throw new NoSuchAlgorithmException(); // This should never be reached as it should be caught by Cipher.getInstance
		}
		byte[] ivbytes = new byte[cipherSize];
		for(int i = 0; i < cipherSize; i++) {
			ivbytes[i] = 1;
		}
	}
	
	/**
	 * Performs the actual decryption of encrypted text
	 * 
	 * @param message		Bytes to decrypt
	 * @param secret		Secret to use to decrypt the text
	 * @return Decrypted text on success, blank otherwise
	 */
	private String doDecrypt(byte[] message, String secret) {
		/* Derive the key, given password and salt. */
		KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt, PBEPERMUTATIONS, PBEKEYSIZE);
		SecretKey tmp;
		try {
			tmp = factory.generateSecret(spec);
		} catch(InvalidKeySpecException e) {
			// More error messaging maybe?
			return "";
		}
		SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), cipherName);
		try {
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv, rng);
		} catch(InvalidKeyException e) {
			return "";
		} catch(InvalidAlgorithmParameterException e) {
			return "";
		}
		try {
			return new String(cipher.doFinal(message), ASCII_CHARSET);
		} catch(Exception e) {
			// Fall through
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
			return "";
		} catch(NoSuchPaddingException e) {
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
			return "".getBytes();
		} catch(NoSuchPaddingException e) {
			return "".getBytes();
		}
		/* Derive the key, given password and salt. */
		KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt, PBEPERMUTATIONS, PBEKEYSIZE);
		SecretKey tmp;
		try {
			tmp = factory.generateSecret(spec);
		} catch(InvalidKeySpecException e) {
			// More error messaging maybe?
			return "".getBytes();
		}
		SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), cipherName);
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv, rng);
		} catch(/*InvalidKey*/Exception e) {
			return "".getBytes();
		}
		try {
			return cipher.doFinal(message.getBytes(characterSetName));
		} catch(Exception e) {
			// Fall through
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
			return "";
		} catch(NoSuchPaddingException e) {
			return "";
		}
		// Initialize our current secret key
		byte[] currentSecretKey = incrementBruteForce(new byte[1]);
		String result = doDecrypt(message, new String(currentSecretKey, ASCII_CHARSET));
		while(result.length() == 0 || !ASCII_ENCODER.canEncode(result)) {
			currentSecretKey = incrementBruteForce(currentSecretKey);
			String strValue = new String(currentSecretKey, ASCII_CHARSET);
			result = doDecrypt(message, strValue);
		}
		return result;
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
		for(int i = arr.length - 1; i >= 0; i--) {
			// If the value is 126 (max), reset it to 32 (min) and go on to the previous index
			if(arr[i] == 126) {
				arr[i] = 32;
			} else {
				// If the value isn't max then increment it and return out
				arr[i] = (byte)(arr[i] + 1);
				if(i == 0 && arr.length > 2) {
					System.err.println("Incrementing first value, new value is: <" + (new String(arr, ASCII_CHARSET)) + ">");
				}
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