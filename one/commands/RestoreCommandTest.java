package one.commands;

import one.commands.RestoreCommand;

import org.junit.Before;

/**
 * this class asserts true for good restore command expressions and asserts
 * false for bad restore command expressions
 */
public class RestoreCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new RestoreCommand();
		good = new String[] { "restore from okay.docx;" };
		bad = new String[] { "restore to ape.txt;" };
	}

}
