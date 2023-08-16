package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JournalEntryModel {

	Connection connection;
	private boolean isBeingUpdated = false;
	private int ID;

	/**
	 * This constructor attempts to connect to database and exits the application if
	 * the connection cannot be made.
	 * 
	 */
	public JournalEntryModel() {

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
	 * This method checks if an entry in the database is being updated
	 * 
	 * @return true if an entry is being updated, otherwise false
	 */
	public boolean getStatus() {
		return isBeingUpdated;
	}

	/**
	 * This method sets the status for if any entry in the database is being updated
	 * 
	 * @param b The specified status for if an entry is being updated
	 */
	public void setStatus(boolean b) {
		isBeingUpdated = b;
	}

	/**
	 * This method accesses the id of an entry that will be modified
	 * 
	 * @return ID The id of the entry to be modified
	 */
	public int getIdModel() {
		return ID;
	}

	/**
	 * This method sets the id of an entry that will be modified
	 * 
	 * @param id The specified id of the entry that will be modified
	 */
	public void setIdModel(int id) {
		ID = id;
	}

	/**
	 * This method creates a journal entry in the database.
	 * 
	 * @param title The title of the journal entry
	 * @param date  The date of the journal entry
	 * @param entry The contents of the journal entry
	 */
	public void createEntry(String title, String date, String entry) {

		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO entries (title, date, entry) VALUES (?, ?, ?)";

		try {

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, title);
			preparedStatement.setString(2, date);
			preparedStatement.setString(3, entry);

			preparedStatement.execute();
			preparedStatement.close();

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	/**
	 * Returns a list of Journal Entries that each have a title or entry which contains the 
	 * search string
	 * 
	 * @param search the string that is being searched for
	 * @return a list of journal entries matching the search string
	 */
	public List<JournalEntry> getEntryByContents(String search) {

		List<JournalEntry> results = new ArrayList<>();

		for (JournalEntry j : getAllEntries()) {

			if (j.getTitle().contains(search) || j.getEntry().contains(search)) {

				results.add(j);

			}

		}

		return results;

	}

	/*
	 * This method fetches all journal entries from the database
	 * 
	 * @return A list containing all the journal entries from the database.
	 */
	public List<JournalEntry> getAllEntries() {
		List<JournalEntry> allEntries = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM entries";

		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String date = resultSet.getString("date");
				String entry = resultSet.getString("entry");
				allEntries.add(new JournalEntry(id, title, date, entry));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return allEntries;
	}

	/**
	 * This method updates an entry from the database based on its unique id
	 * 
	 * @param title   The title of the journal entry
	 * @param date    The date of the journal entry
	 * @param entry   The contents of the journal entry
	 * @param entryId The unique identifier of the entry to be updated
	 * @return true if the database is changed, otherwise false
	 */
	public boolean updateEntry(String title, String date, String entry, int entryId) {
		PreparedStatement preparedStatement = null;
		String query = "UPDATE entries SET title = ?, date = ?, entry = ? WHERE id = ?";

		try {

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, title);
			preparedStatement.setString(2, date);
			preparedStatement.setString(3, entry);
			preparedStatement.setInt(4, entryId);

			int entriesAffected = preparedStatement.executeUpdate();

			return entriesAffected > 0;

		} catch (SQLException e) {

			e.printStackTrace();
			return false;

		}
	}

	/**
	 * This method deletes an entry from "entries" table in the database based on
	 * its unique id
	 * 
	 * @param entryId The unique identifier of the entry to be deleted
	 * @return true if the entry was successfully deleted, otherwise false
	 */
	public boolean deleteEntry(int entryId) {
		PreparedStatement preparedStatement = null;
		String query = "DELETE FROM entries WHERE id = ?";

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, entryId);

			// Execute DELETE query and get the number of entries affected
			int entriesAffected = preparedStatement.executeUpdate();

			// Return true if at least one entry was deleted
			return entriesAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
