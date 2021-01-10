package edu.iastate.cs228.hw2;


import java.util.Comparator;


/**
 * An implementation of {@link Sorter} that performs insertion sort
 * to sort the list.
 * 
 * @author emmanuel paz
 */
public class InsertionSorter extends Sorter
{
    /**
     * this method implements InsertionSort to sort the list of words
     * @param toSort
     *   the list to sort
     * @param comp
     *   the comparator to use to compare elements of the list
     * @throws NullPointerException
     */
  @Override
  public void sort(WordList toSort, Comparator<String> comp) throws NullPointerException
  {
      int j=0;
      for(int i = 1; i < toSort.length(); i++){
          j = i;
        while(j > 0 && comp.compare(toSort.get(j), toSort.get(j-1)) < 0){
          toSort.swap(j, j-1);
          j--;
        }
      }
  }
}
