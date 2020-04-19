package it.polito.tdp.ufo.db;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.State;
import it.polito.tdp.ufo.model.StateIdMap;
import it.polito.tdp.ufo.model.StatePair;
import it.polito.tdp.ufo.model.YearSights;



public class SightingsDAO {

	
	public List<Integer> getYears() {
		String sql = "Select distinct Year(datetime) as year From sighting Where country = 'us' Order by year asc" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list .add(res.getInt("year"));
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	
	public YearSights getYearSights(int year){
		String sql = "Select Year(datetime), count(*) as cnt From sighting Where year(datetime) = ? and country = 'us'";
		
		Connection conn = DBConnect.getConnection();
	
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet res = st.executeQuery();
			YearSights ys  = null;
			
			if (res.next()) {
				 ys = new YearSights(res.getInt("Year(datetime)"), res.getInt("cnt") );
			}
			
			if (ys == null) {
				new RuntimeException("Anno non presente nel database");
			}
			conn.close();
			return ys; 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	public List<State> getStates(YearSights yS, StateIdMap stateIdMap) {
		String sql = "Select distinct state From sighting WHERE country ='us' and Year(datetime)= ? order by datetime ";
		
		List<State> list = new ArrayList<State>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, yS.getYear());
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				State state = new State(rs.getString("state"));
				list.add(stateIdMap.get(state));
			}
			
			conn.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	public List<StatePair> getStatePairs(YearSights yS, StateIdMap stateIdMap) {
		String sql = "select s1.state as state1 , s2.state as state2, count(*) \n" + 
				"from sighting s1, sighting s2 \n" + 
				"where year(s1.datetime)=year(s2.datetime) and year(s1.datetime)=?\n" + 
				"and s1.country='us'and s2.country='us' \n" + 
				"and s2.datetime>s1.datetime and s1.state<>s2.state\n" + 
				"group by s1.state, s2.state ";
		
		List<StatePair> list = new ArrayList<StatePair>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, yS.getYear());
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				State state1 = stateIdMap.get(new State(rs.getString("state1")));
				State state2 = stateIdMap.get(new State(rs.getString("state2")));
				
				list.add(new StatePair(state1, state2));
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
