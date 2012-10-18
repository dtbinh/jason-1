package graph;
import list.*;
/* 
 * vertex class represents a vertex with an Object that stores within this vertex and an adjacency list of edges 
 * Author: Yang Peng and Kuan Chao
 */
public class Vertex {
	private Object vertexObj;
	private DList adjacencyList; //the adjacency list of edges
	
	/*
	 * Create a new Vertex with an Object that stores within this vertex
	 */
	public Vertex(Object vertexObj){
		this.vertexObj = vertexObj;
		this.adjacencyList = new DList();
	}
	/*
	 * getObject returns the object in this vertex
	 */
	public Object getObject(){
		return vertexObj;
	}
	/*
	 * addEdges adds a new edge to the adjacency list of edges
	 */
	public void addEdges(Edge edge){
		adjacencyList.insertBack(edge);
	}
	
	/*
	 * getEdges returns the adjacency list of edges for this vertex
	 */
	public DList getEdges(){
		return adjacencyList;
		
	}
	
	
	
	
}
