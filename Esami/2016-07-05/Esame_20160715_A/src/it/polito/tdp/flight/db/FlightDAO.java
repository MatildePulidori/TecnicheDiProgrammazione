package it.polito.tdp.flight.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.AirlineIdMap;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.AirportIdMap;
import it.polito.tdp.flight.model.Route;


public class FlightDAO {

	public List<Airline> getAllAirlines(AirlineIdMap airlineIdMap) {
		String sql = "SELECT * FROM airline";
		List<Airline> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Airline airline=new Airline(res.getInt("Airline_ID"), res.getString("Name"));
				list.add(airlineIdMap.get(airline));
				
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}



	public List<Airport> getAllAirports(AirportIdMap airportIdMap) {
		String sql = "select * from airport";
		List<Airport> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Airport airport =new Airport(res.getInt("Airport_ID"), res.getString("name"),
						res.getDouble("Latitude"), res.getDouble("Longitude"));
				list.add(airportIdMap.get(airport));
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}


	public List<Route> getRoutes(AirportIdMap airportIdMap, AirlineIdMap airlineIdMap){
		
		String sql = "select distinct airline_id, Source_airport_ID, Destination_airport_ID\n"
				+ "from route where Source_airport_ID<Destination_airport_ID";
		List<Route> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) {
				Airport sourceAirport = airportIdMap.get(rs.getInt("source_airport_id"));
				Airport destinationAirport = airportIdMap.get(rs.getInt("destination_airport_id"));
				Airline airline= airlineIdMap.get(rs.getInt("airline_id"));
				Route route = new Route(airline, sourceAirport, destinationAirport);
				list.add(route);
			}
			
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
	public List<Airport> getAirportsWithRoutes(AirportIdMap airportIdMap) {
		String sql = "select distinct Airport_ID, name, Latitude, Longitude\n" + 
				"from airport, route\n" + 
				"where airport_ID=source_airport_id or airport_ID=destination_airport_id";
		List<Airport> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Airport airport =new Airport(res.getInt("Airport_ID"), res.getString("name"),
						res.getDouble("Latitude"), res.getDouble("Longitude"));
				list.add(airportIdMap.get(airport));
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
