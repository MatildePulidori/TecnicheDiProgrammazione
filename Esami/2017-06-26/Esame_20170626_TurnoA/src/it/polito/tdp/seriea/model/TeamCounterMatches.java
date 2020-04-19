package it.polito.tdp.seriea.model;

public class TeamCounterMatches implements Comparable<TeamCounterMatches>{

	
	private Team teamA;
	private Team teamB;
	private int partiteGiocate;
	
	
	
	public TeamCounterMatches(Team teamA, Team teamB, int partiteGiocate) {
		super();
		this.teamA = teamA;
		this.teamB = teamB;
		this.partiteGiocate = partiteGiocate;
	}
	
	public Team getTeamA() {
		return teamA;
	}
	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}
	public Team getTeamB() {
		return teamB;
	}
	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}
	public int getPartiteGiocate() {
		return partiteGiocate;
	}
	public void addPartiteGiocate(int n) {
		this.partiteGiocate = partiteGiocate + n ;
	}

	@Override
	public int compareTo(TeamCounterMatches o) {
		return -(this.partiteGiocate - o.partiteGiocate);
	}
	
	
	
}
