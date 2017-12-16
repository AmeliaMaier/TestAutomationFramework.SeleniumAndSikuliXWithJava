package doNotChange;


import java.util.ArrayList;
import java.util.List;


import StartChangesHereSwitchStatements.Run;
//import changeWithCare.ExcelAddressData;


public class Main
{

	// variables shared across most or all test suites and test cases
	static String directoryResultsLocation = "TestingDirectoryResults";
	static String txtCurrentSuiteLocation;
	static List<String> listOfTestsToRun = new ArrayList<String>();
	static List<String> listOfTestCaseVariables = new ArrayList<String>();
	static MasterLog masterLog;
	static Log log;
	static GetResults getResults;
	static Run run;

	/* Main control section */
	/**
	 * @param args
	 *            Main.main is the main control section for the Automation
	 *            Testing Framework. The Directory Document is read in for the
	 *            name and location of all test suites to be run. The Test
	 *            suites are then read in for the name and variables for all the
	 *            tests to be run in the given suite, as well as the expected
	 *            result for each test. Note: If a test is expected to fail,
	 *            failure will be a pass for the test. This allows negative
	 *            testing. If a test does not have its expected result (what the
	 *            framework will consider a pass), the test suite is stopped and
	 *            the framework closes the browser to prepare for the next test
	 *            suite.If a test name is not recognized, the framework will not
	 *            try to run it but will attempt to move to the next test in the
	 *            suite. As the tests in a suite are run, a detailed results
	 *            file will be written out with the same name as the test suite
	 *            + Results. Once a test suite is completed, it's general
	 *            results (either a pass or fail) will write out to the
	 *            Directory Results file. If the Excel option is used to feed
	 *            multiple addresses in to a set of tests, a mid level results
	 *            file will be created with the name of the tests suite +
	 *            ExcelResults. This file will be pipe deliminated so it can be
	 *            imported into excel for easy reporting on the results per
	 *            address. This allows the user to have a high level review of
	 *            all the test suites that are run without having to look
	 *            through the detailed log unless a suite fails. Current,
	 *            results are also output to the console to assist in
	 *            troubleshooting during development of tests.
	 */
	public static void main(String[] args)
	{
		run = new Run();
		String directoryLocation = "TestingDirectory.txt";
		masterLog = new MasterLog(directoryResultsLocation);
		log = new Log(masterLog);
		// filling in directory for file names to be used
		List<String> listOfTestSuites = FileReaderUtility.getListOfTestSuites(directoryLocation);
		// confirm there are lines in the directory file
		if (listOfTestSuites.isEmpty() || listOfTestSuites.size() == 0)
		{
			masterLog.writeToDirectoryResults("Error Location: Main.main; Directory File Failed. No Suites found. No Tests Run. Please check your file: " + directoryLocation);
			return;
		}
		// loop to read each test suite and fill in list of test cases for each
		for (int iteratorListOfTestSuites = 0; iteratorListOfTestSuites < listOfTestSuites.size(); iteratorListOfTestSuites++)
		{
			// open writer for new suite (will automatically close last suite
			masterLog.startNewSuite(listOfTestSuites.get(iteratorListOfTestSuites));
			// read current test case file & determine steps to be run
			txtCurrentSuiteLocation = listOfTestSuites.get(iteratorListOfTestSuites) + ".txt";
			listOfTestsToRun = FileReaderUtility.getListOfTestsToRun(txtCurrentSuiteLocation, log);
			// create test case results file to log results in
			// the results file currently in use will always be the last entry
			// in the list
			// if testToRun was read in correctly & has at least one test listed
			if (listOfTestsToRun.isEmpty() || listOfTestsToRun.size() == 0)
			{
				masterLog.writeToSuiteResults("Error Location: COREMain.main; Creating list of tests to run failed. No tests run. Please check your test suite file: " + txtCurrentSuiteLocation);
				masterLog.writeToDirectoryResults("Test Suite Location: " + txtCurrentSuiteLocation + ", General Results: Not Executed");
				continue;// will move on to the next test suite
			}
			// loop to read each test case line and fill in list of variables
			// for each
			for (int iteratorListofTestsToRun = 0; iteratorListofTestsToRun < listOfTestsToRun.size(); iteratorListofTestsToRun++)
			{
				listOfTestCaseVariables = FileReaderUtility.splitLine(listOfTestsToRun.get(iteratorListofTestsToRun));
				// the first variable in every test case is the name of the test
				// case to be run
				if (listOfTestCaseVariables.get(0).equalsIgnoreCase("ExcelGroup"))
				{// if ExcelGroup, all test cases that come after it are part of
					// the ExcelGroup test case, so it breaks out of this loop
					// and moves into another
					masterLog.startNewExcel(listOfTestSuites.get(iteratorListOfTestSuites));
					runExcelGroupTests(iteratorListofTestsToRun);
					break;
					// no further work will be done on the test cases in this
					// suite
				} else
				{
					if (listOfTestCaseVariables.size() < 2)
					{
						masterLog.writeToSuiteResults("Expected Test Result missing. Set to Pass by default.");
						listOfTestCaseVariables.add("pass");
					}
					getResults = run.runTestCase(getResults, listOfTestCaseVariables, txtCurrentSuiteLocation, iteratorListofTestsToRun, log);
					masterLog.writeToSuiteResults(listOfTestCaseVariables.get(0) + ": " + getResults.getPassFail().toUpperCase());
					masterLog.writeToSuiteResults(getResults.getResonForResult());
					masterLog.writeToSuiteResults("Test Case Total Time: " + getResults.getTotalTime() + " millisec");
				}
				// if the test fails, no further tests are run in the suite
				if (!getResults.getTestResult()) break;
			}
			masterLog.writeToDirectoryResults("Test Suite Location: " + txtCurrentSuiteLocation + ", General Results: " + getResults.getPassFail().toUpperCase());
		}
		masterLog.closeLogs();
	}

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

	public static void runExcelGroupTests(int iteratorListofTestsToRun)
	{
		//redacted - this has to be reorganized per project and contained project specific info
	
	}
}
