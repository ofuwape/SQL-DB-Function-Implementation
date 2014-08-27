package one.commands;

import one.commands.UnionCommand;

import org.junit.Before;

/**
 * this class asserts true for good union command expressions and asserts false
 * for bad union command expressions
 */
public class UnionCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new UnionCommand();
		good = new String[] { "union a and b;",
				"union a,b,c,d and union e,f,g,h;" };
		bad = new String[] { "union a or b;" };
	}

}
