package it.polito.tdp.dizionariograph.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {

	public List<String> getAllWordsFixedLength(int length) {

		String sql = "SELECT nome FROM parola WHERE LENGTH(nome) = ?;";
		List<String> parole = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, length);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				parole.add(res.getString("nome"));
			}

			return parole;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<String> getSimilarWords(String parola){
		String sql = "SELECT nome FROM parola WHERE nome LIKE ?" ;
		
		List<String> paroleSimili = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			for (int i=0; i<parola.length(); i++) {
				
				char[] x = parola.toCharArray();
				x[i]='_';
				String daCercare = new String (x);
				st.setString(1, daCercare);
			
				ResultSet res = st.executeQuery();

				while (res.next()) {
				paroleSimili.add(res.getString("nome"));
				}
			}
			conn.close();
			return paroleSimili;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database");
		}
	}

}
