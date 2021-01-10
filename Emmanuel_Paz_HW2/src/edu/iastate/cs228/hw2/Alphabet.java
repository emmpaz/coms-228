package edu.iastate.cs228.hw2;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * A class representing an ordering of characters that can be queried to know
 * the position of a given character.
 * 
 * @author emmanuel paz
 */
public class Alphabet
{
  /**
   * A lookup table containing characters and their positions.
   * Sorted by the character of each entry.
   */
  private CharAndPos[] lookup;


  /**
   * Constructs and initializes the ordering to have exactly the ordering of
   * the elements in the given array.
   * 
   * @param ordering
   *   the array containing the characters, in the ordering desired
   * @throws NullPointerException
   *   if {@code ordering} is {@code null}
   */
  public Alphabet(char[] ordering) throws NullPointerException {
    int i = 0;
    lookup = new CharAndPos[ordering.length];
    for(char character : ordering){
      lookup[i] = new CharAndPos(character, i);
      i++;
    }
    sortAscii(lookup);
  }

  /**
   * Constructs and initializes the ordering by reading from the indicated
   * file. The file is expected to have a single character on each line, and
   * the ordering in the file is the order that will be used.
   * 
   * @param filename
   *   the name of the file to read
   * @throws NullPointerException
   *   if {@code filename} is {@code null}
   * @throws FileNotFoundException
   *   if the file cannot be found
   */
  public Alphabet(String filename) throws NullPointerException, FileNotFoundException{
    File f = new File(filename);
    Scanner number = new Scanner(f);
    int j =0;
    while(number.hasNextLine()){
      String next = number.nextLine();
      j++;
    }
    number.close();
    lookup = new CharAndPos[j];
    Scanner scan = new Scanner(f);
    int i = 0;
    while(scan.hasNextLine()){
      lookup[i] = new CharAndPos(scan.nextLine().charAt(0), i);
      i++;
    }
    scan.close();
    sortAscii(lookup);

  }

  /**
   * this private method sorts lookup table by ascii but the objects inside keep its position
   * @param arr //the array to sort
   */
  private void sortAscii(CharAndPos[] arr){
    int smallest = 0;
    for(int i = 0; i < arr.length - 1; i++){
      smallest = i;

      for(int j = i + 1; j < arr.length; j++){
        if(arr[j].character < arr[smallest].character)
          smallest = j;
      }

      CharAndPos temp = arr[i];
      arr[i] = arr[smallest];
      arr[smallest] = temp;
    }
  }

  /**
   * Returns true if and only if the given character is present in the
   * ordering.
   * 
   * @param c
   *   the character to test
   * @return
   *   true if and only if the given character is present in the ordering
   */
  public boolean isValid(char c){
    return binarySearch(c) != -1;
  }

  /**
   * Returns the position of the given character in the ordering.
   * Returns a negative value if the given character is not present in the
   * ordering.
   * 
   * @param c
   *   the character of which the position will be determined
   * @return
   *   the position of the given character, or a negative value if the given
   *   character is not present in the ordering
   */
  public int getPosition(char c){
    return (binarySearch(c) == -1) ? -1 : lookup[binarySearch(c)].position;
  }

  /**
   * Returns the index of the entry containing the given character within the
   * lookup table {@link #lookup}.
   * Returns a negative value if the given character does not have an entry in
   * the table.
   * 
   * @param toFind
   *   the character for which to search
   * @return
   *   the index of the entry containing the given character, or a negative
   *   value if the given character does not have an entry in the table
   */
  private int binarySearch(char toFind){
    /*
     * note: for testing, you can perform a simple search through the array
     * instead of a binary search, allowing you to test other components with a
     * working (but slower) implementation
     */
    int size = lookup.length;
    int left = 0;
    int right = size - 1;
    while(left <= right){
      int mid = (right + left)/2;
      if(lookup[mid].character == toFind)
        return mid;
      if(toFind < lookup[mid].character)
        right = mid - 1;
      else
        left = mid + 1;
    }


    return -1;
  }


  /**
   * A PODT class containing a character and a position.
   * Used as the entry type within {@link Alphabet#lookup lookup}.
   */
  /* already completed */
  private static class CharAndPos{
    /**
     * The character of the entry.
     */
    public char character;

    /**
     * The position of the entry in the ordering.
     */
    public int position;


    /**
     * Constructs and initializes the entry with the given values.
     * 
     * @param character
     *   the character of the entry
     * @param position
     *   the position of the entry
     */
    public CharAndPos(char character, int position)
    {
      this.character = character;
      this.position = position;
    }


    @Override
    public boolean equals(Object obj)
    {
      if (null == obj || this.getClass() != obj.getClass())
      {
        return false;
      }

      CharAndPos o = (CharAndPos) obj;

      return this.character == o.character && this.position == o.position;
    }

    @Override
    public int hashCode(){
      return character ^ position;
    }

    @Override public String toString(){
      return "{" + character + ", " + position + "}";
    }
  }
}
