package org.energie;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

public class Migrateur {

	public static void main(String[] args) {
		Connection connection = RequeteSQL.connectDB();
		Instant start = Instant.now();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Instant beforeReset = Instant.now();
		RequeteSQL.resetTable(connection);
		Instant afterReset = Instant.now();
		System.out.println(Duration.between(beforeReset, afterReset).toMillis() + "\t Reset Table");
		System.out.println("Start Loading Datas");
		LoadCSV load = new LoadCSV();
		String path = "filesTest";
		File fi = new File(path);
		load.loadDataCSV(fi, true);
		Instant end = Instant.now();
		System.out.println(Duration.between(afterReset, end).toMillis() + " \t Load All Files");
		System.out.println(Duration.between(start, end).toMillis() + " \t Total");

	}

}
