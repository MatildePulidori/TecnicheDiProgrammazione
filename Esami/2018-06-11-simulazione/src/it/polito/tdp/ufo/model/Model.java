package it.polito.tdp.ufo.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.ufo.db.SightingsDAO;
public class Model {
	
	private SightingsDAO sdao;
	private List<Integer> years ;
	private List<YearSights> ySights;
	private StateIdMap stateIdMap;

	private SimpleDirectedGraph<State, DefaultEdge> grafo; 
	
	private List<State> successori;
	private List<State> predecessori;
	private List<State> raggiungibili;
	
	private List<State> seqMassima;
	
	
	public Model() {
		sdao = new SightingsDAO();
		years = new ArrayList<Integer>(sdao.getYears());
	}

	public List<YearSights> getYearSights() {
		ySights =  new ArrayList<YearSights>();
		for (int anno : years) {
			YearSights temp = sdao.getYearSights(anno);
			if (!ySights.contains(temp)){
				ySights.add(temp);
			}
		}
		return this.ySights;
	}

	public void creaGrafo(YearSights yS) {
		
		grafo = new SimpleDirectedGraph<State, DefaultEdge>(DefaultEdge.class);
		
		// vertici 
		stateIdMap = new StateIdMap();
		Graphs.addAllVertices(grafo, sdao.getStates(yS, stateIdMap));
		
		// archi
		List<StatePair> archi = new ArrayList<StatePair>(sdao.getStatePairs(yS, stateIdMap));
		for (StatePair pair : archi) {
			grafo.addEdge(pair.getState1(), pair.getState2());
		}
		
		
	}

	public SimpleDirectedGraph<State, DefaultEdge> getGrafo() {
		return grafo;
	}

	public List<State> getStati(YearSights yS) {
		List<State> stati = new ArrayList<State>(grafo.vertexSet());
		return stati;
	}

	public void analizza(State state) {
		this.successori = new ArrayList<State>(this.getSuccessori(state));
		this.predecessori = new ArrayList<State>(this.getPredecessori(state));
		this.raggiungibili = new ArrayList<State>();
		this.getRaggiungibili(state, raggiungibili);
			
	}

	public List<State> successori(){
		if(this.successori != null)
			return this.successori;
		return null;
	}
	
	public List<State> predecessori(){
		if (this.predecessori !=null)
			return this.predecessori;
		return null;
	}
	
	public List<State> raggiungibili(){
		if (this.raggiungibili != null)
			return this.raggiungibili;
		return null;
	}
	
	private List<State> getRaggiungibili(State state, List<State> ragg) {
		BreadthFirstIterator<State, DefaultEdge> bfv =
				new BreadthFirstIterator<State, DefaultEdge>(this.grafo, state) ;
	
		bfv.next() ; // non voglio salvare il primo elemento
		while(bfv.hasNext()) {
			ragg.add(bfv.next()) ;
		}
		return ragg ;

	}

	private List<State> getPredecessori(State state) {
		return Graphs.predecessorListOf(grafo, state);
		
	}

	private List<State> getSuccessori(State state) {
		return Graphs.successorListOf(grafo, state);
		
	}

	public List<State> seqAvvistamenti(State state) {
		this.seqMassima = new ArrayList<State>();
		
		List<State> parziale = new ArrayList<State>();
		parziale.add(state);
		this.cercaSequenzaOttima(parziale);
		
		return this.seqMassima;
		
	}

	private void cercaSequenzaOttima(List<State> parziale) {
		
		if (parziale.size()>this.seqMassima.size()) {
			this.seqMassima = new ArrayList<State>(parziale);
		}
		
		List<State> candidati = this.getSuccessori(parziale.get(parziale.size()-1));
		
		for (State s  : candidati) {
			if (!parziale.contains(s)) {
				parziale.add(s);
				this.cercaSequenzaOttima(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}
		
}
