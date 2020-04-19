package it.polito.tdp.flightdelays.db;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.AirlineIdMap;
import it.polito.tdp.flightdelays.model.Airport;
import it.polito.tdp.flightdelays.model.AirportIdMap;
import it.polito.tdp.flightdelays.model.CoppiaAirline;
import it.polito.tdp.flightdelays.model.FlightTime;
import it.polito.tdp.flightdelays.model.Tratta;



public class TestDAO {

	public static void main(String[] args) {

		FlightDelaysDAO dao = new FlightDelaysDAO();
		AirportIdMap airportIdMap = new AirportIdMap();
		AirlineIdMap airlineIdMap= new AirlineIdMap();
		
		
		List<Airline> airlines = new ArrayList<>(dao.loadAllAirlines(airlineIdMap));
		List<Airport> airports= new ArrayList<>(dao.loadAllAirports(airportIdMap));
		System.out.println(airlines.size()+" airlines");
		System.out.println(airports.size()+" airports");
		
		int a = (int)(Math.random()*airlines.size());
		Airline airline = airlines.get(a);
		List<Airport> airportOfAirline = new ArrayList<>(dao.loadAllAirportsFromAirline(airline, airportIdMap));
		System.out.println(String.format("La airline %d %s raggiunge %d aeroporti.", airline.getId(), airline.getName(), airportOfAirline.size()));
	
		
		List<Tratta> tratte = new ArrayList<>(dao.loadAllTratte(airline, airportIdMap));
		System.out.println(String.format("La airline %d %s fa %d tratte.", airline.getId(), airline.getName(), tratte.size()));
		
		/*for (Tratta t: tratte) {
			System.out.println(t.toString()+"\n");
		}*/
		
		/*Tratta tratta = new Tratta(airportIdMap.getAirport(2),  airportIdMap.getAirport(51), 24.25);
		double distanza=LatLngTool.distance(new LatLng(tratta.getOrigin().getLatitude(), tratta.getOrigin().getLongitude()),
				new LatLng(tratta.getDestination().getLatitude(), tratta.getDestination().getLongitude()), LengthUnit.KILOMETER);
		
		System.out.println(String.format("Distanza tra aeroporto %d e %d : %f. Peso arco : %f", tratta.getOrigin().getId(),
			tratta.getDestination().getId(), distanza, tratta.getAvgDelay()/distanza));
		*/
		
	
		/*SimpleWeightedGraph<Airline, DefaultWeightedEdge> grafo= new SimpleWeightedGraph<Airline, DefaultWeightedEdge>(DefaultWeightedEdge.class);
			
		//vertici 

		List<Airline> vertici = new ArrayList<>(airlines);
		Graphs.addAllVertices(grafo, vertici);
			
		//archi
		List<CoppiaAirline> archi= new ArrayList<>(dao.getArchi(Month.MAY, airlineIdMap, airportIdMap).values());
		
		for (CoppiaAirline c: archi) {
			System.out.println(c.getAirline1() +" - " + c.getAirline2()+" -->" +c.getAirportsComuni().size()+" aeroporti comuni.\n");
			Graphs.addEdgeWithVertices(grafo, c.getAirline1(), c.getAirline2(), c.getAirportsComuni().size());
		}
		System.out.println(String.format("Grafo creato %d vertici e %d archi", grafo.vertexSet().size(), grafo.edgeSet().size()));*/
		
		
		List<FlightTime> flightTimes = new ArrayList<>(dao.geFlightTime(Month.MAY, 9, airportIdMap));
		FlightTime ft = flightTimes.get(0);
		System.out.println(ft.getOriginAirport()+" - "+ft.getScheduledDepartureDate()+" -->"+ ft.getDestinationAirport()+" - "+ft.getArrivalDate()+" ");
		
	}

}
