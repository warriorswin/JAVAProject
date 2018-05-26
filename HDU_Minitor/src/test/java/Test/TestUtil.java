package Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestUtil {
	final List<String> list = new ArrayList<String>();
	@Test
	public void testClone() {
		System.out.println(1);
		list.addAll(null);
		System.out.println(2);
		list.add("12");
		list.add("34");
		System.out.println(list);
	}
}
