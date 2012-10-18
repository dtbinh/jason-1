//This is a DList modified from Lab4. It contains a head and a tail.
public class DList {

	//Since Lab 4 Uses Protected instead of private and it does not work if I use
	//private to declare the instance variables, so I decide to use protected.
	protected DListNode head;
	protected DListNode tail;
	protected int size =0;
	
	
	//Every method in DList must be public, this is consulted from Lab4...
	
	//Empty DList
	public DList(){
		head = null;
		tail = null;
		size = 0;
	}
	
	
	//Constructs a DList with size 1
	public DList(int type, int length, int sharkHunger){
		DListNode node = new DListNode(type, length);
		if (type == Ocean.SHARK){
			node.item[1] = sharkHunger;
		}
		head = node;
		tail = node;
		size = 1;
	}

	
	public int Size(){
		return size;
	}
	public void insertFirst(int type, int length, int sharkHunger){
		DListNode node = new DListNode(type, length);
		if (type == Ocean.SHARK){
			node.item[1] = sharkHunger;
		}
		head = node;
		tail = node;
		size = 1;
	}
	
	
	// Inserts at the End of the List
	public void insertEnd(int type, int length, int sharkHunger){
		DListNode node = new DListNode(type, length);
		if (type == Ocean.SHARK){
			node.item[1] = sharkHunger;
		}
			tail.next = node;
			node.prev = tail;
			tail = node;
		
		size++;
	}
	//Inserts a Node at the next of the current node
	public void insertNext(DListNode node, int type, int length, int sharkHunger){
		DListNode node1 = new DListNode(type, length);
		if (type == Ocean.SHARK){
			node.item[1] = sharkHunger;
		}
		if (node.next == null){//This means the node is tail
			tail.next = node;
			node.prev = tail;
			tail = node;
		}
		else{
			node1.next = node.next;
			node.next.prev = node1;
			node.next = node1;
			node1.prev = node;
		}
		size++;
	}
	//Inserts a Node at the prev of the current Node
	public void insertPrev(DListNode node, int type, int length, int sharkHunger){
		DListNode node1 = new DListNode(type, length);
		if (type == Ocean.SHARK){
			node.item[1] = sharkHunger;
		}
		if (node.prev == null){//This means the node is head
			node.next = head;
			head.prev = node;
			head = node;
		}else{
			node1.prev = node.prev;
			node.prev.next = node1;
			node1.next = node;
			node.prev = node1;
		}
		
		size++;
	}
	//Remove the node at its Position
	public void removeNow(DListNode node){
		if (node.prev == null && node.next == null){ //the node is head and tail
			node = null;
			head = null;
			tail = null;
		}
		else if (node.prev == null){
			head = node.next;
			node = null;
		}else if (node.next == null){
			tail = node.prev;
			node.prev.next = null;
			node = null;
		}else{
			node.prev.next = node.next;
			node.next.prev = node.prev;
			node = null;
		}
		
		size--;
	}
	//Remove the previous Node
	public void removePrev(DListNode node){
		if (node.prev == head){
			head = node;
			node.prev = null;
		}else{
			node.prev.prev.next = node;
			node.prev = node.prev.prev;
		}
		
		size--;
	}
	//Check whether the the current Node is head
	public boolean hasPrev(DListNode node){
		
		if (node.prev == null){
			return false;
		}
		return true;
		
	}
	//Check whether the current Node is tail
	public boolean hasNext(DListNode node){
		
		if (node.next == null){
			return false;
		}
		return true;
	}
	
}
