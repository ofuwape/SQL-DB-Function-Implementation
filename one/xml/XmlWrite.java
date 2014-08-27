package one.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import one.collections.TableCollection;
import one.main.MyException;
import one.main.Variables;

public class XmlWrite implements Variables {

	private TableCollection tc;

	public XmlWrite() {
		tc = TableCollection.getTC();
	}

	private void write(String file) throws FileNotFoundException {
		String ENCODING = "ISO-8859-1";
		PrintWriter out = new PrintWriter(new FileOutputStream(file));
		out.println("<?xml version=\"1.0\" encoding=\"" + ENCODING + "\"?>");
		out.println("<!DOCTYPE DATABASE SYSTEM \"database.dtd\">");
		out.println("<DATABASE>");
		out.println(tc.getTableCollectionXml());
		out.println("</DATABASE>");
		out.close();
	}

	private void createDTD() throws MyException {
		PrintWriter out;
		try {
			out = new PrintWriter(new FileOutputStream(dataBaseDtdDirectory));
			String ENCODING = "ISO-8859-1";
			out.println("<?xml version=\"1.0\" encoding=\"" + ENCODING + "\"?>");
			out.println("<!ELEMENT DATABASE (TABLE*)>");
			out.println("<!ELEMENT TABLE (FIELDS,FIELD*,TABLENAME)>");
			out.println("<!ELEMENT FIELDS (FNAME*,FTYPE*,TYPELENGTH*)>");
			out.println("<!ELEMENT TABLENAME (#PCDATA)>");
			out.println("<!ELEMENT FNAME (#PCDATA)>");
			out.println("<!ELEMENT FTYPE (#PCDATA)>");
			out.println("<!ELEMENT TYPELENGTH (#PCDATA)>");
			out.close();
		} catch (FileNotFoundException e) {
		}
		;

	}

	public void run() throws MyException {
		String file = dataBaseDirectory;
		new XmlWrite().createDir();
		new XmlWrite().createDTD();
		try {
			new XmlWrite().write(file);
		} catch (FileNotFoundException e) {
		}

	}

	public void createDir() {
		File t = new File(theDirectory);
		if (!t.exists())
			t.mkdir();
	}

}