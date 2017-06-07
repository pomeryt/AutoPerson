package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import utility.event.PlainEvent;

/**
 * Layout of the setting page. <br />
 * <b>PlainEvent</b> from utility package is required.
 * @author Rin
 * @version 1.0.0
 */
public class SettingPage {
	/**
	 * Build layout of the setting page.
	 * @since 1.0.0
	 */
	public SettingPage(){
		// Apply corresponding events to buttons
		final Map<Button, List<PlainEvent>> buttonEvents = new HashMap<Button, List<PlainEvent>>();
		buttonEvents.put(bMain, mainEvent);
		buttonEvents.put(bRecord, recordEvent);
		for (Button button : buttonEvents.keySet()){
			button.setOnAction(actionEvent->{
				for (PlainEvent event : buttonEvents.get(button)){
					event.handle();
				}
			});
		}
		
		// Apply Always On Top event to check box
		cbAlwaysOnTop.setOnAction(actionEvent->{
			for (PlainEvent event : alwaysOnTopEvent){
				event.handle();
			}
		});
		
		// StackPane for storing record button
		StackPane paneRecordButton = new StackPane();
		paneRecordButton.getChildren().add(bRecord);
		paneRecordButton.setAlignment(Pos.CENTER);
		
		// GridPane for listing elements
		GridPane grid = new GridPane();
		grid.addColumn(0, cbAlwaysOnTop, paneRecordButton);
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		
		// StackPane
		pane.getChildren().addAll(grid, bMain);
		StackPane.setAlignment(bMain, Pos.TOP_CENTER);
		StackPane.setMargin(bMain, new Insets(10, 0, 0, 0));
		
		// Scene
		scene = new Scene(pane);
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
	 * When record key button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addRecordEvnet(PlainEvent event){
		recordEvent.add(event);
	}
	
	/**
	 * When Always On Top button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addAlwaysOnTopEvnet(PlainEvent event){
		alwaysOnTopEvent.add(event);
	}
	
	/**
	 * Show keyboard key.
	 * @param keyText keyboard key
	 * @since 1.0.0
	 */
	public void showRecordKey(String keyText){
		bRecord.setText(keyText);
	}
	
	/**
	 * Return CheckBox for Always On Top feature.
	 * @return check box for Always On Top feature.
	 * @since 1.0.0
	 */
	public CheckBox alwaysOnTop(){
		return cbAlwaysOnTop;
	}
	
	/**
	 * Return root container.
	 * @return root container
	 * @since 1.0.0
	 */
	public StackPane root(){
		return pane;
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
	
	private final List<PlainEvent> mainEvent = new ArrayList<PlainEvent>();
	private final List<PlainEvent> recordEvent = new ArrayList<PlainEvent>();
	private final List<PlainEvent> alwaysOnTopEvent = new ArrayList<PlainEvent>();
	private final Button bMain = new Button("To Main");
	private final Button bRecord = new Button("Record Key");
	private final CheckBox cbAlwaysOnTop = new CheckBox("Always On Top");
	private final StackPane pane = new StackPane();
	private final Scene scene;
}
