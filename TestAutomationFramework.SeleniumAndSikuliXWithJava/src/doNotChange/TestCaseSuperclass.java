package doNotChange;

public class TestCaseSuperclass
{
	protected boolean expectedResults, actualResult, testCaseCompleted, variablesNeededAvailable;
	protected boolean[] finalTestCaseResults;
	protected Log log;

	public TestCaseSuperclass(Log l)
	{
		log = l;
	}

	public boolean translateExpectedResults(String eResults)
	{
		if (eResults.equalsIgnoreCase("pass"))
		{
			return true;
		}
		if (eResults.equalsIgnoreCase("fail"))
		{
			return false;
		}
		log.write("Error Location: OpenPage.setExpectedResults; Test's expected result not recognized. Set to Pass by default.");
		return true;
	}

	public boolean[] fillFinalTestCaseResults()
	{
		finalTestCaseResults = new boolean[] { expectedResults, actualResult, testCaseCompleted };
		return finalTestCaseResults;
	}
}
