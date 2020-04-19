package it.polito.tdp.borders.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	
	private BordersDAO bdao;
	
	private SimpleGraph<Country, DefaultEdge> grafo;
	
	private Simulatore simulatore;
	
	
	public Model() {
	}

	
	public List<Country> calcolaConfini(Year year) {
		this.creaGrafo(year);
		if (grafo==null) {
			new RuntimeException("Crea grafo");
		}
		List<Country> countries = new ArrayList<>(grafo.vertexSet());
		Collections.sort(countries);
		return countries;
	}

	public SimpleGraph<Country, DefaultEdge> getGrafo() {
		return grafo;
	}

	
	private void creaGrafo(Year year) {
		bdao = new BordersDAO();

		grafo = new SimpleGraph<>(DefaultEdge.class);
		
		List<Country> vertici = new ArrayList<>(bdao.geCountriesFromAnno(year));
		Graphs.addAllVertices(grafo, vertici);
		
		List<Contiguity> archi = new ArrayList<>(bdao.getConfini(year));
		for (Contiguity c:archi) {
			if (c.getState1no()!=c.getState2no()) {
				Country one= this.getVertex(c.getState1no());
				Country two= this.getVertex(c.getState2no());
				if (one!=null && two !=null) {
					Graphs.addEdgeWithVertices(grafo, this.getVertex(c.getState1no()), this.getVertex(c.getState2no()));
					one.addAdiacenti(two);
					two.addAdiacenti(one);
				}
			}
		}
		
		System.out.println(String.format("Grafo creato, %d vertici e %d archi", grafo.vertexSet().size(), grafo.edgeSet().size()));
	}
	
	private Country getVertex(int stateNumber) {
		for(Country c: grafo.vertexSet()) {
			if (c.getcCode()==stateNumber) {
				return c;
			}
		}
		return null;
	}



	public void simulazioneMigranti(Country country) {
		simulatore = new Simulatore();
		simulatore.init(country, grafo);
		simulatore.run();
		
	}

	public List<CountryStanz> getStanziali(){
		Map<Country, Integer> map= simulatore.getStanziali();
		List<CountryStanz> list=new ArrayList<>();
		
		for (Country c: map.keySet()) {
			list.add(new CountryStanz(c, map.get(c)));
		}
		
		
		Collections.sort(list);
		
		
		return list;
		
	}



}
