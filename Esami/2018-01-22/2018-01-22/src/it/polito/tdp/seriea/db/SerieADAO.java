package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import it.polito.tdp.seriea.model.TeamGoalsSeason;

public class SerieADAO {

	public List<Season> listAllSeasons() {
		String sql = "SELECT season, description FROM seasons Order by Season";
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

	public List<Team> listTeams() {
		String sql = "SELECT team FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Team(res.getString("team")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public TeamGoalsSeason getPuntiInClassifica(Team team, Season season) {
		String sql = "Select Season, HomeTeam as teamH, AwayTeam as teamA, FTR as result\n" + 
				"From matches\n" + 
				"Where (( HomeTeam = ? and (FTR = 'H' or FTR  = 'D') )\n" + 
				"or (AwayTeam = ? and (FTR ='A' or FTR='D') ) )\n"
				+ "and Season = ?";
		
		
		
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, team.getTeam());
			st.setString(2, team.getTeam());
			st.setInt(3, season.getSeason());
			ResultSet rs = st.executeQuery();
			TeamGoalsSeason tgs = null;
			if (rs.next()) {
				tgs = new TeamGoalsSeason(team, season);
				tgs.addPunti(rs.getString("result"));
				while (rs.next()) {
					tgs.addPunti(rs.getString("result"));
				}
			}
			conn.close();
			if (tgs ==null) {
				new RuntimeException("Nessuna partita nella stagione");
			}
			return tgs;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}

	public List<Season> listSeasonTeam(Team team) {
		String sql = "Select matches.season, seasons.description\n" + 
				"From matches, seasons\n" + 
				"Where matches.season = seasons.season\n" + 
				"and (matches.hometeam = ? or matches.awayteam= ?)\n" + 
				"Group by Season "
				+"Order by Season";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, team.getTeam());
			st.setString(2, team.getTeam());
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
	
	

}
