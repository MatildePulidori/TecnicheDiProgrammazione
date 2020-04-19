package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;


public class PowerOutageDAO {

	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	
	public List<PowerOutages> getPowerOutagesNerc(Nerc nerc){
		String sql = "";
		
			sql = "SELECT * FROM poweroutages "
				+ "WHERE"
				+ " nerc_id = ? "
				+ "ORDER BY date_event_began ASC ";
	
		List<PowerOutages> powerOutages = new ArrayList<PowerOutages>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, nerc.getId());
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				PowerOutages pwrOutages = new PowerOutages(rs.getInt("id"),
						rs.getInt("event_type_id"),rs.getInt("tag_id"),rs.getInt("area_id"),
						rs.getInt("nerc_id"),rs.getInt("responsible_id"),rs.getInt("customers_affected"),
						rs.getTimestamp("date_event_began").toLocalDateTime(),rs.getTimestamp("date_event_finished").toLocalDateTime());
				powerOutages.add(pwrOutages);
			}
			
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	return powerOutages;
	}

}
