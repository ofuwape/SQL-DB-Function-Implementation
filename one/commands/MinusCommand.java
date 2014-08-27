package one.commands;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.collections.DataInterface;
import one.main.MyException;
import one.main.RelationalOperations;

public class MinusCommand extends RelationalOperations implements ICommand {

	private Pattern pattern = Pattern.compile(
			"minus\\s(.*?)\\s+and\\s+(.*?)\\s*;\\s*", Pattern.CASE_INSENSITIVE);
	private String firstTableName, secondTableName;
	private DataInterface theFinalDataSet = null;

	@Override
	public void execute() throws MyException {
		System.out.println(theFinalDataSet);
	}

	@Override
	public boolean matches(String str) {
		Matcher matcher = pattern.matcher(str);
		boolean result = matcher.matches();
		if (result) {
			firstTableName = matcher.group(1);
			secondTableName = matcher.group(2);
		}
		return result;
	}

	@Override
	public DataInterface getDataInterface(String input,
			ArrayList<DataInterface> theDataSets) throws MyException {
		matches(input);
		DataInterface dataInterfaceOne = getTableOrDataSet(firstTableName,
				theDataSets);
		DataInterface dataInterfaceTwo = getTableOrDataSet(secondTableName,
				theDataSets);
		return dataInterfaceOne.minus(dataInterfaceTwo);
	}

	@Override
	public void executeFinalStatement(String input,
			ArrayList<DataInterface> theDataSets) throws MyException {
		theFinalDataSet = getDataInterface(input, theDataSets);
		execute();
	}
}
