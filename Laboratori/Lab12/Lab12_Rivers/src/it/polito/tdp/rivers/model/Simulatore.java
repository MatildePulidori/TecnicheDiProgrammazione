package it.polito.tdp.rivers.model;


public class Simulatore {
	
	private River river ;
	private int k;
	private Bacino bacino ;

	private int giorniNo;
	
	public Simulatore(River river, int k) {
		this.river = river;
		this.k = k;
		this.giorniNo=0;
		this.init(river);
	}
	
	
	
	public Bacino getBacino() {
		return bacino;
	}



	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}


	public void init(River r) {
		double flowMed = r.getMediaFlusso()*60*60*24;
		double Q = flowMed * k * 30;
		double CBase = Q /2 ;
		double flowOutMin = flowMed * 0.8;
		bacino = new Bacino(flowMed, Q, CBase, flowOutMin);
		
	}

	public void run() {
		for (Flow f : river.getFlows()) {
			this.processFlow(f);
		}
	}

	

	private void processFlow(Flow f) {
		
		
		double fIn = f.getFlow()*60*60*24;
		
			
			// Acqua oggi : la aggiorno aggiungendo il flusso in entrata
			double cOggi = bacino.getCBase()+fIn;
			
			// se l'acqua è maggiore della capacità, uscirà e riporo il livello alla Q max
			if (cOggi >bacino.getQ()) {
				// tracimazione
				cOggi = bacino.getQ();
			}
			
			// Probabilità
			double p = Math.random();
			
			// Flow min che deve uscire
			double flowMin = bacino.getFlowOutMin();
			
			if(p <= 0.05) {
				//10 volte
				flowMin = flowMin * 10;
			}
			
			// se l'Acqua oggi è maggiore del flow min che deve uscire
			if (cOggi>flowMin) {
				cOggi -= flowMin;
			}
			else {
				giorniNo++;
				cOggi = 0;
			}
			
			bacino.aggiugniCOggi(cOggi);
			bacino.setCBase(cOggi);

		
	}

	public int getGiorniNo() {
		return giorniNo;
	}

	public double getCMedio() {
		double somma = 0;
		for (Double d : bacino.getListaC()) {
			somma += d;
		}
		return somma / bacino.getListaC().size();
	}


}
