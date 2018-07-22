package org.energie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Strategy1FilDeLeau implements IStrategy {

	@Override
	public void execute(String file) {
		try (Stream<String> stream = Files.lines(Paths.get(file))) {
			stream.forEach((str) -> {
				String[] tokens;
				tokens = str.split("\t");
				String project = tokens[0].trim();
				String household = tokens[1].trim();
				String appliance = tokens[2].trim();
				String dateS = tokens[3] + " " + tokens[4];
				int state = Integer.parseInt(tokens[5]);
				int energy = Integer.parseInt(tokens[6]);
				int idAppliance = RequeteSQL.findAppliance(connection, project, household, appliance);
				Map<String, String> datas = new HashMap<>();
				datas.put("project", project);
				datas.put("project", household);
				datas.put("project", appliance);
				if (idAppliance == 0) {
					idAppliance = RequeteSQL.insertAppliance(connection, datas);
				}
				Date dateD = null;
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
				try {
					dateD = formatter.parse(dateS);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (energy != 0) {
					RequeteSQL.insertMesure(connection, state, energy, dateD, idAppliance);
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
