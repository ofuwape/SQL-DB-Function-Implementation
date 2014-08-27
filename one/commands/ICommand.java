package one.commands;

import java.io.FileNotFoundException;

import one.main.MyException;

/**
 * this class implements the other command test classes
 */
public interface ICommand {
	/**
	 * this executes the command once the user's input string is matched with
	 * the command pattern
	 * 
	 * @throws FieldException
	 * @throws FileNotFoundException
	 */
	public void execute() throws MyException, FileNotFoundException;

	/**
	 * this matches the user's input syntactically with the command pattern
	 * 
	 * @throws MyException
	 */
	public boolean matches(String s) throws MyException;
}