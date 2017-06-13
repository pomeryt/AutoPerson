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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import utility.SearchableList;
import utility.event.PlainEvent;

/**
 * Layout of the main page. <br />
 * <b>SearchableList</b> and <b>PlainEvent</b> from utility package are required.
 * @author Rin (pomeryt@gmail.com)
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
		final Button[] buttons = { bNew, bEdit, bKey, bSetting, bRefresh, bAbout, bRemove };
		for (Button button : buttons) {
			button.setMinWidth(75);
		}
		
		// Apply corresponding events to buttons
		final Map<Button, List<PlainEvent>> events = new HashMap<Button, List<PlainEvent>>();
		events.put(bNew, newEvents);
		events.put(bEdit, editEvents);
		events.put(bKey, keyEvents);
		events.put(bSetting, settingEvents);
		events.put(bRefresh, refreshEvents);
		events.put(bAbout, aboutEvents);
		events.put(bRemove, removeEvents);
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
		gridButton.addColumn(0, bNew, bEdit, bKey, bSetting, bRefresh, bAbout, paneRemoveButton);

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
	public void addNewEvents(PlainEvent event){
		newEvents.add(event);
	}
	
	/**
	 * When edit button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addEditEvents(PlainEvent event){
		editEvents.add(event);
	}
	
	/**
	 * When key button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addKeyEvents(PlainEvent event){
		keyEvents.add(event);
	}
	
	/**
	 * When setting button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addSettingEvents(PlainEvent event){
		settingEvents.add(event);
	}
	
	/**
	 * When refresh button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addRefreshEvents(PlainEvent event){
		refreshEvents.add(event);
	}
	
	/**
	 * When remove button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addRemoveEvents(PlainEvent event){
		removeEvents.add(event);
	}
	
	/**
	 * When about button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addAboutEvents(PlainEvent event){
		aboutEvents.add(event);
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
	 * @param isDuplicated whether key is duplicated or not
	 * @since 1.0.0
	 */
	public void showKey(String key, boolean isDuplicated){
		// Change color
		if (isDuplicated){
			lKey.setStyle("-fx-border-color: silver; -fx-text-fill: red;");
		} else {
			lKey.setStyle("-fx-border-color: silver;");
		}
		
		// Show key
		lKey.setText(key);
	}
	
	/**
	 * Call refresh events defined by <b>addrefreshEvents</b> method. <br />
	 * It will break the JavaFX thread if its stage is not showed yet. <br />
	 * In order to prevent breaking the JavaFX thread, use <b>Platform.runLater</b> method.
	 * @since 1.0.0
	 */
	public void callRefreshEvents(){
		for (PlainEvent event : refreshEvents){
			event.handle();
		}
	}
	
	/**
	 * Call edit events defined by <b>addeditEvents</b> method.
	 * @since 1.0.0
	 */
	public void callEditEvents(){
		for (PlainEvent event : editEvents){
			event.handle();
		}
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

	private final SearchableList searchableList;
	private final Label lRunning = new Label("Ready");
	private final Label lKey = new Label();
	private final List<PlainEvent> newEvents = new ArrayList<PlainEvent>();
	private final List<PlainEvent> editEvents = new ArrayList<PlainEvent>();
	private final List<PlainEvent> keyEvents = new ArrayList<PlainEvent>();
	private final List<PlainEvent> settingEvents = new ArrayList<PlainEvent>();
	private final List<PlainEvent> refreshEvents = new ArrayList<PlainEvent>();
	private final List<PlainEvent> aboutEvents = new ArrayList<PlainEvent>();
	private final List<PlainEvent> removeEvents = new ArrayList<PlainEvent>();
	private final Button bNew = new Button("New");
	private final Button bEdit = new Button("Edit");
	private final Button bKey = new Button("Key");
	private final Button bSetting = new Button("Setting");
	private final Button bRefresh = new Button("Refresh");
	private final Button bAbout = new Button("About");
	private final Button bRemove = new Button("Remove");
	private final StackPane pane = new StackPane();
	private final Scene scene;
}