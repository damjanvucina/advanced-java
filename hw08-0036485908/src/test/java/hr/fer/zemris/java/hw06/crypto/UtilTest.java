package hr.fer.zemris.java.hw06.crypto;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw07.crypto.Util;

public class UtilTest {
	
	@Test
	public void hexToByteTemplate() {
		Assert.assertArrayEquals(new byte[] {1, -82, 34}, Util.hextobyte("01aE22"));
	}
	
	@Test
	public void hexToByteRegular() {
		Assert.assertArrayEquals(new byte[] {123, 62, -8, 0}, Util.hextobyte("7b3ef800"));
	}
	
	@Test
	public void hexToByteZeroLengthString() {
		Assert.assertArrayEquals(new byte[] {}, Util.hextobyte(""));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void hexToByteNonHexDigit() {
		Util.hextobyte("NN");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void hexToByteOddNumberOfDigits() {
		Util.hextobyte("1");
	}
	
	@Test
	public void hexToByteCaseInsensitivity() {
		Assert.assertArrayEquals(Util.hextobyte("0123456789ABCDEF"), Util.hextobyte("0123456789abcdef"));
	}
	
	@Test
	public void byteToHexTemplateTest() {
		Assert.assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
	}
	
	@Test
	public void byteToHexRegularTest() {
		Assert.assertEquals("d2150b21", Util.bytetohex(new byte[] {-46, 21, 11, 33}));
	}
	
	@Test(expected=NullPointerException.class)
	public void HexToByteNullArgument(){
		Util.hextobyte(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void ByteToHexNullArgument(){
		Util.bytetohex(null);
	}

}
