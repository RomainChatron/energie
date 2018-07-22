package org.energie;

import java.sql.Connection;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class BenchRequete {

	public static void main(String[] args) {
		Connection connection = RequeteSQL.connectDB();
		System.out.println("Temps\tRequete\t\tResultat");
		Instant start = Instant.now();
		int req1 = RequeteSQL.getConsommationAppliance(connection, "Fridge", "22/01/98 00:01");
		Instant after1 = Instant.now();
		System.out.println(Duration.between(start, after1).toMillis() + "\tRequete 1\t" + req1);
		int req2 = RequeteSQL.getConsommationMaison(connection, "2000900", "22/01/98 15:00");
		Instant after2 = Instant.now();
		System.out.println(Duration.between(after1, after2).toMillis() + "\tRequete 2\t" + req2);
		Map<String, Object> req3 = RequeteSQL.getApplianceMaxConsommation(connection, "01/03/98 00:00");
		Instant after3 = Instant.now();
		System.out.println(Duration.between(after2, after3).toMillis() + "\tRequete 3\t" + req3.get("energy") + " - "
				+ req3.get("equipement"));
	}

}
