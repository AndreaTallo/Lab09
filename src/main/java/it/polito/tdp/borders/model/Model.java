package it.polito.tdp.borders.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;





public class Model {
	private BordersDAO dao = new  BordersDAO();
	private Map<Integer,Country> idMap;
	private SimpleWeightedGraph<Country, DefaultEdge> grafo;
	//private SimpleWeightedGraph<Country, DefaultEdge> grafo2;
	private List<Border> confini;
	private ConnectivityInspector<Country,DefaultEdge> conn;
	private Map<Country, Country> visita;
	
	public Model() {
		idMap=new  TreeMap<Integer,Country>() ;
		dao.loadAllCountries(idMap);
		
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleWeightedGraph<>(DefaultEdge.class);
		//aggiungo vertici "filtrati"
		Graphs.addAllVertices(grafo, dao.getVertici(anno, idMap));
		confini=dao.getCountryPairs(anno,idMap);
		conn=new ConnectivityInspector<Country,DefaultEdge>(grafo);
		for(Border bb:confini ) {
			DefaultEdge e = this.grafo.getEdge(bb.getNazione1(), bb.getNazione2());
		if(e == null) {
			Graphs.addEdgeWithVertices(grafo, bb.getNazione1(), bb.getNazione2());
		}
	}
	}
	/*public void creaGrafoSenzaAnno() {
		grafo2 = new SimpleWeightedGraph<>(DefaultEdge.class);
		//aggiungo vertici "filtrati"
		Graphs.addAllVertices(grafo2, dao.getVerticiSenzaAnno(idMap));
		confini=dao.getCountryPairsSenzaAnno(idMap);
		for(Border bb:confini ) {
			DefaultEdge e = this.grafo2.getEdge(bb.getNazione1(), bb.getNazione2());
		if(e == null) {
			Graphs.addEdgeWithVertices(grafo2, bb.getNazione1(), bb.getNazione2());
		}
	}
	}
	*/
	public String grado() {
		String s="COMPONENTI CONNESSE: "+conn.connectedSets().size()+"\n";
		for(Country cc:grafo.vertexSet()) {
			s=s+cc.getAbbreviazione()+" "+grafo.degreeOf(cc)+"\n";
		}
		return s;
		
		
	}

	public List<Country> getCountryList() {
		return dao.getCountry();
	}
	
	public Set<Country> trovaNazioni(Country c1){
		
		BreadthFirstIterator<Country, DefaultEdge> it 
								= new BreadthFirstIterator<>(grafo, c1);
		visita = new HashMap<>();
		visita.put(c1, null);
		
		it.addTraversalListener(new TraversalListener<Country, DefaultEdge>(){

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				Country nazione1 = grafo.getEdgeSource(e.getEdge());
				Country nazione2 = grafo.getEdgeTarget(e.getEdge());
				
				
				
				if(visita.containsKey(nazione1) && !visita.containsKey(nazione2)) {
					visita.put(nazione2, nazione1);
				} else if (visita.containsKey(nazione2) && !visita.containsKey(nazione1)){
					visita.put(nazione1, nazione2);
				}
				
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

		});
		while(it.hasNext()) {
			it.next();
		}
		Set<Country> temp=new HashSet<Country>();
		for(Country cc:visita.keySet())
			temp.add(cc);
		
		for(Country cc:visita.values())
			temp.add(cc);
		
		return temp;


		
	
	
	}
	
	
	

}
