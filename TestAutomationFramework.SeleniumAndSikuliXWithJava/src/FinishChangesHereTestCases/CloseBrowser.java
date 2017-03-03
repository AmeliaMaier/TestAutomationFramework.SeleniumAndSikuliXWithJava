package FinishChangesHereTestCases;

import java.util.List;

import changeWithCare.SeleniumUtility;
import doNotChange.Log;
import doNotChange.TestCaseInterface;
import doNotChange.TestCaseSuperclass;

public class CloseBrowser extends TestCaseSuperclass implements TestCaseInterface
{	
	public CloseBrowser(Log l){
		super(l);
	}
	
	public void readVariables(List<String> listOfTestCaseVariables)
	{
		actualResult = false;
		testCaseCompleted = false;
		if(listOfTestCaseVariables.size()!= 2){
			log.write("Error Location: CloseBrowser.readVariables; The number of variables provided do not match the expected information for this test case. No further steps taken. Variables provided: " + listOfTestCaseVariables.toString() ); 
			variablesNeededAvailable = false;
			expectedResults = true;
			return;
		}
		variablesNeededAvailable = true;
		expectedResults = translateExpectedResults(listOfTestCaseVariables.get(1));
	}

	public boolean[] runSteps()
	{
		if(!variablesNeededAvailable) return fillFinalTestCaseResults();
		log.write("Attempting to close the browser");
		if (!SeleniumUtility.closeBrowser(log)) return fillFinalTestCaseResults();
		testCaseCompleted = true;
		actualResult = true;
		log.write("Browser successfully closed");
		return fillFinalTestCaseResults();
	}

}
