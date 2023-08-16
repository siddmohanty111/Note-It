package application;

import java.sql.*;

public class LoginModel {

	Connection connection;

	/**
	 * This constructor attempts to connect to database and exits the application if
	 * the connection cannot be made.
	 * 
	 */
	public LoginModel() {
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
	 * gets the password from the database
	 * 
	 * @return the password
	 * @throws SQLException
	 */
	public String getPassword() throws SQLException {
		  PreparedStatement preparedStatement = null;
		  ResultSet resultSet = null;
		  String query = "SELECT * FROM security";

		   try {
		            preparedStatement = connection.prepareStatement(query);
		            resultSet = preparedStatement.executeQuery();

		            if(resultSet == null) {
		                return null;
		            }
		        
		            return resultSet.getString("password");
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		            if (resultSet != null) {
		                resultSet.close();
		            }
		            if (preparedStatement != null) {
		                preparedStatement.close();
		            }
		            
		            connection.close();
		        }
		
		return null;

	}
	
	/**
	 * Checks if the password provided matches the stored password
	 * 
	 * @param password the password being checked
	 * @return whether the provided password is correct
	 * @throws SQLException
	 */
	public boolean passwordCheck(String password) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM security WHERE password = ?";
		boolean doesMatch = false;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, password);

			resultSet = preparedStatement.executeQuery();
			
			if(resultSet == null) {
				//connection.close();
				return false;
			}
			
			doesMatch = password.equals(resultSet.getString("password"));
			System.out.println(resultSet.getString("password"));

			if(doesMatch) {
				connection.close();
			}
			
			return doesMatch; // Return true if there is a matching result

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

	
	/**
	 * This method check if the provided password is the default password
	 * 
	 * @param password the password that needs to be checked
	 * @return true if the provided password matches the default password, otherwise false
	 */
	public boolean isFirstTimeLogin(String password) {
		return password.equals("p");
	}

}
