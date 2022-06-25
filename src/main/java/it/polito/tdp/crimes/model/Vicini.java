package it.polito.tdp.crimes.model;

public class Vicini implements Comparable<Vicini> {

	Distretto d;
	Double distanza;
	public Vicini(Distretto d, double distanza) {
		super();
		this.d = d;
		this.distanza = distanza;
	}
	public Distretto getD() {
		return d;
	}
	public void setD(Distretto d) {
		this.d = d;
	}
	public double getDistanza() {
		return distanza;
	}
	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}
	@Override
	public String toString() {
		return this.getD().getId()+" - "+this.getDistanza();
	}
	@Override
	public int compareTo(Vicini o) {
		return  this.distanza.compareTo(o.distanza);
	}
	
	
	
}
