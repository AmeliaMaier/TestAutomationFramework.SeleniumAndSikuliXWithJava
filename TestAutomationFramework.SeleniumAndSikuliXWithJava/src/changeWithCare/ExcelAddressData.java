package changeWithCare;

import java.util.List;

import org.apache.commons.lang3.time.StopWatch;

public class ExcelAddressData {

	public String streetAddress;
	public String city;
	public String state;
	public String zip;
	public String coreURL;
	public String expectedResult;
	public String gusaid;
	public String otnbURL;
	public String commentAdding;
	public String commentExpected;
	public String restrictionReason;
	public String restricedStatus;
	boolean addressExpectedResult;
	boolean addressActualResult;
	boolean addressCompleted;
	public boolean excelActualResult;
	StopWatch addressStopwatch;

	/**
	 * @param addressDataInput
	 * 
	 *            This is the constructor for this class that takes a list of
	 *            address date that was read in from the excel file and links
	 *            each pieace of data to its proper title. It also starts the
	 *            Stopwatch to track how long the given building takes to
	 *            complete the test cases it is going to be run through and
	 *            resets the actual test case value and the test case completion
	 *            value to allow tracking of these variables at the building
	 *            level
	 */
	public void excelAddressData(List<String> addressDataInput) {
		coreURL = addressDataInput.get(0).trim();
		otnbURL = addressDataInput.get(1).trim();
		gusaid = addressDataInput.get(2).trim();
		streetAddress = addressDataInput.get(3).trim();
		city = addressDataInput.get(4).trim();
		state = addressDataInput.get(5).trim();
		zip = addressDataInput.get(6).trim().substring(0, 5);
		commentAdding = addressDataInput.get(7).trim();
		commentExpected = addressDataInput.get(8).trim();
		restrictionReason = addressDataInput.get(9).trim();
		restricedStatus = addressDataInput.get(10).trim();
		expectedResult = addressDataInput.get(11).trim();
		resetAddressResults();
	}

	/**
	 * this method is a support method that controls the test case result
	 * variables for the current building
	 */
	public void resetAddressResults() {
		setExpectedResults();
		addressActualResult = false;
		addressCompleted = false;
		addressStopwatch = new StopWatch();
		addressStopwatch.start();
	}

	/**
	 * this method is a support method that interprets the users expected
	 * results so it can be easily compared to actual results
	 */
	public void setExpectedResults() {
		if (expectedResult.equalsIgnoreCase("pass")) {
			addressExpectedResult = true;
		} else if (expectedResult.equalsIgnoreCase("fail")) {
			addressExpectedResult = false;
		} else {
			addressExpectedResult = false;
//			FileWriterUtility.safeWriter(
//					"Error Location: testData.TestCase.setExpectedResults; Test's expected result not recognized. Set to Fail by default.");
		}
	}

	/**
	 * @param aResults
	 * 
	 *            This gives the main class access to set the actual results for
	 *            the building
	 */
	public void setActualResults(boolean aResults) {
		addressActualResult = aResults;
	}
	

	/**
	 * @param tcCompleted
	 * 
	 *            This gives the main class access to set the completion status
	 *            for the building
	 */
	public void setTestCaseCompleted(boolean tcCompleted) {
		addressCompleted = tcCompleted;
	}

	/**
	 * This gives the main class access to the actual results for the building,
	 * translating the boolean to a user friendly string
	 */
	public String getAddressActualResult() {
		if (addressActualResult) {
			return "Pass";
		}
		return "Fail";
	}
	
	public void setExcelActualResult(){
		if(!excelActualResult) return;
		if (!addressCompleted) excelActualResult = false;
		else if (!addressExpectedResult == addressActualResult)excelActualResult = false;
	}
	
	public void setExcelActualResult(boolean eaResults){
		excelActualResult = eaResults;
	}
	
	public String getExcelActualResult() {
		if (excelActualResult) {
			return "Pass";
		}
		return "Fail";
	}

	/**
	 * @return
	 * 
	 * 		This gives the main class access to the test completion status
	 *         for the building, translating the boolean to a user friendly
	 *         string
	 */
	public String getTestStatus() {

		if (addressCompleted) {
			return "Pass";
		}
		return "Fail";
	}

	/**
	 * 
	 * @return
	 * 
	 * this method gives the framework access to the total time since the building started being tested
	 */
	public long getTotalTime() {
		return addressStopwatch.getTime();
	}

	/**
	 * @return
	 * 
	 * 		This method returns a pass or fail based on the completion status
	 *         and actual to expected results of the building. If the test cases
	 *         failed to complete, the building will fail. Otherwise, if the
	 *         expected and actual results match the building will pass.
	 *         Otherwise, the building fails.
	 */
	public String getPassFail() {
		if (!addressCompleted) {
			return "Fail";
		} else if (addressExpectedResult == addressActualResult) {
			return "Pass";
		} else {
			return "Fail";
		}
	}
}
