package doNotChange;

import org.apache.commons.lang3.time.StopWatch;

/**
 * @author amaier TestCase is a class designed to hold the variables related to
 *         the test currently being run, allowing the Automation Framework to
 *         compare the expected result to the actual result of the test.
 *         TestCase also tracks if a test case is completed or not. If a test
 *         case ends before it is completed, it fails and a note is added to the
 *         detailed log showing that it failed to complete.
 */
public class GetResults
{

	boolean expectedResult;
	boolean actualResult;
	boolean testCaseCompleted;
	StopWatch stopwatch = new StopWatch();
	Log log;
	boolean[] arrayOfResults;

	public GetResults(Log l)
	{
		log = l;
	}

	public void runTestCaseSteps(TestCaseInterface testCaseInterface)
	{
		stopwatch = new StopWatch();
		stopwatch.start();
		arrayOfResults = testCaseInterface.runSteps();
		if (arrayOfResults.length != 3)
		{
			log.write("Error Location: GetResults.runTestCaseSteps; The number of results from the test case being run are not correct. Assuming the test case was not completed but was expected to pass. Results provided were: " + arrayOfResults.toString());
			expectedResult = true;
			actualResult = false;
			testCaseCompleted = false;
		}
		expectedResult = arrayOfResults[0];
		actualResult = arrayOfResults[1];
		testCaseCompleted = arrayOfResults[2];
	}

	/**
	 * @param eResults
	 *            TestCase.setExpectedResults receives the first variable after
	 *            the test name on a given line in a test suite. "Pass"
	 *            (ignoring case) will set expected result to True. "Fail"
	 *            (ignoring case) will set expected result to False. If the
	 *            string passed in is not recognized, expected result will be
	 *            set to False and a note will be added to the detailed log file
	 *            that this was done.
	 */
	public void setExpectedResults(String eResults)
	{
		if (eResults.equalsIgnoreCase("pass"))
		{
			expectedResult = true;
		} else if (eResults.equalsIgnoreCase("fail"))
		{
			expectedResult = false;
		} else
		{
			expectedResult = false;
			log.write("Error Location: testData.TestCase.setExpectedResults; Test's expected result not recognized. Set to Fail by default.");
		}
	}

	/**
	 * @param aResults
	 *            TestCase.setActualResults is used to track the results being
	 *            seen in the test case. Actual results are only set to True if
	 *            the test case has completed and passed; otherwise, it is left
	 *            as False.
	 */
	public void setActualResults(boolean aResult)
	{
		actualResult = aResult;
	}

	/* tested */
	/**
	 * @param tcCompleted
	 *            TestCase.setTestCaseCompleted is used to track the completion
	 *            of the test case. Test case completed is only set to True if
	 *            the test case has completed, regardless of if it passes or
	 *            fails.
	 */
	public void setTestCaseCompleted(boolean tcCompleted)
	{
		testCaseCompleted = tcCompleted;
	}

	/**
	 * 
	 * @return
	 * 
	 * 		this method gives the framework access to the total time since
	 *         the test case started
	 */
	public long getTotalTime()
	{
		return stopwatch.getTime();
	}

	/**
	 * @return TestCase.getTestResult returns True if the expected result set by
	 *         the user matches the actual result seen. The method returns False
	 *         if the test case either didn't complete or if the expected result
	 *         set by the user does not match the actual result seen. Ex:
	 *         Expected - fail; Actual - fail; test completed; Returns - True
	 */
	public boolean getTestResult()
	{
		if (!testCaseCompleted)
		{
			return false;
		} else
		{
			return (expectedResult == actualResult);
		}
	}

	/**
	 * @return TestCase.getPassFail returns Pass if the expected result set by
	 *         the user matches the actual result seen. The method returns Fail
	 *         if the test case either didn't complete or if the expected result
	 *         set by the user does not match the actual result seen. Ex:
	 *         Expected - fail; Actual - fail; test completed; Returns - Pass
	 */
	public String getPassFail()
	{
		if (!testCaseCompleted)
		{
			return "Fail";
		} else if (expectedResult == actualResult)
		{
			return "Pass";
		} else
		{
			return "Fail";
		}
	}

	/**
	 * TestCase.getReasonForResult writes a legible reason for the pass/fail
	 * provided in getPassFail and getTestResults so the user can tell the
	 * differance between a test that failed to complete and a test the
	 * completed with the wrong results. These outputs are sent to the detailed
	 * log file for the test suite currently in use.
	 */
	public String getResonForResult()
	{
		if (!testCaseCompleted)
		{
			return "Test Case Failed to Complete";
		} else if (expectedResult == actualResult)
		{
			return "Test Case Complete with Expected Result.";
		} else
		{
			return "Test Case Complete with a Different Result than Expected .";
		}
	}

	public boolean getActualResult(){
		return actualResult;
	}
	
	public boolean getTestCaseCompleted(){
		return testCaseCompleted;
	}
}
