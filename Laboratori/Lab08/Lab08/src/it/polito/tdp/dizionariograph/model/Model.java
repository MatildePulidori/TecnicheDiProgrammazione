package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.*;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {

	private Graph<String, DefaultEdge> graph;
	private List<String> listaParoleLunghezza;
	private List<String> paroleVicine;
	private WordDAO wdao;
	private int maxGrado = Integer.MIN_VALUE;
	public String parolaMaxGrado ;

	public Graph<String, DefaultEdge> createGraph(int numeroLettere) {

		graph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		wdao = new WordDAO();

		listaParoleLunghezza = new ArrayList<String>(wdao.getAllWordsFixedLength(numeroLettere));
		if (listaParoleLunghezza.size() == 0) {
			System.out.println("Nessuna parola trovata");
			return null;
		}
		Graphs.addAllVertices(graph, listaParoleLunghezza);
		
		for (String s : listaParoleLunghezza) {
			List<String> paroleSimili = new ArrayList<String>(this.trovaParoleSimili(listaParoleLunghezza, numeroLettere, s));// riscrivi metodo in java
			for (String d : paroleSimili) {
				if (!d.equals(s))
				graph.addEdge(d, s);
			}
		}
		return graph;
	}

	public List<String> displayNeighbours(String parolaInserita) {
		paroleVicine = new ArrayList<String>();
		graph = this.createGraph(parolaInserita.length());
		if (graph.containsVertex(parolaInserita)==true) 
		{	
			paroleVicine = Graphs.neighborListOf(graph, parolaInserita);
		}
		
		return paroleVicine;
	}

	public int findMaxDegree() {
		for (String s : graph.vertexSet()) {
			if (graph.degreeOf(s)>maxGrado) {
				maxGrado = graph.degreeOf(s);
				parolaMaxGrado = s;
			}
		}
		return maxGrado;
	}
	
	

	public List<String> trovaParoleSimili(List<String> paroleLunghezza,int numeroLettere, String parolaData){
		List<String> listaParoleSimili = new ArrayList<String>();
		if (paroleLunghezza.size()!=0 && parolaData!=null & numeroLettere!=0) {
				for (String parolaPotenzialmenteSimile: paroleLunghezza) {
					if (this.sonoSimili(parolaData, parolaPotenzialmenteSimile)== true) {
						listaParoleSimili.add(parolaPotenzialmenteSimile);
					}
				}
		}
		return listaParoleSimili;
	}

	private boolean sonoSimili(String parolaData, String parolaPotenzialmenteSimile) {
		int contatoreDiverse = 0;
		if (parolaData.length() == parolaPotenzialmenteSimile.length()) {
			for (int i=0; i<parolaData.length(); i++) {
				if (parolaData.charAt(i) != parolaPotenzialmenteSimile.charAt(i)) {
					contatoreDiverse++;
				}
			}
			if (contatoreDiverse<=1) {
				return true;
			}
		}
		return false;
	}


}

