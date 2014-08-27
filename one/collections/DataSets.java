package one.collections;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import one.field.Field;
import one.main.MyException;
import one.main.Variables;
import one.type.Ftype;

@SuppressWarnings({ "rawtypes", "serial" })
public class DataSets extends DataInterface implements Serializable, Variables {

	private ArrayList<Rows> theDataSet = new ArrayList<Rows>();
	private String name;
	private ArrayList<Field> fields = new ArrayList<Field>();

	public DataSets(String name, ArrayList<Field> fields,
			ArrayList<Rows> allRows) {
		this.name = name;
		this.fields = fields;
		this.theDataSet = allRows;
	}

	public void writeToFile() throws MyException {
		new File(theDirectory, name).delete();
		for (Rows eachRow : theDataSet) {
			eachRow.writeToFile(name);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ArrayList<Rows> getRows() throws MyException {
		return theDataSet;
	}

	@Override
	public String toString() {
		String fieldNames = "";
		for (Field theField : fields) {
			fieldNames += theField + "	 ";
		}
		String output = fieldNames + "\n";
		for (Rows eachRow : theDataSet) {
			output += eachRow.toString() + "\n";
		}
		return output;
	}

	@SuppressWarnings("unchecked")
	private boolean[] checkRowsThatMeetCondition(boolean whereClauseOrNot,
			String fieldName, String relop, String value) throws MyException {
		boolean[] rowsThatMeetCondition = null;
		if (whereClauseOrNot) {
			rowsThatMeetCondition = new boolean[theDataSet.size()];
			String[] fieldNameOfValue = { fieldName };
			int i = 0;
			for (Rows eachRow : theDataSet) {
				Ftype data = (eachRow.filterTheFieldsData(getFieldNames(),
						fieldNameOfValue)).getData();
				Field theField = getField(fieldName);
				rowsThatMeetCondition[i] = isRelopConditionCorrect(
						data.compareTo(theField.createType(value)), relop);
				i++;
			}
		}
		return rowsThatMeetCondition;
	}

	@Override
	public DataInterface select(boolean whereClauseOrNot, String fieldName,
			String relop, String value) throws MyException {
		boolean[] rowsThatMeetCondition = checkRowsThatMeetCondition(
				whereClauseOrNot, fieldName, relop, value);
		ArrayList<Rows> allData = new ArrayList<Rows>();
		int i = 0;
		for (Rows eachRow : theDataSet) {
			if (rowsThatMeetCondition == null || rowsThatMeetCondition[i]) {
				allData.add(eachRow);
			}
			i++;
		}
		DataSets theDataSet = new DataSets(getName(),
				getFields(rowsThatMeetCondition), allData);
		return theDataSet;
	}

	@Override
	public DataInterface project(String[] theFieldNames) throws MyException {
		ArrayList<Rows> allRowsForDataSet = new ArrayList<Rows>();
		for (Rows eachRow : theDataSet) {
			allRowsForDataSet.add(eachRow.filterTheFieldsData(getFieldNames(),
					theFieldNames));
		}
		ArrayList<Field> theFields = new ArrayList<Field>();
		for (String eachField : theFieldNames) {
			theFields.add(getField(eachField));
		}
		DataSets theDataSet = new DataSets(getName(), theFields,
				allRowsForDataSet);
		return theDataSet;
	}

	@Override
	public DataInterface join(DataInterface secondDataSet) throws MyException {
		ArrayList<Rows> firstTableRows = getRows();
		ArrayList<Rows> secondTableRows = secondDataSet.getRows();
		ArrayList<Rows> finalDataSet = new ArrayList<Rows>();
		for (Rows firstTablesRow : firstTableRows) {
			for (Rows secondTablesRow : secondTableRows) {
				Rows theRow = firstTablesRow.appendThe(secondTablesRow);
				finalDataSet.add(theRow);
			}
		}
		DataSets theDataSet = new DataSets(getName() + secondDataSet.getName(),
				getJoinedFields(secondDataSet), finalDataSet);
		return theDataSet;
	}

	@Override
	public DataInterface union(DataInterface secondDataSet) throws MyException {
		ArrayList<Rows> firstTableRows = getRows();
		ArrayList<Rows> secondTableRows = secondDataSet.getRows();
		if (!unionCompatible(secondDataSet))
			throw new MyException("The fields are not union compatible");
		else {
			for (Rows eachRow : secondTableRows) {
				{
					if (!checkIfRowExists(eachRow, firstTableRows))
						firstTableRows.add(eachRow);
				}
			}
		}
		DataSets theDataSet = new DataSets(getName() + secondDataSet.getName(),
				fields, firstTableRows);
		return theDataSet;
	}

	@Override
	public DataInterface minus(DataInterface secondDataSet) throws MyException {
		ArrayList<Rows> firstTableRows = getRows();
		ArrayList<Rows> secondTableRows = secondDataSet.getRows();
		ArrayList<Rows> finalDataSet = new ArrayList<Rows>();
		if (!unionCompatible(secondDataSet))
			throw new MyException("The fields are not union compatible");
		else {
			for (Rows eachRow : firstTableRows) {
				{
					if (!checkIfRowExists(eachRow, secondTableRows))
						finalDataSet.add(eachRow);
				}
			}
		}
		DataSets theDataSet = new DataSets(getName() + secondDataSet.getName(),
				fields, finalDataSet);
		return theDataSet;
	}

	@Override
	public DataInterface intersect(DataInterface secondDataSet)
			throws MyException {
		ArrayList<Rows> firstTableRows = getRows();
		ArrayList<Rows> secondTableRows = secondDataSet.getRows();
		ArrayList<Rows> finalDataSet = new ArrayList<Rows>();
		if (!unionCompatible(secondDataSet))
			throw new MyException("The fields are not union compatible");
		else {
			for (Rows eachRow : firstTableRows) {
				{
					if (checkIfRowExists(eachRow, secondTableRows))
						finalDataSet.add(eachRow);
				}
			}
		}
		DataSets theDataSet = new DataSets(getName() + secondDataSet.getName(),
				fields, finalDataSet);
		return theDataSet;
	}

	@Override
	public ArrayList<Field> getFields() {
		return fields;
	}

	private ArrayList<Field> getFields(boolean[] rowsThatMeetCondition) {
		ArrayList<Field> theFields = new ArrayList<Field>();
		int i = 0;
		for (Field eachField : fields) {
			if (rowsThatMeetCondition == null || rowsThatMeetCondition[i])
				theFields.add(eachField);
		}
		return theFields;
	}

	private String[] getFieldNames() {
		String output = "";
		for (Field theField : fields) {
			output += theField.getFieldName() + "	 ";
		}
		return output.trim().split("	 ");
	}

}
