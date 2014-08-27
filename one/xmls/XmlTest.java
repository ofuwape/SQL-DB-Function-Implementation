package one.xmls;

import static org.junit.Assert.assertEquals;

import one.collections.Table;
import one.field.BooleanField;
import one.field.CharField;
import one.field.DateField;
import one.field.IntegerField;
import one.field.RealField;
import one.field.VarCharField;
import one.main.MyException;

import org.junit.Test;

public class XmlTest {

	@Test
	public void testMatches() throws MyException {
		Table table = new Table("test");
		table.addField(new IntegerField("int"));
		table.addField(new BooleanField("bool"));
		table.addField(new DateField("date"));
		table.addField(new CharField("zip", 2));
		table.addField(new RealField("real"));
		table.addField(new VarCharField("things"));
		assertEquals(table.getTableXml(), "\t<TABLE>\n"
				+ "\t<TABLENAME>test</TABLENAME>\n" + "\t\t<FIELDS>\n"
				+ "\t\t\t<FIELD>\n\t\t\t\t<FNAME>int</FNAME>\n"
				+ "\t\t\t\t<FTYPE>Integer</FTYPE>\n" + "\t\t\t</FIELD>\n"
				+ "\t\t\t<FIELD>\n\t\t\t\t<FNAME>bool</FNAME>\n"
				+ "\t\t\t\t<FTYPE>Boolean</FTYPE>\n" + "\t\t\t</FIELD>\n"
				+ "\t\t\t<FIELD>\n\t\t\t\t<FNAME>date</FNAME>\n"
				+ "\t\t\t\t<FTYPE>Date</FTYPE>\n" + "\t\t\t</FIELD>\n"
				+ "\t\t\t<FIELD>\n\t\t\t\t<FNAME>zip</FNAME>\n"
				+ "\t\t\t\t<FTYPE>Char</FTYPE>\n"
				+ "\t\t\t\t<TYPELENGTH>2</TYPELENGTH>\n\t\t\t</FIELD>\n"
				+ "\t\t\t<FIELD>\n\t\t\t\t<FNAME>real</FNAME>\n"
				+ "\t\t\t\t<FTYPE>Real</FTYPE>\n" + "\t\t\t</FIELD>\n"
				+ "\t\t\t<FIELD>\n\t\t\t\t<FNAME>things</FNAME>\n"
				+ "\t\t\t\t<FTYPE>VarChar</FTYPE>\n" + "\t\t\t</FIELD>\n"
				+ "\t\t</FIELDS>\n" + "\t</TABLE>\n");
	}
}
