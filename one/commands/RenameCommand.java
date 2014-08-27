package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.collections.TableCollection;
import one.main.MyException;

/**
 * this renames the name of a table in the table collection
 */
public class RenameCommand implements ICommand {

	private Pattern pattern = Pattern.compile(
			"rename\\s+table\\s+(.*?)\\s+to\\s+(.*?)\\s*;\\s*",
			Pattern.CASE_INSENSITIVE);
	private String oldTableName;
	private String newTableName;

	@Override
	public void execute() throws MyException {
		TableCollection.getTC().renameTable(newTableName, oldTableName);

	}

	@Override
	public boolean matches(String str) {
		Matcher matcher = pattern.matcher(str);
		boolean result = matcher.matches();
		if (result) {
			oldTableName = matcher.group(1);
			newTableName = matcher.group(2);
		}
		return result;
	}

}
