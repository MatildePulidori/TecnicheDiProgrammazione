package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SerieADAO sdao;
	private List<Team> teams;
	private TeamIdMap teamIdMap;
	private Simulatore simulatore;
	
	private SimpleWeightedGraph<Team, DefaultWeightedEdge> grafo;
	
	
	public Model() {
		sdao = new SerieADAO();
	}


	public List<Season> getSeasons(){
		return sdao.listSeasons();
	}
	
	
	public void creaGrafo() {
		
		grafo = new SimpleWeightedGraph<Team, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		// vertici
		teamIdMap = new TeamIdMap();
		teams = new ArrayList<Team>(sdao.listTeams(teamIdMap));
		Graphs.addAllVertices(grafo, teams);
	
		// archi
		List<TeamCounterMatches> list = new ArrayList<TeamCounterMatches>(sdao.getTeamCounterMatches(teamIdMap));
		for(TeamCounterMatches tcm : list) {
			if (!tcm.getTeamA().equals(tcm.getTeamB()))
				Graphs.addEdge(grafo, tcm.getTeamA(), tcm.getTeamB(), tcm.getPartiteGiocate());
		}
	}
	
	public SimpleWeightedGraph<Team, DefaultWeightedEdge> getGrafo(){
		if (grafo == null) {
			new RuntimeException("Creare un grafo");
		}
		return grafo;
	}

	public List<TeamCounterMatches> getAvversari (Team team) {
		List<Team> archi = new ArrayList<Team>(Graphs.neighborListOf(grafo, team));
		List<TeamCounterMatches> partite = new ArrayList<TeamCounterMatches>();
		for (Team t: archi) {
			if (!t.equals(team)) {
				DefaultWeightedEdge e = grafo.getEdge(team, t);
				partite.add(new TeamCounterMatches(team, t, (int) grafo.getEdgeWeight(e)));
			}
		}
		Collections.sort(partite);
		return partite;
		
	}


	public Simulatore getSimulatore() {
		return simulatore;
	}
	
	public void simulaTifosi(Season season) {
		simulatore = new Simulatore(season, teamIdMap);
		simulatore.init();
		simulatore.run();
		simulatore.getTeamSeason();
		
	}
	
	public String getRisultatiSimulazione() {
		return this.simulatore.getRisultati();
	}
	
	
	
}
