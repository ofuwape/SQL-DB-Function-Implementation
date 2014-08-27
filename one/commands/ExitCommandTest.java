package one.commands;

import one.commands.ExitCommand;

import org.junit.Before;

/**
 * this class asserts true for good exit command expressions and asserts false
 * for bad exit command expressions
 */
public class ExitCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new ExitCommand();
		good = new String[] { "exit;", "EXIT;" };
		bad = new String[] { "exits;" };
	}

}
