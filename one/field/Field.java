package one.field;

import java.io.RandomAccessFile;
import java.io.Serializable;

import one.main.MyException;
import one.type.Ftype;
import one.xml.XMLEncoder;

/**
 * this class is extended by all the field type classes
 */
@SuppressWarnings({ "rawtypes", "serial" })
public abstract class Field implements Serializable {

	protected String field;
	protected int startingPosition;
	protected int size;

	Field(String field) {
		this.field = field;
	}

	public String getFieldName() {
		return field;
	}

	public void setFieldName(String newName) {
		this.field = newName;
	}

	public String getType() {
		String string = this.getClass().getSimpleName();
		return string.substring(0, string.indexOf("Field"));
	}

	/**
	 * this method which is inherited in the type classes basically returns the
	 * field
	 */
	@Override
	public String toString() {
		return (field + "(" + getType() + ")");
	}

	public String getFieldXml() {
		return ("\t\t\t<FIELD>\n\t\t\t\t<FNAME>" + XMLEncoder.encode(field)
				+ "</FNAME>\n" + "\t\t\t\t<FTYPE>"
				+ XMLEncoder.encode(getType()) + "</FTYPE>\n" + "\t\t\t</FIELD>\n");
	}

	public abstract Ftype createType(String data) throws MyException;

	public abstract Ftype createType(RandomAccessFile file, int rowPos)
			throws MyException;

	public int getSize() {
		return size;
	}

	public abstract void setStartingPositon(int pos);

	public abstract int getStartingPosition();

}