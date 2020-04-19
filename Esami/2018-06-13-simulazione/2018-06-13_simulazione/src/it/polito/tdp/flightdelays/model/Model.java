package it.polito.tdp.flightdelays.model;
import java.time.LocalDateTime;
import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.flightdelays.db.FlightDelaysDAO;
public class Model {

	private FlightDelaysDAO fdao;
	private List<Airline> allAirlines ;
	private List<Airport> allAirports;
	private List<Airport> airportsFromAirline;

	private AirportIdMap airportIdMap;
	private AirlineIdMap airlineIdMap;
	
	
	private Graph<Airport, DefaultWeightedEdge> grafo;
	private List<Tratta> le10Peggiori;
	
	private Simulatore simulatore;
	
	public Model() {
		
		this.fdao = new FlightDelaysDAO();
		
		airportIdMap = new AirportIdMap();
		airlineIdMap = new AirlineIdMap();
		
		this.allAirlines = new ArrayList<Airline>(fdao.loadAllAirlines( airlineIdMap));
		this.allAirports = new ArrayList<Airport>(fdao.loadAllAirports(airportIdMap));
	}
	
	
	public List<Airline> getAllAirlines(){
		return this.allAirlines;
	}

	public List<Airport> getAllAirports() {
		return allAirports;
	}

	/**
	 * Ritorna solo gli aeroporti che hanno voli con la Airline @a passata come parametro
	 * @param a
	 * @return
	 */
	public List<Airport> getAirportsFromAirline(Airline a) {
		this.airportsFromAirline = new ArrayList<Airport>(fdao.loadAllAirportsFromAirline(a, airportIdMap));
		return airportsFromAirline;
	}


	
	public void cercaVoli(Airline a) {
		List<Tratta> listatratte = new ArrayList<Tratta>(this.creaGrafo(a));
		le10Peggiori = new ArrayList<Tratta>();
		for (int i=0; i<10; i++) {
			le10Peggiori.add(listatratte.get(i));
		}
	}
	
	
	private List<Tratta> creaGrafo(Airline airline) {
		
		List<Tratta> listaTratte = new ArrayList<Tratta>(fdao.loadAllTratte(airline, airportIdMap));
		
		Collections.sort(listaTratte);
		
		//grafo
		grafo =  new DefaultDirectedWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//vertici
		for (Tratta t: listaTratte) {
			if (!grafo.vertexSet().contains(t.getOrigin())) {
				grafo.addVertex(t.getOrigin());
			}
			else {
				if (!grafo.vertexSet().contains(t.getDestination())) {
					grafo.addVertex(t.getDestination());
				}
				
					//archi
					Graphs.addEdgeWithVertices(grafo, t.getOrigin(), t.getDestination(), t.getAvgDelay()/t.getDistanza());
				}
			}
		return listaTratte;
		}


	
	public List<Tratta> getLe10Peggiori() {
		return le10Peggiori;
		
	}
	
	
	/**
	 * Metodo che inizializza un simulatore	
	 * @param K
	 * @param V
	 * @param a
	 */
	public void simula(int K, int V, Airline a) {
		this.simulatore= new Simulatore(K, V, this, a);
	}


	public Simulatore getSimulatore() {
		return this.simulatore;
	}
	
	
	public String getRitardoSimualazione() {
		return this.simulatore.getRitardiAccumulati();
		
	}
	
	/**
	 * Metodo che ritorna i voli di una determinata @airline, da un @airport dato, successivi al local date time @ldt
	 * @param airport
	 * @param ldt
	 * @param airline
	 * @return
	 */
	public List<Flight> flights(Airport airport, LocalDateTime ldt, Airline airline){
		
		List<Flight> flights = new ArrayList<Flight>();
		
		flights.addAll(fdao.getFlightsFromAirport(airport, airline, ldt, airportIdMap, airlineIdMap));
		
		return flights;
	}
	
	
}

