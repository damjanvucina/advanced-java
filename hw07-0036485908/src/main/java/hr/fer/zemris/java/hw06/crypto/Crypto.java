package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.KeyStore.TrustedCertificateEntry;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	public static final int CHECKSHA_ARGUMENTS_LENGTH = 2;
	public static final int CRYPTING_ARGUMENTS_LENGTH = 3;
	public static final String DIGESTING_ALGORITHM = "SHA-256";
	public static final boolean ENCRYPTION = true;
	public static final boolean DECRYPTION = false;

	public static void main(String[] args) {
		if (args.length != CHECKSHA_ARGUMENTS_LENGTH && args.length != CRYPTING_ARGUMENTS_LENGTH) {
			throw new IllegalArgumentException("Number of input arguments must be either 2 or 3, was: " + args.length);
		}

		Scanner sc = new Scanner(System.in);
		switch (args[0]) {
		case "checksha":
			processChecksha(args[1], sc);
			break;

		case "encrypt":
			processCrypting(args[2], args[3], sc, ENCRYPTION);
			break;

		case "decrypt":

			break;

		default:
			break;
		}

		sc.close();
	}

	private static void processCrypting(String inputFileName, String outputFileName, Scanner sc, boolean encrypt) {
		String keyText = null;
		String ivText = null;

		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		if (sc.hasNextLine()) {
			keyText = sc.nextLine().trim();
		}

		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		if (sc.hasNextLine()) {
			ivText = sc.nextLine().trim();
		}

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));

		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			System.out.println("Error creating cipher");
			return;
		}

		try {
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			System.out.println("Error initializing cipher");
			return;
		}

		try (FileInputStream inputStream = new FileInputStream(inputFileName);
				FileOutputStream outputStream = new FileOutputStream(outputFileName)) {
			byte[] buff = new byte[4096];
			while (true) {
				int r = inputStream.read(buff);
				if (r < 1)
					break;
				outputStream.write(cipher.update(buff, 0, r), 0, r);
			}
			outputStream.write(cipher.doFinal());
		} catch (IOException | SecurityException  | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} 
		System.out.print(encrypt == true ? "Encryption" : "Decryption");
		System.out.println(" completed. Generated file " + outputFileName + " based on " + inputFileName);
	}

	private static void processChecksha(String fileName, Scanner sc) {
		byte[] userDigest = null;
		System.out.println("Please provide expected sha-256 digest for " + fileName);
		if (sc.hasNextLine()) {
			userDigest = Util.hextobyte(sc.nextLine().trim());
		}

		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(DIGESTING_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid digesting algorithm.");
			return;
		}

		// Path path = Paths.get(fileName);
		// try (BufferedReader bufferedReader = Files.newBufferedReader(path,
		// StandardCharsets.UTF_8)) {
		// String line;
		// while ((line = bufferedReader.readLine()) != null) {
		// messageDigest.update(line.getBytes(StandardCharsets.UTF_8), 0,
		// line.length());
		// }
		//
		// } catch (IOException | SecurityException e) {
		// System.out.println("Error opening file " + path);
		// return;
		// }

		try (FileInputStream fStream = new FileInputStream(fileName)) {
			byte[] buff = new byte[4096];
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
	}

}
