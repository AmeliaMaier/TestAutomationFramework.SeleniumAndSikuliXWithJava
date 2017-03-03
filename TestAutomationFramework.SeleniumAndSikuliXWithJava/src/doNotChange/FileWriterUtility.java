package doNotChange;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterUtility
{

	FileWriter fileWriter;
	BufferedWriter bufferedWriter;
	File currentFile;
	String fileLocation;	

	/**
	 * 
	 * @param fileLocation
	 * 
	 *            This method takes in the location of a text file and checks if
	 *            it exists yet. If it does not exist, it is created with the
	 *            name provided. The Buffered Writer is then set to append to
	 *            the current end of the file.
	 */
	public FileWriterUtility(String fLocation)
	{
		fileLocation = fLocation;
		File file = new File(fileLocation);
		try
		{
			if (!file.exists())// if file doesn't exist, create it
			{
				fileWriter = new FileWriter(file);
			}
			fileWriter = new FileWriter(file, true); // append to file from now
														// on
			// Always wrap FileWriter in BufferedWriter.
			bufferedWriter = new BufferedWriter(fileWriter);
		} catch (IOException ex)
		{
			System.out.println("Error Location: util.FileWriterUtility.setWriterLocaton; Error creating the result file. Attempted to create: " + fileLocation + " Please check the console for the logs.");
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		} catch (Exception ex)
		{
			System.out.println("Error Location: util.FileWriterUtility.setWriterLocaton; Something went very wrong while setting the writer location: " + fileLocation + " Please check the console for the logs.");
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		}
	}

	/**
	 * @param input
	 * 
	 *            This method takes in a line of text to be written out to the
	 *            current text file and adds it to the buffer. The text is not
	 *            actually written out yet.
	 */
	public void safeWriter(String input)
	{
		try
		{
			// Note that write() does not automatically
			// append a newline character.
			bufferedWriter.write(input);
			System.out.println(input);//output for trouble shooting
			bufferedWriter.write("\r\n");

		} catch (IOException ex)
		{
			System.out.println("Error Location: util.FileWriterUtility.safeWriter; Error writing to result file. Attempted to write: " + input + " Please check the console for the logs.");
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		} catch (Exception ex)
		{
			System.out.println("Error Location: util.FileWriterUtility.safeWriter; Something went very wrong while writing to the result file. Please check the console for the logs.");
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		}
	}

	/**
	 * @param fileLocation
	 * 
	 *            This method takes in the name of the file to be removed from
	 *            the buffer and closes it out. The text held in the buffer is
	 *            only written out to the file as it is being closed out.
	 */
	public void closeWriter()
	{
		try
		{
			fileWriter.flush();
			bufferedWriter.close();
		} catch (IOException ex)
		{
			System.out.println("Error Location: util.FileWriterUtility.closeWriter; Error closing the result file. Attempted to write: " + fileLocation + " Please check the console for the logs.");
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		} catch (Exception ex)
		{
			System.out.println("Error Location: util.FileWriterUtility.closeWriter; Something went very wrong while closing the browers. Please check the console for the logs.");
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		}
	}
}
