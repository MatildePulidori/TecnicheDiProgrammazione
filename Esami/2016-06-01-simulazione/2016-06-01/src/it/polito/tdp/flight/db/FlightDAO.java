package it.polito.tdp.flight.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.AirlineIdMap;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.AirportIdMap;
import it.polito.tdp.flight.model.Route;

public class FlightDAO {

	public List<Airport> getAllAirports() {
		
		String sql = "SELECT * FROM airport" ;
		
		List<Airport> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add( new Airport(
						res.getInt("Airport_ID"),
						res.getString("name"),
						res.getDouble("Latitude"),
						res.getDouble("Longitude")));
			}
			
			conn.close();
			
			return list ;
		} catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}
	
	
	public List<Airline> getAllAirlines(AirlineIdMap airlineIdMap){
		
		String sql ="Select distinct airline.airline_id as id , name \n" + 
				"From airline, route\n" + 
				"Where route.airline_id = airline.airline_id";
		
		List<Airline> list = new ArrayList<Airline>();
		
		try {
			Connection connection = DBConnect.getConnection();
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Airline a = new Airline(rs.getInt("id"), rs.getString("name"));
				list.add(airlineIdMap.get(a));
			}
			
			connection.close();
			return list;
		}catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}
	
	
	
	
	public static void main(String args[]) {
		FlightDAO dao = new FlightDAO() ;
		
		List<Airport> arps = dao.getAllAirports() ;
		System.out.println(arps);
	}


	public List<Airport> getAllAirportsOfAirline(Airline airline, AirportIdMap airportIdMap) {
		String sql = "Select distinct airport.airport_id as id, airport.name as name, airport.latitude as lat, airport.longitude as lng\n" + 
				"From airport, route, airline\n" + 
				"Where (airport.airport_id = route.Source_airport_id or \n" + 
				"airport.airport_id = route.Destination_airport_id) and route.airline_id = ? \n" + 
				"Order by airport.airport_id";
		
		
		List<Airport> list = new ArrayList<Airport>();
		
		try {
			Connection connection = DBConnect.getConnection();
			PreparedStatement st = connection.prepareStatement(sql);
			st.setInt(1, airline.getAirlineId());
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Airport a = new Airport(rs.getInt("id"), rs.getString("name"), rs.getDouble("lat"), rs.getDouble("lng"));
				list.add(airportIdMap.get(a));
			}
			
			connection.close();
			return list;
		}catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}
	
	
	public List<Route> trovaCoppieRotte(Airline airline,  AirportIdMap airportIdMap) {
		String sql = "Select source_airport_ID as source, destination_airport_ID as destination\n" + 
				"From route\n" + 
				"Where airline_id = ?\n" + 
				"group by source_airport_ID, destination_airport_ID";
		
		List<Route> list = new ArrayList<>();
		
		try {
			Connection connection = DBConnect.getConnection();
			PreparedStatement st = connection.prepareStatement(sql);
			st.setInt(1, airline.getAirlineId());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Airport source = airportIdMap.get(rs.getInt("source"));
				Airport destination = airportIdMap.get(rs.getInt("destination"));
				Route route =new Route(airline, source, destination);
				list.add(route);
			}
			
			connection.close();
			return list;
		}
		catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}
	
	
}
