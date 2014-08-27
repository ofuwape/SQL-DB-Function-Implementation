package one.collections;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.field.BooleanField;
import one.field.CharField;
import one.field.DateField;
import one.field.Field;
import one.field.IntegerField;
import one.field.RealField;
import one.field.VarCharField;
import one.main.MyException;

public abstract class DataInterface {

	public abstract DataInterface select(boolean whereClauseOrNot,
			String fieldName, String relop, String value) throws MyException;

	public abstract DataInterface project(String[] fields) throws MyException;

	public abstract DataInterface join(DataInterface secondInterface)
			throws MyException;

	public abstract DataInterface union(DataInterface secondInterface)
			throws MyException;

	public abstract DataInterface minus(DataInterface secondInterface)
			throws MyException;

	public abstract DataInterface intersect(DataInterface secondInterface)
			throws MyException;

	public abstract ArrayList<Rows> getRows() throws MyException;

	public abstract String getName();

	public abstract ArrayList<Field> getFields();

	public Field typeFactory(String field, String type) throws MyException {

		Matcher match = Pattern.compile("char\\s*\\((.*?)\\)\\s*",
				Pattern.CASE_INSENSITIVE).matcher(type);
		if (type.equalsIgnoreCase("integer"))
			return new IntegerField(field);

		else if (type.equalsIgnoreCase("boolean"))
			return new BooleanField(field);

		else if (type.equalsIgnoreCase("date"))
			return new DateField(field);

		else if (type.equalsIgnoreCase("real"))
			return new RealField(field);

		else if (type.equalsIgnoreCase("varchar"))
			return new VarCharField(field);

		else if (match.matches()) {
			try {
				return new CharField(field, Integer.parseInt(match.group(1)
						.trim()));
			} catch (NumberFormatException e) {
				throw new MyException("You entered an Invalid Field", e);
			}

		} else
			throw new MyException("You entered an Invalid Field");

	}

	protected boolean isRelopConditionCorrect(int result, String relop) {
		if (relop.equals("=") && result == 0)
			return true;
		else if (relop.equals(">") && result > 0)
			return true;
		else if (relop.equals("<") && result < 0)
			return true;
		else if (relop.equals(">=") && (result == 0 || result > 0))
			return true;
		else if (relop.equals("<=") && (result == 0 || result < 0))
			return true;
		else if (relop.equals("!=") && result != 0)
			return true;
		else
			return false;
	}

	protected boolean checkIfRowExists(Rows theRow, ArrayList<Rows> theRows) {
		for (Rows eachRow : theRows) {
			if ((eachRow.compareTo(theRow)) == 0)
				return true;
		}
		return false;
	}

	protected ArrayList<Field> getJoinedFields(DataInterface secondDataSet)
			throws MyException {
		ArrayList<Field> joinedFields = new ArrayList<Field>();
		ArrayList<Field> firstFieldsList = getFields();
		ArrayList<Field> secondFieldsList = secondDataSet.getFields();
		String firstName = getName();
		String secondName = secondDataSet.getName();
		for (Field eachField : firstFieldsList) {
			joinedFields.add(typeFactory(
					firstName + "." + eachField.getFieldName(),
					eachField.getType()));
		}
		for (Field eachField : secondFieldsList) {
			joinedFields.add(typeFactory(
					secondName + "." + eachField.getFieldName(),
					eachField.getType()));
		}
		return joinedFields;
	}

	protected boolean unionCompatible(DataInterface secondDataSet) {
		ArrayList<Field> secondListFields = secondDataSet.getFields();
		int i = 0;
		for (Field eachField : getFields()) {
			if (!((eachField.getType()).equals((secondListFields.get(i))
					.getType())))
				return false;
			i++;
		}
		return true;
	}

	protected Field getField(String fieldName) throws MyException {
		for (Field theField : getFields()) {
			if (theField.getFieldName().equals(fieldName))
				return theField;
		}
		throw new MyException("The Field Does not Exist");
	}

}
