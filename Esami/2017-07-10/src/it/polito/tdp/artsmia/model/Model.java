package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private ArtsmiaDAO dao;
	private ArtObjectIdMap artObjIdMap;
	private List<ArtObject> artObjExhibition;
	
	private Graph<ArtObject, DefaultWeightedEdge> grafo;
	
	private List<ArtObject> best;
	private int bestDim = Integer.MIN_VALUE;
	
	public Model() {

		dao = new ArtsmiaDAO();
		artObjIdMap = new ArtObjectIdMap();
	}
		
	
	public List<ArtObject> getArtObjects(){
		return this.artObjExhibition;
	}
	
	public Graph<ArtObject, DefaultWeightedEdge> getGrafo(){
		return this.grafo;
	}
	
	public void creaGrafo() {
		
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		artObjExhibition = new ArrayList<ArtObject>(dao.listObjectsExhibition(artObjIdMap));
		Graphs.addAllVertices(this.grafo,this.artObjExhibition);
		
		// archi 
		List<ArtObjectCount> listCoppie = new ArrayList<>(dao.getNumberOfExhibitionComuni(artObjIdMap));
		for (ArtObjectCount aoc : listCoppie) {
			Graphs.addEdgeWithVertices(grafo, aoc.getO1(), aoc.getO2(), aoc.getCount());
		}
		
		
	}


	public ArtObject getArtObject(int objId) {
		for (ArtObject ao : this.artObjExhibition) {
			if (ao.getId() == objId) {
				return ao;
			}
		}
		ArtObject probabile =  dao.getArtObject (objId) ;
		if ( probabile != null)
			return probabile;
		return null;
	}


	public void popolaLun(List<Integer> lun, ArtObject o) {
		for (int i=1; i<Graphs.neighborListOf(grafo, o).size(); i++) {
			lun.add(i+1);
		}
	}


	public List<ArtObject> getCammino(int lun, ArtObject o) {
		
		ArtObject start = this.trovaVerticePartenza(o);
		
		List<ArtObject> parziale = new ArrayList<>();
		parziale.add(start);
		
		this.best = new ArrayList<>();
		best.add(start);
		
		cerca(parziale, lun,  1);
		
		return best;
		
	}


	private void cerca(List<ArtObject> parziale, int lun, int livello) {
		
		if (livello == lun )  {
			int dimParz= calcolaDimensione(parziale);
			if (dimParz> bestDim) {
				bestDim = dimParz;
				best = new ArrayList<>(parziale);
			}
		}
		
		ArtObject ultimo = parziale.get(parziale.size() -1) ;
		List<ArtObject> adiacenti = Graphs.neighborListOf(grafo, ultimo);
		
		for (ArtObject obj : adiacenti) {
			if (!parziale.contains(obj) && obj.getClassification() !=null &&
					obj.getClassification().equals(ultimo.getClassification())){
				parziale.add(obj);
				cerca(parziale, lun, livello+1);
				parziale.remove(parziale.size()-1);
			}
				
		}
		
		
	}


	private int calcolaDimensione(List<ArtObject> parziale) {
		int peso = 0;
		for (int i = 0; i < parziale.size() - 1; i++) {
			DefaultWeightedEdge e = grafo.getEdge(parziale.get(i), parziale.get(i + 1));
			int pesoarco = (int) grafo.getEdgeWeight(e);
			peso += pesoarco;
		}
		return peso;
	}


	private ArtObject trovaVerticePartenza(ArtObject o) {
		ArtObject start = null;
		for (ArtObject ao : this.getGrafo().vertexSet()) {
			if (ao.getId() == o.getId()) {
				start = ao;
				break;
			}
		}
		if (start == null)
			throw new IllegalArgumentException("Vertice " + o.getId() + " non esistente");
		return start;
	}

}
