package it.polito.tdp.borders.model;
import it.polito.tdp.borders.db.BordersDAO;
import java.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.BreadthFirstIterator;

public class Model {

	private BordersDAO bdao;
	private List<Country> countries;
	private List<Border> borders;
	private SimpleGraph<Country, DefaultEdge> graph;
	private List<Country> connessi;
	
	
	public Model() {
		bdao = new BordersDAO();
		countries = new ArrayList<Country>(bdao.loadAllCountries());
	}
	
	/**
	 * Metodo che mi da la lista delle countries
	 * @return
	 */
	public List<Country> getCountries() {
		return countries;
	}
	
	/**
	 * Metodo che ritorna tutti i confini (di tipo 1) che ci sono
	 * NON USATO
	 * @param anno
	 * @return
	 */
	public List<Border> getConfini(){
		borders = new ArrayList<Border>(bdao.getCountryPairs());
		return borders;
	}
	
	/**
	 * Metodo che ritorna tutti i confini (di tipo 1) che ci sono fino all'@anno
	 * passato da parametro .
	 * @param anno
	 * @return
	 */
	public List<Border> getConfiniAnno(int anno){
		borders = new ArrayList<Border>(bdao.getCountryPairs(anno));
		return borders;
	}
	
	/**
	 * Metodo che crea il grafo semplice di tutti i confini(di tipo 1) fino all'@anno
	 * passato da parametro.
	 * @param anno
	 */
	public Graph<Country, DefaultEdge> creaGrafo(int anno) {
		
		//definizione grafo
		this.graph = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		
		// aggiunta vertici
		List<Country > vertici = new ArrayList<Country>(this.getVerticiAnno(anno));
		Graphs.addAllVertices(this.graph, this.getVerticiAnno(anno));
		
		//Per ogni country dell'inseme dei vertici nell'anno indicato 
		for (Country c : vertici) {
			// cerco i suoi confinanti
			List<Country> confinantiC = new ArrayList<Country>(this.getConfinanti(c));
			for (Country confinante : confinantiC) {
				if (!confinante.equals(c)) {
					graph.addEdge(c, confinante);
				}
			}	
		}
		return graph;
	}

	/**
	 * Metodo che ritorna il grafo creato.
	 * @return
	 */
	public SimpleGraph<Country, DefaultEdge> getGraph() {
		return graph;
	}
	
	/**
	 * Numero connessi
	 * @return
	 */
	public Object getNumberOfConnectedComponents() {
		return this.graph.vertexSet().size();
	}
	
	/**
	 * Metodo che ritorna una mappa di countries e per ognuna associa 
	 * il numero di countries confinanti riportate nel grafo (quindi relative all'anno indicato)
	 * @return
	 */
	public Map<Country, Integer> getCountryCounts() {
		Map<Country, Integer> mappaConnessi = new HashMap<Country, Integer>();
 		for (Country c : this.graph.vertexSet()) {
			mappaConnessi.put(c, Graphs.neighborListOf(this.graph, c).size());
		}
		return mappaConnessi;
	}
	
	
	/**
	 * Metodo che dato un @paese trova tutti i connessi con un algoritmo ricorsivo
	 * @param paese
	 * @return
	 */
	public List<Country> trovaConnessi(Country paese) {
		connessi = new ArrayList<Country>();
		
		// livello 1
		int livello = 1;
		connessi.add(paese);
		
		// chiamo il metodo ricorsivo
		this.recursive (paese,  connessi, livello);
		
		return connessi;
	}
	 
	/**
	 * Metodo ricorsivo chiamato da @trovaConnessi
	 * @param paese
	 * @param connessi
	 * @param livello
	 */
	private void recursive(Country paese, List<Country> connessi, int livello) {
		 for (Country c : Graphs.neighborListOf(this.graph, paese)) {
				if (!connessi.contains(c)) {
					connessi.add(c);
					this.recursive(c, connessi, livello+1);
				}	
			}
	}


	/**
	 * Metodo che dato il @paese trova tutti i connessi con un BreadthFirstIterator
	 * @param start
	 * @return
	 */
	public List<Country> trovaConnessiBreadthFirstIterator(Country start) {
		connessi = new ArrayList<Country>();
		BreadthFirstIterator<Country, DefaultEdge> bfi = new BreadthFirstIterator<Country, DefaultEdge>(this.graph, start);
		while(bfi.hasNext()) {
			connessi.add(bfi.next());
		}
		return connessi;
	}

	/*----- METODI PRIVATI -----*/

	/**
	 * Metodo privato che ritorna, dato uno stato @c, i suoi confinanti
	 * @param c
	 * @return
	 */
	private List<Country> getConfinanti(Country c) {
		List<Country> confinanti = new ArrayList<Country>();
		
		for (Border b : this.borders) {
			if (b.getCountryA().equals(c)) {
				if (!confinanti.contains(b.getCountryB()))
					confinanti.add(b.getCountryB());
			}
			if (b.getCountryB().equals(c)) {
				if(!confinanti.contains(b.getCountryA()))
					confinanti.add(b.getCountryA());
			}
		}
		return confinanti;
	}


	/**
	 * Metodo privato che serve per estrapolare solo le countries
	 * che hanno confini (di tipo 1) tra loro fino all'@anno passato da parametro
	 * @param anno
	 * @return
	 */
	private List<Country>  getVerticiAnno(int anno) {
		
		// creo una lista dove aggiungero' le countries, che saranno i vertici del mio grafo
		List<Country> vertici = new ArrayList<Country>() ;
		
		// prendo i confini  fino all'anno passato da paramentro dal DAO
		this.borders = new ArrayList<Border>(bdao.getCountryPairs(anno));
		
		// per ogni confine trovato
		for (Border b : this.borders) {
			
			//se la mia lista di vertici non contiene uno dei due
			//stati del confine b
			//aggiungo lo stato a vertici.
				if (!vertici.contains(b.getCountryA())) {
					vertici.add(b.getCountryA());
				}
				if (!vertici.contains(b.getCountryB())) {
					vertici.add(b.getCountryB());
				}	
			}
		return vertici;
	}



}
