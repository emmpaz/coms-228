package edu.iastate.cs228.hw1;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author @emmanuelpaz
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		for(int i = 0; i < tNew.getLength(); i++){
			for(int j = 0; j < tNew.getWidth(); j++){
				 	tNew.grid[i][j] = tOld.grid[i][j].next(tNew);
			}
		}
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town current town wanting to get profit from
	 * @return the number of casual cells in the town is the profit
	 */
	public static int getProfit(Town town) {
		int profit = 0;
		for(TownCell[] i : town.grid){
			for(TownCell j : i){
				if( j instanceof Casual)
					profit++;
			}
		}

		return profit;
	}
	

	/**
	 * Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should only print the integer part (Just print the 
	 *  integer value. Example if profit is 35.56%, your output should be just: 35).
	 *
	 * Note that this method does not throws any exception, thus you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args main method
	 * 
	 */
	public static void main(String []args) {

		//this while loop is to keep asking the user until they input a correct input, so if they input something other 1 or 2 then it asks again
		while(true) {
			System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly with seed");
			Scanner scan = new Scanner(System.in);
			int input = scan.nextInt();
			if (input == 1) {
				System.out.println("Please enter file path: ");
				Scanner path = new Scanner(System.in);
				//this try contains all the code to attempt if file is found, if not the catch will run
				try{
				Town newTown = new Town(path.next());
				path.close();
				scan.close();
				int cycles = 1;
				int totalProfit = 0;
				System.out.println("Start:");
				System.out.println(newTown);
				System.out.println("Profit: " + getProfit(newTown));
				totalProfit += getProfit(newTown);
				while(cycles < 12){
					System.out.println("  After itr: " + cycles);
					newTown = updatePlain(newTown);
					System.out.println(newTown);
					System.out.println("Profit: " + getProfit(newTown));
					totalProfit += getProfit(newTown);
					cycles++;

				}
				System.out.println();
				System.out.println("Profit % is: " + (totalProfit*100.0)/(newTown.getWidth()*newTown.getLength()*12));
				break;}
				//if file was not found, catch block executes and restarts while statement asking how to populate
				catch(FileNotFoundException exception){
						System.out.println("File was not found. Please try again");

				}
			}
			//this block executes if the user wants to provide the rows, cols, and seed manually
			else if (input == 2) {
				System.out.println("Provide rows, cols and seed integer separated by space:");
				Scanner scanInfo = new Scanner(System.in);
				int row = scanInfo.nextInt();
				int cols = scanInfo.nextInt();
				int seed = scanInfo.nextInt();
				Town newTown = new Town(row, cols);
				newTown.randomInit(seed);
				scanInfo.close();
				scan.close();
				int cycles = 1;
				int totalProfit = 0;
				System.out.println("Start:");
				System.out.println(newTown);
				System.out.println("Profit: " + getProfit(newTown));
				totalProfit += getProfit(newTown);
				while(cycles < 12){
					System.out.println("  After itr: " + cycles);
					newTown = updatePlain(newTown);
					System.out.println(newTown);
					System.out.println("Profit: " + getProfit(newTown));
					totalProfit += getProfit(newTown);
					cycles++;
				}
				System.out.println();
				System.out.println("Profit % is: " + (totalProfit*100.0)/(row*cols*12));
				break;
			}
			//This block executes if user enters input that is not an option
			else {
				System.out.println("Not an option");
			}
		}
	}
}
