/* Maze.java */

import java.util.*;
import set.*;

/**
 *  The Maze class represents a maze in a rectangular grid.  There is exactly
 *  one path between any two points.
 **/

public class Maze {

  // Horizontal and vertical dimensions of the maze.
  protected int horiz;
  protected int vert;
  // Horizontal and vertical interior walls; each is true if the wall exists.
  protected boolean[][] hWalls;
  protected boolean[][] vWalls;

  // Object for generting random numbers.
  private static Random random;

  // Constants used in depth-first search (which checks for cycles in the
  // maze).
  private static final int STARTHERE = 0;
  private static final int FROMLEFT = 1;
  private static final int FROMRIGHT = 2;
  private static final int FROMABOVE = 3;
  private static final int FROMBELOW = 4;

  /**
   *  Maze() creates a rectangular maze having "horizontalSize" cells in the
   *  horizontal direction, and "verticalSize" cells in the vertical direction.
   *  There is a path between any two cells of the maze.  A disjoint set data
   *  structure is used to ensure that there is only one path between any two
   *  cells.
   **/
  public Maze(int horizontalSize, int verticalSize) {
    int i, j;

    horiz = horizontalSize;
    vert = verticalSize;
    if ((horiz < 1) || (vert < 1) || ((horiz == 1) && (vert == 1))) {
      return;                                    // There are no interior walls
    }

    // Create all of the horizontal interior walls.  Initially, every
    // horizontal wall exists; they will be removed later by the maze
    // generation algorithm.
    if (vert > 1) {
      hWalls = new boolean[horiz][vert - 1];
      for (j = 0; j < vert - 1; j++) {
        for (i = 0; i < horiz; i++) {
          hWalls[i][j] = true;
        }
      }
    }
    // Create all of the vertical interior walls.
    if (horiz > 1) {
      vWalls = new boolean[horiz - 1][vert];
      for (i = 0; i < horiz - 1; i++) {
        for (j = 0; j < vert; j++) {
          vWalls[i][j] = true;
        }
      }
    }



    /**
     * Fill in the rest of this method.  You should go through all the walls of
     * the maze in random order, and remove any wall whose removal will not
     * create a cycle.  Use the implementation of disjoint sets provided in the
     * set package to avoid creating any cycles.
     *
     * Note the method randInt() further below, which generates a random
     * integer.  randInt() generates different numbers every time the program
     * is run, so that you can make lots of different mazes.
     **/
     /*Create a disjoint sets data structure in which each cell of the maze is
    represented as a separate item.  Use the DisjointSets class (described in
    the Lecture 33 notes), which is in the set package we've provided.*/
     DisjointSets mazeCells = new DisjointSets(vert*horiz);
     /*(2)  Order the interior walls of the maze in a random order.
     One way to do this is to create an array in which every wall (horizontal
     and vertical) is represented.  (How you represent each wall is up to
     you.)  Scramble the walls by reodering them into a random permutation.
     Each possible permutation (ordering) of walls should be equally likely.
	 */
	 Wall[] walls = new Wall[ horiz* (vert-1) + vert* (horiz-1) ];
	 int z = 0;
	 for (j = 0; j < vert - 1; j++) {
	        for (i = 0; i < horiz; i++) {
	          walls[z] = new HorizWall(i, j);
	          z++;
	        }
	 }
	    
	 for (i = 0; i < horiz - 1; i++) {
	     for (j = 0; j < vert; j++) {
	         walls[z] = new VertWall(i, j);
	         z++;
	     }
	 }
	 
     // Do the swapping here
	 // This swapping follows the algorithm described from the readme file
	 int w = walls.length;
	 while(w>1){
		 int random = randInt(w);
		 Wall tempWall = walls[w-1];
		 walls[w-1] = walls[random];
		 walls[random] = tempWall;
		 w--;
	 }
	
	 
	 /*
	  * (3)  Visit the walls in the (random) order they appear in in the array.
     For each wall you visit:

        (i)  Determine which cell is on each side of the wall.
       (ii)  Determine whether these two cells are members of the same set
             in the disjoint sets data structure.  If they are, then there is
             already a path between them, so you must leave the wall intact to
             avoid creating a cycle.
      (iii)  If the cells are members of different sets, eliminate the wall
             separating them (thereby creating a path from any cell in one
             set to any cell in the other) by setting the appropriate element
             of hWalls or vWalls to false.  Form the union of the two sets in
             the disjoint sets data structure.*/
	 
	 
	 for(i=0; i<walls.length; i++){
		 Wall tempWall = walls[i];
		 int[] cell1 = new int[2], cell2 = new int[2];
		 cell1[0] = 0;
		 cell1[1] = 0;
		 cell2[0] = 0;
		 cell2[1] = 0;
		 int find1 =0, find2 = 0;
		 if (tempWall.isHorizontal()){
			 cell1[0] = tempWall.i;
			 cell1[1] = tempWall.j;
			 cell2[0] = tempWall.i;
			 cell2[1] = tempWall.j + 1;
			 find1 = mazeCells.find(cell1[1]*horiz+cell1[0]);
			 find2 = mazeCells.find(cell2[1]*horiz+cell2[0]);
			 if (find1!=find2){
				 hWalls[cell1[0]][cell1[1]] = false;
				 
			 }
		 }else if (tempWall.isVertical()){
			 cell1[0] = tempWall.i;
			 cell1[1] = tempWall.j;
			 cell2[0] = tempWall.i + 1;
			 cell2[1] = tempWall.j;
			 find1 = mazeCells.find(cell1[1]*horiz+cell1[0]);
			 find2 = mazeCells.find(cell2[1]*horiz+cell2[0]);
			 if (find1!=find2){
				 vWalls[cell1[0]][cell1[1]] = false;
				
			 }
		 }
		 if (find1!=find2){
			 mazeCells.union(find1, find2);
		 }
		
		 
	 }
	 

  }

  /**
   *  toString() returns a string representation of the maze.
   **/
  public String toString() {
    int i, j;
    String s = "";

    // Print the top exterior wall.
    for (i = 0; i < horiz; i++) {
      s = s + "**";
    }
    s = s + "*\n*";

    // Print the maze interior.
    for (j = 0; j < vert; j++) {
      // Print a row of cells and vertical walls.
      for (i = 0; i < horiz - 1; i++) {
        if (vWalls[i][j]) {
          s = s + " *";
        } else {
          s = s + "  ";
        }
      }
      s = s + " *\n*";
      if (j < vert - 1) {
        // Print a row of horizontal walls and wall corners.
        for (i = 0; i < horiz; i++) {
          if (hWalls[i][j]) {
            s = s + "**";
          } else {
            s = s + " *";
          }
        }
        s = s + "\n*";
      }
    }

    // Print the bottom exterior wall.  (Note that the first asterisk has
    // already been printed.)
    for (i = 0; i < horiz; i++) {
      s = s + "**";
    }
    return s + "\n";
  }

  /**
   * horizontalWall() determines whether the horizontal wall on the bottom
   * edge of cell (x, y) exists.  If the coordinates (x, y) do not correspond
   * to an interior wall, true is returned.
   **/
  public boolean horizontalWall(int x, int y) {
    if ((x < 0) || (y < 0) || (x > horiz - 1) || (y > vert - 2)) {
      return true;
    }
    return hWalls[x][y];
  }

  /**
   * verticalWall() determines whether the vertical wall on the right edge of
   * cell (x, y) exists. If the coordinates (x, y) do not correspond to an
   * interior wall, true is returned.
   **/
  public boolean verticalWall(int x, int y) {
    if ((x < 0) || (y < 0) || (x > horiz - 2) || (y > vert - 1)) {
      return true;
    }
    return vWalls[x][y];
  }

  /**
   * randInt() returns a random integer from 0 to choices - 1.
   **/
  private static int randInt(int choices) {
    if (random == null) {       // Only executed first time randInt() is called
      random = new Random();       // Create a "Random" object with random seed
    }
    int r = random.nextInt() % choices;      // From 1 - choices to choices - 1
    if (r < 0) {
      r = -r;                                          // From 0 to choices - 1
    }
    return r;
  }

  /**
   * diagnose() checks the maze and prints a warning if not every cell can be
   * reached from the upper left corner cell, or if there is a cycle reachable
   * from the upper left cell.
   *
   * DO NOT CHANGE THIS METHOD.  Your code is expected to work with our copy
   * of this method.
   **/
  protected void diagnose() {
    if ((horiz < 1) || (vert < 1) || ((horiz == 1) && (vert == 1))) {
      return;                                    // There are no interior walls
    }

    boolean mazeFine = true;

    // Create an array that indicates whether each cell has been visited during
    // a depth-first traversal.
    boolean[][] cellVisited = new boolean[horiz][vert];
    // Do a depth-first traversal.
    if (depthFirstSearch(0, 0, STARTHERE, cellVisited)) {
      System.out.println("Your maze has a cycle.");
      mazeFine = false;
    }

    // Check to be sure that every cell of the maze was visited.
  outerLoop:
    for (int j = 0; j < vert; j++) {
      for (int i = 0; i < horiz; i++) {
        if (!cellVisited[i][j]) {
          System.out.println("Not every cell in your maze is reachable from " +
                             "every other cell.");
          mazeFine = false;
          break outerLoop;
        }
      }
    }

    if (mazeFine) {
      System.out.println("What a fine maze you've created!");
    }
  }

  /**
   * depthFirstSearch() does a depth-first traversal of the maze, marking each
   * visited cell.  Returns true if a cycle is found.
   *
   * DO NOT CHANGE THIS METHOD.  Your code is expected to work with our copy
   * of this method.
   */
  protected boolean depthFirstSearch(int x, int y, int fromWhere,
                                     boolean[][] cellVisited) {
    boolean cycleDetected = false;
    cellVisited[x][y] = true;

    // Visit the cell to the right?
    if ((fromWhere != FROMRIGHT) && !verticalWall(x, y)) {
      if (cellVisited[x + 1][y]) {
        cycleDetected = true;
      } else {
        cycleDetected = depthFirstSearch(x + 1, y, FROMLEFT, cellVisited) ||
                        cycleDetected;
      }
    }

    // Visit the cell below?
    if ((fromWhere != FROMBELOW) && !horizontalWall(x, y)) {
      if (cellVisited[x][y + 1]) {
        cycleDetected = true;
      } else {
        cycleDetected = depthFirstSearch(x, y + 1, FROMABOVE, cellVisited) ||
                        cycleDetected;
      }
    }

    // Visit the cell to the left?
    if ((fromWhere != FROMLEFT) && !verticalWall(x - 1, y)) {
      if (cellVisited[x - 1][y]) {
        cycleDetected = true;
      } else {
        cycleDetected = depthFirstSearch(x - 1, y, FROMRIGHT, cellVisited) ||
                        cycleDetected;
      }
    }

    // Visit the cell above?
    if ((fromWhere != FROMABOVE) && !horizontalWall(x, y - 1)) {
      if (cellVisited[x][y - 1]) {
        cycleDetected = true;
      } else {
        cycleDetected = depthFirstSearch(x, y - 1, FROMBELOW, cellVisited) ||
                        cycleDetected;
      }
    }

    return cycleDetected;
  }

  /**
   * main() creates a maze of dimensions specified on the command line, prints
   * the maze, and runs the diagnostic method to see if the maze is good.
   */
  public static void main(String[] args) {
    int x = 39;
    int y = 15;

    /**
     *  Read the input parameters.
     */
   
    if (args.length > 0) {
      try {
        x = Integer.parseInt(args[0]);
      }
      catch (NumberFormatException e) {
        System.out.println("First argument to Simulation is not an number.");
      }
    }

    if (args.length > 1) {
      try {
        y = Integer.parseInt(args[1]);
      }
      catch (NumberFormatException e) {
        System.out.println("Second argument to Simulation is not an number.");
      }
    }

    Maze maze = new Maze(x, y);
    System.out.print(maze);
    maze.diagnose();
  }

}
