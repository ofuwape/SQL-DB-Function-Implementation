package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.main.MyException;
import one.xml.XmlWrite;

/**
 * This class exits the program
 */
public class ExitCommand implements ICommand {

	private Pattern pattern = Pattern.compile("exit\\s*;\\s*",
			Pattern.CASE_INSENSITIVE);

	@Override
	public void execute() throws MyException {
		new XmlWrite().run();
		System.exit(0);
	}

	@Override
	public boolean matches(String str) {
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

}
