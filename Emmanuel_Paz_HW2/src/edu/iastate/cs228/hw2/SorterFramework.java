package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.util.Comparator;


/**
 * An class that compares various methods of sorting.
 * 
 * @author Mike Petersen and emmanuel paz
 */
public class SorterFramework
{
  /**
   * Loads data necessary to run the sorter statistics output, and runs it.
   * The only logic within this method should be that necessary to use the
   * given file names to create the {@link AlphabetComparator},
   * {@link WordList}, and sorters to use, and then using them to run the
   * sorter statistics output.
   * 
   * @param args
   *   an array expected to contain two arguments:
   *    - the name of a file containing the ordering to use to compare
   *      characters
   *    - the name of a file containing words containing only characters in the
   *      other file
   */
  public static void main(String[] args)
  {
    try {
      Alphabet alphabet = new Alphabet(args[0]);
      AlphabetComparator comparator = new AlphabetComparator(alphabet);
      WordList words = new WordList(args[1]);
      Sorter[] sorters = {new InsertionSorter(), new MergeSorter(), new QuickSorter()};
      SorterFramework toRun = new SorterFramework(sorters, comparator, words, 1000000);
      toRun.run();
    }catch (FileNotFoundException e){
      System.out.println("File was not found");
    }

  }


  /**
   * The comparator to use for sorting.
   */
  private Comparator<String> comparator;

  /**
   * The words to sort.
   */
  private WordList words;

  /**
   * The array of sorters to use for sorting.
   */
  private Sorter[] sorters;

  /**
   * The total amount of words expected to be sorted by each sorter.
   */
  private int totalToSort;


  /**
   * Constructs and initializes the SorterFramework.
   * 
   * @param sorters
   *   the array of sorters to use for sorting
   * @param comparator
   *   the comparator to use for sorting
   * @param words
   *   the words to sort
   * @param totalToSort
   *   the total amount of words expected to be sorted by each sorter
   * @throws NullPointerException
   *   if any of {@code sorters}, {@code comparator}, {@code words}, or
   *   elements of {@code sorters} are {@code null}
   * @throws IllegalArgumentException
   *   if {@code totalToSort} is negative
   */
  public SorterFramework(Sorter[] sorters, Comparator<String> comparator, WordList words, int totalToSort) throws NullPointerException, IllegalArgumentException
  {
    this.sorters = sorters;
    this.comparator = comparator;
    this.words = new WordList(words.getArray());
    this.totalToSort = totalToSort;

  }


  /**
   * Runs all sorters using
   * {@link Sorter#sortWithStatistics(WordList, Comparator, int)
   * sortWithStatistics()}, and then outputs the following information for each
   * sorter:
   *  - the name of the sorter
   *  - the length of the word list sorted each time
   *  - the total number of words sorted
   *  - the total time used to sort words
   *  - the average time to sort the word list
   *  - the number of elements sorted per second
   *  - the total number of comparisons performed
   */
  public void run()
  {
    for(Sorter i : sorters){
      i.sortWithStatistics(words, comparator, totalToSort);
    }
    for(Sorter i : sorters){
      System.out.println("-------------- " + i.getName() + " -------------------");
      System.out.println("Length of word list:  " + words.length() + " words");
      System.out.println("Total words sorted:  " + i.getTotalWordsSorted());
      System.out.println("Total sorting time:  " + i.getTotalSortingTime() + " ns");
      System.out.println("Average sorting time:  " + i.getTotalSortingTime() / words.length() + " ns/list");
      System.out.println("Number of elements sorted per second:  " + (int) (i.getTotalWordsSorted() / (i.getTotalSortingTime()/1000000000.0)) + " words/second");
      System.out.println("Total number of comparisons:  " + i.getTotalComparisons() + " comparisons");
      System.out.println();
    }

  }
}
