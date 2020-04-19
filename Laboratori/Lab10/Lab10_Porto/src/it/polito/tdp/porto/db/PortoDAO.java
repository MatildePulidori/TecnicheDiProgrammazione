package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.AuthorIdMap;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {
	
	/**
	 * Metodo che ritorna tutti gli autori del DB 
	 * @param authorIdMap
	 * @return
	 */
	public List<Author> getTuttiAutori(AuthorIdMap authorIdMap) {
		
		final String sql = "SELECT * FROM author ORDER BY author.lastname ASC";
		List<Author> autori = new ArrayList<Author>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				autori.add(authorIdMap.get(autore));
			}
			conn.close();
			return autori;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/**
	 * Metodo che trova tutti i coautori dato un articolo e un autore
	 * @param authorIdMap
	 * @param eprintid
	 * @return
	 */
	public List<Author> getAllCoAuthor(AuthorIdMap authorIdMap, int eprintid, int authorId){
		
		final String  sql = "SELECT * FROM creator WHERE eprintid = ?";
		List<Author> authors = new ArrayList<Author>();
	
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps =  conn.prepareStatement(sql);
			ps.setInt(1, eprintid);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Author autore = authorIdMap.get(rs.getInt("authorid"));
				//evito di inserire l'autore stesso ogni volta, o di aggiungere duplicati
				if (autore.getId()!=authorId && !authors.contains(autore)) {
					authors.add(authorIdMap.get(autore));	
				}
			}
			conn.close();
			return authors;
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	/**
	 * Metodo che ritorna tutti gli articoli del DB
	 * @param paperIdMap
	 * @return
	 */
	public List<Paper> getTuttiArticoli(PaperIdMap paperIdMap) {

		final String sql = "SELECT * FROM paper";
		List<Paper> papers = new ArrayList<Paper>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				try {
					Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
							rs.getString("publication"), rs.getString("type"), rs.getString("types"));
					papers.add(paperIdMap.get(paper));
				}catch(Throwable t) {
					t.printStackTrace();
				}
				
			}
			conn.close();
			return papers;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	/**
	 * Metodo che ritorna tutte le pubblicazioni di un autore
	 * @param paperIdMap
	 * @param authorid
	 * @return
	 */
	
	public List<Paper> getArticoli(PaperIdMap paperIdMap, AuthorIdMap authorIdMap, int authorid) {

		final String sql = "SELECT * FROM creator WHERE authorid= ? ";
		Author autore = authorIdMap.get(authorid);
		List<Paper> papersAuthor = new ArrayList<Paper>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, authorid);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Paper paper = paperIdMap.get(rs.getInt("eprintid"));
				
				if (!autore.getPapers().contains(paper))
					autore.getPapers().add(paper);
				if (!paper.getAuthors().contains(autore))
					paper.getAuthors().add(autore);
				
				papersAuthor.add(paper);
				
			}
			conn.close();
			return papersAuthor;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

}