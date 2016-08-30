import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Pos;

public class InfoBox {
	public static void display(String startDate, String endDate, String name, int numAbsences, double amountPaid, double amountOwed) {
		Stage window = new Stage();
		Label heading = new Label("General Information for " + name + " from " + startDate + " to " + endDate);
		Label absenceHeading = new Label("Number of Absences");
		Label absenceInfo = new Label(name + " had " + numAbsences + " absence(s)");
		Label amountPaidHeading = new Label("Amount Paid");
		Label amountPaidInfo = new Label(name + " has paid " + amountPaid + " dollars");
		Label amountOwedHeading = new Label("Amount Owed");
		Label amountOwedInfo = new Label(name + " owes " + amountOwed + " dollars");
		VBox layout = new VBox(20);
		layout.getChildren().addAll(heading, absenceHeading, absenceInfo, amountPaidHeading, amountPaidInfo, amountOwedHeading, amountOwedInfo);
		layout.setAlignment(Pos.CENTER);
		Scene myScene = new Scene(layout);
		window.setScene(myScene);
		window.show();
	}
}