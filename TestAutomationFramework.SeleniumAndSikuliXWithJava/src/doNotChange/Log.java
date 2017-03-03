package doNotChange;

public class Log
{
	MasterLog masterLog;
	public Log(MasterLog newMasterLog){
		masterLog = newMasterLog;
	}
	public void write(String output){
		masterLog.writeToSuiteResults(output);
	}
}
