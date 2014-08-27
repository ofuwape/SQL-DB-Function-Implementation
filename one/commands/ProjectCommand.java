package one.commands;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.collections.DataInterface;
import one.main.MyException;
import one.main.RelationalOperations;

public class ProjectCommand extends RelationalOperations implements ICommand {

	private Pattern pattern = Pattern.compile(
			"project\\s+(.*?)\\s+over\\s*(.*?)\\s*;\\s*",
			Pattern.CASE_INSENSITIVE);
	private String tableName;
	private String[] fieldNames;
	private DataInterface theFinalDataSet = null;

	@Override
	public void execute() throws MyException {
		System.out.print(theFinalDataSet);
	}

	@Override
	public boolean matches(String str) {
		Matcher matcher = pattern.matcher(str);
		boolean result = matcher.matches();
		if (result) {
			tableName = matcher.group(1);
			fieldNames = matcher.group(2).split(",");
		}
		return result;
	}

	@Override
	public DataInterface getDataInterface(String input,
			ArrayList<DataInterface> theDataSets) throws MyException {
		matches(input);
		DataInterface dataInterface = getTableOrDataSet(tableName, theDataSets);
		return dataInterface.project(fieldNames);
	}

	@Override
	public void executeFinalStatement(String input,
			ArrayList<DataInterface> theDataSets) throws MyException {
		theFinalDataSet = getDataInterface(input, theDataSets);
		execute();
	}
}
