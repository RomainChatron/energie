package org.energie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import com.mysql.jdbc.Statement;

public class RequeteSQL {

	private static Connection connection = null;

	public static Connection connectDB() {

		String url = "jdbc:mysql://localhost:3306/energie?useSSL=false";
		String login = "root";
		String pwd = "WhiteTiger1M";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (DriverManager.getConnection(url, login, pwd));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return connection;

	}

	public void closeDB(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int insertAppliance(Connection connection, Map<String, String> datas) {
		String sql = "INSERT INTO appliance_house (household, appliance, project) VALUES(?,?,?)";
		int id = 0;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, datas.get("household"));
			stmt.setString(2, datas.get("appliance"));
			stmt.setString(3, datas.get("project"));

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static void insertMesure(Connection connection, int state, int energy, Date date, int fk) {
		String sql = "INSERT INTO mesure (date_time_mesure, state, energy, FK_appliance) VALUES(?,?,?,?)";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setTimestamp(1, new java.sql.Timestamp(date.getTime()));
			stmt.setInt(2, state);
			stmt.setInt(3, energy);
			stmt.setInt(4, fk);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connection) {
		RequeteSQL.connection = connection;
	}

}