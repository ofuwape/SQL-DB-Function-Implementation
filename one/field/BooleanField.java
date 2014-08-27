package one.field;

import java.io.RandomAccessFile;

import one.main.MyException;
import one.type.BooleanType;

/**
 * this class basically exemplifies the type boolean
 */
@SuppressWarnings("serial")
public class BooleanField extends Field {

	public BooleanField(String field) {
		super(field);
		this.size = 1;
	}

	@Override
	public BooleanType createType(String data) {
		return new BooleanType(data);
	}

	@Override
	public BooleanType createType(RandomAccessFile file, int rowPos)
			throws MyException {
		return new BooleanType(file, startingPosition + rowPos);
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
