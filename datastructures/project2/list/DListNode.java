package list;

import player.ChipCoordinate;
import player.Move;




public class DListNode extends ListNode{
	
	/* x is the x position, y is the y position */
	protected DListNode next;
	protected DListNode prev;
	

	protected DListNode(Move move){
		next = null;
		prev = null;
		this.move = move;
	}
	
	protected DListNode(ChipCoordinate chip){
		next = null;
		prev = null;
		this.chip = chip;
	}
	protected DListNode(int x, int y){
		next = null;
		prev = null;
		coordinate[0] = x;
		coordinate[1] = y;
	}
	protected DListNode(DList list){
		next = null;
		prev = null;
		this.list = list;
	}
	
	public ListNode next(){
		return next;
	}
	public ListNode prev(){
		return prev;
	}
	public Move getMove(){
		return move;
	}
	public boolean hasNext(){
		if (next == null){
			return false;
		}
		return true;
	}
	public boolean nodeHasList(){
		if (list.getSize() == 0){
			return false;
		}
		return true;
	}
	/**
	 * get the x coordinate that is stored in the node
	 * @return the x coordinate of the node
	 */
	public int getCoorx(){
		return coordinate[0];
	}
	/**
	 * get the y coordinate that is stored in the node
	 * @return the y coordinate of the node
	 */
	public int getCoory(){
		return coordinate[1];
	}
	
	public ChipCoordinate getChip(){
		return chip;
	}
	public DList getList(){
		return list;
	}
	
}

