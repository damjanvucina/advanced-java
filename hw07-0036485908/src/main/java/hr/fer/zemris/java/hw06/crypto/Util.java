package hr.fer.zemris.java.hw06.crypto;

import java.util.Arrays;

public class Util {

	public static final int HEX_RADIX = 16;

	public static byte[] hextobyte(String keyText) {
		if (keyText.length() != 32) {
			throw new IllegalArgumentException(
					"Input string must contain exactly 32 hex characters, was: " + keyText.length());

		}
		if (hasInvalidCharacters(keyText)) {
			throw new IllegalArgumentException("Input string contains non-hex character");
		}

		int length = keyText.length();
		byte[] data = new byte[length / 2];

		for (int i = 0; i < length; i += 2) {
			data[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
					+ Character.digit(keyText.charAt(i + 1), 16));
		}
		
		return data;
	}

	public static String bytetohex(byte[] byteArray) {
		StringBuilder sb = new StringBuilder(2 * byteArray.length);

		for (byte b : byteArray) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

	private static boolean hasInvalidCharacters(String keyText) {
		try {
			Integer.parseInt(keyText, HEX_RADIX);
			return false;
			
		} catch (NumberFormatException e) {
			return true;
		}
	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString(hextobyte("01aE22")));
		System.out.println(bytetohex(new byte[] { 1, -82, 34 }));
	}
}
