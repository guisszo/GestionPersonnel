package com.iam.gestionpersonnel.dao;

/*
 * 
 * @author Matar THIOYE
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iam.gestionpersonnel.model.Personnel;

public class PersonnelDAO {

	private String jdbcURL = "jdbc:mysql://localhost:3306/personneldb?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";

	private static final String INSERT_USERS_SQL = "INSERT INTO personnels" + "  (nom, email, pays) VALUES "
			+ " (?, ?, ?);";

	private static final String SELECT_USER_BY_ID = "select id,nom,email,pays from personnels where id =?";
	private static final String SELECT_ALL_USERS = "select * from personnels";
	private static final String DELETE_USERS_SQL = "delete from personnels where id = ?;";
	private static final String UPDATE_USERS_SQL = "update personnels set nom = ?,email= ?, pays =? where id = ?;";

	public PersonnelDAO() {
	}

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public void insertUser(Personnel user) throws SQLException {
		System.out.println(INSERT_USERS_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setString(1, user.getNom());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPays());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Personnel selectUser(int id) {
		Personnel user = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String nom = rs.getString("nom");
				String email = rs.getString("email");
				String pays = rs.getString("pays");
				user = new Personnel(id, nom, email, pays);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}

	public List<Personnel> selectAllUsers() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Personnel> users = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String nom = rs.getString("nom");
				String email = rs.getString("email");
				String pays = rs.getString("pays");
				users.add(new Personnel(id, nom, email, pays));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;
	}

	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateUser(Personnel user) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
			System.out.println("updated personnel:" + statement);
			statement.setString(1, user.getNom());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPays());
			statement.setInt(4, user.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	public List<Personnel> searchPerson(String nom, String email, String pays) {
		List<Personnel> users = new ArrayList<>();
		int indexNom = 0, i = 0, indexEmail = 0, indexPays = 0;
		try {
			String req = "select id,nom,email,pays from personnels where 1=1";
			// Step 1: Establishing a Connection

			Connection connection = getConnection();
			System.out.println(email);
			if (nom != "") {
				req = req + " and nom like ?";
				indexNom = i + 1;
				i = i + 1;
			}
			if (email != "") {
				req = req + " and email like ?";
				indexEmail = i + 1;
				i = i + 1;
			}
			if (pays != "") {
				req = req + " and pays like ?";
				indexPays = i + 1;
				i = i + 1;
			}
			// Step 2:Create a statement using connection object

			PreparedStatement preparedStatement = connection.prepareStatement(req);
			System.out.println("gvh" + preparedStatement);

			if (nom != "") {
				preparedStatement.setString(indexNom, "%" + nom + "%");
			}
			if (email != "") {
				preparedStatement.setString(indexEmail, "%" + email + "%");
			}
			if (pays != "") {
				preparedStatement.setString(indexPays, "%" + pays + "%");
			}
			System.out.println(preparedStatement);
// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String name = rs.getString("nom");
				String mail = rs.getString("email");
				String country = rs.getString("pays");
				int id = rs.getInt("id");
				users.add(new Personnel(id, name, mail, country));
			}

		} catch (SQLException e) {
			printSQLException(e);
		}
		System.out.println(users);
		return users;
	}
}
