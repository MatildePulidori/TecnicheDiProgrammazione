package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.formulaone.model.Circuit;
import it.polito.tdp.formulaone.model.Constructor;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.DriverIdMap;
import it.polito.tdp.formulaone.model.DriverSeasonResult;
import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Season;



public class FormulaOneDAO {
	
	Model model;
	
	public FormulaOneDAO(Model model) {
		this.model=model;
	}

	public List<Integer> getAllYearsOfRace() {
		
		String sql = "SELECT year FROM races ORDER BY year" ;
		
		try {
			Connection conn = ConnectDB.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Integer> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(rs.getInt("year"));
			}
			
			rs.close();
			conn.close();
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	
	
	public List<Season> getAllSeasons() {
		
		String sql = "SELECT year, url FROM seasons ORDER BY year" ;
		
		try {
			Connection conn = ConnectDB.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Season> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url"))) ;
			}
			
			rs.close();
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	

	public List<Driver> getAllDrivers() {
		String sql =  "SELECT driverId, driverRef, code, forename, surname, dob, nationality, url FROM drivers";
		
		try {
			Connection conn = ConnectDB.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Driver> list = new ArrayList<Driver>() ;
			while(rs.next()) {
			
				list.add(new Driver(rs.getInt("driverId"), rs.getString("forename"), rs.getString("surname"))) ;
			}
			
			rs.close();
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	 	
	public List<Circuit> getAllCircuits() {

		String sql = "SELECT circuitId, name FROM circuits ORDER BY name";

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Circuit> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Circuit(rs.getInt("circuitId"), rs.getString("name")));
			}

			
			rs.close();
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Constructor> getAllConstructors() {

		String sql = "SELECT constructorId, name FROM constructors ORDER BY name";

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Constructor> constructors = new ArrayList<>();
			while (rs.next()) {
				constructors.add(new Constructor(rs.getInt("constructorId"), rs.getString("name")));
			}

			rs.close();
			conn.close();
			return constructors;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}

	public List< Driver> getDriversOfSeason(Season season, DriverIdMap driverIdMap) {
		String sql =  "SELECT DISTINCT drivers.driverId, forename, surname\n" + 
				"FROM drivers, races, results\n" + 
				"WHERE races.year = ? \n" + 
				"AND results.raceId = races.raceId\n" + 
				"AND results.driverId =  drivers.driverId\n"
				+ "AND results.position IS NOT NULL";
		
		List<Driver> list = new ArrayList<Driver>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,	 season.getYear());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				list.add(driverIdMap.get(new Driver(rs.getInt("driverId"), rs.getString("forename"), rs.getString("surname"))));
			}

			rs.close();
			conn.close();
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	
	public List<DriverSeasonResult> getDriverSeasonResult(Season season, DriverIdMap driverIdMap){
		String sql =  "SELECT  r1.driverId as d1, r2.driverId as d2, COUNT(*) as cnt\n" + 
				"FROM races, results as r1, results as r2 \n" + 
				"WHERE r1.raceId = r2.raceId \n" + 
				"AND races.year = ? \n" + 
				"AND races.raceId = r1.raceId \n" + 
				"AND r1.position IS NOT NULL \n" + 
				"AND r2.position IS NOT NULL \n" + 
				"AND r1.position < r2.position\n" + 
				"GROUP BY d1, d2";
		
		List<DriverSeasonResult> list= new ArrayList<DriverSeasonResult>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,	 season.getYear());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Driver d1 = driverIdMap.get(rs.getInt("d1"));
				Driver d2 = driverIdMap.get(rs.getInt("d2"));
				int cnt = rs.getInt("cnt");
				
				if (d1 == null || d2 == null) {
					System.err.format("Skippping %d %d\n", rs.getInt("d1"), rs.getInt("d2"));
				} else {
					list.add(new DriverSeasonResult(d1, d2, cnt));
				}
			}
			rs.close();
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	



}
