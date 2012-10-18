/* HashTableChained.java */

package dict;
import list.*;
/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
	protected List[] hashTable; //a list array of tables
	protected int buckets; //number of hash table buckets
	protected int sizeEstimate;


  public HashTableChained(){
	  hashTable = new DList[101];
	  buckets = 0;
	  this.sizeEstimate = 101;
  }
  
 
  

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
	  int newcode = ((7*code + 11)% 931307893) % this.sizeEstimate;
	  return Math.abs(newcode);
	  
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
	  return buckets;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
	  return buckets == 0 ;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
	  resizeHashTable();
	  Entry entry = new Entry();
	  entry.key = key;
	  entry.value = value;
	  int bucket = compFunction(key.hashCode());
	  arrayInsertion(bucket, entry);
	  this.buckets++;
	  return entry;
  }
  private void arrayInsertion(int bucket, Entry entry){
	  bucket = Math.abs(bucket); //Sometimes the bucket may be negative!!!!!]
	  if (this.hashTable[bucket] == null){
		  this.hashTable[bucket] = new DList();
		  this.hashTable[bucket].insertFront(entry);
	  }else{
		  this.hashTable[bucket].insertFront(entry);
	  }
  }
  public List[] getHashTable(){
	  List[] hashTable2 = new DList[sizeEstimate];
	  hashTable2 = hashTable.clone();
	  return hashTable2;
  }
  
  
  /**
   * resizeHashTable is used to resize(enlarge) the current hash table if the load factor exceeds 0.75
   */
  private void resizeHashTable(){
	  double n = buckets;
	  double N = sizeEstimate;
	  int NN = sizeEstimate;
	  if (n/N >=0.75){
		  List[] hashTable2 = new DList[sizeEstimate];
		  hashTable2 = hashTable.clone();
		  hashTable = new DList[sizeEstimate*2];
		  this.sizeEstimate = sizeEstimate*2;
		  int a = 0;
		  while(a<NN){
			  if (hashTable2[a] != null){
				  DListNode node = (DListNode) hashTable2[a].front();
				  try {
				  while(true){
				  Entry entry = (Entry) node.item();
				  int bucket = compFunction(entry.key.hashCode());
				  arrayInsertion(bucket, entry);
				  node = (DListNode) node.next();
				  }
				 } catch (InvalidNodeException e) {
						
				}
			  }
			  a++;
		  }
		  
	  }
	  
  }
 

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
	  int bucket = compFunction(key.hashCode());
	  try {
		  DListNode node = (DListNode) this.hashTable[bucket].front();
		  while(true){
			  if (((Entry) node.item()).key().equals(key)){
				  return (Entry) node.item();
			  }
			  node = (DListNode) node.next();
		  }
	} catch (InvalidNodeException e) {
		return null;
	} catch (NullPointerException e) {
		return null; 
	}
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
	  int bucket = compFunction(key.hashCode());
	  try {
		  DListNode node = (DListNode) this.hashTable[bucket].front();
		  while(true){
			  if (((Entry) node.item()).key().equals(key)){
				  Entry entry = (Entry) node.item();
				  node.remove();
				  this.buckets--;
				  return entry;
			  }
			  
			  node = (DListNode) node.next();
		  }
	} catch (InvalidNodeException e) {
		return null;
	} catch (NullPointerException e) {
		return null; 
	}
	
	  
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
	  this.hashTable = new DList[hashTable.length];
	  buckets = 0;
  }
  public void collisions(){
	  System.out.println("Number of hashTable buckets is : "+ buckets);
	  int collisions =0;
	  for (int i =0; i<hashTable.length ; i++){
		  if (!(hashTable[i]==null)){
			  collisions = collisions+ hashTable[i].length()-1;
		  }
	  }
	  System.out.println("Number of collisions is :"+collisions);
	  
  }
  
  /*
   * Main function used to test the hashTable
   */
  public static void main(String[] args){
	  HashTableChained hashTable = new HashTableChained();
	  hashTable.insert("MICROSOFT", "WINDOWSPHONE");
	  hashTable.insert("GOOGLE", "ANDROID");
	  hashTable.insert("APPLE", "IOS");
	  hashTable.insert("HP", "WebOS");
	  hashTable.insert("AMAZON", "Kindle");
	  hashTable.insert("BLACKBERRY", "BLACKBERRYOS");
	  hashTable.insert("ORACLE", "DB2");
	  System.out.println(hashTable.size());
	  System.out.println(hashTable.hashTable.length);
	  System.out.println(hashTable.find("BLACKBERRY").value);
	  System.out.println(hashTable.find("AMAZON").value);
	  System.out.println(hashTable.find("YAHOO"));
	  System.out.println(hashTable.find("HP").value);
	  System.out.println(hashTable.find("MICROSOFT").value);
	  System.out.println(hashTable.find("GOOGLE").value);
	  System.out.println(hashTable.find("APPLE").value);
	  System.out.println(hashTable.find("ORACLE").value);
	  
	  
	  
	  
	  
	  
	  
	 
  }
}
