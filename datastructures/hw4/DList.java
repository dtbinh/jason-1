/* DList.java */

package list;

/**
 *  A DList is a mutable doubly-linked list ADT.  Its implementation is
 *  circularly-linked and employs a sentinel (dummy) node at the head
 *  of the list.
 *
 *  DO NOT CHANGE ANY METHOD PROTOTYPES IN THIS FILE.
 */

public class DList {

  /**
   *  head references the sentinel node.
   *  size is the number of items in the list.  (The sentinel node does not
   *       store an item.)
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected DListNode head;
  protected int size;

  /* DList invariants:
   *  1)  head != null.
   *  2)  For any DListNode x in a DList, x.next != null.
   *  3)  For any DListNode x in a DList, x.prev != null.
   *  4)  For any DListNode x in a DList, if x.next == y, then y.prev == x.
   *  5)  For any DListNode x in a DList, if x.prev == y, then y.next == x.
   *  6)  size is the number of DListNodes, NOT COUNTING the sentinel,
   *      that can be accessed from the sentinel (head) by a sequence of
   *      "next" references.
   */

  /**
   *  newNode() calls the DListNode constructor.  Use this class to allocate
   *  new DListNodes rather than calling the DListNode constructor directly.
   *  That way, only this method needs to be overridden if a subclass of DList
   *  wants to use a different kind of node.
   *  @param item the item to store in the node.
   *  @param prev the node previous to this node.
   *  @param next the node following this node.
   */
  protected DListNode newNode(Object item, DListNode prev, DListNode next) {
    return new DListNode(item, prev, next);
  }

  /**
   *  DList() constructor for an empty DList.
   */
  public DList() {
    //  Your solution here.
	  size = 0;
	  head = newNode(null, null, null);
	  head.next = head;
	  head.prev = head;
  }

  /**
   *  isEmpty() returns true if this DList is empty, false otherwise.
   *  @return true if this DList is empty, false otherwise. 
   *  Performance:  runs in O(1) time.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /** 
   *  length() returns the length of this DList. 
   *  @return the length of this DList.
   *  Performance:  runs in O(1) time.
   */
  public int length() {
    return size;
  }

  /**
   *  insertFront() inserts an item at the front of this DList.
   *  @param item is the item to be inserted.
   *  Performance:  runs in O(1) time.
   */
  public void insertFront(Object item) {
    // Your solution here.
		  if (size == 0){
			 DListNode node = newNode(item, head, head);
			 head.next = node;
			 head.prev = node;
		  }else{
			  DListNode node = newNode(item, head, head.next);
			  head.next = node;
			  head.next.next.prev = node;
		  }
		  size++;
  }

  /**
   *  insertBack() inserts an item at the back of this DList.
   *  @param item is the item to be inserted.
   *  Performance:  runs in O(1) time.
   */
  public void insertBack(Object item) {
    // Your solution here.
	 if (size ==0){
		 DListNode node = newNode(item, head, head);
		 head.prev = node;
		 head.next = node;
	 }else{
		 DListNode node = newNode(item, head.prev, head);
		 head.prev = node;
		 head.prev.prev.next = node;
		 
	 }
	 size++;
  }

  /**
   *  front() returns the node at the front of this DList.  If the DList is
   *  empty, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @return the node at the front of this DList.
   *  Performance:  runs in O(1) time.
   */
  public DListNode front() {
    // Your solution here.
	  if (size == 0){
		  return null;
	  }else{
		  return head.next;
	  }
  }

  /**
   *  back() returns the node at the back of this DList.  If the DList is
   *  empty, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @return the node at the back of this DList.
   *  Performance:  runs in O(1) time.
   */
  public DListNode back() {
    // Your solution here.
	  if (size ==0){
		  return null;
	  }else{
		  return head.prev;
	  }
  }

  /**
   *  next() returns the node following "node" in this DList.  If "node" is
   *  null, or "node" is the last node in this DList, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @param node the node whose successor is sought.
   *  @return the node following "node".
   *  Performance:  runs in O(1) time.
   */
  public DListNode next(DListNode node) {
    // Your solution here.
	  if (node == null){
		  return null;
	  }else if(node.next == head){
		  return null;
	  }else{
		  return node.next;
	  }
  }

  /**
   *  prev() returns the node prior to "node" in this DList.  If "node" is
   *  null, or "node" is the first node in this DList, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @param node the node whose predecessor is sought.
   *  @return the node prior to "node".
   *  Performance:  runs in O(1) time.
   */
  public DListNode prev(DListNode node) {
    // Your solution here.
	  if (node == null){
		  return null;
	  }else if (node == head.next){
		  return null;
	  }else{
		  return node.prev;
	  }
  }

  /**
   *  insertAfter() inserts an item in this DList immediately following "node".
   *  If "node" is null, do nothing.
   *  @param item the item to be inserted.
   *  @param node the node to insert the item after.
   *  Performance:  runs in O(1) time.
   */
  public void insertAfter(Object item, DListNode node) {
    // Your solution here.
	  if (node == null){
		  return;
	  }
	  DListNode node1 = node.next;
	  node.next = newNode(item, node, node1);
	  node1.prev = node.next;
	  size++;
  }

  /**
   *  insertBefore() inserts an item in this DList immediately before "node".
   *  If "node" is null, do nothing.
   *  @param item the item to be inserted.
   *  @param node the node to insert the item before.
   *  Performance:  runs in O(1) time.
   */
  public void insertBefore(Object item, DListNode node) {
    // Your solution here.
	  if (node == null){
		  return;
	  }
	  DListNode node1 = node.prev;
	  node.prev = newNode(item, node1, node);
	  node1.next = node.prev;
	  size++;
  }

  /**
   *  remove() removes "node" from this DList.  If "node" is null, do nothing.
   *  Performance:  runs in O(1) time.
   */
  public void remove(DListNode node) {
    // Your solution here.
	  if (node == null){
		  return;
	  }
	  node.prev.next = node.next;
	  node.next.prev = node.prev;
	  node = null;
	  size--;
  }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   *  Performance:  runs in O(n) time, where n is the length of the list.
   */
  public String toString() {
    String result = "[  ";
    DListNode current = head.next;
    while (current != head) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }
  public static void main(String[] args){
	  System.out.println("Testing Constructor...");
	  DList d = new DList();
	  System.out.println(d.length());
	  System.out.println(d.head.item);
	  
	  System.out.println("Testing insertFront and insertBack...");
	  d.insertFront(3);
	  System.out.println(d.head.next.item);
	  System.out.println(d.head.prev.item);
	  d.insertBack(6);
	  System.out.println(d.head.prev.item);
	  System.out.println(d.head.prev.prev.item);
	  System.out.println(d.head.next.next.item);
	  d.insertFront(2);
	  System.out.println(d.head.next.item);
	  System.out.println(d.head.next.next.item);
	  System.out.println(d.head.prev.prev.prev.item);
	  d.insertBack(7);
	  System.out.println(d.head.prev.item);
	  System.out.println(d.head.prev.prev.item);
	  System.out.println(d.head.next.next.next.next.item);
	  System.out.println(d.length());
	  
	  //Right now the DList should be head->2->3->6->7->head
	  System.out.println("Testing front and back...");
	  System.out.println(d.front().item);
	  System.out.println(d.back().item);
	  
	  System.out.println("Testing next and prev...");
	  System.out.println(d.next(d.head).item);
	  System.out.println(d.prev(d.head.next));
	  System.out.println(d.next(d.head.next.next.next.next));
	  System.out.println(d.next(d.head.next.next).item);
	  System.out.println(d.prev(d.head.prev).item);
	  
	  
	  System.out.println("Testing insertAfter and insertBefore");
	  d.insertAfter(4, d.head.next.next);
	  DListNode node = d.head;
	  while(node.next!= d.head){
		 node = node.next;
		 System.out.print(node.item);
	  }
	  System.out.println("");
	  d.insertBefore(5, d.head.prev.prev);
	  node = d.head;
	  while(node.next!= d.head){
		 node = node.next;
		 System.out.print(node.item);
	  }
	  System.out.println("");
	  d.insertAfter(1, d.head);
	  node = d.head;
	  while(node.next!= d.head){
		 node = node.next;
		 System.out.print(node.item);
	  }
	  System.out.println("");
	  d.insertBefore(8, d.head);
	  node = d.head;
	  while(node.next!= d.head){
		 node = node.next;
		 System.out.print(node.item);
	  }
	  System.out.println("");
	  System.out.println("Testing remove...");
	  d.remove(d.head.next);
	  node = d.head;
	  while(node.next!= d.head){
		 node = node.next;
		 System.out.print(node.item);
	  }
	  System.out.println("");
	  d.remove(d.head.next.next);
	  node = d.head;
	  while(node.next!= d.head){
		 node = node.next;
		 System.out.print(node.item);
	  }
	  System.out.println(""); 
	  System.out.println(d.length());
	  d.remove(d.head.prev.prev);
	  node = d.head;
	  while(node.next!= d.head){
		 node = node.next;
		 System.out.print(node.item);
	  }
	  System.out.println("");
	  d.remove(d.next(d.head.next.next));
	  node = d.head;
	  while(node.next!= d.head){
		 node = node.next;
		 System.out.print(node.item);
	  }
	  System.out.println("");
	  System.out.println(d.length());
  }
}
