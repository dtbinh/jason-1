/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/* Kruskal.java */

import graph.*;

import set.*;
import queue.*;
import list.*;
import dict.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   */
  public static WUGraph minSpanTree(WUGraph g){
     //Kruskal's algorithm
      
      //1.
      WUGraph T = new WUGraph();
      Object [] verts = g.getVertices();
      for(Object o : verts){
          T.addVertex(o);
      }
      
      //2.
      
      //DList of KEdges
      DList listOfKEdges = new DList();
      HashTableChained table = new HashTableChained(); // used for memberOf
      //table contains DList Nodes with the Kedges.
      
      for(Object o : verts){
      Neighbors n = g.getNeighbors(o);    
      Object [] nList = n.neighborList;
      int [] wList = n.weightList;
      
      for(int x = 0; x < nList.length; x++){
          //creates a weighted edge from Object o to neighbor 
          KEdge newEdge = new KEdge(o, nList[x], wList[x]);
          //no self edges allowed in this list.
          boolean selfEdge = newEdge.getVertex1().equals(newEdge.getVertex2());
          boolean mOf = memberOf(newEdge, table);
          if(!selfEdge && !mOf){
              listOfKEdges.insertBack(newEdge);
                    try {
                        table.insert(newEdge, listOfKEdges.back().item());
                    } catch (InvalidNodeException ex) {
                        
                    }
              
          }
          
      }   
      }
      //all the distinct edges are in listOfKEdges
      //System.out.println(listOfKEdges); //test works :)
      
      //3.
      //do merge sort 
      //put stuff in listOfKEdges into queue for sort
      LinkedQueue q = new LinkedQueue();
      DListNode newNode = (DListNode) listOfKEdges.front();
      while(newNode.isValidNode()){
          try{
           KEdge newEdge = (KEdge) newNode.item();  
           q.enqueue(newEdge);
           newNode = (DListNode) newNode.next();
           
          } catch(InvalidNodeException x){
              
          }
      }
      
     
      ListSorts.mergeSort(q); // sorts in O(ElogE) time
      
      
      
      verts = T.getVertices(); //array of the vertex objects in T
      DisjointSets d = new DisjointSets(verts.length);
      HashTableChained vertTableVertKey = new HashTableChained();
      //map all the vertex objects to ints from 0 to n-1 
      //where n is the number of vertex objects
      for(int x = 0; x < verts.length; x++){
          Object o = verts[x];
          vertTableVertKey.insert(o, x);
      }
     
      //objects are mapped
      //iterate through LinkedQueue q that contains KEdges in order
      while(!q.isEmpty()){
            try {
                KEdge e = (KEdge) q.dequeue();
                if (d.find((Integer) vertTableVertKey.find(e.getVertex1()).value())!= d.find((Integer) vertTableVertKey.find(e.getVertex2()).value())){
                	T.addEdge(e.getVertex1(), e.getVertex2(), e.getWeight());
                	d.union(d.find((Integer) vertTableVertKey.find(e.getVertex1()).value()), d.find((Integer)vertTableVertKey.find(e.getVertex2()).value()));
                }
                
            } catch (QueueEmptyException ex) {
              
            }
          
      }
      
      return T;
     
     
  }

  /*
   * helper function
   * returns whether KEdge e is in HashTableChained table.
   * HashTableChained table is a table of KEdges
   * returns true or false
   * a KEdge (u,v, weight) is the same as  KEdge (v,u, weight)
   */
  private static boolean memberOf (KEdge e, HashTableChained table){ //change using hastable   
      if (table.find(e) != null){
          return true;
      }else{
          return false;
      }
      
      
      
  }
  
  public static void main(String [] args){
      WUGraph g = new WUGraph(); 
      String a = "a";
      String b = "b";
      String c = "c";
      String d = "d";
      g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addVertex(d);
        g.addEdge(a, b, 350);
        g.addEdge(a, c, 54);
        g.addEdge(a, d, 23);
        g.addEdge(b, c, 940);
        g.addEdge(b, d, 578);
        g.addEdge(c, d, 15);
        g.addEdge(a, a, 23); //self edge
        g.addEdge(b, b, 234);
         Neighbors na = g.getNeighbors(a);
        Neighbors nb = g.getNeighbors(b);
        Neighbors nc = g.getNeighbors(c);
        Neighbors nd = g.getNeighbors(d); 
        for(Object o : na.neighborList){
            System.out.print(o + " ");
        }
        System.out.println();
        for(Object o : nb.neighborList){
            System.out.print(o + " ");
        }
        System.out.println();
        for(Object o : nc.neighborList){
            System.out.print(o + " ");
        }
        System.out.println();
        for(Object o : nd.neighborList){
            System.out.print(o + " ");
        }
        System.out.println();
        for(int x : na.weightList){
            System.out.print(x + " ");
        }
        System.out.println();
        for(int x : nb.weightList){
            System.out.print(x + " ");
        }
        System.out.println();
        for(int x : nc.weightList){
            System.out.print(x + " ");
        }
        System.out.println();
        for(int x : nd.weightList){
            System.out.print(x + " ");
        }
        System.out.println();
      Kruskal.minSpanTree(g);
  }
}
