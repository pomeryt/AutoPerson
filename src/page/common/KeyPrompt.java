package page.common;

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
import utility.event.PlainEvent;

public class KeyPrompt implements NativeKeyListener {
	public KeyPrompt(Pane pane, PlainEvent saveEvent) {
		build(pane, saveEvent);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent key) {
		Platform.runLater(() -> {
			if (integerEvent != null){
				final CorrectKey correctKey = new CorrectKey();
				int keyCode = correctKey.convert(key.getKeyCode(), key.getRawCode());
				integerEvent.handle(keyCode);
			}
		});
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public void onKeyPressed(IntegerEvent integerEvent){
		this.integerEvent = integerEvent;
	}
	
	public void showText(String text){
		lText.setText(text);
	}

	private void build(Pane pane, PlainEvent saveEvent) {
		// Initialize a container
		StackPane paneRecord = new StackPane();

		// Label for showing key
		lText.setPrefWidth(Integer.MAX_VALUE);
		lText.setAlignment(Pos.CENTER);
		lText.setStyle(
				"-fx-background-color: white; -fx-effect: dropshadow(one-pass-box, deepskyblue, 10, 0.5, 0, 0);");

		// Add native key listener
		GlobalScreen.addNativeKeyListener(this);

		// Save Button
		Button bSave = new Button("Save");
		bSave.setFocusTraversable(false);
		bSave.setStyle("-fx-effect: dropshadow(one-pass-box, deepskyblue, 10, 0.5, 0, 0);");
		bSave.setOnAction(actionEvent -> {
			GlobalScreen.removeNativeKeyListener(this);
			pane.getChildren().remove(paneRecord);
			saveEvent.handle();
		});

		// StackPane for save button
		StackPane paneSave = new StackPane();
		paneSave.getChildren().add(bSave);

		// GridPane
		GridPane grid = new GridPane();
		grid.addColumn(0, lText, paneSave);
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		// Top container
		paneRecord.setStyle("-fx-background-color: rgba(192, 192, 192, 0.8);");
		paneRecord.getChildren().add(grid);

		// Root container
		pane.getChildren().add(paneRecord);

		// Prevent pressing any button by space key
		paneRecord.requestFocus();
	}
	
	private IntegerEvent integerEvent;
	
	private final Label lText = new Label();
}
