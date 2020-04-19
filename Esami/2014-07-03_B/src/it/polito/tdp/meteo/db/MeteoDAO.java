/***************************************************************************\
 *               *                                                         *
 *    #####      *  (!) 2014 by Giovanni Squillero                         *
 *   ######      *  Politecnico di Torino - Dip. Automatica e Informatica  *
 *   ###   \     *  Cso Duca degli Abruzzi 24 / I-10129 TORINO / ITALY     *
 *    ##G  c\    *                                                         *
 *    #     _\   *  tel : +39-011-564.7092  /  Fax: +39-011-564.7099       *
 *    |   _/     *  mail: giovanni.squillero@polito.it                     *
 *    |  _/      *  www : http://www.cad.polito.it/staff/squillero/        *
 *               *                                                         *
\***************************************************************************/

package it.polito.tdp.meteo.db;

import it.polito.tdp.meteo.bean.MeteoDay;
import it.polito.tdp.meteo.bean.Situazione;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO per l'accesso al database {@code meteo}
 * 
 * @author Fulvio
 * 
 */
public class MeteoDAO {


			
	
	public List<Situazione> getUmiditaMedia(Month month){
		String sql = "Select localita , avg(umidita) media \r\n" + 
				"from situazione\r\n" + 
				"where  Month(data) = ? \r\n" + 
				"group by localita";
		List<Situazione> medie = new ArrayList<>();
		
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, month.getValue());
			ResultSet rs = st.executeQuery();
				
			while(rs.next()) {
				Situazione situazione = new Situazione(rs.getString("localita"), rs.getDouble("media"), month);
				medie.add(situazione);
			}
			
			con.close();
			return medie;
				
 		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<MeteoDay> getDatiMese(Month month) {
		String sql = "Select localita, data, umidita\r\n" + 
				"from situazione\r\n" + 
				"where month(data) = ? order by data";
		List<MeteoDay> list = new ArrayList<>();
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, month.getValue());
			ResultSet rs = st.executeQuery();
				
			while(rs.next()) {
				MeteoDay mday= new MeteoDay(rs.getString("localita"), rs.getDouble("umidita"), rs.getDate("data").toLocalDate());
				list.add(mday);
			}
			
			con.close();
			return list;
				
 		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<String> getCitta() {
		String sql = "Select distinct localita from situazione";
		List<String> list = new ArrayList<>();
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
				
			while(rs.next()) {
				String citta=rs.getString("localita");
				list.add(citta);
			}
			
			con.close();
			return list;
				
 		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
