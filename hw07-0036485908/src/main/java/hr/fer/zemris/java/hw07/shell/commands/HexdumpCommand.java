package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedReader;

import static hr.fer.zemris.java.hw07.crypto.Util.bytetohex;
import static hr.fer.zemris.java.hw07.shell.MyShell.WHITESPACE;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * The method that is used for dumping the byte contents of a given file. This
 * method can be used for debugging the file and inspection of the file's
 * storing mechanism.
 */
public class HexdumpCommand extends Command {

	/** The size of the buffer */
	public static final int BUFFER_SIZE = 16;

	/** The Constant OFFSET_FORMAT_LENGTH. */
	public static final int OFFSET_FORMAT_LENGTH = 8;

	/** The Constant ZERO. */
	public static final String ZERO = "0";

	/**
	 * The constant that identifies middle of the block when printing to the console
	 */
	public static final int MIDDLE_OF_BLOCK = 16;

	/**
	 * The constant that identifies separator of the block when printing to the
	 * console.
	 */
	public static final String SEPARATOR = "|";

	/**
	 * The constant that identifies width of the table when printing to the console
	 */
	public static final int TABLE_WIDTH = 24;

	/**
	 * The constant that defines the minimum ascii value that can be printed or the
	 * corresponding character will be replaced with a dot (.)
	 */
	public static final int ASCII_MIN = 32;

	/**
	 * The constant that defines the maximum ascii value that can be printed or the
	 * corresponding character will be replaced with a dot (.)
	 */
	public static final int ASCII_MAX = 127;

	/**
	 * Instantiates a new hexdump command and provides description that can later be
	 * obtained via help command..
	 */
	public HexdumpCommand() {
		super("hexdump", Arrays.asList("Expects a single argument: file name", "Produces hex-output"));

	}

	/**
	 * Method that is in charge of the execution of the hexdump command. The command
	 * expects a single argument: file name, and produces hex-output. On the right
	 * side of the image only a standard subset of characters is shown; for all
	 * other characters a '.' is printed instead (i.e. replaces all bytes whose
	 * value is less than 32 or greater than 127 with '.').
	 * 
	 * @return ShellStatus The enum that defines the result of the execution of the
	 *         specified command. MyShell program will end by terminating only if
	 *         there is no way of recovering from the user's invalid input.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		if (input.length != 1) {
			env.writeln(
					"Command " + getCommandName() + " takes a single argument, arguments provided: " + input.length);
			return ShellStatus.CONTINUE;
		}

		Path path = getResolved(env, input[0]);
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

	/**
	 * Method that normalizes each character of the block by checking its asci
	 * value. If the ascii value is not within the specified limits, the
	 * corresponding char is replaced by a dot(.).
	 *
	 * @param buffer
	 *            the buffer used to read the file
	 * @return the string representation of the block
	 */
	private String asciiNormalize(String buffer) {
		StringBuilder sb = new StringBuilder();

		for (char c : buffer.toCharArray()) {
			if ((int) c < ASCII_MIN || (int) c > ASCII_MAX) {
				sb.append(".");
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Prints the buffer formatted.
	 *
	 * @param env
	 *            the reference to the object that is assigned with the task of
	 *            making the communication between the user, shell and specific
	 *            command possible and uniform.
	 * @param blockOutput
	 *            the block output
	 */
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
		if (length != 2 * TABLE_WIDTH) {
			if (length < TABLE_WIDTH) {
				sb.append(appendSymbol(WHITESPACE, TABLE_WIDTH - length));
				sb.append(SEPARATOR);
				sb.append(appendSymbol(WHITESPACE, TABLE_WIDTH - 1));
			} else {
				sb.append(appendSymbol(WHITESPACE, 2 * TABLE_WIDTH - length));
			}
		}

		sb.append(WHITESPACE).append(SEPARATOR).append(WHITESPACE);

		env.write(sb.toString());
	}

	/**
	 * Prints the block offset.
	 *
	 * @param env
	 *            the referenced that is makes the communication between the user,
	 *            shell and specific command possible and unifor
	 * @param blockOffset
	 *            the block offset
	 */
	private void printBlockOffset(Environment env, int blockOffset) {
		env.write(formatOffsetOutput(blockOffset) + ":");
	}

	/**
	 * Formats offset output.
	 *
	 * @param blockOffset
	 *            the block offset
	 * @return the string representation of the block offset
	 */
	private String formatOffsetOutput(int blockOffset) {
		int length = Integer.toHexString(blockOffset).length();
		StringBuilder sb = new StringBuilder(appendSymbol(ZERO, OFFSET_FORMAT_LENGTH - length));

		sb.append(Integer.toHexString(blockOffset));

		return sb.toString();
	}

	/**
	 * Appends the specified symbol specified number of times together.
	 *
	 * @param symbol
	 *            the specified symbol
	 * @param length
	 *            the number of times the symbol is to be appended to itself
	 * @return the string representation of the appended symbol
	 */
	private String appendSymbol(String symbol, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(symbol);
		}

		return sb.toString();
	}

}
