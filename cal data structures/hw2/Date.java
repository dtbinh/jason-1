/* Date.java */

import java.io.*;

class Date {

  /* Put your private data fields here. */
	private int month;
	private int day;
	private int year;

  /** Constructs a date with the given month, day and year.   If the date is
   *  not valid, the entire program will halt with an error message.
   *  @param month is a month, numbered in the range 1...12.
   *  @param day is between 1 and the number of days in the given month.
   *  @param year is the year in question, with no digits omitted.
   */
  public Date(int month, int day, int year) {
	  if (!isValidDate(month, day, year)){
		  System.out.println("Error!, Date is not valid.");
		  System.exit(0);
	  }else{
		 this.month = month;
		 this.day = day;
		 this.year = year;
		  
	  }
  }

  /** Constructs a Date object corresponding to the given string.
   *  @param s should be a string of the form "month/day/year" where month must
   *  be one or two digits, day must be one or two digits, and year must be
   *  between 1 and 4 digits.  If s does not match these requirements or is not
   *  a valid date, the program halts with an error message.
   */
  public Date(String s) {
	  this(Month(s), Day(s),Year(s));
	
	  

  }
  

private static int Year(String s) {
	int year = 10000;
	try{
		if (s.charAt(1)=='/'){
			if(s.charAt(3)=='/'){
			year = Integer.parseInt(s.substring(4));
			}
			else if (s.charAt(4) == '/'){
			year = Integer.parseInt(s.substring(5));
			}
		}
		else if (s.charAt(2) =='/'){
			if (s.charAt(4) =='/'){
			year = Integer.parseInt(s.substring(5));
			}
			else if(s.charAt(5)== '/'){
			year= Integer.parseInt(s.substring(6));
			}	
			
		}		
	if (year > 9999){
		System.out.println("Error!, Date is not valid.");
		System.exit(0);
	}
	return year;
	}catch(Exception e){
		System.out.println("Error!, Date is not valid.");
		System.exit(0);
	}
	return -1;
}

private static int Day(String s){
	  int day;
	  try{
		  if (s.charAt(1) == '/'){
			  if (s.charAt(3) == '/'){
				  day = Integer.parseInt(s.substring(2, 3));
				  return day;
			  }
			  else if (s.charAt(4) == '/'){
				  day = Integer.parseInt(s.substring(2, 4));
				  return day;
			  }
		  }else if (s.charAt(2) == '/'){
			  if (s.charAt(4) == '/'){
				  day = Integer.parseInt(s.substring(3, 4));
				  return day;
			  }
			  else if (s.charAt(5) == '/'){
				  day = Integer.parseInt(s.substring(3, 5));
				  return day;
			  }
		  }
	  }catch (Exception e){
		  System.out.println("Error!, Date is not valid.");
		  System.exit(0);
	  }
	  
	  return -1;
  }
  
  
  private static int Month(String s){
	  int month;
	  if (s.length() < 5){
		  System.out.println("Error!, Date is not valid.");
		  System.exit(0);
	  }
	  try{
		  if (s.charAt(1) == '/'){
			  month = Integer.parseInt(s.substring(0, 1));
			  return month;
		  }
		  else if (s.charAt(2) == '/'){
			  month = Integer.parseInt(s.substring(0, 2));
			  return month;
		  }
		  
	  }catch(Exception e){
		  System.out.println("Error!, Date is not valid.");
		  System.exit(0);
	  }
	  return -1;
  }
 
  
  /** Checks whether the given year is a leap year.
   *  @return true if and only if the input year is a leap year.
   */
  public static boolean isLeapYear(int year) {
    if (year % 4 == 0){
    	if (year %100 == 0){
    		if (year % 400 ==0){
    			return true;
    		}
    		return false;
    		
    	}
    	return true;
    	
    }
    else{
    	return false;
    }
  }

  /** Returns the number of days in a given month.
   *  @param month is a month, numbered in the range 1...12.
   *  @param year is the year in question, with no digits omitted.
   *  @return the number of days in the given month.
   */
  public static int daysInMonth(int month, int year) {
    switch(month){
    case 1: return 31;
    case 2: if (isLeapYear(year)){
    	return 29;
    }else{
    	return 28;
    }
    case 3: return 31;
    case 4: return 30;
    case 5: return 31;
    case 6: return 30;
    case 7: return 31;
    case 8: return 31;
    case 9: return 30;
    case 10: return 31;
    case 11: return 30;
    case 12: return 31;
    default: return -1;
    
    
    }
  }

  /** Checks whether the given date is valid.
   *  @return true if and only if month/day/year constitute a valid date.
   *
   *  Years prior to A.D. 1 are NOT valid.
   */
  public static boolean isValidDate(int month, int day, int year) {
	  if (year <1){
		  return false;
	  }
	  else{
		  int a = daysInMonth(month, year);
		  if (day>a || day <1){
			  return false;
		  }
		  return true;
		  
	  }
  }

  /** Returns a string representation of this date in the form month/day/year.
   *  The month, day, and year are expressed in full as integers; for example,
   *  12/7/2006 or 3/21/407.
   *  @return a String representation of this date.
   */
  public String toString() {
    return month + "/" + day + "/" + year ;
  }

  /** Determines whether this Date is before the Date d.
   *  @return true if and only if this Date is before d. 
   */
  public boolean isBefore(Date d) {
    if (this.year < d.year){
    	return true;
    }
    else if (this.year > d.year){
    	return false;
    }
    else{
    	if (this.month < d.month){
    		return true;
    	}
    	else if (this.month > d.month){
    		return false;
    	}
    	else{
    		if (this.day<d.day){
    			return true;
    		}
    		else if(this.day>d.day){
    			return false;
    		}
    		else{
    			return false;
    		}
    		
    	}
    }
  }

  /** Determines whether this Date is after the Date d.
   *  @return true if and only if this Date is after d. 
   */
  public boolean isAfter(Date d) {
    	if (this.month == d.month && this.day == d.day && this.year==d.year){
    		return false;
    	}
    	return !isBefore(d);
  }

  /** Returns the number of this Date in the year.
   *  @return a number n in the range 1...366, inclusive, such that this Date
   *  is the nth day of its year.  (366 is used only for December 31 in a leap
   *  year.)
   */
  public int dayInYear() {
	  	int date = 0;
		switch(this.month){
	    case 1: return this.day;
	    case 2: for(int i=1;i<this.month;i++){date = date + daysInMonth(i, this.year);}
	    return date + this.day;
	    case 3: for(int i=1;i<this.month;i++){date = date + daysInMonth(i, this.year);}
	    return date + this.day;
	    case 4: for(int i=1;i<this.month;i++){date = date + daysInMonth(i, this.year);}
	    return date + this.day;
	    case 5: for(int i=1;i<this.month;i++){date = date + daysInMonth(i, this.year);}
	    return date + this.day;
	    case 6: for(int i=1;i<this.month;i++){date = date + daysInMonth(i, this.year);}
	    return date + this.day;
	    case 7: for(int i=1;i<this.month;i++){date = date + daysInMonth(i, this.year);}
	    return date + this.day;
	    case 8: for(int i=1;i<this.month;i++){date = date + daysInMonth(i, this.year);}
	    return date + this.day;
	    case 9: for(int i=1;i<this.month;i++){date = date + daysInMonth(i, this.year);}
	    return date + this.day;
	    case 10: for(int i=1;i<this.month;i++){date = date + daysInMonth(i, this.year);}
	    return date + this.day;
	    case 11: for(int i=1;i<this.month;i++){date = date + daysInMonth(i, this.year);}
	    return date + this.day;
	    case 12: for(int i=1;i<this.month;i++){date = date + daysInMonth(i, this.year);}
	    return date + this.day;
	    default: return -1;
		}
	
    
    
  }

  /** Determines the difference in days between d and this Date.  For example,
   *  if this Date is 12/15/2012 and d is 12/14/2012, the difference is 1.
   *  If this Date occurs before d, the result is negative.
   *  @return the difference in days between d and this date.
   */
  public int difference(Date d) {
	int temp =0;
    	if (this.year == d.year){
    		return this.dayInYear()- d.dayInYear();
    	}
    	else if(this.isAfter(d)){
    		if (isLeapYear(d.year)){
    			temp = 366-d.dayInYear();
    		}else{
    			temp = 365-d.dayInYear();
    		}
    		for (int i=d.year+1; i<this.year; i++){
    			if (isLeapYear(i)){
    				temp = temp + 366;
    			}
    			else{
    				temp = temp + 365;
    			}
    		}
    		return temp + this.dayInYear();
    	}
    	else{
    		if (isLeapYear(this.year)){
    			temp = 366-this.dayInYear();
    		}else{
    			temp = 365-this.dayInYear();
    		}
    		for (int i=this.year+1; i<d.year; i++){
    			if (isLeapYear(i)){
    				temp = temp +366;
    			}
    			else{
    				temp = temp +365;
    			}
    		}
    		
  
    		return -(temp + d.dayInYear());
    	}
		
    
  }

  public static void main(String[] argv) {
	  /* System.out.println(isLeapYear(1600));
	  System.out.println(isLeapYear(2000));
	  System.out.println(isLeapYear(1800));
	  System.out.println(isLeapYear(1900));
	  System.out.println(isLeapYear(104));
	  System.out.println(isLeapYear(105));
	  
	  System.out.println(daysInMonth(1, 2012));
	  System.out.println(daysInMonth(2, 2012));
	  System.out.println(daysInMonth(3, 2012));
	  System.out.println(daysInMonth(4, 2012));
	  System.out.println(daysInMonth(5, 2012));
	  System.out.println(daysInMonth(6, 2012));
	  System.out.println(daysInMonth(7, 2012));
	  System.out.println(daysInMonth(8, 2012));
	  System.out.println(daysInMonth(9, 2012));
	  System.out.println(daysInMonth(10, 2012));
	  System.out.println(daysInMonth(11, 2012));
	  System.out.println(daysInMonth(12, 2012));
	  System.out.println(daysInMonth(2, 2013));
	  System.out.println(daysInMonth(2, 2016));
	  System.out.println(daysInMonth(2, 2000));
	  System.out.println(daysInMonth(2, 2100));
	  
	  System.out.println(isValidDate(2, 29, 2012));
	  System.out.println(isValidDate(1, 30, 2012));
	  System.out.println(isValidDate(3, 31, 2012));
	  System.out.println(isValidDate(4, 31, 2012));
	  System.out.println(isValidDate(5, 31, 2012));
	  System.out.println(isValidDate(6, 31, 2012));
	  System.out.println(isValidDate(7, 31, 2012));
	  System.out.println(isValidDate(8, 31, 2012));
	  System.out.println(isValidDate(9, 31, 2012));
	  System.out.println(isValidDate(10, 31, 2012));
	  System.out.println(isValidDate(11, 31, 2012));
	  System.out.println(isValidDate(12, 31, 2012));
	  System.out.println(isValidDate(12, 31, -10));
	  System.out.println(isValidDate(2, 29, 2013));
	  System.out.println(isValidDate(6, 30, 2013));
	  System.out.println(isValidDate(12, 25, 2013));
	  System.out.println(isValidDate(8, 8, 2013));
	  System.out.println(isValidDate(-1, 8, 2013));
	  System.out.println(isValidDate(6, 0, 2013));
	*/  
	 
    System.out.println("\nTesting constructors.");
    Date d1 = new Date(1, 1, 1);
    System.out.println("Date should be 1/1/1: " + d1);
    d1 = new Date(2, 10, 2009);
    System.out.println("Date should be 2/10/2009: " + d1);
    d1 = new Date(2, 29, 2012);
    System.out.println("Date should be 2/29/2012: " + d1);
    d1 = new Date(11, 29, 2011);
    System.out.println("Date should be 11/29/2011: " + d1);
    //d1 = new Date(2, 29, 2011);
    //System.out.println("Date should be Error: " + d1);
    
	//Date t1 = new Date("hey dude");
	//System.out.println("Date should be Error: " + t1);
	//Date t1 = new Date("11/31/2009");
	//System.out.println("Date should be Error: " + t1);
	//Date t1 = new Date("12/4");
	//System.out.println("Date should be Error: " + t1);
	//Date t1 = new Date("   11/4/2010 AD");
	//System.out.println("Date should be Error: " + t1);
	//Date t1 = new Date("139/2/3001");
	//System.out.println("Date should be Error: " + t1);
	//Date t1 = new Date("13/55/2001");
	//System.out.println("Date should be Error: " + t1);
	Date t1 = new Date("12/25/20011");
	System.out.println("Date should be Error: " + t1);
	//Date t1 = new Date("12/ch/2001");
	//System.out.println("Date should be Error: " + t1);
	//Date t1 = new Date("ec/ab/2011");
	//System.out.println("Date should be Error: " + t1);  
	//Date t1 = new Date("11/30");
	//System.out.println("Date should be Error: " + t1); 
	//Date t1 = new Date("5/30/AD20");
	//System.out.println("Date should be Error: " + t1); 
	//Date t1 = new Date("13/06/2006");
	//System.out.println("Date should be Error: " + t1); 
    //Date t1 = new Date("6/-6/2001");
    //System.out.println("Date should be Error: " + t1); 
    //Date t1 = new Date("Ja/5/2010");
    //System.out.println("Date should be Error: " + t1); 
    //Date t1 = new Date("Holy Cow! Holy Cow! Holy Cow! Holy Cow! Holy Cow!");
    //System.out.println("Date should be Error: " + t1); 
    //Date t1 = new Date("/5/2000");
    //System.out.println("Date should be Error: " + t1);
    //Date t1 = new Date("06//2009");
    //System.out.println("Date should be Error: " + t1);
    //Date t1 = new Date("02/29/2001");
    //System.out.println("Date should be Error: " + t1);
    //Date t1 = new Date("2/29/1800");
    //System.out.println("Date should be Error: " + t1);
    //Date t1 = new Date("006/4/0002");
    //System.out.println("Date should be Error: " + t1);
	  Date t2 = new Date("9/06/2006");
	  System.out.println("Date should be 9/6/2006 " + t2); 
	  t2 = new Date("01/01/2012");
	  System.out.println("Date should be 1/1/2012: " + t2);
	  t2 = new Date("03/5/2013");
	  System.out.println("Date should be 3/5/2013: " + t2);
	  
	 
    d1 = new Date("2/4/2");
    System.out.println("Date should be 2/4/2: " + d1);
    d1 = new Date("2/29/2000");
    System.out.println("Date should be 2/29/2000: " + d1);
    d1 = new Date("2/29/1904");
    System.out.println("Date should be 2/29/1904: " + d1);
    
    d1 = new Date(12, 31, 1975);
    System.out.println("Date should be 12/31/1975: " + d1);
    Date d2 = new Date("1/1/1976");
    System.out.println("Date should be 1/1/1976: " + d2);
    Date d3 = new Date("1/2/1976");
    System.out.println("Date should be 1/2/1976: " + d3);
	
	
    
    Date d4 = new Date("2/27/1977");
    Date d5 = new Date("8/31/2110");

    /* I recommend you write code to test the isLeapYear function! */

    System.out.println("\nTesting before and after.");
    System.out.println(d2 + " after " + d1 + " should be true: " + 
                       d2.isAfter(d1));
    System.out.println(d3 + " after " + d2 + " should be true: " + 
                       d3.isAfter(d2));
    System.out.println(d1 + " after " + d1 + " should be false: " + 
                       d1.isAfter(d1));
    System.out.println(d1 + " after " + d2 + " should be false: " + 
                       d1.isAfter(d2));
    System.out.println(d2 + " after " + d3 + " should be false: " + 
                       d2.isAfter(d3));

    System.out.println(d1 + " before " + d2 + " should be true: " + 
                       d1.isBefore(d2));
    System.out.println(d2 + " before " + d3 + " should be true: " + 
                       d2.isBefore(d3));
    System.out.println(d1 + " before " + d1 + " should be false: " + 
                       d1.isBefore(d1));
    System.out.println(d2 + " before " + d1 + " should be false: " + 
                       d2.isBefore(d1));
    System.out.println(d3 + " before " + d2 + " should be false: " + 
                       d3.isBefore(d2));
    
    
    
    
    System.out.println("\nTesting difference.");
    System.out.println(d1 + " - " + d1  + " should be 0: " + 
                       d1.difference(d1));
    System.out.println(d2 + " - " + d1  + " should be 1: " + 
                       d2.difference(d1));
    System.out.println(d3 + " - " + d1  + " should be 2: " + 
                       d3.difference(d1));
    System.out.println(d3 + " - " + d4  + " should be -422: " + 
                       d3.difference(d4));
    System.out.println(d5 + " - " + d4  + " should be 48762: " + 
                       d5.difference(d4));
    Date a1 = new Date("12/10/1919");
    Date a2 = new Date("12/10/2012");
    System.out.println(a1 + " - " + a2 + " should be -33969: " + a1.difference(a2));
    System.out.println(a2 + " - " + a1 + " should be 33969: " + a2.difference(a1));
    a1 = new Date("5/20/2000");
    a2 = new Date("5/20/2008");
    System.out.println(a2 + " - " + a1 + " should be 2922: " + a2.difference(a1));
    a1 = new Date("3/20/2012");
    a2 = new Date("2/1/2012");
    System.out.println(a2 + " - " + a1 + " should be -48: " + a2.difference(a1));
    a1 = new Date("6/17/2109");
    a2 = new Date("9/5/2002");
    System.out.println(a1 + " - " + a2 + " should be 39001: " + a1.difference(a2));
    a1 = new Date("3/26/1991");
    a2 = new Date("2/1/2012");
    System.out.println(a2 + " - " + a1 + " should be 7617: " + a2.difference(a1));
    System.out.println(a1 + " - " + a2 + " should be -7617: " + a1.difference(a2));
    a1 = new Date("12/31/2011");
    a2 = new Date("1/3/2012");
    System.out.println(a1 + " - " + a2 + " should be -3: " + a1.difference(a2));
    
    
    
    /* Tesing dayInYear() Method */
    System.out.println( "\nTesting dayInYear()");
    d1 = new Date("1/1/2010");
    System.out.println("Day in year should be 1: " + d1.dayInYear());
    d1 = new Date("2/1/2010");
    System.out.println("Day in year should be 32: " + d1.dayInYear());
    d1 = new Date("3/1/2010");
    System.out.println("Day in year should be 60: " + d1.dayInYear());
    d1 = new Date("4/20/2010");
    System.out.println("Day in year should be 110: " + d1.dayInYear());
    d1 = new Date("5/30/2010");
    System.out.println("Day in year should be 150: " + d1.dayInYear());
    d1 = new Date("6/22/2010");
    System.out.println("Day in year should be 173: " + d1.dayInYear());
    d1 = new Date("7/31/2010");
    System.out.println("Day in year should be 212: " + d1.dayInYear());
    d1 = new Date("8/1/2010");
    System.out.println("Day in year should be 213: " + d1.dayInYear());
    d1 = new Date("9/9/2010");
    System.out.println("Day in year should be 252: " + d1.dayInYear());
    d1 = new Date("10/17/2010");
    System.out.println("Day in year should be 290: " + d1.dayInYear());
    d1 = new Date("11/30/2010");
    System.out.println("Day in year should be 334: " + d1.dayInYear());
    d1 = new Date("12/31/2010");
    System.out.println("Day in year should be 365: " + d1.dayInYear());
    d1 = new Date("1/20/2012");
    System.out.println("Day in year should be 20: " + d1.dayInYear());
    d1 = new Date("2/29/2012");
    System.out.println("Day in year should be 60: " + d1.dayInYear());
    d1 = new Date("3/26/2012");
    System.out.println("Day in year should be 86: " + d1.dayInYear());
    d1 = new Date("4/16/2012");
    System.out.println("Day in year should be 107: " + d1.dayInYear());
    d1 = new Date("5/1/2012");
    System.out.println("Day in year should be 122: " + d1.dayInYear());
    d1 = new Date("6/15/2012");
    System.out.println("Day in year should be 167: " + d1.dayInYear());
    d1 = new Date("7/4/2012");
    System.out.println("Day in year should be 186: " + d1.dayInYear());
    d1 = new Date("8/31/2012");
    System.out.println("Day in year should be 244: " + d1.dayInYear());
    d1 = new Date("9/3/2012");
    System.out.println("Day in year should be 247: " + d1.dayInYear());
    d1 = new Date("10/10/2012");
    System.out.println("Day in year should be 284: " + d1.dayInYear());
    d1 = new Date("11/22/2012");
    System.out.println("Day in year should be 327: " + d1.dayInYear());
    d1 = new Date("12/31/2012");
    System.out.println("Day in year should be 366: " + d1.dayInYear());
    
  }
}
