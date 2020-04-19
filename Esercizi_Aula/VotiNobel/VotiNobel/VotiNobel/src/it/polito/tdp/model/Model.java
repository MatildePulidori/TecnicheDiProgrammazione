package it.polito.tdp.model;

<<<<<<< HEAD
import java.util.*;
import it.polito.tdp.dao.EsameDAO;
=======
import java.util.ArrayList;
import java.util.List;
>>>>>>> soluzione_in_aula

import it.polito.tdp.dao.EsameDAO;

public class Model {
<<<<<<< HEAD
	
	private List<Esame> esami ;
	private EsameDAO edao;
	private List<Esame> soluzione;
=======

	private List<Esame> esami;
	private EsameDAO edao;
	private List<Esame> soluzione;
	private double bestAvg;
>>>>>>> soluzione_in_aula
	
	public Model() {
		edao = new EsameDAO();
		esami = edao.getTuttiEsami();
<<<<<<< HEAD
	}
	
	
	public List<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		soluzione = new ArrayList<Esame>();
		
		int step=0;
		List<Esame> parziale = new ArrayList<>();
		recursive(step, parziale, numeroCrediti);
		
		return soluzione;
	}


	private void recursive(int step, List<Esame> parziale, int numeroCrediti) {
	
		
=======
//		for (Esame e: esami) {
//			System.out.println(e);
//		}
	}
	
	public List<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		// inizializzazione
		soluzione = new ArrayList<Esame>();
		bestAvg = 0.0;
		
		int step = 0;
		List<Esame> parziale = new ArrayList<>();
		recursive(step, parziale, numeroCrediti);
		
		return soluzione;
	}
	
	public int totCrediti(List<Esame> parziale) {
		int somma = 0;
		for (Esame e: parziale) {
			somma += e.getCrediti();
		}
		return somma;
	}
	
	public double avg(List<Esame> parziale) {
		double avg = 0;
		for (Esame e : parziale) {
			avg += e.getCrediti() * e.getVoto();
		}
		avg /= totCrediti(parziale);
		return avg;
	}
	
	private void recursive(int step, List<Esame> parziale, int numeroCrediti) {
		
		// Debug ??
		// for (Esame e : parziale) {
		//	System.out.print(e.getCodins() + " ");
		// }
		// System.out.println(" ");
		
		
		// Condizione di terminazione
		if (totCrediti(parziale) > numeroCrediti) {
			return;
		}
		
		// Controllo se ho trovato una nuova soluzione migliore
		if (totCrediti(parziale) == numeroCrediti) {
			if (avg(parziale) > bestAvg) {
				soluzione = new ArrayList(parziale);
				bestAvg = avg(parziale);
			}
		}
		
		// Generazione di una nuova soluzione parziale
		for (Esame esame : esami) {
			if (!parziale.contains(esame)) {
				parziale.add(esame);
				recursive(step+1, parziale, numeroCrediti);
				parziale.remove(esame);
			}
		}
>>>>>>> soluzione_in_aula
	}

	
	
}
