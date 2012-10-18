/* OpenCommercial.java */

import java.net.*;
import java.io.*;

/**  A class that provides a main function to read five lines of a commercial
 *   Web page and print them in reverse order, given the name of a company.
 */

class OpenCommercial {

  /** Prompts the user for the name X of a company (a single string), opens
   *  the Web site corresponding to www.X.com, and prints the first five lines
   *  of the Web page in reverse order.
   *  @param arg is not used.
   *  @exception Exception thrown if there are any problems parsing the 
   *             user's input or opening the connection.
   */
  public static void main(String[] arg) throws Exception {

    BufferedReader keyboard;
    String inputLine;

    keyboard = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("Please enter the name of a company (without spaces): ");
    System.out.flush();        /* Make sure the line is printed immediately. */
    inputLine = keyboard.readLine();
    /* Replace this comment with your solution.  */
    /* I consulted the lecture notes posted online on CS61B website for URL, InputSteam, InputStreamReader, and BufferedReader */
    URL u = new URL("http://www." + inputLine + ".com/");
    InputStream ins = u.openStream();
    InputStreamReader isr = new InputStreamReader(ins);
    BufferedReader html = new BufferedReader(isr);
    /* Store these lines into String variables so that I can print out in reverse order*/
    String aa = html.readLine();
    String bb = html.readLine();
    String cc = html.readLine();
    String dd = html.readLine();
    String ee = html.readLine();
    System.out.println(ee);
    System.out.println(dd);
    System.out.println(cc);
    System.out.println(bb);
    System.out.println(aa);

  }
}
/* Name: Yang Peng
 * Login: cs61b-kk
 * SID: 21093456 
 * Collaborations: None, but I did consult the lecture notes posted on CS61B website (see Using Objects and my comments)
 */