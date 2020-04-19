package it.polito.tdp.Ruzzle.model;
import java.util.*;

public class TestModel {

	public static void main(String[] args) {
		for (int size = 4; size<16; size++) {
			Model m = new Model(size);
			m.reset();
			long start = System.nanoTime();
			List<String> tutte = m.trovaTutte();
			long stop = System.nanoTime();
			
			long tempoImpiegato = stop-start;
			System.out.format("Size = %d, trovate %d parola in %d msec \n",	size, tutte.size(), tempoImpiegato/1000000);
		}

	}

}
