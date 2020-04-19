package it.polito.tdp.flight.model;

import java.util.*;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {

	
	private FlightDAO flightDAO;
	
	private AirlineIdMap airlineIdMap;
	private AirportIdMap airportIdMap;
	private List<Airline> airlines;
	
	private SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	
	
	public Model () {
		
		flightDAO = new FlightDAO();	
		airlineIdMap = new AirlineIdMap();
		airportIdMap = new AirportIdMap();
		
		airlines = new ArrayList<>(flightDAO.getAllAirlines(airlineIdMap));
	}
	
	public List<Airline> getAirlines() {
		return this.airlines;
	}

	public Set<Airport> getServiti(Airline a) {
		this.creaGrafo(a);
		return grafo.vertexSet();
	}
	
	

	private void creaGrafo(Airline airline) {
		
		grafo = new SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		
		Graphs.addAllVertices(grafo, flightDAO.getAllAirportsOfAirline(airline, airportIdMap));
		
		List<Route> routes = new ArrayList<Route>(flightDAO.trovaCoppieRotte(airline, airportIdMap));
		
		for (Route r : routes) {
			Airport source = airportIdMap.get(r.getSourceAirport().getAirportId());
			Airport destination = airportIdMap.get(r.getDestinationAirport().getAirportId());
			if (source != null && destination!= null && !source.equals(destination)) {
				double weight = LatLngTool.distance(new LatLng(source.getLatitude(), source.getLongitude()), new LatLng(destination.getLatitude(), destination.getLongitude()), LengthUnit.KILOMETER);
				Graphs.addEdgeWithVertices(grafo, source, destination, weight);
			}
			
		}
	}

	public List<AirportDistance> trovaRaggiungibili(Airport airport, Airline airline) {
		
		List<AirportDistance> list = new ArrayList<>();
		
		for (Airport dest : grafo.vertexSet()) {
			DijkstraShortestPath<Airport, DefaultWeightedEdge> dsp = new DijkstraShortestPath<Airport, DefaultWeightedEdge>(grafo, airport, dest);
			GraphPath<Airport,DefaultWeightedEdge> gp = dsp.getPath();
			
			if (gp!=null)
				list.add(new AirportDistance(dest, dsp.getPathEdgeList().size(), dsp.getPathLength()));
		}	
		Collections.sort(list);
		
		return list;
	}

	
	
	
	
	
	
	
	
}
