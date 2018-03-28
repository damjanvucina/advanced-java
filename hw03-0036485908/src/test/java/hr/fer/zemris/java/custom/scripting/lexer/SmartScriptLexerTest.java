package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class SmartScriptLexerTest {
	
	public SmartScriptLexer lexer;
	
	@Test(expected=SmartScriptLexerException.class)
	public void initializeWithNullText() {
		lexer = new SmartScriptLexer(null);
	}
	
	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}
	
	@Test
	public void testEmptyString() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals("Empty input must generate only EOF token.", SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void successiveGettingSameTokenTest() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		SmartScriptToken token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testExcThrowingAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}
	
	@Test
	public void spacesTabsNewLinesTest() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
		
		//first returns empty TEXT tag then EOF tag
		assertEquals("Input had no content. Lexer should generated only EOF token.", SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals("Input had no content. Lexer should generated only EOF token.", SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testInvalidEscape() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \\a    ");

		lexer.nextToken();
	}
	
	@Test
	public void testEchoTagContent() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is {$= i $}-th time this message is generated.");

		SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "This is "),
			new SmartScriptToken(SmartScriptTokenType.TAG_START, "{$"),
			new SmartScriptToken(SmartScriptTokenType.TAG, "="),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
			new SmartScriptToken(SmartScriptTokenType.TAG_END, "$}"),
			new SmartScriptToken(SmartScriptTokenType.TEXT, "-th time this message is generated."),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testForTagContent() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i 1 10 1 $}");

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, ""),
			new SmartScriptToken(SmartScriptTokenType.TAG_START, "{$"),
			new SmartScriptToken(SmartScriptTokenType.TAG, "FOR"),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, "1"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, "10"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, "1"),
			new SmartScriptToken(SmartScriptTokenType.TAG_END, "$}"),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testMinusAndFunctionAndDouble() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i -1 -1.11 \"IAmAStringTest\" $}");

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, ""),
			new SmartScriptToken(SmartScriptTokenType.TAG_START, "{$"),
			new SmartScriptToken(SmartScriptTokenType.TAG, "FOR"),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, "-1"),
			new SmartScriptToken(SmartScriptTokenType.DOUBLE, "-1.11"),
			new SmartScriptToken(SmartScriptTokenType.STRING, "IAmAStringTest"),
			new SmartScriptToken(SmartScriptTokenType.TAG_END, "$}"),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testEscapingStringInText() {
		SmartScriptLexer lexer = new SmartScriptLexer("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}");

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "A tag follows "),
			new SmartScriptToken(SmartScriptTokenType.TAG_START, "{$"),
			new SmartScriptToken(SmartScriptTokenType.TAG, "="),
			new SmartScriptToken(SmartScriptTokenType.STRING, "Joe \"Long\" Smith"),
			new SmartScriptToken(SmartScriptTokenType.TAG_END, "$}"),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	
	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
		int counter = 0;
		for(SmartScriptToken expected : correctData) {
			SmartScriptToken actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}

	

}
