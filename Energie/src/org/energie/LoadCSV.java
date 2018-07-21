package org.energie;

import java.io.File;

public class LoadCSV {
	public void loadCSV(File fileName) {
		for (final File fileEntry : fileName.listFiles()) {

			if (fileEntry.isDirectory()) {
				loadCSV(fileEntry);
			} else {
				String chemin = fileName + "\\" + fileEntry.getName();
				IStrategy strategy = new Strategy1();
				System.out.println(chemin);
				strategy.execute(chemin);
			}
		}
	}
}
