package org.energie;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

public class LoadCSV {
	public void loadDataCSV(File fileName, boolean init) {
		for (final File fileEntry : fileName.listFiles()) {

			if (fileEntry.isDirectory()) {
				loadDataCSV(fileEntry, init);
			} else if (init) {
				Instant start = Instant.now();
				String chemin = fileName + "\\" + fileEntry.getName();
				IStrategy strategy = new Strategy1Migration();
				strategy.execute(chemin);
				Instant end = Instant.now();
				System.out.println(Duration.between(start, end).toMillis() + " \t " + chemin);
			} else if (!init) {
				Instant start = Instant.now();
				String chemin = fileName + "\\" + fileEntry.getName();
				IStrategy strategy = new Strategy1FilDeLeau();
				strategy.execute(chemin);
				Instant end = Instant.now();
				System.out.println(Duration.between(start, end).toMillis() + " \t " + chemin);
				new File(chemin).delete();
			}
		}
	}
}
