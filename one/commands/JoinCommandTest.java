package one.commands;

import one.commands.JoinCommand;

import org.junit.Before;

/**
 * this class asserts true for good join command expressions and asserts false
 * for bad join command expressions
 */
public class JoinCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new JoinCommand();
		good = new String[] { "join a and b;" };
		bad = new String[] { "joint a and b;" };
	}

}
