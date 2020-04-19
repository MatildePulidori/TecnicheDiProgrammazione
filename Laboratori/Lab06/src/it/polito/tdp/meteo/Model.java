package it.polito.tdp.meteo;

import java.time.Month;
import java.util.*;

import it.polito.tdp.meteo.bean.*;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	
	MeteoDAO meteoDAO;
	private List<Citta> cittaMeteo;
	private List<SimpleCity> soluzioneMigliore;
	private Double costoMigliore = Double.MAX_VALUE;

	public Model() {
		meteoDAO = new MeteoDAO();
		cittaMeteo = meteoDAO.getCities();
	}
	
	public List<Citta> getCities(){
		
		return cittaMeteo;
	}
	public enum Months {

		JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC;

		private static Map<Integer, Months> ss = new TreeMap<Integer, Months>();
		private static final int START_VALUE = 1;
		private int value;

		static {
			for (int i = 0; i < values().length; i++) {
				values()[i].value = START_VALUE + i;
				ss.put(values()[i].value, values()[i]);
			}
		}

		public static Months fromInt(int i) {
			return ss.get(i - 1);
		}

		public int value() {
			return value;
		}
	}


	/**
	 * Metodo che calcola l'umidita media di una @citta nel @mese passato come parametro
	 * @param mese
	 * @return
	 */
	public Double getUmiditaMedia(Month mese, Citta citta) {
		
		double umiditaMediaCitta = meteoDAO.getAvgRilevamentiLocalitaMese(mese, citta);
		
		return umiditaMediaCitta;
	}

	
	/**
	 * Metodo che trova la sequenza di citta da visitare, nel @mese passato da parametro
	 * @param m
	 * @return
	 */
	public String trovaSequenza(Month m) {
		for (Citta c : cittaMeteo) {
			c.setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(m, c.getNome()));
		}
		this.soluzioneMigliore = null;
		List<SimpleCity> soluzParziale = new ArrayList<SimpleCity>();
		
		this.recursive(soluzParziale , 0);
		return soluzioneMigliore.toString() +"\nCosto: "+costoMigliore+" €.";
	}


	/**
	 * Metodo che controlla la validita di una soluzione @parziale
	 * @param parziale
	 * @return
	 */
	private boolean controllaParziale(List<SimpleCity> parziale) {
		if (parziale==null) {
			return false;
		}
		if (parziale.size()==0) {
			return true;
		}
		// Controllo vincolo numero giorni max nella citta
		for(Citta c : cittaMeteo) {
			if (c.getCounter() > NUMERO_GIORNI_CITTA_MAX) {
				return false;
			}
		}
		
		SimpleCity previsione = parziale.get(0);
		int contatore = 0;
		
		for(SimpleCity s : parziale) {
			
			//Quando devo cambiare citta 
			if (!previsione.equals(s)) {
				
				//Controllo vincolo numero giorni min nella citta
				if (contatore < NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
					return false;
				}
				
				//Se posso cambiare citta aumento il contatore a 1 per la citta successiva
				contatore = 1;
				previsione = s;
			}
			
			// Se la citta è la stessa aumento solo il contatore dei giorni
			else {
				contatore ++;
			}
		}

		return true; 
	}
	
	
	/**
	 * Metodo ricorsivo che guarda tutte le possibili soluzioni di citta e trova quella con @costoMigliore
	 * @param parziale
	 * @param step
	 */
	public void recursive (List<SimpleCity> parziale, int step) {
		
		// A condizione terminale
		// se la somma dei contatori per ogni citta fa 15, cioe sono al giorno 15,
		// controllo se la soluzione ha costo minore della soluz.migliore fin'ora trovata
		if (step == NUMERO_GIORNI_TOTALI) {
			// if il costo è minore di quello già trovato la aggiungo, altrimenti no
			double costoParziale = this.calcolaCostoSoluzParziale(parziale);
			if (soluzioneMigliore == null || costoParziale < costoMigliore) {
				soluzioneMigliore =  new ArrayList<SimpleCity>(parziale);
				costoMigliore = costoParziale;
				return;
			}
		}
		
		// B soluz parziale 
			for (Citta c : cittaMeteo){
				parziale.add(new SimpleCity(c.getNome(), calcolaCostoCittaGiorno(step, c)));
				c.increaseCounter();
				// C filtro
				// controllo con il metodo this.controllaParziale(List<SimpleCity> parziale) 
				// che questa soluzione parziale sia corretta 
				// se TRUE allora vado avanti con un altra soluzione e chiamo il metodo recursive(parziale, step+1);
				if (this.controllaParziale(parziale)==true) {
					this.recursive(parziale, step+1);
				}
				// D backtracking 
				parziale.remove(new SimpleCity(c.getNome(), calcolaCostoCittaGiorno(step, c)));
				c.decreaseCounter();
			}
		
	}

	/**
	 * Calcola il costo per quel giorno della ispezione nella citta @c nel giorno @step
	 * @param step
	 * @param c
	 * @return
	 */
	private int calcolaCostoCittaGiorno(int step, Citta c) {
		int umiditaGiorno = 0;
		for (int i = 0; i < cittaMeteo.size(); i++) {
			Citta cittaCurr = cittaMeteo.get(i);
			if (cittaCurr.equals(c)) {
				umiditaGiorno = cittaCurr.getRilevamenti().get(step).getUmidita();
				i=cittaMeteo.size();
			}
		}
		int costoGiornoStep =  COST + umiditaGiorno;
		return costoGiornoStep;
	}

	
	/**
	 * Metodo che calcola il costo di una soluzione @parziale, passata da parametro
	 * @param parziale
	 * @return
	 */
	private int calcolaCostoSoluzParziale(List<SimpleCity> parziale) {
		int costoTot = 0;
		for (SimpleCity simpleCity : parziale) {
			costoTot += simpleCity.getCosto();
		}
		
		return costoTot;
		
	}
}
