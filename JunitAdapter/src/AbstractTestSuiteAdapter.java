//package de.susebox; this should be changed for each test subjects

import junit.extensions.TestDecorator;
import junit.framework.*;
import junit.textui.TestRunner;

import java.util.*;
/**
 * This is a wrapper class added for ARTS framework. This class contains all junit test cases of the test subject and provides APIs to execute test case one by one
 * e.g. in Jtopas , the SuseboxTestSuite class comes with jtopas contains all test cases but does not separate them one by one
 * @author WEINING LIU
 */
public class AbstractTestSuiteAdapter implements TestSuiteAdapter{
    
	private static Map<String,TestCase> entireTestSuite;
	
	//run individual test case by its unique name
	public static void main(String[] args){
		//expect args contains one only one unique test case name
		if(args.length==1){
			String testcaseName = args[0];
			if(entireTestSuite.containsKey(testcaseName)){
				TestRunner.run(entireTestSuite.get(args[0]));
			}else{
				System.out.println(testcaseName + "does not exist in given test suite");
			}
		}else{
			System.out.println("Please provide one argument - test case name only");
		}
	}
	
	
	public AbstractTestSuiteAdapter(TestSuite jsuite){
		entireTestSuite = new HashMap<String,TestCase>();
	}
	/**
	 * 
	 * @return number of unique test cases added into this suite
	 */
	public int addTest(TestSuite jsuite){
        //recursively breaks down a test suite into a list of test cases
		Set<TestCase> testcases = parseTestSuite(jsuite);
		//create unique name for each test case
		int testID=0;
		for(TestCase t: testcases){
			String testcaseNewUniqueName = t.getClass().getName()+"."+t.getName()+"."+testID;
			entireTestSuite.put(testcaseNewUniqueName, t);
			testID++;
		}
		return testID;
	}
	
	/**
	 * Iterate entire test suite and print them out. Output is ordered by class name, then test name, then test id
	 */
	public void outputAllTestCases(){
		//TODO: implement a comparator and return iostream, 
		System.out.println(entireTestSuite.keySet().toString());
	}
	
	/**
	 * Recursively parse a test suite into individual test cases
	 * TODO: maybe consider a sortedSet
	 * @param ts
	 */
	
	protected Set<TestCase> parseTestSuite(TestSuite ts){
		
		Set<TestCase> resultSet = new HashSet<TestCase>();
		if(ts.countTestCases()==0)//empty suite
			return resultSet;
		for(Enumeration e= ts.tests();e.hasMoreElements();){
			Object obj =e.nextElement();
			if(obj instanceof TestCase){
				//add to resultSet
				resultSet.add((TestCase)obj);
			}else if(obj instanceof TestSuite){
				//make recursive call
				resultSet.addAll(parseTestSuite((TestSuite)obj));
			}else if(obj instanceof TestDecorator){
				System.out.println("ERROR -Not yet implemented!");
				//do nothing to the result set , this obj will be skipped
			}else{
				//do nothing to the result set , this obj will be skipped
				System.out.println("ERROR - Unknown type in test suite" + obj.getClass().toString());
			}
		}
		return resultSet;
	}
	//run all test cases
	//run all test cases and call EMMA to dump coverage data after each test case run
}
