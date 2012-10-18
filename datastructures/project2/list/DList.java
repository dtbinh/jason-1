//This is a DList modified from Lab4. It contains a head and a tail.
package list;

import player.ChipCoordinate;
import player.Move;


public class DList extends List{

	//Since Lab 4 Uses Protected instead of private and it does not work if I use
	//private to declare the instance variables, so I decide to use protected.
	protected DListNode head;
	protected DListNode tail;
	
	//Every method in DList must be public, this is consulted from Lab4...
	
	//Empty DList
	public DList(){
		head = null;
		tail = null;
		size = 0;
	}
	
	// Inserts a move the End of the List
	public void insertEnd(Move move){
		DListNode node = new DListNode(move);
		if (size == 0){
			head = node;
			tail = node;
		}else{
			tail.next = node;
			node.prev = tail;
			tail = node;
		}
		size++;
	}
	public void insertEnd(DList list){
		DListNode node = new DListNode(list);
		if (size == 0){
			head = node;
			tail = node;
		}else{
			tail.next = node;
			node.prev = tail;
			tail = node;
		}
		size++;
	}
	public void insertEnd(ChipCoordinate chip){
		DListNode node = new DListNode(chip);
		if (size == 0){
			head = node;
			tail = node;
		}else{
			tail.next = node;
			node.prev = tail;
			tail = node;
		}
		size++;
	}
	public void insertEnd(int x, int y){
		DListNode node = new DListNode(x, y);
		if (size == 0){
			head = node;
			tail = node;
		}else{
			tail.next = node;
			node.prev = tail;
			tail = node;
		}
		size++;
	}
	public void removeEnd(){
		if (size ==0){
			return;
		}
		if (size ==1){
			head = null;
			tail = null;
			size--;
			return;
		}
		tail = tail.prev;
		tail.next = null;
		size--;
		return;
	}
	public void removeFront(){
		if (size ==0){
			return;
		}
		if (size ==1){
			head = null;
			tail = null;
			size--;
			return;
		}
		head = head.next;
		head.prev = null;
		size--;
		return;
	}
	public DListNode front(){
		return head;
	}
	


}