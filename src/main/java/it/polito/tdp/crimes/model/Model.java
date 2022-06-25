package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	
	private EventsDao dao;
	private List<Distretto> vertici;
	private SimpleWeightedGraph<Distretto,DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao= new EventsDao();
		this.vertici= new ArrayList<>();
	}
	
	public List<Integer> getAnni(){
		return this.dao.getAnno();
	}
	
	public void creaGrafo(int anno) {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.vertici=this.dao.getDistretti(anno);
		//aggiungo vertici
		Graphs.addAllVertices(this.grafo, this.vertici);
		//aggiungo archi
		for(Distretto d1: this.vertici) {
			for(Distretto d2: this.vertici) {
				if(d1.getId()!=d2.getId()) {
					double peso=LatLngTool.distance(d1.getCoordinate(), d2.getCoordinate(), LengthUnit.KILOMETER);
					Graphs.addEdgeWithVertices(this.grafo, d1, d2, peso);
				}
			}
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Distretto> getVertici(){
		return this.vertici;
	}
	
	public List<Vicini> getVicini(Distretto selezionato){
		List<Vicini> result= new ArrayList<>();
		List<Distretto> distretti= Graphs.neighborListOf(this.grafo,selezionato);
		for(Distretto d : distretti) {
			Vicini v= new Vicini(d,this.grafo.getEdgeWeight(this.grafo.getEdge(selezionato, d)));
			result.add(v);
		}
		Collections.sort(result);
		return result;
	}
}
