package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SecurityController extends SceneController implements Initializable {

	public SecurityModel changePassModel = new SecurityModel();
	public LoginModel loginModel = new LoginModel();
	
	@FXML
	private TextField oldPasswordField;

	@FXML
	private TextField newPasswordField;

	@FXML
	private TextField confirmNewPasswordField;
	
	@FXML
	private Label oldPasswordWarning;

	@FXML
	private Label newPasswordWarning;

	@FXML
	private Label confirmPasswordWarning;

	@FXML
	private TextField securityQuestionField;

	@FXML
	private TextField securityAnswerField;

	@FXML
	private Label isConn;
	
	private String password;

	/**
	 * This initializes the controller and sets a label to display whether the
	 * database has been connected to or not.
	 * 
	 * @param location  The location used to resolve relative paths for root object,
	 *                  otherwise null
	 * @param resources The resources used to localize the root object, otherwise
	 *                  null
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (changePassModel.isDbConnected()) {
			isConn.setText("Connected");
		} else {
			isConn.setText("Not Connected");
		}
		
		password = getPassword();
	}
	
	/**
	 * Returns the password stored in the database
	 * 
	 * @return the password
	 */
	public String getPassword() {
		try {
			return loginModel.getPassword();
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * When the submit button of the GUI is clicked, the app switches to the login
	 * screen.
	 * 
	 * @param event The action of clicking the submit button
	 * @throws IOException
	 */
	public void Submit(ActionEvent event) throws IOException {

		try {
			if(!oldPasswordField.getText().equals(password)) {
				oldPasswordField.setText("");
				oldPasswordWarning.setText("Old password is not correct");
				newPasswordWarning.setText("");
				confirmPasswordWarning.setText("");
			}
			else if(newPasswordField.getText().equals("p")) {
				newPasswordField.setText("");
				newPasswordWarning.setText("Invalid password");
				oldPasswordWarning.setText("");
				confirmPasswordWarning.setText("");
			}
			else if(!newPasswordField.getText().equals(confirmNewPasswordField.getText())) {
				confirmNewPasswordField.setText("");
				confirmPasswordWarning.setText("Passwords do not match");
				oldPasswordWarning.setText("");
				newPasswordWarning.setText("");
			}
			else {
				changePassModel.storeCredentials(newPasswordField.getText(), securityQuestionField.getText(),
						securityAnswerField.getText(), oldPasswordField.getText());
				((Node) event.getSource()).getScene().getWindow().hide();
				changeScene("LoginView.fxml");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
