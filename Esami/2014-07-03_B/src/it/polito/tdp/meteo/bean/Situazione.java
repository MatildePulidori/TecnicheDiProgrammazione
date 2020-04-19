package it.polito.tdp.meteo.bean;

import java.time.Month;

public class Situazione {
	
	private String citta;
	private double mediaUmidita;
	private Month mese;
	
	public Situazione(String citta, double mediaUmidita, Month mese) {
		super();
		this.citta = citta;
		this.mediaUmidita = mediaUmidita;
		this.mese = mese;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public double getMediaUmidita() {
		return mediaUmidita;
	}

	public void setMediaUmidita(double mediaUmidita) {
		this.mediaUmidita = mediaUmidita;
	}

	public Month getMese() {
		return mese;
	}

	public void setMese(Month mese) {
		this.mese = mese;
	}

	@Override
	public String toString() {
		return citta + ", mediaUmidita=" + mediaUmidita ;
	}
	
	
}
