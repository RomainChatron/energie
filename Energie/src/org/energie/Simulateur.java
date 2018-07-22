package org.energie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulateur {

	public static void main(String[] args) {
		System.out.println("Début de simulation");
		String[] time = new String[] { "00:00", "00:10", "00:20", "00:30", "00:40", "00:50", "01:00", "01:10", "01:20",
				"01:30", "01:40", "01:50", "02:00", "02:10", "02:20", "02:30", "02:40", "02:50", "03:00", "03:10",
				"03:20", "03:30", "03:40", "03:50", "04:00", "04:10", "04:20", "04:30", "04:40", "04:50", "05:00",
				"05:10", "05:20", "05:30", "05:40", "05:50", "06:00", "06:10", "06:20", "06:30", "06:40", "06:50",
				"07:00", "07:10", "07:20", "07:30", "07:40", "07:50", "08:00", "08:10", "08:20", "08:30", "08:40",
				"08:50", "09:00", "09:10", "09:20", "09:30", "09:40", "09:50", "10:00", "10:10", "10:20", "10:30",
				"10:40", "10:50", "11:00", "11:10", "11:20", "11:30", "11:40", "11:50", "12:00", "12:10", "12:20",
				"12:30", "12:40", "12:50", "13:00", "13:10", "13:20", "13:30", "13:40", "13:50", "14:00", "14:10",
				"14:20", "14:30", "14:40", "14:50", "15:00", "15:10", "15:20", "15:30", "15:40", "15:50", "16:00",
				"16:10", "16:20", "16:30", "16:40", "16:50", "17:00", "17:10", "17:20", "17:30", "17:40", "17:50",
				"18:00", "18:10", "18:20", "18:30", "18:40", "18:50", "19:00", "19:10", "19:20", "19:30", "19:40",
				"19:50", "20:00", "20:10", "20:20", "20:30", "20:40", "20:50", "21:00", "21:10", "21:20", "21:30",
				"21:40", "21:50", "22:00", "22:10", "22:20", "22:30", "22:40", "22:50", "23:00", "23:10", "23:20",
				"23:30", "23:40", "23:50" };

		String[] date = new String[] { "01/06/18", "02/06/18", "03/06/18", "04/06/18", "05/06/18", "06/06/18",
				"07/06/18", "08/06/18", "09/06/18", "10/06/18", "11/06/18", "12/06/18", "13/06/18", "14/06/18",
				"15/06/18", "16/06/18", "17/06/18", "18/06/18", "19/06/18", "20/06/18", "21/06/18", "22/06/18",
				"23/06/18", "24/06/18", "25/06/18", "26/06/18", "27/06/18", "28/06/18", "29/06/18", "30/06/18", };

		Random binary = new Random();
		int j = 0;
		for (int i = 0; true; i++) {
			Path file = Paths.get("filesFilDeLeau/data" + i);

			List<String> appliances = new ArrayList<>();
			appliances.add("Irise (France)	2000900	Fridge (Kitchen, 180l)	" + date[j] + "	" + time[i % 144]
					+ "	0	0000" + (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000900	Vertical freezer (Cellar, 145l)	" + date[j] + "	" + time[i % 144]
					+ "	0	0000" + (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000900	Washing machine ()	" + date[j] + "	" + time[i % 144]
					+ "	0	0000" + (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000900	Total site light consumption ()	" + date[j] + "	" + time[i % 144]
					+ "	0	0000" + (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000900	Halogen lamp 1 (0,5kW)	" + date[j] + "	" + time[i % 144]
					+ "	0	0000" + (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000900	Power supply for wood boiler ()	" + date[j] + "	" + time[i % 144]
					+ "	0	0000" + (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000900	Site consumption ()	" + date[j] + "	" + time[i % 144]
					+ "	0	0000" + (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000900	Water heater ()	" + date[j] + "	" + time[i % 144] + "	0	0000"
					+ (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000901	Fridge (Kitchen, 200l)	" + date[j] + "	" + time[i % 144]
					+ "	0	0000" + (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000901	Chest freezer (Kitchen, 200l)	" + date[j] + "	" + time[i % 144]
					+ "	0	0000" + (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000901	Washing machine (4,5kg, 2,2kW)	" + date[j] + "	" + time[i % 144]
					+ "	0	0000" + (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000901	Dish washer (12 place setting)	" + date[j] + "	" + time[i % 144]
					+ "	0	0000" + (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000901	Clothes drier (without condenser) (3,2kW)	" + date[j] + "	"
					+ time[i % 144] + "	0	0000" + (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000901	TV (67cm)	" + date[j] + "	" + time[i % 144] + "	0	0000"
					+ (binary.nextBoolean() ? binary.nextInt(15) : "00"));
			appliances.add("Irise (France)	2000901	Total site light consumption ()	" + date[j] + "	" + time[i % 144]
					+ "	0	0000" + (binary.nextBoolean() ? binary.nextInt(15) : "00"));

			try {
				if (i != 0 && i % 144 == 0) {
					j++;
				}

				Files.write(file, appliances);
				System.out.println(file.getFileName() + " i = " + i + " j = " + j);
				if (j == 30) {
					System.out.println("Fin de simulation");
					System.exit(0);
				}
				Thread.sleep(10000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
