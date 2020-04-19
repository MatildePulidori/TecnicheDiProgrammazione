package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.List;

public class Bacino {
	
	private double flowMedio ;
	private double Q;
	private double CBase;
	private double flowOutMin;
	private List<Double> listaC;
	
	
	public Bacino(double flowMedio, double q, double CBase, double flowOutMin) {
		super();
		this.flowMedio = flowMedio;
		this.Q = q;
		this.CBase = CBase;
		this.flowOutMin = flowOutMin;
		this.listaC = new ArrayList<Double>();
		
	}


	public double getFlowMedio() {
		return flowMedio;
	}


	public void setFlowMedio(double flowMedio) {
		this.flowMedio = flowMedio;
	}


	public double getQ() {
		return Q;
	}


	public void setQ(double q) {
		Q = q;
	}


	public double getCBase() {
		return CBase;
	}

	public void setCBase(double cBase) {
		this.CBase=cBase;
	}

	public double aggiugniCOggi(double COggi) {
		this.listaC.add(COggi);
		return COggi;
	}


	public double getFlowOutMin() {
		return flowOutMin;
	}


	public void setFlowOut(double flowOutMin) {
		this.flowOutMin = flowOutMin;
	}
	
	public List<Double> getListaC(){
		return this.listaC;
	}
	
	

}
