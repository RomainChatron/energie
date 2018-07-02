package org.energie;

import java.sql.Connection;

public interface IStrategy {

	Connection connection = RequeteSQL.getConnection();

	public void execute();

}
