package it.polito.tdp.flight.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.flight.model.Event.EventType;


public class Simulatore {
	
	//coda degli eventi
	PriorityQueue<Event> pq;
	
	//modello del mondo
	SimpleWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	AirportIdMap airportIdMap;
	Map<Airport, Integer> map;
	List<Airport> airports;
	
	//parametri simulazione
	int k;
	int day=0;
	LocalTime start;
	final int oreTraDueVoli = 2;
	
	

	public void init(SimpleWeightedGraph<Airport, DefaultWeightedEdge> grafo, int k, AirportIdMap airportIdMap) {
		this.k = k;
		this.grafo = grafo;
		this.airportIdMap = airportIdMap;
		this.start = LocalTime.of(06, 00);
		this.pq=new PriorityQueue<Event>();
		this.map=new HashMap<>();
		this.airports = new ArrayList<>(grafo.vertexSet());
		
		for (int i=0; i<k; i++) {
			Passeggero p= new Passeggero(i);
			int aeroporto = (int)(Math.random()*airports.size());
			Airport partenza =  airports.get(aeroporto);
			pq.add(new Event(EventType.ARRIVO, start, partenza, p));
			airportIdMap.get(partenza).addNumPasseggeri(1);
			map.put(airportIdMap.get(partenza), airportIdMap.get(partenza).getNumPasseggeri());
		}
	}
	
	public void run() {
		
		Event event;
		while((event=pq.poll())!=null) {
			processEvent(event);
		}
	}

	private void processEvent(Event e) {
		switch (e.getType()) {
		case ARRIVO:
			
			LocalTime time=e.getTime();
			Airport airport =e.getAirport();
			Passeggero passeggero = e.getPasseggero();
	
			List<Airport> raggiungibili = new ArrayList<>(Graphs.neighborListOf(grafo, airport));
			if (raggiungibili.size()!=0) {
				int destinazione = (int)(Math.random()*raggiungibili.size());
				Airport dest = raggiungibili.get(destinazione);
				
				double tempoVolo= grafo.getEdgeWeight(grafo.getEdge(airport, dest));
				long minutesToAdd = (long) (tempoVolo*60);
				LocalTime partenza =null;
				if (time.isAfter(LocalTime.of(23, 00)) && time.isBefore(LocalTime.of(7, 00))) {
					day++;
					partenza=LocalTime.of(7, 00);
				}
				if ( time.isAfter(LocalTime.of(7, 00)) && time.isBefore(LocalTime.of(9, 00)) ){
				
					partenza=LocalTime.of(9, 00);	
				}
				if (time.isAfter(LocalTime.of(9, 00)) && time.isBefore(LocalTime.of(11, 00))) {
					partenza=LocalTime.of(11, 00);
				}
				
				if (time.isAfter(LocalTime.of(11, 00)) && time.isBefore(LocalTime.of(13, 00))) {
					partenza=LocalTime.of(13, 00);
				}
				if (time.isAfter(LocalTime.of(13, 00)) && time.isBefore(LocalTime.of(15, 00))) {
					partenza=LocalTime.of(15, 00);
				}
				if (time.isAfter(LocalTime.of(15, 00)) && time.isBefore(LocalTime.of(17, 00))) {
					partenza=LocalTime.of(17, 00);
				}
				if (time.isAfter(LocalTime.of(17, 00)) && time.isBefore(LocalTime.of(19, 00))) {
					partenza=LocalTime.of(19, 00);
				}
				if (time.isAfter(LocalTime.of(19, 00)) && time.isBefore(LocalTime.of(21, 00))) {
					partenza=LocalTime.of(21, 00);
				}
				if (time.isAfter(LocalTime.of(21, 00)) && time.isBefore(LocalTime.of(23, 00))) {
					partenza=LocalTime.of(23, 00);
				}
				if (day>2) {
					break;
				}		
				pq.add(new Event(EventType.ARRIVO, partenza.plusMinutes(minutesToAdd), airportIdMap.get(dest),passeggero));
				airportIdMap.get(dest).addNumPasseggeri(1);
				airportIdMap.get(airport).removeNumPasseggeri(1);
				map.put(airportIdMap.get(airport), airportIdMap.get(airport).getNumPasseggeri());
				map.put(airportIdMap.get(dest), airportIdMap.get(dest).getNumPasseggeri());
			
			break;
			}
		}
	}
	
	
	public Map<Airport, Integer> getMap() {
		return this.map;
	}
	

}
