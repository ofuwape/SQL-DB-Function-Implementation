package one.main;

import java.util.ArrayList;

import one.collections.DataInterface;
import one.collections.TableCollection;

public abstract class RelationalOperations extends SplitFieldNameRelopValue {

	public abstract DataInterface getDataInterface(String aStatement,
			ArrayList<DataInterface> theDataSets) throws MyException;

	public DataInterface getTableOrDataSet(String name,
			ArrayList<DataInterface> theDataSets) throws MyException {
		DataInterface theDataInterface = null;
		try {
			theDataInterface = TableCollection.getTC().getTable(name);
		} catch (MyException e) {
			if (e.getMessage().equals("The table does not exist")) {
				try {
					int position = Integer.parseInt(Character.toString(name
							.charAt(0)));
					theDataInterface = theDataSets.get(position);
				} catch (NumberFormatException ex) {
					throw new MyException("The table does not exist", ex);
				} catch (IndexOutOfBoundsException ex) {
					throw new MyException("The table does not exist", ex);
				}
			}
		}
		return theDataInterface;
	}

	public abstract void executeFinalStatement(String input,
			ArrayList<DataInterface> theDataSets) throws MyException;
}
