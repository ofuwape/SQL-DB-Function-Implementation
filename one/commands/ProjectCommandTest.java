package one.commands;

import one.commands.ProjectCommand;

import org.junit.Before;

/**
 * this class asserts true for good project command expressions and asserts
 * false for bad project command expressions
 */
public class ProjectCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new ProjectCommand();
		good = new String[] { "Project table a over b;" };
		bad = new String[] { "Project a;" };
	}

}
