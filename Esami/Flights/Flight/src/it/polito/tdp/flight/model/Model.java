package it.polito.tdp.flight.model;

import java.util.*;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import com.javadocmd.simplelatlng.*;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private FlightDAO fdao = null;
	private List<Airport> airports;
	private List<Airline> airlines;
	private List<Route> routes;
	
	AirlineIdMap airlineIdMap ;
	AirportIdMap airportIdMap;
	RouteIdMap routeIdMap;
	
	SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo ;
	
	public Model () {
		this.fdao = new FlightDAO();
		
		airlineIdMap = new AirlineIdMap();
		airportIdMap = new AirportIdMap(); 
		routeIdMap = new RouteIdMap();
		
		airlines = fdao.getAllAirlines(airlineIdMap);
		System.out.println(airlines.size());

		airports =fdao.getAllAirports(airportIdMap);
		System.out.println(airports.size());

		routes = fdao.getAllRoutes(routeIdMap, airportIdMap, airlineIdMap);
		System.out.println(routes.size());

	}
	
	public List<Airport>  getAirports(){
		if (this.airports == null) {
			return new ArrayList<Airport>();
		}
		return this.airports;
	}

	public List<Airline> getAirlines() {
		if (this.airlines == null) {
			return new ArrayList<Airline>();
		}
		return this.airlines;
	}

	public List<Route> getRoutes() {
		if (this.routes == null) {
			return new ArrayList<Route>();
		}
		return routes;
	}
	
	public void createGraph() {
		
		//creare il grafo
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
	
		//giungere i vertici
		Graphs.addAllVertices(grafo, this.airports);
		
		
		//aggiungere gli archi
		//iterando sulle rotte
		for(Route r : routes) {
		
			Airport sourceAirport = r.getSourceAirport();
			Airport destinationAirport = r.getDestinationAirport();
			
			// attenzione che bisogna evitare i loop
			if (!sourceAirport.equals(destinationAirport)) {
				double weight = LatLngTool.distance(new LatLng(sourceAirport.getLatitude(), 
						sourceAirport.getLongitude()), new LatLng(destinationAirport.getLatitude(), 
								destinationAirport.getLongitude()), LengthUnit.KILOMETER);
				Graphs.addEdge(grafo, sourceAirport, destinationAirport, weight);
			}
		}
		
		System.out.println("Vertici : "+grafo.vertexSet().size()+", Archi : "+grafo.edgeSet().size());
	}
	
	public void printStats() {
		
		if (grafo != null) {
			this.createGraph();
		}
		
		ConnectivityInspector<Airport, DefaultWeightedEdge > ci = new ConnectivityInspector<Airport, DefaultWeightedEdge>(grafo);
		System.out.println(ci.connectedSets().size());
		
	}
	
	public Set<Airport> getBiggestSCC(){
		
		ConnectivityInspector<Airport, DefaultWeightedEdge > ci = new ConnectivityInspector<Airport, DefaultWeightedEdge>(grafo);
		
		Set<Airport> bestSet = null;
		int bestSize = 0;
		
		for(Set<Airport> s : ci.connectedSets()) {
			if (s.size()>bestSize) {
				bestSize = s.size();
				bestSet = new HashSet<Airport>(s);
			}
		}
		return bestSet;
		
	}

	public List<Airport> getShortestPath(int id1, int id2) {
		
		Airport a1 = this.airportIdMap.get(id1);
		Airport a2 = this.airportIdMap.get(id2);
		
		System.out.println("Areoporto 1 : " +a1);
		System.out.println("Areoporto 2 : " +a2);
		
		
		if (a1 == null || a2 == null) {
			throw new RuntimeException("Gli areoporti selezionati non sono presenti in memoria");
		}
		
		ShortestPathAlgorithm<Airport, DefaultWeightedEdge> spa = new DijkstraShortestPath<Airport, DefaultWeightedEdge>(grafo);
		double weight = spa.getPathWeight(a1, a2);
		System.out.println("Peso del cammino minimo : "+weight+" km");
		GraphPath<Airport, DefaultWeightedEdge> gp= spa.getPath(a1, a2);

		return gp.getVertexList();
	}

}
