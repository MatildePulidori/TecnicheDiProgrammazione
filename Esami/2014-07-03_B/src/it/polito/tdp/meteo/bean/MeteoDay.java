package it.polito.tdp.meteo.bean;

import java.time.LocalDate;

public class MeteoDay implements Comparable<MeteoDay> {
	
	private String citta;
	private double umidita;
	private LocalDate date;
	
	
	public MeteoDay(String citta, double umidita, LocalDate date) {
		super();
		this.citta = citta;
		this.umidita = umidita;
		this.date = date;
	}


	public String getCitta() {
		return citta;
	}


	public void setCitta(String citta) {
		this.citta = citta;
	}


	public double getUmidita() {
		return umidita;
	}


	public void setUmidita(double umidita) {
		this.umidita = umidita;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((citta == null) ? 0 : citta.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeteoDay other = (MeteoDay) obj;
		if (citta == null) {
			if (other.citta != null)
				return false;
		} else if (!citta.equals(other.citta))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return citta + ", "+date+",  " +umidita ;
	}


	@Override
	public int compareTo(MeteoDay o) {
		return date.compareTo(o.date);
	}
	
	
		

}
