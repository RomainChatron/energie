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
		System.out.println("Load CSV");
		LoadCSV load = new LoadCSV();
		String path = "filesTest";
		File fi = new File(path);
		load.loadCSV(fi);
		Instant end = Instant.now();
		System.out.println(Duration.between(start, end).toMillis());
	}

}
