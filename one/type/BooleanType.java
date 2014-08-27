package one.type;

import java.io.IOException;
import java.io.RandomAccessFile;

import one.main.MyException;

@SuppressWarnings("serial")
public class BooleanType extends Ftype<BooleanType> {

	private boolean theData;

	public BooleanType(String data) {
		this.theData = Boolean.parseBoolean(data);
	}

	public BooleanType(RandomAccessFile file, int position) throws MyException {
		try {
			file.seek(position);
			this.theData = file.readBoolean();
		} catch (IOException e) {
			throw new MyException("File missing");
		}
	}

	@Override
	public void writeToRAF(RandomAccessFile reader, Long writePosition)
			throws MyException {
		try {
			reader.seek(writePosition);
			reader.writeBoolean(theData);
		} catch (IOException e) {
			throw new MyException("File is missing");
		}
	}

	@Override
	public String toString() {

		return Boolean.toString(theData);
	}

	@Override
	public int compareTo(BooleanType data) {
		return (new Boolean(theData)).compareTo(data.theData);
	}

}
