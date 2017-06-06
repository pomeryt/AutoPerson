package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import utility.SearchableList;
import utility.event.PlainEvent;

/**
 * Layout of the main page. <br />
 * <b>SearchableList</b> and <b>PlainEvent</b> from utility package are required.
 * @author Rin
 * @version 1.0.0
 */
public class MainPage {
	/**
	 * Build layout of the main page.
	 * @since 1.0.0
	 */
	public MainPage(){
		// Label for running status
		lRunning.setAlignment(Pos.CENTER);
		lRunning.setPrefWidth(Integer.MAX_VALUE);
		lRunning.setStyle("-fx-border-color: silver;");
		
		// Label for showing key
		lKey.setAlignment(Pos.CENTER);
		lKey.setPrefWidth(Integer.MAX_VALUE);
		lKey.setStyle("-fx-border-color: silver;");

		// SearchableList
		searchableList = new SearchableList(pane);

		// Remove Button
		bRemove.setStyle("-fx-text-fill: red;");

		// Button array
		final Button[] buttons = { bNew, bEdit, bKey, bSetting, bRefresh, bRemove };
		for (Button button : buttons) {
			button.setMinWidth(75);
		}
		
		// Apply corresponding events to buttons
		final Map<Button, List<PlainEvent>> events = new HashMap<Button, List<PlainEvent>>();
		events.put(bNew, newEvent);
		events.put(bEdit, editEvent);
		events.put(bKey, keyEvent);
		events.put(bSetting, settingEvent);
		events.put(bRefresh, refreshEvent);
		events.put(bRemove, removeEvent);
		for (Button button : events.keySet()){
			button.setOnAction(actionEvent->{
				for (PlainEvent event : events.get(button)){
					event.handle();
				}
			});
		}

		// StackPane for aligning the remove button on bottom-left
		final StackPane paneRemoveButton = new StackPane();
		paneRemoveButton.getChildren().add(bRemove);
		paneRemoveButton.setAlignment(Pos.BOTTOM_CENTER);
		paneRemoveButton.setPrefHeight(Integer.MAX_VALUE);

		// GridPane for the buttons
		final GridPane gridButton = new GridPane();
		gridButton.addColumn(0, bNew, bEdit, bKey, bSetting, bRefresh, paneRemoveButton);

		// GridPane for joining buttons and SearchableList.
		final GridPane gridButtonAndList = new GridPane();
		gridButtonAndList.addRow(0, gridButton, searchableList.body());

		// GridPane for joining gridButtonAndList and status label.
		final GridPane gridStat = new GridPane();
		gridStat.addColumn(0, lRunning, lKey, gridButtonAndList);
		gridStat.setPadding(new Insets(10, 10, 10, 10));

		// StackPane
		pane.getChildren().add(gridStat);
		
		// Scene
		scene = new Scene(pane);
	}
	
	/**
	 * When new button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addNewEvent(PlainEvent event){
		newEvent.add(event);
	}
	
	/**
	 * When edit button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addEditEvent(PlainEvent event){
		editEvent.add(event);
	}
	
	/**
	 * When key button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addKeyEvent(PlainEvent event){
		keyEvent.add(event);
	}
	
	/**
	 * When setting button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addSettingEvent(PlainEvent event){
		settingEvent.add(event);
	}
	
	/**
	 * When refresh button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addRefreshEvent(PlainEvent event){
		refreshEvent.add(event);
	}
	
	/**
	 * When remove button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addRemoveEvent(PlainEvent event){
		removeEvent.add(event);
	}
	
	/**
	 * Display script name to indicate the script is running. <br />
	 * The script name will be shown as lime green and bold.
	 * @param status script name
	 * @since 1.0.0
	 */
	public void showAsRunning(String status){
		lRunning.setStyle("-fx-border-color: silver; -fx-font-weight: bold; -fx-text-fill: limegreen;");
		lRunning.setText(status);
	}
	
	/**
	 * Display "Ready" to indicate no script is currently running. <br />
	 * The text will be same as JavaFX default except there will be silver border.
	 * @since 1.0.0
	 */
	public void showAsReady(){
		lRunning.setStyle("-fx-border-color: silver;");
		lRunning.setText("Ready");
	}
	
	/**
	 * Display start key of the script.
	 * @param key start key of the script
	 * @since 1.0.0
	 */
	public void showKey(String key){
		lKey.setText(key);
	}
	
	/**
	 * Return SearchableList.
	 * @return SearchableList
	 * @since 1.0.0
	 */
	public SearchableList searchableList(){
		return searchableList;
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

	private final SearchableList searchableList;
	private final Label lRunning = new Label("Ready");
	private final Label lKey = new Label();
	private final List<PlainEvent> newEvent = new ArrayList<PlainEvent>();
	private final List<PlainEvent> editEvent = new ArrayList<PlainEvent>();
	private final List<PlainEvent> keyEvent = new ArrayList<PlainEvent>();
	private final List<PlainEvent> settingEvent = new ArrayList<PlainEvent>();
	private final List<PlainEvent> refreshEvent = new ArrayList<PlainEvent>();
	private final List<PlainEvent> removeEvent = new ArrayList<PlainEvent>();
	private final Button bNew = new Button("New");
	private final Button bEdit = new Button("Edit");
	private final Button bKey = new Button("Key");
	private final Button bSetting = new Button("Setting");
	private final Button bRefresh = new Button("Refresh");
	private final Button bRemove = new Button("Remove");
	private final StackPane pane = new StackPane();
	private final Scene scene;
}