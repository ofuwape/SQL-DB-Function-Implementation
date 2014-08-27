package one.xml;

import java.io.IOException;

import one.collections.Table;
import one.collections.TableCollection;
import one.field.CharField;
import one.field.Field;
import one.main.MyException;
import one.main.Variables;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class XmlRead implements Variables {

	public void run() throws MyException {
		new XmlRead().saxReader();
	}

	private void saxReader() throws MyException {
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(new ExampleContentHandler());
			reader.parse(dataBaseDirectory);
		} catch (SAXException e) {
			throw new MyException("Corrupt file", e);
		} catch (IOException e) {
		}
	}

	private class ExampleContentHandler extends DefaultHandler {
		private boolean fname = false;
		private boolean typelength = false;
		private boolean tablename = false;
		private Table thetable;
		private Field f;
		private String thefieldname;
		private String thefieldtype;
		private boolean ftype = false;

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if (qName.equalsIgnoreCase("FNAME")) {
				fname = true;
			}
			if (qName.equalsIgnoreCase("FTYPE")) {
				ftype = true;
			}
			if (qName.equalsIgnoreCase("TYPELENGTH")) {
				typelength = true;
			}
			if (qName.equalsIgnoreCase("TABLENAME")) {
				tablename = true;
			}

		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			TableCollection tc = TableCollection.getTC();
			if (qName.equalsIgnoreCase("FIELD")) {
				try {
					thetable.addField(f);
				} catch (MyException e) {
					System.out.println("Duplication of Field");
				}
			}
			if (qName.equalsIgnoreCase("FIELDS")) {
				try {
					tc.addTable(thetable);
				} catch (MyException e) {
					System.out.println("The table already exist");
				}
			}
			if (qName.equalsIgnoreCase("FNAME")) {
				fname = false;
			}
			if (qName.equalsIgnoreCase("FTYPE")) {
				ftype = false;
			}
			if (qName.equalsIgnoreCase("TYPELENGTH")) {
				typelength = false;
			}
			if (qName.equalsIgnoreCase("TABLENAME")) {
				tablename = false;
			}

		}

		@Override
		public void characters(char[] ch, int start, int length) {
			String username = new String(ch, start, length);
			if (tablename) {

				thetable = new Table(username);

			}
			if (fname) {
				thefieldname = username;
			}
			if (ftype) {
				thefieldtype = username;
				if (!thefieldtype.equalsIgnoreCase("char")) {
					try {
						f = thetable.typeFactory(thefieldname, thefieldtype);
					} catch (MyException e) {
						e.printStackTrace();
					}
				}
			}
			if (typelength) {
				f = new CharField(thefieldname, Integer.parseInt(username));
			}

		}
	}
}
