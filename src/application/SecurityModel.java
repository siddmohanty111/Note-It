package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecurityModel {

	Connection connection;

	/**
	 * This constructor attempts to connect to database and exits the application if
	 * the connection cannot be made.
	 * 
	 */
	public SecurityModel() {
		connection = SqliteConnection.Connector();
		if (connection == null) {
			System.out.println("Connection not successful.");
			System.exit(1);
		}
	}

	/**
	 * This method checks if the model is still connected to the database.
	 * 
	 * @return true if connected, otherwise false
	 */
	public boolean isDbConnected() {
		try {
			return !connection.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
     * This method stores the provided credentials in the database and updates the
     * password, security question, and security answer for the default user.
     * 
     * @param password         The new password that is going to be stored in the
     *                         database
     * @param securityQuestion The security question associated with the user's
     *                         account
     * @param securityAnswer   The answer to the security question associated with
     *                         the user's account
     * @param oldPassword	   The old password the the user has been using
     *                         
     */
	public void storeCredentials(String password, String securityQuestion, String securityAnswer, String oldPassword) {
		PreparedStatement preparedStatement = null;
		String query = "UPDATE security SET password = ?, securityQuestion = ?, securityAnswer = ? WHERE password = ?";

		try {
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, password);
			preparedStatement.setString(2, securityQuestion);
			preparedStatement.setString(3, securityAnswer);
			preparedStatement.setString(4, oldPassword);

			preparedStatement.execute();

			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
     * This method retrieves the security question associated with the user's
     * account.
     * 
     * @return The security question message
     */
	public String getSecurityQuestion() {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT securityQuestion FROM security";

		try {
			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();

			String securityQuestion = resultSet.getString("securityQuestion");
			return securityQuestion;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * This method retrieves the security answer associated with the user's
     * account.
     * 
     * @return The security answer saved in database
     */
	public String getSecurityAnswer() {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT securityAnswer FROM security";

		try {
			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();

			String securityQuestion = resultSet.getString("securityAnswer");
			return securityQuestion;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
     * This method updates the password for the default user in the database.
     * 
     * @param password The new password saved in the database
     */
	public void saveNewPassword(String password) {
		PreparedStatement preparedStatement = null;
		String query = "UPDATE security SET password = ?";

		try {
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, password);

			preparedStatement.execute();

			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * This method checks if the provided security answer matches stored answer in
     * database.
     * 
     * @param answerEntry The security asnwer checked
     * @return true if the provided security answer macthes the one stored in the database
     * @throws SQLException
     */
	public boolean isCorrectSecurityAnswer(String answerEntry) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM security WHERE securityAnswer = ?";

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, answerEntry);

			resultSet = preparedStatement.executeQuery();
			return resultSet.next(); // Return true if there is a matching result

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}
}
