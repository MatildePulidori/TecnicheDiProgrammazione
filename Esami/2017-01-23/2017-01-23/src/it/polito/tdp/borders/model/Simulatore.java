package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Simulatore {
	
	// coda degli eventi 
	PriorityQueue<Event> pq;
		
	// modello del mondo
	Country partenza;
	Map<Country, Integer> stanziali;
	SimpleGraph<Country, DefaultEdge> grafo;
	
	// parametri simulazione
	static int numMigranti = 1000;
	
	// output
	int T;
	
	
	public void init(Country partenza, SimpleGraph<Country, DefaultEdge> grafo) {
		
		this.T=1;
		this.partenza=partenza;
		this.grafo=grafo;
		this.stanziali = new HashMap<>();
		
		for (Country c:grafo.vertexSet()) {
			this.stanziali.put(c, 0);
		}
		
		pq= new PriorityQueue<>();
		pq.add(new Event( T, partenza, numMigranti));
	}
	
	public void run() {
		Event e;
		while((e=pq.poll())!=null){
			this.processEvent(e);
		}
		
	}

	private void processEvent(Event e) {
		this.T = e.getTempo();
		int arrivi = e.getNumMigranti();
		Country stato = e.getProvenienza();
		
		List<Country> confinanti = new ArrayList<>(Graphs.neighborListOf(grafo, stato));;
		int migranti = (arrivi/2)/ confinanti.size();
		
		if (migranti!=0) {
			for (Country arrivo: confinanti) {
				pq.add(new Event(e.getTempo()+1, arrivo, migranti));
			}
		}
		int rimasti = arrivi-migranti*confinanti.size();
		this.stanziali.put(stato, this.stanziali.get(stato)+rimasti);
		
	}
	
	public Map<Country, Integer> getStanziali(){
		return this.stanziali;
	}
	
	public int getT() {
		return T;
	}

}
