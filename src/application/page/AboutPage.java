package application.page;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utility.ErrorMessage;
import utility.event.PlainEvent;

/**
 * About Page. <br />
 * It contains following informations: developer, version, and link of source code. <br />
 * <b>PlainEvent</b> and <b>ErrorMessage</b> are required.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public class AboutPage {
	public AboutPage(Stage stage){
		// To Main button
		bMain.setOnAction(actionEvent->{
			for (PlainEvent event : mainEvents){
				event.handle();
			}
		});
		
		// Label for developer
		final Label lDeveloper = new Label("Developer: Rin");
		lDeveloper.setPrefWidth(Integer.MAX_VALUE);
		lDeveloper.setAlignment(Pos.CENTER);
		
		// Label for version
		final Label lVersion = new Label("Version: 1.0.0");
		lVersion.setPrefWidth(Integer.MAX_VALUE);
		lVersion.setAlignment(Pos.CENTER);
		
		// Link to source code
		final Hyperlink linkWeb = new Hyperlink("More Information");
		linkWeb.setOnAction(actionEvent->{
			try {
				Desktop.getDesktop().browse(new URI("https://pomeryt.github.io/AutoPerson/"));
			} catch (Exception exception) {
				ErrorMessage errorMessage = new ErrorMessage(exception, stage);
				errorMessage.showThenClose();
			}
		});
		
		// StackPane for link
		final StackPane paneLink = new StackPane();
		paneLink.getChildren().add(linkWeb);
		
		// GridPane
		final GridPane grid = new GridPane();
		grid.addColumn(0, lDeveloper, lVersion, paneLink);
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		
		// Root container
		final StackPane pane = new StackPane();
		pane.getChildren().addAll(grid, bMain);
		StackPane.setAlignment(bMain, Pos.TOP_CENTER);
		StackPane.setMargin(bMain, new Insets(10, 0, 0, 0));
		
		// Scene
		scene = new Scene(pane);
	}
	
	/**
	 * When To Main button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addMainEvent(PlainEvent event){
		mainEvents.add(event);
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
	
	private final List<PlainEvent> mainEvents = new ArrayList<PlainEvent>();
	private final Button bMain = new Button("To Main");
	private final Scene scene;
}
