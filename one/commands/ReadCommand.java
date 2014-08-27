package one.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.main.Main;
import one.main.MyException;

/**
 * this reads commands from a file address and processes the commands from the
 * file
 */
public class ReadCommand implements ICommand {

	private Pattern pattern = Pattern.compile("read\\s+'(.*?)'\\s*;\\s*",
			Pattern.CASE_INSENSITIVE);

	private String storeString;

	@Override
	public void execute() throws MyException {
		{
			Scanner rescan;
			try {
				rescan = new Scanner(new File(storeString));
			} catch (FileNotFoundException e) {
				throw new MyException(
						"Sorry you typed the name of a file that does not exist",
						e);
			}
			new Main().read(rescan);
			rescan.close();
		}
	}

	@Override
	public boolean matches(String str) {
		Matcher matcher = pattern.matcher(str);
		boolean result = matcher.matches();
		if (result) {
			storeString = matcher.group(1);
		}
		return result;
	}

}
