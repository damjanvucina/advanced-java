package hr.fer.zemris.java.hw06.crypto;


public class Util {

	public static final String HEX_DIGITS = "[0-9A-Fa-f]+";

	public static byte[] hextobyte(String keyText) {
		if (!keyText.matches(HEX_DIGITS) && keyText.length() > 0) {
			throw new IllegalArgumentException("Input string contains non-hex character");
		}
		if(keyText.length() % 2 == 1) {
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

	public static String bytetohex(byte[] byteArray) {
		StringBuilder sb = new StringBuilder(2 * byteArray.length);

		for (byte b : byteArray) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}
}
