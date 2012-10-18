/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aniket
 */
public class KEdge implements Comparable {
    /*
     * This is an Edge data storage class that is used in Kruskal
     * it takes two objects and an int weight and turns into an KEdge object
     * This is a weighted edge for a weighted undirected graph
     */
    
    private Object vertex1;
    private Object vertex2;
    private int weight;
    
    public KEdge(Object o1, Object o2, int weight){
        vertex1 = o1;
        vertex2 = o2;
        this.weight = weight;
    }
    
    public KEdge(){
        this(null, null, 0);
    }
    
    public void setVertex1(Object o){
        vertex1 = o;
    }
    
    public void setVertex2(Object o){
        vertex2 = o;
    }
    
    public void setWeight(int weight){
        this.weight = weight;
    }
    
    public Object getVertex1(){
        return vertex1;
    }
    
    public Object getVertex2(){
        return vertex2;
    }
    
    public int getWeight(){
        return weight;
    }
    
    
    public String toString(){
        String s = "(v1:" + vertex1 + ", v2:" + vertex2 + ", w:" + weight + ")";
        return s;
    }
    
    
    /*
     * Two KEdges are equal if this.vertex1 equals e.vertex1 and this.vertex2 
     * equals e.vertex2 or if this.vertex1 equals e.vertex2 and this.vertex2 
     * equals e.vertex1. i.e edge (u,v) equals edge (v,u) and edge (u,v)
     */
    public boolean equals(Object e){
     if(e instanceof KEdge){
         return (((vertex1.equals(((KEdge) e).vertex1) && vertex2.equals(((KEdge) e).vertex2)) || 
                 (vertex1.equals(((KEdge) e).vertex2) && vertex2.equals(((KEdge) e).vertex1))) && 
                 (weight == ((KEdge) e).weight));
     } else {
         return false;
     }   
    }

    /*
     * used for sorting KEdges by weight from low to high
     * this only sorts KEdges by weight
     * a return value of 0 does not necessarily means this KEdge object.equals()
     * another KEdge object. 
     */
    public int compareTo(Object o) {
        if(o instanceof KEdge){
        KEdge e = (KEdge) o;
        if(this.weight < e.weight){
            return -1;
        } else {
            if(this.weight > e.weight){
                return 1;
            } else {
                return 0; // weights are equal
            }
        }
        
        } else {
            return -1; //KEdge comes before any other object. default value.
        }
    }
    
    
   /**
   * hashCode() returns a hashCode equal to the sum of the hashCodes of each
   * of the two vertex objects of the pair, so that the order of the objects will
   * not affect the hashCode.  Self-edges are treated differently:  we don't
   * add an object's hashCode to itself, since the result would always be even.
   * We add one to the hashCode so that a self-edge will not collide with the
   * object itself if vertices and edges are stored in the same hash table.
   */ 
   public int hashCode() {
    if (vertex1.equals(vertex2)) {
      return vertex1.hashCode() + 1;
    } else {
      return vertex1.hashCode() + vertex2.hashCode();
    }
  }
    
    
    
}
