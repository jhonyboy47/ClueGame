// Authors: Michael Crews and Jhon Malagon

package javaFX;

import java.util.ArrayList;
import java.util.List;

import clueGame.Board;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotResult;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;


public class ClueGameStart extends Application{
	
	static Board board;
	
	public static void  main(String[] args) {
		board = Board.getInstance();
		// Board board = Board.getInstance();
		board.setConfigFiles("ClueGameBoardCSV.csv", "ClueRooms.csv");	
		board.setPlayersFile("ClueGamePlayers.csv");
		board.setWeaponsFile("ClueGameWeapons.csv");
		board.initialize();
		launch(args);
	}
	
	@Override
	public void start(Stage window) throws Exception {
		// Set the title of the window to "Clue Game"
		window.setTitle("Clue Game");
		
		// Close the program properly if the user tries to close the program
		window.setOnCloseRequest(e ->{
			e.consume();
			window.close();
		});
		

	    BorderPane pane = new BorderPane();
	    
	    BorderPane controlGUIPane = ControlGUI.drawControlGUI();
	    
	    
	    
	    
	    board.drawBoard(pane);
	    
	    BorderPane pane2 = board.drawRoomNames(pane);
	   
	    
	    pane2.setBottom(controlGUIPane);
	    
	    // board.drawRoomNames(pane);
	   
	    
	    // Create a scene and pass in pane and the correct dimensions for the scene 
		Scene scene = new Scene(pane2, 1200, 900);
		
		
		// Set the window to the scene we just created
		window.setScene(scene);
		
		
		window.setMinWidth(1200);
		window.setMinHeight(900);
		
		
		// Show the window to the user
		window.show();
	}
}
	