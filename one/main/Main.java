package one.main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.collections.DataInterface;
import one.commands.BackUpCommand;
import one.commands.DefineIndexCommand;
import one.commands.DefineTableCommand;
import one.commands.DeleteCommand;
import one.commands.DropCommand;
import one.commands.ExitCommand;
import one.commands.ICommand;
import one.commands.InsertCommand;
import one.commands.IntersectCommand;
import one.commands.JoinCommand;
import one.commands.MinusCommand;
import one.commands.PrintCommand;
import one.commands.ProjectCommand;
import one.commands.ReadCommand;
import one.commands.RenameCommand;
import one.commands.RestoreCommand;
import one.commands.SelectCommand;
import one.commands.UnionCommand;
import one.commands.UpdateCommand;
import one.xml.XmlRead;
import one.xml.XmlWrite;

/**
 * The main class starts up the program that gets inputs from the user
 */
public class Main {

	private ICommand[] commands = new ICommand[] { new SelectCommand(),
			new PrintCommand(), new ExitCommand(), new ReadCommand(),
			new BackUpCommand(), new RestoreCommand(),
			new DefineTableCommand(), new RenameCommand(), new DropCommand(),
			new DefineIndexCommand(), new DeleteCommand(), new InsertCommand(),
			new UpdateCommand(), new ProjectCommand(), new JoinCommand(),
			new IntersectCommand(), new UnionCommand(), new MinusCommand(), };

	public static void main(String[] args) {
		new Main().run();
	}

	/**
	 * This method passes the command line scanner to the read method that is
	 * called
	 */
	private void run() {
		read(new Scanner(System.in));

	}

	private Pattern pattern = Pattern.compile("(\\([^)^(]*?\\))");
	private RelationalOperations[] operations = new RelationalOperations[] {
			new SelectCommand(), new ProjectCommand(), new JoinCommand(),
			new IntersectCommand(), new UnionCommand(), new MinusCommand() };
	private ICommand[] relationalCommands = new ICommand[] {
			new SelectCommand(), new ProjectCommand(), new JoinCommand(),
			new IntersectCommand(), new UnionCommand(), new MinusCommand() };

	private RelationalOperations getTheOperation(String input)
			throws MyException {
		RelationalOperations oper = null;
		boolean found = false;
		int i = 0;
		while (!found && i < operations.length) {
			if (relationalCommands[i].matches(input)) {
				oper = operations[i];
				found = true;
			}
			i++;
		}
		if (oper == null)
			throw new MyException("Incorrect statement");
		return oper;
	}

	private void parse(String input) throws MyException {
		ArrayList<DataInterface> theDataSets = new ArrayList<DataInterface>();
		Matcher m = pattern.matcher(input);
		String theInput;
		int counter = 0;
		while (m.find()) {
			String aStatement = m.group(1);
			theInput = aStatement.replace("(", "");
			theInput = theInput.replace(")", "") + ";";
			theDataSets.add(getTheOperation(theInput).getDataInterface(
					theInput, theDataSets));
			input = input.replace(aStatement, counter + "DataSet");
			counter++;
			m = pattern.matcher(input);
		}

		getTheOperation(input).executeFinalStatement(input, theDataSets);
	}

	/**
	 * This method used to read in user inputs either from Scanners for the
	 * command line or for files
	 */
	public void read(Scanner get) {
		try {
			new XmlRead().run();
			new XmlWrite().createDir();
		} catch (MyException e) {
			System.out.println(e.getMessage());
		}
		LABEL: while (true) {
			System.out.print(">");
			String st = "";
			try {
				st = get.nextLine();
				while (!st.contains(";")) {
					st = st + " " + (get.nextLine());
				}
				st = st.trim();
			} catch (NoSuchElementException e) {
				return;
			}
			for (ICommand command : commands) {
				try {
					boolean match = command.matches(st);
					if (match) {
						if (command instanceof SelectCommand
								|| command instanceof ProjectCommand
								|| command instanceof JoinCommand
								|| command instanceof UnionCommand
								|| command instanceof MinusCommand
								|| command instanceof IntersectCommand)
							parse(st);
						else {
							command.execute();
						}
						continue LABEL;
					}
				} catch (MyException e) {
					System.out.println(e.getMessage());
				} catch (FileNotFoundException e) {

				}
			}
			System.out.println("This statement is not correct");

		}

	}
}
