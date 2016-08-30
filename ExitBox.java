import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.geometry.Pos;

public class ExitBox {
	static boolean shouldClose;

	public static boolean display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		Label exitLabel = new Label("Are you sure you want to exit the application");
		Button exitButtonYes = new Button("Yes");
		Button exitButtonNo = new Button("No");
		exitButtonYes.setOnAction(e -> {
			shouldClose = true;
			window.close();
		});
		exitButtonNo.setOnAction(e -> {
			shouldClose = false;
			window.close();
		});
		VBox exitLayout = new VBox(20);
		HBox buttonLayout = new HBox(20);
		buttonLayout.getChildren().addAll(exitButtonYes, exitButtonNo);
		exitLayout.getChildren().addAll(exitLabel, buttonLayout);
		exitLayout.setAlignment(Pos.CENTER);
		buttonLayout.setAlignment(Pos.CENTER);
		Scene exitScene = new Scene(exitLayout, 300, 300);
		window.setScene(exitScene);
		window.showAndWait();
		return shouldClose;
	}
}