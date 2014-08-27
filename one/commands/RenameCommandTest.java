package one.commands;

import one.commands.RenameCommand;

import org.junit.Before;

/**
 * this class asserts true for good rename command expressions and asserts false
 * for bad rename command expressions
 */
public class RenameCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new RenameCommand();
		good = new String[] { "Rename TABLE students to Professors;" };
		bad = new String[] { "Rename table level with models;" };
	}

}
