package application;
	
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;

import javafx.application.Application;
import javafx.stage.Stage;
import utility.ErrorMessage;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			
			// Set GlobalScreen to only show warnings and errors.
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.WARNING);
			logger.setUseParentHandlers(false);
			
			// GlobalScreen
			GlobalScreen.registerNativeHook();
			
			// Stage
			stage.setTitle("AutoPerson");
			stage.setWidth(300);
			stage.setHeight(325);
			stage.show();
			stage.setOnCloseRequest(windowEvent->{
				System.exit(0);
			});
			
		} catch(Exception exception) {
			
			// Show error message when an exception occurs and then close the program.
			ErrorMessage errorMessage = new ErrorMessage(exception);
			errorMessage.showThenClose();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
