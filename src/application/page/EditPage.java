package application.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import utility.event.PlainEvent;

/**
 * Layout of the edit page. <br />
 * <b>PlainEvent</b> from utility package is required.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public class EditPage {
	/**
	 * Build layout of the edit page.
	 * @since 1.0.0
	 */
	public EditPage(){
		// Label for showing status		
		final Label[] lStatus = {lRecording, lScriptName};
		for (Label iter : lStatus){
			iter.setPrefWidth(Integer.MAX_VALUE);
			iter.setAlignment(Pos.CENTER);
			iter.setStyle("-fx-border-color: silver;");
		}
		
		// Label for Loop and Sleep settings
		final Label lLoop = new Label("Loop");
		final Label lSleep1 = new Label("Sleep1");
		final Label lSleep2 = new Label("Sleep2");
		
		final Label[] lLoopSleep = {lLoop, lSleep1, lSleep2};
		for (Label iter: lLoopSleep){
			iter.setMinWidth(100);
			iter.setAlignment(Pos.CENTER);
			iter.setStyle("-fx-border-color: silver;");
		}
		
		// TextField for Loop and Sleep settings
		final TextField[] tfLoopSleep = {tfLoop, tfSleep1, tfSleep2};
		for (TextField iter : tfLoopSleep){
			iter.setFocusTraversable(false);
			iter.setAlignment(Pos.CENTER);
			iter.setMinWidth(100);
		}
		
		// GridPane for joining Loop and Sleep components
		final GridPane gridLoop = new GridPane();
		gridLoop.addColumn(0, lLoop, tfLoop);
		
		final GridPane gridSleep1 = new GridPane();
		gridSleep1.addColumn(0, lSleep1, tfSleep1);
		
		final GridPane gridSleep2 = new GridPane();
		gridSleep2.addColumn(0, lSleep2, tfSleep2);
		
		// Buttons for left menu
		bMain.setFocusTraversable(false);
		
		bClear.setFocusTraversable(false);
		bClear.setStyle("-fx-text-fill: red;");
		bClear.setOnAction(actionEvent->{
			taScript.clear();
		});
		
		final Button[] bMenu = {bMain, bClear};
		for (Button iter : bMenu){
			iter.setMinWidth(100);
		}
		
		// Apply corresponding events to buttons
		final Map<Button, List<PlainEvent>> buttonEvents = new HashMap<Button, List<PlainEvent>>();
		buttonEvents.put(bMain, mainEvent);
		buttonEvents.put(bClear, clearEvent);
		for (Button button : buttonEvents.keySet()){
			button.setOnAction(actionEvent->{
				for (PlainEvent event : buttonEvents.get(button)){
					event.handle();
				}
			});
		}
		
		// StackPane for storing the clear button
		final StackPane paneClear = new StackPane();
		paneClear.getChildren().add(bClear);
		paneClear.setAlignment(Pos.BOTTOM_CENTER);
		paneClear.setPrefHeight(Integer.MAX_VALUE);
		
		// GridPane for left menu
		final GridPane gridMenu = new GridPane();
		gridMenu.addColumn(0, gridLoop, gridSleep1, gridSleep2, bMain, paneClear);
		gridMenu.setPadding(new Insets(10, 10, 10, 10));
		gridMenu.setVgap(10);
		gridMenu.setStyle("-fx-border-color: silver;");
		
		// TextArea on the right
		taScript.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		// GridPane for joining the menu and text area
		final GridPane gridBody = new GridPane();
		gridBody.addRow(0, gridMenu, taScript);
		gridBody.setPrefHeight(Integer.MAX_VALUE);
		
		// GridPane for joining all
		final GridPane grid = new GridPane();
		grid.addColumn(0, lRecording, lScriptName, gridBody);
		grid.setPadding(new Insets(10, 10, 10, 10));
		
		// StackPane
		final StackPane pane = new StackPane();
		pane.getChildren().add(grid);
		
		// Scene
		scene = new Scene(pane);
		
		// Prevent focus on text field at first
		pane.requestFocus();
	}
	
	/**
	 * When "To Main" button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addMainEvnet(PlainEvent event){
		mainEvent.add(event);
	}
	
	/**
	 * When clear button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addClearEvent(PlainEvent event){
		clearEvent.add(event);
	}
	
	/**
	 * Show whether it is recording or not.
	 * @param isRecording
	 * @since 1.0.0
	 */
	public void showRecording(boolean isRecording){
		if (isRecording){
			lRecording.setStyle("-fx-border-color: silver; -fx-font-weight: bold; -fx-text-fill: limegreen;");
			lRecording.setText("Recording");
		} else {
			lRecording.setStyle("-fx-border-color: silver;");
			lRecording.setText("Ready");
		}
	}
	
	/**
	 * Show script name that user is currently working.
	 * @param scriptName also known as name of file
	 * @since 1.0.0
	 */
	public void showScriptName(String scriptName){
		lScriptName.setText(scriptName);
	}
	
	/**
	 * Return script name which is acquired from the Label.
	 * @return script name
	 * @since 1.0.0
	 */
	public String scriptName(){
		return lScriptName.getText();
	}
	
	/**
	 * Return TextArea for script.
	 * @return text area for script
	 * @since 1.0.0
	 */
	public TextArea scriptArea(){
		return taScript;
	}
	
	/**
	 * Return TextField for loop.
	 * @return text field for loop
	 * @since 1.0.0
	 */
	public TextField loop(){
		return tfLoop;
	}
	
	/**
	 * Return TextField for Sleep1.
	 * @return text field for sleep1
	 * @since 1.0.0
	 */
	public TextField sleep1(){
		return tfSleep1;
	}
	
	/**
	 * Return TextField for Sleep2.
	 * @return text field for sleep2
	 * @since 1.0.0
	 */
	public TextField sleep2(){
		return tfSleep2;
	}
	
	/**
	 * This method must be called in order to use this object. <br />
	 * Simply, set scene of a stage to this body.
	 * @return Scene
	 * @since 1.0.0
	 */
	public Scene body(){
		return scene;
	}
	
	private final Label lRecording = new Label("Ready");
	private final Label lScriptName = new Label();
	private final TextArea taScript = new TextArea();
	private final TextField tfLoop = new TextField();
	private final TextField tfSleep1 = new TextField();
	private final TextField tfSleep2 = new TextField();
	private final List<PlainEvent> mainEvent = new ArrayList<PlainEvent>();
	private final List<PlainEvent> clearEvent = new ArrayList<PlainEvent>();
	private final Button bMain = new Button("To Main");
	private final Button bClear = new Button("Clear");
	private final Scene scene;
}
