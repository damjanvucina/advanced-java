package hr.fer.zemris.java.custom.scripting.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;

public class SmartScriptParserTest {

	SmartScriptParser parser;
	
	String doc1;
	String doc2;
	String doc3;
	String doc4;
	String doc5;
	String doc6;
	String doc7;
 	String doc8;
 	String doc9;
//	String doc10;
//	String doc11;
//	String doc12;
//	String doc13;
//	String doc14;
//	String doc15;
//	String doc16;
//	String doc17;
	
	@Before
	public void initializeDocuments() {
		doc1 = loader("doc1.txt");
		doc2 = loader("doc2.txt");
		doc3 = loader("doc3.txt");
		doc4 = loader("doc4.txt");
		doc5 = loader("doc5.txt");
		doc6 = loader("doc6.txt");
		doc7 = loader("doc7.txt");
 		doc8 = loader("doc8.txt");
 		doc9 = loader("doc9.txt");
//		doc10 = loader("doc10.txt");
//		doc11 = loader("doc11.txt");
//		doc12 = loader("doc12.txt");
//		doc13 = loader("doc13.txt");
//		doc14 = loader("doc14.txt");

	}

	@Test
	public void templateRegularTest() {
		parseTwiceandAssert(doc1);
	}

	@Test
	public void escapeCurlyBracketInTextTest() {
		parser = new SmartScriptParser(doc2);
		int numberOfChildren = parser.getDocumentNode().numberOfChildren();
		Assert.assertEquals(2, numberOfChildren);
	}

	@Test
	public void tagCaseInsensitivityTest() {
		parseTwiceandAssert(doc3);
	}

	@Test
	public void disregardSpacesInForTagTest() {
		parseTwiceandAssert(doc4);
	}
	
	@Test
	public void disregardSpacesInEchoTagTest() {
		parseTwiceandAssert(doc5);
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void tooFewExpressionInForTag() {
		parseTwiceandAssert(doc6);
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void tooManyExpressionInForTag() {
		parseTwiceandAssert(doc7);
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void nonElementVariableFirstInForTag() {
		parseTwiceandAssert(doc7);
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void invalidFuncitonNameTest() {
		parseTwiceandAssert(doc8);
	}
	
	private void parseTwiceandAssert(String document) {
		parser = new SmartScriptParser(document);
		String firstIteration = parser.getDocumentNode().toString();

		parser = new SmartScriptParser(firstIteration);
		String secondIteration = parser.getDocumentNode().toString();

		Assert.assertEquals(firstIteration, secondIteration);
	}

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}
}
