package one.type;

import java.io.IOException;
import java.io.RandomAccessFile;

import one.main.MyException;

@SuppressWarnings("serial")
public class CharType extends Ftype<CharType> {
	private String theData;

	public CharType(String data, int charLength) throws MyException {
		if (!(data.charAt(0) == '\'' && data.charAt(data.length() - 1) == '\''))
			throw new MyException(
					"Data for type Char has to be in single quotes");
		else {
			data = data.replace("'", "");
			if (!(data.length() == charLength))
				throw new MyException(
						"The char type input is not the same size with the specified size in your table structure");
			this.theData = data;
		}
	}

	public CharType(RandomAccessFile file, int charLength, int position)
			throws MyException {
		String output = "";
		try {
			file.seek(position);
			for (int i = 0; i < charLength * 2 - 1; i++) {
				if (i % 2 == 0) {
					output += file.readChar();
				}
			}
			this.theData = output;
		} catch (IOException e) {
			throw new MyException("File missing");
		}
	}

	@Override
	public void writeToRAF(RandomAccessFile reader, Long writePosition)
			throws MyException {
		try {
			reader.seek(writePosition);
			reader.writeChars(theData);
		} catch (IOException e) {
			throw new MyException("File is missing");
		}
	}

	@Override
	public String toString() {
		return theData;
	}

	@Override
	public int compareTo(CharType data) {
		return theData.compareTo(data.theData);
	}

}
