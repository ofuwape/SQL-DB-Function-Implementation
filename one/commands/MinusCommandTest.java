package one.commands;

import one.commands.MinusCommand;

import org.junit.Before;

/**
 * this class asserts true for good minus command expressions and asserts false
 * for bad minus command expressions
 */
public class MinusCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new MinusCommand();
		good = new String[] { "minus a and b;" };
		bad = new String[] { "minusb;" };
	}

}
