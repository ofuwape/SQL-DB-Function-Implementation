package one.randomacessfile;

import static org.junit.Assert.assertEquals;

import java.io.File;

import one.collections.Table;
import one.field.BooleanField;
import one.field.CharField;
import one.field.DateField;
import one.field.IntegerField;
import one.field.RealField;
import one.field.VarCharField;
import one.main.MyException;
import one.main.Variables;
import one.xml.XmlWrite;

import org.junit.After;
import org.junit.Test;

public class BinaryFileTest implements Variables {

	@Test
	public void testMatches() throws MyException {
		new XmlWrite().createDir();
		Table table = new Table("test");
		table.addField(new IntegerField("int"));
		table.addField(new BooleanField("bool"));
		table.addField(new DateField("date"));
		table.addField(new CharField("zip", 2));
		table.addField(new RealField("real"));
		table.addField(new VarCharField("things"));

		String[] input = { "1", "false", "'01/07/2005'", "'d5'", "4.6",
				"'works'", "true" };
		table.createTypes(input);
		assertEquals(table.datatoString(table.readAndSaveData()),
				"		1		false		01/07/2005		d5		4.6		works		\n");
	}

	@After
	public void tearDown() {
		new File(theDirectory, "test").delete();
	}

}
