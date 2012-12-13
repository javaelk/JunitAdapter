import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.*;

public class AbstractTestSuiteAdapterTest extends TestCase {
    
	TestSuite ts1,ts2,ts3;
	
	public AbstractTestSuiteAdapterTest(String name){
		super(name);
	}
	
	protected void setUp(){
		ts1 = new TestSuite();
		ts1.addTest(new TestClass1());
		ts2 = new TestSuite();
		ts2.addTest(ts1);
		ts2.addTest(new TestClass2());
		ts2.addTest(new TestClass3());
		ts3 = new TestSuite();
		ts3.addTest(ts2);
		ts3.addTest(ts1);
		ts3.addTest(new TestClass2());
		ts3.addTest(new TestClass4());
		//5 test cases in total, 2x TestClass2
	}
	public void testParseTestSuite(){

		Set<TestCase> result = new AbstractTestSuiteAdapter(ts3).parseTestSuite(ts3);
		assertTrue(result.size()==5);
	}
	
	public void testAddTest(){
		AbstractTestSuiteAdapter adp = new AbstractTestSuiteAdapter(ts3);
		
		assertEquals(5,adp.addTest(ts3));
		adp.outputAllTestCases();
		
	}
	public class TestClass1 extends TestCase{}
	public class TestClass2 extends TestCase{}
	public class TestClass3 extends TestCase{}
	public class TestClass4 extends TestCase{}
	
}
