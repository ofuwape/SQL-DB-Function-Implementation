package one.commands;

import one.commands.InsertCommand;

import org.junit.Before;

/**
 * this class asserts true for good insert command expressions and asserts false
 * for bad insert command expressions
 */
public class InsertCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new InsertCommand();
		good = new String[] { "insert (4) into table;" };
		bad = new String[] { "insert case intotable;" };
	}

}
