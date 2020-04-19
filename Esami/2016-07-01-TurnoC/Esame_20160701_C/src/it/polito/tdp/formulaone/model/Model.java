package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	private FormulaOneDAO fdao;
	private DriverIdMap driverIdMap;
	private List<Constructor> constructors;
	
	private SimpleWeightedGraph<Driver, DefaultWeightedEdge> grafo;
	
	List<Driver> dreamTeam ;
	
	
	
	public Model() {
		fdao = new FormulaOneDAO(); 
		driverIdMap = new DriverIdMap();
		constructors = new ArrayList<>(fdao.getAllConstructors());
		
	}
	
	public List<Constructor> getConstructors(){
		return this.constructors;
	}

	public Driver getBest(Constructor constructor) {
		Driver bestDriver = null;
		int maxGare = Integer.MIN_VALUE;
		
		this.creaGrafo(constructor);
		if (grafo == null) {
			new RuntimeException("Creare grafo");
		}
		
		
		for(Driver driver: grafo.vertexSet()) {
			Set<DefaultWeightedEdge> set = grafo.edgesOf(driver);
			int numGare=  this.calcolaNumGare(set);
			if (numGare> maxGare) {
				maxGare = numGare;
				bestDriver = new Driver(driver);
			}
		}
		return bestDriver;
	}
	
	
	public SimpleWeightedGraph<Driver, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	private int calcolaNumGare(Set<DefaultWeightedEdge> set) {
		int sum = 0;
		for (DefaultWeightedEdge dfe : set) {
			sum += grafo.getEdgeWeight(dfe);
		}
		return sum;
	}

	private void creaGrafo(Constructor constructor) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		List<Driver> drivers = new ArrayList<>(fdao.getDriversOfConstructor(constructor, driverIdMap));
		Graphs.addAllVertices(grafo, drivers);
		
		List<DriversPair> archi =  new ArrayList<>(fdao.getArchi(constructor, driverIdMap));
		
		for (DriversPair coppia : archi) {
			if (!coppia.getD1().equals(coppia.getD2())){
				Graphs.addEdgeWithVertices(grafo, coppia.getD1(), coppia.getD2(), coppia.getNumRaceInsieme());
			}
		}
		
		
		System.out.println(String.format("Grafo creato, %d vertici e %d archi", grafo.vertexSet().size(), grafo.edgeSet().size()));
	}

	public List<Driver> getDreamTeam(int k) {
		dreamTeam = new ArrayList<Driver>();
		List<Driver> parziale = new ArrayList<>();
		int bestValue = Integer.MIN_VALUE;
		
		this.recursive(parziale, k, 0, bestValue);
		
		return  dreamTeam;
	}

	
	public void recursive(List<Driver> parziale, int k, int valore, int maxValore) {
		if (parziale.size()==k) {
			if (this.calcolaValore(parziale, grafo)>maxValore) {
				dreamTeam =new ArrayList<>(parziale);
				return;
			}
		}
		for (Driver driver : grafo.vertexSet()) {
			//DepthFirstIterator<Driver, DefaultWeightedEdge> dfi = new DepthFirstIterator<>(grafo , driver);
			if (!parziale.contains(driver)){
				parziale.add(driver);
				
				int val = this.calcolaValore(parziale, grafo);
				recursive(parziale, k, val, maxValore);
			
				parziale.remove(parziale.size()-1);
			}
		}
	}
			
			

	private int calcolaValore(List<Driver> parziale, SimpleWeightedGraph<Driver, DefaultWeightedEdge> grafo2) {
		int sum =0 ;
		for (int i = 0 ; i<parziale.size(); i++) {
			for (int j = i+1; j<parziale.size(); j++) {
				if (!parziale.get(i).equals(parziale.get(j))) {
					DefaultWeightedEdge dfe=grafo.getEdge(parziale.get(i), parziale.get(j));
					if (dfe!=null)
					sum += grafo.getEdgeWeight(dfe);
				}
			}
		}
		
		return sum;
	}

}
