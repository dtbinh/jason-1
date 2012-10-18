package list;

public class LockDListNode extends DListNode{

	//Nodes are not locked when they are first created.
	protected boolean locked = false;
	LockDListNode(Object i, DListNode p, DListNode n) {
		super(i, p, n);
		locked = false;
		// TODO Auto-generated constructor stub
	}
	
	
	
}
