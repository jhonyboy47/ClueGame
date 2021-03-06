// Authors: Michael Crews and Jhon Malagon

package javaFX;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;
import clueGame.Suggestion;
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
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;


public class ControlGUI{
	
	
	private static Label showGuess = new Label();
	
	// BorderPane to hold all labels and buttons
	private static BorderPane pane = new BorderPane();
	
	private static Label showGuessResult = new Label();
	private static Button nextPlayerButton = new Button("Next Player");
	
	
	public static BorderPane drawControlGUI(Board board) {

		Label showDieRoll = new Label("");
		
		// Vbox for holding showing whose turn it is
		VBox whoseTurnVbox = new VBox(10);
		
		// set necessary attributes to whoseTurnVbox
		HBox.setHgrow(whoseTurnVbox, Priority.ALWAYS);
		whoseTurnVbox.setMaxWidth(Double.MAX_VALUE);
		whoseTurnVbox.setAlignment(Pos.TOP_CENTER);
		whoseTurnVbox.setMaxWidth(Double.MAX_VALUE);
		
	
		Label whoseTurn = new Label("Whose turn?");
		whoseTurn.setFont(new Font(30));
	
		Label showWhoseTurn = new Label();
	    showWhoseTurn.setMinSize(200, 35);
		showWhoseTurn.setFont(new Font(20));
		showWhoseTurn.setStyle("-fx-border-color: blue");
		showWhoseTurn.setAlignment(Pos.CENTER);
		
		// Add the labels to whoseTurnVbox in order
		whoseTurnVbox.getChildren().addAll(whoseTurn, showWhoseTurn);
		
		// HBox to hold next player and make accusation buttons
		HBox playerButtonHbox = new HBox();
		
		
		nextPlayerButton.setOnAction(e ->{
			
			// Resest the guess/guess result labels
			showGuess.setText("");
			showGuessResult.setText("");
			
			
			Player nextPlayer = board.getNextPlayer();
			
			//handles situation where player has not moved from the position
			// human player was at the start of players turn
			if(nextPlayer instanceof HumanPlayer) {
				if(((HumanPlayer) nextPlayer).getJustMoved() == false) {
					return;
				}
			}
			
			board.unHighlightTargets();
			
			board.setNextDieRoll();
			showDieRoll.setText(board.getDieRollString());
			
			board.setNextPlayer();
			
			nextPlayer = board.getNextPlayer();
			showWhoseTurn.setText(nextPlayer.getPlayerName());
			
			//Movements based off of computer or human player
			if ( nextPlayer instanceof HumanPlayer) {
				board.highlightTargetsIfHuman();
			}
			
			else if (nextPlayer instanceof ComputerPlayer) {
				// If statement for it the ComputerPlayers think they should make an accusation
				if(ComputerPlayer.getMakeAccusation()) {
					// Get the accusation that the ComputerPlayers think is correct
					Suggestion suggestion = ComputerPlayer.getAccusation();
					Solution accusation = new Solution(suggestion.person, suggestion.weapon, board.getLegend().get(suggestion.room));
					
					// If the accusation is false, any ComputerPlayer shouldn't make an accusation until another good accusation is found
					ComputerPlayer.setMakeAccusation(board.checkAccusation(accusation));

					// Show the results of the accusation
					AccusationWindow.showWindow(nextPlayer, accusation, board.checkAccusation(accusation));
				} else {
					
					// Do normal computerPlayer movements and suggestions
					board.makeComputerPlayerMove();
					
					if(board.getCellAt(nextPlayer.getRow(), nextPlayer.getColumn()).isRoom()) {
						board.makeComputerPlayerSuggestion();
					}
				}	
			}
		});
	
		
		Button makeAccusationButton = new Button("Make Accusation");
		
		makeAccusationButton.setOnAction(e ->{
			Player nextPlayer = board.getNextPlayer();
			// If statement to show invalid selection and to show Accusation Menu if the Human Player is allowed to
			if(!(nextPlayer instanceof HumanPlayer && !((HumanPlayer) nextPlayer).getJustMoved())) {
				InvalidMakeAccusationButtonPress.displayInvalidPress();
			} else {
				AccusationMenu.makeAccusationMenu();
			}
			
		});
		
		nextPlayerButton.setFont(new Font(25));
		makeAccusationButton.setFont(new Font(25));
		nextPlayerButton.setMinHeight(120);
		makeAccusationButton.setMinHeight(120);
		nextPlayerButton.setMinWidth(200);
		makeAccusationButton.setMinWidth(200);
		
		HBox.setHgrow(nextPlayerButton, Priority.ALWAYS);
		HBox.setHgrow(makeAccusationButton, Priority.ALWAYS);
		makeAccusationButton.setMaxWidth(Double.MAX_VALUE);
		nextPlayerButton.setMaxWidth(Double.MAX_VALUE);
		playerButtonHbox.getChildren().addAll(whoseTurnVbox, nextPlayerButton, makeAccusationButton);		
		
		// grid to hold the die roll labels
		GridPane dieRollGrid = new GridPane();
		
		// set necessary attributes to dieRollGrid
		dieRollGrid.setHgap(30);
	    dieRollGrid.setVgap(10);
	    dieRollGrid.setPadding(new Insets(0, 10, 0,0));
	    dieRollGrid.setAlignment(Pos.CENTER);
	    
	    // HBox to hold two labels that will show the die roll 
		HBox dieRollHbox = new HBox();
		dieRollHbox.setAlignment(Pos.CENTER);
		 
		
		// Use css to set a border color for the dieRollHbox
		dieRollHbox.setStyle("-fx-border-color: gray");
	
		
		showDieRoll.setMinSize(60, 0);
		showDieRoll.setFont(new Font(20));
		showDieRoll.setStyle("-fx-border-color: blue");
		showDieRoll.setAlignment(Pos.CENTER);
		
		Label dieRoll = new Label("Die Roll");
		dieRoll.setMinSize(120, 45);
		dieRoll.setFont(new Font(25));
		dieRoll.setAlignment(Pos.CENTER);
		
		// Padding label so dieRollHbox looks nicer
		Label paddingLabel = new Label();
		paddingLabel.setMinSize(10, 0);
		
		
		// Add all labels to dieRollHbox
	    dieRollHbox.getChildren().addAll(dieRoll, showDieRoll, paddingLabel);
	    
	    // Add dieRollHbox to dieRollGrid so that the border looks nice
	    dieRollGrid.add(dieRollHbox, 1, 0);
	    
	    // GridPane to hold guessVbox so that the border of guessVbox looks nice
	    GridPane guessGrid = new GridPane();
	    
	    // set necessary attributes to guessGrid
		guessGrid.setHgap(30);
	    guessGrid.setVgap(10);
	    guessGrid.setPadding(new Insets(20, 0, 20, 0));
	    guessGrid.setAlignment(Pos.CENTER);
	    
	    // Vbox for holding the guess labels
	    VBox guessVbox = new VBox();
	    guessVbox.setStyle("-fx-border-color: gray");
	    guessVbox.setMinSize(400, 50);
	    guessVbox.setPadding(new Insets(5,5,5,5));
	    
	    
	    Label guessLabel = new Label("Guess");
	    guessLabel.setMinSize(60, 0);
	    guessLabel.setFont(new Font(25));
	    
	    showGuess.setFont(new Font(20));
	    showGuess.setMinSize(390, 20);
	    showGuess.setStyle("-fx-border-color: blue");
	    showGuess.setPadding(new Insets(0, 10, 0, 10));
	    showGuess.setMinHeight(Region.USE_PREF_SIZE);
	    
	    guessVbox.getChildren().addAll(guessLabel, showGuess);
	    
	    
	    // Add guessVbox to guessGrid so that the borders looks nice
	    guessGrid.add(guessVbox, 0, 0);
	    
	    // guessResultGrid to hold everything for showing the guess result
	    GridPane guessResultGrid = new GridPane();
	    guessResultGrid.setHgap(30);
	    guessResultGrid.setVgap(10);
	    guessResultGrid.setPadding(new Insets(0, 30, 0, 20));
	    guessResultGrid.setAlignment(Pos.CENTER_LEFT);
	    
	    
	    VBox guessResultVbox = new VBox();
	    guessResultVbox.setStyle("-fx-border-color: gray");
	    guessResultVbox.setMinSize(250, 50);
	    guessResultVbox.setPadding(new Insets(5,5,5,5));
	    
	    Label guessResultLabel = new Label("Guess Result");
	    guessResultLabel.setMinSize(60, 0);
	    guessResultLabel.setFont(new Font(25));
	    
	    
	    showGuessResult.setPadding(new Insets(0, 10, 0, 10));
	    showGuessResult.setFont(new Font(20));
	    showGuessResult.setMinSize(240, 20);
	    showGuessResult.setStyle("-fx-border-color: blue");
	   
	    
	    guessResultVbox.getChildren().addAll(guessResultLabel, showGuessResult);
	    
	    guessResultGrid.add(guessResultVbox, 0, 0);
	    
	    
	    // Add necessary components to the pane in the correct location(i.e top, bottom, left, right)
	    pane.setTop(playerButtonHbox);
	    pane.setRight(guessResultGrid);
	    pane.setLeft(dieRollGrid);
	    pane.setCenter(guessGrid);
	    return pane;
	}
	
	
	// Used to show the guess on the control GUI
	public static void setGuess(String roomGuess, String weaponGuess, String personGuess) {
		
		showGuess.setText(personGuess + ", " + roomGuess + ", " + weaponGuess);
		
		showGuess.setFont(new Font(20));
		
		// While to make sure that the guess can fit onto the GUI
		while(true) {
			pane.applyCss();
			pane.layout();
			
			
			String actualString = ((Text)showGuess.lookup(".text")).getText();
			
			if(showGuess.getText().equals(actualString)) {
				break;
			} else {
				Font font = showGuess.getFont();
				
				showGuess.setFont(new Font(font.getSize() - 1));
			}
		}
	}
	
	
	// Used to show the guess result on the control GUI
	public static void setGuessResult(Card disproveCard) {
		
		if(disproveCard == null) {
			showGuessResult.setText("No New Clue");
		} else {
			showGuessResult.setText(disproveCard.getCardName());
		}
		
		
		showGuessResult.setFont(new Font(20));
		
		// While to make sure that the guess result can fit onto the GUI
		while(true) {
			pane.applyCss();
			pane.layout();
			
			String actualString = ((Text)showGuess.lookup(".text")).getText();
			
			if(showGuess.getText().equals(actualString)) {
				break;
			} else {
				Font font = showGuessResult.getFont();
				
				showGuessResult.setFont(new Font(font.getSize() - 1));
			}
		}
		
	}  
	
}
