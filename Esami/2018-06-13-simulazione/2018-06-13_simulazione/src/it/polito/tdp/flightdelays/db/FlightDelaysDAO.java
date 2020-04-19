package it.polito.tdp.flightdelays.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.AirlineIdMap;
import it.polito.tdp.flightdelays.model.Airport;
import it.polito.tdp.flightdelays.model.AirportIdMap;
import it.polito.tdp.flightdelays.model.CoppiaAirline;
import it.polito.tdp.flightdelays.model.Flight;
import it.polito.tdp.flightdelays.model.FlightTime;
import it.polito.tdp.flightdelays.model.Tratta;




public class FlightDelaysDAO {

	public List<Airline> loadAllAirlines(AirlineIdMap airlineIdMap) {
		String sql = "SELECT id, iata_code, airline from airlines";
		List<Airline> result = new ArrayList<Airline>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airline airline = new Airline(rs.getInt("ID"),rs.getString("iata_code"), rs.getString("airline"));
				
				result.add(airlineIdMap.getAirline(airline));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Airport> loadAllAirports(AirportIdMap airportIdMap) {
		String sql = "SELECT id, iata_code, airport, city, state, country, latitude, longitude, timezone_offset\n" + 
				"FROM airports WHERE latitude IS NOT NULL and longitude IS NOT NULL";
		List<Airport> result = new ArrayList<Airport>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airport airport = new Airport(rs.getInt("id"),rs.getString("iata_code"), rs.getString("airport"), rs.getString("city"),
						rs.getString("state"), rs.getString("country"), rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getInt("timezone_offset"));
				result.add(airportIdMap.getAirport(airport));
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Airport> loadAllAirportsFromAirline(Airline airline, AirportIdMap airportIdMap) {
		String sql = "SELECT airline_id, origin_airport_id, destination_airport_id \r\n" + 
				"FROM flights\r\n" + 
				"WHERE airline_id = ?\r\n" + 
				"and origin_airport_id<>destination_airport_id\r\n" + 
				"GROUP BY origin_airport_id ,destination_airport_id\r\n" + 
				"";
		List<Airport> result = new ArrayList<Airport>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, airline.getId());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if (!result.contains(airportIdMap.getAirport(rs.getInt("origin_airport_id"))) && 
						!result.contains(airportIdMap.getAirport(rs.getInt("destination_airport_id"))) &&
						airportIdMap.getAirport(rs.getInt("origin_airport_id"))!=null && airportIdMap.getAirport(rs.getInt("destination_airport_id"))!=null) {
					result.add(airportIdMap.getAirport(rs.getInt("origin_airport_id")));
					result.add(airportIdMap.getAirport(rs.getInt("destination_airport_id")));
				}
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Tratta> loadAllTratte(Airline a, AirportIdMap airportIdMap) {
		String sql = "SELECT AVG(arrival_delay) as delay, airline_id, origin_airport_id, destination_airport_id, scheduled_departure_date, "
				+ "arrival_date, departure_delay, arrival_delay, elapsed_time, distance FROM flights WHERE airline_id = ? GROUP BY origin_airport_id , destination_airport_id";
		List<Tratta> result = new LinkedList<Tratta>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a.getId());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Airport originAirport = airportIdMap.getAirport(rs.getInt("origin_airport_id"));
				Airport destinationAirport = airportIdMap.getAirport(rs.getInt("destination_airport_id"));
				
				if (originAirport!=null && destinationAirport!=null) {
					
					Tratta tratta = new Tratta(originAirport, destinationAirport, rs.getDouble("delay"));
					
				
				result.add((tratta));
				}
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	

	public List<Flight> getFlightsFromAirport(Airport airport, Airline airline, LocalDateTime ldt, AirportIdMap airportIdMap,
			AirlineIdMap airlineIdMap) {
		String sql =" SELECT id, airline, flight_number, origin_airport_id, destination_airport_id, scheduled_dep_date, arrival_date, departure_delay, arrival_delay, air_time, distance\r\n" + 
				"FROM flights WHERE airline = ? AND origin_airport_id = ? AND scheduled_dep_date >= ? " ;
		
		List<Flight> flightsFromAirport = new ArrayList<Flight>();	
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, airline.getId());
			st.setInt(2, airport.getId());
			st.setTimestamp(3, Timestamp.valueOf(ldt));
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airline airLine = airlineIdMap.getAirline(airline);
				Airport originAirport = airportIdMap.getAirport(airport);
				Airport destinationAirport = airportIdMap.getAirport(rs.getInt("destination_airport_id"));
				
				if (originAirport!=null && destinationAirport!=null) {
					
				
					Flight flight = new Flight(rs.getInt("id"), airLine, rs.getInt("flight_number"), originAirport, destinationAirport, 
							rs.getTimestamp("scheduled_dep_date").toLocalDateTime(),rs.getTimestamp("arrival_date").toLocalDateTime(),rs.getInt("departure_delay"),
							rs.getInt("arrival_delay"), rs.getInt("air_time"), rs.getInt("distance"));
					
					flightsFromAirport.add(flight);
			
				}
			}
			conn.close();
			return flightsFromAirport;
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	
	public Map<CoppiaAirline, CoppiaAirline> getArchi (Month month, AirlineIdMap airlineIdMap, AirportIdMap airportIdMap){
		String sql= "Select distinct f1.airline_id, f2.airline_id, f1.origin_airport_id,(f1.destination_airport_id)\r\n" + 
				"from flights as f1, flights as f2 \r\n" + 
				"where (f1.airline_id <f2.airline_id)\r\n" + 
				"and \r\n" + 
				"((f1.origin_airport_id = f2.origin_airport_id and f1.destination_airport_id = f2.destination_airport_id)\r\n" + 
				"	or (f1.origin_airport_id = f2.destination_airport_id and f1.destination_airport_id = f2.origin_airport_id))\r\n" + 
				"and month(f1.scheduled_departure_date)=? \r\n" + 
				"and month(f2.scheduled_departure_date)=? \r\n" + 
				"order by f1.airline_id, f2.airline_id";
		
		Map<CoppiaAirline, CoppiaAirline> map = new HashMap<>();
		
		try {
			
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, month.getValue());
			st.setInt(2, month.getValue());
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) {
				Airline one= airlineIdMap.getAirline(rs.getInt("f1.airline_id"));
				Airline two= airlineIdMap.getAirline(rs.getInt("f2.airline_id"));
				CoppiaAirline coppia= new CoppiaAirline(one, two);
				if (!map.containsKey(coppia)) {
					map.put(coppia, coppia);
				}
				map.get(coppia).addAirportsComuni(airportIdMap.getAirport(rs.getInt("f1.origin_airport_id")));
				map.get(coppia).addAirportsComuni(airportIdMap.getAirport(rs.getInt("f1.destination_airport_id")));
				
			}
			
			
			
			conn.close();
			return map;
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	public List<Airport> getOriginsAirlines(Airline a1, Airline a2, AirportIdMap airportIdMap){
		
		String sql ="Select distinct f1.origin_airport_id as originId\n" + 
				"From flights as f1, flights as f2\n" + 
				"Where f1.airline = ?\n" + 
				"and f2.airline = ?\n" + 
				"and (f1.origin_airport_id = f2.origin_airport_id)";
		List<Airport> origins = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a1.getId());
			st.setInt(2, a2.getId());
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				if (airportIdMap.getAirport(rs.getInt("originId"))!=null) {
					origins.add(airportIdMap.getAirport(rs.getInt("originId")));
				}
				
				
			}
			conn.close();
			return origins;
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	
	
	
	public List<Airport> getDestinationsAirlines(Airline a1, Airline a2, AirportIdMap airportIdMap){
		
		String sql ="Select distinct f1.destination_airport_id as destId\n" + 
				"From flights as f1, flights as f2\n" + 
				"Where f1.airline = ?\n" + 
				"and f2.airline = ?\n" + 
				"and (f1.destination_airport_id = f2.destination_airport_id)";
		List<Airport> origins = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a1.getId());
			st.setInt(2, a2.getId());
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				if (airportIdMap.getAirport(rs.getInt("destId"))!=null) {
					origins.add(airportIdMap.getAirport(rs.getInt("destId")));
				}
				
				
			}
			conn.close();
			return origins;
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	
	
	public List<FlightTime> geFlightTime(Month month,int day, AirportIdMap airportIdMap){
		String sql = "Select origin_airport_id, scheduled_departure_date, departure_delay, a1.timezone_offset,\r\n" + 
				"destination_airport_id, arrival_date, arrival_delay, a2.timezone_offset, (a2.timezone_offset-a1.timezone_offset)\r\n" + 
				"from flights, airports as a1, airports as a2\r\n" + 
				"where month(scheduled_departure_date)=? and day (scheduled_departure_date)=?\r\n" + 
				"and origin_airport_id=a1.id and destination_airport_id=a2.id\r\n" + 
				"order by scheduled_departure_date";
		
		List<FlightTime> list= new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, month.getValue());
			st.setInt(2, day);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				FlightTime ft= new FlightTime(airportIdMap.getAirport(rs.getInt("origin_airport_id")), 
						airportIdMap.getAirport(rs.getInt("destination_airport_id")), rs.getTimestamp("scheduled_departure_date").toLocalDateTime(),
						rs.getTimestamp("arrival_date").toLocalDateTime(), rs.getInt("departure_delay"), rs.getInt("arrival_delay"));
				
				list.add(ft);
			}
			
			conn.close();
			return list;
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
