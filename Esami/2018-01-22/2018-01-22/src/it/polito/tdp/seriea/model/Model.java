package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {

	private SerieADAO sdao;
	private List<Team> teams ;
	private List<Season> seasons;
	
	private List<TeamGoalsSeason> listPuntiClassifica ;
	private SimpleDirectedWeightedGraph<TeamGoalsSeason, DefaultWeightedEdge> grafo;
	
	private int maxPunteggio = Integer.MIN_VALUE;
	private TeamGoalsSeason annataOro;
	
	private List<TeamGoalsSeason> migliorCamminoVirtuoso;
	
	public Model() {
		sdao = new SerieADAO();
		teams = new ArrayList<Team>(sdao.listTeams());
		seasons =new ArrayList<Season>(sdao.listAllSeasons());
		
	}
	
	public List<Team> getSquadre() {
		return teams;
	}
	

	public List<Season> getSeasons() {
		return seasons;
	}

	public List<TeamGoalsSeason> calcolaPuntiInClassifica(Team team) {
		this.listPuntiClassifica  = new ArrayList<TeamGoalsSeason>();
		for (Season s: this.getSeasons()) {
			TeamGoalsSeason tgs = sdao.getPuntiInClassifica(team, s);
			if (tgs!=null) {
				listPuntiClassifica.add(tgs);
			}
		}
		return listPuntiClassifica ;
	}
	
	public TeamGoalsSeason trovaAnnataOro(Team team) {
		int punteggio = 0;
		this.creaGrafo(team);
		for (TeamGoalsSeason tgs : this.getGrafo().vertexSet()) {
			punteggio = this.calcolaAnnata(this.grafo.incomingEdgesOf(tgs), this.grafo.outgoingEdgesOf(tgs));
			if (punteggio > maxPunteggio) {
				this.annataOro = tgs;
				this.maxPunteggio = punteggio;
			}
		}
		return annataOro;
	}
	
	public int  getMaxPunteggio() {
		return this.maxPunteggio;
	}
	
	private int calcolaAnnata(Set<DefaultWeightedEdge> incomingEdgesOf, Set<DefaultWeightedEdge> outgoingEdgesOf) {
		int somma = 0;
		for (DefaultWeightedEdge dwe : incomingEdgesOf) {
			somma += grafo.getEdgeWeight(dwe);
		}
		for (DefaultWeightedEdge dwe : outgoingEdgesOf) {
			somma -= grafo.getEdgeWeight(dwe);
		}
		return somma;
	}

	public SimpleDirectedWeightedGraph<TeamGoalsSeason, DefaultWeightedEdge> getGrafo(){
		return this.grafo;
	}

	private void creaGrafo(Team team) {
		grafo = new SimpleDirectedWeightedGraph<TeamGoalsSeason, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		// archi
		for (TeamGoalsSeason tgs : this.listPuntiClassifica) {
			if (!grafo.vertexSet().contains(tgs))
				grafo.addVertex(tgs);
		}
		
		// vertici
		for (int i=0; i<this.listPuntiClassifica.size(); i++) {
			for (int j=i+1; j<this.listPuntiClassifica.size(); j++) {
				TeamGoalsSeason tgs1 = this.listPuntiClassifica.get(i);
				TeamGoalsSeason tgs2 = this.listPuntiClassifica.get(j);
				if (tgs1 != null && tgs2 !=null && !tgs1.equals(tgs2)) {
					if (tgs1.getPunti()>tgs2.getPunti()) {
						Graphs.addEdge(grafo, tgs1, tgs2, tgs1.getPunti()-tgs2.getPunti());
					}else if (tgs1.getPunti()< tgs2.getPunti()) {
						Graphs.addEdge(grafo, tgs2, tgs1, tgs2.getPunti()-tgs1.getPunti());
					}
				}
			}
		}
		
	}

	
	public List<TeamGoalsSeason> camminoVirtuoso(Team team) {
		
		migliorCamminoVirtuoso = new ArrayList<TeamGoalsSeason>();

		List<TeamGoalsSeason> prova =new ArrayList<TeamGoalsSeason>();
		
		this.recursive(prova);
		
		return migliorCamminoVirtuoso;
	}
	
	public void recursive(List<TeamGoalsSeason> camminoProva){
		int i = 0;
		while (i<this.listPuntiClassifica.size()) {
			camminoProva.clear();
			TeamGoalsSeason tgs = this.listPuntiClassifica.get(i);
			camminoProva.add(tgs);
			
			for (int j = i+1; j<this.listPuntiClassifica.size(); j++) {
				TeamGoalsSeason successiva = this.listPuntiClassifica.get(j);
				if ( successiva!= null && camminoProva.size()>=1 &&
						 successiva.getPunti() > camminoProva.get(camminoProva.size()-1).getPunti()) {
					camminoProva.add(successiva);
					if (camminoProva.size()>this.migliorCamminoVirtuoso.size()) {
						this.migliorCamminoVirtuoso = new ArrayList<TeamGoalsSeason>(camminoProva);
					}
				}else {
					break;
				}
			}
			i++;
		}
	}
	
	
	
	

}
