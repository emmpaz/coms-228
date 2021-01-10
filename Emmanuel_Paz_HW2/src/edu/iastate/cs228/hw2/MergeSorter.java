package edu.iastate.cs228.hw2;
import java.util.Comparator;


/**
 * An implementation of {@link Sorter} that performs merge sort
 * to sort the list.
 * 
 * @author emmanuel paz
 */
public class MergeSorter extends Sorter
{
    /**
     * this calls mergeSorter to sort
     * @param toSort
     *   the list to sort
     * @param comp
     *   the comparator to use to compare elements of the list
     * @throws NullPointerException
     */
  @Override
  public void sort(WordList toSort, Comparator<String> comp) throws NullPointerException{
    mergeSortRec(toSort, comp, 0, toSort.length()-1);
  }

    /**
     * this is the recursion part of mergesort
     * @param list the list to sort
     * @param comp the comparator to use
     * @param start the starting index
     * @param end the last index
     */
  private void mergeSortRec(WordList list, Comparator<String> comp, int start, int end)
  {

    if (list.length() <= 1)
      return;
    int mid = list.length() / 2;
    String[] leftArray = new String[mid];
    int j = 0;
    for(int i = 0; i < leftArray.length; i++){
      leftArray[i] = list.get(j);
      j++;
    }
    String[] rightArray = new String[list.length()-mid];
    for(int i = 0; i< rightArray.length;i++){
      rightArray[i] = list.get(j);
      j++;
    }
    WordList leftList = new WordList(leftArray);
    WordList rightList = new WordList(rightArray);
    mergeSortRec(leftList, comp, start, mid);
    mergeSortRec(rightList, comp, mid+1, end);
    merge(leftList, rightList, comp, list);
  }

    /**
     * this method merges the left and right arrays
     * @param listLeft the left list
     * @param listRight the right list
     * @param comp the comparator used to sort
     * @param list the list to add back to
     */
  private void merge(WordList listLeft, WordList listRight, Comparator<String> comp, WordList list){
      int p = listLeft.length();
      int q = listRight.length();
      int i = 0;
      int j = 0;
      int index = 0;
      while(i < p && j < q){
          if(comp.compare(listLeft.get(i), listRight.get(j)) < 0){
            list.set(index, listLeft.get(i));
            i++;
            index++;
          }
          else{
            list.set(index, listRight.get(j));
            j++;
            index++;
          }
      }
      while(i < p){
        list.set(index, listLeft.get(i));
        i++;
        index++;
      }
      while(j < q){
        list.set(index, listRight.get(j));
        j++;
        index++;
      }
  }
}
