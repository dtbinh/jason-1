import java.io.BufferedReader;
import java.io.InputStreamReader;
/* Name: Yang Peng
 * Login: cs61b-kk
 * SID: 21093456
 * Collaborations: None with any other people, but some of them are from OpenCommercial.java
 */

class Nuke2 {
	// Some of these lines are copied from OpenCommercial.java

	  public static void main(String[] arg) throws Exception {
	    BufferedReader keyboard;
	    String inputLine;
	    keyboard = new BufferedReader(new InputStreamReader(System.in));
	    System.out.flush();        /* Make sure the line is printed immediately. */
	    inputLine = keyboard.readLine();
	    if (inputLine.length()==2){
	    	System.out.println(inputLine.substring(0,1));
	    }
	    if (inputLine.length()>2){
	    System.out.print(inputLine.substring(0,1));
	    System.out.println(inputLine.substring(2));
	    }
	    
	    
	    }
	   
	  }
	
	
	
	
	
	
