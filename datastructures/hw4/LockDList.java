package list;

public class LockDList extends DList{

	protected LockDListNode newNode(Object item, DListNode prev, DListNode next) {
	    return new LockDListNode(item, prev, next);
	  }
	
	public LockDList(){
		super();
	}
	
	
	public void lockNode(DListNode node){
		// Lock the node here
		((LockDListNode) node).locked = true;	
	}
	
	//Override the remove method so that the locked node cannot be removed.
	public void remove(DListNode node){
		if (node == null){
			return;
		}else if (((LockDListNode) node).locked){
			//System.out.println("Been through here"); //This is just for testing, please ignore this...
			return;
		}
		super.remove(node);
	}
	public static void main(String[] args){
		 System.out.println("Testing LockDList Constructor...");
		 LockDList ld = new LockDList();
		 System.out.println("Size should be zero: "+ld.length());
		 System.out.println("Head item should be null: " +ld.head.item);
		
		 System.out.println("Testing LockDList methods which inherited from DList...");
		 ld.insertFront("A");
		 ld.insertBack("B");
		 ld.insertAfter("C", ld.back());
		 System.out.print("List should contain ABC: ");
		 LockDListNode locknode = (LockDListNode) ld.head;
		 while((LockDListNode) locknode.next!= (LockDListNode) ld.head){
			 locknode = (LockDListNode) locknode.next;
			 System.out.print(locknode.item);
		 }
		 ld.insertBefore("This should not be here", ld.prev((LockDListNode) ld.head.next));
		 System.out.println("");
		 System.out.print("List should contain ABC: ");
		 locknode = (LockDListNode) ld.head;
		 while((LockDListNode) locknode.next!= (LockDListNode) ld.head){
			 locknode = (LockDListNode) locknode.next;
			 System.out.print(locknode.item);
		 }
		 ld.insertBefore("D", ld.head);
		 System.out.println("");
		 System.out.print("List should contain ABCD: ");
		 locknode = (LockDListNode) ld.head;
		 while((LockDListNode) locknode.next!= (LockDListNode) ld.head){
			 locknode = (LockDListNode) locknode.next;
			 System.out.print(locknode.item);
		 }
		 
		 ld.insertBack("F");
		 ld.insertAfter("G", ld.next(ld.next(ld.next(ld.next(ld.next(ld.head))))));
		 System.out.println("");
		 System.out.print("List should contain ABCDFG: ");
		 locknode = (LockDListNode) ld.head;
		 while((LockDListNode) locknode.next!= (LockDListNode) ld.head){
			 locknode = (LockDListNode) locknode.next;
			 System.out.print(locknode.item);
		 }
		 System.out.println("");
		 System.out.println("List size should be 6: " + ld.length());
		 
		 System.out.println("Testing remove method in LockDList");
		 ld.lockNode(ld.next(ld.next(ld.head)));
		 ld.remove(ld.prev(ld.prev(ld.prev(ld.prev(ld.prev(ld.head))))));
		 System.out.print("List should contain ABCDFG: ");
		 locknode = (LockDListNode) ld.head;
		 while((LockDListNode) locknode.next!= (LockDListNode) ld.head){
			 locknode = (LockDListNode) locknode.next;
			 System.out.print(locknode.item);
		 }
		 ld.remove(ld.next(ld.prev(ld.prev(ld.prev(ld.head)))));
		 System.out.println("");
		 System.out.print("List should contain ABCDG: ");
		 locknode = (LockDListNode) ld.head;
		 while((LockDListNode) locknode.next!= (LockDListNode) ld.head){
			 locknode = (LockDListNode) locknode.next;
			 System.out.print(locknode.item);
		 }
		 ld.remove(ld.back());
		 ld.lockNode(ld.front());
		 ld.lockNode(ld.next(ld.front()));
		 ld.lockNode(ld.next(ld.next(ld.front())));
		 ld.lockNode(ld.back());
		 locknode = (LockDListNode) ld.head;
		 while((LockDListNode) locknode.next!= (LockDListNode) ld.head){
			 locknode = (LockDListNode) locknode.next;
			 ld.remove(locknode);
			 
		 }
		 System.out.println("");
		 System.out.print("List should contain ABCD: ");
		 locknode = (LockDListNode) ld.head;
		 while((LockDListNode) locknode.next!= (LockDListNode) ld.head){
			 locknode = (LockDListNode) locknode.next;
			 System.out.print(locknode.item);
			 
		 }
		 ld.remove(ld.front());
		 ld.remove(ld.back());
		 System.out.println("");
		 System.out.print("List should contain ABCD: ");
		 locknode = (LockDListNode) ld.head;
		 while((LockDListNode) locknode.next!= (LockDListNode) ld.head){
			 locknode = (LockDListNode) locknode.next;
			 System.out.print(locknode.item);
			 
		 }
		 ((LockDListNode) ld.back()).locked = false;
		 ld.remove(ld.back());
		 System.out.println("");
		 System.out.print("List should contain ABC: ");
		 locknode = (LockDListNode) ld.head;
		 while((LockDListNode) locknode.next!= (LockDListNode) ld.head){
			 locknode = (LockDListNode) locknode.next;
			 System.out.print(locknode.item);
			 
		 }
		 System.out.println("\nList size should be 3: "+ld.length());
		 ((LockDListNode) ld.back()).locked = false;
		 ld.remove((LockDListNode) ld.back());
		 System.out.print("List should contain AB: ");
		 locknode = (LockDListNode) ld.head;
		 while((LockDListNode) locknode.next!= (LockDListNode) ld.head){
			 locknode = (LockDListNode) locknode.next;
			 System.out.print(locknode.item);
			 
		 }
		 System.out.println("");
		 ld.remove((LockDListNode) ld.back());
		 System.out.print("List should contain AB: ");
		 locknode = (LockDListNode) ld.head;
		 while((LockDListNode) locknode.next!= (LockDListNode) ld.head){
			 locknode = (LockDListNode) locknode.next;
			 System.out.print(locknode.item);
			 
		 }
		 System.out.println("\nList size should be 2: "+ld.length());
		 System.out.println("All test cases finished for LockDList and DList.");
	}
	
	
}
