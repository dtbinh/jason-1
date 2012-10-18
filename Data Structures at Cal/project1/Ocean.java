/* Ocean.java */

/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having width i and height j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {

  /**
   *  Do not rename these constants.  WARNING:  if you change the numbers, you
   *  will need to recompile Test4.java.  Failure to do so will give you a very
   *  hard-to-find bug.
   */

  public final static int EMPTY = 0;
  public final static int SHARK = 1;
  public final static int FISH = 2;

  /**
   *  Define any variables associated with an Ocean object here.  These
   *  variables MUST be private.
   */
  private int i;
  private int j;
  private int starveTime;
  private int[][][] ocean;
  private int[] wrapping = {0, 0};


  /**
   *  The following methods are required for Part I.
   */

  /**
   *  Ocean() is a constructor that creates an empty ocean having width i and
   *  height j, in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public Ocean(int i, int j, int starveTime) {
    // Your solution here.
	  this.i = i;
	  this.j = j;
	  this.starveTime = starveTime;
	  ocean = new int[i][j][2];
	  for (int temp =0; temp < i; temp++){
		  for (int temp1 = 0; temp1<j; temp1++){
			 ocean[temp][temp1][0] = EMPTY;
			 ocean[temp][temp1][1] = 0; //Used to indicating shark's hunger
		  }
	  }	 
	  
	  
  }

  /**
   *  width() returns the width of an Ocean object.
   *  @return the width of the ocean.
   */

  public int width() {
    // Replace the following line with your solution.
    return this.i;
  }

  /**
   *  height() returns the height of an Ocean object.
   *  @return the height of the ocean.
   */

  public int height() {
    // Replace the following line with your solution.
    return this.j;
  }

  /**
   *  starveTime() returns the number of timesteps sharks survive without food.
   *  @return the number of timesteps sharks survive without food.
   */

  public int starveTime() {
    // Replace the following line with your solution.
    return this.starveTime;
  }

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
	  wrapping(x, y);
	  if (cellContents(wrapping[0], wrapping[1]) == EMPTY){
		  ocean[wrapping[0]][wrapping[1]][0] = FISH;
	  }
  }
  /* 
   * This is a wrapping method used to wrap around the edges
   */
  private void wrapping(int x, int y){
	  //Because i and j is always positive
	  if (x < 0){
		  x = x + i;
	  }
	  if (y < 0){
		  y = y + j;
	  }
	  x = x % i;
	  y = y % j;
	  wrapping[0] = x;
	  wrapping[1] = y;
  }
  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    // Your solution here.
	  wrapping(x, y);
	  addShark(wrapping[0], wrapping[1], this.starveTime);
	  
  }

  /**
   *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
   *  a fish, and SHARK if it contains a shark.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int cellContents(int x, int y) {
    // Replace the following line with your solution.
	wrapping(x, y);
	return ocean[wrapping[0]][wrapping[1]][0];
  }

  /**
   *  timeStep() performs a simulation timestep as described in README.
   *  @return an ocean representing the elapse of one timestep.
   */
  
  //Check if any neighbor is a fish
  private boolean neighborsfish(int x, int y){
	  
	  for (int a = x-1; a<x+2; a++){
		  for (int b = y-1; b<y+2; b++){
			  if (cellContents(a, b)==FISH){
				  return true;
			  }
		  }
	  }
	  return false;
  }
  private boolean AtLeastTwoNeighborsShark(int x, int y){
	  int temp = 0;
	  for (int a = x-1; a<x+2; a++){
		  for (int b = y-1; b<y+2; b++){
			if (cellContents(a, b) == SHARK){
				temp++;
			}
		  }
	  }
	  if (temp >=2){
		  return true;
	  }
	  return false;
  }
  private boolean AtLeastTwoNeigiborsFish(int x, int y){
	  int temp = 0;
	  for (int a = x-1; a<x+2; a++){
		  for (int b = y-1; b<y+2; b++){
			if (cellContents(a, b) == FISH){
				temp++;
			}
		  }
	  }
	  if (temp >=2){
		  return true;
	  }
	  return false;
	  
  }
  private boolean FewerthanTwoFish(int x, int y){
	  int temp = 0;
	  for (int a = x-1; a<x+2; a++){
		  for (int b = y-1; b<y+2; b++){
			  if (cellContents(a, b) == FISH){
					temp++;
				} 
		  }
	  }
	  if (temp < 2){
		  return true;
	  }
	  return false;
  }
  private boolean AtMostOneNeighborShark(int x, int y){
	  int temp = 0;
	  for (int a = x-1; a<x+2; a++){
		  for (int b = y-1; b<y+2; b++){
			  if (cellContents(a, b) == SHARK){
					temp++;
				} 
		  }
	  }
	  if (temp < 2){
		  return true;
	  }
	  return false;
	  
	  
	  
  }
  
  private boolean neighborsShark(int x, int y){
	  for (int a = x-1; a<x+2; a++){
		  for (int b = y-1; b<y+2; b++){
			  if (cellContents(a, b) == SHARK){
				  return true;
				} 
		  }
	  }  
	  return false;
  }
  private boolean EmptyOrFish(int x, int y){
	  for (int a = x-1; a<x+2; a++){
		  for (int b = y-1; b<y+2; b++){
			  if (cellContents(a, b) == SHARK){
					return false;
				} 
		  }
	  }
	  return true;
	  
  }
  //The code here may contain unnecessary code that can be deleted or combined with other codes,
  //but this is so far the best I can do due to time constraints for the project due date.
  public Ocean timeStep() {
    // Replace the following line with your solution.
	Ocean newOcean = new Ocean(this.i, this.j, this.starveTime);
	for (int a = 0; a<this.i; a++){
		for (int b =0; b<this.j; b++){
			newOcean.ocean[a][b][0] = ocean[a][b][0];
			newOcean.ocean[a][b][1] = ocean[a][b][1];
			/*1) If a cell contains a shark, and any of its neighbors is a fish, then the
		shark eats during the timestep, and it remains in the cell at the end of the
		timestep.  (We may have multiple sharks sharing the same fish.  This is fine;
		miraculously, they all get enough to eat.)*/
			if (this.cellContents(a, b) == SHARK && this.neighborsfish(a, b) == true){
				newOcean.ocean[a][b][1] = this.starveTime;
			}
			/*2) If a cell contains a shark, and none of its neighbors is a fish, it gets
hungrier during the timestep.  If this timestep is the (starveTime + 1)th
timestep the shark has gone through without eating, then the shark dies
(disappears).  Otherwise, it remains in the cell.  An example demonstrating
this rule appears below.*/
			if(this.cellContents(a, b) == SHARK && !this.neighborsfish(a, b)){
				newOcean.ocean[a][b][1] = newOcean.ocean[a][b][1] -1;
				if (newOcean.ocean[a][b][1] <0){
					newOcean.ocean[a][b][0] = EMPTY;
					newOcean.ocean[a][b][1] = 0;
				}
				//else SHARK remains in the cell
			}
			/*3) If a cell contains a fish, and all of its neighbors are either empty or are
other fish, then the fish stays where it is.*/
			if(cellContents(a, b) == FISH && this.EmptyOrFish(a, b)){
				//fish stays where it is.
			}
			/*4) If a cell contains a fish, and one of its neighbors is a shark, then the
fish is eaten by a shark, and therefore disappears.*/
			if (cellContents(a, b) == FISH && this.neighborsShark(a, b)){
				newOcean.ocean[a][b][0] = EMPTY;
				//The starveTime is taken in Rule One.
			}
			/*5) If a cell contains a fish, and two or more of its neighbors are sharks, then
a new shark is born in that cell.  Sharks are well-fed at birth; _after_ they
are born, they can survive an additional starveTime timesteps without eating.
(But they will die at the end of starveTime + 1 consecutive timesteps without
eating.)*/
			if (cellContents(a, b) == FISH && AtLeastTwoNeighborsShark(a, b)){
				//A new shark is born!! and set the starveTime
				newOcean.ocean[a][b][0] = SHARK;
				newOcean.ocean[a][b][1] = this.starveTime;
				
			}
			/* 6) If a cell is empty, and fewer than two of its neighbors are fish, then the
cell remains empty.*/
			if (cellContents(a, b) == EMPTY && FewerthanTwoFish(a, b)){
				//The cell remains empty
			}
			/*7) If a cell is empty, at least two of its neighbors are fish, and at most one
of its neighbors is a shark, then a new fish is born in that cell.*/
			if (cellContents(a, b) == EMPTY && AtLeastTwoNeigiborsFish(a, b) && AtMostOneNeighborShark(a, b)){
			newOcean.ocean[a][b][0] = FISH;	
			}
			/* 8) If a cell is empty, at least two of its neighbors are fish, and at least two
of its neighbors are sharks, then a new shark is born in that cell.  (The new 
shark is well-fed at birth, even though it hasn't eaten a fish yet.)*/
			if (cellContents(a, b) == EMPTY && AtLeastTwoNeigiborsFish(a, b) && AtLeastTwoNeighborsShark(a, b)){
				//A newShark is born!! set the starveTime
				newOcean.ocean[a][b][0] = SHARK;
				newOcean.ocean[a][b][1] = this.starveTime;
			}
			//All cases should fall in the 8 cases above, there should not be an exception!
			
	}
		}
	  
    return newOcean;
  }

  /**
   *  The following method is required for Part II.
   */

  /**
   *  addShark() (with three parameters) places a shark in cell (x, y) if the
   *  cell is empty.  The shark's hunger is represented by the third parameter.
   *  If the cell is already occupied, leave the cell as it is.  You will need
   *  this method to help convert run-length encodings to Oceans.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   *  @param feeding is an integer that indicates the shark's hunger.  You may
   *         encode it any way you want; for instance, "feeding" may be the
   *         last timestep the shark was fed, or the amount of time that has
   *         passed since the shark was last fed, or the amount of time left
   *         before the shark will starve.  It's up to you, but be consistent.
   */

  public void addShark(int x, int y, int feeding) {
    // Your solution here.
	  wrapping(x, y);
	  if (cellContents(wrapping[0], wrapping[1]) == EMPTY){
		  ocean[wrapping[0]][wrapping[1]][0] = SHARK;
		  //the amount of time left before the shark will starve.
		  ocean[wrapping[0]][wrapping[1]][1] = feeding;
		  
	  }
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  sharkFeeding() returns an integer that indicates the hunger of the shark
   *  in cell (x, y), using the same "feeding" representation as the parameter
   *  to addShark() described above.  If cell (x, y) does not contain a shark,
   *  then its return value is undefined--that is, anything you want.
   *  Normally, this method should not be called if cell (x, y) does not
   *  contain a shark.  You will need this method to help convert Oceans to
   *  run-length encodings.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int sharkFeeding(int x, int y) {
    // Replace the following line with your solution.
    wrapping(x, y);
    if (cellContents(wrapping[0], wrapping[1])!= SHARK){
    	return 0;
    }
    return ocean[wrapping[0]][wrapping[1]][1];
  }

}
