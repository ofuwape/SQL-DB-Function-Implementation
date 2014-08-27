package one.commands;

import one.commands.DefineIndexCommand;

import org.junit.Before;

/**
 * this class asserts true for good defineindex command expressions and asserts false
 * for bad  defineindex command expressions
 */
public class DefineIndexCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new DefineIndexCommand();
		good = new String[] { "define index on ray (char);" };
		bad = new String[] { "defineindex on ray;" };
	}

}
