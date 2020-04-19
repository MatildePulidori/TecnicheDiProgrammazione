package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import it.polito.tdp.seriea.model.TeamCounterMatches;
import it.polito.tdp.seriea.model.TeamIdMap;

public class SerieADAO {

	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> listTeams(TeamIdMap teamIdMap) {
		String sql = "SELECT DISTINCT team FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(teamIdMap.get(new Team(res.getString("team"))));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

	public List<Team> listTeamsSeason(Season season, TeamIdMap teamIdMap) {
		String sql = "SELECT DISTINCT team FROM teams, matches WHERE teams.team = matches.HomeTeam GROUP BY matches.HomeTeam";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(teamIdMap.get(new Team(res.getString("team"))));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
		
	public List<TeamCounterMatches> getTeamCounterMatches(TeamIdMap teamIdMap){
		String sql = "SELECT m1.HomeTeam, m2.AwayTeam, count(*) as cnt\r\n" + 
				"FROM matches as m1, matches as m2, teams as t1\r\n" + 
				"WHERE m1.match_id = m2.match_id \r\n" + 
				"AND m1.HomeTeam != m2.AwayTeam\r\n" + 
				"GROUP BY m1.HomeTeam, m2.AwayTeam";
		
	
		List<TeamCounterMatches> list = new ArrayList<TeamCounterMatches>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Team t1  = teamIdMap.get(res.getString("m1.HomeTeam"));
				Team t2  = teamIdMap.get(res.getString("m2.AwayTeam"));
				int count = res.getInt("cnt");
				
				if (t1 == null || t2 == null) {
					System.err.format("Skip %s %s", res.getString("teamA"), res.getString("teamB"));
				} else {
					if ((this.contains(list, t2, t1))!=null) {
						this.contains(list, t2, t1).addPartiteGiocate(count);
					} else {
						list.add(new TeamCounterMatches(t1, t2, count));
					}
				}
			}

			conn.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private TeamCounterMatches contains(List<TeamCounterMatches> list, Team t2, Team t1) {
		for (TeamCounterMatches teamCounterMatches : list) {
			if (teamCounterMatches.getTeamA().equals(t2) && teamCounterMatches.getTeamB().equals(t1))
				return teamCounterMatches;
		}
		return null;
	}

	public List<Match> getPartiteStagione(Season season, TeamIdMap teamIdMap) {
		
		String sql = "SELECT match_id, HomeTeam, AwayTeam, FTR, FTHG, FTAG, Date\r\n" + 
				"FROM matches\r\n" + 
				"WHERE Season = ? \r\n" + 
				"ORDER BY Date";
		

		List<Match> list = new ArrayList<Match>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, season.getSeason());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Team homeTeam = teamIdMap.get(res.getString("HomeTeam"));
				Team awayTeam = teamIdMap.get(res.getString("AwayTeam"));
				list.add(new Match(res.getInt("match_id"), res.getDate("Date").toLocalDate(), homeTeam, awayTeam, 
						res.getString("FTR"), res.getInt("FTHG"), res.getInt("FTAG")));
			
			}
			
			conn.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
	}

}
