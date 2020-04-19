package it.polito.tdp.flightdelays.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class FlightTime {
	
	private Airport originAirport;
	private Airport destinationAirport;
	private ZonedDateTime scheduledDepartureDate;
	private ZonedDateTime arrivalDate;
	private int departureDelay;
	private int arrivalDelay;
	private int timeZoneDifference;
	
	
	public FlightTime(Airport originAirport, Airport destinationAirport, LocalDateTime scheduledDepartureDate,
			LocalDateTime arrivalDate, int departureDelay, int arrivalDelay) {
		super();
		this.originAirport = originAirport;
		this.destinationAirport = destinationAirport;
		this.scheduledDepartureDate = ZonedDateTime.of(scheduledDepartureDate, ZoneOffset.ofHours(originAirport.getTimezone()));
		this.arrivalDate = ZonedDateTime.of(arrivalDate, ZoneOffset.ofHours(destinationAirport.getTimezone()));
		this.departureDelay = departureDelay;
		this.arrivalDelay = arrivalDelay;
		
		this.timeZoneDifference= destinationAirport.getTimezone()-originAirport.getTimezone();
	}


	public Airport getOriginAirport() {
		return originAirport;
	}


	public void setOriginAirport(Airport originAirport) {
		this.originAirport = originAirport;
	}


	public Airport getDestinationAirport() {
		return destinationAirport;
	}


	public void setDestinationAirport(Airport destinationAirport) {
		this.destinationAirport = destinationAirport;
	}


	public ZonedDateTime getScheduledDepartureDate() {
		return scheduledDepartureDate;
	}


	public void setScheduledDepartureDate(ZonedDateTime scheduledDepartureDate) {
		this.scheduledDepartureDate = scheduledDepartureDate;
	}


	public ZonedDateTime getArrivalDate() {
		return arrivalDate;
	}


	public void setArrivalDate(ZonedDateTime arrivalDate) {
		this.arrivalDate = arrivalDate;
	}


	public int getDepartureDelay() {
		return departureDelay;
	}


	public void setDepartureDelay(int departureDelay) {
		this.departureDelay = departureDelay;
	}


	public int getArrivalDelay() {
		return arrivalDelay;
	}


	public void setArrivalDelay(int arrivalDelay) {
		this.arrivalDelay = arrivalDelay;
	}


	public int getTimeZoneDifference() {
		return timeZoneDifference;
	}


	public void setTimeZoneDifference(int timeZoneDifference) {
		this.timeZoneDifference = timeZoneDifference;
	}
	
	
	
	
	

}
