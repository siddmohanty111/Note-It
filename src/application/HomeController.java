package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeController extends SceneController implements Initializable {

	@FXML
	private TableView<JournalEntry> entryTable;

	@FXML
	private TableColumn<JournalEntry, String> dateCol;

	@FXML
	private TableColumn<JournalEntry, String> titleCol;

	@FXML
	private Button searchButton;

	private ObservableList<JournalEntry> data;

	/**
	 * This initializes the controller, and sets the Table on the home screen to display all entries
	 * 
	 * @param location  The location used to resolve relative paths for root object,
	 *                  otherwise null
	 * @param resources The resources used to localize the root object, otherwise
	 *                  null
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		data = FXCollections.observableArrayList();
		setCellTable();
		loadDataFromDatabase();
	}

	/*
	 * This method sets the cell value factories for the date and title columns in
	 * TableView.
	 */
	private void setCellTable() {
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
	}

	/*
	 * This method loads data from the database and populates the TableView with
	 * journal entries.
	 */
	private void loadDataFromDatabase() {
		List<JournalEntry> entriesList = entryModel.getAllEntries();
		data.addAll(entriesList);
		entryTable.setItems(data);
	}

	/**
	 * When the logout button of the GUI is clicked, the app switches back to login
	 * screen.
	 * 
	 * @param event The action of clicking on the logout button
	 */
	@FXML
	public void Logout(ActionEvent event) {
		try {
			((Node) event.getSource()).getScene().getWindow().hide();
			changeScene("LoginView.fxml");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * When the change password button of the GUI is clicked, the app switches to
	 * the change password screen
	 * 
	 * @param event The action of clicking on the change password button
	 */
	@FXML
	public void ChangePassword(ActionEvent event) {
		try {
			((Node) event.getSource()).getScene().getWindow().hide();
			changeScene("SecurityView.fxml");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * When the "+" button of the GUI is clicked, the app jumps to the entry
	 * screen.
	 * 
	 * @param event The action of clicking on the "+" button
	 */
	@FXML
	public void CreateNewEntry(ActionEvent event) {
		try {
			((Node) event.getSource()).getScene().getWindow().hide();
			changeScene("EntryView.fxml");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * When the Search button is clicked, the app jumps to the search screen
	 * 
	 * @param event
	 */
	@FXML
	public void Search(ActionEvent event) {

		try {
			((Node) event.getSource()).getScene().getWindow().hide();
			changeScene("SearchView.fxml");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
