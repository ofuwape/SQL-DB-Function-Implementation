package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.collections.TableCollection;
import one.main.MyException;

/**
 * this removes a table from the table collection
 */
public class DropCommand implements ICommand {

	private Pattern pattern = Pattern.compile(
			"drop\\s+table\\s+(.*?)\\s*;\\s*", Pattern.CASE_INSENSITIVE);
	private String tableName = "";

	@Override
	public void execute() throws MyException {
		TableCollection.getTC().removeTable(tableName);
	}

	@Override
	public boolean matches(String str) {
		Matcher matcher = pattern.matcher(str);
		boolean result = matcher.matches();
		if (result) {
			tableName = matcher.group(1);
		}
		return result;
	}

}
