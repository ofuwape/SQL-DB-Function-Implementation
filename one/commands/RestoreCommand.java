package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.collections.TableCollection;
import one.main.MyException;

public class RestoreCommand implements ICommand {

	private Pattern pattern = Pattern.compile(
			"restore\\s+from\\s+'(.*?)'\\s*;\\s*", Pattern.CASE_INSENSITIVE);
	private String fileName;

	@Override
	public void execute() throws MyException {
		TableCollection.getTC().restore(fileName);
	}

	@Override
	public boolean matches(String str) {
		Matcher matcher = pattern.matcher(str);
		boolean result = matcher.matches();
		if (result) {
			fileName = matcher.group(1);
		}
		return result;
	}

}
