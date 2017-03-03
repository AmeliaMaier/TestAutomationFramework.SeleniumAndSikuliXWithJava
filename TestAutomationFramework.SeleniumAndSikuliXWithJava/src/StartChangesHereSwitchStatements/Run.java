package StartChangesHereSwitchStatements;

import java.util.Arrays;
import java.util.List;

import FinishChangesHereTestCases.*;
import changeWithCare.ExcelAddressData;
import doNotChange.GetResults;
import doNotChange.Log;
import doNotChange.TestCaseInterface;

public class Run
{
	/**
	 * 
	 * @param listSplitTestInput
	 * @param listTestsToRun
	 * @param txtCurrentSuiteLocation
	 * @param iteratorListofTestsToRun
	 * @param txtResultsLocation
	 *            This method is used when there is a test suite that calls for
	 *            and ExcelGroup and provides the file name of an excel file
	 *            holding information for one or more addresses to be run
	 *            through the test cases that follow the ExcelGroup command.
	 *            Each address will be run through the tests, one at a time, and
	 *            their results will be output in the directoryResults (highest
	 *            level), testsuite+ExcelResults (mid level), and
	 *            testsuiteResults (lowest and most detailed level)
	 */
	public GetResults runExcelSubTestCase(List<String> listOfTestCaseVariables, int iteratorListofTestsToRun, int testCaseIterator, ExcelAddressData currentAddressData,GetResults getResults,String txtCurrentSuiteLocation, Log log)
	{
		switch (listOfTestCaseVariables.get(0).toLowerCase())
		{
			case "openpage":
				listOfTestCaseVariables.addAll(Arrays.asList(currentAddressData.coreURL));
				getResults = runTestCase(getResults,listOfTestCaseVariables, txtCurrentSuiteLocation, iteratorListofTestsToRun, log);
				break;
			case "closepage":
				getResults = runTestCase(getResults,listOfTestCaseVariables, txtCurrentSuiteLocation, iteratorListofTestsToRun, log);
				break;
			default:
				log.write("Error Location: COREMain.main; The Test on line: " + (testCaseIterator + 1) + " in file: " + txtCurrentSuiteLocation + " is not recognized.");
				getResults.setActualResults(false);
				getResults.setExpectedResults("pass");
				getResults.setTestCaseCompleted(false);
				break;
		}// switch(listSplitTestInput.get(0).toLowerCase())
		return getResults;
	}

	/**
	 * 
	 * @param listOfTestCaseVariables
	 * @param txtCurrentSuiteLocation
	 * @param testLocation
	 *            This is a support method used by both the main method and
	 *            excelgroup method to confirm test cases are run the same way
	 */
	public GetResults runTestCase(GetResults getResults, List<String> listOfTestCaseVariables, String txtCurrentSuiteLocation, int iteratorListofTestsToRun, Log log)
	{
		TestCaseInterface testCaseInterface = null;
		boolean testCaseRecognized = true;
		getResults = new GetResults(log);
		// sets to lowerCase to ignore capitalization typos
		switch (listOfTestCaseVariables.get(0).toLowerCase())
		{
			case "openpage":
				testCaseInterface = new OpenPage(log);
				break;
			case "closepage":
				testCaseInterface = new CloseBrowser(log);
				break;
			case "login":
				testCaseInterface = new Login(log);
				break;
			case "searchconfirmedwithimage":
				testCaseInterface = new SearchConfirmedWithImage(log);
				break;
			default:
				log.write("Error Location: COREMain.main; The Test on line: " + (iteratorListofTestsToRun + 1) + " in file: " + txtCurrentSuiteLocation + " is not recognized.");
				testCaseRecognized = false;
				break;
		}// switch(listSplitTestInput.get(0).toLowerCase())
		if (testCaseRecognized)
		{
			testCaseInterface.readVariables(listOfTestCaseVariables);
			getResults.runTestCaseSteps(testCaseInterface);
		}else{
			getResults.setActualResults(false);
			getResults.setExpectedResults("pass");
			getResults.setTestCaseCompleted(false);
		}
		return getResults;
	}
}
