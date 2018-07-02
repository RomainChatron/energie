package org.energie;

import java.io.File;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;

public class LoadCSV {
	public void loadCSV(File fileName) {
		int i = 0;
		for (final File fileEntry : fileName.listFiles()) {

			if (i < 1) {
				if (fileEntry.isDirectory()) {
					loadCSV(fileEntry);
				} else {
					//this.readFileCSV(new File(folderPath + "\\" + fileEntry.getName()));
					String chemin = fileName +"\\"+fileEntry.getName();
					IStrategy strategy = new Strategy1();
					System.out.println(chemin);
					strategy.execute(chemin);
					//System.out.println(fileEntry.getName());
				}
			}
			i++;
		}

	}

	/*public void readFileCSV(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}*/
}
