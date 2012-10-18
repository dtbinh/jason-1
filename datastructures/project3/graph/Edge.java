package graph;

/**
 * 
 * @author Yang Peng and Kuan Chao
 *
 */
/*[iii]  If you want to use an encapsulated DList class, you could use
just a single object to represent an edge, and put this object
into both adjacency lists.  The edge object contains two
DListNode references (signifying its position in each DList), so
it can extract itself from both adjacency lists in O(1) time.*/

public class Edge {
	private Vertex u;
    private Vertex v;
	private int weight;
	
	//Constructs an edge with a vertex pair and a given weight
	public Edge(Vertex u, Vertex v, int weight){
		this.u = u;
		this.v = v;
		this.weight = weight;
		
	}
	/*
	 * getPartnerRef returns the partner
	 */
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public int getWeight(){
		return this.weight;
	}
	public Vertex getVertexU(){
		return this.u;
	}
	public Vertex getVertexV(){
		return this.v;
	}
	
	
	
	
}
