/*
 * Board class represents the gameBoard composed of black and white chips with INVALID areas
 */
package player;

import list.DList;
import list.DListNode;


public class Board {
	// Board Instance Variables
	private int[][] gameBoard = new int[8][8]; //Initialize an empty 8*8 gameBoard
	private int blackChips = 0;
	private int whiteChips = 0;
	
	public static final int EMPTY = 9;
	//Chips color
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	
	//INVALID means no chips can be placed into this cell
	public static final int INVALID = 5;
	
	
	private ChipCoordinate chip = new ChipCoordinate();
	
	private DList tempList = new DList();
	private DList networks = new DList();
	/* 
	 * Board constructor constructs an empty board with BLACKGOAL, WHITEGOAL, EMPTY, and INVALID
	 */
	public Board(){
		
		for (int i = 0; i<8; i++){
			for(int j=0; j<8; j++){
				this.gameBoard[i][j] = EMPTY;
			}
		}
		/* INVALID means the cell is INVALID for any given chips */
		gameBoard[0][0] = INVALID;
		gameBoard[7][0] = INVALID;
		gameBoard[0][7] = INVALID;
		gameBoard[7][7] = INVALID;
		
		
	}
	/**
	 * validMoves is used to determine whether the move is valid
	 * @param m is the type of move and the coordinates
	 * @param color is the color of the chip
	 * @return true if the move is valid, false otherwise
	 */
	private boolean validMoves(Move m, int color){
		if (m.moveKind == Move.ADD){
			return addHelper(m, color);
		}else if (m.moveKind == Move.STEP){
			if (gameBoard[m.x2][m.y2] != color){
				return false;
			}
			if (color == BLACK){
				if (this.blackChips <10){
					return false;
				}
			}else if (color == WHITE){
				if (this.whiteChips <10){
					return false;
				}
			}
			if (m.x1 == m.x2 && m.y1 == m.y2){
				return false;
			}
			this.gameBoard[m.x2][m.y2] = EMPTY;
			if (color == BLACK){
				this.blackChips--;
			}
			else if (color == WHITE){
				this.whiteChips--;
			}
			if (!addHelper(m, color)){
				this.gameBoard[m.x2][m.y2] = color;
				if (color == BLACK){
					this.blackChips++;
				}
				else if (color == WHITE){
					this.whiteChips++;
				}
				return false;
			}else{
				this.gameBoard[m.x2][m.y2] = color;
				if (color == BLACK){
					this.blackChips++;
				}
				else if (color == WHITE){
					this.whiteChips++;
				}
				return true;
			}
		}
		return true;
	}
	
	/**
	 * doMove performs the specified move given the color and move. If it already has a winning network, should return 
	 * false (no more moves!).
	 * @param m is the move given
	 * @param color is the color given
	 * @return true if the move is being performed, false otherwise.
	 */
	public boolean doMove(Move m, int color){
		if (m == null){
			return false;
		}
		if (this.hasWinningNetwork(color) || this.hasWinningNetwork((color+1)%2)){
			return false;
		}
		if (!validMoves(m, color)){
			return false;
		}
		if (m.moveKind == Move.ADD){
			gameBoard[m.x1][m.y1] = color;
			if (color == BLACK){
				blackChips++;
			}
			else{
				whiteChips++;
			}
		}
		else if (m.moveKind == Move.STEP){
			gameBoard[m.x1][m.y1] = color;
			gameBoard[m.x2][m.y2] = EMPTY;
		}
		return true;
	}
	
	
	/**
	 * addHelper is a helper function for validMoves.
	 * it is used for checking whether the add is valid
	 * @param m is the type of move and the coordinates
	 * @param color is the color of the chip
	 * @return true if it is okay to add, false otherwise
	 */
	private boolean addHelper(Move m, int color){
		
		if (color == BLACK && blackChips == 10){
			return false;
		}
		else if (color == WHITE && whiteChips == 10){
			return false;
		}
		/* 
		 * 1)  No chip may be placed in any of the four corners. 
		 * 3)  No chip may be placed in a square that is already occupied.
		 */
		else if (getCell(m.x1, m.y1) != EMPTY){
			return false;
		}
		/*
		 * 2)  No chip may be placed in a goal of the opposite color.
		 */
		else if (color == BLACK && (m.x1 == 0 || m.x1 == 7 )){
			return false;
		}
		else if (color == WHITE && (m.y1 == 0 || m.y1 == 7)){
			return false;
		}
		// when the chip is placed in black upper goal area
		else if (color == BLACK && m.y1 == 0){
			if (!blackUpper(m)){
				return false;
			}
		}
		// when the chip is placed in black lower goal area
		else if (color == BLACK && m.y1 == 7){
			if (!blackLower(m)){
				return false;
			}
		}
		// when the chip is placed in white left goal area
		else if (color == WHITE && m.x1 == 0){
			if (!whiteLeft(m)){
				return false;
			}
		}
		// when the chip is placed in white right goal area
		else if (color == WHITE && m.x1 == 7){
			if (!whiteRight(m)){
				return false;
			}
		}
		else if (color == BLACK){
			if (!blackGeneral(m)){
				return false;
			}
		}
		else if (color == WHITE){
			if (!whiteGeneral(m)){
				return false;
			}
		}
		return true;
		
	}
	
	/**
	 * blackGeneral is a helper function which calls blackLowerGoal, blackUpperGoal, and blackGeneralGoal to 
	 * determine the whether the general move for black is valid or not.
	 * @param m is the move being passed in
	 * @return true if the move is valid, false otherwise
	 */
	private boolean blackGeneral(Move m){
		if (blackGeneralGoal(m.x1, m.y1) >1){
			return false;
		}
		else if (blackGeneralGoal(m.x1, m.y1) ==1){
			int b[] = blackGeneralGoalOne(m.x1, m.y1);
			if (b[1] == 7){
				if (blackLowerGoal(b[0], b[1]) >0){
					return false;
				}
			}else if (b[1] == 0){
				if (blackUpperGoal(b[0], b[1]) >0){
					return false;
				}
			}
			else{
				if (blackGeneralGoal(b[0], b[1]) >0){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * whiteGeneral is a helper function which calls whiteLeftGoal, whiteRightGoal, and whiteGeneralGoal to 
	 * determine the whether the general move for white is valid or not.
	 * @param m is the move being passed in
	 * @return true if the move is valid, false otherwise
	 */
	private boolean whiteGeneral(Move m){
		if (whiteGeneralGoal(m.x1, m.y1) >1){
			return false;
		}
		else if (whiteGeneralGoal(m.x1, m.y1) ==1){
			int b[] = whiteGeneralGoalOne(m.x1, m.y1);
			if (b[0] == 7){
				if (whiteRightGoal(b[0], b[1]) >0){
					return false;
				}
			}else if (b[0] == 0){
				if (whiteLeftGoal(b[0], b[1]) >0){
					return false;
				}
			}
			else{
				if (whiteGeneralGoal(b[0], b[1]) >0){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * whiteGeneralGoalOne is a helper function which checks if any of its neighbors are white, then it stores the value of the neighbor that has 
	 * white color.
	 * @param x is the x-coordinate of the value that needs to be checked
	 * @param y is the y-coordinate of the value that needs to be checked
	 * @return the array of two values if there's neighbor with white chip
	 */
	private int[] whiteGeneralGoalOne(int x, int y){
		int temp[] = new int[2];
		for (int i = x-1; i< x+2 ; i++){
			for (int j = y-1; j< y+2 ; j++){
				if (!(i == x && j ==y)){
					if (this.gameBoard[i][j] == WHITE){
						temp[0] = i;
						temp[1] = j;
					}
				}
			}
		}
		return temp;
	}
	
	/**
	 * blackGeneralGoalOne is a helper function which checks if any of its neighbors are black, then it stores the value of the neighbor that has 
	 * black color.
	 * @param x  x is the x-coordinate of the value that needs to be checked
	 * @param y  y is the x-coordinate of the value that needs to be checked
	 * @return the array of two values if there's neighbor with black chip
	 */
	private int[] blackGeneralGoalOne(int x, int y){
		int temp[] = new int[2];
		for (int i = x-1; i< x+2 ; i++){
			for (int j = y-1; j< y+2 ; j++){
				if (!(i == x && j ==y)){
					if (this.gameBoard[i][j] == BLACK){
						temp[0] = i;
						temp[1] = j;
					}
				}
			}
		}
		return temp;
	}
	/**
	 * whiteGeneralGoal is a helper function which checks the number of white chips around the coordinate with distance of 1.
	 * @param x is the x-coordinate of the value that needs to be checked
	 * @param y is the y-coordinate of the value that needs to be checked
	 * @return the number of neighbors that has white chips
	 */
	private int whiteGeneralGoal(int x, int y){
		int temp = 0;
		for (int i = x-1; i< x+2 ; i++){
			for (int j = y-1; j< y+2 ; j++){
				if (!(i == x && j ==y)){
					if (this.gameBoard[i][j] == WHITE){
						temp++;
					}
				}
			}
		}
		return temp;
	}
	/**
	 * blackGeneralGoal is a helper function which checks the number of black chips around the coordinate with distance of 1.
	 * @param x is the x-coordinate of the value that needs to be checked
	 * @param y is the y-coordinate of the value that needs to be checked
	 * @return the number of neighbors that has black chips
	 */
	private int blackGeneralGoal(int x, int y){
		int temp = 0;
		for (int i = x-1; i< x+2 ; i++){
			for (int j = y-1; j< y+2 ; j++){
				if (!(i == x && j ==y)){
					if (this.gameBoard[i][j] == BLACK){
						temp++;
					}
				}
			}
		}
		return temp;
	}
	/**
	 * whiteRight is a boolean which checks whether if the move is okay to move
	 * @param m is the move of the white chip
	 * @return true if it is okay to move, false otherwise
	 */
	private boolean whiteRight(Move m){
		if (whiteRightGoal(m.x1, m.y1) >1){
			return false;
		}
		else if (whiteRightGoal(m.x1, m.y1) ==1){
			int b[] = whiteRightGoalOne(m.x1, m.y1);
			if (b[0] == 7){
				if (whiteRightGoal(b[0], b[1]) >0){
					return false;
				}
			}else{
				if (whiteGeneralGoal(b[0], b[1]) >0){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * whiteRightGoal is a helper function which checks the neighbor of the location if it is located in the right-goal area.
	 * @param x is the x-coordinate of the location
	 * @param y is the y-coordinate of the location
	 * @return the number of white chips around the location in one of the white goal area
	 */
	private int whiteRightGoal(int x, int y){
		int temp = 0;
		if (this.gameBoard[x][y-1] == WHITE){
			  temp++;
		}
		if (this.gameBoard[x][y+1] == WHITE){
			  temp++;
		}
		if (this.gameBoard[x-1][y] == WHITE){
			  temp++;
		}
		if (this.gameBoard[x-1][y-1] == WHITE){
			  temp++;
		}
		if (this.gameBoard[x-1][y+1] == WHITE){
			  temp++;
		}
		return temp;
	}
	
	/**
	 * whiteRightGoalone is a helper function which stores the location of neighbor of the location if it is located in the right-goal area.
	 * @param x is the x-coordinate of location
	 * @param y is the y-coordinate of location
	 * @return the array that indicates the location that contained white chips
	 */
	private int[] whiteRightGoalOne(int x, int y){
		int temp[] = new int[2];
		if (this.gameBoard[x][y-1] == WHITE){
			temp[0] = x;
			temp[1] = y-1;
		}
		if (this.gameBoard[x][y+1] == WHITE){
			temp[0] = x;
			temp[1] = y+1;
		}
		if (this.gameBoard[x-1][y-1] == WHITE){
			temp[0] = x-1;
			temp[1] = y-1;
		}
		if (this.gameBoard[x-1][y] == WHITE){
			temp[0] = x-1;
			temp[1] = y;
		}
		if (this.gameBoard[x-1][y+1] == WHITE){
			temp[0] = x-1;
			temp[1] = y+1;
		}
		return temp;
		
		
	}
	/**
	 * whiteLeft is a boolean which checks whether if the move is okay to move
	 * @param m is the move of the white chip
	 * @return true if it is okay to move, false otherwise
	 *
	 */
	private boolean whiteLeft(Move m){
		if (whiteLeftGoal(m.x1, m.y1) >1){
			return false;
		}
		else if (whiteLeftGoal(m.x1, m.y1) ==1){
			int b[] = whiteLeftGoalOne(m.x1, m.y1);
			if (b[0] == 0){
				if (whiteLeftGoal(b[0], b[1]) >0){
					return false;
				}
			}else{
				if (whiteGeneralGoal(b[0], b[1]) >0){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * whiteLeftGoal is a helper function which checks the neighbor of the location if it is located in the left-goal area.
	 * @param x is the x-coordinate of the location
	 * @param y is the y-coordinate of the location
	 * @return the number of white chips around the location in one of the white goal area
	 */
	private int whiteLeftGoal(int x, int y){
		int temp = 0;
		if (this.gameBoard[x][y-1] == WHITE){
			  temp++;
		}
		if (this.gameBoard[x][y+1] == WHITE){
			  temp++;
		}
		if (this.gameBoard[x+1][y] == WHITE){
			  temp++;
		}
		if (this.gameBoard[x+1][y-1] == WHITE){
			  temp++;
		}
		if (this.gameBoard[x+1][y+1] == WHITE){
			  temp++;
		}
		return temp;
	}
	/**
	 * whiteleftGoalone is a helper function which stores the location of neighbor of the location if it is located in the left-goal area.
	 * @param x is the x-coordinate of location
	 * @param y is the y-coordinate of location
	 * @return the array that indicates the location that contained white chips
	 */
	private int[] whiteLeftGoalOne(int x, int y){
		int temp[] = new int[2];
		if (this.gameBoard[x][y-1] == WHITE){
			temp[0] = x;
			temp[1] = y-1;
		}
		if (this.gameBoard[x][y+1] == WHITE){
			temp[0] = x;
			temp[1] = y+1;
		}
		if (this.gameBoard[x+1][y-1] == WHITE){
			temp[0] = x+1;
			temp[1] = y-1;
		}
		if (this.gameBoard[x+1][y] == WHITE){
			temp[0] = x+1;
			temp[1] = y;
		}
		if (this.gameBoard[x+1][y+1] == WHITE){
			temp[0] = x+1;
			temp[1] = y+1;
		}
		return temp;
		
		
	}
	/**
	 *  blackLower is a boolean which checks whether if the move is okay to move
	 * @param m is the move of the black chip
	 * @return true if it is okay to move, false otherwise
	 */
	private boolean blackLower(Move m){
		if (blackLowerGoal(m.x1, m.y1) > 1){
			return false;
		}
		else if (blackLowerGoal(m.x1, m.y1) ==1){
			int b[] = blackLowerGoalOne(m.x1, m.y1);
			if (b[1] == 7){
				if (blackLowerGoal(b[0], b[1]) >0){
					return false;
				}
			}else{
				if (blackGeneralGoal(b[0], b[1]) >0){
					return false;
				}
			}
		}
		return true;	
	}
	
	/**
	 * blackLowerGoal is a helper function which checks the neighbor of the location if it is located in the lower-goal area.
	 * @param x is the x-coordinate of the location
	 * @param y is the y-coordinate of the location
	 * @return the number of black chips around the location in one of the black goal area
	 */
	private int blackLowerGoal(int x, int y){
		int temp = 0;
		if (this.gameBoard[x-1][y] == BLACK){
			  temp++;
		}
		if (this.gameBoard[x+1][y] == BLACK){
			  temp++;
		}
		if (this.gameBoard[x-1][y-1] == BLACK){
			  temp++;
		}
		if (this.gameBoard[x][y-1] == BLACK){
			  temp++;
		}
		if (this.gameBoard[x+1][y-1] == BLACK){
			  temp++;
		}
		return temp;
	}
	/**
	 * blackLowerGoalone is a helper function which stores the location of neighbor
	 * of the location if it is located in the lower-goal area.
	 * @param x is the x-coordinate of location
	 * @param y is the y-coordinate of location
	 * @return the array that indicates the location that contained black chips
	 */
	private int[] blackLowerGoalOne(int x, int y){
		int temp[] = new int[2];
		if (this.gameBoard[x-1][y] == BLACK){
			temp[0] = x-1;
			temp[1] = y;
		}
		if (this.gameBoard[x+1][y] == BLACK){
			temp[0] = x+1;
			temp[1] = y;
		}
		if (this.gameBoard[x-1][y-1] == BLACK){
			temp[0] = x-1;
			temp[1] = y-1;
		}
		if (this.gameBoard[x][y-1] == BLACK){
			temp[0] = x;
			temp[1] = y-1;
		}
		if (this.gameBoard[x+1][y-1] == BLACK){
			temp[0] = x+1;
			temp[1] = y-1;
		}
		return temp;
	}
	
	/**
	 * blackUpper is a boolean which checks whether if the move is okay to move
	 * @param m is the move of the black chip
	 * @return true if it is okay to move, false otherwise
	 */
	private boolean blackUpper(Move m){
		if (blackUpperGoal(m.x1, m.y1) >1){
			return false;
		}
		else if (blackUpperGoal(m.x1, m.y1) ==1){
			int b[] = blackUpperGoalOne(m.x1, m.y1);
			if (b[1] == 0){
				if (blackUpperGoal(b[0], b[1]) >0){
					return false;
				}
			}else{
				if (blackGeneralGoal(b[0], b[1]) >0){
					return false;
				}
			}
		}
		return true;	
	}
	
	
	/**
	 * blackUpperGoal checks the number of black chips in its neighbor
	 * @param x is the x coordinate of the current chip
	 * @param y is the y coordinate of the current chip
	 * @return the number of black chips in its neighbor
	 */
	private int blackUpperGoal(int x, int y){
		int temp = 0;
		if (this.gameBoard[x-1][y] == BLACK){
			  temp++;
		}
		if (this.gameBoard[x+1][y] == BLACK){
			  temp++;
		}
		if (this.gameBoard[x-1][y+1] == BLACK){
			  temp++;
		}
		if (this.gameBoard[x][y+1] == BLACK){
			  temp++;
		}
		if (this.gameBoard[x+1][y+1] == BLACK){
			  temp++;
		}
		return temp;
	}
	
	/**
	 * blackUpperGoalOne is a helper function for blackUpper which gives the coordinate of the
	 * neighbor black chip
	 * @param x is the x coordinate
	 * @param y is the y coordinate
	 * @return the coordinate of the neighbor black chip
	 */
	private int[] blackUpperGoalOne(int x, int y){
		int temp[] = new int[2];
		if (this.gameBoard[x-1][y] == BLACK){
			temp[0] = x-1;
			temp[1] = y;
		}
		if (this.gameBoard[x+1][y] == BLACK){
			temp[0] = x+1;
			temp[1] = y;
		}
		if (this.gameBoard[x-1][y+1] == BLACK){
			temp[0] = x-1;
			temp[1] = y+1;
		}
		if (this.gameBoard[x][y+1] == BLACK){
			temp[0] = x;
			temp[1] = y+1;
		}
		if (this.gameBoard[x+1][y+1] == BLACK){
			temp[0] = x+1;
			temp[1] = y+1;
		}
		return temp;
	}
	
	
	
	
	
	
	
	/**
	 * clone method returns a clone of current Board
	 * @return a new cloned Board to prevent corruption of current gameBoard
	 */
	public Board clone(){
		Board newBoard = new Board();
		for (int j = 0; j < 8; j++){
			for (int i = 0; i < 8; i++){
				newBoard.gameBoard[i][j] = gameBoard[i][j];
			}
		}
		newBoard.blackChips = this.blackChips;
		newBoard.whiteChips = this.whiteChips;
		return newBoard;
	}
	
	
	
	/**
	 * getCell(x, y) is used to get the information of a given cell
	 * @param x is the x cell position
	 * @param y is the y cell position
	 * @return the cellContents at coordinate x, y
	 */
	private int getCell(int x, int y){
		return gameBoard[x][y];
	}
	
	/**
	 * validMovesList returns a list of valid moves
	 * @param color is the color of the chip (determines which player)
	 * @return a list of valid moves
	 */
	public DList validMovesList(int color){
		if (color == BLACK){
			if (this.blackChips <10){
				return addValidMovesList(color);
			}
		}
		else if (color == WHITE){
			if (this.whiteChips <10){
				return addValidMovesList(color);
			}
		}
		return stepValidMovesList(color);
	}
	
	
	/**
	 * addValifMovesList is a helper function for validMovesList
	 * this is used to return a list of valid ADD moves
	 * @param color is the color of the chip
	 * @return a list of valid moves
	 */
	private DList addValidMovesList(int color){
		DList list = new DList();
		for (int j = 0 ; j < 8 ; j++){
			for (int i = 0; i < 8 ; i++){
				Move m = new Move(i, j);
				if (this.validMoves(m, color)){
					list.insertEnd(m);
				}
			}
		}
		return list;
	}
	/**
	 * stepValidMovesList is a helper function for validMovesList
	 * this is used to return a list of valid STEP moves
	 * @param color is the color of the chip
	 * @return a list of valid moves
	 */
	private DList stepValidMovesList(int color){
		DList listOne = new DList();
		DList listTwo = new DList();
		for (int j = 0 ; j < 8 ; j++){
			for (int i = 0; i < 8 ; i++){
				if (this.gameBoard[i][j] == color){
					listOne.insertEnd(i, j);
				}
			}
		}
		DListNode node = listOne.front();
		while (node.hasNext()){
			int x = node.getCoorx();
			int y = node.getCoory();
			for (int j = 0 ; j < 8 ; j++){
				for (int i = 0; i < 8 ; i++){
					Move newMove = new Move(i, j, x, y);
					if (validMoves(newMove, color)){
						listTwo.insertEnd(newMove);
					}
				}
			}
			node = (DListNode) node.next();
		}
		int x = node.getCoorx();
		int y = node.getCoory();
		for (int j = 0 ; j < 8 ; j++){
			for (int i = 0; i < 8 ; i++){
				Move newMove = new Move(i, j, x, y);
				if (validMoves(newMove, color)){
					listTwo.insertEnd(newMove);
				}
			}
		}
		
		return listTwo;
	}
	
	
	/**
	 * checkUpper is the method that checks whether the Upper direction of the chip contains any chips
	 * @param color is the color of the chip, can be black or white
	 * @param x is the x-coordinate of indicated chip
	 * @param y is the y-coordinate of indicated chip
	 * @return true if chip founded, false otherwise.
	 */
	private boolean checkUpper(int color, int x, int y){
		int y1 = y - 1;
		while (y1 >= 0){
			int cellInfo = this.getCell(x, y1);
			if (cellInfo == color){
				this.chip = new ChipCoordinate(x, y1);
				return true;
			}
			else if (cellInfo != EMPTY){
				return false;
			}
			y1--;
		}
		return false;
		
	}
	/**
	 * checkLower is the method that checks whether the Lower direction of the chip contains any chips
	 * @param color is the color of the chip, can be black or white
	 * @param x is the x-coordinate of indicated chip
	 * @param y is the y-coordinate of indicated chip
	 * @return true if chip founded, false otherwise.
	 */
	private boolean checkLower(int color, int x, int y){
		int y1 = y + 1;
		while (y1 <= 7){
			int cellInfo = this.getCell(x, y1);
			if (cellInfo == color){
				this.chip = new ChipCoordinate(x, y1);
				return true;
			}
			else if (cellInfo != EMPTY){
				return false;
			}
			y1++;
		}
		return false;
		
	}
	
	/**
	 * checkUpperRight is the method that checks whether the UpperRight direction of the chip contains any chips
	 * @param color is the color of the chip, can be black or white
	 * @param x is the x-coordinate of indicated chip
	 * @param y is the y-coordinate of indicated chip
	 * @return true if chip founded, false otherwise.
	 */
	private boolean checkUpperRight(int color, int x, int y){
		int x2 = x + 1;
		int y2 = y - 1;
		
		while (y2 >= 0 && x2 <= 7){
			int cellInfo = this.getCell(x2, y2);
			if (cellInfo == color){
				this.chip = new ChipCoordinate(x2, y2);
				return true;
			}
			else if (cellInfo != EMPTY){
				return false;
			}
			x2++;
			y2--;
		}
		return false;		
	}
	/**
	 * checkUpperLeft is the method that checks whether the UpperLeft direction of the chip contains any chips
	 * @param color is the color of the chip, can be black or white
	 * @param x is the x-coordinate of indicated chip
	 * @param y is the y-coordinate of indicated chip
	 * @return true if chip founded, false otherwise.
	 */
	private boolean checkUpperLeft(int color, int x, int y){
		int x2 = x - 1;
		int y2 = y - 1;
		
		while (y2 >= 0 && x2 >= 0){
			int cellInfo = this.getCell(x2, y2);
			if (cellInfo == color){
				this.chip = new ChipCoordinate(x2, y2);
				return true;
			}
			else if (cellInfo != EMPTY){
				return false;
			}
			x2--;
			y2--;
		}
		return false;		
	}
	/**
	 * checkLeft is the method that checks whether the Left direction of the chip contains any chips
	 * @param color is the color of the chip, can be black or white
	 * @param x is the x-coordinate of indicated chip
	 * @param y is the y-coordinate of indicated chip
	 * @return true if chip founded, false otherwise.
	 */
	private boolean checkLeft(int color, int x, int y){
		int x1 = x - 1;
		while (x1 >= 0){
			int cellInfo = this.getCell(x1, y);
			if (cellInfo == color){
				this.chip = new ChipCoordinate(x1, y);
				return true;
			}
			else if (cellInfo != EMPTY){
				return false;
			}
			x1--;
		}
		return false;		
	}
	/**
	 * checkRight is the method that checks whether the Right direction of the chip contains any chips
	 * @param color is the color of the chip, can be black or white
	 * @param x is the x-coordinate of indicated chip
	 * @param y is the y-coordinate of indicated chip
	 * @return true if chip founded, false otherwise.
	 */
	private boolean checkRight(int color, int x, int y){
		int x1 = x + 1;
		while (x1 <= 7){
			int cellInfo = this.getCell(x1, y);
			if (cellInfo == color){
				this.chip = new ChipCoordinate(x1, y);
				return true;
			}
			else if (cellInfo != EMPTY){
				return false;
			}
			x1++;
		}
		return false;				
	}
	/**
	 * checkLowerLeft is the method that checks whether the LowerLeft direction of the chip contains any chips
	 * @param color is the color of the chip, can be black or white
	 * @param x is the x-coordinate of indicated chip
	 * @param y is the y-coordinate of indicated chip
	 * @return true if chip founded, false otherwise.
	 */
	private boolean checkLowerLeft(int color, int x, int y){
		int x2 = x - 1;
		int y2 = y + 1;
		
		while (y2 <= 7 && x2 >= 0){
			int cellInfo = this.getCell(x2, y2);
			if (cellInfo == color){
				this.chip = new ChipCoordinate(x2, y2);
				return true;
			}
			else if (cellInfo != EMPTY){
				return false;
			}
			x2--;
			y2++;
		}
		return false;		
	}
	/**
	 * checkLowerRight is the method that checks whether the LowerRight direction of the chip contains any chips
	 * @param color is the color of the chip, can be black or white
	 * @param x is the x-coordinate of indicated chip
	 * @param y is the y-coordinate of indicated chip
	 * @return true if chip founded, false otherwise.
	 */
	private boolean checkLowerRight(int color, int x, int y){
		int x2 = x + 1;
		int y2 = y + 1;
		
		while (y2 <= 7 && x2 <= 7){
			int cellInfo = this.getCell(x2, y2);
			if (cellInfo == color){
				this.chip = new ChipCoordinate(x2, y2);
				return true;
			}
			else if (cellInfo != EMPTY){
				return false;
			}
			x2++;
			y2++;
		}
		return false;		
	}
	
	
	/**
	 * hasWinningNetwork is used to determine whether the board has a winning network for a given color
	 * @param color is the color of the side
	 * @return true if the color has a winning network, false otherwise.
	 */
	public boolean hasWinningNetwork(int color){
		if (color == BLACK){
			if (!blackUpperGoalHasBlackChip() || !blackLowerGoalHasBlackChip()){
				return false;
			}
			if (this.blackChips <6){
				return false;
			}
			DList list = evaluateNetworks(color);
			if (list.getSize()>0){
				return true;
			}
			
		}else if (color == WHITE){
			if (!whiteLeftGoalHasWhiteChip() || !whiteRightGoalHasWhiteChip()){
				return false;
			}
			if (this.whiteChips <6){
				return false;
			}
			DList list = evaluateNetworks(color);
			if (list.getSize()>0){
				return true;
			}
			
		}
		return false;
	}
	/**
	 * collectAllNetworks is used to collect all networks
	 * @param color is the color of the network
	 * @return a list of all networks
	 */
	public DList collectAllNetworks(int color){
		if (color == BLACK){
			DList list = blackChipsInUpperGoal();
			if (list.getSize()==0){
				return new DList();
			}
			DListNode node = list.front();
			while(node.hasNext()){	
				tempList.insertEnd(node.getChip());
				collectBlackSingleNetworks(node.getChip());
				tempList.removeEnd();
				node = (DListNode) node.next();
			}
			tempList.insertEnd(node.getChip());
			collectBlackSingleNetworks(node.getChip());
			tempList = new DList(); //reset the templist
			DList newlist = this.cloneDListWithList(networks);
			networks = new DList(); //reset the networks
			return newlist;
		}else if (color == WHITE){
			DList list = whiteChipsInLeftGoal();
			if (list.getSize()==0){
				return new DList();
			}
			DListNode node = list.front();
			while(node.hasNext()){	
				tempList.insertEnd(node.getChip());
				collectWhiteSingleNetworks(node.getChip());
				tempList.removeEnd();
				node = (DListNode) node.next();
			}
			tempList.insertEnd(node.getChip());
			collectWhiteSingleNetworks(node.getChip());
			tempList = new DList(); //reset the templist
			DList newlist = this.cloneDListWithList(networks);
			networks = new DList(); //reset the networks
			return newlist;
		}
		
		return new DList();
	}
	/**
	 * evaluateNetworks evaluates all networks and returns a list of validNetworks
	 * @param color is the color of the chip being evaluated
	 * @return a list of validNetworks
	 */
	private DList evaluateNetworks(int color){
		DList netlist = collectAllNetworks(color);
		if (netlist.getSize() == 0){
			return new DList();
		}
		DList validNetworks = new DList();
		DListNode node = netlist.front();
		while(node.hasNext()){
			DList list = node.getList();
			if (list.getSize() < 6){
				//Do nothing, since a network less than 6 chips is not a valid network
			}else if (evaluateConnection(list)){
				//Do nothing, since the network is not valid in this case
			}else{
				validNetworks.insertEnd(list);
			}
			
			node = (DListNode) node.next();
		}
		DList list = node.getList();
		if (list.getSize() < 6){
			//Do nothing, since a network less than 6 chips is not a valid network
		}else if (evaluateConnection(list)){
			//Do nothing, since the network is not valid in this case
		}else{
			validNetworks.insertEnd(list);
		}
		return validNetworks;
	}
	
	
	
	/**
	 * evaluateConnection checks there is more than 2 connections of the same direction
	 * @param list is the list of networks being passed in
	 * @return true if more than 2 connections in the same direction, false otherwise.
	 */
	private boolean evaluateConnection(DList list){
		DList list2 = new DList();
		DListNode node = list.front();
		while(node.hasNext()){
			list2.insertEnd(node.getChip());
			if (list2.getSize() >2){
				if(checkXCoordinate(list2)){
					return true;
				}else if (checkYCoordinate(list2)){
					return true;
				}else if (checkXYCoordinate(list2)){
					return true;
				}
				
				list2.removeFront();
			}
			node = (DListNode) node.next();
		}
		list2.insertEnd(node.getChip());
		if (list2.getSize() >2){
			if(checkXCoordinate(list2)){
				return true;
			}else if (checkYCoordinate(list2)){
				return true;
			}else if (checkXYCoordinate(list2)){
				return true;
			}
			list2.removeFront();
		}
		return false;
	}
	/**
	 * checkXYCoordinate checks if there are more than 3 connections in the LowerLeft, UpperLeft, UpperRight, and
	 * LowerRight direction
	 * @param list is the list of chips being passed in
	 * @return true if there are more than 3 chips in the same direction as mentioned in the description, false otherwise.
	 */
	private boolean checkXYCoordinate(DList list){
		int ax = list.front().getChip().getXCoordinate();
		int ay = list.front().getChip().getYCoordinate();
		int bx = list.front().next().getChip().getXCoordinate();
		int by = list.front().next().getChip().getYCoordinate();
		int cx = list.front().next().next().getChip().getXCoordinate();
		int cy = list.front().next().next().getChip().getYCoordinate();
		if (ax != bx && bx != cx && ax != cx && ay!=by && by!=cy && ay!=cy){
			if (cx< bx && bx < ax){
				if (cy > by && by > ay){
					return true;
				}
				if (cy < by && by < ay){
					return true;
				}
			}
			if (cx> bx && bx > ax){
				if (cy > by && by > ay){
					return true;
				}
				if (cy < by && by < ay){
					return true;
				}
			}
		}
		return false;		
	}
	
	
	/**
	 * checkXCoordinate checks if there are 3 same direction chips in X
	 * @param list is the list of chips being passed in
	 * @return true if there are 3 same direction chips in x-axis, false otherwise.
	 */
	private boolean checkXCoordinate(DList list){
		int ax = list.front().getChip().getXCoordinate();
		int bx = list.front().next().getChip().getXCoordinate();
		int cx = list.front().next().next().getChip().getXCoordinate();
		if(ax == bx && bx == cx){
			return true;
		}
		return false;
	}
	/**
	 * checkYCoordinate checks if there are 3 same direction chips in Y
	 * @param list is the list of chips being passed in
	 * @return true if there are 3 same direction chips in y-axis, false otherwise.
	 */
	private boolean checkYCoordinate(DList list){
		int ay = list.front().getChip().getYCoordinate();
		int by = list.front().next().getChip().getYCoordinate();
		int cy = list.front().next().next().getChip().getYCoordinate();
		if (ay == by && by == cy){
			return true;
		}
		return false;
	}
	/**
	 * collectBlackSingleNetworks is used to collect black networks starting from a chip in the upper goal area
	 * @param chip is a black chip from the upper goal area
	 */
	private void collectBlackSingleNetworks(ChipCoordinate chip){
		DList list = this.chipsConnection(BLACK, chip.getXCoordinate(), chip.getYCoordinate());
		if (list.getSize() == 0){
			return;
		}
		DListNode node = list.front();
		while(node.hasNext()){
			if (this.isUpperGoal(node.getChip())){
				//do nothing if the connection is with the upper goal (basically cut the connection)
			}else if (this.isLowerGoal(node.getChip())){
				//if the chip is within the lower goal: add to the network
				tempList.insertEnd(node.getChip());
				networks.insertEnd(cloneDList(tempList));
				tempList.removeEnd();
			}else if (this.hasChipCoordinate(node.getChip(), tempList)){
				//do nothing if the connection has chip that is used twice
			}else{
				tempList.insertEnd(node.getChip());
				collectBlackSingleNetworks(node.getChip());
				tempList.removeEnd();
			}
			node = (DListNode) node.next();
		}
		if (this.isUpperGoal(node.getChip())){
			//do nothing if the connection is with the upper goal (basically cut the connection)
		}else if (this.isLowerGoal(node.getChip())){
			//if the chip is within the lower goal: add to the network
			tempList.insertEnd(node.getChip());
			networks.insertEnd(cloneDList(tempList));
			tempList.removeEnd();
		}else if (this.hasChipCoordinate(node.getChip(), tempList)){
			//do nothing if the connection has chip that is used twice
		}else{
			tempList.insertEnd(node.getChip());
			collectBlackSingleNetworks(node.getChip());
			tempList.removeEnd();
		}
		
	}
	
	/**
	 * collectWhiteSingleNetworks is used to collect white networks starting from a chip in the left goal area
	 * @param chip is a white chip from the left goal area
	 */
	private void collectWhiteSingleNetworks(ChipCoordinate chip){
		DList list = this.chipsConnection(WHITE, chip.getXCoordinate(), chip.getYCoordinate());
		if (list.getSize() ==0){
			return;
		}
		DListNode node = list.front();
		while(node.hasNext()){
			if (this.isLeftGoal(node.getChip())){
				//do nothing if the connection is with the left goal (basically cut the connection)
			}else if (this.isRightGoal(node.getChip())){
				//if the chip is within the right goal: add to the network
				tempList.insertEnd(node.getChip());
				networks.insertEnd(cloneDList(tempList));
				tempList.removeEnd();
			}else if (this.hasChipCoordinate(node.getChip(), tempList)){
				//do nothing if the connection has chip that is used twice
			}else{
				tempList.insertEnd(node.getChip());
				collectWhiteSingleNetworks(node.getChip());
				tempList.removeEnd();
			}
			node = (DListNode) node.next();
		}
		if (this.isLeftGoal(node.getChip())){
			//do nothing if the connection is with the left goal (basically cut the connection)
		}else if (this.isRightGoal(node.getChip())){
			//if the chip is within the right goal: add to the network
			tempList.insertEnd(node.getChip());
			networks.insertEnd(cloneDList(tempList));
			tempList.removeEnd();
		}else if (this.hasChipCoordinate(node.getChip(), tempList)){
			//do nothing if the connection has chip that is used twice
		}else{
			tempList.insertEnd(node.getChip());
			collectWhiteSingleNetworks(node.getChip());
			tempList.removeEnd();
		}
		
	}
	
	
	/**
	 * cloneDListWithList clones a DList that contains another DList
	 * @param list is the list to be cloned
	 * @return a new cloned separated references DList
	 */
	private DList cloneDListWithList(DList list){
		if (list.getSize()==0){
			return new DList();
		}
		DList newList = new DList();
		DListNode listNode = list.front();
		while (listNode.hasNext()){
			newList.insertEnd(listNode.getList());
			listNode = (DListNode) listNode.next();
		}
		newList.insertEnd(listNode.getList());
		return newList;
		
	}
	/**
	 * cloneDList returns a clone of DList (so that the references does not change)
	 * @param list is the list to be cloned
	 * @return a new cloned separated DList
	 */
	private DList cloneDList(DList list){
		if (list.getSize()==0){
			return new DList();
		}
		DList newList = new DList();
		DListNode listNode = list.front();
		while (listNode.hasNext()){
			newList.insertEnd(listNode.getChip());
			listNode = (DListNode) listNode.next();
		}
		newList.insertEnd(listNode.getChip());
		return newList;
		
	}
	
	/**
	 * chipsOnBoard checks whether the chips exists on board or not
	 * @param color is the color of the chip
	 * @param x is the x coordinate of the chip
	 * @param y is the y coordinate of the chip
	 * @return true if the chips exists on board, false otherwise
	 */
	public boolean chipsOnBoard(int color, int x, int y){
		if (this.gameBoard[x][y] == color){
			return true;
		}
		return false;
	}
	/**
	 * chipsConnection gets a list of chip connection of a given color
	 * @param color is the color of the color
	 * @param x is the x coordinate of the chip
	 * @param y is the y coordinate of the chip
	 * @return a list of connection of a given chip
	 */
	public DList chipsConnection(int color, int x, int y){
		int a = x;
		int b = y;
		DList list = new DList();
		//This print statement is for debugging purposes, please ignore when grading.
		if (this.gameBoard[x][y] != color){
			System.out.println("Error!, the chip does not exist on board!");
		}
		if (checkUpper(color, a, b)){
			list.insertEnd(chip);
		}
		if (checkUpperLeft(color, a, b)){
			list.insertEnd(chip);
		}
		if (checkUpperRight(color, a, b)){
			list.insertEnd(chip);
		}
		if (checkLeft(color, a, b)){
			list.insertEnd(chip);
		}
		if (checkRight(color, a, b)){
			list.insertEnd(chip);
		}
		if (checkLowerLeft(color, a, b)){
			list.insertEnd(chip);
		}
		if (checkLower(color, a, b)){
			list.insertEnd(chip);
		}
		if (checkLowerRight(color, a, b)){
			list.insertEnd(chip);
		}
		return list;
		
		
	}
	/**
	 * isGoalArea checks whether the chip is within one of the four goal area
	 * @param chip
	 * @return true if the chip is within one of the four goal area, false otherwise.
	 */
	public boolean isGoalArea(ChipCoordinate chip){
		if (isUpperGoal(chip) || isLowerGoal(chip) || isLeftGoal(chip) || isRightGoal(chip)){
			return true;
		}
		return false;
	}
	/**
	 * numChipsinLowerorRightGoalArea is used to get the number of chips in Lower or Right Goal Area
	 * @param color is the color of the chip
	 * @return the number of chips in Lower or Right goal area
	 */
	public int numChipsinLowerorRightGoalArea(int color){
		if (color == BLACK){
			DList b = this.blackChipsInLowerGoal();
			
			return b.getSize();
		}
		if (color == WHITE){
			DList b = this.whiteChipsInRightGoal();
			
			return b.getSize();
		}
		return 0;
		
	}
	/**
	 * numChipsinUpperorLeftGoalArea is used to get the number of chips in Upper or left Goal Area
	 * @param color is the color of the chip
	 * @return the number of chips in upper or left goal area
	 */
	public int numChipsinUpperorLeftGoalArea(int color){
		if (color == BLACK){
			DList a = this.blackChipsInUpperGoal();
			
			return a.getSize();
		}
		if (color == WHITE){
			DList a = this.whiteChipsInLeftGoal();
			
			return a.getSize();
		}
		return 0;
		
	}
		
		
	/**
	 * isUpperGoal checks whether the chip is in upper goal area
	 * @param chip is the coordinate of the chip
	 * @return true if the chip is placed in the upper goal area, false otherwise.
	 */
	private boolean isUpperGoal(ChipCoordinate chip){
		if (chip.getYCoordinate() == 0){
			return true;
		}
		return false;
	}
	/**
	 * isLowerGoal checks whether the chip is in lower goal area
	 * @param chip is the coordinate of the chip
	 * @return true if the chip is placed in the lower goal area, false otherwise.
	 */
	private boolean isLowerGoal(ChipCoordinate chip){
		if (chip.getYCoordinate() == 7){
			return true;
		}
		return false;
	}
	/**
	 * isLeftGoal checks whether the chip is in left goal area
	 * @param chip is the coordinate of the chip
	 * @return true if the chip is placed in the left goal area, false otherwise.
	 */
	private boolean isLeftGoal(ChipCoordinate chip){
		if (chip.getXCoordinate() ==0){
			return true;
		}
		return false;
	}
	
	/**
	 * isRightGoal checks whether the chip is in right goal area
	 * @param chip is the coordinate of the chip
	 * @return true if the chip is placed in the right goal area, false otherwise.
	 */
	private boolean isRightGoal(ChipCoordinate chip){
		if (chip.getXCoordinate() ==7){
			return true;
		}
		return false;
	}
	
	
	/**
	 * hasChipCoordinate is used to check whether a chip is within the given list or not
	 * @param node is the node that stores the chip
	 * @param list is the list being passed in
	 * @return true if the chip is within the list, false otherwise
	 */
	private boolean hasChipCoordinate(ChipCoordinate chip, DList list){
		if (list.getSize() ==0){
			return false;
		}
		DListNode nodeone = list.front();
		while(nodeone.hasNext()){
			ChipCoordinate coor1 = nodeone.getChip();
			if (chip.getXCoordinate() == coor1.getXCoordinate() && chip.getYCoordinate() == coor1.getYCoordinate()){
				return true;
			}
			nodeone = (DListNode) nodeone.next();
		}
		ChipCoordinate coor1 = nodeone.getChip();
		if (chip.getXCoordinate() == coor1.getXCoordinate() && chip.getYCoordinate() == coor1.getYCoordinate()){
			return true;
		}
		
		return false;
	}
	/**
	 * blackChipsInUpperGoal is a helper method used to get black chips in upper goal area
	 * @return a list of black chips in the upper goal area
	 */
	private DList blackChipsInUpperGoal(){
		DList list = new DList();
		for (int i = 1; i<7 ; i++){
			if (this.gameBoard[i][0] == BLACK){
				list.insertEnd(new ChipCoordinate(i, 0));
			}
		}
		return list;
	}
	/**
	 * blackChipsInLowerGoal is a helper method used to get black chips in lower goal area
	 * @return a list of black chips in the lower goal area
	 */
	private DList blackChipsInLowerGoal(){
		DList list = new DList();
		for (int i = 1; i<7 ; i++){
			if (this.gameBoard[i][7] == BLACK){
				list.insertEnd(new ChipCoordinate(i, 7));
			}
		}
		return list;
	}
	/**
	 * whiteChipsInLeftGoal is a helper method used to get white chips in left goal area
	 * @return a list of white chips in the left goal area
	 */
	private DList whiteChipsInLeftGoal(){
		DList list = new DList();
		for (int i = 1; i<7 ; i++){
			if (this.gameBoard[0][i] == WHITE){
				list.insertEnd(new ChipCoordinate(0, i));
			}
		}
		return list;
	}
	
	/**
	 * whiteChipsInRightGoal is a helper method used to get white chips in right goal area
	 * @return a list of white chips in the right goal area
	 */
	private DList whiteChipsInRightGoal(){
		DList list = new DList();
		for (int i = 1; i<7 ; i++){
			if (this.gameBoard[7][i] == WHITE){
				list.insertEnd(new ChipCoordinate(7, i));
			}
		}
		return list;
	}
	/**
	 * blackUpperGoalHasBlackChip is a helper method used to check whether the upper goal area has any black chips
	 * @return true if there is at least one black chip in the upper goal area, false otherwise
	 */
	private boolean blackUpperGoalHasBlackChip(){
		for (int i = 1; i<7 ; i++){
			if (this.gameBoard[i][0] == BLACK){
				return true;
			}
		}
		return false;
	}
	/**
	 * blackLowerGoalHasBlackChip is a helper method used to check whether the lower goal area has any black chips
	 * @return true if there is at least one black chip in the lower goal area, false otherwise
	 */
	private boolean blackLowerGoalHasBlackChip(){
		for (int i = 1; i<7 ; i++){
			if (this.gameBoard[i][7] == BLACK){
				return true;
			}
		}
		return false;
	}
	/**
	 * whiteLeftGoalHasWhiteChip is a helper method used to check whether the left goal area has any white chips
	 * @return true if there is at least one white chip in the left goal area, false otherwise
	 */
	private boolean whiteLeftGoalHasWhiteChip(){
		for (int i =1; i < 7; i++){
			if (this.gameBoard[0][i] == WHITE){
				return true;
			}
		}
		return false;
	}
	/**
	 * whiteLeftGoalHasWhiteChip is a helper method used to check whether the right goal area has any white chips
	 * @return true if there is at least one white chip in the right goal area, false otherwise
	 */
	private boolean whiteRightGoalHasWhiteChip(){
		for (int i =1; i < 7; i++){
			if (this.gameBoard[7][i] == WHITE){
				return true;
			}
		}
		return false;
	}
	
	/* 
	 * main function is for testing only. Please ignore this when grading 
	 */
	public static void main(String[] args){
		
		Board a4 = new Board();
		System.out.println(a4.hasWinningNetwork(BLACK));
		System.out.println(a4.hasWinningNetwork(WHITE));
		Move m1 = new Move(1, 1);
		a4.doMove(m1, 1);
		m1 = new Move(2, 1);
		a4.doMove(m1, 0);
		m1 = new Move(2, 2);
		a4.doMove(m1, 1);
		m1 = new Move(3, 2);
		a4.doMove(m1, 0);
		m1 = new Move(4, 2);
		a4.doMove(m1, 1);
		m1 = new Move(5, 2);
		a4.doMove(m1, 0);
		m1 = new Move(5, 3);
		a4.doMove(m1, 1);
		m1 = new Move(6, 3);
		a4.doMove(m1, 0);
		m1 = new Move(1, 4);
		a4.doMove(m1, 1);
		m1 = new Move(2, 4);
		a4.doMove(m1, 0);
		m1 = new Move(2, 5);
		a4.doMove(m1, 1);
		m1 = new Move(3, 5);
		a4.doMove(m1, 0);
		m1 = new Move(4, 5);
		a4.doMove(m1, 1);
		m1 = new Move(5, 5);
		a4.doMove(m1, 0);
		m1 = new Move(5, 6);
		a4.doMove(m1, 1);
		m1 = new Move(6, 6);
		a4.doMove(m1, 0);
		m1 = new Move(6, 1);
		a4.doMove(m1, 1);
		m1 = new Move(4, 0);
		a4.doMove(m1, 0);
		m1 = new Move(0, 6);
		a4.doMove(m1, 1);
		m1 = new Move(1, 7);
		a4.doMove(m1, 0);
		System.out.println("");
		for (int i = 0 ; i < 8 ; i++){
			for (int j=0; j<8 ; j++){
				System.out.print(a4.getCell(j, i));
				if (j != 7){
					System.out.print("-");
				}
			}
			System.out.println("");
		}
		System.out.println("black Chip size" + a4.blackChips);
		System.out.println("White Chip size" + a4.whiteChips);
		
		
		System.out.println(a4.hasWinningNetwork(BLACK));
		System.out.println(a4.hasWinningNetwork(WHITE));
		
		System.out.println("\n\n\nStarts another new Board...");
		Board a5 = new Board();
		m1 = new Move(0, 2);
		a5.doMove(m1, 1);
		m1 = new Move(1, 2);
		a5.doMove(m1, 0);
		m1 = new Move(4, 6);
		a5.doMove(m1, 1);
		m1 = new Move(1, 0);
		a5.doMove(m1, 0);
		m1 = new Move(1, 6);
		a5.doMove(m1, 1);
		m1 = new Move(6, 2);
		a5.doMove(m1, 0);
		m1 = new Move(4, 3);
		a5.doMove(m1, 1);
		m1 = new Move(6, 7);
		a5.doMove(m1, 0);;
		System.out.println("");
		for (int i = 0 ; i < 8 ; i++){
			for (int j=0; j<8 ; j++){
				System.out.print(a5.getCell(j, i));
				if (j != 7){
					System.out.print("-");
				}
			}
			System.out.println("");
		}
		System.out.println("black Chip size" + a5.blackChips);
		System.out.println("White Chip size" + a5.whiteChips);
		
		
	}
	
	
	
	
	
	
	
	
	
}
