package it.polito.tdp.formulaone.model;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import it.polito.tdp.formulaone.model.Event.EventType;

public class Simulatore {
	
	private Race race;
	private double P;
	private int T;
	
	private PriorityQueue<Event> pq;
	
	public Simulatore(Race race, double p, int t) {
		super();
		this.race = race;
		P = p;
		T = t;
	}


	public Race getRace() {
		return race;
	}


	public void setRace(Race race) {
		this.race = race;
	}


	public double getP() {
		return P;
	}


	public void setP(double p) {
		P = p;
	}


	public int getT() {
		return T;
	}


	public void setT(int t) {
		T = t;
	}
	
	
	
	public void init() {
		pq = new PriorityQueue<Event>();
		for (Driver driver : this.race.getPartecipanti()) {
			if (driver.getGiri().size()!=0) {
			pq.add(new Event(EventType.GIRO, driver, driver.getGiri().get(0), 0));
			driver.getGiri().remove(0);
			}
		}
	}
	
	public void run() {
		Event e;
		while((e=pq.poll())!=null) {
			this.processEvent(e);
		}
	}


	private void processEvent(Event e) {
		switch (e.getEventType()) {
		case GIRO:
			
			// prendo il driver dell'evento
			Driver dr = this.getDriver(e.getDriver());
			
			// aggiungo nella lista dei suoi arrivi pe ogni giro il millisec in cui e` arrivato 
			dr.addArrivo(e.getMillisecsStart()+e.getLapTime().getMiliseconds());
			
			// per tenere traggia del millisecondo tot sommo ai millisec totali quelli del giro
			dr.addMilliSec(e.getLapTime().getMiliseconds());
			
			
			// con una probabilita` p dovra fae un pitstop
			double probabilitaPitStop = Math.random();
			if (probabilitaPitStop<= this.P) {
				
				// e quinid aggiungero` T secondi (T*1000 millisecondi) al giro
				dr.addMilliSec(T*1000);
			}

			// se il driver ha fatto altri giri aggiungo alla pririty queue un alto giro, con momento di partenza quello ultimo di arrivo
			if (!e.getDriver().getGiri().isEmpty())
				pq.add(new Event(EventType.GIRO, e.getDriver(), dr.getGiri().get(0), e.getDriver().getMilliSecTotali()));
			
			

			break;
		}
			

	}
	
	public Driver getDriver(Driver driver) {
		for (Driver d : this.race.getPartecipanti()) {
			if (d.getDriverId()==driver.getDriverId())
				return d;
		}
		return null;
	}
	
	
	public Map<Integer, Driver> getMigliorDriver() {
		Map<Integer, Driver> mappaArrivi = new TreeMap<>();
		int maxGiri = this.calcolaMaxGiri();
		
		for (int i=0; i<maxGiri; i++) {
			int tempoRecord = Integer.MAX_VALUE;
			Driver miglioreDelGiro = null;
			for (Driver driver : this.race.getPartecipanti()) {
				if (driver.getArrivi().get(i)!=null) {
					if (driver.getArrivi().get(i)< tempoRecord) {
						tempoRecord = driver.getArrivi().get(i);
						miglioreDelGiro = driver;
					}
				}
			}
			mappaArrivi.put(i, miglioreDelGiro);
		}
		return mappaArrivi;
				
	}


	private int calcolaMaxGiri() {
		int maxGiri=Integer.MIN_VALUE;
		for (Driver d: this.race.getPartecipanti()) {
			if (d.getArrivi().size()> maxGiri)
				maxGiri = d.getArrivi().size();
		}
		return maxGiri;
	}
	
}
