/* WUGraph.java */

package graph;
import list.*;
import dict.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
	private DList vertices;//You need to maintain a list of vertices, list is circular linked
	
	private HashTableChained vertexTable;
	private HashTableChained edgeTable; //Edge table from u to v
	private HashTableChained edgeTablev; //Edge table from v to u
	
	

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph(){
	  this.vertices = new DList();
	  this.vertexTable = new HashTableChained();
	  this.edgeTable = new HashTableChained();
	  this.edgeTablev = new HashTableChained();
	  
	  
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
	  return this.vertexTable.size();
  }

  /**
   * edgeCount() returns the number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
	  return this.edgeTable.size();
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices(){
	  if(this.vertexCount() ==0){
		  return new Object[0];
	  }
	  Object[] vertices = new Object[this.vertexCount()];
	  int a =0;
	  try {
		  
		  DListNode node = (DListNode) this.vertices.front();
		  while(a<this.vertexCount()){
			  
			  vertices[a] = ((Vertex) node.item()).getObject(); //node.item() refers to the vertex object at current node
			  a++;
			  node = (DListNode) node.next();
		  }
	  } catch (InvalidNodeException e) {
		  
	  }
	  return vertices;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.  The
   * vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex){
	  if (isVertex(vertex)){
		  return;
	  }
	  Vertex newVertex = new Vertex(vertex);
	  this.vertices.insertBack(newVertex);
	  vertexTable.insert(vertex, this.vertices.back());
	
	  
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex){
	  if (!isVertex(vertex)){
		  return;
	  }
	  try {
		  Vertex removeVertex =  (Vertex) ((ListNode) vertexTable.find(vertex).value()).item();
		  DList vertexEdges = removeVertex.getEdges();
		  DListNode node = (DListNode) vertexEdges.front();
		  while(true){
			  Object u = ((Edge) node.item()).getVertexU().getObject();
			  Object v = ((Edge) node.item()).getVertexV().getObject();
			  removeEdge(vertex, u);
			  removeEdge(vertex, v);
			  node = (DListNode) vertexEdges.front();
			  
		  }
	  
  	  } catch (InvalidNodeException e) {
  	  }
	  DListNode vertexNode = (DListNode) vertexTable.find(vertex).value();
	  try {
		vertexNode.remove();
	} catch (InvalidNodeException e) {
		
	}
	  vertexTable.remove(vertex);
  }

  /**
   * isVertex() retur ns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
	  return vertexTable.find(vertex)!= null;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex){
	 if (this.isVertex(vertex)){
		 Vertex a;
		try {
			a = (Vertex) ((ListNode) vertexTable.find(vertex).value()).item();
			return a.getEdges().length();
		} catch (InvalidNodeException e) {
			
		}
		 
	 }
	 return 0;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex){
	  //to be implemented
	  if (!isVertex(vertex)){
		  return null;
	  }
	  Neighbors myNeighbor = new Neighbors();
	  try {
		Vertex targetVertex = (Vertex) ((ListNode) vertexTable.find(vertex).value()).item();
		DList vertexEdges = targetVertex.getEdges();
		if (vertexEdges.isEmpty()){
			return null;
		}
		myNeighbor.neighborList = new Object[vertexEdges.length()];
		myNeighbor.weightList = new int[vertexEdges.length()];
		DListNode node = (DListNode) vertexEdges.front();
		int a = 0;
		while(true){
			Object u = ((Edge) node.item()).getVertexU().getObject();
			Object v = ((Edge) node.item()).getVertexV().getObject();
			if (vertex == u){
				myNeighbor.neighborList[a] = v;
				myNeighbor.weightList[a] = ((Edge) node.item()).getWeight();
			}else{
				myNeighbor.neighborList[a] = u;
				myNeighbor.weightList[a] = ((Edge) node.item()).getWeight();
			}
			a++;
			node = (DListNode) node.next();
		}
	} catch (InvalidNodeException e) {
		
	}
	  return myNeighbor;
	  
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the edge is already
   * contained in the graph, the weight is updated to reflect the new value.
   * Self-edges (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight){
	  if (!isVertex(u) || !isVertex(v)){
		  return;
	  }
	  VertexPair pair = new VertexPair(u,v);
	  if (this.isEdge(u, v)){
		  
			  Edge newEdge;
			try {
				newEdge = (Edge) ((ListNode) edgeTable.find(pair).value()).item();
				newEdge.setWeight(weight);
			  	return;
			} catch (InvalidNodeException e) {	
			}
		  	  
		  
	  }
	  Vertex vu = new Vertex(u);
	  Vertex vv = new Vertex(v);
	  Edge newEdge = new Edge(vu, vv, weight);
	  if (u == v){
		  Vertex vert;
		try {
			vert = (Vertex) ((ListNode) vertexTable.find(u).value()).item();
			vert.addEdges(newEdge);
			edgeTable.insert(pair, vert.getEdges().back());
		} catch (InvalidNodeException e) {
			
		}
		  
		  return;
	  }
	  
	  Vertex vertu;
	try {
		vertu = (Vertex) ((ListNode) vertexTable.find(u).value()).item();
		vertu.addEdges(newEdge);
		Vertex vertv = (Vertex) ((ListNode) vertexTable.find(v).value()).item();
		vertv.addEdges(newEdge);
		edgeTable.insert(pair, vertu.getEdges().back());
		edgeTablev.insert(pair, vertv.getEdges().back());
	} catch (InvalidNodeException e) {
		
	}
	  
	
	  
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v){
	  if (!isEdge(u, v)){
		  return;
	  }
	  VertexPair pair = new VertexPair(u, v);
	  
	  
	  try {
		  DListNode removeEdge = (DListNode) edgeTable.find(pair).value();
		  removeEdge.remove();
		  edgeTable.remove(pair);
		  if (u != v){
			  DListNode removeEdgev = (DListNode) edgeTablev.find(pair).value();
			  removeEdgev.remove();
			  edgeTablev.remove(pair);
		  }
	  } catch (InvalidNodeException e) {
		//cannot happen
	  }
	  
	  
	  
	  
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v){
	  if ( !(isVertex(u) && isVertex(v)) ){
		  return false;
	  }
	  VertexPair pair = new VertexPair(u, v);
	  return edgeTable.find(pair) != null;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but
   * also more annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v){
	  if (!isEdge(u, v)){
		  return 0;
	  }
	  VertexPair pair = new VertexPair(u, v);
	  Edge edge;
	try {
		 edge = (Edge) ((ListNode) edgeTable.find(pair).value()).item();
		 return edge.getWeight();
	} catch (InvalidNodeException e) {
		return 0;
	}
	 
  }

}
