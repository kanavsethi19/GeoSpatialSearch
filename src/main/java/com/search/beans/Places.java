package com.search.beans;

import java.util.Map;

/**
 * @author kanav.sethi
 *
 */
public class Places {
	private String location;
	private Map<Object, Object> locations;
	private Integer miles;
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Map<Object, Object> getLocations() {
		return locations;
	}

	public void setLocations(Map<Object, Object> locations) {
		this.locations = locations;
	}

	public Integer getMiles() {
		return miles;
	}

	public void setMiles(Integer miles) {
		this.miles = miles;
	}
	
}
