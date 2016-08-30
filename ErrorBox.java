import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.geometry.Pos;

public class ErrorBox {
	public static void display(String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		Label errorLabel = new Label(message);
		Button errorButton = new Button("Ok");
		errorButton.setOnAction(e -> window.close());
		StackPane errorLayout = new StackPane();
		errorLayout.getChildren().addAll(errorLabel, errorButton);
		errorLayout.setAlignment(errorButton, Pos.BOTTOM_CENTER);
		Scene errorScene = new Scene(errorLayout, 300, 300);
		window.setScene(errorScene);
		window.showAndWait();
	}
}