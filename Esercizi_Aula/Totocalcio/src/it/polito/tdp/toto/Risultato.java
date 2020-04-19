package it.polito.tdp.toto;

public enum Risultato {

	UNO, DUE, ICS;

	public String toString() {
		switch(this) {
		case UNO:
			return "1";
		case DUE:
			return "2";
		case ICS:
			return "X";
		default:
			return null;
		}
	}

}
