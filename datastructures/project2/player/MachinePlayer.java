/* MachinePlayer.java */

package player;

import list.DList;
import list.DListNode;


/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {


    protected int color;
    protected int searchDepth;
    protected Board gameBoard;
    protected final int WIN = 100;
	protected final int LOSE = -100;

  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
      this(color, 2);
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
      this.color = color;
      this.searchDepth = searchDepth;
      gameBoard = new Board();
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
	  Move m = (Move) miniMax(color, searchDepth, LOSE, WIN, gameBoard)[0];
	  forceMove(m);
	  return m;
  } 

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
      return gameBoard.doMove(m, (color + 1) %2);
  }
  
  
  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
      return gameBoard.doMove(m, color);
  }
  
  /**
   * minimax returns the best move after searching to depth
   * @param color is the color of the MachinePlayer
   * @param depth is the depth of search
   * @param alpha is used to determine LOSE rate (used for alphabeta pruning)
   * @param beta is used to determine WIN rate (used for alphabeta pruning)
   * @param b the current gameboard
   * @return the best move after searching to depth
   */
  private Object[] miniMax(int color, int depth, int alpha, int beta, Board b) {
	  DList moves = b.validMovesList(color);
	  Object[] ret = new Object[2];
	  if (b.hasWinningNetwork(color)) {
		  ret[0] = new Move();
		  ret[1] = WIN;
		  return ret;
	  }
	  if (b.hasWinningNetwork((color + 1) %2))
	  {
		  ret[0] = new Move();
		  ret[1] = LOSE;
		  return ret;
	  }
	  if (depth == 0) {
		  ret[1] = (evaluate(b, color) < -1 * evaluate(b, ((color + 1) %2))) ? evaluate(b, color) : -1 * evaluate(b, (color + 1)%2);
		  return ret;
	  }
	  ret[0] = moves.front().getMove();
	  ret[1] = alpha;
	  for (DListNode i = moves.front(); i != null && beta > alpha; i = (DListNode) i.next()){
		Move current = i.getMove();		
		Board temp = b.clone();
		temp.doMove(current, color);
		int bestScore =  -1 * (Integer)miniMax((color + 1)%2, depth - 1, -1 * beta, -1 * alpha, temp)[1];
		if (alpha < bestScore){
			alpha = bestScore;
			ret[0] = current;
			ret[1] = alpha;
		}

	  }
	  return ret;
  }
  /**
   * evaluate function evaluates the board to give a score of the move.
   * @param b is the temporary board
   * @param color is the color of the chips
   * @return the values of the move.
   */
  private static int evaluate(Board b, int color) {
	  int aa=0, bb=0;
	  if (b.numChipsinUpperorLeftGoalArea(color) ==0){
		  return -20;
	  }
	  if (b.numChipsinLowerorRightGoalArea(color)==0){
		  return -20;
	  }
	  if (b.collectAllNetworks((color+1)%2).getSize() ==0){
		  for (int i=0; i<8; i++){
			  for(int j=0; j<8; j++){
				  if (b.chipsOnBoard(color, i, j)){
					  aa += b.chipsConnection(color, i, j).getSize();
					  if (b.isGoalArea(new ChipCoordinate(i, j))){
						  bb += 2*b.chipsConnection((color), i, j).getSize() + b.chipsConnection((color), i, j).getSize()/2;
					  }
				  }
				  if (b.chipsOnBoard((color+1)%2, i, j)){
					  bb += b.chipsConnection((color+1)%2, i, j).getSize();
					  if (b.isGoalArea(new ChipCoordinate(i, j))){
						  bb += 2*b.chipsConnection((color+1)%2, i, j).getSize();
					  }
				  }
				  
			  }
		  }
		  return aa-bb;
	  }
	  return b.collectAllNetworks(color).getSize()  -b.collectAllNetworks((color+1)%2).getSize();
	 
  }


  public static void main(String[] args) {
	  MachinePlayer a = new MachinePlayer(0);
	  Board b = a.gameBoard;
	  b.doMove(new Move(6,0), 0);
	  b.doMove(new Move(6,5), 0);
	  b.doMove(new Move(5,5), 0);
	  b.doMove(new Move(3,3), 0);
	  b.doMove(new Move(3,5), 0);
	  b.doMove(new Move(1,7), 0);
	  System.out.println(b.hasWinningNetwork(0));
	  for (DListNode i = b.validMovesList(0).front(); i != null; i = (DListNode) i.next())
	  {
		  System.out.println(i.getMove());
	  }
  }

  
  
}