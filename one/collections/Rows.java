package one.collections;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;

import one.main.MyException;
import one.main.Variables;
import one.type.Ftype;

@SuppressWarnings({ "rawtypes", "serial" })
public class Rows implements Comparable<Rows>, Serializable, Variables {
	private ArrayList<Ftype> row = new ArrayList<Ftype>();

	public Rows(ArrayList<Ftype> theRow) {
		row = theRow;
	}

	public Rows filterTheFieldsData(String[] tablesFieldNames,
			String[] specifiedFieldNames) throws MyException {
		ArrayList<Ftype> newRow = new ArrayList<Ftype>();
		for (String eachField : specifiedFieldNames) {
			int i = 0;
			for (Ftype eachData : row) {
				if (tablesFieldNames[i].equals(eachField.trim())) {
					newRow.add(eachData);
				}
				i++;
			}
		}
		return new Rows(newRow);
	}

	public Ftype getData() {
		return row.get(0);
	}

	@Override
	public String toString() {
		String output = "";
		for (Ftype eachType : row) {
			output += eachType + "	 	";
		}
		return output;
	}

	public Rows appendThe(Rows secondTablesRow) {
		ArrayList<Ftype> nRow = new ArrayList<Ftype>();
		nRow.addAll(row);
		nRow.addAll(secondTablesRow.row);
		return new Rows(nRow);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(Rows otherRow) {
		int count = 0, result = 0;
		while (count < row.size()) {
			result += (row.get(count)).compareTo((otherRow.row).get(count));
			if (result != 0)
				return result;
			count++;
		}
		return result;
	}

	public void writeToFile(String name) throws MyException {
		try {
			RandomAccessFile file = new RandomAccessFile(new File(theDirectory,
					name), "rw");
			file.seek(file.length());
			file.writeByte(1);
			for (Ftype theTypes : row) {
				theTypes.writeToRAF(file, file.length());
			}
			file.close();
		} catch (IOException e) {
			throw new MyException("File missing");
		}

	}
}
