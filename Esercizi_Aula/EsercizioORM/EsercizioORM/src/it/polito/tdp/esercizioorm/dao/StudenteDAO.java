package it.polito.tdp.esercizioorm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.esercizioorm.model.Corso;
import it.polito.tdp.esercizioorm.model.Studente;
import it.polito.tdp.esercizioorm.model.StudenteIdMap;

public class StudenteDAO {

	public List<Studente> getTuttiStudenti(StudenteIdMap studentemap){
		
		String sql = "SELECT matricola, nome, cognome, cds FROM studente";

		List<Studente> result = new ArrayList<Studente>();

		try {
			Connection conn = ConnectDBCP.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Studente s= new Studente(res.getInt("matricola"), res.getString("nome"), res.getString("cognome"),
						res.getString("cds"));
				result.add(studentemap.get(s));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	public void getStudentiFromCorso(Corso c, StudenteIdMap studentemap) {
		
		String sql = "SELECT s.matricola, s.nome, s.cognome, s.cds FROM studente as s, iscrizione as i WHERE s.matricola=i.matricola and i.codins= ? ";
		
		
		try {
			Connection conn = ConnectDBCP.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, c.getCodIns());
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				Studente s = new Studente(res.getInt("matricola"), res.getString("nome"), res.getString("cognome"),
						res.getString("cds"));
				c.getStudenti().add(studentemap.get(s));
			}
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public boolean iscriviStudenteACorso(Studente studente, Corso corso) { 
		// Copia soluzione lab4
		
		final String sql = "INSERT INTO iscrizione VALUES (?, ?)";
				
		Connection conn = ConnectDBCP.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodIns());
		
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
