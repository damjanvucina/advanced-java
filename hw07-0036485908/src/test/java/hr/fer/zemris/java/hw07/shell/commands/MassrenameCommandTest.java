package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.hw07.shell.Dispatcher;

public class MassrenameCommandTest {
	
	Dispatcher env;
	Scanner sc;
	
	@Before
	public void setUp() {
		sc = new Scanner(System.in);
		env = new Dispatcher(sc);
	}
	
	@Test
	public void validateMassrenameArgumentsTest() {
		MassrenameCommand command = new MassrenameCommand();
		String[] input = command.splitArguments("C:\\Users\\D4MJ4N\\Desktop\\a C:\\Users\\D4MJ4N\\Desktop\\b filter \"a\"");
		command.processMassrenameArguments(env, input);
	}

}
