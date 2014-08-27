package one.commands;

import static org.junit.Assert.*;

import one.commands.ICommand;
import one.main.MyException;

import org.junit.Before;
import org.junit.Test;

/**
 * this test class is implemented by all the command test classes
 */
public abstract class ICommandTest {

	protected ICommand command;

	protected String[] good;

	protected String[] bad;

	/**
	 * this method is sets up the good and bad expressions that would be tested
	 */
	@Before
	public abstract void setUp();

	/**
	 * this tests sample good and bad expressions of the command
	 * 
	 * @throws MyException
	 */
	@Test
	public void testMatches() throws MyException {
		for (String s : good) {
			assertTrue(command.matches(s));
		}
		for (String s : bad) {
			assertFalse(command.matches(s));
		}
	}

}
