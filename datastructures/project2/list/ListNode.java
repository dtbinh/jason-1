/* ListNode.java */

package list;

import player.ChipCoordinate;
import player.Move;

/**
 *  A ListNode is a mutable node in a list.  No implementation is provided.
 *
 *  DO NOT CHANGE THIS FILE.
 **/

public abstract class ListNode {

  /**
   *  item references the item stored in the current node.
   *  myList references the List that contains this node.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected Object item;
  protected Move move = new Move();
  protected DList list = new DList();
  protected int[] coordinate = new int[2];
  protected ChipCoordinate chip = new ChipCoordinate();

  

  /**
   *  next() returns the node following this node.  If this node is invalid,
   *  throws an exception.
   *
   *  @return the node following this node.
   *  @exception InvalidNodeException if this node is not valid.
   */
  public abstract ListNode next();

  /**
   *  prev() returns the node preceding this node.  If this node is invalid,
   *  throws an exception.
   *
   *  @param node the node whose predecessor is sought.
   *  @return the node preceding this node.
   *  @exception InvalidNodeException if this node is not valid.
   */
  public abstract ListNode prev();
  
  public abstract boolean nodeHasList();
  
  public abstract boolean hasNext();
  
  public abstract Move getMove();

  public abstract DList getList();
  
  public abstract ChipCoordinate getChip();
  
  public abstract int getCoorx();
	
  public abstract int getCoory();
	

}
