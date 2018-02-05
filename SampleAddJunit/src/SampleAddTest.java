import junit.framework.TestCase;
public class SampleAddTest { private SampleAdd sampleClass; 
@Before public void setUp() { sampleClass = new SampleAdd(); } @Test public void testAdd() { Assert.assertEquals(5, sampleClass.add(3, 2)); } @After public void settleDown() { sampleClass = null; } } - See more at:
