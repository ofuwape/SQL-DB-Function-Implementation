package one.commands;

import one.commands.DefineTableCommand;

import org.junit.Before;

/**
 * this class asserts true for good definetable command expressions and asserts
 * false for bad definetable command expressions
 */
public class DefineTableCommandTest extends ICommandTest {

	@Before
	public void setUp() {
		command = new DefineTableCommand();
		good = new String[] {
				"define table alphabets having fields(a integer);",
				"define table alphabets having fields(a integer, b varchar  ,  c char (  5 ) );" };
		bad = new String[] { "define table alphabets having field(a integer, b varchar);" };
	}

}
