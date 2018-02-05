package Test;
import junit.framework.TestCase;


public class FactTest1 extends TestCase {
	public void test() {
		//Prg2 test = new Prg2();
		int output =main.FactNew.fact(5);
		assertEquals(120, output);
	}
}
