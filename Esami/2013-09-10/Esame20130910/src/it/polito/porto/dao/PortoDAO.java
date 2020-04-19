package it.polito.porto.dao;

import it.polito.porto.bean.PortoArticle;
import it.polito.porto.bean.PortoArticleIdMap;
import it.polito.porto.bean.PortoCreator;
import it.polito.porto.bean.PortoCreatorIdMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PortoDAO {

	public List<PortoArticle> getAllArticle(PortoArticleIdMap articleIdMap) {
		final String sql = "SELECT eprintid, year, title FROM article";

		List<PortoArticle> result = new ArrayList<PortoArticle>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				PortoArticle art = new PortoArticle(
						rs.getLong("eprintid"),
						rs.getInt("year"),
						rs.getString("title")
						) ;
				result.add(articleIdMap.getPortoArticle(art));
			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public List<PortoArticle> getArticlesOfCreator(PortoCreator cre, PortoCreatorIdMap creatorIdMap, PortoArticleIdMap articleIdMap) {
		final String sql = "SELECT article.eprintid, year, title FROM article, authorship " +
				"WHERE article.eprintid = authorship.eprintid " +
				"AND authorship.id_creator = ?";

		List<PortoArticle> result = new ArrayList<PortoArticle>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, creatorIdMap.getPortoCreator(cre).getId()) ;

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				PortoArticle art = new PortoArticle(
						rs.getLong("eprintid"),
						rs.getInt("year"),
						rs.getString("title")
						) ;
				
				result.add(articleIdMap.getPortoArticle(art));
				
				articleIdMap.getPortoArticle(art).addCreator(creatorIdMap.getPortoCreator(cre)); // aggiungo l'autore alla lista degli autori dell'articolo
				creatorIdMap.getPortoCreator(cre).addArticolo(art); // aggiungo l'articolo alla lista degli articoli dell'autore 
			}
			
			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	
	public List<PortoCreator> getAllCreator(PortoCreatorIdMap creatorIdMap) {
		final String sql = "SELECT id_creator, family_name, given_name FROM creator";

		List<PortoCreator> result = new ArrayList<PortoCreator>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				PortoCreator cre = new PortoCreator(
						rs.getInt("id_creator"),
						rs.getString("given_name"),
						rs.getString("family_name")
						) ;
				result.add(creatorIdMap.getPortoCreator(cre));
			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public List<PortoCreator> getCreatorsOfArticle(PortoArticle art, PortoCreatorIdMap creatorIdMap, PortoArticleIdMap articleIdMap ) {
		final String sql = "SELECT creator.id_creator, family_name, given_name FROM creator, authorship " +
				"WHERE authorship.id_creator=creator.id_creator " +
				"AND authorship.eprintid = ?";

		List<PortoCreator> result = new ArrayList<PortoCreator>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setLong(1, art.getEprintid()) ;

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				PortoCreator cre = new PortoCreator(
						rs.getInt("id_creator"),
						rs.getString("given_name"),
						rs.getString("family_name")
						) ;
				
				articleIdMap.getPortoArticle(art).addCreator(creatorIdMap.getPortoCreator(cre)); // aggiungo il creator alla lista di creator dell'articolo
				creatorIdMap.getPortoCreator(cre).addArticolo(art); // aggiungo l'articolo alla lista degli articoli del creator
				
				result.add(creatorIdMap.getPortoCreator(cre));
			}

			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}


	
	public int createArticle(PortoArticle art) {
		final String sql = "INSERT INTO article (eprintid, year, title) VALUES (?, ?, ?)";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setLong(1, art.getEprintid());
			st.setInt(2, art.getDate());
			st.setString(3, art.getTitle());

			int res = st.executeUpdate();

			st.close();
			conn.close();
			return res;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int createArticles(Collection<PortoArticle> articles) {
		final String sql = "INSERT INTO article (eprintid, year, title) VALUES (?, ?, ?)";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			int res = 0;
			
			for (PortoArticle art : articles) {

				st.setLong(1, art.getEprintid());
				st.setInt(2, art.getDate());
				st.setString(3, art.getTitle());

				res += st.executeUpdate();
			}

			st.close();
			conn.close();
			return res;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
	}

	public int createCreator(PortoCreator cre) {
		final String sql = "INSERT INTO creator (id_creator, family_name, given_name) VALUES (?, ?, ?)";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, cre.getId());
			st.setString(2, cre.getFamily());
			st.setString(3, cre.getGiven());

			int res = st.executeUpdate();

			st.close();
			conn.close();
			return res;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void addAuthorship(PortoCreator cre, PortoArticle art) {
		final String sql = "INSERT INTO authorship (eprintid, id_creator) VALUES (?, ?)";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setLong(1, art.getEprintid()) ;
			st.setInt(2, cre.getId());

			st.executeUpdate();

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
		
	}
	
	/**
	 * Test methods for the DAO
	 * @param args
	 */
	public static void main(String[] args) {
		//PortoDAO dao = new PortoDAO() ;
		
		//List<PortoCreator> creators = dao.getAllCreator() ;
		//List<PortoArticle> articles = dao.getAllArticle() ;
		
		//System.out.format("Loaded %d authors and %d articles\n", creators.size(), articles.size()) ;
		
		/*for( PortoCreator cre: creators) {
			System.out.format("%d %s %s\n", cre.getId(), cre.getFamily(), cre.getGiven()) ;
			List<PortoArticle> myArticles = dao.getArticlesOfCreator(cre) ;
			cre.setArticoli(myArticles) ;
			for( PortoArticle art: myArticles) {
				System.out.format("    %d %d %s\n", art.getDate(), art.getEprintid(), art.getTitle()) ;
			}
		}
		
		
		for( PortoArticle art: articles) {
			System.out.format("%d %d %s\n", art.getDate(), art.getEprintid(), art.getTitle()) ;
			List<PortoCreator> myAutors = dao.getCreatorsOfArticle(art) ;
			// art.setCreators(myAutors) ;
			for( PortoCreator cre: myAutors ) {
				System.out.format("    %d %s %s\n", cre.getId(), cre.getFamily(), cre.getGiven()) ;
			}
		*/		
		}
		
	

}
