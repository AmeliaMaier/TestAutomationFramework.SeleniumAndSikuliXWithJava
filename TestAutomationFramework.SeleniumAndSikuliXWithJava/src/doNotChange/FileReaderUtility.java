package doNotChange;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUtility {
	
	/** 	 
	 * @param txtFileDirectoryLocation
	 * @return
	 * ONBMain.getFileDirectoryList takes in a string that represents the name of the Directory File.
	 * The Directory file is the text file that contains all the names of the Test Suites to be run.
	 * This method opens the Directory text file, ignoring any line that starts with "//", and saves each line into an array of Test Suites.
	 * The method returns the array of Test Suite names after removing all blank spaces from the beginning or end of each name.
	 * This method is currently separate from the getTestsToRun method to make code easier to read.
	 */
	public static List<String> getListOfTestSuites(String txtFileDirectoryLocation) {
		List<String> txtFileDirectoryList = new ArrayList<String>();
		String line;
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(txtFileDirectoryLocation);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				txtFileDirectoryList.add(line);
			}
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Error Location: util.FileReaderUtility.getFileDirectoryList; Unable to open file: " + txtFileDirectoryLocation + ". Please check the logs in the console.");
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		} catch (IOException ex) {
			System.out.println("Error Location: util.FileReaderUtility.getFileDirectoryList; Error reading file: " + txtFileDirectoryLocation + ". Please check the logs in the console.");
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		} catch (Exception ex){
			System.out.println("Error Location: util.FileReaderUtility.getFileDirectoryList; Something went very wrong while reading the file at: " + txtFileDirectoryLocation + ". Please check the logs in the console.");
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		}		
		return removeComments(txtFileDirectoryList);
	}

	/**
	 * 
	 * @param txtOfTestsToRun
	 * @return
	 * ONBMain.getTestsToRun takes in a string that represents the name of a Test Suite file.
	 * Each Test Suite file is the text file that contains all the names of the tests to be run.
	 * This method opens the Test Suite text file, ignoring any line that starts with "//", and saves each line into an array of tests.
	 * The method returns the array of test names after removing all blank spaces from the beginning or end of each name.
	 * Most test names will include variables to be used while running the test. Ex: Login, Pass, PMTEST, 123456 means the test Login is expected to pass if it uses username PMTEST with passwork 123456.
	 * This line will be parsed at a later stage in the process.
	 * This method is currently separate from the getFileDirectlyList method to make code easier to read.
	 */
	public static List<String> getListOfTestsToRun(String txtOfTestsToRun, Log log) {
		List<String> testsToRun = new ArrayList<String>();
		String line;
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(txtOfTestsToRun);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				testsToRun.add(line);
			}
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			log.write("Error Location: util.FileReaderUtility.getTestsToRun; Unable to open file: " + txtOfTestsToRun + ". Please check the logs in the console.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
		} catch (IOException ex) {
			log.write("Error Location: util.FileReaderUtility.getTestsToRun; Error reading file: " + txtOfTestsToRun + ". Please check the logs in the console.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
		}catch (Exception ex){
			log.write("Error Location: util.FileReaderUtility.getTestsToRun; Something went very wrong while reading the file at: " + txtOfTestsToRun + ". Please check the logs.");
			log.write(ex.getMessage());
			ex.printStackTrace(System.out);
		}
		return removeComments(testsToRun);
	}

	/**
	 * @param rawInput
	 * @return
	 * ONBMain.removeComments is used by getTestToRun and getDirectoryList in order to remove any line that begins with "//" from the text files they are reading in.
	 * This method allows the user to add comments to the text files that control the automation framework.
	 * Any line that beings with "//" is assumed to be a comment and isn't read as a Test Suite or test case.
	 */
	public static List<String> removeComments(List<String> rawInput)
	{
		List<String> cleanedInput = new ArrayList<String>();
		for(int location = 0; location < rawInput.size(); location ++)
		{
			if(!rawInput.get(location).startsWith("//"))
			{
				cleanedInput.add((rawInput.get(location).trim()));
			}
		}
		return cleanedInput;
	}

	/**
	 * @param rawInput
	 * @return
	 * ONBMain.splitLine is used by main to split a test name from the variables that follow it, so the Automation Framework will be able to select the correct test and run it in the way requested by the tester.
	 * Ex: Login, Pass, PMTEST, 123456 means the test Login is expected to pass if it uses username PMTEST with passwork 123456.
	 * All tests will be read from the Test Suite file as a string with at least Test Name, Expected Result.
	 * This method takes that string and separates it into an array of strings. 
	 *  - Location 0 will always be the test name. 
	 *  - Location 1 will always be the expected results.
	 *  - All other locations will always be based on the order of use for the variables needed.
	 */
	public static List<String> splitLine(String rawInput){
		List<String> cleanSplitInput = new ArrayList<String>();
		String[] splitInput;
		splitInput = rawInput.split(",");
		for(int location = 0; location < splitInput.length; location ++){
			cleanSplitInput.add(splitInput[location].trim());
		}
		return cleanSplitInput;
	}
}
