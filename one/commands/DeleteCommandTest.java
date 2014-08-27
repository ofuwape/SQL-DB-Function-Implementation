package one.commands;

import one.commands.DeleteCommand;

import org.junit.Before;

/**
 * this class asserts true for delete command expressions and asserts false for
 * bad delete command expressions
 */
public class DeleteCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new DeleteCommand();
		good = new String[] { "Delete a; Delete a where a=b;"};
		bad = new String[] { "deletea;" };
	}

}
