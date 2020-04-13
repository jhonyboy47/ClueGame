package javaFX;



import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class WelcomeDialog {
	public static void displayWelcomeDialog(String humanPlayerName) {
		
		Stage window = new Stage();
		
		
		Button button = new Button("Hello");
	
		Label actionText = new Label("Test");
        ImageView graphic = new ImageView(new Image("assets/icons/editor_action_info_icon.png"));
        
        actionText.setGraphic(graphic);
		
		// DialogPane dialogPane = new DialogPane();
		// dialogPane.setStyle("-fx-font-size: 30;");
		
		// dialogPane.getChildren().add(button);
		
		
		Scene scene = new Scene(actionText, 400, 400);
		
		window.setScene(scene);
		window.showAndWait();
		
		
	}
	
}
