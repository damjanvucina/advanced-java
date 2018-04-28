package hr.fer.zemris.java.hw06.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class that allows the user to encrypt/decrypt given file using the AES
 * cryptoalgorithm and the 128-bit encryption key or calculate and check the
 * SHA-256 file digest. File (message) digest is a fixed-size binary digest
 * which is calculated from arbitrary long data. Digest is depending on the used
 * algorithm always the same length, regardless of the input file length. In
 * this program, algoritm SHA-256 is used so digest is 256 bits long. Generally
 * speaking, the original data can not be reconstructed from the digest and this
 * is not what the digests are used for. Digests are used to verify if the
 * received data. Available commands are checksha, encrypt and decrypt and they
 * peform operations described above. Encryption is the process of taking data
 * (called cleartext) and a key, and producing data (ciphertext) meaningless to
 * a third-party who does not know the key. Decryption is the inverse process:
 * that of taking ciphertext and a key and producing cleartext.
 * 
 * @author Damjan Vučina
 */
public class Crypto {

	/** The constant defining arguments number in case of calculating digest. */
	public static final int CHECKSHA_ARGUMENTS_LENGTH = 2;

	/** The constant defining arguments number in case of performing encryption. */
	public static final int CRYPTING_ARGUMENTS_LENGTH = 3;

	/** The constant defining size of the used buffer. */
	public static final int BUFFER_SIZE = 4096;

	/** The constant used for identifying the used digesting algorithm. */
	public static final String DIGESTING_ALGORITHM = "SHA-256";

	/**
	 * The constant used for identifying that encrypting operation is to take place.
	 */
	public static final boolean ENCRYPTION = true;

	/**
	 * The constant used for identifying that encrypting operation is to take place.
	 */
	public static final boolean DECRYPTION = false;

	/**
	 * The main method which is stared when the program is run. Arguments are used
	 * here. Available commands are checksha, encrypt and decrypt and they peform
	 * operations described above. Checksha command as next argument expects a file
	 * whose digest is to be calculated. Encrypt command as arguments expects file
	 * to be encrypted and the location of the newly encrypted file. Dencrypt
	 * command as arguments expects file to be decrypted and the location of the
	 * newly decrypted file.
	 *
	 * @param args
	 *            the arguments used as described above. Available commands are
	 *            checksha, encrypt and decrypt.
	 */
	public static void main(String[] args) {
		if (args.length != CHECKSHA_ARGUMENTS_LENGTH && args.length != CRYPTING_ARGUMENTS_LENGTH) {
			throw new IllegalArgumentException("Number of input arguments must be either 2 or 3, was: " + args.length);
		}

		switch (args[0]) {
		case "checksha":
			processChecksha(args[1]);
			break;

		case "encrypt":
			processCrypting(args[1], args[2], ENCRYPTION);
			break;

		case "decrypt":
			processCrypting(args[1], args[2], DECRYPTION);
			break;

		default:
			throw new IllegalArgumentException(
					"Supported commands are checksha, encrypt and decrypt. Entered command: " + args[0]);
		}

	}

	/**
	 * Method used for the process of encrypting and decrypting the data. Method as
	 * arguments expects name of the file to be crypted and the name of the newly
	 * crypted file or the name of the file to be decrypted and the name of the
	 * newly decrypted file, depending on the last argument.
	 *
	 * @param inputFileName
	 *            the name of the file that is to be crpypted/decrypted, depending
	 *            on the last argument
	 * @param outputFileName
	 *            the name of the new file that is to be calculated via
	 *            crypting/decrypting, depending on the last argument
	 * @param encrypt
	 *            the identification of the encrypting/decrypting operation that is
	 *            to take place
	 */
	private static void processCrypting(String inputFileName, String outputFileName, boolean encrypt) {
		Scanner sc = new Scanner(System.in);
		String keyText = null;
		String ivText = null;

		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");
		if (sc.hasNextLine()) {
			keyText = sc.nextLine().trim();
		}

		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");
		if (sc.hasNextLine()) {
			ivText = sc.nextLine().trim();
		}

		Cipher cipher = initializeCipher(keyText, ivText, encrypt);

		try (FileInputStream inputStream = new FileInputStream(inputFileName);
				FileOutputStream outputStream = new FileOutputStream(outputFileName)) {
			byte[] buff = new byte[BUFFER_SIZE];

			while (true) {
				int r = inputStream.read(buff);
				if (r < 1)
					break;
				outputStream.write(cipher.update(buff, 0, r));
			}
			outputStream.write(cipher.doFinal());

		} catch (IOException | SecurityException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}

		System.out.print(encrypt == true ? "Encryption" : "Decryption");
		System.out.println(" completed. Generated file " + outputFileName + " based on file " + inputFileName);
		sc.close();
	}

	/**
	 * Helper method used for the purpose of initializing cipher object. Cipher
	 * provides the functionality of a cryptographic cipher used for encryption and
	 * decryption. Encryption is the process of taking data (called cleartext) and a
	 * key, and producing data (ciphertext) meaningless to a third-party who does
	 * not know the key. Decryption is the inverse process: that of taking
	 * ciphertext and a key and producing cleartext.
	 *
	 * @param keyText
	 *            the delegate in the process
	 * @param ivText
	 *            the provider in the process
	 * @param encrypt
	 *            the transformation that is to take place
	 * @return the new cipher object
	 */
	private static Cipher initializeCipher(String keyText, String ivText, boolean encrypt) {
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));

		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			System.out.println("Error creating cipher");
		}

		try {
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			System.out.println("Error initializing cipher");
		}

		return cipher;
	}

	/**
	 * Helper method used for processing message digest from the file defined via
	 * method argument. The MessageDigest class is an engine class designed to
	 * provide the functionality of cryptographically secure message digests such as
	 * SHA-256 or SHA-512. A cryptographically secure message digest takes
	 * arbitrary-sized input (a byte array), and generates a fixed-size output,
	 * called a digest or hash. MessageDigest objects are obtained by using one of
	 * the getInstance() static factory methods in the MessageDigest class. The
	 * factory method returns an initialized message digest object. It thus does not
	 * need further initialization.
	 *
	 * @param fileName
	 *            the name of the file whose digest is to be calculated
	 */
	private static void processChecksha(String fileName) {
		Scanner sc = new Scanner(System.in);
		byte[] userDigest = null;
		System.out.println("Please provide expected sha-256 digest for " + fileName);
		System.out.print("> ");
		if (sc.hasNextLine()) {
			userDigest = Util.hextobyte(sc.nextLine().trim());
		}

		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(DIGESTING_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid digesting algorithm.");
		}

		try (FileInputStream fStream = new FileInputStream(fileName)) {
			byte[] buff = new byte[BUFFER_SIZE];
			while (true) {
				int r = fStream.read(buff);
				if (r < 1)
					break;
				messageDigest.update(buff, 0, r);
			}
		} catch (IOException | SecurityException e) {
			System.out.println("Error opening file " + fileName);
		}

		byte[] actualDigest = messageDigest.digest();
		if (MessageDigest.isEqual(userDigest, actualDigest)) {
			System.out.println("Digesting completed. Digest of " + fileName + " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of " + fileName + " does not match the expected digest.");
			System.out.println("Digest was: " + Util.bytetohex(actualDigest));
		}

		sc.close();
	}

}
