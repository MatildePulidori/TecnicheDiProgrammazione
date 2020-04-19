package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	private final Map<Integer, Country> mappaCountries = new TreeMap<Integer, Country>();
	
	
	/**
	 * Metodo che interroga il database e trova tutti gli stati dal DB
	 * @return
	 */
	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Country(rs.getString("StateAbb"), rs.getInt("ccode"), rs.getString("StateNme")));
				mappaCountries.put(rs.getInt("ccode"), new Country(rs.getString("StateAbb"), rs.getInt("ccode"), rs.getString("StateNme")));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	/**
	 * Metodo che trova tutte le coppie di stati confinanti nel DB
	 * @param anno
	 * @return
	 */
	public List<Border> getCountryPairs(int anno) {
		String sql = "SELECT * FROM contiguity WHERE year<= ? AND conttype='1'";
		List<Border> confini = new ArrayList<Border>();
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				Country countryA = this.mappaCountries.get(rs.getInt("state1no"));
				Country countryB = this.mappaCountries.get(rs.getInt("state2no"));
				confini.add(new Border(countryA, countryB, rs.getInt("year"), rs.getInt("conttype")));
			}
			conn.close();
			return confini;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database") ;
		}
		
	}

	
	/**
	 * Metodo che da tutti i confini di tipo 1 del DB 
	 * @return
	 */
	public List<Border> getCountryPairs() {
		String sql = "SELECT * FROM contiguity WHERE conttype='1'";
		List<Border> confini = new ArrayList<Border>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				Country countryA = this.mappaCountries.get(rs.getInt("state1no"));
				Country countryB = this.mappaCountries.get(rs.getInt("state2no"));
				confini.add(new Border(countryA, countryB, rs.getInt("year"), rs.getInt("conttype")));
			}
			conn.close();
			return confini;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database") ;
		}
	}
}
