package it.polito.tdp.formulaone.model;

import java.util.HashMap;

public class DriverIdMap {
	
private HashMap<Integer, Driver> map;
	
	public DriverIdMap() {
		this.map = new HashMap<Integer, Driver>();
	} 
	
	public Driver get(int driverId) {
		return this.map.get(driverId);
	}

	public Driver get(Driver driver) {
		Driver old = this.map.get(driver.getDriverId());
		if (old == null) {
			this.map.put(driver.getDriverId(), driver);
			return driver;
		}
		return old;
	}
	

}
