package one.field;

import java.io.RandomAccessFile;
import one.main.MyException;
import one.type.DateType;

/**
 * this class basically exemplifies the type date
 */
@SuppressWarnings("serial")
public class DateField extends Field {

	public DateField(String field) {
		super(field);
		this.size = 8;
	}

	@Override
	public DateType createType(String data) throws MyException {

		return new DateType(data);
	}

	@Override
	public DateType createType(RandomAccessFile file, int rowPos)
			throws MyException {
		return new DateType(file, startingPosition + rowPos);
	}

	@Override
	public void setStartingPositon(int pos) {
		startingPosition = pos;

	}

	@Override
	public int getStartingPosition() {
		return startingPosition;
	}

}
