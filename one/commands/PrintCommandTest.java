package one.commands;

import one.commands.PrintCommand;

import org.junit.Before;

/**
 * this class asserts true for good print command expressions and asserts false
 * for bad  print command expressions
 */
public class PrintCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new PrintCommand();
		good = new String[] { "PRINT table_a;print dictionary;" };
		bad = new String[] { "printtableb;" };
	}

}
