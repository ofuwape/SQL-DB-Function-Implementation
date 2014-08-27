package one.commands;

import one.commands.IntersectCommand;

import org.junit.Before;

/**
 * this class asserts true for good intersect command expressions and asserts false
 * for bad  intersect command expressions
 */
public class IntersectCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new IntersectCommand();
		good = new String[] { "INTERsect at and bt;" };
		bad = new String[] { "intersect tableXand b;" };
	}

}
