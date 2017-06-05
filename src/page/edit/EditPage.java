package page.edit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.jnativehook.GlobalScreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import page.Page;
import page.main.MainPage;
import utility.ErrorMessage;
import utility.primitive.MyInteger;

public class EditPage extends Page {
	public EditPage() {
		// Label for showing status
		Label[] lStatus = { lReady, lFile };
		for (Label iter : lStatus) {
			iter.setPrefWidth(Integer.MAX_VALUE);
			iter.setAlignment(Pos.CENTER);
			iter.setStyle("-fx-border-color: silver;");
		}

		// Label for Loop and Sleep settings
		Label lLoop = new Label("Loop");
		Label lSleep1 = new Label("Sleep1");
		Label lSleep2 = new Label("Sleep2");

		Label[] lLoopSleep = { lLoop, lSleep1, lSleep2 };
		for (Label iter : lLoopSleep) {
			iter.setMinWidth(100);
			iter.setAlignment(Pos.CENTER);
			iter.setStyle("-fx-border-color: silver;");
		}

		// TextField for Loop and Sleep settings
		TextField[] tfLoopSleep = { tfLoop, tfSleep1, tfSleep2 };
		for (TextField iter : tfLoopSleep) {
			iter.setFocusTraversable(false);
			iter.setAlignment(Pos.CENTER);
			iter.setMinWidth(100);
		}

		// GridPane for joining Loop and Sleep components
		GridPane gridLoop = new GridPane();
		gridLoop.addColumn(0, lLoop, tfLoop);

		GridPane gridSleep1 = new GridPane();
		gridSleep1.addColumn(0, lSleep1, tfSleep1);

		GridPane gridSleep2 = new GridPane();
		gridSleep2.addColumn(0, lSleep2, tfSleep2);

		// Buttons for left menu
		bMain.setFocusTraversable(false);

		Button bClear = new Button("Clear");
		bClear.setFocusTraversable(false);
		bClear.setStyle("-fx-text-fill: red;");
		bClear.setOnAction(actionEvent -> {
			taScript.clear();
		});

		Button[] bMenu = { bMain, bClear };
		for (Button iter : bMenu) {
			iter.setMinWidth(100);
		}

		// StackPane for storing the clear button
		StackPane paneClear = new StackPane();
		paneClear.getChildren().add(bClear);
		paneClear.setAlignment(Pos.BOTTOM_CENTER);
		paneClear.setPrefHeight(Integer.MAX_VALUE);

		// GridPane for left menu
		GridPane gridMenu = new GridPane();
		gridMenu.addColumn(0, gridLoop, gridSleep1, gridSleep2, bMain, paneClear);
		gridMenu.setPadding(new Insets(10, 10, 10, 10));
		gridMenu.setVgap(10);
		gridMenu.setStyle("-fx-border-color: silver;");

		// TextArea on the right
		taScript.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

		// GridPane for joining the menu and text area
		GridPane gridBody = new GridPane();
		gridBody.addRow(0, gridMenu, taScript);
		gridBody.setPrefHeight(Integer.MAX_VALUE);

		// GridPane for joining all
		GridPane grid = new GridPane();
		grid.addColumn(0, lReady, lFile, gridBody);
		grid.setPadding(new Insets(10, 10, 10, 10));

		// StackPane
		StackPane pane = new StackPane();
		pane.getChildren().add(grid);

		// Scene
		scene = new Scene(pane);

		// Prevent focus on text field at first
		pane.requestFocus();
	}

	public void linkToMainPage(Stage stage, MainPage mainPage, Path path) {
		// Leave to MainPage button
		bMain.setOnAction(actionEvent -> {
			// Save script
			try {
				// Lines of script
				String[] lines = taScript.getText().split("\n");

				// Start Key
				BufferedReader bufferedReader = Files.newBufferedReader(path);
				String startKey = bufferedReader.readLine();
				bufferedReader.close();

				// Prepare to write script to text file
				BufferedWriter writer = Files.newBufferedWriter(path);

				// Write first three lines for configuration
				writer.write(startKey + "\n");
				writer.write(tfLoop.getText() + "\n");
				writer.write(tfSleep1.getText() + "\n");
				writer.write(tfSleep2.getText() + "\n");

				// Write script
				for (String command : lines) {
					writer.write(command + "\n");
					writer.flush();
				}

				writer.close();

			} catch (IOException ioException) {
				ErrorMessage errorMessage = new ErrorMessage(ioException, stage);
				errorMessage.showThenClose();
			}

			// Remove listeners
			if (script != null){
				GlobalScreen.removeNativeKeyListener(script);
				GlobalScreen.removeNativeMouseListener(script);
				GlobalScreen.removeNativeMouseWheelListener(script);
			}

			// Reset status information
			lReady.setStyle("-fx-border-color: silver; -fx-text-fill: black;");
			lReady.setText("Ready");

			// Go to main page
			stage.setScene(mainPage.body());
		});
	}
	
	public void showFileName(String fileName){
		lFile.setText("fileName");
	}
	
	public void addNativeListeners(MyInteger recordKey){
		// Start NativeListeners
		script = new Script(recordKey, lReady, taScript);
		GlobalScreen.addNativeKeyListener(script);
		GlobalScreen.addNativeMouseListener(script);
		GlobalScreen.addNativeMouseWheelListener(script);
	}

	public void showScript(Path path) throws Exception {
		
		// Reader
		BufferedReader reader = Files.newBufferedReader(path);

		// Skip first line
		reader.readLine();

		// Loop and Sleep as string
		String loop = reader.readLine();
		String sleep1 = reader.readLine();
		String sleep2 = reader.readLine();

		// Show Loop and Sleep informations
		tfLoop.setText(loop);
		tfSleep1.setText(sleep1);
		tfSleep2.setText(sleep2);

		// Read rest of lines and store them to text area
		taScript.clear();
		String line = reader.readLine();
		while (line != null) {
			taScript.appendText(line + "\n");
			line = reader.readLine();
		}
		reader.close();

	}

	public Scene body() {
		return scene;
	}
	
	private Script script;
	
	private final Label lReady = new Label("Ready");
	private final TextArea taScript = new TextArea();
	private final TextField tfLoop = new TextField();
	private final TextField tfSleep1 = new TextField();
	private final TextField tfSleep2 = new TextField();
	private final Label lFile = new Label();
	private final Button bMain = new Button("To Main");
	private final Scene scene;
}
