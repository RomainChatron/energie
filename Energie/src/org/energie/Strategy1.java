package org.energie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.mysql.jdbc.StringUtils;

public class Strategy1 implements IStrategy {

	Map<String, String> datas = new HashMap<>();
	Map<String, Integer> id = new HashMap<>();

	public void execute(String file) {
		try (Stream<String> stream = Files.lines(Paths.get(file))) {
			stream.forEach((str) -> {
				String[] tokens;
				tokens = str.split("\t");
				if (str.length() != 0 && !StringUtils.isStrictlyNumeric(str.substring(0, 2))) {
					// it does not start with 2 number -> header
					String[] head;
					head = tokens[0].split(":");
					if (head[0].trim().equalsIgnoreCase("PROJECT")) {
						datas.put("project", head[1].trim());
					} else if (head[0].trim().equalsIgnoreCase("HOUSEHOLD")) {
						datas.put("household", head[1].trim());
					} else if (head[0].trim().equalsIgnoreCase("APPLIANCE")) {
						datas.put("appliance", head[1].trim());
						id.put("id", RequeteSQL.insertAppliance(connection, datas));
					}
				} else if (str.length() != 0 && StringUtils.isStrictlyNumeric(str.substring(0, 2))) {
					// start with 2 number -> mesure datas
					String dateS = tokens[0] + " " + tokens[1];
					Date dateD = null;
					int state = Integer.parseInt(tokens[2]);
					int energy = Integer.parseInt(tokens[3]);
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
					try {
						dateD = formatter.parse(dateS);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (energy != 0) {
						RequeteSQL.insertMesure(connection, state, energy, dateD, id.get("id"));
					}
				}

			});
			connection.commit();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}