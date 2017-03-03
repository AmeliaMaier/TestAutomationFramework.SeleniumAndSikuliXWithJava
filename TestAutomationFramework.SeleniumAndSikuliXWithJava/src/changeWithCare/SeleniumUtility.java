package changeWithCare;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


import doNotChange.Log;


/**
 * @author amaier SeleniumUtility is a utility class that controls all
 *         interaction between the Automation Framework and the Selenium
 *         library. This class allows multiple classes to all access the same
 *         methods that wrap selenium commands in Error handling.
 */
public class SeleniumUtility
{
	// static List <String> tabList = new ArrayList<String>();
	static WebDriver driver = null;

	/**
	 * SeleniumUtility.openBrowser closes any existing instance of the browser
	 * and then opens a new instance of said browser.
	 */
	public static boolean openBrowser(Log log)
	{
		try
		{
			driver.close();
		} catch (Exception ex)
		{
		}

		try
		{
			log.write("Attempting to open a new browser instance.");
			driver = new ChromeDriver();
			// tabList = new ArrayList<String>();
		} catch (Exception ex)
		{
			log.write("Something may have gone wrong opening the browser. No further tests run. Please see log file in console.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
		log.write("New browser instance opened successfully.");
		return true;
	}

	/**
	 * @param urlList
	 *            SeleniumUtility.openURL takes in a string that represents the
	 *            url to be opened by the browser instance. It waits 20 seconds
	 *            after the browser is opened to attempt to send it the URL to
	 *            navigate to.
	 */
	public static boolean openURL(String url, Log log)
	{

		// Open url in Mozzila Browser
		try
		{
			// Open url in Mozzila Browser
			Thread.sleep(5000);
			driver.get(url);
			log.write(url + " opened in browser.");
			 driver.manage().window().maximize();
			// tabList.add(0, driver.getWindowHandle());
		} catch (Exception ex)
		{
			log.write("Something may have gone wrong loading url: " + url + ". No further tests run. Please see log in console.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}

		try
		{
			Thread.sleep(10000);
			log.write("Open URL test case result: " + driver.getCurrentUrl().equalsIgnoreCase(url));
			return driver.getCurrentUrl().equalsIgnoreCase(url);
		} catch (Exception ex)
		{
			log.write("Something may have gone wrong loading url: " + url + ". No further tests run. Please see log in console.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
	}

	/*
	public static boolean openURL_ConfirmByURL(List<String> urlList, Log log)
	{
		if (urlList.size() < 4)
		{
			log.write("Error Location: util.SeleniumUtility.openURL; Either the URL or confirmation text is missing from your test case information. No further tests run.");
			return false;
		}

		String url = urlList.get(2);

		// Open url in Mozzila Browser
		try
		{
			// Open url in Mozzila Browser
			Thread.sleep(5000);
			driver.get(url);
			log.write(url + " opened in browser.");
			// tabList.add(0, driver.getWindowHandle());
		} catch (Exception ex)
		{
			log.write("Something may have gone wrong loading url: " + urlList + ". No further tests run. Please see log in console.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}

		int currentTime = 0;
		int maxTime = 60;

		try
		{
			Thread.sleep(10000);
			log.write("Open URL test case result: " + driver.getCurrentUrl().equalsIgnoreCase(url));
			Thread.sleep(5000);
			return driver.getCurrentUrl().equalsIgnoreCase(url);
		} catch (Exception ex)
		{
			log.write("Something may have gone wrong loading url: " + url + ". No further tests run. Please see log in console.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
	}
*/

	/**
	 * SeleniumUtility.closeBrowser closes the current browser instance attached
	 * to the driver object.
	 */
	public static boolean closeBrowser(Log log)
	{
		try
		{
			// closes the browser driver is focused on
			driver.close();
			log.write("Browser closed.");
		} catch (Exception ex)
		{
			log.write("Something may have gone wrong closing the browser down. No further tests run. Please see log in console.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
		log.write("The browser has been closed and the system reset.");
		return true;
	}

	/**
	 * @param fieldId
	 * @param input
	 * @param outputText
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.elementByID_EnterTextValue allows the framework
	 *         to pass in a field id and the user's input. The method will find
	 *         the element on the page with the id provided and send the user's
	 *         input as key strokes to the given field. If the method can't find
	 *         the element, it will wait a total of maxTime (60 sec), attempting
	 *         every 5 seconds. - This allows for slow, but acceptable loading
	 *         times - maxTime should be set to whatever total time constitutes
	 *         a failure of the system due to excessive loading time. - this may
	 *         vary from the maxTime in other methods, depending on user
	 *         requirements - 60 seconds set without business input, just a
	 *         generic setting right now. If the method fails to find the
	 *         element after maxTime or fails to send the input successfully,
	 *         the test case will be set to fail and test case completion will
	 *         be set to false. In some cases, failure at this point is actually
	 *         a completion of a test that was expected to fail, this logic
	 *         should be included in the method that called this one. If an
	 *         error occurs, the outputText provided will be added to a generic
	 *         message to be written out to the detailed test suite log. This
	 *         allows easy error reporting and troubleshooting.
	 */
	public static boolean elementByID_EnterTextValue(String fieldId, String input, String outputText, Log log)
	{
		int currentTime = 0;
		int maxTime = 60;
		try
		{
			while (driver.findElements(By.id(fieldId)).isEmpty() && currentTime < maxTime)
			{
				// wait up to 60 secs for field
				Thread.sleep(5000);
				currentTime += 5;
				log.write("Waiting to find " + outputText + " element: " + currentTime + " seconds");
			}
		} catch (Exception ex)
		{
			log.write("Wait Failed to Execute");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
		}

		try
		{
			// Enter user input into selected field
			driver.findElement(By.id(fieldId)).sendKeys(input);
			log.write(outputText + " entered into page.");
		} catch (Exception ex)
		{
			log.write("Unable to enter " + outputText + " into page. No further tests run.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param dropDownID
	 * @param visibleText
	 * @param outputText
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.selectorByID_ByVisibleTexts allows the framework
	 *         to pass in a field id and the user's input. The method will find
	 *         the element on the page with the id provided and select the
	 *         user's request from the available options in the drop down
	 *         identified by the id. If the method can't find the element, it
	 *         will wait a total of maxTime (60 sec), attempting every 5
	 *         seconds. This allows for slow, but acceptable loading times.
	 *         MaxTime should be set to whatever total time constitutes a
	 *         failure of the system due to excessive loading time. This may
	 *         vary from the maxTime in other methods, depending on user
	 *         requirements 60 seconds set without business input, just a
	 *         generic setting right now. If the method fails to find the
	 *         element after maxTime or fails to send the input successfully,
	 *         the test case will be set to fail and test case completion will
	 *         be set to false. In some cases, failure at this point is actually
	 *         a completion of a test that was expected to fail, this logic
	 *         should be included in the method that called this one. If an
	 *         error occurs, the outputText provided will be added to a generic
	 *         message to be written out to the detailed test suite log. This
	 *         allows easy error reporting and troubleshooting.
	 */
	public static boolean selectorByID_ByVisibleText(String dropDownID, String visibleText, String outputText, Log log)
	{
		int currentTime = 0;
		int maxTime = 60;
		try
		{
			while (driver.findElements(By.id(dropDownID)).isEmpty() && currentTime < maxTime)
			{
				// wait up to 60 secs for field
				Thread.sleep(5000);
				currentTime += 5;
				log.write("Waiting to find " + outputText + " element: " + currentTime + " seconds");
			}
		} catch (Exception ex)
		{
			log.write("Wait Failed to Execute");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
		}
		try
		{
			Select dropdown = new Select(driver.findElement(By.id(dropDownID)));
			dropdown.selectByVisibleText(visibleText);
			log.write("Entered " + outputText);
		} catch (Exception ex)
		{
			log.write("Unable to enter " + outputText + ". No further tests run.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param xPath
	 * @param textToConfirm
	 * @param outputText
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.elementByXPath_ConfirmText allows the framework
	 *         to pass in a field xpath and the text the field should contain.
	 *         The method will find the element on the page with the xpath
	 *         provided and compare the expected text to the text in the given
	 *         field. If the method can't find the element, it will wait a total
	 *         of maxTime (60 sec), attempting every 5 seconds. This allows for
	 *         slow, but acceptable loading times MaxTime should be set to
	 *         whatever total time constitutes a failure of the system due to
	 *         excessive loading time. This may vary from the maxTime in other
	 *         methods, depending on user requirements 60 seconds set without
	 *         business input, just a generic setting right now. If the method
	 *         fails to find the element after maxTime or fails to send the
	 *         input successfully, the test case will be set to fail and test
	 *         case completion will be set to false. In some cases, failure at
	 *         this point is actually a completion of a test that was expected
	 *         to fail, this logic should be included in the method that called
	 *         this one. If an error occurs, the outputText provided will be
	 *         added to a generic message to be written out to the detailed test
	 *         suite log. This allows easy error reporting and troubleshooting.
	 */
	public static boolean elementByXPath_ConfirmText(String xPath, String textToConfirm, String outputText, Log log)
	{
		int currentTime = 0;
		int maxTime = 60;
		try
		{
			while (driver.findElements(By.xpath(xPath)).isEmpty() && currentTime < maxTime)
			{
				// wait up to 60 secs for field
				Thread.sleep(5000);
				currentTime += 5;
				log.write("Waiting to find " + outputText + " element: " + currentTime + " seconds");
			}
		} catch (Exception ex)
		{
			log.write("Wait Failed to Execute");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
		}
		try
		{
			if (!driver.findElement(By.xpath(xPath)).getText().equalsIgnoreCase(textToConfirm))
			{
				log.write("Unable to find " + textToConfirm + ". No further tests run.");
				return false;
			}
			driver.findElement(By.xpath(xPath)).click();
			log.write(outputText + " found");
		} catch (Exception ex)
		{
			log.write("Unable to find " + outputText + ". No further tests run.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param fieldId
	 * @param outputText
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.elementByID_SelectButton allows the framework to
	 *         pass in an id. The method will find the element on the page with
	 *         the id provided and click the button. If the method can't find
	 *         the element, it will wait a total of maxTime (60 sec), attempting
	 *         every 5 seconds. - This allows for slow, but acceptable loading
	 *         times - maxTime should be set to whatever total time constitutes
	 *         a failure of the system due to excessive loading time. - this may
	 *         vary from the maxTime in other methods, depending on user
	 *         requirements - 60 seconds set without business input, just a
	 *         generic setting right now. If the method fails to find the
	 *         element after maxTime or fails to click the button successfully,
	 *         the test case will be set to fail and test case completion will
	 *         be set to false. In some cases, failure at this point is actually
	 *         a completion of a test that was expected to fail, this logic
	 *         should be included in the method that called this one. If an
	 *         error occurs, the outputText provided will be added to a generic
	 *         message to be written out to the detailed test suite log. This
	 *         allows easy error reporting and troubleshooting.
	 */
	public static boolean elementByID_ClickButton(String fieldId, String outputText, Log log)
	{
		int currentTime = 0;
		int maxTime = 60;

		try
		{
			while (driver.findElements(By.id(fieldId)).isEmpty() && currentTime < maxTime)
			{
				// wait up to 60 secs
				Thread.sleep(5000);
				currentTime += 5;
				log.write("Waiting for " + outputText + " element: " + currentTime + " seconds");
			}
		} catch (Exception ex)
		{
			log.write("Wait Failed to Execute");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
		}
		try
		{
			driver.findElement(By.id(fieldId)).click();
			log.write(outputText + " selected.");
		} catch (Exception ex)
		{
			log.write("Unable to select " + outputText + ". No further tests run.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
		return true;
	}

	/**
	 * @param fieldPartialText
	 * @param outputText
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.elementByText_SelectButton allows the framework
	 *         to pass in a piece of the fields text. The method will find the
	 *         element on the page with the partial text provided and clicks the
	 *         button. If the method can't find the element, it will wait a
	 *         total of maxTime (60 sec), attempting every 5 seconds. - This
	 *         allows for slow, but acceptable loading times - maxTime should be
	 *         set to whatever total time constitutes a failure of the system
	 *         due to excessive loading time. - this may vary from the maxTime
	 *         in other methods, depending on user requirements - 60 seconds set
	 *         without business input, just a generic setting right now. If the
	 *         method fails to find the element after maxTime or fails to click
	 *         the button successfully, the test case will be set to fail and
	 *         test case completion will be set to false. In some cases, failure
	 *         at this point is actually a completion of a test that was
	 *         expected to fail, this logic should be included in the method
	 *         that called this one. If an error occurs, the outputText provided
	 *         will be added to a generic message to be written out to the
	 *         detailed test suite log. This allows easy error reporting and
	 *         troubleshooting.
	 */
	public static boolean elementByText_ClickButton(String fieldPartialText, String outputText, Log log)
	{
		int currentTime = 0;
		int maxTime = 60;

		try
		{
			while (driver.findElements(By.partialLinkText(fieldPartialText)).isEmpty() && currentTime < maxTime)
			{
				// wait up to 60 secs
				Thread.sleep(5000);
				currentTime += 5;
				log.write("Waiting for " + outputText + " element: " + currentTime + " seconds");
			}
		} catch (Exception ex)
		{
			log.write("Wait Failed to Execute");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
		}
		try
		{
			driver.findElement(By.partialLinkText(fieldPartialText)).click();
			log.write(outputText + " selected.");
		} catch (Exception ex)
		{
			log.write("Unable to select " + outputText + ". No further tests run.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
		return true;

	}

	/**
	 * @param buttonXPath
	 * @param outputText
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.elementByXPath_SelectButton allows the framework
	 *         to pass in an xpath. The method will find the element on the page
	 *         with the xpath provided and clicks the button. If the method
	 *         can't find the element, it will wait a total of maxTime (60 sec),
	 *         attempting every 5 seconds. - This allows for slow, but
	 *         acceptable loading times - maxTime should be set to whatever
	 *         total time constitutes a failure of the system due to excessive
	 *         loading time. - this may vary from the maxTime in other methods,
	 *         depending on user requirements - 60 seconds set without business
	 *         input, just a generic setting right now. If the method fails to
	 *         find the element after maxTime or fails to click the button
	 *         successfully, the test case will be set to fail and test case
	 *         completion will be set to false. In some cases, failure at this
	 *         point is actually a completion of a test that was expected to
	 *         fail, this logic should be included in the method that called
	 *         this one. If an error occurs, the outputText provided will be
	 *         added to a generic message to be written out to the detailed test
	 *         suite log. This allows easy error reporting and troubleshooting.
	 */
	public static boolean elementByXPath_ClickButton(String buttonXPath, String outputText, Log log)
	{
		int currentTime = 0;
		int maxTime = 60;

		try
		{
			while (driver.findElements(By.xpath(buttonXPath)).isEmpty() && currentTime < maxTime)
			{
				// wait up to 60 secs
				Thread.sleep(5000);
				currentTime += 5;
				log.write("Waiting for " + outputText + " element: " + currentTime + " seconds");
			}
		} catch (Exception ex)
		{
			log.write("Wait Failed to Execute");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
		}
		try
		{
			driver.findElement(By.xpath(buttonXPath)).click();
			log.write(outputText + " selected.");
		} catch (Exception ex)
		{
			log.write("Unable to select " + outputText + ". No further tests run.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
		return true;

	}

	/**
	 * @param textToSearchFor
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.elementByText_ConfirmTextExists allows the
	 *         framework to pass in a piece of the fields text. The method will
	 *         find the element on the page with the partial text provided and
	 *         confirm it exists. If the method can't find the element, it will
	 *         wait a total of maxTime (60 sec), attempting every 5 seconds. -
	 *         This allows for slow, but acceptable loading times - maxTime
	 *         should be set to whatever total time constitutes a failure of the
	 *         system due to excessive loading time. - this may vary from the
	 *         maxTime in other methods, depending on user requirements - 60
	 *         seconds set without business input, just a generic setting right
	 *         now. If the method fails to find the element after maxTime, the
	 *         test case will be set to fail and test case completion will be
	 *         set to false. In some cases, failure at this point is actually a
	 *         completion of a test that was expected to fail, this logic should
	 *         be included in the method that called this one. If an error
	 *         occurs, the outputText provided will be added to a generic
	 *         message to be written out to the detailed test suite log. This
	 *         allows easy error reporting and troubleshooting.
	 */
	public static boolean elementByText_ConfirmTextExists(String textToSearchFor, Log log)
	{

		int currentTime = 0;
		int maxTime = 60;

		try
		{
			log.write("Attempting to find " + textToSearchFor + " on the page.");
			while (driver.findElements(By.xpath("//*[contains(text(),'" + textToSearchFor + "')]")).isEmpty() && currentTime < maxTime)
			{
				// wait up to 60 secs
				Thread.sleep(5000);
				currentTime += 5;
				log.write("Waiting for " + textToSearchFor + " element: " + currentTime + " seconds");
			}
			return !driver.findElements(By.xpath("//*[contains(text(),'" + textToSearchFor + "')]")).isEmpty();
		} catch (Exception ex)
		{
			log.write("Wait Failed to Execute");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
	}

	/**
	 * 
	 * @param textToSearchFor1
	 * @param textToSearchFor2
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.elementByText_ConfirmTextExists_TwoOptions allows
	 *         the framework to pass in two strings and confirm that at least
	 *         one exists on the page. The method will find the element(s) on
	 *         the page with the partial text provided. If the method can't find
	 *         the element, it will wait a total of maxTime (15 sec), attempting
	 *         every 5 seconds. This allows for slow, but acceptable loading
	 *         times MaxTime should be set to whatever total time constitutes a
	 *         failure of the system due to excessive loading time. This may
	 *         vary from the maxTime in other methods, depending on user
	 *         requirements 5 seconds set without business input, just a generic
	 *         setting right now. If the method fails to find the element(s)
	 *         after maxTime, the test case status and results are not affected,
	 *         as this is a soft error. If this causes a failure in the test
	 *         case, it should be handled in the method calling this one. If an
	 *         error occurs, the outputText provided will be added to a generic
	 *         message to be written out to the detailed test suite log. This
	 *         allows easy error reporting and troubleshooting.
	 */
	public static boolean elementByText_ConfirmTextExists_TwoOptions(String textToSearchFor1, String textToSearchFor2, Log log)
	{

		int currentTime = 0;
		int maxTime = 30;

		try
		{
			log.write("Checking page for two phrases (" + textToSearchFor1 + ") and (" + textToSearchFor2 + ")");
			while (driver.findElements(By.xpath("//*[contains(text(),'" + textToSearchFor1 + "')]")).isEmpty() && driver.findElements(By.xpath("//*[contains(text(),'" + textToSearchFor2 + "')]")).isEmpty() && currentTime < maxTime)
			{
				Thread.sleep(5000);
				currentTime += 5;
				log.write("Waiting for " + textToSearchFor1 + " or " + textToSearchFor2 + " element: " + currentTime + " seconds");
			}
			return !(driver.findElements(By.xpath("//*[contains(text(),'" + textToSearchFor1 + "')]")).isEmpty() && driver.findElements(By.xpath("//*[contains(text(),'" + textToSearchFor2 + "')]")).isEmpty());
		} catch (Exception ex)
		{
			log.write("Wait Failed to Execute");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
	}

	/**
	 * 
	 * @param textToSearchFor
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.elementByText_ConfirmTextExists_OneOption allows
	 *         the framework to pass in a string and confirm that it exists on
	 *         the page. The method will find the element on the page with the
	 *         partial text provided. If the method can't find the element, it
	 *         will wait a total of maxTime (15 sec), attempting every 5
	 *         seconds. This allows for slow, but acceptable loading times
	 *         MaxTime should be set to whatever total time constitutes a
	 *         failure of the system due to excessive loading time. This may
	 *         vary from the maxTime in other methods, depending on user
	 *         requirements 5 seconds set without business input, just a generic
	 *         setting right now. If the method fails to find the element after
	 *         maxTime, the test case status and results are not affected, as
	 *         this is a soft error. If this causes a failure in the test case,
	 *         it should be handled in the method calling this one. If an error
	 *         occurs, the outputText provided will be added to a generic
	 *         message to be written out to the detailed test suite log. This
	 *         allows easy error reporting and troubleshooting.
	 */
	public static boolean elementByText_ConfirmTextExists_OneOption(String textToSearchFor, Log log)
	{

		int currentTime = 0;
		int maxTime = 10;

		try
		{
			log.write("Check page for phrase: " + textToSearchFor);
			while (driver.findElements(By.xpath("//*[contains(text(),'" + textToSearchFor + "')]")).isEmpty() && currentTime < maxTime)
			{
				Thread.sleep(5000);
				currentTime += 5;
				log.write("Waiting for " + textToSearchFor + " element: " + currentTime + " seconds");
			}
			return !driver.findElements(By.xpath("//*[contains(text(),'" + textToSearchFor + "')]")).isEmpty();
		} catch (Exception ex)
		{
			log.write("Wait Failed to Execute");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
	}

	/**
	 * @param id
	 * @param expectedValue
	 * @param outputText
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.elementByID_ConfirmText allows the framework to
	 *         pass in an id and an expected text value. The method will find
	 *         the element on the page with the id provided and check if it
	 *         contains the provided text value. If the method can't find the
	 *         element, it will wait a total of maxTime (60 sec), attempting
	 *         every 5 seconds. - This allows for slow, but acceptable loading
	 *         times - maxTime should be set to whatever total time constitutes
	 *         a failure of the system due to excessive loading time. - this may
	 *         vary from the maxTime in other methods, depending on user
	 *         requirements - 60 seconds set without business input, just a
	 *         generic setting right now. If the method fails to find the
	 *         element after maxTime, the test case will be set to fail and test
	 *         case completion will be set to false. In some cases, failure at
	 *         this point is actually a completion of a test that was expected
	 *         to fail, this logic should be included in the method that called
	 *         this one. If an error occurs, the outputText provided will be
	 *         added to a generic message to be written out to the detailed test
	 *         suite log. This allows easy error reporting and troubleshooting.
	 */
	public static boolean elementByID_ConfirmText(String id, String expectedValue, String outputText, Log log)
	{
		int currentTime = 0;
		int maxTime = 60;

		try
		{
			while (driver.findElements(By.id(id)).isEmpty() && currentTime < maxTime)
			{
				// wait up to 60 secs
				Thread.sleep(5000);
				currentTime += 5;
				log.write("Waiting for " + outputText + " element: " + currentTime + " seconds");
			}
		} catch (Exception ex)
		{
			log.write("Wait Failed to Execute");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
		}
		try
		{
			if (driver.findElement(By.id(id)).getText().equalsIgnoreCase(expectedValue))
			{
				log.write(outputText + " value confirmed.");
//				System.out.println(driver.findElement(By.id(id)).getText());
			} else
			{
				log.write(outputText + " value did not match. <---------------soft error");
				log.write(driver.findElement(By.id(id)).getText() + "found.");
				return false;
			}
		} catch (Exception ex)
		{
			log.write("Unable to read " + outputText + " value. <------------------soft error");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param elementXPath
	 * @param output
	 * @return
	 */
	public static String elementByXPath_GetTextValue(String elementXPath, String output, Log log)
	{
		int currentTime = 0;
		int maxTime = 60;
		String textValue;
		try
		{
			while (driver.findElements(By.xpath(elementXPath)).isEmpty() && currentTime < maxTime)
			{
				// wait up to 60 secs
				Thread.sleep(5000);
				currentTime += 5;
				log.write("Waiting for " + output + " element: " + currentTime + " seconds");
			}
		} catch (Exception ex)
		{
			log.write("Wait Failed to Execute");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
		}
		try
		{
			textValue = driver.findElement(By.xpath(elementXPath)).getText();
		} catch (Exception ex)
		{
			log.write("Unable to find or pull the text values from " + output + ". No further tests run.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return null;
		}
		return textValue;
	}

	/*
	 * Doesn't currently work in Chrome public static boolean
	 * openURL_NewTab(String url, String confirmationText, String output) { try
	 * { // Open a new tab using Ctrl + t System.out.println(
	 * "trying to open new tab"); Actions newTab = new Actions(driver);
	 * newTab.sendKeys(Keys.CONTROL + "t").perform(); // Switch between tabs
	 * using Ctrl + \t System.out.println("trying to switch to new tab");
	 * driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "\t");
	 * } catch (Exception ex) {
	 * 
	 * } return true; }
	 */

	/**
	 * 
	 * @param fieldXPath
	 * @param input
	 * @param outputText
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.elementByXPath_EnterTextValue allows the
	 *         framework to pass in an xpath and the user's input. The method
	 *         will find the element on the page with the xpath provided and
	 *         send the user's input as key strokes to the given field. If the
	 *         method can't find the element, it will wait a total of maxTime
	 *         (60 sec), attempting every 5 seconds. - This allows for slow, but
	 *         acceptable loading times - maxTime should be set to whatever
	 *         total time constitutes a failure of the system due to excessive
	 *         loading time. - this may vary from the maxTime in other methods,
	 *         depending on user requirements - 60 seconds set without business
	 *         input, just a generic setting right now. If the method fails to
	 *         find the element after maxTime or fails to send the input
	 *         successfully, the test case will be set to fail and test case
	 *         completion will be set to false. In some cases, failure at this
	 *         point is actually a completion of a test that was expected to
	 *         fail, this logic should be included in the method that called
	 *         this one. If an error occurs, the outputText provided will be
	 *         added to a generic message to be written out to the detailed test
	 *         suite log. This allows easy error reporting and troubleshooting.
	 */
	/*
	 * public static boolean elementByXPath_EnterTextValue(String fieldXPath,
	 * String input, String outputText) { int currentTime = 0; int maxTime = 60;
	 * 
	 * while (driver.findElements(By.xpath(fieldXPath)).isEmpty() && currentTime
	 * < maxTime) { // wait up to 60 secs for username field
	 * driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	 * currentTime += 5; FileWriterUtility.safeWriter("\tWaiting to find " +
	 * outputText + " element: " + currentTime + " seconds"); } try { // Enter
	 * user input into selected field
	 * driver.findElement(By.xpath(fieldXPath)).sendKeys(input);
	 * FileWriterUtility.safeWriter("\t" + outputText +
	 * " entered into login page."); } catch (Exception ex) {
	 * FileWriterUtility.safeWriter("\tUnable to enter " + outputText +
	 * " into login page. No further tests run.");
	 * TestCase.setActualResults(false); TestCase.setTestCaseCompleted(false);
	 * System.out.println(ex.getMessage()); return false; } return true; }
	 */

	/**
	 * 
	 * @param urlPartialText
	 * @param outputText
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.urlByPartialText allows the framework to pass in
	 *         a partial url. The method will check if the partial url provided
	 *         is included in the current page's url. If the method can't find
	 *         the url, it will wait a total of maxTime (60 sec), attempting
	 *         every 5 seconds. - This allows for slow, but acceptable loading
	 *         times - maxTime should be set to whatever total time constitutes
	 *         a failure of the system due to excessive loading time. - this may
	 *         vary from the maxTime in other methods, depending on user
	 *         requirements - 60 seconds set without business input, just a
	 *         generic setting right now. If the method fails to find the
	 *         element after maxTime, the test case will be set to fail and test
	 *         case completion will be set to false. In some cases, failure at
	 *         this point is actually a completion of a test that was expected
	 *         to fail, this logic should be included in the method that called
	 *         this one. If an error occurs, the outputText provided will be
	 *         added to a generic message to be written out to the detailed test
	 *         suite log. This allows easy error reporting and troubleshooting.
	 */
	/*
	 * public static boolean urlByPartialText(String urlPartialText, String
	 * outputText) { int currentTime = 0; int maxTime = 60;
	 * 
	 * try {
	 * 
	 * while (!driver.getCurrentUrl().contains(urlPartialText) && currentTime <
	 * maxTime) { // wait up to 60 secs for page to finish loading
	 * driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	 * currentTime += 5; FileWriterUtility.safeWriter("\tWaiting for " +
	 * outputText + " : " + currentTime + " seconds"); }
	 * FileWriterUtility.safeWriter("\tCurrent URL: " + driver.getCurrentUrl());
	 * return driver.getCurrentUrl().contains(urlPartialText); } catch
	 * (Exception ex) { FileWriterUtility.safeWriter(
	 * "\tUnable to read url. No further tests run.");
	 * TestCase.setActualResults(false); TestCase.setTestCaseCompleted(false);
	 * System.out.println(ex.getMessage()); return false; } }
	 */

	/*
	 * public static boolean PageByElementID(String fieldID, String outputText)
	 * { int currentTime = 0; int maxTime = 60;
	 * 
	 * while((driver.findElements(By.id("tempStandaloneGUSAID"))).size() == 0 &&
	 * currentTime < maxTime) { // wait up to 60 secs for page to finish loading
	 * driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	 * currentTime += 5; ONBFileWriter.SafeWriter("\tWaiting to confirm " +
	 * outputText + " : " + currentTime +" seconds"); } return
	 * !((driver.findElements(By.id(fieldID))).size() == 0); }
	 */

	/* tested */
	/**
	 * 
	 * @param iFrameXPath
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.switchToIFrame_ByXPath allows the framework to
	 *         pass in an xpath. The method will find the IFrame identified by
	 *         the xpath provided and repoint the driver object to that IFrame.
	 *         If the method fails to find the IFrame, the test case will be set
	 *         to fail and test case completion will be set to false. In some
	 *         cases, failure at this point is actually a completion of a test
	 *         that was expected to fail, this logic should be included in the
	 *         method that called this one. If an error occurs, the outputText
	 *         provided will be added to a generic message to be written out to
	 *         the detailed test suite log. This allows easy error reporting and
	 *         troubleshooting.
	 */

	public static boolean switchToIFrame_ByXPath(String iFrameXPath, Log log)
	{
		try
		{
			driver.switchTo().frame(driver.findElement(By.xpath(iFrameXPath)));
			log.write("Switching to IFrame");
		} catch (Exception ex)
		{
			log.write("Unable to switch to IFrame. No further tests run.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
		return true;
	}

	public static boolean elementByXPath_EnterTextValue(String elementXPath, String input, String outputText, Log log)
	{

		int currentTime = 0;
		int maxTime = 60;
		try
		{
			while (driver.findElements(By.xpath(elementXPath)).isEmpty() && currentTime < maxTime)
			{
				// wait up to 60 secs for field
				Thread.sleep(5000);
				currentTime += 5;
				log.write("Waiting to find " + outputText + " element: " + currentTime + " seconds");
			}
		} catch (Exception ex)
		{
			log.write("Wait Failed to Execute");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
		}

		try
		{
			// Enter user input into selected field
			driver.findElement(By.xpath(elementXPath)).sendKeys(input);
			log.write(outputText + " entered into page.");
		} catch (Exception ex)
		{
			log.write("Unable to enter " + outputText + " into page. No further tests run.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
		return true;
	}

	public static String getURL()
	{
		return driver.getCurrentUrl();
	}
/*
	public static boolean openURL_ConfirmByURL(String url, Log log)
	{
		// Open url in Mozzila Browser
		try
		{
			// Open url in Mozzila Browser
			Thread.sleep(5000);
			driver.get(url);
			log.write(url + " opened in browser.");
			// tabList.add(0, driver.getWindowHandle());
		} catch (Exception ex)
		{
			log.write("Something may have gone wrong loading url: " + url + ". No further tests run. Please see log in console.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
		try
		{
			Thread.sleep(10000);
			log.write("Open URL test case result: " + driver.getCurrentUrl().equalsIgnoreCase(url));
			return driver.getCurrentUrl().equalsIgnoreCase(url);
		} catch (Exception ex)
		{
			log.write("Something may have gone wrong loading url: " + url + ". No further tests run. Please see log in console.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
			return false;
		}
	}
*/
	/* tested */
	/**
	 * 
	 * @return boolean - completed without error - True; error occurred - False
	 *         SeleniumUtility.alertAccept() will redirect the driver object to
	 *         the pop-up alert and accept it. * If the method fails to do this
	 *         the test case will be set to fail and test case completion will
	 *         be set to false. In some cases, failure at this point is actually
	 *         a completion of a test that was expected to fail, this logic
	 *         should be included in the method that called this one. If an
	 *         error occurs, the outputText provided will be added to a generic
	 *         message to be written out to the detailed test suite log. This
	 *         allows easy error reporting and troubleshooting.
	 */
	/*
	 * public static boolean alertAccept() { try {
	 * driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); Alert
	 * alert = driver.switchTo().alert(); alert.accept(); } catch (Exception ex)
	 * { FileWriterUtility.safeWriter(
	 * "\tUnable to switch to Alert. No further tests run.");
	 * TestCase.setActualResults(false); TestCase.setTestCaseCompleted(false);
	 * System.out.println(ex.getMessage()); return false; } return true; }
	 */
	/*
	 * public static void tempTest() { // confirm building specifics page
	 * driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	 * driver.switchTo().defaultContent();
	 * driver.switchTo().frame(driver.findElement(By.id(
	 * "testbpmportal:9443/teamworks/fauxRedirect.lsw"))); //
	 * testbpmportal:9443/teamworks/fauxRedirect.lsw }
	 */

	/*
	 * public static boolean SwitchToMainFrame(){ try{
	 * driver.switchTo().frame(0); } catch (Exception ex) {
	 * FileWriterUtility.SafeWriter(
	 * "\tUnable to switch to main IFrame. No further tests run.");
	 * TestCase.SetActualResults(false); TestCase.SetTestCaseCompleted(false);
	 * System.out.println(ex.getMessage()); return false; } return true; }
	 */

	/*
	 * public static void ReassignElementSearch(String tagType){
	 * System.out.println("tagType: " + tagType); List<WebElement> elements =
	 * driver.findElements(By.tagName(tagType)); System.out.println(
	 * "elements.size(): " + elements.size()); for(int i = 0; i <
	 * elements.size(); i++){
	 * 
	 * 
	 * System.out.println("id: " + elements.get(i).getAttribute("id") +
	 * " name: " + elements.get(i).getAttribute("name") + " Is Enabled: " +
	 * elements.get(1).isEnabled());
	 * 
	 * } }
	 */
}
