package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.seriea.db.SerieADAO;
import it.polito.tdp.seriea.model.Event.EventType;

public class Simulatore {
	
	private Season season;
	private TeamIdMap teamIdMap;
	private SerieADAO sdao ;
	private int P;
	
	private PriorityQueue<Event> pq;
	private List<Match> matches;
	private List<Team> teams;
	
	public Simulatore(Season season, TeamIdMap teamIdMap) {
		this.season = season ;
		this.teamIdMap = teamIdMap;
		this.sdao = new SerieADAO();
		this.P =10;
	}

	public List<Match> getPartiteStagione() {
		matches =  new ArrayList<Match>(sdao.getPartiteStagione (season, teamIdMap));
		return matches;
	}
	
	
	public List<Team> getTeamSeason(){
		teams = new ArrayList<Team>( sdao.listTeamsSeason(season, teamIdMap));
		return teams;
		
	}
	
	public void init() {
		teams = this.getTeamSeason();
		pq = new PriorityQueue<Event>();
		for (Match match : this.getPartiteStagione()) {
			double rapporto = (double)( match.getHomeTeam().getNumeroTifosi() /match.getAwayTeam().getNumeroTifosi());
			if ( rapporto == 1 ) {
				pq.add(new Event (EventType.STESSO_NUMTIFOSI, match));
			} else {
				pq.add(new Event (EventType.DIVERSO_NUMTIFOSI, match));
			}
		}
	}
	
	public void run() {
		Event e;
		while ((e=pq.poll()) != null) {
			this.processEvent(e);
		}
		
	}

	private void processEvent(Event e) {
		Team hTeam =this.getTeam(e.getMatch().getHomeTeam());
		e.getMatch().setHomeTeam(hTeam);
		Team aTeam =this.getTeam( e.getMatch().getAwayTeam());
		e.getMatch().setAwayTeam(aTeam);
		
		switch (e.getType()) {
		
		case STESSO_NUMTIFOSI:
			// modifico tifosi
			double scarto = e.getMatch().getFthg()-e.getMatch().getFtag();
			double percentuale  = (double)(scarto*P)/100;
			
			if (scarto>0) { // ha perso awayTeam, riduco i suoi tifosi, aumento tifosi homeTeam
				int nTifosi =(int)( percentuale * aTeam.getNumeroTifosi());
				hTeam.addNumeroTifosi(nTifosi);
				hTeam.addPunteggioClassifica(3);
				aTeam.removeNumeroTifosi(nTifosi);
				
			}if (scarto < 0) {  //ha perso homeTeam, riduco i suoi tifosi, aumento tifosi awayTeam
				int nTifosi = (int) percentuale * hTeam.getNumeroTifosi();
				aTeam.addNumeroTifosi(nTifosi);
				aTeam.addPunteggioClassifica(3);
				hTeam.removeNumeroTifosi(nTifosi);
			} else if (scarto == 0) {
				aTeam.addPunteggioClassifica(1);
			}
			
			
			break;
			
		case DIVERSO_NUMTIFOSI:
			
			double numeroHomeGoals = (double)(e.getMatch().getFthg()) ;
			double numeroAwayGoals = (double)(e.getMatch().getFtag()) ;
			double rapporto =(numeroHomeGoals )/ (numeroAwayGoals);
			
			
			// modifico goal delle squadre
			if (rapporto > 0 && rapporto < 1) {
				double probabilita = Math.random();
				if (probabilita <= (1-rapporto)) {
					e.getMatch().removeFthg(1);
				}
				
			}  else if (rapporto>1) {
				double probabilita = Math.random();
				if (probabilita <= (1 - (1/rapporto))) {
					e.getMatch().removeFtag(1);
				}
			} 
			
			
			// modifico tifosi
			double scart = e.getMatch().getFthg()-e.getMatch().getFtag();
			double percentual  = (scart*P)/100;
			
			if (scart>0) { // ha perso awayTeam, riduco i suoi tifosi, aumento tifosi homeTeam
				int nTifosi = (int) (percentual * aTeam.getNumeroTifosi());
				hTeam.addNumeroTifosi(nTifosi);
				hTeam.addPunteggioClassifica(3);
				aTeam.removeNumeroTifosi(nTifosi);
				
			} if (scart < 0) {  //ha perso homeTeam, riduco i suoi tifosi, aumento tifosi awayTeam
				int nTifosi = (int) percentual * hTeam.getNumeroTifosi();
				aTeam.addNumeroTifosi(nTifosi);
				aTeam.addPunteggioClassifica(3);
				hTeam.removeNumeroTifosi(nTifosi);
			} else if (scart == 0) {
				aTeam.addPunteggioClassifica(1);
				hTeam.addPunteggioClassifica(1);
			}
			
			break;
		}
		
	}
	

	private Team getTeam(Team team) {
		for (Team t : this.teams) {
			if (t.equals(team))
				return t;
		}
		return null;
		
	}

	public String getRisultati() {
		StringBuilder s = new StringBuilder();
		for (Team team : this.teams) {
			s.append(team.toString()+ " "+team.getNumeroTifosi()+" "+team.getPunteggioClassifica()+"\n");
		}
		
		return s.toString();
	}
}
