import list.DList;
import list.InvalidNodeException;
import list.List;
import list.ListNode;

/* Set.java */


/**
 *  A Set is a collection of Comparable elements stored in sorted order.
 *  Duplicate elements are not permitted in a Set.
 **/
public class Set {
  /* Fill in the data fields here. */
	protected List setList;
  /**
   * Set ADT invariants:
   *  1)  The Set's elements must be precisely the elements of the List.
   *  2)  The List must always contain Comparable elements, and those elements 
   *      must always be sorted in ascending order.
   *  3)  No two elements in the List may be equal according to compareTo().
   **/

  /**
   *  Constructs an empty Set. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public Set() { 
    // Your solution here.
	  setList = new DList();
  }

  /**
   *  cardinality() returns the number of elements in this Set.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int cardinality() {
    // Replace the following line with your solution.
	  return setList.length();
  }

  /**
   *  insert() inserts a Comparable element into this Set.
   *
   *  Sets are maintained in sorted order.  The ordering is specified by the
   *  compareTo() method of the java.lang.Comparable interface.
   *
   *  Performance:  runs in O(this.cardinality()) time.
   **/
  @SuppressWarnings({ "unchecked", "rawtypes" })
public void insert(Comparable c) {
    // Your solution here.
	  if (setList.length() == 0){
			setList.insertFront(c);
		  }
		  else{
			  ListNode setNode = setList.front();
			  int i =1;
			  try{
			  while (i<setList.length()){
				  if (((Comparable<Comparable>) setNode.item()).compareTo(c) ==0){
					  return;
				  }
				  else if (((Comparable<Comparable>) setNode.item()).compareTo(c) >0){
					  setNode.insertBefore(c);
					  return;
				  }else{
					  setNode = setNode.next();
					  i++;
				  }
			  }
			  if (((Comparable<Comparable>) setNode.item()).compareTo(c) ==0){
				  return;
			  }
			  else if (((Comparable<Comparable>) setNode.item()).compareTo(c) >0){
				  setNode.insertBefore(c);
				  return;
			  }else{
				  setNode.insertAfter(c);
				  return;
			  }
			  
			  }catch(InvalidNodeException e){
				  //This doesn't matter
				  //The worst part of Java is that you are required to throw exception even when you don't
				  //expect to ever use it!!!  O(Python) > O(Java) in this case :) Just kidding...
			  }  
		  }
  }

  /**
   *  union() modifies this Set so that it contains all the elements it
   *  started with, plus all the elements of s.  The Set s is NOT modified.
   *  Make sure that duplicate elements are not created.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Your implementation should NOT copy elements of s or "this", though it
   *  will copy _references_ to the elements of s.  Your implementation will
   *  create new _nodes_ for the elements of s that are added to "this", but
   *  you should reuse the nodes that are already part of "this".
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
   **/
  @SuppressWarnings("unchecked")
public void union(Set s) {
    // Your solution here.
	  if (setList.length() == 0){
			 setList = s.setList;
			 return;
		 }
		 if (s.setList.length() ==0){
			 return;
		 }
		 ListNode thisset = setList.front();
		 ListNode otherset = s.setList.front();
		 int thissetSize = setList.length();
		 int othersetSize = s.setList.length();
		 int i = 1, j =1;
		 try{
		 while (i<thissetSize && j<othersetSize){
			 if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) <0){
				thisset = thisset.next();
				i++;
			 }
			 else if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) ==0){
				 otherset = otherset.next();
				 j++;
			 }else{
				 thisset.insertBefore(otherset.item());
				 otherset = otherset.next();
				 j++;
			 }
		 }
		 if (i ==thissetSize){
			 while (j<=othersetSize){
				 if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) >0){
						thisset.insertBefore(otherset.item());
						otherset = otherset.next();
						j++;
					 }
				 else if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) <0){
					 	thisset.insertAfter(otherset.item());
					 	thisset = thisset.next(); //Crucial here!!
						otherset = otherset.next();
						j++;
				 }else{
					 otherset = otherset.next();
					 j++;
				 }
			 }
		 }else{
			 while(i<thissetSize){
				 if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) <0){
					 thisset = thisset.next();
					 i++;
				 }else if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) ==0){
					 return;
				 }else{
					 thisset.insertBefore(otherset.item());
					 return;
				 }
			 }
			 if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) ==0){
				 return;
			 }else if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) <0){
				 thisset.insertAfter(otherset.item());
				 return;
			 }else{
				 thisset.insertBefore(otherset.item());
				 return;
			 } 
		 }
		 }catch(InvalidNodeException e){
			 //This Doesn't matter!
			 //Oops! O(Python)>O(Java) again:) Just kidding.....
		 }
  }

  /**
   *  intersect() modifies this Set so that it contains the intersection of
   *  its own elements and the elements of s.  The Set s is NOT modified.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Do not construct any new ListNodes during the execution of intersect.
   *  Reuse the nodes of "this" that will be in the intersection.
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT CONSTRUCT ANY NEW NODES.
   *  DO NOT ATTEMPT TO COPY ELEMENTS.
   **/
  @SuppressWarnings("unchecked")
public void intersect(Set s) {
    // Your solution here.
	  if (setList.length() ==0){
	  		return;
	  	}
	  	if (s.setList.length() == 0){
	  		ListNode node = setList.front();
	  	try{
	  		while(true){
	  			ListNode node1 = node.next();
	  			node.remove();
				node = node1;
	  		}
	  	} catch (InvalidNodeException e) {
			try {
				node.remove();
			} catch (InvalidNodeException e1) {
				//This doesn't matter
				//Wow!! Look at here, I'm not sure what the heck is wrong with so many exceptions...
				//I'm confused. To James Gosling- I seriouslly don't know how you created this language.
			}
		}
	  	return;
	  	}
	  	ListNode thisset = setList.front();
	  	ListNode otherset = s.setList.front();
	    int thissetSize = setList.length();
	    int othersetSize = s.setList.length();
	    int i =1, j=1;
	    try{
	    while (i<thissetSize && j<othersetSize){
	    	if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) ==0){
	    		thisset = thisset.next();
	    		otherset = otherset.next();
	    		i++;
	    		j++;
	    	}
	    	else if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) <0){
	    		ListNode tempNode = thisset.next();
	    		thisset.remove();
	    		thisset = tempNode;
	    		i++;
	    	}else{
	    		otherset = otherset.next();
	    		j++;
	    	}
	    }
	    if (i == thissetSize){
	    	while (j <= othersetSize){
	    		if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) ==0){
	    			return;
	    		}else if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) >0){
	    			if (j == othersetSize){
	    				thisset.remove();
	    				return;
	    			}
	    			otherset = otherset.next();
	    			j++;
	    		}else{
	    			thisset.remove();
	    			return;
	    		}
	    	}
	    	
	    }else{
	    	while (i<= thissetSize){
	    		if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) ==0){
	    			if (i == thissetSize){
	    				return;
	    			}
	    			thisset = thisset.next();
	    			i++;
	    			
	    		}else if (((Comparable<Object>) thisset.item()).compareTo(otherset.item()) <0){
	    			if (i == thissetSize){
	    			thisset.remove();
	    			return;
	    			}
	    			ListNode tempNode = thisset.next();
	    			thisset.remove();
	    			thisset = tempNode;
	    		}
	    		else{
	    			if (i == thissetSize){
		    			thisset.remove();
		    			return;
		    		}
	    			ListNode tempNode = thisset.next();
	    			thisset.remove();
	    			thisset = tempNode;	
	    		}	
	    	}
	    }
	    
	    }catch(InvalidNodeException e){
	    	//Doesn't matter
	    	//Ouch, the third exception in Set.java
	    	//Java- No, it's the fifth!!!
	    }
  }

  /**
   *  toString() returns a String representation of this Set.  The String must
   *  have the following format:
   *    {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
   *            between them.
   *    {  1  2  3  } for a Set of three Integer elements.  No spaces before
   *            "{" or after "}"; two spaces before and after each element.
   *            Elements are printed with their own toString method, whatever
   *            that may be.  The elements must appear in sorted order, from
   *            lowest to highest according to the compareTo() method.
   *
   *  WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
   *            FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
   *            DEVIATIONS WILL LOSE POINTS.
   **/
  public String toString() {
    // Replace the following line with your solution.
	  if (setList.length() ==0){
		  return "{  }";
	  }
	  String string = "{  ";
	  ListNode node = setList.front();
	  try {
	  for (int i =1; i<=setList.length(); i++){
		  string = string + node.item() + "  ";
		  node = node.next();
	  }
	  }catch (InvalidNodeException e) {
			//Doesn't matter here.
	}
	  return string + "}";
	  
  }

  public static void main(String[] argv) {
    Set s = new Set();
    s.insert(new Integer(3));
    s.insert(new Integer(4));
    s.insert(new Integer(3));
    System.out.println("Set s = " + s);

    Set s2 = new Set();
    s2.insert(new Integer(4));
    s2.insert(new Integer(5));
    s2.insert(new Integer(5));
    System.out.println("Set s2 = " + s2);

    Set s3 = new Set();
    s3.insert(new Integer(5));
    s3.insert(new Integer(3));
    s3.insert(new Integer(8));
    System.out.println("Set s3 = " + s3);

    s.union(s2);
    System.out.println("After s.union(s2), s = " + s);

    s.intersect(s3);
    System.out.println("After s.intersect(s3), s = " + s);

    System.out.println("s.cardinality() = " + s.cardinality());
    // You may want to add more (ungraded) test code here.
    // Not sure if this is enough... 
    // Do you know that Java is an island in Indonesia? I really want to go there and see if people over there
    // know how to program in Java.
    Set s4 = new Set();
    s4.insert(new Integer(1));
    s4.insert(new Integer(2));
    s4.insert(new Integer(1));
    s4.insert(new Integer(1));
    s4.insert(new Integer(2));
    s4.insert(new Integer(1));
    s4.insert(new Integer(3));
    s4.insert(new Integer(9));
    s4.insert(new Integer(5));
    s4.insert(new Integer(8));
    System.out.println("Set s4 = " + s4);
    
    s4.union(s3);
    System.out.println("After s4.union(s3), s4 = " + s4);
    s4.union(s2);
    System.out.println("After s4.union(s2), s4 = " + s4);
    
    Set s5 = new Set();
    s5.insert(new Integer(1));
    s5.insert(new Integer(2));
    s5.insert(new Integer(6));
    s5.insert(new Integer(7));
    System.out.println("Set s5 = " + s5);
    
    Set s6 = new Set();
    s6.insert(new Integer(3));
    s6.insert(new Integer(4));
    s6.insert(new Integer(9));
    s6.insert(new Integer(6));
    System.out.println("Set s6 = " + s6);
    
    s5.union(s6);
    System.out.println("After s5.union(s6), s5 = " + s5);
    
    Set s7 = new Set();
    s7.insert(new Integer(5));
    s7.insert(new Integer(9));
    s7.insert(new Integer(7));
    s7.insert(new Integer(6));
    System.out.println("Set s7 = " + s7);
    s5.union(s7);
    System.out.println("After s5.union(s7), s5 = " + s5);
    s5.union(s3);
    System.out.println("After s5.union(s3), s5 = " + s5);
    s5.union(s2);
    System.out.println("After s5.union(s2), s5 = " + s5);
    System.out.println("Set s2 = " +s2);
    s7.union(s5);
    System.out.println("After s7.union(s5), s7 = " + s7);
    System.out.println("s7.cardinality() = " + s7.cardinality());
    System.out.println("s5.cardinality() = " + s5.cardinality());
    
    s7.intersect(s5);
    System.out.println("After s7.intersect(s5), s7 = " + s7);
    s5.intersect(s2);
    System.out.println("After s5.intersect(s2), s5 = " + s5);
    s5.intersect(s3);
    System.out.println("After s5.intersect(s3), s5 = " + s5);
    
    Set s8 = new Set();
    s8.insert(new Integer(1));
    s8.insert(new Integer(3));
    s8.insert(new Integer(3));
    s8.insert(new Integer(5));
    s8.insert(new Integer(7));
    s8.insert(new Integer(9));
    System.out.println("Set s8 = "+s8);
    
    s8.intersect(s7);
    System.out.println("After s8.intersect(s7), s8 = " + s8);
    s7.intersect(s8);
    System.out.println("After s7.intersect(s8), s7 = " + s7);
    s7.intersect(s6);
    System.out.println("After s7.intersect(s6), s7 = " + s7);
    s7.intersect(s);
    System.out.println("After s7.intersect(s), s7 = " + s7);
    s7.intersect(s5);
    System.out.println("After s7.intersect(s5), s7 = " + s7);
    
    Set s9 = new Set();
    s9.insert(new Integer(5));
    s9.insert(new Integer(11));
    s9.insert(new Integer(6));
    s9.insert(new Integer(9));
    s9.insert(new Integer(8));
    s9.insert(new Integer(7));
    s9.insert(new Integer(4));
    System.out.println("Set s9 = "+ s9);
    
    s8.intersect(s9);
    System.out.println("After s8.intersect(s9), s8 = " + s8);
    System.out.println("Set s9 = "+ s9);
    s9.intersect(s8);
    System.out.println("After s9.intersect(s8), s9 = " + s9);
    
    s9.union(s8);
    System.out.println("After s9.union(s8), s9 = " + s9);
    System.out.println("s9.cardinality() = " + s9.cardinality());
    s9.union(s2);
    System.out.println("After s9.union(s2), s9 = " + s9);
    
    s9.union(s);
    System.out.println("After s9.union(s), s9 = " + s9);
    
    s9.intersect(s5);
    System.out.println("After s9.intersect(s5), s9 = " + s9);
    s9.intersect(s4);
    System.out.println("After s9.intersect(s4), s9 = " + s9);
    s9.intersect(s6);
    System.out.println("After s9.intersect(s6), s9 = " + s9);
    System.out.println("s9.cardinality() = " + s9.cardinality());
    
    Set s10 = new Set();
    System.out.println("s10 = "+s10);
    s10.union(s5);
    System.out.println("After s10.union(s5), s10 = " + s10);
    s10.union(s4);
    System.out.println("s4 = "+s4);
    System.out.println("After s10.union(s4), s10 = " + s10);
    
    System.out.println("s6 = "+s6);
    s10.intersect(s6);
    System.out.println("After s10.intersect(s6), s10 = " + s10);
    
    Set s11 = new Set();
    s11.insert(new Integer(4));
    s11.insert(new Integer(3));
    System.out.println("s11 = "+ s11);
    
    s10.intersect(s11);
    System.out.println("After s10.intersect(s11), s10 = " + s10);
    
    s10.union(s4);
    System.out.println("After s10.union(s4), s10 = " + s10);
    System.out.println("s10.cardinality() = " + s10.cardinality());
    
    s10.intersect(s7);
    System.out.println("After s10.intersect(s7), s10 = " + s10);
    System.out.println("s10.cardinality() = " + s10.cardinality());
    
    System.out.println("\nTest cases ended... Java freezed my machine one time while doing this homework");
  }
}
