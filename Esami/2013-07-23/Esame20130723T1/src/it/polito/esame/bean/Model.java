package it.polito.esame.bean;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.esame.dao.IscrittiDAO;

public class Model {
	
	private List<Studente> studenti;
	private StudenteIdMap stIdMap;
	private List<Corso> corsi;
	private CorsoIdMap corsoIdMap;
	private IscrittiDAO dao;
	private Graph<Studente, DefaultWeightedEdge> grafo;
	private List<Studente> miglioreLista;
	public int maxCorsiInsieme = Integer.MIN_VALUE;
	
	public Model() {
		dao= new IscrittiDAO();
		stIdMap = new StudenteIdMap();
		corsoIdMap = new CorsoIdMap();
		
		this.studenti= new ArrayList<Studente>(dao.getAllStudente(stIdMap));
		this.corsi = new ArrayList<Corso>(dao.getAllCorso(corsoIdMap));
		
	}

	public List<Studente> getStudenti() {
		
		return studenti;
	}

	public Studente getStudente(int matricola) {
		return stIdMap.getStudente(matricola);
	}
	
	public List<Corso> getCorsi() {
		return corsi;
	}

	public List<Corso> getCorsiStudente(Studente s) {
		List<Corso> corsiStudente = new ArrayList<Corso>();
		if ( this.getStudente(s.getMatricola())!=null) {
			corsiStudente =  dao.getCorsiByStudente(s, stIdMap, corsoIdMap);
		}
		Set<Corso> corsi = new HashSet<Corso>(corsiStudente);
		s.setCaricodidattico(corsi);
		return corsiStudente;
	}

	public List<Studente> trovaSimili(Studente s) {
		this.creaGrafo(s);
		
		miglioreLista= new ArrayList<Studente>();
		
		for (Studente other : grafo.vertexSet()) {
			if (!other.equals(s)) {
				DefaultWeightedEdge edge = grafo.getEdge(s, other);
				if (grafo.getEdgeWeight(edge) == this.maxCorsiInsieme) {
					miglioreLista.add(other);
				}
			}
		}
		
		return miglioreLista;
		
	}
	
	private void creaGrafo(Studente s) {
		grafo = new SimpleWeightedGraph<Studente, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Studente studente = stIdMap.getStudente(s);
		grafo.addVertex(studente);
		
		//vertici
		for (Corso corso : studente.getCaricodidattico()) {
			
			//Per ogni corso a cui è iscritto @s, cerco gli altri iscritti
			List<Studente> studentiColleghi = new ArrayList<Studente>(dao.getStudentiByCorso(corso, stIdMap, corsoIdMap));
			// Per ogni studente @collega del corso, se non è ancora nel grafo, lo inserisco
			for(Studente collega : studentiColleghi) {
				if (!grafo.vertexSet().contains(stIdMap.getStudente(collega))) {
					grafo.addVertex(stIdMap.getStudente(collega));
				}
			}
		}
		
		
		//archi
		for (Studente other : grafo.vertexSet()) {
			if (!other.equals(s)) {
				Studente altro = (stIdMap.getStudente(other));
				dao.getCorsiByStudente(altro, stIdMap, corsoIdMap);
				int weight = this.cercaCorsiComuni(studente, altro);
				Graphs.addEdge(grafo, studente, altro, weight);
				
				if (weight>this.maxCorsiInsieme) {
					this.maxCorsiInsieme=weight;
				}
			}
		}
	}

	
	private int cercaCorsiComuni(Studente s, Studente other) {
		List<Corso> corsiComuni = new ArrayList<Corso>();
		
		for (Corso corso : s.getCaricodidattico()) {
			for (Corso corsoCollega:  other.getCaricodidattico()) {
				
				if (corso.equals(corsoCollega) && !corsiComuni.contains(corso)) {
					corsiComuni.add(corso);
					
				}
			}
		}
		return corsiComuni.size();
	
	}

	
	

}
