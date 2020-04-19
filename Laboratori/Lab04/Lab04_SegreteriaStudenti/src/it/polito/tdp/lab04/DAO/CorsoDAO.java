package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;


public class CorsoDAO {

	/**
	 * Ottiene tutti i corsi del database
	 * @return
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new ArrayList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);	
				corsi.add(c);
			}
			conn.close();
			return corsi;

		} catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
	}

	
	/**
	 * Dato un codice insegnamento, ottengo il corso
	 * @param corso
	 */
	public void getCorso(Corso corso) {
		
		final String sql = "SELECT *"+
				"FROM corso "+
				"WHERE (codins = ? )"; 

		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodiceInsegnamento()); 
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				corso.setCrediti(rs.getInt("crediti"));
				corso.setNomeCorso(rs.getString("nome"));
				corso.setPeriodoDidattico(rs.getInt("pd"));
			}
			conn.close();
			
		} catch (SQLException sqle) {
			
			throw new RuntimeException("Errore Db");
		}
	}

	/**
	 * Ottengo tutti gli studenti iscritti al Corso 
	 * @param corso
	 * @return
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		final String sql = "SELECT * FROM iscrizione, studente WHERE iscrizione.matricola=studente.matricola AND codins=?";

		List<Studente> studenti = new ArrayList<Studente>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodiceInsegnamento()); 
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				int matricola = rs.getInt("matricola");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				
				Studente s = new Studente(matricola, nome, cognome);
				studenti.add(s);
			}
			conn.close();
			return studenti;
			
		} catch(SQLException sqle) {
			throw new RuntimeException("Errore Db");
		}
	
		
	}

	/**
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso
	 * @param studente
	 * @param corso
	 * @return
	 */
	public boolean iscriviStudenteACorso(Studente studente, Corso corso) {
		final String sql = "INSERT INTO iscrizione VALUES (?, ?)";
		
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodiceInsegnamento());
		
			int rs = st.executeUpdate();
	
			if (rs!=0) {
				conn.close();
				return true;
			}
			conn.close();
			return false;
		
		} catch (SQLException e) {
			throw new RuntimeException("Errore DB");
		}
	}

	
}
