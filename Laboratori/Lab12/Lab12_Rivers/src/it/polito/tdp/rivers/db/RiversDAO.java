package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.RiverIdMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	
	public List<River> getAllRivers(RiverIdMap riverIdMap) {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				River r = new River(res.getInt("id"), res.getString("name"));
				rivers.add(riverIdMap.get(r));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	
	public List<Flow> getRiverFlowMisures(River river, RiverIdMap riverIdMap) {
		String sql = "SELECT * FROM flow WHERE river = ?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet rs = st.executeQuery();
			
			//prendo il river
			River r = riverIdMap.get(river.getId());
			r.getFlows();
			
			while(rs.next()) {
				//predo la misura
				Flow flow  = new Flow(rs.getInt("id"), rs.getDate("day").toLocalDate(), rs.getDouble("flow"), r);
				//la aggiungo alla lista del River il flow
				r.addFlow(flow);
			}
			
			conn.close();
			return r.getFlows();
				
		} catch (SQLException e ) {
			throw new RuntimeException("SQL Error");
		}
		
	}
		
}
