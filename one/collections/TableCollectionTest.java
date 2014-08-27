package one.collections;

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

public class TableCollectionTest {

	@Test
	public void testMatches() throws MyException {
		Table table = new Table("test");
		table.addField(new IntegerField("int"));
		table.addField(new BooleanField("bool"));
		table.addField(new DateField("date"));
		table.addField(new CharField("zip", 2));
		table.addField(new RealField("real"));
		table.addField(new VarCharField("things"));
		assertEquals(
				table.toString(),
				"test***	 int(Integer)	 bool(Boolean)	 date(Date)	 zip(Char(2))	 real(Real)	 things(VarChar)");
	}
}
