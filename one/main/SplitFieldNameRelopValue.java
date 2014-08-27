package one.main;

import java.util.regex.Matcher;

public abstract class SplitFieldNameRelopValue implements Variables {
	protected String fieldName;
	protected String relop;
	protected String value;

	public void getFieldNameRelopValue(String fieldNameRelopValue)
			throws MyException {
		fieldNameRelopValue = fieldNameRelopValue.trim();
		Matcher match = relopPattern.matcher(fieldNameRelopValue);
		if (!match.matches())
			throw new MyException("the where clause statement is invalid");
		else {
			if (match.group(1) == null || match.group(2) == null
					|| match.group(3) == null)
				throw new MyException("The where clause statement is invalid");
			else {
				fieldName = match.group(1);
				relop = match.group(2);
				value = match.group(3);
			}

		}
	}

}
