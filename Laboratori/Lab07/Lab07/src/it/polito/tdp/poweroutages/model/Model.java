package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	PowerOutageDAO podao;
	private int numeroMaxUtentiAffetti = Integer.MIN_VALUE ;
	private List<PowerOutages> insiemeMiglioreDiPowerOutages =  new ArrayList<PowerOutages>();
	
	/**
	 * Costruttore del model
	 */
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	/**
	 * Metodo che ritorna tutti i NERC del database
	 * @return
	 */
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	/**
	 * Metodo che richiama il metodo ricorsivo e mi restituisce il 
	 * caso peggiore = insieme di power outages che hanno attaccato più persone 
	 * possibili, nei limiti di @maxYears e @maxHours
	 * @param nerc
	 * @param maxYears
	 * @param maxHours
	 * @return
	 */
	public String doWorstCase(Nerc nerc, int maxYears, int maxHours) {
		
		List<PowerOutages> pOutagesNerc = podao.getPowerOutagesNerc(nerc);
		List<PowerOutages> parz = new ArrayList<PowerOutages>();
		
		this.recursive(maxYears, maxHours, pOutagesNerc, parz);
		
		StringBuilder sB = new StringBuilder();
		for (PowerOutages p : insiemeMiglioreDiPowerOutages) {
			sB.append(p.toString()+"\n");
		}
		sB.append(numeroMaxUtentiAffetti);
		
		return sB.toString();
	}

	/**
	 * Metodo ricorsivo che crea l' insieme di poweroutages con il vincolo
	 * di @maxYears e @maxHours e il numero di costumersAffected massimo.
	 * @param maxYears
	 * @param maxHours
	 * @param pwrOutagesNerc
	 * @param parziale
	 */
	 
	private void recursive(int maxYears, int maxHours, List<PowerOutages> pwrOutagesNerc, List<PowerOutages> parziale) {
		
		// A Condizione terminazione
		if (this.calcolaUtentiAffetti(parziale)>numeroMaxUtentiAffetti) {
			numeroMaxUtentiAffetti = this.calcolaUtentiAffetti(parziale);
			insiemeMiglioreDiPowerOutages.clear();
			insiemeMiglioreDiPowerOutages.addAll(parziale);
		}
		
		// B Soluzione parziale
		for (PowerOutages p : pwrOutagesNerc) {
			if (!parziale.contains(p)) {
				boolean var = false;
				if (parziale.size()>1) {
					if (p.getDataInizio().isAfter(parziale.get(parziale.size()-1).getDataInizio()))
						var = true;
				}
				else {
					var=true;
				}
			if (var==true) {
				parziale.add(p);
				
				// C Filtro
				if (this.isValid(parziale, maxYears, maxHours) == true)
					this.recursive(maxYears, maxHours, pwrOutagesNerc, parziale);
				
				// D Backtrack
				parziale.remove(p);
			}
		}
		
	}
}
	
	private int calcolaUtentiAffetti(List<PowerOutages> parziale) {
		int somma = 0;
		for(PowerOutages p : parziale) {
			somma += p.getCostumersAffected();
		}
		return somma;
		
	}

	/**
	 * Metodo che controlla se una soluzione @parziale è valida 
	 * Nei limiti di @maxYears e @maxHours
	 * @param parziale
	 * @param maxYears
	 * @param maxHours
	 * @return
	 */
	private boolean isValid(List<PowerOutages> parziale, int maxYears, int maxHours) {
		
		PowerOutages ultimoInserito= parziale.get(parziale.size()-1);
		
		if (parziale.size()!=1) {
			
			PowerOutages primoInserito = parziale.get(0);
			LocalDateTime dataInizio = primoInserito.getDataInizio();
			
			// Faccio la differenza in anni tra il primo e l'ultimo poweroutages inserito
			// tutti i poweroutages sono in ordine cronologico
			long differenzaAnni = dataInizio.until(ultimoInserito.getDataFine(), ChronoUnit.YEARS);
			
			// Controllo la differenza 
			if (differenzaAnni>maxYears)
				return false;
			}
		
		// Calcolo il tempo totale e controllo che sia inferiore o uguale a @maxHours
		if (this.calcolaTempoTotale(parziale)<=maxHours)
			return true;
		else 
			return false;
	}

	/**
	 * Metodo che calcola il tempo totale accumulato di poweroutages
	 * di una soluzione @parziale passata da parametro 
	 * @param parziale
	 * @return
	 */
	private  long calcolaTempoTotale(List<PowerOutages> parziale) {
		long sommaMin = 0 ;
		for (PowerOutages p : parziale) {
			LocalDateTime datainizio = p.getDataInizio();
			long minutiTrascorsi = datainizio.until(p.getDataFine(), ChronoUnit.HOURS);
			sommaMin += minutiTrascorsi;
		}
		return sommaMin;
		
	}

}
