package one.commands;

import one.commands.SelectCommand;

import org.junit.Before;

/**
 * this class asserts true for good select command expressions and asserts false
 * for bad select command expressions
 */
public class SelectCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new SelectCommand();
		good = new String[] { "select a where a>f;" };
		bad = new String[] { "selects a;" };
	}

}
