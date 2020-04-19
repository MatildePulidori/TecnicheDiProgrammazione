package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.formulaone.model.CoppiaRaces;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.DriverIdMap;
import it.polito.tdp.formulaone.model.LapTime;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.RaceIdMap;
import it.polito.tdp.formulaone.model.Season;
public class FormulaOneDAO {

	public List<Season> getAllSeasons() {
		String sql = "Select distinct seasons.year as s \n" + 
				"from seasons, races\n" + 
				"where seasons.year = races.year\n" + 
				"Order by s ";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Season> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Season(rs.getInt("s")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Race> getRaces(Season season, RaceIdMap raceIdMap){
		String sql = "Select distinct raceId, name, year, date\n" + 
				"from races\n" + 
				"where year = ? \n" + 
				"Order by year"; 
		
		List<Race> list = new ArrayList<Race>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, season.getYear());
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Race race = new Race(rs.getInt("raceId"), rs.getInt("year"), rs.getString("name"), rs.getDate("date").toLocalDate());
				list.add(raceIdMap.get(race));
			}
			conn.close();
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<Race> getRacesForsimulation(Season season, RaceIdMap raceIdMap){
		String sql = "Select distinct laptimes.raceId, name, year, date\n" + 
				"from races, laptimes\n" + 
				"where year = ?\n" + 
				"and laptimes.raceId = races.raceId\n" + 
				"Order by year"; 
		
		List<Race> list = new ArrayList<Race>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, season.getYear());
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Race race = new Race(rs.getInt("raceId"), rs.getInt("year"), rs.getString("name"), rs.getDate("date").toLocalDate());
				list.add(raceIdMap.get(race));
			}
			conn.close();
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<CoppiaRaces> getArchi(Season s, RaceIdMap raceIdMap) {
		String sql = "Select tab1.raceId, tab2.raceId,count(*) as cnt\n" + 
				"from results as tab1, results as tab2, races as races1, races as races2\n" + 
				"where races1.raceId = tab1.raceId\n" + 
				"and races2.raceId = tab2.raceId\n" + 
				"and races2.year = ?\n" + 
				"and races1.year = ?\n" + 
				"and tab1.driverId = tab2.driverId\n" + 
				"and tab1.raceId>tab2.raceId\n" + 
				"group by tab1.raceId, tab2.raceId";
		
		List<CoppiaRaces> list = new ArrayList<>();
		
		try { 
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, s.getYear());
			st.setInt(2, s.getYear());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Race r1= raceIdMap.get(rs.getInt("tab1.raceId"));
				Race r2 = raceIdMap.get(rs.getInt("tab2.raceId"));
				int numDriverComuni = rs.getInt("cnt");
				
				CoppiaRaces cr = new CoppiaRaces(r1, r2, numDriverComuni);
				list.add(cr);
				
			}
			conn.close();
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
		
		
		public List<Driver> getDriversOfRace(Race race, DriverIdMap driverIdMap){
			String sql = "Select distinct laptimes.driverId, drivers.forename, drivers.surname\n" + 
					"from races, laptimes, drivers\n" + 
					"where laptimes.raceId = ?\n" + 
					"and laptimes.raceId = races.raceId\n" + 
					"and laptimes.driverId = drivers.driverId";
			List<Driver> driversOfRace = new ArrayList<>();
			
			try {
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, race.getRaceId());
				ResultSet rS= st.executeQuery();
				
				
				while(rS.next()) {
					int i = rS.getInt("laptimes.driverId");
					Driver driver = new Driver(i, rS.getString("drivers.forename"), rS.getString("drivers.surname"));
					driversOfRace.add(driverIdMap.get(driver));
				}
				
				conn.close();
				return driversOfRace;
				
			}catch(SQLException e) {
				e.printStackTrace();
				return null;
			}
			
		}
		
		
		
		public List<LapTime> getLapTimesOfDriverInRace(Driver driver, Race race){
			
			String sql = "Select raceId, driverId, lap, milliseconds\n" + 
					"From laptimes\n" + 
					"Where raceId = ? and driverId=? ";
			List<LapTime> giriOfDriver= new ArrayList<>();
			
			try {
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, race.getRaceId());
				st.setInt(2, driver.getDriverId());
				ResultSet rS= st.executeQuery();
				
				
				while(rS.next()) {
					
					LapTime lapTime = new LapTime(race, driver, rS.getInt("lap"), rS.getInt("milliseconds"));
					giriOfDriver.add(lapTime);
				
				}
				
				conn.close();
				return giriOfDriver;
				
			}catch(SQLException e) {
				e.printStackTrace();
				return null;
			}
			
		}
}
