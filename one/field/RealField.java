package one.field;

import java.io.RandomAccessFile;

import one.main.MyException;
import one.type.RealType;

/**
 * this class basically exemplifies the type real
 */
@SuppressWarnings("serial")
public class RealField extends Field {

	public RealField(String field) {
		super(field);
		this.size = 8;
	}

	@Override
	public RealType createType(String data) throws MyException {

		return new RealType(data);
	}

	@Override
	public RealType createType(RandomAccessFile file, int rowPos)
			throws MyException {
		return new RealType(file, startingPosition + rowPos);
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
