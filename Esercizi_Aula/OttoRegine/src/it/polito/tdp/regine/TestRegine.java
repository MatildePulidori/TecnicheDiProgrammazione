package it.polito.tdp.regine;

import java.util.List;

public class TestRegine {

	public static void main(String[] args) {
		Regine r = new Regine() ;
		
		List<Integer> sol = r.posiziona(8) ;
		
		System.out.println(sol);

	}

}
