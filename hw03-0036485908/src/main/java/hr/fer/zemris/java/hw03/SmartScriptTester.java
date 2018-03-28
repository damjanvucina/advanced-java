package hr.fer.zemris.java.hw03;

import java.util.Objects;
import java.io.IOException;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {

	public static void main(String[] args) throws IOException {

		// String docBody = new String(
		// Files.readAllBytes(Paths.get("C:\\Workspaces\\JAVA\\hw03-0036485908\\examples\\doc1.txt")),
		// StandardCharsets.UTF_8);

		String docBody = "This is sample text.\r\n" + 
				"{$ FOR i 1 10 1 $}\r\n" + 
				"This is {$= i $}-th time this message is generated.\r\n" + 
				"{$END$}\r\n" + 
				"{$FOR i 0 10 2 $}\r\n" + 
				"sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" + 
				"{$END$}";
		SmartScriptParser parser = null;

		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
		// content of docBody
	}

	private static String createOriginalDocumentBody(DocumentNode document) {
		document = Objects.requireNonNull(document, "Document cannot be null");

		return document.toString();
	}

}
