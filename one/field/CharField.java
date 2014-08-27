package one.field;

import java.io.RandomAccessFile;

import one.main.MyException;
import one.type.CharType;
import one.xml.XMLEncoder;

/**
 * this class basically exemplifies the type char
 */
@SuppressWarnings("serial")
public class CharField extends Field {
	private int charLength;

	public CharField(String field, int charLength) {
		super(field);
		this.charLength = charLength;
		this.size = 2 * charLength;
	}

	@Override
	public String getType() {
		return "Char(" + charLength + ")";
	}

	@Override
	public String toString() {
		return field + "(" + getType() + ")";
	}

	@Override
	public CharType createType(String data) throws MyException {
		return new CharType(data, charLength);
	}

	@Override
	public String getFieldXml() {
		return ("\t\t\t<FIELD>\n\t\t\t\t<FNAME>" + XMLEncoder.encode(field)
				+ "</FNAME>\n" + "\t\t\t\t<FTYPE>"
				+ XMLEncoder.encode(getType()) + "</FTYPE>\n"
				+ "\t\t\t\t<TYPELENGTH>" + XMLEncoder.encode(charLength) + "</TYPELENGTH>\n\t\t\t</FIELD>\n");
	}

	@Override
	public CharType createType(RandomAccessFile file, int rowPos)
			throws MyException {
		return new CharType(file, charLength, startingPosition + rowPos);
	}

	public int getCharLength() {
		return charLength;

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
