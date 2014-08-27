package one.collections;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import one.main.MyException;
import one.main.Variables;

/**
 * this class stores the tables into a table collection
 */
@SuppressWarnings("serial")
public class TableCollection implements Variables, Serializable {
	private HashMap<String, Table> hashMap = new HashMap<String, Table>();
	private static TableCollection tc = null;

	public void backUp(String fileName) throws MyException {
		for (Table eachTable : hashMap.values()) {
			eachTable.generateDataSet();
		}
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(tc);
			out.close();
		} catch (IOException ex) {
			throw new MyException("Invalid File");
		}

	}

	public void restore(String fileName) throws MyException {
		FileInputStream fis;
		try {
			fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			hashMap = ((TableCollection) ois.readObject()).hashMap;
			for (Table eachTable : hashMap.values()) {
				eachTable.writeDataSetToFile();
			}
			ois.close();
		} catch (IOException e) {
			throw new MyException("Invalid File");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private TableCollection() {
	}

	/**
	 * this method returns the table collection
	 */
	public static TableCollection getTC() {
		if (tc == null)
			tc = new TableCollection();
		return tc;
	}

	/**
	 * this method adds tables to the table collection
	 * 
	 * @throws MyException
	 */
	public void addTable(Table table) throws MyException {
		if (hashMap.containsKey(table.getName()))
			throw new MyException("The table already exist");
		else {
			hashMap.put(table.getName(), table);
		}
	}

	public void setCollection(TableCollection newCollection) {
		this.hashMap = newCollection.hashMap;
	}

	public Table getTable(String TableName) throws MyException {
		if (!hashMap.containsKey(TableName))
			throw new MyException("The table does not exist");
		else
			return hashMap.get(TableName);
	}

	public void removeTable(String tableName) throws MyException {
		if (hashMap.remove(tableName) == null)
			throw new MyException("The table does not exist");
		new File(theDirectory, tableName).delete();
	}

	/**
	 * this method renames an existing table
	 */
	public void renameTable(String newName, String oldName) throws MyException {
		Table newTable = hashMap.remove(oldName);
		if (newTable == null)
			throw new MyException(
					"The table you are trying to rename does not exist");
		else {
			newTable.setName(newName);
			tc.addTable(newTable);
			new File(theDirectory, oldName).renameTo(new File(theDirectory,
					newName));
		}
	}

	@Override
	public String toString() {
		String output = "";
		for (Table table : hashMap.values()) {
			output += table + "\n";
		}
		return output;
	}

	public String getTableCollectionXml() {
		String output = "";
		for (Table table : hashMap.values()) {
			output += table.getTableXml();
		}
		return output;
	}

}
