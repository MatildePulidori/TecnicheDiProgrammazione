package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;


import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {

	private FormulaOneDAO fdao;
	private  SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge> grafo ;	
	private List<Driver> listaDriversOfSeason;
	private DriverIdMap driverIdMap;
	
	private List<Driver> bestDreamTeam;
	private int bestDreamTeamValue;

	
	public Model() {
		fdao = new FormulaOneDAO(this);
		driverIdMap = new DriverIdMap();
	}
	
	public List<Season> getListaSeason() {
		return fdao.getAllSeasons();
	}
	
	public void creaGrafo(Season season) {	
		grafo = new SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		// vertici
		listaDriversOfSeason = new ArrayList<Driver>(fdao.getDriversOfSeason(season, driverIdMap));
		Graphs.addAllVertices(grafo, listaDriversOfSeason);
		// archi
		for (DriverSeasonResult dsr: fdao.getDriverSeasonResult(season, driverIdMap)) {
			if (!dsr.getD1().equals(dsr.getD2()))
				Graphs.addEdgeWithVertices(grafo, dsr.getD1(), dsr.getD2(), dsr.getCounter());
		}
		System.out.format("Grafo creato: %d archi, %d nodi\n",grafo.edgeSet().size(), grafo.vertexSet().size());
	}
	
	
	public Driver getBestDriver() {
		if (grafo == null) {
			new RuntimeException("Creare il grafo.");
		}
		
		//Inizializzazione
		Driver bestDriver = null;
		int best = Integer.MIN_VALUE;
		
		//
		for (Driver d : grafo.vertexSet()) {
			int sum = 0;
			
			//Itero sugli archi uscenti
			for (DefaultWeightedEdge e: grafo.outgoingEdgesOf(d)) {
				sum += grafo.getEdgeWeight(e);
			}
			//Itero sugli archi entranti
			for (DefaultWeightedEdge e: grafo.incomingEdgesOf(d)) {
				sum -= grafo.getEdgeWeight(e);
			}
			
			if (sum > best || bestDriver == null) {
				best = sum;
				bestDriver = d ;
			}
		}
		
		if (bestDriver == null) {
			new RuntimeException("BestDriver not found");
		}
		
		return bestDriver;
		
	}
	
	public List<Driver> getDreamTeam(int K){
		bestDreamTeam = new ArrayList<Driver>();
		bestDreamTeamValue = Integer.MAX_VALUE;
		recursive(0, new ArrayList<Driver>(), K );
		
		return bestDreamTeam;
		
	}

	private void recursive(int step, ArrayList<Driver> tempDreamTeam, int K ) {
		
		// condizione terminazione 
		if(step >= K) {
			if (evaluate(tempDreamTeam) < bestDreamTeamValue) {
				bestDreamTeamValue = evaluate(tempDreamTeam);
				bestDreamTeam = new ArrayList<Driver>(tempDreamTeam);
				return;
			}
		}
		
		// soluz. parziale
		for (Driver driver : grafo.vertexSet()) {
			if (!tempDreamTeam.contains(driver)) {
				tempDreamTeam.add(driver);          //controlla hashCode e equals()!
				recursive(step+1, tempDreamTeam, K);
				tempDreamTeam.remove(driver);
			}
			
		}
		
	}

	private int evaluate(ArrayList<Driver> tempDreamTeam) {
		int sum = 0;
		Set<Driver> tempDreamTeamSet = new HashSet<Driver>(tempDreamTeam);
		for (DefaultWeightedEdge e: grafo.edgeSet()) {
			if (tempDreamTeamSet.contains(grafo.getEdgeTarget(e))) {
				sum += grafo.getEdgeWeight(e);
			}
		}
		return sum;
	}
	
	



	
}
