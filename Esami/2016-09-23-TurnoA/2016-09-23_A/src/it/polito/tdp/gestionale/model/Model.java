package it.polito.tdp.gestionale.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.gestionale.db.DidatticaDAO;

public class Model {

	private List<Corso> corsi;
	private List<Studente> studenti;
	private DidatticaDAO dDAO;
	
	private StudenteIdMap sIdMap;
	private CorsoIdMap cIdMap;
	
	
	private SimpleGraph<Nodo, DefaultEdge> grafo;
	
	private List<Corso> migliorCammino ;

	public Model() {
		dDAO = new DidatticaDAO();
		
		sIdMap = new StudenteIdMap();
		cIdMap = new CorsoIdMap();
		
		
		studenti = new ArrayList<Studente>(dDAO.getTuttiStudenti(sIdMap));
		corsi = new ArrayList<Corso>(dDAO.getTuttiICorsi(cIdMap));
	}

	
	
	public List<FrequentazioneCorso> statistiche() {
		this.creaGrafo();
		
		List<FrequentazioneCorso> studentiGrafo = new ArrayList<FrequentazioneCorso>();
		
		for (Nodo n : this.grafo.vertexSet()) {
			if (n instanceof Studente) {
				FrequentazioneCorso fc  =  new FrequentazioneCorso(Graphs.neighborListOf(grafo, n).size());
				if (!studentiGrafo.contains(fc)){
					studentiGrafo.add(fc);
					this.getFreq(fc, studentiGrafo).addNumStudenti(1);
				} else if (studentiGrafo.contains(fc)) {
					this.getFreq(fc, studentiGrafo).addNumStudenti(1);
				}
			}
		}
		
		Collections.sort(studentiGrafo);
		return studentiGrafo;
	}
	
	
	private FrequentazioneCorso getFreq(FrequentazioneCorso fc, List<FrequentazioneCorso>  list) {
		for (FrequentazioneCorso f: list) {
			if (f.equals(fc))
				return f;
		}
		return null;
	}

	
	private void creaGrafo() {
		
		grafo = new SimpleGraph<Nodo, DefaultEdge>(DefaultEdge.class);
		
		// vertici
		Graphs.addAllVertices(grafo, this.studenti);
		Graphs.addAllVertices(grafo, this.corsi);
	
		
		// archi
		for (Corso c : this.corsi) {
			dDAO.getStudentiIscrittiAlCorso(c, sIdMap, cIdMap);
			for (Studente s : cIdMap.get(c).getStudenti()) {
				if (this.controllaCorrispondenza(c, s)==true)
					Graphs.addEdgeWithVertices(grafo, c, s);
			}
		}
		System.out.println(String.format("Grafo creato, %d vertici e %d archi", grafo.vertexSet().size(), grafo.edgeSet().size()));
	}

	
	private boolean controllaCorrispondenza(Corso c, Studente s) {
		if (s.getCorsi().contains(c) && c.getStudenti().contains(s))
			return true;
		return false;
	}



	public List<Corso> cercaNumCorsiMinimo() {
		
		migliorCammino = new ArrayList<Corso>();
		
		List<Corso> parziale = new ArrayList<Corso>();
		
		List<Studente> studentiRimasti = new ArrayList<Studente>(this.studenti);
		
		this.recursive(migliorCammino, parziale, studentiRimasti);
		
		return migliorCammino;
		
	}


	private void recursive(List<Corso> soluzMigliore, List<Corso> prova, List<Studente> rimastiDaIncontrare) {
		
		if (prova.size()<soluzMigliore.size() && rimastiDaIncontrare.size() == 0) {
			migliorCammino = new ArrayList<Corso>(prova);
			return;
		}
		
		for (Corso c : prova) {
			rimastiDaIncontrare.removeAll(c.getStudenti());
		}
		if (rimastiDaIncontrare.isEmpty()) {
			if (soluzMigliore.isEmpty())
				soluzMigliore.addAll(prova);
			if (prova.size()<soluzMigliore.size()) {
				soluzMigliore.clear();
				soluzMigliore.addAll(prova);
			}
		}
		
		for(Corso c : this.corsi) {
			if (prova.isEmpty() || c.getCodins().compareTo(prova.get(prova.size()-1).getCodins())>0) {
				prova.add(c);
				recursive(soluzMigliore, prova, rimastiDaIncontrare);
			}
		}
		
	}
}
