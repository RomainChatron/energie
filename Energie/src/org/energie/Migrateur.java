package org.energie;

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

		IStrategy strategy = new Strategy1();
		strategy.execute();

		


		Instant end = Instant.now();
		System.out.println(Duration.between(start, end).toMillis());

	}
	
	
	
	
	


}
