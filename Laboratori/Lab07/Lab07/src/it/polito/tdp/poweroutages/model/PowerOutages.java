package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class PowerOutages {

	
	public String toString() {
		return "PowerOutages [id=" + id + ", eventType=" + eventType + ", tagId=" + tagId + ", areaId=" + areaId
				+ ", nercId=" + nercId + ", responsibleId=" + responsibleId + ", costumersAffected=" + costumersAffected
				+ ", dataInizio=" + dataInizio + ", dataFine=" + dataFine + "]";
	}


	private int id;
	private int eventType;
	private int tagId;
	private int areaId;
	private int nercId;
	private int responsibleId;
	private int costumersAffected;
	private LocalDateTime dataInizio;
	private LocalDateTime dataFine;

	
	public PowerOutages(int id, int eventType, int tagId, int areaId, int nercId, int responsibleId,
			int costumersAffected, LocalDateTime dataInizio, LocalDateTime dataFine) {
		this.id = id;
		this.eventType = eventType;
		this.tagId = tagId;
		this.areaId = areaId;
		this.nercId = nercId;
		this.responsibleId = responsibleId;
		this.costumersAffected = costumersAffected;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getEventType() {
		return eventType;
	}


	public void setEventType(int eventType) {
		this.eventType = eventType;
	}


	public int getTagId() {
		return tagId;
	}


	public void setTagId(int tagId) {
		this.tagId = tagId;
	}


	public int getAreaId() {
		return areaId;
	}


	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}


	public int getNercId() {
		return nercId;
	}


	public void setNercId(int nercId) {
		this.nercId = nercId;
	}


	public int getResponsibleId() {
		return responsibleId;
	}


	public void setResponsibleId(int responsibleId) {
		this.responsibleId = responsibleId;
	}


	public int getCostumersAffected() {
		return costumersAffected;
	}


	public void setCostumersAffected(int costumersAffected) {
		this.costumersAffected = costumersAffected;
	}


	public LocalDateTime getDataInizio() {
		return dataInizio;
	}


	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}


	public LocalDateTime getDataFine() {
		return dataFine;
	}


	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}
	
	
	
	
}
