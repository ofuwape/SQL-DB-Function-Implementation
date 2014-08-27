package one.main;

import java.util.regex.Pattern;

public interface Variables {
	String theDirectory = "MyDirectory";
	String dataBaseDirectory = "MyDirectory/database.xml";
	String dataBaseDtdDirectory = "MyDirectory/database.dtd";
	String VarCharFile = "VARCHARS";
	String whereClause = "(?:\\s+Where\\s+(.*?))?\\s*;\\s*";
	Pattern relopPattern = Pattern
			.compile("(.*?)\\s*(>=|<=|!=|>|<|=)\\s*(.*?)");
}
