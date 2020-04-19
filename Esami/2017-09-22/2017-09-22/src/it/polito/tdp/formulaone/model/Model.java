package it.polito.tdp.formulaone.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {

	private FormulaOneDAO fdao ;
	private List<Season> seasons;
	private RaceIdMap raceIdMap;
	private DriverIdMap driverIdMap;
	
	private SimpleWeightedGraph<Race, DefaultWeightedEdge> grafo;
	private double maxPeso = Double.MIN_VALUE;
	
	private Simulatore simulatore;
	
	public Model() {
		fdao = new FormulaOneDAO();
		seasons = new ArrayList<Season>(fdao.getAllSeasons());
	}
	
	public List<Season> getSeasons(){
		return this.seasons;
	}

	public List<CoppiaRaces> getMaxArco(Season season){
		this.creaGrafo(season);
		if (grafo == null) {
			new RuntimeException("Creare il grafo");
			return null;
		}
		
		List<CoppiaRaces> bestResult = new ArrayList<>();
		for (DefaultWeightedEdge edge : grafo.edgeSet()) {
			if (grafo.getEdgeWeight(edge)> maxPeso) {
				bestResult.clear();
				bestResult.add(new CoppiaRaces(grafo.getEdgeSource(edge), grafo.getEdgeTarget(edge), (int)(grafo.getEdgeWeight(edge) ) ));
			}
			if (grafo.getEdgeWeight(edge) ==maxPeso) {
				bestResult.add(new CoppiaRaces(grafo.getEdgeSource(edge), grafo.getEdgeTarget(edge), (int)(grafo.getEdgeWeight(edge) ) ));
			}
		}
		Collections.sort(bestResult);
		return bestResult;
	}
	
	
	public SimpleWeightedGraph<Race, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	private void creaGrafo(Season season) {
		
		grafo = new SimpleWeightedGraph<Race, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		
		//vertici
		raceIdMap = new RaceIdMap();
		Graphs.addAllVertices(grafo, fdao.getRaces(season, raceIdMap));
		
		//archi
		List<CoppiaRaces> archi = new ArrayList<>(fdao.getArchi(season, raceIdMap));
		for (CoppiaRaces cr : archi) {
			if (cr.getRace1()!=cr.getRace2())
				Graphs.addEdge(grafo, cr.getRace1(), cr.getRace2(), cr.getNumPartecipantiComuni());
		}
		
		
	}

	public List<Race> getRacesForSimulation(Season season){
		return fdao.getRacesForsimulation(season, raceIdMap);
	}
	
	
	public void simula(Race race, double p, int t) {
		driverIdMap = new DriverIdMap();
		List<Driver> drivers=new ArrayList<>(fdao.getDriversOfRace(race, driverIdMap));
		race.setPartecipanti(drivers);
		for (Driver d: race.getPartecipanti()) {
			List<LapTime> giri = new ArrayList<>(fdao.getLapTimesOfDriverInRace(d, race));
			d.setGiri(giri);
		}
		
		simulatore = new Simulatore(race, p, t);
		simulatore.init();
		simulatore.run();
		
	}

	public Simulatore getSimulatore() {
		if (simulatore==null)
			return null;
		return simulatore;
	}

	
	
}
