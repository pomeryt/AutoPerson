package utility;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * It shows the stack trace of exception and then terminate the program.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public class ErrorMessage {
	/**
	 * Exception should be passed through the constructor. <br />
	 * Stage should be provided in order to turn off always on top feature before showing the error message.
	 * @param exception Exception
	 * @param stage JavaFX stage
	 * @since 1.0.0
	 */
	public ErrorMessage(Exception exception, Stage stage){
		this.exception = exception;
		this.stage = stage;
	}
	
	/**
	 * This method will show the error message. <br />
	 * When an user close the pop-up, the program will be terminated.
	 * @since 1.0.0
	 */
	public void showThenClose(){
		// Turn off always on top property of the stage
		stage.setAlwaysOnTop(false);
		
		// Create a stack trace as string.
		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter);
		exception.printStackTrace(printWriter);
		final String stackTrace = stringWriter.toString();
		
		// Label
		final Label lException = new Label();
		lException.setText("Full Error Message:");
		
		// TextArea
		final TextArea taException = new TextArea();
		taException.setText(stackTrace);
		taException.setEditable(false);
		taException.setWrapText(true);
		taException.setMaxWidth(Double.MAX_VALUE);
		taException.setMaxHeight(Double.MAX_VALUE);
		
		// GridPane
		final GridPane gridException = new GridPane();
		gridException.addColumn(0, lException, taException);
		GridPane.setVgrow(taException, Priority.ALWAYS);
		GridPane.setHgrow(taException, Priority.ALWAYS);
		
		// Alert object
		final Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Message");
		alert.setHeaderText("You have found a bug.");
		alert.setContentText("Please report this bug with the details below.");
		alert.getDialogPane().setExpandableContent(gridException);
		alert.setOnCloseRequest(dialogEvent->{
			System.exit(0);
		});
		alert.showAndWait();
	}
	
	private final Exception exception;
	private final Stage stage;
}
