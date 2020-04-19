package it.polito.tdp.music.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.music.db.MusicDAO;

public class Model {
	
	private MusicDAO dao;
	private static int primiInClassifica = 20;
	private List<ArtistListening> primi20;
	private Month mese;
	private SimpleWeightedGraph<Country, DefaultWeightedEdge> grafo;
	
	public Model() {
		dao=new MusicDAO();
	}

	public List<ArtistListening> getPiuAscoltatiDelMese(Month month) {
		this.mese=month;
		List<ArtistListening> allClassifica = new ArrayList<>(dao.getTrendsOfMonh(mese));
		this.primi20 = new ArrayList<>();
		
		for (int i=0; i<primiInClassifica; i++) {
			primi20.add(allClassifica.get(i));
		}
		
		return primi20;
	}

	public void getDistanzaMax() {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		List<Country> vertici = new ArrayList<>(dao.getAllCountries());
		
		if (vertici.size()!=0)
			Graphs.addAllVertices(grafo, vertici);
		
		
		
		
		
	}

}
