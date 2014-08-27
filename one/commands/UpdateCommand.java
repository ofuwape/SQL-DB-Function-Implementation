package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.collections.Table;
import one.collections.TableCollection;
import one.main.MyException;
import one.main.SplitFieldNameRelopValue;
import one.main.Variables;

public class UpdateCommand extends SplitFieldNameRelopValue implements
		ICommand, Variables {
	private Pattern pattern = Pattern.compile(
			"update\\s+(.*?)\\s+set\\s+(.*?)\\s*=\\s*(.*?)" + whereClause,
			Pattern.CASE_INSENSITIVE);
	private String tableName;
	private String fieldNameRelopValue;
	private boolean whereClauseOrNot;
	private String newUpdateData;
	private String fieldNameOfFieldAboutToBeUpdated;

	@Override
	public void execute() throws MyException {
		Table t = TableCollection.getTC().getTable(tableName);
		t.updateFields(whereClauseOrNot, fieldName, relop, value,
				newUpdateData, fieldNameOfFieldAboutToBeUpdated);
	}

	@Override
	public boolean matches(String str) throws MyException {
		Matcher matcher = pattern.matcher(str);
		boolean result = matcher.matches();
		if (result) {
			whereClauseOrNot = false;
			tableName = matcher.group(1);
			fieldNameOfFieldAboutToBeUpdated = matcher.group(2);
			newUpdateData = matcher.group(3);
			fieldNameRelopValue = matcher.group(4);
			if (fieldNameRelopValue != null) {
				getFieldNameRelopValue(fieldNameRelopValue);
				whereClauseOrNot = true;
			}
		}
		return result;

	}

}
