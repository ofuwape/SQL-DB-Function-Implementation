package one.commands;

import one.commands.BackUpCommand;

import org.junit.Before;

/**
 * this class asserts true for good backup command expressions and asserts false
 * for bad backup command expressions
 */
public class BackUpCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new BackUpCommand();
		good = new String[] { "backup to a;" };
		bad = new String[] { "backup;" };
	}

}
