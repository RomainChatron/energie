package org.energie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.Statement;

public class RequeteSQL {

	private static Connection connection = null;

	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connection) {
		RequeteSQL.connection = connection;
	}

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

	public static int findAppliance(Connection connection, String project, String household, String appliance) {
		String sql = "SELECT id FROM appliance_house WHERE project = ? AND household = ? AND appliance = ?";
		int id = 0;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, project);
			stmt.setString(2, household);
			stmt.setString(3, appliance);

			ResultSet rs = stmt.executeQuery();
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

	public static void resetTable(Connection connection) {
		String dropMesure = "DROP TABLE IF EXISTS mesure";
		String dropAppliance_house = "DROP TABLE IF EXISTS appliance_house";
		String createTableMesure = "CREATE TABLE appliance_house (id INT(3) UNSIGNED PRIMARY KEY AUTO_INCREMENT, household INT(8) UNSIGNED NOT NULL, appliance VARCHAR(48) NOT NULL, project VARCHAR(32) NOT NULL)";
		String createTableAppliance_house = "CREATE TABLE mesure (id INT(6) UNSIGNED PRIMARY KEY AUTO_INCREMENT, date_time_mesure DATETIME NOT NULL, state int(4) UNSIGNED NOT NULL, energy int(8) UNSIGNED NOT NULL, FK_appliance int(3) UNSIGNED, CONSTRAINT FK_mesure_appliance FOREIGN KEY (FK_appliance) REFERENCES appliance_house (id))";

		try {
			PreparedStatement stmtDropMesure = connection.prepareStatement(dropMesure);
			PreparedStatement stmtDropAppliance_house = connection.prepareStatement(dropAppliance_house);
			PreparedStatement stmtCreateTableMesure = connection.prepareStatement(createTableMesure);
			PreparedStatement stmtCreateTableAppliance_house = connection.prepareStatement(createTableAppliance_house);
			stmtDropMesure.execute();
			stmtDropAppliance_house.execute();
			stmtCreateTableMesure.execute();
			stmtCreateTableAppliance_house.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void createDateTimeIndex(Connection connection) {
		String indexSQL = "ALTER TABLE energie.mesure ADD INDEX dateTimeIndex(date_time_mesure)";

		try {
			PreparedStatement stmt = connection.prepareStatement(indexSQL);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int getConsommationAppliance(Connection connection, String appliance, String dateJour) {
		int result = 0;
		String req = "SELECT sum(energy) FROM mesure JOIN appliance_house ON FK_appliance = appliance_house.id WHERE appliance LIKE ? AND date_time_mesure between ? AND ?";

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Date date = formatter.parse(dateJour);
			Date date2 = new Date();
			date2.setTime(date.getTime() + (60000 * 60 * 24) - 60000);

			PreparedStatement stmt = connection.prepareStatement(req);
			stmt.setString(1, appliance + "%");
			stmt.setString(2, formatter2.format(date));
			stmt.setString(3, formatter2.format(date2));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int getConsommationMaison(Connection connection, String household, String dateTime) {
		int result = 0;
		String req = "SELECT sum(energy) FROM mesure JOIN appliance_house ON FK_appliance = appliance_house.id WHERE household = ? AND date_time_mesure between ? AND ?";

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Date date = formatter.parse(dateTime);
			Date date2 = new Date();
			date2.setTime(date.getTime() - (60000 * 59));

			PreparedStatement stmt = connection.prepareStatement(req);
			stmt.setString(1, household);
			stmt.setString(2, formatter2.format(date2));
			stmt.setString(3, formatter2.format(date));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Map<String, Object> getApplianceMaxConsommation(Connection connection, String dateTime) {
		Map<String, Object> result = new HashMap<>();
		String req = "SELECT max(sumEnergy), nomEquipement " + "FROM ("
				+ "SELECT sum(energy) as sumEnergy, appliance as nomEquipement "
				+ "FROM mesure JOIN appliance_house ON FK_appliance = appliance_house.id "
				+ "WHERE date_time_mesure BETWEEN DATE_ADD(?, INTERVAL - 1 MONTH) AND ? "
				+ "GROUP BY FK_appliance) as temp";

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Date date = formatter.parse(dateTime);

			PreparedStatement stmt = connection.prepareStatement(req);
			stmt.setString(1, formatter2.format(date));
			stmt.setString(2, formatter2.format(date));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result.put("energy", rs.getInt(1));
				result.put("equipement", rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

}