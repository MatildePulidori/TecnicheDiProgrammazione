package it.polito.tdp.anagrammi.DAO;

import java.sql.*;

public class AnagrammaDAO {
	
	public boolean isCorrect(String anagramma) {
		boolean isCorrect;
		final String sql = "SELECT nome "+ 
				"FROM parola "+ 
				"WHERE (nome = ? )";	
		try {
			Connection conn = ConnectDataBase.getConnection();
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1,  anagramma.toString());
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				isCorrect = true;
			}
			else {
				isCorrect = false;
			}
			conn.close();
			return isCorrect;
			
		}
		catch (SQLException sqle) {
			throw new RuntimeException("Errore Db");
		}

	}
	
}
