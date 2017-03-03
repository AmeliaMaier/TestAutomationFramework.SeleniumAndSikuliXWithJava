package FinishChangesHereTestCases;

import java.util.List;

import changeWithCare.SeleniumUtility;
import changeWithCare.SikuliXUtility;
import doNotChange.*;

public class SearchConfirmedWithImage extends TestCaseSuperclass implements TestCaseInterface
{
	String keyword,imageFileName;
	
	public SearchConfirmedWithImage(Log l)
	{
		super(l);
	}

	public void readVariables(List<String> listOfTestCaseVariables)
	{
		actualResult = false;
		testCaseCompleted = false;
		if (listOfTestCaseVariables.size() != 4)
		{
			log.write("Error Location:  SearchConfirmedWithImage.readVariables; The number of variables provided do not match the expected information for this test case. No further steps taken. Variables provided: " + listOfTestCaseVariables.toString());
			variablesNeededAvailable = false;
			expectedResults = true;
			return;
		}
		variablesNeededAvailable = true;
		expectedResults = translateExpectedResults(listOfTestCaseVariables.get(1));
		keyword = listOfTestCaseVariables.get(2);
		imageFileName = listOfTestCaseVariables.get(3);
	}

	public boolean[] runSteps()
	{
		String searchTextInputFieldFileName = "SearchTextInputField.JPG";
		String searchIconButtonXPath = "//*[@id='searchbox']/button";		
		if (!variablesNeededAvailable) return fillFinalTestCaseResults();
		log.write("Attempting to search for a product by keyword and confirm it is found base on an image.");
		if(!SikuliXUtility.enterText("images/"+searchTextInputFieldFileName, keyword, log))return fillFinalTestCaseResults();
		if(!SeleniumUtility.elementByXPath_ClickButton(searchIconButtonXPath, "Search Button", log))return fillFinalTestCaseResults();
		testCaseCompleted = true;
		actualResult = SikuliXUtility.verifyImage("images/" + imageFileName, log);
		log.write("Search for a product by keyword and confirming it is found base on an image is completed.");
		return fillFinalTestCaseResults();
	}

}
