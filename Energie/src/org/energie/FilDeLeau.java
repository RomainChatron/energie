package org.energie;

import java.io.File;
import java.sql.Connection;
import java.time.Instant;

public class FilDeLeau {

	public static void main(String[] args) {
		Connection connection = RequeteSQL.connectDB();
		Instant start = Instant.now();
		LoadCSV load = new LoadCSV();

		String path = "filesFilDeLeau";
		File fi = new File(path);
		while (true) {
			load.loadDataCSV(fi, false);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
