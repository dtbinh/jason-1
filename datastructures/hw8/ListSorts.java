/* ListSorts.java */

import list.*;

public class ListSorts {

  private final static int SORTSIZE = 10000000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
	LinkedQueue newQueue = new LinkedQueue();
	while(q.size() != 0){
		Object item = new Object();
		try {
			item = q.dequeue();
		} catch (QueueEmptyException e) {
			// cannot happen!
		}
		LinkedQueue tempQueue = new LinkedQueue();
		tempQueue.enqueue(item);
		newQueue.enqueue(tempQueue);
	}
    
	return newQueue;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  @SuppressWarnings({ "unchecked", "rawtypes" })
public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
	LinkedQueue newQueue = new LinkedQueue();
    while(!q1.isEmpty() && !q2.isEmpty()){
    	try {
			if (((Comparable) q1.front()).compareTo((Comparable) q2.front()) > 0){
				newQueue.enqueue(q2.dequeue());
			}else if (((Comparable) q1.front()).compareTo((Comparable) q2.front()) <0){
				newQueue.enqueue(q1.dequeue());
			}else{
				newQueue.enqueue(q1.dequeue());
			}
		} catch (QueueEmptyException e) {
			//Cannot happen here
		}
    }
    if (q1.isEmpty()){
    	while(!q2.isEmpty()){
    		try {
				newQueue.enqueue(q2.dequeue());
			} catch (QueueEmptyException e) {
			}
    	}
    }else if (q2.isEmpty()){
    	while(!q1.isEmpty()){
    		try {
				newQueue.enqueue(q1.dequeue());
			} catch (QueueEmptyException e) {
			}
    	}
    }
    
    return newQueue;
    
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  @SuppressWarnings({ "rawtypes", "unchecked" })
public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    
	  while(true){
		  try {
			  if (qIn.size() == 0){
				  break;
			  }
			  Comparable item = (Comparable) qIn.dequeue();
			  if (pivot.compareTo(item) >0){
				  qSmall.enqueue(item);
			  }else if (pivot.compareTo(item) <0){
				  qLarge.enqueue(item);
			  }else{
				  qEquals.enqueue(item);
			  }
		  } catch (QueueEmptyException e) {
		  }
	 }
	 if (qSmall.size()>0){
		 quickSort(qSmall);
	 }
	 if (qLarge.size()>0){
		 quickSort(qLarge);
	 }
	 
  
	
  }
  

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    // Your solution here.
	  LinkedQueue tempQueue = makeQueueOfQueues(q);
	  while(tempQueue.size()>1){
		  try {
			  LinkedQueue sortedQueues = mergeSortedQueues((LinkedQueue) tempQueue.dequeue(), (LinkedQueue) tempQueue.dequeue());
			  tempQueue.enqueue(sortedQueues);
		  } catch (QueueEmptyException e) {
				  //Cannot happen
		  }
	  }
	  try {
		  q.append((LinkedQueue) tempQueue.dequeue());
	  } catch (QueueEmptyException e) {
		//Cannot happen..
	  }
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  @SuppressWarnings("rawtypes")
public static void quickSort(LinkedQueue q) {
    // Your solution here.
	  if (q.size()==0){
		  return;
	  }
	  int pivot = 1 + (int)(Math.random() * (q.size()));
	  Comparable pivotItem = (Comparable) q.nth(pivot);
	  LinkedQueue qSmall = new LinkedQueue(), qLarge = new LinkedQueue(), qEquals = new LinkedQueue();
	  partition(q, pivotItem, qSmall, qEquals, qLarge);
	  q.append(qSmall);
	  q.append(qEquals);
	  q.append(qLarge);
	  
  }


  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {
	  
	  
	System.out.println("Testing makeQueueOfQueues...");
	LinkedQueue testqueue = makeRandom(10);
	System.out.println(testqueue.toString());
	LinkedQueue testqueue2 = makeQueueOfQueues(testqueue);
	System.out.println(testqueue2.toString());
	testqueue = makeRandom(0);
	System.out.println(testqueue.toString());
	testqueue2 = makeQueueOfQueues(testqueue);
	System.out.println(testqueue2.toString());
	testqueue = makeRandom(1);
	System.out.println(testqueue.toString());
	testqueue2 = makeQueueOfQueues(testqueue);
	System.out.println(testqueue2.toString());
	System.out.println("Testing mergeSortedQueues for one empty and one sorted...");
	LinkedQueue testqueue1 = new LinkedQueue();
	testqueue2 = new LinkedQueue();
	testqueue = mergeSortedQueues(testqueue1, testqueue2);
	System.out.println(testqueue1.toString());
	System.out.println(testqueue2.toString());
	System.out.println(testqueue.toString());
	
	testqueue1.enqueue(new Integer(2));
	testqueue1.enqueue(new Integer(3));
	testqueue1.enqueue(new Integer(5));
	testqueue1.enqueue(new Integer(7));
	System.out.println(testqueue1.toString());
	testqueue = mergeSortedQueues(testqueue1, testqueue2);
	System.out.println(testqueue1.toString());
	System.out.println(testqueue2.toString());
	System.out.println(testqueue.toString());
	
	testqueue2.enqueue(new Integer(2));
	testqueue2.enqueue(new Integer(3));
	testqueue2.enqueue(new Integer(5));
	testqueue2.enqueue(new Integer(7));
	System.out.println(testqueue2.toString());
	testqueue = mergeSortedQueues(testqueue1, testqueue2);
	System.out.println(testqueue1.toString());
	System.out.println(testqueue2.toString());
	System.out.println(testqueue.toString());
	
	System.out.println("Testing mergeSortedQueues for two sorted...");
	testqueue1.enqueue(new Integer(2));
	testqueue1.enqueue(new Integer(3));
	testqueue1.enqueue(new Integer(5));
	testqueue1.enqueue(new Integer(7));
	testqueue2.enqueue(new Integer(2));
	testqueue2.enqueue(new Integer(3));
	testqueue2.enqueue(new Integer(5));
	testqueue2.enqueue(new Integer(7));
	System.out.println(testqueue1.toString());
	System.out.println(testqueue2.toString());
	testqueue = mergeSortedQueues(testqueue1, testqueue2);
	System.out.println(testqueue1.toString());
	System.out.println(testqueue2.toString());
	System.out.println(testqueue.toString());
	System.out.println();
	testqueue1.enqueue(new Integer(2));
	testqueue1.enqueue(new Integer(4));
	testqueue1.enqueue(new Integer(6));
	testqueue1.enqueue(new Integer(8));
	testqueue2.enqueue(new Integer(2));
	testqueue2.enqueue(new Integer(3));
	testqueue2.enqueue(new Integer(5));
	testqueue2.enqueue(new Integer(7));
	System.out.println(testqueue1.toString());
	System.out.println(testqueue2.toString());
	testqueue = mergeSortedQueues(testqueue1, testqueue2);
	System.out.println(testqueue1.toString());
	System.out.println(testqueue2.toString());
	System.out.println(testqueue.toString());
	System.out.println();
	
	testqueue1.enqueue(new Integer(2));
	testqueue1.enqueue(new Integer(9));
	testqueue1.enqueue(new Integer(9));
	testqueue2.enqueue(new Integer(3));
	testqueue2.enqueue(new Integer(3));
	testqueue2.enqueue(new Integer(5));
	testqueue2.enqueue(new Integer(6));
	testqueue2.enqueue(new Integer(8));
	testqueue2.enqueue(new Integer(9));
	System.out.println(testqueue1.toString());
	System.out.println(testqueue2.toString());
	testqueue = mergeSortedQueues(testqueue1, testqueue2);
	System.out.println(testqueue1.toString());
	System.out.println(testqueue2.toString());
	System.out.println(testqueue.toString());
	
	System.out.println("Testing mergeSort...");
    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());
    q = makeRandom(13);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());
    q = makeRandom(17);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());
    q = new LinkedQueue();
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());
    q = makeRandom(1);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());
    q = makeRandom(2);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());
    q = makeRandom(3);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());
    q = makeRandom(1000);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());
    
    System.out.println("Testing quickSort...");
    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());
    q = makeRandom(0);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());
    q = makeRandom(1);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());
    q = makeRandom(3);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());
    q = makeRandom(13);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());
    q = makeRandom(17);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());
    q = makeRandom(1000);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());
    
    
    
    System.out.println("Testing Part III...");
    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
    
  }

}
