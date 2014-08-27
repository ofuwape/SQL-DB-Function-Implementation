package one.type;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import one.main.MyException;
import one.main.Variables;

@SuppressWarnings("serial")
public class VarCharType extends Ftype<VarCharType> implements Variables {
	private String theData;

	public VarCharType(String data) throws MyException {
		if (!(data.charAt(0) == '\'' && data.charAt(data.length() - 1) == '\''))
			throw new MyException(
					"Data for type VarChar has to be in single quotes");
		this.theData = data.replace("'", "");
	}

	public VarCharType(RandomAccessFile file, int position) throws MyException {
		RandomAccessFile readerVarChar;
		String output;
		try {
			readerVarChar = new RandomAccessFile(new File(theDirectory,
					VarCharFile), "rw");
			file.seek(position);
			Long address = file.readLong();
			readerVarChar.seek(address);
			output = readerVarChar.readUTF();
			readerVarChar.close();
			this.theData = output;
		} catch (IOException e) {
			throw new MyException("File Missing");
		}
	}

	@Override
	public void writeToRAF(RandomAccessFile reader, Long writePosition)
			throws MyException {
		RandomAccessFile readerVarChar;
		try {
			readerVarChar = new RandomAccessFile(new File(theDirectory,
					VarCharFile), "rw");
			readerVarChar.seek(readerVarChar.length());
			Long position = readerVarChar.getFilePointer();
			readerVarChar.writeUTF(theData);
			reader.seek(writePosition);
			reader.writeLong(position);
			readerVarChar.close();
		} catch (IOException e) {
			throw new MyException("File Missing");
		}

	}

	@Override
	public String toString() {
		return theData;
	}

	@Override
	public int compareTo(VarCharType data) {
		return theData.compareTo(data.theData);
	}

}
