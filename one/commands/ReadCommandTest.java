package one.commands;

import one.commands.ReadCommand;

import org.junit.Before;

/**
 * this class asserts true for good read command expressions and asserts false
 * for bad read command expressions
 */
public class ReadCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new ReadCommand();
		good = new String[] { "read ray.txt;", "read          kay.txt;" };
		bad = new String[] { "readkay.txt;" };
	}

}
