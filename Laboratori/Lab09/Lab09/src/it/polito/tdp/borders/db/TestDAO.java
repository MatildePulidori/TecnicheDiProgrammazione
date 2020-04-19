package it.polito.tdp.borders.db;

import java.util.List;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class TestDAO {

	public static void main(String[] args) {

		BordersDAO dao = new BordersDAO();
		
		List<Country> countries = dao.loadAllCountries();
		System.out.println();
		System.out.println("Lista di tutte le nazioni:");
		for(Country c:  countries) {
			System.out.println(c.toString());	
		}
		
		int anno = 1998;
		List<Border> borders = dao.getCountryPairs(anno);
		System.out.println();
		System.out.println("Lista di tutti i confini fino al "+anno+":");
		for(Border b:  borders) {
			System.out.println(b.toString());	
		}
		
	}
}
