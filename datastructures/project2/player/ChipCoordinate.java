package player;
public class ChipCoordinate {
	private int x;
	private int y;
	
	
	
	public int getXCoordinate(){
		return x;
	}
	
	public int getYCoordinate(){
		return y;
	}
	public ChipCoordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	public ChipCoordinate(){
		this.x = 0;
		this.y = 0;
	}
	
	public String toString() {
		String s = "("+ x + ", " + y + ")";
		return s;
		
	}
	
}
