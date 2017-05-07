package application;
	
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			
			// Stage
			stage.setTitle("AutoPerson");
			stage.setWidth(300);
			stage.setHeight(325);
			stage.show();
			stage.setOnCloseRequest(windowEvent->{
				
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
