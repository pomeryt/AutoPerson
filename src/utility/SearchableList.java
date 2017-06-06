package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import utility.event.StringEvent;

/**
 * This class shows a list of strings with searching feature. <br />
 * Duplicated item addition will be ignored. <br />
 * Items will be automatically sorted alphabetically. <br />
 * This class requires <b>StringEvent.java</b> and <b>center.css</b> files from utility package.
 * @author Rin
 * @version 1.0.0
 */
public class SearchableList {
	/**
	 * A container should be provided for the size.
	 * @param container It will try to fit to this container.
	 * @since 1.0.0
	 */
	public SearchableList(Region container) {
		// ListView
		listView.setItems(obsList);
		listView.prefWidthProperty().bind(pane.widthProperty());
		listView.prefHeightProperty().bind(pane.heightProperty());
		listView.getStylesheets().add("utility/center.css");
		listView.getSelectionModel().selectedIndexProperty().addListener(e->{
			if (listView.getSelectionModel().getSelectedIndex() != -1){
				for (StringEvent selectionEvent : selectionEvents){
					selectionEvent.handle(listView.getSelectionModel().getSelectedItem());
				}
			}
		});
		
		// TextField as a search bar
		tfSearch.textProperty().addListener(observable->{
			updateList();
		});

		// GridPane
		final GridPane grid = new GridPane();
		grid.addColumn(0, tfSearch, listView);

		// StackPane
		pane.getChildren().add(grid);
		pane.prefWidthProperty().bind(container.widthProperty());
		pane.prefHeightProperty().bind(container.heightProperty());
	}
	
	/**
	 * Adds an item to the list.
	 * @param item New element
	 * @since 1.0.0
	 */
	public void add(String item){
		set.add(item);
		updateList();
	}
	
	/**
	 * Removes an item from the list.
	 * @param item An element to be removed
	 * @since 1.0.0
	 */
	public void remove(String item){
		set.remove(item);
		updateList();
	}
	
	/**
	 * Clears the list.
	 * @since 1.0.0
	 */
	public void clear(){
		set.clear();
		updateList();
	}
	
	/**
	 * This method must be called in order to use this object. <br />
	 * Simply, place this body in a container.
	 * @return The body of this object
	 * @since 1.0.0
	 */
	public StackPane body() {
		return pane;
	}
	
	/**
	 * When an item is selected, the specified action will be executed.
	 * @param selectionEvent An implementation of execution
	 * @since 1.0.0
	 */
	public void addSelectionEvent(StringEvent selectionEvent){
		selectionEvents.add(selectionEvent);
	}
	
	/**
	 * It will return the selected item. <br />
	 * If no item is selected, then it will return null.
	 * @return A selected item
	 * @since 1.0.0
	 */
	public String selectedItem(){
		return listView.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * This method will updates the list according to the searching feature.
	 * @since 1.0.0
	 */
	private void updateList(){
		// Get text from the search bar
		final String text = tfSearch.getText();
		
		// Clears the list
		obsList.clear();
		
		// Add matching items
		for (String iter : set){
			if (iter.contains(text)){
				obsList.add(iter);
			}
		}
	}
	
	private final List<StringEvent> selectionEvents = new ArrayList<StringEvent>();
	private final Set<String> set = new TreeSet<String>();
	private final ListView<String> listView = new ListView<String>();
	private final ObservableList<String> obsList = FXCollections.observableArrayList();
	private final TextField tfSearch = new TextField();
	private final StackPane pane = new StackPane();
}
