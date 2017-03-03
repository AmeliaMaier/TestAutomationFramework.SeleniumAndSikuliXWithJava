package doNotChange;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MasterLog
{
	List<String> suiteResultsLocationList = new ArrayList<String>();
	List<FileWriterUtility> suiteWriterList = new ArrayList<FileWriterUtility>();
	List<String> excelResultsLocationList = new ArrayList<String>();
	List<FileWriterUtility> excelResultsWriterList = new ArrayList<FileWriterUtility>();
	String directoryResultsLocation;
	FileWriterUtility directoryWriter;

	public MasterLog(String dirResultLocation)
	{
		String directoryResultsLocation = dirResultLocation + getTimeStamp() + ".txt";
		directoryWriter = new FileWriterUtility(directoryResultsLocation);
	}

	public void startNewSuite(String suiteName)
	{
		closeSuite();
		suiteResultsLocationList.add(suiteName + "Results" + getTimeStamp() + ".txt");
		suiteWriterList.add(new FileWriterUtility(suiteResultsLocationList.get(suiteResultsLocationList.size()-1)));
	}
	
	public void startNewExcel(String suiteName)
	{
		closeExcel();
		excelResultsLocationList.add(suiteName + "ExcelResults" + getTimeStamp() + ".txt");
		excelResultsWriterList.add(new FileWriterUtility(excelResultsLocationList.get(excelResultsLocationList.size()-1)));
		writeToExcelResults("Address | Expected Result | Test Result | Test Completed | Full Result | Total Time (ms)");
	}

	public void writeToDirectoryResults(String output)
	{
		directoryWriter.safeWriter(output);
	}

	public void writeToSuiteResults(String output)
	{
		suiteWriterList.get(suiteWriterList.size()-1).safeWriter(output);
	}
	
	public void writeToExcelResults(String output)
	{
		excelResultsWriterList.get(excelResultsWriterList.size()-1).safeWriter(output);
	}

	public void closeSuite()
	{
		if(!suiteWriterList.isEmpty() && !(suiteWriterList.size()==0)) suiteWriterList.get(suiteWriterList.size()-1).closeWriter();
	}
	
	public void closeExcel()
	{
		if(!excelResultsWriterList.isEmpty() && !(excelResultsWriterList.size()==0)) 
			excelResultsWriterList.get(excelResultsWriterList.size()-1).closeWriter();
	}

	public void closeLogs()
	{
		closeSuite();
		closeExcel();
		directoryWriter.closeWriter();
	}
	
	public static String getTimeStamp()
	{
		String timeStamp = new SimpleDateFormat(" MM_dd_yyy  HH_mm_ss").format(new Date());
		return timeStamp;
	}


}
