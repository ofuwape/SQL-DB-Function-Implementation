package one.field;

import java.io.RandomAccessFile;

import one.main.MyException;
import one.type.VarCharType;

/**
 * this class basically exemplifies the type VarChar
 */
@SuppressWarnings("serial")
public class VarCharField extends Field {

	public VarCharField(String field) {
		super(field);
		this.size = 8;
	}

	@Override
	public VarCharType createType(String data) throws MyException {
		return new VarCharType(data);
	}

	@Override
	public VarCharType createType(RandomAccessFile file, int rowPos)
			throws MyException {
		return new VarCharType(file, startingPosition + rowPos);
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
