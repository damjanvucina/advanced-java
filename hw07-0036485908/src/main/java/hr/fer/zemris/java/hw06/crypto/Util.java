package hr.fer.zemris.java.hw06.crypto;

import java.util.Objects;

/**
 * Utility class used for the purpose of providing user with the methods for
 * performing transformations from byte arrays to String representations of
 * hexadecimal number and vice versa.
 * 
 * @author Damjan Vučina
 */
public class Util {

	/**
	 * The regular expression used for the purpose of validating String
	 * representations of hexadecimal numbers
	 */
	public static final String HEX_DIGITS = "[0-9A-Fa-f]+";

	/**
	 * Method used for performing transformations from String representations of
	 * hexadecimal number to byte arrays.
	 *
	 * @param keyText
	 *            String representations of hexadecimal number
	 * @return the byte[] array
	 * 
	 * @throws IllegalArgumentException
	 *             if Input string contains non-hex character or if input string
	 *             contains odd number of digits.
	 */
	public static byte[] hextobyte(String keyText) {
		Objects.requireNonNull(keyText, "Input text cannot be null.");
		
		if (!keyText.matches(HEX_DIGITS) && keyText.length() > 0) {
			throw new IllegalArgumentException("Input string contains non-hex character");
		}
		if (keyText.length() % 2 == 1) {
			throw new IllegalArgumentException("Input string must contain even number of digits.");
		}

		int length = keyText.length();
		byte[] data = new byte[length / 2];

		for (int i = 0; i < length; i += 2) {
			data[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
					+ Character.digit(keyText.charAt(i + 1), 16));
		}

		return data;
	}

	/**
	 * Method used for performing transformations from byte arrays to String
	 * representations of hexadecimal number.
	 *
	 * @param byteArray
	 *            the byte array
	 * @return String representations of hexadecimal number
	 */
	public static String bytetohex(byte[] byteArray) {
		Objects.requireNonNull(byteArray, "Input array cannot be null.");
		StringBuilder sb = new StringBuilder(2 * byteArray.length);

		for (byte b : byteArray) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}
}
