package one.type;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import one.main.MyException;

@SuppressWarnings("serial")
public class DateType extends Ftype<DateType> {
	private Date theData;

	public DateType(String data) throws MyException {
		if (!(data.charAt(0) == '\'' && data.charAt(data.length() - 1) == '\''))
			throw new MyException(
					"Data for type Date has to be in single quotes");
		else {
			data = data.replace("'", "");
			try {
				this.theData = new SimpleDateFormat("mm/dd/yyyy").parse(data);
			} catch (ParseException e) {
				throw new MyException("Invalid Date");
			}
		}
	}

	public DateType(RandomAccessFile file, int position) throws MyException {
		try {
			file.seek(position);
			this.theData = new Date(file.readLong());
		} catch (IOException e) {
			throw new MyException("Invalid Date");
		}
	}

	@Override
	public void writeToRAF(RandomAccessFile reader, Long writePosition)
			throws MyException {
		try {

			reader.seek(writePosition);
			reader.writeLong(theData.getTime());
		} catch (IOException e) {
			throw new MyException("File is missing");
		}
	}

	@Override
	public String toString() {
		return new SimpleDateFormat("mm/dd/yyyy").format(theData);

	}

	@Override
	public int compareTo(DateType data) {
		return theData.compareTo(data.theData);
	}
}
