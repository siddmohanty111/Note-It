package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SearchController extends SceneController implements Initializable {

	@FXML
	private TextField searchBar;

	@FXML
	private TableView<JournalEntry> outputTable;

	@FXML
	private TableColumn<JournalEntry, String> dateCol;

	@FXML
	private TableColumn<JournalEntry, String> titleCol;

	private ObservableList<JournalEntry> searchData;

	/**
	 * 
	 * Initializes the controller and sets the table on the search screen to initially show all entries.
	 * 
	 * @param location  The location used to resolve relative paths for root object,
	 *                  otherwise null
	 * @param resources The resources used to localize the root object, otherwise
	 *                  null
	 *
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		searchData = FXCollections.observableArrayList();
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		List<JournalEntry> entriesList = entryModel.getAllEntries();
		searchData.addAll(entriesList);
		outputTable.setItems(searchData);

	}

	/**
	 * When the edit button is clicked, the app displays the entry that has been selected in the table
	 * in a text editor
	 * 
	 * @param event The action of clicking on the edit button
	 */
	@FXML
	public void Edit(ActionEvent event) {

		JournalEntry selectedEntry = outputTable.getSelectionModel().getSelectedItem();

		if (selectedEntry != null) {

			try {
				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/application/EntryView.fxml").openStream());
				EntryController entryController = loader.getController();
				entryController.populate(selectedEntry);
				entryController.setStatus(true);
				Scene scene = new Scene(root, 600, 400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	/*
	 * When the "Delete" button is clicked, the selected entry is deleted from
	 * TableView and the database
	 * 
	 * @param event The ActionEvent triggered by clicking the "Delete" button
	 */
	@FXML
	public void Delete(ActionEvent event) {

		// Get selected entry from TableView
		JournalEntry selectedEntry = outputTable.getSelectionModel().getSelectedItem();

		// Check if an entry is selected in TableView
		if (selectedEntry != null) {

			// Delete selected item fromTableView
			outputTable.getItems().remove(selectedEntry);

			// Delete the entry from the database using its unique id
			if (entryModel.deleteEntry(selectedEntry.getId())) {
				System.out.println("Entry deleted from database.");
			} else {
				System.out.println("Failed to delete entry.");
			}
		}
	}

	/**
	 * When the "Back" button is pressed, the app exits the Search view and returns to the Home page
	 * 
	 * @param event The action of clicking on the back button
	 */
	@FXML
	public void Back(ActionEvent event) {

		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			changeScene("HomeView.fxml");

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Refreshes the search table with the data provided in the Search bar
	 * 
	 * @param event The action of clicking the search button
	 */
	@FXML
	public void Search(ActionEvent event) {

		try {

			updateSearchTable();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * Updates the search table
	 */
	private void updateSearchTable() {
		searchData = FXCollections.observableArrayList();
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		List<JournalEntry> entriesList = entryModel.getEntryByContents(searchBar.getText());
		searchData.addAll(entriesList);
		outputTable.setItems(searchData);
	}

	/**
	 * Updates the search table every time a key is inputted into the search bar
	 * 
	 * @param event The act of typing a key while the cursor has selected the search bar
	 */
	public void loopedSearch(Event event) {

		try {

			updateSearchTable();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
