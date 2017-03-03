package FinishChangesHereTestCases;

import java.util.List;

import changeWithCare.SeleniumUtility;
import doNotChange.Log;
import doNotChange.TestCaseInterface;
import doNotChange.TestCaseSuperclass;

public class Login extends TestCaseSuperclass implements TestCaseInterface
{
	String userName;
	String password;
	
	public Login(Log l)
	{
		super(l);
	}

	public void readVariables(List<String> listOfTestCaseVariables)
	{
		actualResult = false;
		testCaseCompleted = false;
		if (listOfTestCaseVariables.size() != 4)
		{
			log.write("Error Location:  Login.readVariables; The number of variables provided do not match the expected information for this test case. No further steps taken. Variables provided: " + listOfTestCaseVariables.toString());
			variablesNeededAvailable = false;
			expectedResults = true;
			return;
		}
		variablesNeededAvailable = true;
		expectedResults = translateExpectedResults(listOfTestCaseVariables.get(1));
		userName = listOfTestCaseVariables.get(2);
		password = listOfTestCaseVariables.get(3);
	}

	public boolean[] runSteps()
	{
		String loginButtonXPath = "//*[@id='header']/div[2]/div/div/nav/div[1]/a";
		String emailTextFieldXPath = "//*[@id='email']";
		String passwordTextFieldXPath = "//*[@id='passwd']";
		String signInButtonXPath = "//*[@id='SubmitLogin']/span";
		String confirmationText = "Sign out";		
		if (!variablesNeededAvailable) return fillFinalTestCaseResults();
		log.write("Attempting to Login");
		if(!SeleniumUtility.elementByXPath_ClickButton(loginButtonXPath, "login button on main page", log)) return fillFinalTestCaseResults();
		if(!SeleniumUtility.elementByXPath_EnterTextValue(emailTextFieldXPath, userName, "username in the email field", log))return fillFinalTestCaseResults();
		if(!SeleniumUtility.elementByXPath_EnterTextValue(passwordTextFieldXPath, password, "password in the password field", log)) return fillFinalTestCaseResults();
		if(!SeleniumUtility.elementByXPath_ClickButton(signInButtonXPath, "sign in button on login page", log))return fillFinalTestCaseResults();
		testCaseCompleted = true;
		actualResult = SeleniumUtility.elementByText_ConfirmTextExists(confirmationText, log);
		log.write("Login test case completed");
		return fillFinalTestCaseResults();
	}

}
