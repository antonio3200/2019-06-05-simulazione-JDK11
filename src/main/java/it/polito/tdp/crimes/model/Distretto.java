package it.polito.tdp.crimes.model;

import com.javadocmd.simplelatlng.LatLng;

public class Distretto {

	int id;
	LatLng coordinate;
	public Distretto(int id, LatLng coordinate) {
		super();
		this.id = id;
		this.coordinate = coordinate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LatLng getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(LatLng coordinate) {
		this.coordinate = coordinate;
	}
	@Override
	public String toString() {
		return this.id+" - "+this.coordinate;
	}
	
	
	
}
