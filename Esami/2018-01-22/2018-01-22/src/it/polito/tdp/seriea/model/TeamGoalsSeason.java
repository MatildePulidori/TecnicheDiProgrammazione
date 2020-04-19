package it.polito.tdp.seriea.model;

public class TeamGoalsSeason {

	private Team team;
	private Season season;
	private int punti;
	
	public TeamGoalsSeason(Team team, Season season) {
		super();
		this.team = team;
		this.season = season;
		this.punti=0;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public int getPunti() {
		return punti;
	}

	public void addPunti(String result) {
		if (result.equals("H") || result.equals("A"))
			this.punti += 3;
		else if (result.equals("D")) {
			this.punti += 1;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((season == null) ? 0 : season.hashCode());
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
		TeamGoalsSeason other = (TeamGoalsSeason) obj;
		if (season == null) {
			if (other.season != null)
				return false;
		} else if (!season.equals(other.season))
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return season +", "+ team + ",  punti=" + punti ;
	}
	
	
	

}
