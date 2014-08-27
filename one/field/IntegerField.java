package one.field;

import java.io.RandomAccessFile;

import one.main.MyException;
import one.type.IntegerType;

/**
 * this class basically exemplifies the type integer
 */
@SuppressWarnings("serial")
public class IntegerField extends Field {

	public IntegerField(String field) {
		super(field);
		this.size = 4;
	}

	@Override
	public IntegerType createType(String data) throws MyException {
		return new IntegerType(data);
	}

	@Override
	public IntegerType createType(RandomAccessFile file, int rowPos)
			throws MyException {
		return new IntegerType(file, startingPosition + rowPos);
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
