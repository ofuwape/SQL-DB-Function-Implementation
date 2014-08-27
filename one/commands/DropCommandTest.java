package one.commands;

import one.commands.DropCommand;

import org.junit.Before;

/**
 * this class asserts true for good drop command expressions and asserts false
 * for bad drop command expressions
 */
public class DropCommandTest extends ICommandTest {
	@Before
	public void setUp() {
		command = new DropCommand();
		good = new String[] { "drop table a;" };
		bad = new String[] { "droptable a;" };
	}

}
