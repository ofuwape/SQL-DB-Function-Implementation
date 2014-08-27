package one.type;

import java.io.IOException;
import java.io.RandomAccessFile;

import one.main.MyException;

@SuppressWarnings("serial")
public class IntegerType extends Ftype<IntegerType> {
	private int theData;

	public IntegerType(String data) throws MyException {
		try {
			this.theData = Integer.parseInt(data);
		} catch (NumberFormatException e) {
			throw new MyException("Invalid Type Integer Data", e);
		}
	}

	public IntegerType(RandomAccessFile file, int position) throws MyException {
		try {
			file.seek(position);
			this.theData = file.readInt();
		} catch (IOException e) {
			throw new MyException("File missing");
		}
	}

	@Override
	public void writeToRAF(RandomAccessFile reader, Long writePosition)
			throws MyException {
		try {

			reader.seek(writePosition);
			reader.writeInt(theData);
		} catch (IOException e) {
			throw new MyException("File is missing");
		}
	}

	@Override
	public String toString() {
		return Integer.toString(theData);
	}

	@Override
	public int compareTo(IntegerType data) {
		return theData - data.theData;
	}

}
