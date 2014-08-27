package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefineIndexCommand implements ICommand {

	private Pattern pattern = Pattern.compile(
			"define\\s+index\\s+on\\s+(.*?)\\s*\\((.*?)\\)\\s*;\\s*", Pattern.CASE_INSENSITIVE);

	@Override
	public void execute() {
		System.out
				.println("This is a syntactically correct Define index statement");
	}

	@Override
	public boolean matches(String str) {
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

}
