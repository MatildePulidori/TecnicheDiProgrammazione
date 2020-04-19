package it.polito.tdp.porto.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PaperIdMap;
import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private PortoDAO pdao;
	private List<Author> autori;
	private List<Paper> pubblicazioni; 
	private Graph<Author, DefaultEdge> grafo ;
	
	AuthorIdMap authorIdMap;
	PaperIdMap paperIdMap;
	
	
	public Model() {
		
		pdao = new PortoDAO();
		
		authorIdMap = new AuthorIdMap();
		paperIdMap = new PaperIdMap();
		
		autori = new ArrayList<Author>(pdao.getTuttiAutori(authorIdMap));
		pubblicazioni = new ArrayList<Paper>(pdao.getTuttiArticoli(paperIdMap));
		
	}

	public List<Author> getAutori() {
		return autori;
	}
	

	public List<Paper> getPubblicazioni() {
		return pubblicazioni;
	}

	public Graph<Author, DefaultEdge> createGraph() {
		
		// creao il grafo
		grafo = new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		
		// aggiungo i vertici 
		Graphs.addAllVertices(grafo, autori);
		
		//per ogni autore
		for (Author autore : grafo.vertexSet()) {
			
			//trovo le sue pubblicazioni 
			List<Paper> pubblicazioniDiAutore = new ArrayList<Paper>(pdao.getArticoli(paperIdMap, authorIdMap, autore.getId()));
			
			// per ogni pubblicazione trovo i coautori
			for (Paper p : pubblicazioniDiAutore) {
				//se ce ne sono
				List<Author> coautori = new ArrayList<Author>(pdao.getAllCoAuthor(authorIdMap, p.getEprintid(), autore.getId()));
				if (coautori!=null){
				// per ogni coautore aggiungo un arco
					for (Author coautore : coautori) {
						Graphs.addEdgeWithVertices(grafo, autore, coautore);
					}
				}
			}
		}
		return grafo;
	}
	
	public List<Author> trovaCoautori(Author primoAutore) {
	
		Author autore = authorIdMap .get(primoAutore);
		
		List<Author> coautori = new ArrayList<Author>(Graphs.neighborListOf(grafo, autore));
		
		return coautori;
	}

	public List<Author> trovaNonCoautori (Author primoAutore){
		Author autore = authorIdMap .get(primoAutore);
		List<Author> allAuthors = new ArrayList<Author>(grafo.vertexSet());
		allAuthors.removeAll(this.trovaCoautori(autore));
		allAuthors.remove(autore);
		List<Author> nonCoautori =  new ArrayList<Author>(allAuthors);
		
		return nonCoautori;
	}

	public List<Paper> trovaSequenza(Author primoAutore, Author secondoAutore) {
		
		
		DijkstraShortestPath<Author, DefaultEdge> spa = new DijkstraShortestPath<Author, DefaultEdge>(grafo, primoAutore, secondoAutore);
		List<DefaultEdge> archi = new ArrayList<DefaultEdge> (spa.getPathEdgeList());
		List<Paper> papers = new ArrayList<Paper>();
		
		for (DefaultEdge arco : archi ) {
			
			Author autoreSorgente = grafo.getEdgeSource(arco);
			Author autoreDestinazione = grafo.getEdgeTarget(arco);
			
			papers.add(this.trovaArticoloComune(autoreSorgente, autoreDestinazione));
		}
		

		return papers;
	}

	private Paper trovaArticoloComune(Author autoreSorgente, Author autoreDestinazione) {
		
		for (Paper p : autoreSorgente.getPapers()) {
			if (p.getAuthors().contains(autoreDestinazione))
				return p;
		}
		return null;
	}

}
