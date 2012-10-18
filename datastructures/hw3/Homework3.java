/* Homework3.java */

public class Homework3 {

  /**
   *  smoosh() takes an array of ints.  On completion the array contains
   *  the same numbers, but wherever the array had two or more consecutive
   *  duplicate numbers, they are replaced by one copy of the number.  Hence,
   *  after smoosh() is done, no two consecutive numbers in the array are the
   *  same.
   *
   *  Any unused elements at the end of the array are set to -1.
   *
   *  For example, if the input array is [ 0 0 0 0 1 1 0 0 0 3 3 3 1 1 0 ],
   *  it reads [ 0 1 0 3 1 0 -1 -1 -1 -1 -1 -1 -1 -1 -1 ] after smoosh()
   *  completes.
   *
   *  @param ints the input array.
   **/

  public static void smoosh(int[] ints) {
    // Fill in your solution here.  (Ours is fourteen lines long, not counting
    // blank lines or lines already present in this file.)
	 // Mine is super-long in this case... 
	  if (ints.length == 0 || ints.length == 1){
		  return;
	  }
	  int removeLen = 0;
	  int total =0;
	  int j = 0;
	  int[] intsClone = ints.clone();
	  for (int i=0; i<intsClone.length-1;i++){
		 if (i == intsClone.length-2){
			 if (intsClone[i]==intsClone[i+1]){
				 total = total +=removeLen;
				 ints[j] = intsClone[i];
				 total += 1;
			 }else{
				 total = total += removeLen;
				 ints[j]= intsClone[i];
				 ints[j+1]= intsClone[i+1];
			 }
			 break; //break the for loop!
		 }
		 if (intsClone[i] == intsClone[i+1]){
			 removeLen++; 
		 }else{
			 ints[j] = intsClone[i];
			 j++;
			 total += removeLen;
			 removeLen =0;
		 }
	  }
	  for (int i=ints.length-1;i>ints.length-1-total;i--){
		  ints[i] = -1;
	  }
  }

  /**
   *  stringInts() converts an array of ints to a String.
   *  @return a String representation of the array.
   **/

  private static String stringInts(int[] ints) {
    String s = "[  ";
    for (int i = 0; i < ints.length; i++) {
      s = s + Integer.toString(ints[i]) + "  ";
    }
    return s + "]";
  }

  /**
   *  main() runs test cases on your smoosh and squish methods.  Prints summary
   *  information on basic operations and halts with an error (and a stack
   *  trace) if any of the tests fail.
   **/

  public static void main(String[] args) {
    String result;
    int i;


    System.out.println("Let's smoosh arrays!\n");

    int[] test1 = {3, 7, 7, 7, 4, 5, 5, 2, 0, 8, 8, 8, 8, 5};
    System.out.println("smooshing " + stringInts(test1) + ":");
    smoosh(test1);
    result = stringInts(test1);
    System.out.println(result);
    TestHelper.verify(result.equals(
            "[  3  7  4  5  2  0  8  5  -1  -1  -1  -1  -1  -1  ]"),
                      "BAD SMOOSH!!!  No cookie.");

    int[] test2 = {6, 6, 6, 6, 6, 3, 6, 3, 6, 3, 3, 3, 3, 3, 3};
    System.out.println("smooshing " + stringInts(test2) + ":");
    smoosh(test2);
    result = stringInts(test2);
    System.out.println(result);
    TestHelper.verify(result.equals(
            "[  6  3  6  3  6  3  -1  -1  -1  -1  -1  -1  -1  -1  -1  ]"),
                      "BAD SMOOSH!!!  No cookie.");

    int[] test3 = {4, 4, 4, 4, 4};
    System.out.println("smooshing " + stringInts(test3) + ":");
    smoosh(test3);
    result = stringInts(test3);
    System.out.println(result);
    TestHelper.verify(result.equals("[  4  -1  -1  -1  -1  ]"),
                      "BAD SMOOSH!!!  No cookie.");

    int[] test4 = {0, 1, 2, 3, 4, 5, 6};
    System.out.println("smooshing " + stringInts(test4) + ":");
    smoosh(test4);
    result = stringInts(test4);
    System.out.println(result);
    TestHelper.verify(result.equals("[  0  1  2  3  4  5  6  ]"),
                      "BAD SMOOSH!!!  No cookie.");
    
    int[] testa = {2, 3, 3, 3, 5, 7, 7, 7, 7, 3, 3, 3};
    System.out.println("smooshing " + stringInts(testa) + ":");
    smoosh(testa);
    result = stringInts(testa);
    System.out.println(result);
    TestHelper.verify(result.equals("[  2  3  5  7  3  -1  -1  -1  -1  -1  -1  -1  ]"),
                      "BAD SMOOSH!!!  No cookie.");
    int[] testaa = {5, 5};
    System.out.println("smooshing " + stringInts(testaa) + ":");
    smoosh(testaa);
    result = stringInts(testaa);
    System.out.println(result);
    TestHelper.verify(result.equals("[  5  -1  ]"),
                      "BAD SMOOSH!!!  No cookie.");
    int[] testaaa = {1, 2, 2, 2, 3, 3, 4};
    System.out.println("smooshing " + stringInts(testaaa) + ":");
    smoosh(testaaa);
    result = stringInts(testaaa);
    System.out.println(result);
    TestHelper.verify(result.equals("[  1  2  3  4  -1  -1  -1  ]"),
                      "BAD SMOOSH!!!  No cookie.");
    int[] testaaaa = {};
    System.out.println("smooshing " + stringInts(testaaaa) + ":");
    smoosh(testaaaa);
    result = stringInts(testaaaa);
    System.out.println(result);
    TestHelper.verify(result.equals("[  ]"),
                      "BAD SMOOSH!!!  No cookie.");
    int[] testaaaaa = {0};
    System.out.println("smooshing " + stringInts(testaaaaa) + ":");
    smoosh(testaaaaa);
    result = stringInts(testaaaaa);
    System.out.println(result);
    TestHelper.verify(result.equals("[  0  ]"),
                      "BAD SMOOSH!!!  No cookie.");
    int[] testab = {0, 0, 0, 0, 1, 1, 0, 0, 0, 3, 3, 3, 1, 1, 0};
    System.out.println("smooshing " + stringInts(testab) + ":");
    smoosh(testab);
    result = stringInts(testab);
    System.out.println(result);
    TestHelper.verify(result.equals("[  0  1  0  3  1  0  -1  -1  -1  -1  -1  -1  -1  -1  -1  ]"),
                      "BAD SMOOSH!!!  No cookie.");
    int[] testab1 = {3, 3, 3, 6, 3, 3, 6, 3, 3, 1, 2, 2, 3, 2};
    System.out.println("smooshing " + stringInts(testab1) + ":");
    smoosh(testab1);
    result = stringInts(testab1);
    System.out.println(result);
    TestHelper.verify(result.equals("[  3  6  3  6  3  1  2  3  2  -1  -1  -1  -1  -1  ]"),
                      "BAD SMOOSH!!!  No cookie.");
    int[] testab2 = {2, 3, 3, 2, 2};
    System.out.println("smooshing " + stringInts(testab2) + ":");
    smoosh(testab2);
    result = stringInts(testab2);
    System.out.println(result);
    TestHelper.verify(result.equals("[  2  3  2  -1  -1  ]"),
                      "BAD SMOOSH!!!  No cookie.");
    int[] testab3 = {1, 3, 5, 7, 9, 1, 3, 5, 7, 9};
    System.out.println("smooshing " + stringInts(testab3) + ":");
    smoosh(testab3);
    result = stringInts(testab3);
    System.out.println(result);
    TestHelper.verify(result.equals("[  1  3  5  7  9  1  3  5  7  9  ]"),
                      "BAD SMOOSH!!!  No cookie.");
    int[] testab4 = {1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1};
    System.out.println("smooshing " + stringInts(testab4) + ":");
    smoosh(testab4);
    result = stringInts(testab4);
    System.out.println(result);
    TestHelper.verify(result.equals("[  1  2  1  2  1  -1  -1  -1  -1  -1  -1  -1  -1  -1  -1  -1  ]"),
                      "BAD SMOOSH!!!  No cookie.");
    int[] testab5 = {8, 7, 6, 5, 4, 3, 2, 1, 0, 0};
    System.out.println("smooshing " + stringInts(testab5) + ":");
    smoosh(testab5);
    result = stringInts(testab5);
    System.out.println(result);
    TestHelper.verify(result.equals("[  8  7  6  5  4  3  2  1  0  -1  ]"),
                      "BAD SMOOSH!!!  No cookie.");

    System.out.println("\nLet's squish linked lists!\n");

    int[] test5 = {3, 7, 7, 7, 4, 5, 5, 2, 0, 8, 8, 8, 8, 5};
    SList list5 = new SList();
    for (i = 0; i < test5.length; i++) {
      list5.insertEnd(new Integer(test5[i]));
    }
    System.out.println("squishing " + list5.toString() + ":");
    list5.squish();
    result = list5.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  3  7  4  5  2  0  8  5  ]"),
                      "BAD SQUISH!!!  No biscuit.");

    int[] test6 = {6, 6, 6, 6, 6, 3, 6, 3, 6, 3, 3, 3, 3, 3, 3};
    SList list6 = new SList();
    for (i = 0; i < test6.length; i++) {
      list6.insertEnd(new Integer(test6[i]));
    }
    System.out.println("squishing " + list6.toString() + ":");
    list6.squish();
    result = list6.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  6  3  6  3  6  3  ]"),
                      "BAD SQUISH!!!  No biscuit.");

    int[] test7 = {4, 4, 4, 4, 4};
    SList list7 = new SList();
    for (i = 0; i < test7.length; i++) {
      list7.insertEnd(new Integer(test7[i]));
    }
    System.out.println("squishing " + list7.toString() + ":");
    list7.squish();
    result = list7.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  4  ]"),
                      "BAD SQUISH!!!  No biscuit.");

    int[] test8 = {0, 1, 2, 3, 4, 5, 6};
    SList list8 = new SList();
    for (i = 0; i < test8.length; i++) {
      list8.insertEnd(new Integer(test8[i]));
    }
    System.out.println("squishing " + list8.toString() + ":");
    list8.squish();
    result = list8.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  0  1  2  3  4  5  6  ]"),
                      "BAD SQUISH!!!  No biscuit.");

    SList list9 = new SList();
    System.out.println("squishing " + list9.toString() + ":");
    list9.squish();
    result = list9.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  ]"),
                      "BAD SQUISH!!!  No biscuit.");
    
    int[] testt1 = {2, 2, 3, 3, 3, 6, 9, 9};
    SList listt1 = new SList();
    for (i = 0; i < testt1.length; i++) {
      listt1.insertEnd(new Integer(testt1[i]));
    }
    System.out.println("squishing " + listt1.toString() + ":");
    listt1.squish();
    result = listt1.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  2  3  6  9  ]"),
                      "BAD SQUISH!!!  No biscuit.");
    
    int[] testt2 = {6, 6, 5, 6, 6};
    SList listt2 = new SList();
    for (i = 0; i < testt2.length; i++) {
      listt2.insertEnd(new Integer(testt2[i]));
    }
    System.out.println("squishing " + listt2.toString() + ":");
    listt2.squish();
    result = listt2.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  6  5  6  ]"),
                      "BAD SQUISH!!!  No biscuit.");
    
    int[] testt3 = {1, 7, 9, 10, 10, 10, 10, 10, 10, 1};
    SList listt3 = new SList();
    for (i = 0; i < testt3.length; i++) {
      listt3.insertEnd(new Integer(testt3[i]));
    }
    System.out.println("squishing " + listt3.toString() + ":");
    listt3.squish();
    result = listt3.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  1  7  9  10  1  ]"),
                      "BAD SQUISH!!!  No biscuit.");
    int[] testt4 = {2, 8, 2, 8, 2, 2, 8, 8, 6, 6, 3};
    SList listt4 = new SList();
    for (i = 0; i < testt4.length; i++) {
      listt4.insertEnd(new Integer(testt4[i]));
    }
    System.out.println("squishing " + listt4.toString() + ":");
    listt4.squish();
    result = listt4.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  2  8  2  8  2  8  6  3  ]"),
                      "BAD SQUISH!!!  No biscuit.");
    
    int[] testt5 = {0};
    SList listt5 = new SList();
    for (i = 0; i < testt5.length; i++) {
      listt5.insertEnd(new Integer(testt5[i]));
    }
    System.out.println("squishing " + listt5.toString() + ":");
    listt5.squish();
    result = listt5.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  0  ]"),
                      "BAD SQUISH!!!  No biscuit.");
    int[] testt6 = {2, 3, 9, 3, 9, 8, 8, 9};
    SList listt6 = new SList();
    for (i = 0; i < testt6.length; i++) {
      listt6.insertEnd(new Integer(testt6[i]));
    }
    System.out.println("squishing " + listt6.toString() + ":");
    listt6.squish();
    result = listt6.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  2  3  9  3  9  8  9  ]"),
                      "BAD SQUISH!!!  No biscuit.");
    int[] testt7 = {2, 8, 8, 2, 5, 2, 5, 2};
    SList listt7 = new SList();
    for (i = 0; i < testt7.length; i++) {
      listt7.insertEnd(new Integer(testt7[i]));
    }
    System.out.println("squishing " + listt7.toString() + ":");
    listt7.squish();
    result = listt7.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  2  8  2  5  2  5  2  ]"),
                      "BAD SQUISH!!!  No biscuit.");
    int[] testt8 = {0, 0, 0, 0, 1, 1, 0, 0, 0, 3, 3, 3, 1, 1, 0};
    SList listt8 = new SList();
    for (i = 0; i < testt8.length; i++) {
      listt8.insertEnd(new Integer(testt8[i]));
    }
    System.out.println("squishing " + listt8.toString() + ":");
    listt8.squish();
    result = listt8.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  0  1  0  3  1  0  ]"),
                      "BAD SQUISH!!!  No biscuit.");
    
    System.out.println("\nLet's twin linked lists!\n");

    System.out.println("twinning " + list6.toString() + ":");
    list6.twin();
    result = list6.toString();
    System.out.println(result);
    TestHelper.verify(result.equals(
                      "[  6  6  3  3  6  6  3  3  6  6  3  3  ]"),
                      "BAD TWIN!!!  No gravy.");

    System.out.println("twinning " + list7.toString() + ":");
    list7.twin();
    result = list7.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  4  4  ]"),
                      "BAD TWIN!!!  No gravy.");

    System.out.println("twinning " + list9.toString() + ":");
    list9.twin();
    result = list9.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  ]"),
                      "BAD TWIN!!!  No gravy.");
    
    System.out.println("twinning " + listt1.toString() + ":");
    listt1.twin();
    result = listt1.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  2  2  3  3  6  6  9  9  ]"),
                      "BAD TWIN!!!  No gravy.");
    
    int[] tests1 = {1, 1, 8, 8, 8, 2};
    SList lists1 = new SList();
    for (i = 0; i < tests1.length; i++) {
        lists1.insertEnd(new Integer(tests1[i]));
      }
    System.out.println("twinning " + lists1.toString() + ":");
    lists1.twin();
    result = lists1.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  1  1  1  1  8  8  8  8  8  8  2  2  ]"),
                      "BAD TWIN!!!  No gravy.");
    
    int[] tests2 = {1, 1, 3, 5, 7, 7};
    SList lists2 = new SList();
    for (i = 0; i < tests1.length; i++) {
        lists2.insertEnd(new Integer(tests2[i]));
      }
    System.out.println("twinning " + lists2.toString() + ":");
    lists2.twin();
    result = lists2.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  1  1  1  1  3  3  5  5  7  7  7  7  ]"),
                      "BAD TWIN!!!  No gravy.");
    
    int[] tests3 = {8, 8, 8, 8, 8, 8, 8, 8};
    SList lists3 = new SList();
    for (i = 0; i < tests3.length; i++) {
        lists3.insertEnd(new Integer(tests3[i]));
      }
    System.out.println("twinning " + lists3.toString() + ":");
    lists3.twin();
    result = lists3.toString();
    System.out.println(result);
    TestHelper.verify(result.equals("[  8  8  8  8  8  8  8  8  8  8  8  8  8  8  8  8  ]"),
                      "BAD TWIN!!!  No gravy.");
    
  }

}
