package one.type;

import java.io.RandomAccessFile;
import java.io.Serializable;

import one.main.MyException;

@SuppressWarnings({ "rawtypes", "serial" })
public abstract class Ftype<F extends Ftype> implements Comparable<F>,
		Serializable {

	public abstract void writeToRAF(RandomAccessFile reader, Long position)
			throws MyException;
}
