package application;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import utility.event.IntegerEvent;

/**
 * Prompt for key setting. <br />
 * <b>IntegerEvent</b>, <b>CorrectKey</b>, and <b>JNativeHook</b> are required.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public class KeyPrompt implements NativeKeyListener {
	/**
	 * The prompt will be added into the container. <br/>
	 * Providing a root container of scene is recommended.
	 * @param pane a container
	 * @since 1.0.0
	 */
	public KeyPrompt(Pane pane) {
		// Remember parameters
		this.pane = pane;
		
		// Label for showing key
		lText.setPrefWidth(Integer.MAX_VALUE);
		lText.setAlignment(Pos.CENTER);
		lText.setStyle("-fx-background-color: white; -fx-effect: dropshadow(one-pass-box, deepskyblue, 10, 0.5, 0, 0);");

		// Save Button
		bSave.setFocusTraversable(false);
		bSave.setStyle("-fx-effect: dropshadow(one-pass-box, deepskyblue, 10, 0.5, 0, 0);");
		bSave.setOnAction(actionEvent->{
			// Remove NativeKeyListener
			GlobalScreen.removeNativeKeyListener(this);
			
			// Remove prompt
			pane.getChildren().remove(paneRecord);
			
			// Making sure that the key has been defined
			if (key == null){
				return;
			}
			
			// Handle events
			for (IntegerEvent event : saveEvents){
				event.handle(key);
			}
		});

		// StackPane for save button
		final StackPane paneSave = new StackPane();
		paneSave.getChildren().add(bSave);

		// GridPane
		final GridPane grid = new GridPane();
		grid.addColumn(0, lText, paneSave);
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		// Top container
		paneRecord.setStyle("-fx-background-color: rgba(192, 192, 192, 0.8);");
		paneRecord.getChildren().add(grid);
	}
	
	/**
	 * Show the prompt and start native key listener.
	 * @since 1.0.0
	 */
	public void show(){
		// Show prompt
		pane.getChildren().add(paneRecord);
		
		// Add NativeKeyListener
		GlobalScreen.addNativeKeyListener(this);
		
		// Prevent pressing any button by space key
		paneRecord.requestFocus();
	}
	
	/**
	 * When save button is clicked, the specified action will be executed. <br />
	 * The parameter of event would be a key code.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addSaveEvent(IntegerEvent event){
		saveEvents.add(event);
	}
	
	/**
	 * Show text on the prompt.
	 * @param text
	 * @since 1.0.0
	 */
	public void showText(String text){
		lText.setText(text);
	}
	
	/**
	 * When keyboard key is pressed, the specified action will be executed. <br />
	 * The parameter of event would be a corrected key code.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addKeyEvents(IntegerEvent event){
		keyEvents.add(event);
	}
	
	/**
	 * Show pressed keyboard key and assign its AWT key code.
	 * @param key native key event
	 * @since 1.0.0
	 */
	@Override
	public void nativeKeyPressed(NativeKeyEvent key) {
		// Define key
		final CorrectKey correctKey = new CorrectKey();
		this.key = correctKey.convert(key.getKeyCode(), key.getRawCode());
		
		// Show keyboard key
		Platform.runLater(()->{
			lText.setText(KeyEvent.getKeyText(this.key));
		});
		
		// Execute key events
		for (IntegerEvent event : keyEvents){
			event.handle(this.key);
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private Integer key;
	
	private final Label lText = new Label();
	private final List<IntegerEvent> keyEvents = new ArrayList<IntegerEvent>();
	private final List<IntegerEvent> saveEvents = new ArrayList<IntegerEvent>();
	private final Button bSave = new Button("Save");
	private final StackPane paneRecord = new StackPane();
	private final Pane pane;
}
