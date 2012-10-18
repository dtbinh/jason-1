/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package queue;

/**
 *
 * @author Aniket
 */
public class ListSorts {

  private final static int SORTSIZE = 100000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
    try{
        LinkedQueue qOfQ = new LinkedQueue();
        while(!(q.isEmpty())){
            LinkedQueue nQueue = new LinkedQueue();
            nQueue.enqueue(q.dequeue());
            qOfQ.enqueue(nQueue);
        }
        return qOfQ;
        
    } catch (QueueEmptyException e){
        System.out.println("EMPTY QUEUE!!");
        return new LinkedQueue();
    }
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
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
    try {
        LinkedQueue q3 = new LinkedQueue();
        while(!(q1.isEmpty()) && !(q2.isEmpty())){
            Comparable item1 = (Comparable) q1.front();
            Comparable item2 = (Comparable) q2.front();
            int compare = item1.compareTo(item2);
            
            if(compare < 0){
                //item1 is less than item2
                q3.enqueue(q1.dequeue());
            } else {
                if(compare > 0){
                    q3.enqueue(q2.dequeue());
                } else {
                    //they are the same
                    q3.enqueue(q1.dequeue());
                }
            }
        }
        if(!(q1.isEmpty())){
            q3.append(q1);
        } else {
            q3.append(q2);
        }
        
        return q3;
        
    } catch (QueueEmptyException e){
        System.out.println("EMPTY QUEUE!!");
        return new LinkedQueue();
    }
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
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
    try {
        while(!(qIn.isEmpty())){
            Comparable item = (Comparable) qIn.dequeue();
            int compare = item.compareTo(pivot);
            if(compare < 0){
               qSmall.enqueue(item);                 
            } else {
                if(compare > 0){
                    qLarge.enqueue(item);
                } else {
                    //compare == 0
                    qEquals.enqueue(item);
                }
            }
        }
    } catch (QueueEmptyException e){
        System.out.println("EMPTY QUEUE!!");
    }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    
    try{
     if(q.size() > 1) {
     LinkedQueue qOfQ = makeQueueOfQueues(q);
     while(qOfQ.size() > 1){
        LinkedQueue lQ1 = (LinkedQueue) qOfQ.dequeue();
        LinkedQueue lQ2 = (LinkedQueue) qOfQ.dequeue();
        LinkedQueue mergedQueue = mergeSortedQueues(lQ1, lQ2);
        qOfQ.enqueue(mergedQueue);
     }
     q.append((LinkedQueue) qOfQ.dequeue());
     }
    } catch(QueueEmptyException e){
        System.out.println("EMPTY QUEUE!!");
    }
    
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
         if(q.size() > 1) {
         int ran = 1 + ((int) (q.size() * Math.random()));
         Object item = q.nth(ran);
         LinkedQueue sQ = new LinkedQueue();
         LinkedQueue lQ = new LinkedQueue();
         LinkedQueue eQ = new LinkedQueue();
         partition(q, (Comparable) item, sQ, eQ, lQ);
         quickSort(sQ);
         quickSort(lQ);
         q.append(sQ);
         q.append(eQ);
         q.append(lQ);
         }
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

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());    
    mergeSort(q);
    System.out.println(q.toString());
    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

    //Remove these comments for Part III.
  /*  Timer stopWatch = new Timer();
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
    */
  }

}