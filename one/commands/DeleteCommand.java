package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.collections.Table;
import one.collections.TableCollection;
import one.main.MyException;
import one.main.SplitFieldNameRelopValue;
import one.main.Variables;

public class DeleteCommand extends SplitFieldNameRelopValue implements
		ICommand, Variables {

	private Pattern pattern = Pattern.compile("delete\\s+(.*?)" + whereClause,
			Pattern.CASE_INSENSITIVE);
	private String tableName;
	private String fieldNameRelopValue;
	private boolean whereClauseOrNot;

	@Override
	public void execute() throws MyException {
		Table t = TableCollection.getTC().getTable(tableName);
		t.deleteFields(whereClauseOrNot, fieldName, relop, value);
	}

	@Override
	public boolean matches(String str) throws MyException {
		Matcher matcher = pattern.matcher(str);
		boolean result = matcher.matches();
		if (result) {
			whereClauseOrNot = false;
			tableName = matcher.group(1);
			fieldNameRelopValue = matcher.group(2);
			if (fieldNameRelopValue != null) {
				getFieldNameRelopValue(fieldNameRelopValue);
				whereClauseOrNot = true;
			}
		}
		return result;

	}

}
