package one.commands;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.collections.DataInterface;
import one.main.MyException;
import one.main.RelationalOperations;
import one.main.Variables;

public class SelectCommand extends RelationalOperations implements ICommand,
		Variables {

	private Pattern pattern = Pattern.compile("select\\s+(.*?)" + whereClause,
			Pattern.CASE_INSENSITIVE);
	private String tableName;
	private String fieldNameRelopValue;
	private boolean whereClauseOrNot;
	private DataInterface theFinalDataSet;

	public void execute() throws MyException {
		System.out.print(theFinalDataSet);
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

	@Override
	public DataInterface getDataInterface(String input,
			ArrayList<DataInterface> theDataSets) throws MyException {
		matches(input);
		DataInterface dataInterface = getTableOrDataSet(tableName, theDataSets);
		return dataInterface.select(whereClauseOrNot, fieldName, relop, value);
	}

	@Override
	public void executeFinalStatement(String input,
			ArrayList<DataInterface> theDataSets) throws MyException {
		theFinalDataSet = getDataInterface(input, theDataSets);
		execute();
	}
}
