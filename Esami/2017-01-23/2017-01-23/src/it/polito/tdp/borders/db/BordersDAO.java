package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.Contiguity;
import it.polito.tdp.borders.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BordersDAO {
	
	public List<Country> loadAllCountries() {
		
		String sql = 
				"SELECT ccode,StateAbb,StateNme " +
				"FROM country " +
				"ORDER BY StateAbb " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				Country c = new Country(
						rs.getInt("ccode"),
						rs.getString("StateAbb"), 
						rs.getString("StateNme")) ;
				
				list.add(c) ;
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
	
	public List<Contiguity>  getConfini(Year year){
		String sql = "select state1no, state1ab, state2no, state2ab, year\n" + 
				"from contiguity\n" + 
				"where year<= ?\n" + 
				"and conttype = 1\n" + 
				"and state1no<state2no\n" + 
				"order by year";
		List<Contiguity> contiguities = new ArrayList<>();
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, year.getValue());
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) {
				Contiguity contiguity = new Contiguity(rs.getInt("state1no"), rs.getInt("state2no"));
				contiguities.add(contiguity);
			}
			
			conn.close();
			return contiguities;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}


	public List<Country> geCountriesFromAnno(Year year) {
		String sql = "select distinct ccode, stateAbb, stateNme\n" + 
				"from country, contiguity\n" + 
				"where (ccode = state1no\n" + 
				"or ccode = state2no) \n" + 
				"and conttype =1\n" + 
				"and year <= ?\n"+
				"order by ccode";
		
		List<Country> countries = new ArrayList<>();
		try {
			Connection conn=DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year.getValue());
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) {
				Country country = new Country(rs.getInt("ccode"), rs.getString("stateAbb"), rs.getString("stateNme"));
				countries.add(country);
			}
			conn.close();
			return countries;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
}
