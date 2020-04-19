package it.polito.tdp.seriea.model;

import java.util.HashMap;

@SuppressWarnings("serial")
public class TeamIdMap extends HashMap<String, Team>{

	public Team get(Team team) {
		Team old = this.get(team.getTeam());
		if (old!= null) {
			return old;
		}
		this.put(team.getTeam(), team);
		return team;
	}
}
