package FinishChangesHereTestCases;

import java.util.List;

import changeWithCare.SeleniumUtility;
import doNotChange.Log;
import doNotChange.TestCaseInterface;
import doNotChange.TestCaseSuperclass;

public class OpenPage extends TestCaseSuperclass implements TestCaseInterface
{
	String urlRequested;
	
	public OpenPage(Log l){
		super(l);
	}

	public void readVariables(List<String> listOfTestCaseVariables)
	{
		actualResult = false;
		testCaseCompleted = false;
		if (listOfTestCaseVariables.size() != 3)
		{
			log.write("Error Location: OpenPage.readVariables; The number of variables provided do not match the expected information for this test case. No further steps taken. Variables provided: " + listOfTestCaseVariables.toString() ); 
			variablesNeededAvailable = false;
			expectedResults = true;
			return;
		}
		variablesNeededAvailable = true;
		expectedResults = translateExpectedResults(listOfTestCaseVariables.get(1));
		urlRequested = listOfTestCaseVariables.get(2);
	}

	public boolean[] runSteps()
	{
		if (!variablesNeededAvailable) return fillFinalTestCaseResults();
		log.write("Attempting to open a new browser instance and the requested url.");
		if (!SeleniumUtility.openBrowser(log)) return fillFinalTestCaseResults();
		testCaseCompleted = true;
		actualResult = SeleniumUtility.openURL(urlRequested, log);
		log.write("Successfully opened a new browser instance and the requested url.");
		return fillFinalTestCaseResults();
	}
}
