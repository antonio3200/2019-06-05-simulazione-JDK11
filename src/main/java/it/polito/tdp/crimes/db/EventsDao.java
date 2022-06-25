package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.crimes.model.Distretto;
import it.polito.tdp.crimes.model.Event;



public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> getAnno(){
		String sql="SELECT  distinct YEAR(reported_date) AS anno "
				+ "FROM events "
				+ "ORDER BY anno";
		List<Integer> result= new ArrayList<>();
		Connection conn= DBConnect.getConnection();
		try {
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				int i= rs.getInt("anno");
				result.add(i);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("SQL ERROR");
		}
		return result;
	}
	
	public List<Distretto> getDistretti(int anno){
		String sql="SELECT e.district_id AS id,AVG(e.geo_lon) AS lon, AVG(e.geo_lat) AS lat "
				+ "FROM events e "
				+ "WHERE YEAR(e.reported_date)=? "
				+ "GROUP BY id";
		List<Distretto> result=new ArrayList<>();
		Connection conn= DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs= st.executeQuery();
			while(rs.next()) {
				int id= rs.getInt("id");
				double lon= rs.getDouble("lon");
				double lat= rs.getDouble("lat");
				LatLng coor= new LatLng(lat,lon);
				Distretto d = new Distretto(id,coor);
				result.add(d);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("SQL ERROR");
		}
		return result;
	}

}
