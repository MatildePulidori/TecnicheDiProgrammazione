package it.polito.tdp.meteo.db;

import java.sql.*;
import java.time.Month;
import java.util.*;
import it.polito.tdp.meteo.bean.*;

public class MeteoDAO {

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(Month m, String localita) {
		final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE (Localita = ? && MONTH(Data) = ?) ORDER BY data ASC";
		
		List<Rilevamento> rilevamentiLocalitaMese = new ArrayList<Rilevamento>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, localita);
			st.setInt(2, m.getValue());
			
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamentiLocalitaMese.add(r);
			}

			conn.close();
			return rilevamentiLocalitaMese;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Double getAvgRilevamentiLocalitaMese(Month mese, Citta citta) {
		final String sql = "SELECT Localita, AVG(Umidita) as umiditaMedia FROM situazione WHERE (MONTH(Data) = ? && Localita = ?) GROUP BY Localita";
		
		try {
			double umiditaMedia = 0.0;
			
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese.getValue());
			st.setString(2, citta.getNome());
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				umiditaMedia = rs.getDouble("umiditaMedia");
			}
			
			conn.close();
			return umiditaMedia;
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Citta> getCities() {
		
		final String sql = "SELECT DISTINCT Localita FROM situazione"; 
		
		List<Citta> cittaMeteo = new ArrayList<Citta>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Citta citta = new Citta(rs.getString("Localita"));
				cittaMeteo.add(citta);
			}
			
			conn.close();
			return cittaMeteo;
		}
		catch(SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
