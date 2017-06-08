package application;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import utility.event.PlainEvent;
import utility.event.StringEvent;

/**
 * Prompt for creating new script. <br />
 * <b>StringEvent</b> and <b>PlainEvent</b> in utility package are required.
 * @author Rin (pomeryt@gmail.com)
 * @version 1.0.0
 */
public class NewPrompt {
	/**
	 * The prompt will be added into the container. <br/>
	 * Providing a root container of scene is recommended.
	 * @param pane a container
	 * @since 1.0.0
	 */
	public NewPrompt(Pane pane) {
		// Remember parameters
		this.pane = pane;
		
		// Label for an error message
		lError.setPrefWidth(Integer.MAX_VALUE);
		lError.setAlignment(Pos.CENTER);
		lError.setStyle("-fx-text-fill: red;");

		// TextField for prompt
		final TextField tfNew = new TextField();
		tfNew.setPromptText("Enter a script name");

		// Create Button
		bCreate.setOnAction(actionEvent->{
			for (StringEvent event : createEvents){
				event.handle(tfNew.getText());
			}
		});

		// Cancel Button
		bCancel.setOnAction(actionEvent->{
			for (PlainEvent event : cancelEvents){
				event.handle();
			}
		});

		// GridPane for top
		final GridPane gridTop = new GridPane();
		gridTop.addRow(0, tfNew, bCreate, bCancel);
		gridTop.setAlignment(Pos.CENTER);

		// GridPane for everything
		final GridPane grid = new GridPane();
		grid.addColumn(0, gridTop, lError);
		grid.setAlignment(Pos.CENTER);

		// StackPane for entire prompt
		paneNew.getChildren().add(grid);
		paneNew.setStyle("-fx-background-color: rgba(192, 192, 192, 0.7);");
		paneNew.setPadding(new Insets(0, 10, 0, 10));
	}
	
	/**
	 * When create button is clicked, the specified action will be executed. <br />
	 * The parameter of event would be what user entered in the text field.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addCreateEvent(StringEvent event){
		createEvents.add(event);
	}
	
	/**
	 * When cancel button is clicked, the specified action will be executed.
	 * @param event An implementation of action
	 * @since 1.0.0
	 */
	public void addCancelEvents(PlainEvent event){
		cancelEvents.add(event);
	}
	
	/**
	 * Show error message.
	 * @param message error message
	 * @since 1.0.0
	 */
	public void showError(String message){
		lError.setText(message);
	}
	
	/**
	 * Remove the prompt.
	 * @since 1.0.0
	 */
	public void removePrompt(){
		pane.getChildren().remove(paneNew);
	}
	
	/**
	 * Show the prompt to user.
	 * @since 1.0.0
	 */
	public void show(){
		pane.getChildren().add(paneNew);
	}
	
	private final Label lError = new Label();
	private final List<StringEvent> createEvents = new ArrayList<StringEvent>();
	private final List<PlainEvent> cancelEvents = new ArrayList<PlainEvent>();
	private final Button bCreate = new Button("Create");
	private final Button bCancel = new Button("Cancel");
	private final StackPane paneNew = new StackPane();
	private final Pane pane;
}
