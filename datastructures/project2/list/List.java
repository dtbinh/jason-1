/* List.java */

package list;

import player.ChipCoordinate;
import player.Move;

/**
 *  A List is a mutable list ADT.  No implementation is provided.
 *
 *  DO NOT CHANGE THIS FILE.
 **/

public abstract class List {

  /**
   *  size is the number of items in the list.
   **/

  protected int size;

  /**
   *  isEmpty() returns true if this List is empty, false otherwise.
   *
   *  @return true if this List is empty, false otherwise. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public boolean isEmpty() {
    return size == 0;
  }

  /** 
   *  getSize() returns the length of this List. 
   *
   *  @return the length of this List.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int getSize() {
    return size;
  }

 

  public abstract void insertEnd(Move m);
  public abstract void insertEnd(ChipCoordinate chip);
  public abstract void insertEnd(int x, int y);
  public abstract void insertEnd(DList list);
  public abstract void removeEnd();
  public abstract void removeFront();
  
  
  /**
   *  front() returns the node at the front of this List. 
   *  @return a ListNode at the front of this List.
   */
  public abstract ListNode front();


  

}
