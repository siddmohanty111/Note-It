package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EntryController extends SceneController implements Initializable {

	@FXML
	private TextField title;

	@FXML
	private DatePicker date;

	@FXML
	private TextArea entry;
	
	@FXML
	private Spinner<Integer> timePickerHour;
	
	@FXML
	private Spinner<Integer> timePickerMinute;
	
	private int id;

	/**
	 * This initializes the controller and sets the date field to the current date
	 * by default.
	 * 
	 * @param location  The location used to resolve relative paths for root object,
	 *                  otherwise null
	 * @param resources The resources used to localize the root object, otherwise
	 *                  null
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// System.out.println(location.toString());

		date.setValue(LocalDate.now());
		timePickerHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23));
		timePickerMinute.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));
		timePickerHour.getValueFactory().setValue(LocalTime.now().getHour());
		timePickerMinute.getValueFactory().setValue(LocalTime.now().getMinute());
		
	}

	/**
	 * When the cancel button of the GUI is clicked, the app switches back to home
	 * screen.
	 * 
	 * @param event The action of clicking on the cancel button
	 *
	 */
	@FXML
	public void Cancel(ActionEvent event) {

		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			changeScene("HomeView.fxml");

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * When the save button of the GUI is clicked, the app saves the journal entry
	 * to the database, and switches back to the home page.
	 * 
	 * @param event The action of clicking on the save button
	 * 
	 */
	@FXML
	public void Save(ActionEvent event) {

		try {
			
			String add = "";

			if (timePickerMinute.getValue() < 10) {
				
				add = "0";
				
			}
	
			String dateTime = date.getValue().toString() + " " + timePickerHour.getValue() + ":" + add + timePickerMinute.getValue();
			
			if(!entryModel.getStatus()) {
				entryModel.createEntry(title.getText(), dateTime, entry.getText());
			} else {
				System.out.println("Preparing to update entry.");
				entryModel.updateEntry(title.getText(), dateTime, entry.getText(),
						id);
			}
			
			((Node) event.getSource()).getScene().getWindow().hide();
			changeScene("HomeView.fxml");

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * Sets a value that represents whether an entry is being updated or created
	 * 
	 * @param isBeingUpdated whether the entry is being updated or not
	 */
	public void setStatus(boolean isBeingUpdated) {
		entryModel.setStatus(isBeingUpdated);
	}
	
	/**
	 * Populates the Entry view with the journal entry that was found via search
	 * 
	 * @param foundEntry
	 */
	public void populate(JournalEntry foundEntry) {
		id = foundEntry.getId();
		title.setText(foundEntry.getTitle());

		String entryDate = foundEntry.getDate().split(" ")[0].trim();
		String entryTime = foundEntry.getDate().split(" ")[1].trim();
		
		System.out.println(entryDate);
		System.out.println(entryTime);

		date.setValue(LocalDate.parse(entryDate));
		timePickerHour.getValueFactory().setValue(Integer.valueOf(entryTime.split(":")[0]));
		timePickerMinute.getValueFactory().setValue(Integer.valueOf(entryTime.split(":")[1]));
		
		entry.setText(foundEntry.getEntry());

	}

}
