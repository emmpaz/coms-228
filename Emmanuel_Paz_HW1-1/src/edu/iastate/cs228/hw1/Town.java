package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


/**
 *  @author @emmanuelpaz
 *
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length length of grid
	 * @param width width of grid
	 */
	public Town(int length, int width) {
		this.length = length;
		this.width = width;
		grid = new TownCell[this.length][this.width];

	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName file string
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File f = new File(inputFileName);
		Scanner scan = new Scanner(f);
		String num = scan.nextLine();
		Scanner line = new Scanner(num);
		String stringWidth = line.next();
		this.length = Integer.parseInt(stringWidth);
		String stringLength = line.next();
		this.width = Integer.parseInt(stringLength);
		line.close();
		grid = new TownCell[length][width];
		int i = 0, j = 0;
		while(scan.hasNextLine()){
			String row = scan.nextLine();
			Scanner eachItem = new Scanner(row);
			while(eachItem.hasNext()){
				String cell = eachItem.next();
				switch (cell){
					case "C":
						grid[i][j] = new Casual(this, i, j);
						break;
					case "S":
						grid[i][j] = new Streamer(this, i , j);
						break;
					case "R":
						grid[i][j] = new Reseller(this, i, j);
						break;
					case "E":
						grid[i][j] = new Empty(this, i ,j);
						break;
					case "O":
						grid[i][j] = new Outage(this, i, j);

				}
				j++;

			}
			j = 0;
			i++;

		}
		scan.close();

	}
	
	/**
	 * Returns width of the grid.
	 * @return width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		for(int i = 0; i < length; i++){
			for(int j = 0; j < width; j++){
				int num = rand.nextInt(5);
				switch(num){
					case 0:
						grid[i][j] = new Reseller(this, i, j);
						break;
					case 1:
						grid[i][j] = new Empty(this, i , j);
						break;
					case 2:
						grid[i][j] = new Casual(this, i, j);
						break;
					case 3:
						grid[i][j] = new Outage(this, i ,j);
						break;
					case 4:
						grid[i][j] = new Streamer(this, i, j);
				}
			}
		}

	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		//TODO: Write your code here.
		for(int i = 0; i < this.length; i++){
			for(int j = 0; j < this.width; j++){
				s += this.grid[i][j].who().toString().charAt(0) + " ";
			}
			if(i < this.length-1)
				s += "\n";
		}
		return s;
	}
}
