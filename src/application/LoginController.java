package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController extends SceneController implements Initializable {
	public LoginModel loginModel = new LoginModel();

	@FXML
	private Label isConnected;

	@FXML
	private TextField passwordEntry;

	@FXML
	private Hyperlink forgotPasswordHyperlink;

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
		// TODO Auto-generated method stub
		if (loginModel.isDbConnected()) {
			isConnected.setText("Connected");
		} else {
			isConnected.setText("Not Connected");
		}
	}
	
	/**
	 * When the login button of the GUI is clicked, the app verifies whether the
	 * entered password is correct. If so, app moves to the home page, otherwise,
	 * prompts for re-entry of password.
	 * 
	 * For first time logging in, the app will redirect to password change screen.
	 * 
	 * @param event The action of clicking on the login button
	 * @throws IOException
	 */
	public void Login(ActionEvent event) throws IOException {
		try {
			boolean passCorrect = loginModel.passwordCheck(passwordEntry.getText());
			System.out.println(passCorrect);
			System.out.println(passwordEntry.getText());
			
			if (passCorrect) {
				((Node) event.getSource()).getScene().getWindow().hide(); // hides login window

				if (loginModel.isFirstTimeLogin(passwordEntry.getText())) {
					
					changeScene("SecurityView.fxml");

				} else {					

					changeScene("HomeView.fxml");

				}
			} else {
				passwordEntry.setText("");
				isConnected.setText("Password is not correct. Try again.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * When the "forgot password" hyperlink is clicked, the app will redirect you to
	 * the forgot password screen.
	 * 
	 * @param event The action of clicking on the "forgot password" hyperlink
	 */
	public void ForgotPassword(ActionEvent event) {

		try {
			((Node) event.getSource()).getScene().getWindow().hide(); // hides login window

			changeScene("ForgotPassView.fxml");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
