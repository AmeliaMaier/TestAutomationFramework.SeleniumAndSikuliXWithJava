package doNotChange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtility
{

	static String fileLocation;
	static int currentRow;
	static FileInputStream inputStream;
	static Workbook workbook;
	static org.apache.poi.ss.usermodel.Sheet firstSheet;
	static org.apache.poi.ss.usermodel.Sheet outputSheet;

	public static void setFileLocation(String fLocation)
	{
		currentRow = 1;
		fileLocation = fLocation;
		try
		{
			inputStream = new FileInputStream(new File(fileLocation));
			workbook = new XSSFWorkbook(inputStream);
			firstSheet = workbook.getSheetAt(0);
		} catch (FileNotFoundException ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		} catch (Exception ex)
		{
//			FileWriterUtility.safeWriter("Unable to set the Excel File location.");
//			FileWriterUtility.safeWriter(ex.getMessage());
			ex.printStackTrace(System.out);
		}
	}

	public static int getNumberOfRows()
	{
		return firstSheet.getLastRowNum();
	}

	/*
	 * public static void setRow(int rowNumber){ currentRow = rowNumber; }
	 */

	public static List<String> getRowData()
	{
		List<String> rawRowData = new ArrayList<String>();
		Row row = firstSheet.getRow(currentRow);
		for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext();)
		{
			Cell cell = cellIterator.next();
			if (cell.getCellType() == 1)
			{
				rawRowData.add(cell.getStringCellValue());
			} else if (cell.getCellType() == 0)
			{
				rawRowData.add(Double.toString(cell.getNumericCellValue()));
			} else
			{
//				FileWriterUtility.safeWriter("Row: " + currentRow + ", Column: " + cell.getColumnIndex() + " value type is not recognized.");
			}
		}
		currentRow++;
		return rawRowData;
	}

}
