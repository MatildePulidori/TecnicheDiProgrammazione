package it.polito.tdp.anagrammi.DAO;

import java.sql.*;

public class ConnectDataBase {
	
	static private final String jdbcUrl = "jdbc:mysql://localhost/dizionario?user=root";
	static private Connection connection = null;
	
	public static Connection getConnection() {
		
		try {
			if (connection== null || connection.isClosed()) {
				connection = DriverManager.getConnection(jdbcUrl);
			}
			
			return connection;
		}
		catch (SQLException sqle) {
			throw new RuntimeException("Cannot get a connection " + jdbcUrl, sqle);
		}
	}
}
