package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;



public class BordersDAO {

	public void loadAllCountries(Map<Integer,Country> idMap) {

		String sql = "SELECT Ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
		
			
			if(!idMap.containsKey(rs.getInt("Ccode"))) {
				Country n= new Country( rs.getString("StateNme"), rs.getString("StateAbb"), rs.getInt("ccode"));
				idMap.put(rs.getInt("Ccode"), n);
			}
			
			}
			
			conn.close();
	

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	public List<Country> getCountry() {

		String sql = "SELECT Ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result=new ArrayList<Country>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
		
			
			
				Country n= new Country( rs.getString("StateNme"), rs.getString("StateAbb"), rs.getInt("ccode"));
				result.add(n);
			
			
			}
			rs.close();
			st.close();
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Country> getVertici(int anno, Map<Integer,Country> idMap){
		String sql = "SELECT state1no "
				+ "FROM contiguity "
				+ "WHERE conttype=1 AND YEAR<=? ";
			 
		List<Country> result = new LinkedList<>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				result.add(idMap.get(rs.getInt("state1no")));
			}
			
			rs.close();
			st.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Country> getVerticiSenzaAnno(Map<Integer,Country> idMap){
		String sql = "SELECT state1no "
				+ "FROM contiguity "
				+ "WHERE conttype=1 ";
			 
		List<Country> result = new LinkedList<>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				result.add(idMap.get(rs.getInt("state1no")));
			}
			
			rs.close();
			st.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	

	public List<Border> getCountryPairs(int anno,Map<Integer,Country> idMap) {
		String sql = "SELECT state1no, state2no "
				+ "FROM contiguity "
				+ "WHERE conttype=1 AND YEAR<=? ";
		List<Border> result = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			

			while (rs.next()) {
				Country sorgente = idMap.get(rs.getInt("state1no"));
				Country destinazione = idMap.get(rs.getInt("state2no"));
				if(sorgente != null && destinazione != null) {
					result.add(new Border(sorgente, 
							destinazione));
				}
				
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

	
	}
	public List<Border> getCountryPairsSenzaAnno(Map<Integer,Country> idMap) {
		String sql = "SELECT state1no, state2no "
				+ "FROM contiguity "
				+ "WHERE conttype=1";
		List<Border> result = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			

			while (rs.next()) {
				Country sorgente = idMap.get(rs.getInt("state1no"));
				Country destinazione = idMap.get(rs.getInt("state2no"));
				if(sorgente != null && destinazione != null) {
					result.add(new Border(sorgente, 
							destinazione));
				}
				
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

	
	}
}
