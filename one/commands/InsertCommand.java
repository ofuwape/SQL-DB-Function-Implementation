package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.collections.TableCollection;
import one.main.MyException;
import one.main.Variables;

public class InsertCommand implements ICommand, Variables {

	private Pattern pattern = Pattern.compile(
			"insert\\s*\\((.*?)\\)\\s*into\\s+(.*?)\\s*;\\s*",
			Pattern.CASE_INSENSITIVE);
	private String data, tableName;

	@Override
	public void execute() throws MyException {
		String[] splits = data.split(",");
		for (int i = 0; i < splits.length; i++) {
			splits[i] = splits[i].trim();
		}
		TableCollection.getTC().getTable(tableName).createTypes(splits);
	}

	@Override
	public boolean matches(String str) {
		Matcher matcher = pattern.matcher(str);
		boolean result = matcher.matches();
		if (result) {
			data = matcher.group(1);
			tableName = matcher.group(2);
		}
		return result;
	}

}