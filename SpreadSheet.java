import java.io.*;

public class SpreadSheet {

	public static void createSpreadsheet(String grade) {
		File csvFile = new File("grade_" + grade + ".csv");
		if (!csvFile.exists()) {
			try {
				FileWriter csvWriter = new FileWriter(csvFile);
				BufferedWriter myWriter = new BufferedWriter(csvWriter);
				myWriter.write("Name,Date,Amount\n");
				myWriter.close();
			} catch(IOException e) {
				ErrorBox.display("An IOException has been thrown for one of the following reasons if the named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason");
			}
			System.out.println("Creating a Spreadsheet for grade " + grade + "...");
		} else {
			ErrorBox.display("Cannot create a SpreadSheet that already exists");
		}
	}

	public static void deleteSpreadsheet(String grade) {
		File csvFile = new File("grade_" + grade + ".csv");
		if (csvFile.exists()) {
			System.out.println("Deleting a Spreadsheet for grade " + grade + "...");
			csvFile.delete();
		} else {
			ErrorBox.display("There is no SpreadSheet with this name");
		}
	}
}