package edu.iastate.cs228.hw2;


import java.util.Comparator;


/**
 * An implementation of {@link Sorter} that performs quick sort
 * to sort the list.
 * 
 * @author emmanuel paz
 */
public class QuickSorter extends Sorter
{
  /**
   * this method is calls the quicksorter to sort
   * @param toSort
   *   the list to sort
   * @param comp
   *   the comparator to use to compare elements of the list
   * @throws NullPointerException
   */
  @Override
  public void sort(WordList toSort, Comparator<String> comp) throws NullPointerException{
    quickSortRec(toSort, comp, 0, toSort.length()-1);
  }
  /**
   * the part of quickSort that does the recursion for the list
   */
  private void quickSortRec(WordList list, Comparator<String> comp, int start, int end){
    if(start >= end)
      return;
    int pivot = partition(list, comp, start, end);
    quickSortRec(list, comp, start, pivot-1);
    quickSortRec(list, comp, pivot+1, end);
  }

  /**
   * this places the pivot in its sorted index
   * @param list the list to sort
   * @param comp the comparator to use
   * @param start the starting index
   * @param end the final index
   * @return the pivot in sorted position
   */
  private int partition(WordList list, Comparator<String> comp, int start, int end){

    int j = end-1;
    while(start<=j) {
      while (comp.compare(list.get(start), list.get(end)) < 0)
        start++;
      while (j > -1 && comp.compare(list.get(j), list.get(end)) > 0)
        j--;

      if(start<=j)
        list.swap(start++,j--);
    }
    list.swap(end, start);
    return start;
  }
}
