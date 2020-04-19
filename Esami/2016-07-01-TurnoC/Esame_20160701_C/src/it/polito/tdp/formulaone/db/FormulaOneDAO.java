package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.formulaone.model.Circuit;
import it.polito.tdp.formulaone.model.Constructor;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.DriverIdMap;
import it.polito.tdp.formulaone.model.DriversPair;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {

	public List<Integer> getAllYearsOfRace() {
		
		String sql = "SELECT year FROM races ORDER BY year" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Integer> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(rs.getInt("year"));
			}
			
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
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Season> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(new Season(Year.of(rs.getInt("year")), rs.getString("url"))) ;
			}
			
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
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Circuit> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Circuit(rs.getInt("circuitId"), rs.getString("name")));
			}

			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Constructor> getAllConstructors() {

		String sql = "Select distinct constructors.constructorId, constructors.name\n" + 
				"From results, constructors\n" + 
				"Where results.constructorId = constructors.constructorId\n" + 
				"Order by constructors.constructorId\n";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Constructor> constructors = new ArrayList<>();
			while (rs.next()) {
				constructors.add(new Constructor(rs.getInt("constructorId"), rs.getString("name")));
			}

			conn.close();
			return constructors;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}

	
	public List<Driver> getDriversOfConstructor(Constructor constructor, DriverIdMap driverIdMap){
		
		String sql= "Select distinct drivers.driverId, drivers.forename, drivers.surname\n" + 
				"From results, drivers, constructors\n" + 
				"Where constructors.constructorId=results.constructorId\n" + 
				"and constructors.constructorId = ?\n" + 
				"and drivers.driverId = results.driverId";
		
		List<Driver> drivers = new ArrayList<>();
		try {
			Connection connection = DBConnect.getConnection();
			PreparedStatement st = connection.prepareStatement(sql);
			st.setInt(1, constructor.getConstructorId());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Driver driver = new Driver(rs.getInt("drivers.driverId"), rs.getString("drivers.forename"), rs.getString("drivers.surname"));
				drivers.add(driverIdMap.get(driver));
			}
			connection.close();
			return drivers;
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
		
	}

	public List<DriversPair> getArchi(Constructor constructor, DriverIdMap driverIdMap){
		String sql = "Select r1.driverId as d1 , r2.driverId as d2, count(*) as cnt\n" + 
			"From results as r1, results as r2\n" + 
			"Where r1.constructorId = ?\n" + 
			"and r2.constructorId = r1.constructorId\n" + 
			"and r1.raceId = r2.raceId\n" + 
			"and r1.driverId< r2.driverId\n" + 
			"group by r1.driverId, r2.driverId";
		
		List<DriversPair> coppie = new ArrayList<>();
		try {
			Connection connection = DBConnect.getConnection();
			PreparedStatement st = connection.prepareStatement(sql);
			st.setInt(1, constructor.getConstructorId());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Driver d1= driverIdMap.get(rs.getInt("d1"));
				Driver d2 = driverIdMap.get(rs.getInt("d2"));
				int count = rs.getInt("cnt");
				
				coppie.add(new DriversPair(d1, d2, count));
			}
			connection.close();
			return coppie;
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	
	public static void main(String[] args) {
		FormulaOneDAO dao = new FormulaOneDAO() ;
		
		List<Integer> years = dao.getAllYearsOfRace() ;
		System.out.println(years);
		
		List<Season> seasons = dao.getAllSeasons() ;
		System.out.println(seasons);

		
		List<Circuit> circuits = dao.getAllCircuits();
		System.out.println(circuits);

		List<Constructor> constructors = dao.getAllConstructors();
		System.out.println(constructors);
		
	}
	
}
