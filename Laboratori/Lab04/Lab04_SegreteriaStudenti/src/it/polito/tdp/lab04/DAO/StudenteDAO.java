package it.polito.tdp.lab04.DAO;

import java.sql.*;
import java.util.*;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	/**
	 * Restituisce vero se lo studente (@st) è iscritto al corso (@c).
	 * Altrimenti falso.
	 * @param st
	 * @param c
	 * @return
	 */
	public boolean isStudenteIscrittoACorso(Studente studente, Corso c) {
		final String sql = "SELECT codins , matricola "+
				"FROM iscrizione "+
				"WHERE iscrizione.codins = ? "+ 
					"AND iscrizione.matricola =?";
		
		boolean iscritto = false;
		
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, c.getCodiceInsegnamento());
			st.setInt(2, studente.getMatricola() );
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				iscritto = true;
			}
			
			conn.close();
			return iscritto;
		} catch (SQLException e) {
		
		} catch (NumberFormatException nfe) {
			
		}
		
		return false;	
	}

	
	public List<Corso> getCorsiACuiIscrittoStudente (Studente s) {
		
		final String sql = "SELECT *\n" + 
				"FROM corso\n" + 
				"WHERE codins IN \n" + 
				"	(SELECT codins \n" + 
				"	FROM iscrizione\n" + 
				"	WHERE matricola = ?) ";
		List<Corso> corsi = new ArrayList<Corso>();
		
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, s.getMatricola());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso (rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"),  rs.getInt("pd"));
				corsi.add(c);
			}
			
			conn.close();
			return corsi;
		}
		catch (SQLException sqle) {
			throw new RuntimeException();
		}
	}
	
	
	/**
	 * Restituisce uno studente, data un numero di matricola (@matricola)
	 * @param matricola
	 * @return
	 */
	public Studente getStudente(int matricola) {

		final String sql = "SELECT * FROM studente where matricola=?";

		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			Studente studente = null;

			if (rs.next()) {

				studente = new Studente(matricola, rs.getString("nome"), rs.getString("cognome"));
			}

			conn.close();
			return studente;

		} catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
	}

}
