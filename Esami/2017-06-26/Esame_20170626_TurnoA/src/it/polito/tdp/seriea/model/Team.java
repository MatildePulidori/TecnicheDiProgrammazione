package it.polito.tdp.seriea.model;

public class Team {

	private String team;
	private int numeroTifosi;
	private int punteggioClassifica;
	
	public Team(String team) {
		super();
		this.team = team;
		this.numeroTifosi = 1000;
		this.punteggioClassifica = 0;
	}

	/**
	 * @return the team
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * @param team
	 * the team to set
	 */
	public void setTeam(String team) {
		this.team = team;
	}
	
	public int getNumeroTifosi() {
		return numeroTifosi;
	}

	public void addNumeroTifosi(int T) {
		this.numeroTifosi = numeroTifosi + T;
	}

	public void removeNumeroTifosi(int T) {
		this.numeroTifosi = numeroTifosi - T;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return team;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((team == null) ? 0 : team.hashCode());
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
		Team other = (Team) obj;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}

	public void setNumeroTifosi(int numeroTifosi) {
		this.numeroTifosi = numeroTifosi;
		
	}

	public int getPunteggioClassifica() {
		return punteggioClassifica;
	}

	public void addPunteggioClassifica(int punteggioClassifica) {
		this.punteggioClassifica += punteggioClassifica;
	}
	
	

}
