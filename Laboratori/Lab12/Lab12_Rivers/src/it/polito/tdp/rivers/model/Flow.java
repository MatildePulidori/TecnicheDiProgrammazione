package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Flow implements Comparable<Flow>{
	
	private int id;
	private LocalDate day;
	private double flow;
	private River river;
	
	private double flowAlGiorno;
	private double Q;
	private double flowOut;

	public Flow(int id, LocalDate day, double flow, River river) {
		this.id=id;
		this.day = day;
		this.flow = flow;
		this.river = river;
	}

	
	public int getId() {
		return id;
	}


	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public double getFlow() {
		return flow;
	}

	public void setFlow(double flow) {
		this.flow = flow;
	}

	
	public double getFlowAlGiorno() {
		return flowAlGiorno;
	}


	public void setFlowAlGiorno(double flowAlGiorno) {
		this.flowAlGiorno = flowAlGiorno;
	}

	public double getQ() {
		return Q;
	}

	public void setQ(double Q) {
		this.Q = Q;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flow other = (Flow) obj;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Flow [day=" + day + ", flow=" + flow + ", river=" + river + "]";
	}

	@Override
	public int compareTo(Flow other) {
		return this.getDay().compareTo(other.getDay());
	}


	public double getFlowOut() {
		return this.flowOut;
	}
	
	public void setFlowOut(double flowOut) {
		this.flowOut = flowOut;
	}


	

	
}
