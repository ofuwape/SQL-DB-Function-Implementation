package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.collections.Table;
import one.collections.TableCollection;
import one.main.MyException;

/**
 * this prints each table and it's fields from the table collection
 */
public class PrintCommand implements ICommand {

	private Pattern pattern = Pattern.compile("print\\s+(.*?)\\s*;\\s*",
			Pattern.CASE_INSENSITIVE);
	private String input;

	@Override
	public void execute() throws MyException {

		if (input.equalsIgnoreCase("dictionary")) {
			System.out.println("Tables " + "*****************"
					+ " Field(types)");
			System.out.print(TableCollection.getTC());

		}
		if (!input.equalsIgnoreCase("dictionary")) {
			Table t = TableCollection.getTC().getTable(input);
			System.out.println(t);
			System.out.print(t.datatoString(t.readAndSaveData()));
		}
	}

	@Override
	public boolean matches(String str) {
		Matcher matcher = pattern.matcher(str);
		boolean result = matcher.matches();
		if (result) {
			input = matcher.group(1);
		}
		return result;
	}

}
