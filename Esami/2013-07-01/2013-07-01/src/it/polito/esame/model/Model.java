package it.polito.esame.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.esame.bean.Parola;
import it.polito.esame.dao.ParoleDAO;

public class Model {
	
	private ParoleDAO pdao;
	private List<Parola> parole;
	private List<Parola> paroleLung;
	private Graph<Parola, DefaultEdge> grafo;
	private Parola parolaMaxConnected ;
	private int maxConnection;
	
	public Model(){
		pdao = new ParoleDAO();
		parole = new ArrayList<Parola>(pdao.getAllParola());
	}

	public void cercaParoleLunghezza(int lunghezza) {
		paroleLung = new ArrayList<Parola>(pdao.getAllParolaWithLength(lunghezza));
		grafo = this.creaGrafo(paroleLung);
	}
	

	public List<Parola> getParole() {
		return parole;
	}

	public List<Parola> getParoleLung() {
		return paroleLung;
	}

	public Graph<Parola, DefaultEdge> getGrafo() {
		return grafo;
	}
	
	public Parola getParolaMaxConnected() {
		return parolaMaxConnected;
	}

	public int getMaxConnection() {
		return maxConnection;
	}

	private Graph<Parola, DefaultEdge> creaGrafo(List<Parola> paroleLunghezza) {
		
		// 1 creo grafo
		grafo = new SimpleGraph<Parola, DefaultEdge>(DefaultEdge.class);
		
		// 2 aggiungo i vertici
		Graphs.addAllVertices(grafo, paroleLunghezza);
		
		// 3 aggiungo gli archi
			 // arco : connessione tra due parole che hanno solo una lettera di differenza
		for (Parola p : grafo.vertexSet()) {
			List<Parola> simili = this.trovaSimili(p, paroleLunghezza);
			for (Parola simile : simili) {
				grafo.addEdge(p, simile);
			}
		}
		this.parolaMaxConnected = this.getMaxConnected();
		return grafo;
		
	}

	
	private List<Parola> trovaSimili(Parola p, List<Parola> paroleLunghezza) {
		List<Parola> simili = new ArrayList<Parola>();
		for (Parola parola : paroleLunghezza) {
			
			if (!parola.equals(p)) {
				if (controllaSeSimili(parola, p)==true) {
					simili.add(parola);
				}
			}
			
		}
		return simili;
	}

	private boolean controllaSeSimili(Parola pDaComparare, Parola pData) {
		
		String parola1 = pData.getNome();
		String parola2 = pDaComparare.getNome();
		int lettereDiverse =0 ;
		
		for (int i=0; i<parola1.length(); i++) {
				if (parola1.charAt(i)!=parola2.charAt(i)) {
					lettereDiverse++;
					if (lettereDiverse>1)
						return false;
				}
		}
		
		return true;
	}

	public Parola cercaParola(String parolaPartenza) {
		for (Parola p : paroleLung) {
			if (p.getNome().equals(parolaPartenza))
				return p;
		}
		return null;
	}

	public List<Parola> cercaSequenza(String parolaPartenza, String parolaArrivo) {
		
		List<Parola> parole = new ArrayList<Parola>();
		Parola pPartenza =this.cercaParola(parolaPartenza);
		Parola pFine = this.cercaParola(parolaArrivo);
		
		if ((pPartenza!=null)&&(pFine!=null)) {
			DijkstraShortestPath<Parola, DefaultEdge> shortpath = new DijkstraShortestPath<Parola, DefaultEdge>(grafo, pPartenza, pFine);
			List<DefaultEdge> archi = new ArrayList<DefaultEdge>(shortpath.getPathEdgeList());
			if (archi.size()!=0) {
				parole.add(pPartenza);
				for (DefaultEdge arco : archi) {
					Parola daAggiungere = grafo.getEdgeTarget(arco);
					if (!parole.contains(daAggiungere))
						parole.add(daAggiungere);
				}
			
			}
		}
		return parole;
		 
	}

	public boolean isOk(String parola, int lunghezza) {
		if (parola.length() == lunghezza && this.cercaParola(parola)!=null)
			return true;
		return false;
	}

	private Parola getMaxConnected () {
		int max = Integer.MIN_VALUE;
		Parola cercata = null; 
		
		for (Parola p : this.paroleLung) {
			if (Graphs.neighborListOf(grafo, p).size()>max) {
				cercata = p;
				max = Graphs.neighborListOf(grafo, p).size();
			
			}
		}
		maxConnection = max;
		return cercata;
	}
	
}
