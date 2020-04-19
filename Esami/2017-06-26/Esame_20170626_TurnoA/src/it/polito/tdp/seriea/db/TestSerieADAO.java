package it.polito.tdp.seriea.db;

import java.util.List;

import it.polito.tdp.seriea.model.Season;

public class TestSerieADAO {

	public static void main(String[] args) {
		SerieADAO dao = new SerieADAO();

		List<Season> seasons = dao.listSeasons();
		System.out.println(seasons);
		System.out.println("Seasons # rows: " + seasons.size());

	}

}
