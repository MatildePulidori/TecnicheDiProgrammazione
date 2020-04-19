package it.polito.tdp.meteo.bean;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.db.MeteoDAO;


public class Model {

	
	private MeteoDAO mDao;
	private int mese;
	
	private List<MeteoDay> dati;
	private List<String> citta;
	private Map<Integer,String> viaggioMigliore ;
	private double costoMigliore;
	private final int C =100;
	private final int k=1;
	private final int maxVisite = 12;
	
	
	public Model() {
		mDao = new MeteoDAO();
	}

	public MeteoDAO getmDao() {
		return mDao;
	}


	public  List<Situazione> getMediaUmidita(Month month) {
		this.mese=month.getValue();
		List<Situazione> medie = new ArrayList<>(mDao.getUmiditaMedia(month));	
		return medie;
	}

	public Map<Integer,String> getCammino(Month month) {
		this.mese=month.getValue();
		Map<Integer,String> viaggio = new HashMap<>() ;
		dati= new ArrayList<>(mDao.getDatiMese(month));
		citta = new ArrayList<>(mDao.getCitta());
		this.viaggioMigliore=null;
		this.costoMigliore=Double.MAX_VALUE;
		
		LocalDate primoGiorno = LocalDate.of(2013, month.getValue(), 1);
		LocalDate ultimoGiorno = primoGiorno.with(TemporalAdjusters.lastDayOfMonth());
		
		ricerca(1, viaggio,ultimoGiorno.getDayOfMonth());
		
		return viaggioMigliore;
	}

	private void ricerca(int i, Map<Integer, String> viaggio, int dayOfMonth) {
		if (i==dayOfMonth+1) {
			double costo = calcolaCosto(viaggio, dayOfMonth);
			if (costo<costoMigliore) {
				costoMigliore=costo;
				viaggioMigliore = new HashMap<>(viaggio);
			}
			return;
		}

		for(String c : citta) {
			if(numVisite(c, viaggio)<this.maxVisite) {
				viaggio.put(i, c) ;
				ricerca(i+1, viaggio, dayOfMonth) ;
				viaggio.remove(i) ;
		
			}
		}
	
	}

	private int numVisite(String citta, Map<Integer, String> viaggio) {
		int numVisite=0;
		for (String city: viaggio.values()) {
			if (city.equals(citta)) {
				numVisite++;
			}
		}
		return numVisite;
	}

	private double calcolaCosto(Map<Integer, String> viaggio, int dayOfMonth) {
		double costo = 0.0 ;
		
		String cittaIeri = null ;
		for(int giorno = 1; giorno<=dayOfMonth; giorno++) {
			String cittaOggi = viaggio.get(giorno) ;

			if( cittaIeri!=null && !cittaOggi.equals(cittaIeri)) {
				costo += C ;
			}
			cittaIeri = cittaOggi ;
			
			LocalDate oggi = LocalDate.of(2013, this.mese, giorno) ;
			// trovare umitida nella 'cittaOggi' nel giorno 'oggi'
			Double um = this.getUmidita(cittaIeri, oggi);
			if(um != null)
				costo += k* um ;
			else
				costo += k * 100.0 ;
				// se manca il dato, ipotizzo il caso peggiore
		}
		
		return costo ;
	}

	private Double getUmidita(String cittaIeri, LocalDate oggi) {
		for(MeteoDay md: this.dati) {
			if (md.getCitta().equals(cittaIeri) && md.getDate().equals(oggi)) {
				return md.getUmidita();
			}
		}
		return null;
	}


	
	
	
}
