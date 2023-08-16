package application;

import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class SceneController {
	
	public JournalEntryModel entryModel = new JournalEntryModel();

	/**
     * A method that changes the view to the provided fxml file
     * 
     * @param xmlPath the fxml filepath that contains the view to change to
     * @throws Exception
     */
    public void changeScene(String xmlPath) throws Exception {
    	
    	String filePath = "/application/" + xmlPath;
    	
    	Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(filePath).openStream());
		Scene scene = new Scene(root, 600, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
    }

}
