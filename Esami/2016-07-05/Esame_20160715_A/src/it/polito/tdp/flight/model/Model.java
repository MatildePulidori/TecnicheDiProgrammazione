package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;




public class Model {
	
	private FlightDAO fDAO;
	private AirportIdMap airportIdMap;
	private AirlineIdMap airlineIdMap;
	private List<Airport> airports;
	private List<Airline> airlines;
	private SimpleWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	
	private static double velocitaAereo = 800;
	private double distanzaMax=Double.MIN_VALUE;
	
	private Simulatore simulatore;
	
	
	public Model() {
		fDAO=new FlightDAO();
		airportIdMap = new AirportIdMap();
		airlineIdMap = new AirlineIdMap();
		airports = new ArrayList<>(fDAO.getAllAirports(airportIdMap));
		airlines = new ArrayList<>(fDAO.getAllAirlines(airlineIdMap));
		
		
	}
	
	public List<Airline> getAirlines() {
		return airlines;
	}

	public SimpleWeightedGraph<Airport, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public void creaGrafo(int distanza) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//vertici
		List<Airport> vertici = new ArrayList<>(this.airports);
		Graphs.addAllVertices(grafo, vertici);
		
		
		// tutte le rotte dal dao
		List<Route> rotte =new ArrayList<>(fDAO.getRoutes(airportIdMap, airlineIdMap));
		// selezione le rotte che mi interessano col il metodo calcolaDistanze
		Map<Route, Double> archi = new HashMap<>(this.calcolaDistanze(rotte, distanza));

		// aggiungo archi con il loro peso solo tra gli aeroporti che mi interessano
		for (Route route: archi.keySet()) {
			Graphs.addEdgeWithVertices(grafo, route.getSource(), route.getDestination(), archi.get(route)/velocitaAereo);
		}
		
		System.out.println(String.format("Grafo creato, %d vertici, %d archi", grafo.vertexSet().size(), grafo.edgeSet().size()));
	}


	private Map<Route, Double> calcolaDistanze(List<Route> rotte, int distanzaMax) {
		Map<Route, Double> selezionate= new HashMap<>();
		
		for (Route r:rotte) {
			if (!r.getSource().equals(r.getDestination())) {
				LatLng sourcePoint = new LatLng(r.getSource().getLatitude(), r.getSource().getLongitude());
				LatLng destPoint = new LatLng(r.getDestination().getLatitude(), r.getDestination().getLongitude());
				double distance=LatLngTool.distance(sourcePoint, destPoint, LengthUnit.KILOMETER);
				if (distance<=distanzaMax) {
					selezionate.put(r, distance);
				}
			}
		}
		return selezionate;
	}

	 	
	
	public boolean getConnections() {
		SimpleWeightedGraph<Airport, DefaultWeightedEdge> grafo2 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo2, fDAO.getAirportsWithRoutes(airportIdMap));
		Graphs.addAllEdges(grafo, grafo2, grafo.edgeSet());
		List<Airport> airports= new ArrayList<>(grafo2.vertexSet());
		for (int i=0; i<airports.size(); i++) {
			for (int j=i+1; j<airports.size(); j++) {
				DijkstraShortestPath<Airport, DefaultWeightedEdge> dsp =new DijkstraShortestPath<Airport, DefaultWeightedEdge>(
						grafo2, airports.get(i), airports.get(j));
				GraphPath<Airport, DefaultWeightedEdge> gp= dsp.getPath();
				if (gp==null) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	public int printStats() {
		
		if (grafo != null) {
			new RuntimeException("Crea il grafo prima");
		}
		ConnectivityInspector<Airport, DefaultWeightedEdge > ci = new ConnectivityInspector<Airport, DefaultWeightedEdge>(grafo);
		return ci.connectedSets().size();
	}
	
	
	
	public Set<Airport> getBiggestCC() {
		ConnectivityInspector<Airport, DefaultWeightedEdge> ci= new ConnectivityInspector<Airport, DefaultWeightedEdge>(grafo);
		
		Set<Airport> bestSet=null;
		int maxCC = Integer.MIN_VALUE;
		
		for (Set<Airport> s: ci.connectedSets()) {
			if (s.size()>maxCC) {
				maxCC=s.size();
				bestSet=new HashSet<Airport>(s);
			}
		}
		return bestSet;
	}

	public Airport getAeroportPiuLontano() {
		Airport fiumicino= this.airportIdMap.get(1555);
		BreadthFirstIterator<Airport, DefaultWeightedEdge> bfi= new BreadthFirstIterator<>(grafo,fiumicino );
		Airport piuLontanoDaFiumicino=null;
		distanzaMax=Double.MIN_VALUE;
		
		LatLng point1 = new LatLng(fiumicino.getLatitude(), fiumicino.getLongitude());
		while(bfi.hasNext()) {
			Airport two= bfi.next();
			LatLng point2 = new LatLng(two.getLatitude(), two.getLongitude());
			double distanzaCorrente =LatLngTool.distance(point1, point2, LengthUnit.KILOMETER);
			if (distanzaCorrente>distanzaMax) {
				distanzaMax= distanzaCorrente;
				piuLontanoDaFiumicino = two;
			}
			
		}
		return piuLontanoDaFiumicino;
	}

	public double getDistanzaMaxFiumicino() {
		return this.distanzaMax;
	}
	
	
	public void doSimulazione(int k) {
		
		SimpleWeightedGraph<Airport, DefaultWeightedEdge> grafo2 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo2, fDAO.getAirportsWithRoutes(airportIdMap));
		Graphs.addAllEdges(grafo, grafo2, grafo.edgeSet());
		simulatore=new Simulatore();
		simulatore.init(grafo2, k, airportIdMap);
		simulatore.run();
	}
	
	public Map<Airport, Integer> getOutput(){
		return simulatore.getMap();
	}

	

	
	
	
	
}
