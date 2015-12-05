package com.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.eviware.soapui.impl.wsdl.WsdlProjectPro;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.model.testsuite.TestRunner.Status;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.support.types.StringToObjectMap;


public class SOAPUIRunnerTest {

    @Test
    public void test() {
    	
    	try {

	        SoapUIExtensionTestCaseRunner runner = new  SoapUIExtensionTestCaseRunner();
	        runner.setOutputFolder("target/reports");
	        
	        runner.getLicenseManagerServiceFactory();
	
	        // assign folder path of composite soapui project to SOAPUI_CI_PROJECT variable 
	        
	        String SOAPUI_CI_PROJECT= "SOAPUI composite project folder path";
	
	        System.out.println("******************************* SOAP UI PROJECT FILE ************* "+SOAPUI_CI_PROJECT);
	
	        runner.setProjectFile(SOAPUI_CI_PROJECT);
	        runner.setPrintReport(true);
	        runner.setIgnoreErrors(false);
	        runner.setPrintReport(true);
	
	
	        WsdlProjectPro project = new WsdlProjectPro(SOAPUI_CI_PROJECT);
	        List<WsdlTestSuite> testSuites = project.getTestSuiteList();
	
            for( WsdlTestSuite suite : testSuites ) {

                List<WsdlTestCase> testCases = suite.getTestCaseList();
                
                for (WsdlTestCase testCase : testCases) {
                	
                	System.out.println("========================== TEST CASE =====================::"+ testCase.getName());
                	WsdlTestCaseRunner testRunner = new WsdlTestCaseRunner( testCase, new StringToObjectMap());
                	List<TestStep> testSteps = testCase.getTestStepList();
                	
                	for(TestStep testStep: testSteps) {
                		
                		System.out.println("--------------------- TEST STEP ----------------- :"+ testStep.getName());
                		testRunner.runTestStep(testStep);
                		if(testRunner.getStatus().equals(Status.FAILED)) {
                			Assert.fail();
                		}
                	}
                }
            }
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

}
