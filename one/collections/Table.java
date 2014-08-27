package one.collections;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import one.field.Field;
import one.main.MyException;
import one.main.Variables;
import one.type.Ftype;
import one.xml.XMLEncoder;

/**
 * this class stores the fields in a table
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class Table extends DataInterface implements Variables, Serializable {
	private String tableName;
	private ArrayList<Field> fields = new ArrayList<Field>();
	private int rowLength = 1;
	private DataSets theDataSet;

	public void generateDataSet() throws MyException {
		theDataSet = new DataSets(tableName, fields, getRows());
	}

	public Table(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * this method adds fields into the table
	 * 
	 * @throws MyException
	 */
	public void addField(Field field) throws MyException {
		for (Field eachField : fields) {
			if (eachField.getFieldName().equals(field.getFieldName())) {
				throw new MyException("Field already exist");
			}
		}
		fields.add(field);
		field.setStartingPositon(rowLength);
		rowLength += field.getSize();

	}

	/**
	 * this method returns the name of the table
	 */
	@Override
	public String getName() {
		return tableName;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	private String[] getFieldNames() {
		String output = "";
		for (Field theField : fields) {
			output += theField.getFieldName() + "	 ";
		}
		return output.trim().split("	 ");
	}

	@Override
	public String toString() {
		return tableName + "***" + getNamesOfFieldsAndTypes();
	}

	public String getNamesOfFieldsAndTypes() {
		String allfields = "";
		for (Field field : fields) {
			allfields += "	 " + field;
		}
		return allfields;
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

	/**
	 * this method creates the xml string of a table
	 */
	public String getTableXml() {
		String allfields = "";
		for (Field field : fields) {
			allfields += field.getFieldXml();
		}
		return "\t<TABLE>\n" + "\t<TABLENAME>" + XMLEncoder.encode(tableName)
				+ "</TABLENAME>\n" + "\t\t<FIELDS>\n" + allfields
				+ "\t\t</FIELDS>\n" + "\t</TABLE>\n";
	}

	public ArrayList<ArrayList<Ftype>> readAndSaveData() throws MyException {
		ArrayList<ArrayList<Ftype>> allData;
		try {
			RandomAccessFile file = new RandomAccessFile(new File(theDirectory,
					tableName), "rw");
			allData = new ArrayList<ArrayList<Ftype>>();
			for (int rowNum = 0; rowNum < (int) file.length() / rowLength; rowNum++) {
				ArrayList<Ftype> eachRow = new ArrayList<Ftype>();
				for (Field f : fields) {
					eachRow.add(f.createType(file, rowNum * rowLength));
				}
				if (!isDelete(file, rowNum))
					allData.add(eachRow);
			}
			file.close();
		} catch (IOException e) {
			throw new MyException("File missing");
		}
		return allData;
	}

	private boolean isDelete(RandomAccessFile file, int rowNum)
			throws MyException {
		try {
			boolean outcome = false;
			file.seek(rowNum * rowLength);
			if (file.readByte() == 0) {
				outcome = true;
			}
			return outcome;
		} catch (IOException e) {
			throw new MyException("File missing");
		}

	}

	private void setDelete(RandomAccessFile file, int rowNum)
			throws MyException {
		try {
			file.seek(rowNum * rowLength);
			file.writeByte(0);
		} catch (IOException e) {
			throw new MyException("File missing");
		}

	}

	public String datatoString(ArrayList<ArrayList<Ftype>> data)
			throws MyException {
		String output = "";
		for (ArrayList<Ftype> eachRow : data) {
			output += "		";
			for (Ftype typs : eachRow) {
				output += typs.toString() + "		";
			}

			output += "\n";
		}
		return output;
	}

	public void createTypes(String[] splits) throws MyException {
		if (fields.size() != splits.length)
			throw new MyException(
					"Input doesn't match the table's field structure");
		else {
			ArrayList<Ftype> parsedData = new ArrayList<Ftype>();
			int i = 0;
			for (Field f : fields) {
				parsedData.add(f.createType(splits[i]));
				i++;
			}
			writeToBinaryFile(parsedData);
		}

	}

	/**
	 * this writes a data into the binary file of a table
	 */
	private void writeToBinaryFile(ArrayList<Ftype> typs) throws MyException {
		try {
			RandomAccessFile file = new RandomAccessFile(new File(theDirectory,
					tableName), "rw");
			file.seek(file.length());
			file.writeByte(1);
			for (Ftype theTypes : typs) {
				theTypes.writeToRAF(file, file.length());
			}
			file.close();
		} catch (IOException e) {
			throw new MyException("File missing");
		}
	}

	public void writeDataSetToFile() throws MyException {
		if (theDataSet != null)
			theDataSet.writeToFile();
	}

	public void setName(String newName) {
		tableName = newName;

	}

	@SuppressWarnings("unchecked")
	private boolean[] checkRowsThatMeetCondition(boolean whereClauseOrNot,
			String fieldName, String relop, String value) throws MyException {
		boolean[] rowsThatMeetCondition = null;
		if (whereClauseOrNot) {
			try {
				Field theField = getField(fieldName);
				RandomAccessFile file = new RandomAccessFile(new File(
						theDirectory, tableName), "rw");
				int numberOfRows = (int) file.length() / rowLength;
				rowsThatMeetCondition = new boolean[numberOfRows];
				for (int rowNum = 0; rowNum < numberOfRows; rowNum++) {
					rowsThatMeetCondition[rowNum] = isRelopConditionCorrect(
							theField.createType(file, rowNum * rowLength)
									.compareTo(theField.createType(value)),
							relop);
				}
				file.close();
			} catch (IOException e) {
				throw new MyException("File missing");
			}
		}
		return rowsThatMeetCondition;

	}

	public void deleteFields(boolean whereClauseOrNot, String fieldName,
			String relop, String value) throws MyException {
		boolean[] rowsThatMeetCondition = checkRowsThatMeetCondition(
				whereClauseOrNot, fieldName, relop, value);
		try {
			RandomAccessFile file = new RandomAccessFile(new File(theDirectory,
					tableName), "rw");
			for (int rowNum = 0; rowNum < (int) file.length() / rowLength; rowNum++) {
				if (rowsThatMeetCondition == null
						|| rowsThatMeetCondition[rowNum])
					setDelete(file, rowNum);
			}
			file.close();
		} catch (IOException e) {
			throw new MyException("File missing");
		}
	}

	public void updateFields(boolean whereClauseOrNot, String fieldName,
			String relop, String value, String newUpdateData,
			String fieldNameOfFieldAboutToBeUpdated) throws MyException {
		boolean[] rowsThatMeetCondition = checkRowsThatMeetCondition(
				whereClauseOrNot, fieldName, relop, value);
		try {
			Field theField = getField(fieldNameOfFieldAboutToBeUpdated);
			RandomAccessFile file = new RandomAccessFile(new File(theDirectory,
					tableName), "rw");
			for (int i = 0; i < (int) file.length() / rowLength; i++) {
				Long writePosition = (long) (theField.getStartingPosition() + (rowLength * i));
				if (rowsThatMeetCondition == null || rowsThatMeetCondition[i])
					(theField.createType(newUpdateData)).writeToRAF(file,
							writePosition);
			}
			file.close();
		} catch (IOException e) {
			throw new MyException("File missing");
		}
	}

	private ArrayList<Rows> getArrayListOfRows(
			ArrayList<ArrayList<Ftype>> allData) {
		ArrayList<Rows> theRows = new ArrayList<Rows>();
		for (ArrayList<Ftype> eachRow : allData) {
			theRows.add(new Rows(eachRow));
		}
		return theRows;
	}

	@Override
	public ArrayList<Rows> getRows() throws MyException {
		return getArrayListOfRows(readAndSaveData());
	}

	@Override
	public DataInterface select(boolean whereClauseOrNot, String fieldName,
			String relop, String value) throws MyException {
		boolean[] rowsThatMeetCondition = checkRowsThatMeetCondition(
				whereClauseOrNot, fieldName, relop, value);
		ArrayList<Rows> allData = new ArrayList<Rows>();
		int i = 0;
		for (Rows eachRow : getRows()) {
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
		for (Rows eachRow : getRows()) {
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

}
