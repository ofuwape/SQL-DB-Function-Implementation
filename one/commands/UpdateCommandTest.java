package one.commands;

import one.commands.UpdateCommand;

import org.junit.Before;

/**
 * this class asserts true for good update command expressions and asserts false
 * for bad update command expressions
 */
public class UpdateCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new UpdateCommand();
		good = new String[] { "update tableR set a = b where a<b;",
				"update Table_A set bron=der;" };
		bad = new String[] { "update tableR sets a=n;" };
	}

}
