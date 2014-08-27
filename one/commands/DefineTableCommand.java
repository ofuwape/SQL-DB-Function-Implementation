package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.collections.Table;
import one.collections.TableCollection;
import one.main.MyException;
import one.field.Field;

/**
 * this creates a table and adds it to the table collection
 */
public class DefineTableCommand implements ICommand {

	private Pattern pattern = Pattern
			.compile(
					"Define\\s+table\\s+(.*?)\\s+having\\s+fields\\s*\\((.*?)\\)\\s*;\\s*",
					Pattern.CASE_INSENSITIVE);
	private String fieldsAndTypes;
	private String tableName;

	@Override
	public void execute() throws MyException {
		TableCollection tc = TableCollection.getTC();

		Table t = new Table(tableName);
		String splitted[];
		for (String string : fieldsAndTypes.split(",")) {
			splitted = (string.trim()).split("\\s+", 2);
			try {
				Field f = t.typeFactory(splitted[0], splitted[1]);
				t.addField(f);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new MyException("You entered an Invalid Field", e);
			}
		}
		tc.addTable(t);

	}

	@Override
	public boolean matches(String str) {
		Matcher matcher = pattern.matcher(str);
		boolean result = matcher.matches();
		if (result) {
			tableName = matcher.group(1);
			fieldsAndTypes = matcher.group(2);
		}
		return result;

	}

}
