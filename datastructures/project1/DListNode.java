
public class DListNode {
	//item[0] returns type, item[1] returns sharkHunger, item[2] returns length
	protected int[] item = new int[3];
	protected DListNode next;
	protected DListNode prev;
	// This is a node with TYPE and sharkHunger value. 
	DListNode(int type, int length){
		next = null;
		prev = null;
		item[0] = type;
		item[1] = 0;
		item[2] = length;
	}
}
