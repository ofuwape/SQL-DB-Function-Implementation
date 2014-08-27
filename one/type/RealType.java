package one.type;

import java.io.IOException;
import java.io.RandomAccessFile;

import one.main.MyException;

@SuppressWarnings("serial")
public class RealType extends Ftype<RealType> {
	private Double theData;

	public RealType(String data) throws MyException {
		try {
			this.theData = Double.parseDouble(data);
		} catch (NumberFormatException e) {
			throw new MyException("Invalid Type Real Data", e);
		}
	}

	public RealType(RandomAccessFile file, int position) throws MyException {
		try {
			file.seek(position);
			this.theData = file.readDouble();
		} catch (IOException e) {
			throw new MyException("File missing");
		}
	}

	@Override
	public void writeToRAF(RandomAccessFile reader, Long writePosition)
			throws MyException {
		try {

			reader.seek(writePosition);
			reader.writeDouble(theData);
		} catch (IOException e) {
			throw new MyException("File is missing");
		}
	}

	@Override
	public String toString() {
		return Double.toString(theData);
	}

	@Override
	public int compareTo(RealType data) {
		return (int) (theData - data.theData);
	}

}
