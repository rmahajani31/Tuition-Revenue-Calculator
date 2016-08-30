import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.SelectionMode;
import javafx.collections.*;
import java.util.Scanner;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Locale;

public class TuitionRevenue extends Application {

	Stage window;
	Label dateLabel;
	TextField dateInput;
	Button dateButton;
	VBox dateLayout;
	Label classLabel;
	ComboBox<String> classMenu;
	VBox classLayout;
	Label studentLabel;
	ComboBox<String> studentMenu;
	VBox studentLayout;
	Label revenueLabel;
	TextField revenueInput;
	VBox revenueLayout;
	Button addButton;
	VBox addLayout;
	Scene addScene;
	Scene dateScene;
	VBox deleteLayout;
	Button deleteButton;
	Scene deleteScene;
	Label startLabel;
	Label endLabel;
	TextField startDateInput;
	TextField endDateInput;
	VBox startLayout;
	VBox endLayout;
	HBox dateRangeLayout;
	Button clearButton;
	VBox clearLayout;
	Scene clearScene;
	Button generalInfoButton;
	VBox generalInfoLayout;
	Scene generalInfoScene;
	Label gradeLabel;
	TextField gradeInput;
	Button spreadsheetButton;
	VBox spreadsheetLayout;
	Scene spreadsheetScene;
	Date sessionDate;
	File newFile;
	FileWriter csvWriter;
	BufferedWriter myWriter;
	Button deleteSpreadsheetButton;
	VBox deleteSpreadsheetLayout;
	Scene deleteSpreadsheetScene;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Saturday Class");
		System.out.println("Starting Session...");
		window.setOnCloseRequest(e -> System.out.println("Ending Session..."));
		setToDefaultScene();
		window.show();
	}

	private MenuBar createFileMenu() {
		Menu settings = new Menu("Settings");
		MenuItem createSpreadsheet = new MenuItem("New Spreadsheet...");
		MenuItem deleteSpreadsheet = new MenuItem("Delete Spreadsheet...");
		MenuItem specifyDate = new MenuItem("Specify Date...");
		MenuItem addRecords = new MenuItem("Add Records...");
		MenuItem deleteRecords = new MenuItem("Delete Records...");
		MenuItem clearRecords = new MenuItem("Clear Records...");
		MenuItem getInfo = new MenuItem("General Info...");
		MenuItem exit = new MenuItem("Exit");
		createSpreadsheet.setOnAction(e -> setToSpreadsheetScene());
		deleteSpreadsheet.setOnAction(e -> setToDeleteSpreadsheetScene());
		specifyDate.setOnAction(e -> setToDefaultScene());
		addRecords.setOnAction(e -> {
			if (validateDate(dateInput.getText())) {
				setToAddScene();
			}
		});
		deleteRecords.setOnAction(e -> {
				setToDeleteScene();
		});
		clearRecords.setOnAction(e -> {
				setToClearScene();
		});
		getInfo.setOnAction(e -> {
				setToGeneralInfoScene();
		});
		exit.setOnAction(e -> {
			if (ExitBox.display()) {
				System.out.println("Ending Session...");
				window.close();
			}
		});
		settings.getItems().add(createSpreadsheet);
		settings.getItems().add(deleteSpreadsheet);
		settings.getItems().add(new SeparatorMenuItem());
		settings.getItems().add(specifyDate);
		settings.getItems().add(new SeparatorMenuItem());
		settings.getItems().addAll(addRecords, deleteRecords, clearRecords);
		settings.getItems().add(new SeparatorMenuItem());
		settings.getItems().add(getInfo);
		settings.getItems().add(new SeparatorMenuItem());
		settings.getItems().add(exit);
		MenuBar settingsMenu = new MenuBar();
		settingsMenu.getMenus().addAll(settings);
		return settingsMenu;
	}

	private void setToDefaultScene() {
		window.setScene(createDefaultScene());
	}

	private void setToAddScene() {
		window.setScene(createAddScene());
	}

	private void setToDeleteScene() {
		window.setScene(createDeleteScene());
	}

	private void setToClearScene() {
		window.setScene(createClearScene());
	}

	private void setToGeneralInfoScene() {
		window.setScene(createGeneralInfoScene());
	}

	private void setToSpreadsheetScene() {
		window.setScene(createSpreadsheetScene());
	}

	private void setToDeleteSpreadsheetScene() {
		window.setScene(createDeleteSpreadsheetScene());
	}

	private Scene createDefaultScene() {
		dateLayout = new VBox(10);
		dateLayout.getChildren().addAll(createFileMenu(), createDateLayout(), createSelectDateButton());
		dateScene = new Scene(dateLayout, 400, 400);
		return dateScene;
	}

	private boolean validateDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			sessionDate = formatter.parse(date);
			return true;
		} catch(ParseException e) {
			ErrorBox.display("You must enter a valid date to continue this session");
			return false;
		}
	}

	private Scene createAddScene() {
		addLayout = new VBox(70);
		addLayout.getChildren().addAll(createFileMenu(), createClassLayout(), createStudentLayout(), createRevenueLayout(), createAddButton());
		addLayout.setAlignment(Pos.CENTER);
		addLayout.setMinWidth(500);
		addScene = new Scene(addLayout);
		return addScene;
	}

	private Scene createDeleteScene() {
		deleteLayout = new VBox(70);
		deleteLayout.getChildren().addAll(createFileMenu(), createClassLayout(), createStudentLayout(), createDateRangeLayout(), createDeleteButton());
		deleteLayout.setAlignment(Pos.CENTER);
		deleteLayout.setMinWidth(500);
		deleteScene = new Scene(deleteLayout);
		return deleteScene;
	}

	private Scene createClearScene() {
		clearLayout = new VBox(70);
		clearLayout.getChildren().addAll(createFileMenu(), createClassLayout(), createStudentLayout(), createDateRangeLayout(), createClearButton());
		clearLayout.setAlignment(Pos.CENTER);
		clearLayout.setMinWidth(500);
		clearScene = new Scene(clearLayout);
		return clearScene;
	}

	private Scene createGeneralInfoScene() {
		generalInfoLayout = new VBox(70);
		generalInfoLayout.getChildren().addAll(createFileMenu(), createClassLayout(), createStudentLayout(), createDateRangeLayout(), createGeneralInfoButton());
		generalInfoLayout.setAlignment(Pos.CENTER);
		generalInfoLayout.setMinWidth(500);
		generalInfoScene = new Scene(generalInfoLayout);
		return generalInfoScene;
	}

	private Scene createSpreadsheetScene() {
		spreadsheetLayout = new VBox(70);
		spreadsheetLayout.getChildren().addAll(createFileMenu(), createEditableGradeLayout(), createSpreadsheetButton());
		spreadsheetLayout.setAlignment(Pos.CENTER);
		spreadsheetLayout.setMinWidth(500);
		spreadsheetScene = new Scene(spreadsheetLayout);
		return spreadsheetScene;
	}

	private Scene createDeleteSpreadsheetScene() {
		deleteSpreadsheetLayout = new VBox(70);
		deleteSpreadsheetLayout.getChildren().addAll(createFileMenu(), createEditableGradeLayout(), createDeleteSpreadsheetButton());
		deleteSpreadsheetLayout.setAlignment(Pos.CENTER);
		deleteSpreadsheetLayout.setMinWidth(500);
		deleteSpreadsheetScene = new Scene(deleteSpreadsheetLayout);
		return deleteSpreadsheetScene;
	}

	private ObservableList<String> readNames(String grade) throws FileNotFoundException {
		ObservableList<String> nameList = FXCollections.observableArrayList();
		Scanner read = new Scanner(new File("names_" + grade + ".txt"));
		while (read.hasNext()) {
			nameList.add(read.next());
		}
		return nameList;
	}

	private ObservableList<String> readClasses() throws FileNotFoundException {
		ObservableList<String> classList = FXCollections.observableArrayList();
		Scanner read = new Scanner(new File("classes.txt"));
		while (read.hasNext()) {
			classList.add(read.nextLine());
		}
		return classList;
	}

	private void changeStudentDropDown() {
		if(classMenu.getValue() != null) {
				String grade = classMenu.getValue();
				clearItems(studentMenu);
				try {
					studentMenu.getItems().addAll(readNames(grade));
				} catch(FileNotFoundException e) {
					System.out.println("names file was not found...");
				}
				studentMenu.setPromptText("");
				studentMenu.setEditable(true);
			}
	}

	private void clearItems(ComboBox<String> menu) {
		menu.getItems().removeAll(menu.getItems());
	}

	private VBox createClassLayout() {
		classLabel = new Label("Class");
		classMenu = new ComboBox<>();
		try {
			classMenu.getItems().addAll(readClasses());
		} catch(FileNotFoundException e) {
			System.out.println("Error could not find file classes.txt...");
		}
		classMenu.setOnAction(e -> changeStudentDropDown());
		classLayout = new VBox(10);
		classLayout.getChildren().addAll(classLabel, classMenu);
		classLayout.setAlignment(Pos.CENTER);
		return classLayout;
	}

	private VBox createStudentLayout() {
		studentLabel = new Label("Student");
		studentMenu = new ComboBox<>();
		studentMenu.setPromptText("First Pick A Class");
		studentLayout = new VBox(10);
		studentLayout.getChildren().addAll(studentLabel, studentMenu);
		studentLayout.setAlignment(Pos.CENTER);
		return studentLayout;
	}

	private VBox createRevenueLayout() {
		revenueLabel = new Label("Revenue");
		revenueInput = new TextField();
		revenueInput.setText("50");
		revenueLayout = new VBox(10);
		revenueLayout.getChildren().addAll(revenueLabel, revenueInput);
		revenueLayout.setAlignment(Pos.CENTER);
		return revenueLayout;
	}

	private VBox createDateLayout() {
		dateLabel = new Label("Specify the Date for this Session");
		dateInput = new TextField();
		dateInput.setPromptText("Enter the Date in the format MM/dd/yyyy");
		dateInput.setFocusTraversable(false);
		VBox dateDesign = new VBox();
		dateDesign.getChildren().addAll(dateLabel, dateInput);
		return dateDesign;
	}

	private Button createSelectDateButton() {
		dateButton = new Button("Select this Date");
		dateButton.setOnAction(e -> {
			if (validateDate(dateInput.getText())) {
				System.out.println("Successfully specified the date for this session as " + sessionDate.toString());
				setToAddScene();
			}
		});
		return dateButton;
	}

	private Button createAddButton() {
		addButton = new Button("Add");
		addButton.setOnAction(e -> {
			if(validateAdd()) {
				if(addToSpreadsheet()) {
					System.out.println("The record for " + studentMenu.getValue() + " being added to the spreadsheet...");
				}
			}
		});
		return addButton;
	}

	private boolean validateAdd() {
		boolean answer = true;
		if (classMenu.getValue() != null && studentMenu.getValue() != null) {
			if (!revenueInput.getText().equalsIgnoreCase("Abs")) {
				try {
					Double.parseDouble(revenueInput.getText());
					answer = true;
				} catch(NumberFormatException e) {
					ErrorBox.display("Must type a valid revenue to add a record");
					answer = false;
				} 
			}
		}
		return answer;
	}

	private HBox createDateRangeLayout() {
		startLabel = new Label("Enter the starting date");
		endLabel = new Label("Enter the ending date");
		startDateInput = new TextField();
		startDateInput.setPromptText("Date format MM/dd/yyyy");
		endDateInput = new TextField();
		endDateInput.setPromptText("Date format MM/dd/yyyy");
		startLayout = new VBox(10);
		startLayout.getChildren().addAll(startLabel, startDateInput);
		endLayout = new VBox(10);
		endLayout.getChildren().addAll(endLabel, endDateInput);
		dateRangeLayout = new HBox(30);
		dateRangeLayout.getChildren().addAll(startLayout, endLayout);
		dateRangeLayout.setAlignment(Pos.CENTER);
		return dateRangeLayout;
	}

	private Button createDeleteButton() {
		deleteButton = new Button("Delete");
		deleteButton.setOnAction(e -> {
			if (validateDate(startDateInput.getText()) && validateDate(endDateInput.getText()) && validateFields()) {
				if (deleteFromSpreadsheet()) {
					System.out.println("The record(s) for " + studentMenu.getValue() + " from start date " + startDateInput.getText() + " to end date " + endDateInput.getText() + " being removed from the spreadsheet...");
				}
			}
		});
		return deleteButton;
	}

	private boolean validateFields() {
		return classMenu.getValue() != null && studentMenu.getValue() != null;
	}

	private Button createClearButton() {
		clearButton = new Button("Clear");
		clearButton.setOnAction(e -> {
			if (validateDate(startDateInput.getText()) && validateDate(endDateInput.getText()) && validateFields()) {
				if (clearFromSpreadsheet()) {
					System.out.println("The record(s) for " + studentMenu.getValue() + " from start date " + startDateInput.getText() + " to end date " + endDateInput.getText() + " being cleared from the spreadsheet...");
				}
			}
		});
		return clearButton;
	}

	private Button createGeneralInfoButton() {
		generalInfoButton = new Button("Get Info");
		generalInfoButton.setOnAction(e -> {
			if (validateDate(startDateInput.getText()) && validateDate(endDateInput.getText()) && validateFields()) {
				if (getGeneralInfo()) {
					System.out.println("The general information for " + studentMenu.getValue() + " from start date " + startDateInput.getText() + " to end date " + endDateInput.getText() + " being retrieved from the spreadsheet...");
				}
			}
		});
		return generalInfoButton;
	}

	private VBox createEditableGradeLayout() {
		gradeLabel = new Label("Enter a grade as a valid number");
		gradeInput = new TextField();
		gradeInput.setPromptText("Enter a grade");
		gradeInput.setFocusTraversable(false);
		VBox gradeLayout = new VBox(10);
		gradeLayout.getChildren().addAll(gradeLabel, gradeInput);
		return gradeLayout;
	}

	private Button createSpreadsheetButton() {
		spreadsheetButton = new Button("Create Spreadsheet");
		spreadsheetButton.setOnAction(e -> {
				SpreadSheet.createSpreadsheet(gradeInput.getText());
		});
		return spreadsheetButton;
	}

	private Button createDeleteSpreadsheetButton() {
		deleteSpreadsheetButton = new Button("Delete Spreadsheet");
		deleteSpreadsheetButton.setOnAction(e -> {
				SpreadSheet.deleteSpreadsheet(gradeInput.getText());
		});
		return deleteSpreadsheetButton;
	}

	private boolean addToSpreadsheet() {
		String classValue = classMenu.getValue();
		newFile = new File("grade_" + classValue + ".csv");
		if (validateFileExists(newFile)) {
			try {
				String name = studentMenu.getValue();
				String date = sessionDate.toString();
				String revenue = revenueInput.getText();
				csvWriter = new FileWriter(newFile, true);
				myWriter = new BufferedWriter(csvWriter);
				myWriter.write(name + "," + date + "," + revenue + "\n");
				myWriter.close();
				return true;
			} catch(IOException e) {
				ErrorBox.display("An IOException has been thrown for one of the following reasons if the named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason");
				return false;
			}
		}
		return false;
	}

	private boolean validateFileExists(File newFile) {
		try {
			if (!newFile.exists()) {
				throw(new Exception());
			}
			return true;
		} catch(Exception e) {
			ErrorBox.display("Please create a spreadsheet from the settings menu corressponding to this grade before continuing");
			return false;
		}
	}

	private boolean deleteFromSpreadsheet() {
		String classValue = classMenu.getValue();
		File oldFile = new File("grade_" + classValue + ".csv");
		Scanner scan = new Scanner("");
		try {
			scan = new Scanner(oldFile);
			newFile = new File("grade_" + classValue + "tmp.csv");
			csvWriter = new FileWriter(newFile, true);
			myWriter = new BufferedWriter(csvWriter);
		} catch(FileNotFoundException e) {
			ErrorBox.display("Please create a spreadsheet from the settings menu corressponding to this grade before continuing");
			return false;
		} catch(IOException e) {
			ErrorBox.display("An IOException has been thrown for one of the following reasons if the named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason");
			return false;
		}
		while (scan.hasNext()) {
			String scanLine = scan.nextLine();
			if(!validateLineField(scanLine)) {
				try {
					myWriter.write(scanLine + "\n");
				} catch(IOException e) {
					ErrorBox.display("An IOException has been thrown for one of the following reasons if the named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason");
					return false;
				}
			}
		}
		try {
			myWriter.close();
		} catch(IOException e) {
			ErrorBox.display("An IOException has been thrown for one of the following reasons if the named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason");				return false;
		}
		oldFile.delete();
		newFile.renameTo(new File("grade_" + classValue + ".csv"));
		return true;
	}

	private boolean clearFromSpreadsheet() {
		String classValue = classMenu.getValue();
		File oldFile = new File("grade_" + classValue + ".csv");
		Scanner scan = new Scanner("");
		try {
			scan = new Scanner(oldFile);
			newFile = new File("grade_" + classValue + "tmp.csv");
			csvWriter = new FileWriter(newFile, true);
			myWriter = new BufferedWriter(csvWriter);
		} catch(FileNotFoundException e) {
			ErrorBox.display("Please create a spreadsheet from the settings menu corressponding to this grade before continuing");
			return false;
		} catch(IOException e) {
			ErrorBox.display("An IOException has been thrown for one of the following reasons if the named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason");
			return false;
		}
		while (scan.hasNext()) {
			String scanLine = scan.nextLine();
			if(!validateLineField(scanLine)) {
				try {
					myWriter.write(scanLine + "\n");
				} catch(IOException e) {
					ErrorBox.display("An IOException has been thrown for one of the following reasons if the named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason");
					return false;
				}
			} else {
				try {
					String mySubstring = scanLine.substring(0, scanLine.lastIndexOf(","));
					String revenue = scanLine.substring(scanLine.lastIndexOf(",") + 1);
					myWriter.write(mySubstring + ",0" + revenue + "\n");
				} catch(IOException e) {
					ErrorBox.display("An IOException has been thrown for one of the following reasons if the named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason");
					return false;
				}
			}
		}
		try {
			myWriter.close();
		} catch(IOException e) {
			ErrorBox.display("An IOException has been thrown for one of the following reasons if the named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason");				return false;
		}
		oldFile.delete();
		newFile.renameTo(new File("grade_" + classValue + ".csv"));
		return true;
	}

	private boolean getGeneralInfo() {
		String classValue = classMenu.getValue();
		newFile = new File("grade_" + classValue + ".csv");
		Scanner scan = new Scanner("");
		try {
			scan = new Scanner(newFile);

		} catch (FileNotFoundException e) {
			ErrorBox.display("Please create a spreadsheet from the settings menu corressponding to this grade before continuing");
			return false;
		}
		int numAbsences = 0;
		double amountPaid = 0;
		double amountOwed = 0;
		while (scan.hasNext()) {
			String scanLine = scan.nextLine();
			String revenue = scanLine.substring(scanLine.lastIndexOf(",") + 1);
			if (validateLineField(scanLine)) {
				if (revenue.equalsIgnoreCase("Abs")) {
					numAbsences += 1;
				} else if (revenue.startsWith("0")) {
					amountPaid += Double.parseDouble(revenue.substring(revenue.indexOf("0") + 1));
				} else {
					amountOwed += Double.parseDouble(revenue);
				}
			}
		}
		InfoBox.display(startDateInput.getText(), endDateInput.getText(), studentMenu.getValue(), numAbsences, amountPaid, amountOwed);	
		return true;
	}

	private boolean validateLineField(String line) {
		if (line.equalsIgnoreCase("Name,Date,Amount")) {
			return false;
		}
		else {
			String[] arr = line.split(",");
			String name = arr[0];
			Date date = convertSpreadsheetStringToDate(arr[1]);
			return validateDateField(date) && validateStudentField(name);
		}
	}

	private Date convertSpreadsheetStringToDate(String myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
		Date date = new Date();
		try {
			date = formatter.parse(myDate);
		} catch(ParseException e) {
			ErrorBox.display("The Date in the input couldn't be formatted properly");
		}
		return date;
	}

	private Date convertStringToDate(String myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		try {
			date = formatter.parse(myDate);
		} catch(ParseException e) {
			ErrorBox.display("The Date in the input couldn't be formatted properly");
		}
		return date;
	}

	private boolean validateDateField(Date date) {
		Date startDate = convertStringToDate(startDateInput.getText());
		Date endDate = convertStringToDate(endDateInput.getText());
		return (date.equals(startDate) || date.after(startDate)) && (date.equals(endDate) || date.before(endDate));
	}

	private boolean validateStudentField(String name) {
		return name.equals(studentMenu.getValue());
	}
}