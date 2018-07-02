package org.energie;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

public class Migrateur {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = RequeteSQL.connectDB();
		Instant start = Instant.now();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//IStrategy strategy = new Strategy1();
		//strategy.execute();
		System.out.println("Load CSV");
		LoadCSV load = new LoadCSV();
		String path = "C:\\Users\\hpEnvy\\Desktop\\delex\\IRISE\\irise-toBeExtracted\\data\\Enertech\\Campagnes\\Remodece\\Travail\\Files";
		//String folder = "C:\\Users\\hpEnvy\\Desktop\\delex\\IRISE\\irise-toBeExtracted\\data\\Enertech\\Campagnes\\Remodece\\Travail";
		File fi = new File(path);
		load.loadCSV( fi);
		Instant end = Instant.now();
		System.out.println(Duration.between(start, end).toMillis());
	}

}
