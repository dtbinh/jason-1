/* RunLengthEncoding.java */
//Thanks for all the TAs and students in Piazza/Office Hours, you guys really helped me a lot in finishing 
//this project! Suggestions: If possible, make the readme file more clear with more explanations and examples.
//Also, give some hint for PartIV if possible since it really took me a long time to figure out. (Without asking
//for some clues, I won't be able to figure out PartIV at all). Make a better auto-grader if you can too!
/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
	private int i;
	private int j;
	private int starveTime;
	private DList runsObjects;
	private DListNode Position;
	private boolean first = true;
	
	
	
	

  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with three parameters) is a constructor that creates
   *  a run-length encoding of an empty ocean having width i and height j,
   *  in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public RunLengthEncoding(int i, int j, int starveTime) {
    // Your solution here.
	  this.i = i;
	  this.j = j;
	  this.starveTime = starveTime;
	  runsObjects = new DList(Ocean.EMPTY, i*j, starveTime);//See DList implementation for more details
	  Position = runsObjects.head;
	  first = true;
	  
  }

  /**
   *  RunLengthEncoding() (with five parameters) is a constructor that creates
   *  a run-length encoding of an ocean having width i and height j, in which
   *  sharks starve after starveTime timesteps.  The runs of the run-length
   *  encoding are taken from two input arrays.  Run i has length runLengths[i]
   *  and species runTypes[i].
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   *  @param runTypes is an array that represents the species represented by
   *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
   *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
   *         sharks (which are equivalent to sharks that have just eaten).
   *  @param runLengths is an array that represents the length of each run.
   *         The sum of all elements of the runLengths array should be i * j.
   */

  public RunLengthEncoding(int i, int j, int starveTime, int[] runTypes, int[] runLengths) {
    // Your solution here.
	  this.i = i;
	  this.j = j;
	  this.starveTime = starveTime;
	  runsObjects = new DList(runTypes[0], runLengths[0], starveTime);//See DList implementation for more details
	  for (int a = 1; a<runTypes.length; a++){
		  runsObjects.insertEnd(runTypes[a], runLengths[a], starveTime);//Inserts at the END of the DList
		  
	  }
	  Position = runsObjects.head;
	  first = true;
  }

  /**
   *  restartRuns() and nextRun() are two methods that work together to return
   *  all the runs in the run-length encoding, one by one.  Each time
   *  nextRun() is invoked, it returns a different run (represented as a
   *  TypeAndSize object), until every run has been returned.  The first time
   *  nextRun() is invoked, it returns the first run in the encoding, which
   *  contains cell (0, 0).  After every run has been returned, nextRun()
   *  returns null, which lets the calling program know that there are no more
   *  runs in the encoding.
   *
   *  The restartRuns() method resets the enumeration, so that nextRun() will
   *  once again enumerate all the runs as if nextRun() were being invoked for
   *  the first time.
   *
   *  (Note:  Don't worry about what might happen if nextRun() is interleaved
   *  with addFish() or addShark(); it won't happen.)
   */

  /**
   *  restartRuns() resets the enumeration as described above, so that
   *  nextRun() will enumerate all the runs from the beginning.
   */

  public void restartRuns(){
    // Your solution here.
	  Position = runsObjects.head;
	  first = true;
  }

  /**
   *  nextRun() returns the next run in the enumeration, as described above.
   *  If the runs have been exhausted, it returns null.  The return value is
   *  a TypeAndSize object, which is nothing more than a way to return two
   *  integers at once.
   *  @return the next run in the enumeration, represented by a TypeAndSize
   *          object.
   */
  
  public TypeAndSize nextRun() {
    // Replace the following line with your solution.
	if (runsObjects.Size() == 0){
		return null;
	}
	if (Position == runsObjects.head && first == true){
		first = false;
		return new TypeAndSize(Position.item[0], Position.item[2]);
		
	}
	//Found bug!, Without this, the auto-grader won't pass since Position is initialized to null
	if (Position == null){
		Position = runsObjects.head;
		first = false;
		return new TypeAndSize(Position.item[0], Position.item[2]);
	}
	if (Position.next == null){
		return null;
	}
	Position = Position.next;
	return new TypeAndSize(Position.item[0], Position.item[2]);
  }
  
  /**
   *  toOcean() converts a run-length encoding of an ocean into an Ocean
   *  object.  You will need to implement the three-parameter addShark method
   *  in the Ocean class for this method's use.
   *  @return the Ocean represented by a run-length encoding.
   */

  public Ocean toOcean() {
	//The list should not be empty, it should at least contain something inside if you have ran a RunLengthEncoding
		//constructor.
		if (runsObjects.Size() == 0){
			System.out.println("Error! Nothing is inside the list!");
			System.exit(0);
		}
    // Replace the following line with your solution.
	Ocean newOcean = new Ocean(this.i, this.j, this.starveTime);
	int position =0;
	int acount = 0;
	DListNode node = runsObjects.head;
	boolean firstnode = true;
	//Here, I make sure that I run the runsObjects.head
		while (node.next != null){
			if (firstnode){
				firstnode = false;
			}else{
				node = node.next;
			}
		//currentNode.item[2] is the length of currentNode. e.g. {SHARK, 3 , 5} means there are 5 sharks with
		//sharkHunger value 3.
			for (int a = 0; a< node.item[2]; a++){
				if (node.item[0] == Ocean.SHARK){
					newOcean.addShark((acount+position)%newOcean.width(), (acount+position)/newOcean.width(), node.item[1]);
				}
				else if (node.item[0] == Ocean.FISH){
					newOcean.addFish((acount+position)%newOcean.width(), (acount+position)/newOcean.width());
				}else if (node.item[0] == Ocean.EMPTY){
					//we do not need to care if currentNode.item[0] is empty since it is being taken care when we constructs
					//a newOcean.
				}
				acount++;
			}
			position += acount;
			acount =0;
		}	
	  
	  
    return newOcean;
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of an input Ocean.  You will need to implement
   *  the sharkFeeding method in the Ocean class for this constructor's use.
   *  @param sea is the ocean to encode.
   */
  //This method is to change a node's length. In here, I also used recursion concept.
  private void changeNodeLength(DListNode node, int nodeLength){
	  if (node.next == null){
		  node.item[2] = nodeLength;
		  return;
	  }
	  node = node.next;
	  
	  changeNodeLength(node, nodeLength);
  }
  public RunLengthEncoding(Ocean sea) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
	if (sea.width() ==0 || sea.height() == 0){
		System.out.println("Error!, there is no sea!");
		System.exit(0);
		}
	this.i = sea.width();
	this.j = sea.height();
	this.starveTime = sea.starveTime();
	int nodeLength =1;
	runsObjects = new DList();//Create an empty list first
	int type = 0, type1 =0, sharkHunger =0, sharkHunger1 =0;
	
	//Walk through each cell contents in Ocean
	for (int a =0; a<this.i*this.j; a++){
		if (a!= 0){
		//type1 stores the previous type and sharkHunger1 stores the previous sharkHunger value 
		type1 = type;
		sharkHunger1 = sharkHunger;
		type =  sea.cellContents(a%sea.width(), a/sea.width());
		sharkHunger = sea.sharkFeeding(a%sea.width(), a/sea.width());
		}
		//Insert a node first
		if (a == 0){
			type =  sea.cellContents(a%sea.width(), a/sea.width());
			sharkHunger = sea.sharkFeeding(a%sea.width(), a/sea.width());
			runsObjects.insertFirst(type, nodeLength, sharkHunger);
		}else{
			if (type == type1 && sharkHunger == sharkHunger1){
				nodeLength++;
			}else{
				changeNodeLength(runsObjects.head, nodeLength);
				nodeLength = 1;
				runsObjects.insertEnd(type, nodeLength, sharkHunger);
			}
		}
		if (a == this.i * this.j -1 && type == type1 && sharkHunger == sharkHunger1){
			changeNodeLength(runsObjects.head, nodeLength);
		}
	}
	
	
    check();
  }

  /**
   *  The following methods are required for Part IV.
   */

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.  The final run-length
   *  encoding should be compressed as much as possible; there should not be
   *  two consecutive runs of sharks with the same degree of hunger.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
	DListNode node = runsObjects.head;
	int xitemCoor = x%i;
	int yitemCoor = y%i;
	int itemCoor = x+y*i +1;
	int accessCoor = 0;
	int insideNodePosition = 0;
	//Used to find the currNode and the position inside the Node.
	while (accessCoor != itemCoor){
		insideNodePosition = 0;
		int tempvar = node.item[2];
		while (tempvar>0){
			if (accessCoor == itemCoor){
				break;
			}
			insideNodePosition++;
			accessCoor++;
			tempvar--;
			
		}
		if (accessCoor == itemCoor){
			break;
		}
		node = node.next;
	}
	if (node.item[0]!= Ocean.EMPTY){//If the Item is not empty, leave the cell as it is.
		return;
	}
	if (node.item[2] == 1){//If the Node Length is 1
		if (runsObjects.hasPrev(node) && runsObjects.hasNext(node)){
			if (node.prev.item[0] == Ocean.FISH && node.next.item[0] == Ocean.FISH){
				int templength = node.prev.item[2];
				runsObjects.removePrev(node);
				node.next.item[2] = node.next.item[2]+ templength + 1;
				runsObjects.removeNow(node);
			}
			else if (node.prev.item[0] == Ocean.FISH){
				node.prev.item[2]++;
				runsObjects.removeNow(node);
			}else if (node.next.item[0] == Ocean.FISH){
				node.next.item[2]++;
				runsObjects.removeNow(node);
			}else{
				node.item[0] = Ocean.FISH;
			}
		}else if (runsObjects.hasPrev(node)){
			if (node.prev.item[0] == Ocean.FISH){
				node.prev.item[2]++;
				runsObjects.removeNow(node);
			}else{
				node.item[0] = Ocean.FISH;
			}
		}else{
			if (node.next.item[0] == Ocean.FISH){
				node.next.item[2]++;
				runsObjects.removeNow(node);
			}else{
				node.item[0] = Ocean.FISH;
			}
			
		}
	}else{
		if (insideNodePosition == 1){ //The it is at the first node position
			node.item[2]--;
			if (runsObjects.hasPrev(node)&& node.prev.item[0] == Ocean.FISH){
				node.prev.item[2]++;
			}else{
				runsObjects.insertPrev(node, Ocean.FISH, 1, 0);
			}
		}else if (insideNodePosition == node.item[2]){ //If it is at the last node position
			node.item[2]--;
			if (runsObjects.hasNext(node)&& node.next.item[0] == Ocean.FISH){
				node.next.item[2]++;
			}else{
				runsObjects.insertNext(node, Ocean.FISH, 1, 0);
			}
		}else{
			int lengthtemp = node.item[2];
			runsObjects.insertPrev(node, Ocean.EMPTY, insideNodePosition-1, 0);
			runsObjects.insertNext(node, Ocean.EMPTY, lengthtemp-insideNodePosition, 0);
			node.item[0] = Ocean.FISH;
			node.item[2] = 1;
		}
	}
    check();
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  The final run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs of sharks with the same degree
   *  of hunger.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
	  DListNode node = runsObjects.head;
		int xitemCoor = x%i;
		int yitemCoor = y%i;
		int itemCoor = x+y*i +1;
		int accessCoor = 0;
		int insideNodePosition = 0;
	
		//Used to find the Node and the position inside the Node.
		
		while (accessCoor != itemCoor){
			insideNodePosition = 0;
			int tempvar = node.item[2];
			while (tempvar>0){
				if (accessCoor == itemCoor){
					break;
				}
				insideNodePosition++;
				accessCoor++;
				tempvar--;
				
			}
			if (accessCoor == itemCoor){
				break;
			}
			node = node.next;
		}
		if (node.item[0]!= Ocean.EMPTY){//If the Item is not empty, leave the cell as it is.
			return;
		}
		
		if (node.item[2] == 1){//If the Node Length is 1
			if (runsObjects.hasPrev(node) && runsObjects.hasNext(node)){
				if (node.prev.item[0] == Ocean.SHARK && node.prev.item[1] == this.starveTime  && node.next.item[0] == Ocean.SHARK && node.next.item[1] == this.starveTime){
					int templength = node.prev.item[2];
					runsObjects.removePrev(node);
					node.next.item[2] = node.next.item[2]+ templength + 1;
					runsObjects.removeNow(node);
				}
				else if (node.prev.item[0] == Ocean.SHARK && node.prev.item[1] == this.starveTime){
					node.prev.item[2]++;
					runsObjects.removeNow(node);
				}else if (node.next.item[0] == Ocean.SHARK && node.next.item[1] == this.starveTime){
					node.next.item[2]++;
					runsObjects.removeNow(node);
				}else{
					node.item[0] = Ocean.SHARK;
					node.item[1] = this.starveTime;
					node.item[2] = 1;
				}
			}else if (runsObjects.hasPrev(node)){
				if (node.prev.item[0] == Ocean.SHARK && node.prev.item[1] == this.starveTime){
					node.prev.item[2]++;
					runsObjects.removeNow(node);
				}else{
					node.item[0] = Ocean.SHARK;
					node.item[1] = this.starveTime;
					node.item[2] = 1;
				}
			}else{
				if (node.next.item[0] == Ocean.SHARK && node.next.item[1] == this.starveTime){
					node.next.item[2]++;
					runsObjects.removeNow(node);
				}else{
					node.item[0] = Ocean.SHARK;
					node.item[1] = this.starveTime;
					node.item[2] = 1;
				}
				
			}
		}else{
			if (insideNodePosition == 1){ //The it is at the first node position
				node.item[2]--;
				if (runsObjects.hasPrev(node)&& node.prev.item[0] == Ocean.SHARK && node.prev.item[1] == this.starveTime){
					node.prev.item[2]++;
				}else{
					runsObjects.insertPrev(node, Ocean.SHARK, 1, this.starveTime);
				}
			}else if (insideNodePosition == node.item[2]){ //If it is at the last node position
				node.item[2]--;
				if (runsObjects.hasNext(node)&& node.next.item[0] == Ocean.SHARK && node.next.item[1] == this.starveTime){
					node.next.item[2]++;
				}else{
					runsObjects.insertNext(node, Ocean.SHARK, 1, this.starveTime);
				}
			}else{
				int lengthtemp = node.item[2];
				runsObjects.insertPrev(node, Ocean.EMPTY, insideNodePosition-1, 0);
				runsObjects.insertNext(node, Ocean.EMPTY, lengthtemp-insideNodePosition, 0);
				node.item[0] = Ocean.SHARK;
				node.item[1] = this.starveTime;
				node.item[2] = 1;
			}
		}
    check();
  }
  

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same contents, or if the sum of all run
   *  lengths does not equal the number of cells in the ocean.
   */

  public void check() {
	  	DListNode Position = runsObjects.head;
		int type = Position.item[0];
		int sharkHunger = Position.item[1];
		int type1, sharkHunger1;
		int sumLength = Position.item[2];
		while (Position.next!= null){
			type1 = type;
			sharkHunger1 = sharkHunger;
			Position = Position.next;
			type = Position.item[0];
			sharkHunger = Position.item[1];
			sumLength = sumLength+ Position.item[2];
			if (type == type1 && sharkHunger == sharkHunger1){
				System.out.println("Error!, Two consecutive runs have the same contents!");
				System.exit(0);
			}
		}
		
		if (sumLength != this.i*this.j){
			System.out.println("Error!, The sum of all run Lengths does not equal to the number of cells in the ocean.");
			System.out.println("The sumLength is : "+sumLength);
			System.out.println("Number of cells in the Ocean is : "+this.i*this.j);
			System.exit(0);
		}
	
  }
  
  
}
