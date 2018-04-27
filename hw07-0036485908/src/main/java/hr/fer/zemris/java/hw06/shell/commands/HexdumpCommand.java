package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import static hr.fer.zemris.java.hw06.shell.MyShell.WHITESPACE;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import static hr.fer.zemris.java.hw06.crypto.Util.bytetohex;

public class HexdumpCommand extends Command {
	public static final int BUFFER_SIZE = 16;
	public static final int OFFSET_FORMAT_LENGTH = 8;
	public static final String ZERO = "0";
	public static final int MIDDLE_OF_BLOCK = 16;
	public static final String SEPARATOR = "|";
	public static final int TABLE_WIDTH = 24;
	public static final int ASCII_MIN = 32;
	public static final int ASCII_MAX = 127;

	public HexdumpCommand() {
		super("hexdump", Arrays.asList("Expects a single argument: file name", "Produces hex-output"));

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		if (input.length != 1) {
			env.writeln(
					"Command " + getCommandName() + " takes a single argument, arguments provided: " + input.length);
			return ShellStatus.CONTINUE;
		}

		Path path = Paths.get(input[0]);
		if (Files.isDirectory(path)) {
			env.writeln("Command " + getCommandName() + " argument must be a normal file, not a directory.");
			return ShellStatus.CONTINUE;
		}

		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(path, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}

		char[] buffer = new char[BUFFER_SIZE];
		String blockHex;
		int blockOffset = 0;
		String blockOutput;
		int r;
		try {
			while (true) {
				r = reader.read(buffer);
				if (r < 1)
					break;

				printBlockOffset(env, blockOffset += BUFFER_SIZE);
				blockHex = String.valueOf(Arrays.copyOf(buffer, r));
				blockOutput = bytetohex(blockHex.getBytes());
				printBufferFormatted(env, blockOutput);
				env.writeln(asciiNormalize(String.valueOf(Arrays.copyOf(buffer, r))));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ShellStatus.CONTINUE;
	}

	private String asciiNormalize(String buffer) {
		StringBuilder sb = new StringBuilder();
		
		for(char c : buffer.toCharArray()) {
			if((int) c < ASCII_MIN || (int) c > ASCII_MAX ) {
				sb.append(".");
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	private void printBufferFormatted(Environment env, String blockOutput) {
		StringBuilder sb = new StringBuilder();
		int padding = 0;
		for (char c : blockOutput.toCharArray()) {

			if (padding == MIDDLE_OF_BLOCK) {
				sb.append(SEPARATOR);
			} else if (padding % 2 == 0) {
				sb.append(WHITESPACE);
			}
			sb.append(c);
			padding++;
		}
		
		int length = sb.length();
		if(length != 2*TABLE_WIDTH) {
			if(length < TABLE_WIDTH) {
				sb.append(appendSymbol(WHITESPACE, TABLE_WIDTH - length));
				sb.append(SEPARATOR);
				sb.append(appendSymbol(WHITESPACE, TABLE_WIDTH-1));
			} else {
				sb.append(appendSymbol(WHITESPACE, 2*TABLE_WIDTH- length));
			}
		}
		
		sb.append(WHITESPACE).append(SEPARATOR).append(WHITESPACE);

		env.write(sb.toString());
	}

	// hexdump C:\Users\D4MJ4N\Desktop\a.txt
	private void printBlockOffset(Environment env, int blockOffset) {
		env.write(formatOffsetOutput(blockOffset) + ":");
	}

	private String formatOffsetOutput(int blockOffset) {
		int length = Integer.toHexString(blockOffset).length();
		StringBuilder sb = new StringBuilder(appendSymbol(ZERO, OFFSET_FORMAT_LENGTH - length));

		sb.append(Integer.toHexString(blockOffset));

		return sb.toString();
	}

	private String appendSymbol(String symbol, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(symbol);
		}

		return sb.toString();
	}

}
