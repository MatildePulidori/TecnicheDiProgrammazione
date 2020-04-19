package it.polito.tdp.seriea.model;

import java.time.LocalDate;

public class Match {

	private int id;
	private LocalDate date;
	private Team homeTeam;
	private Team awayTeam;
	private String ftr; // full time result (H, A, D)
	private int fthg;
	private int ftag;
	// E' possibile aggiungere altri campi, se risulteranno necessari

	/**
	 * New match
	 * 
	 * @param id
	 * @param season
	 * @param div
	 * @param date
	 * @param homeTeam
	 * @param awayTeam
	 * @param fthg
	 * @param ftag
	 * @param ftr
	 */
	public Match(int id, LocalDate date, Team homeTeam, Team awayTeam, String ftr, int fthg, int ftag) {
		super();
		this.id = id;
		this.date = date;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.ftr = ftr;
		this.fthg = fthg;
		this.ftag = ftag;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @return the homeTeam
	 */
	public Team getHomeTeam() {
		return homeTeam;
	}

	/**
	 * @return the awayTeam
	 */
	public Team getAwayTeam() {
		return awayTeam;
	}
	
	/**
  	* @return the ftr
	 */
	public String getFtr() {
		return ftr;
	}

	/**
	 * @param id
	 * the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param date
	 * the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @param homeTeam
	 * the homeTeam to set
	 */
	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
		this.homeTeam.setNumeroTifosi(homeTeam.getNumeroTifosi());
	}

	/**
	 * @param awayTeam
	 * the awayTeam to set
	 */
	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
		this.awayTeam.setNumeroTifosi(awayTeam.getNumeroTifosi());
	}

	/**
	 * @param ftr
	 * the ftr to set
	 */
	public void setFtr(String ftr) {
		this.ftr = ftr;
	}


	public int getFthg() {
		return fthg;
	}

	public void removeFthg(int fthg) {
		if (this.fthg >= fthg )
			this.fthg -= fthg;
	}

	public int getFtag() {
		return ftag;
	}

	public void removeFtag(int ftag) {
		if (this.ftag >= ftag )
			this.ftag -= ftag;
	}

	
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
