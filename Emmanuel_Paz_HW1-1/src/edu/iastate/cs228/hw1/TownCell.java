package edu.iastate.cs228.hw1;

/**
 * 
 * @author @emmanuelpaz
 *	Also provide appropriate comments for this class
 *
 */
public abstract class TownCell {

	public State who;
	//the town the cell should be pointing to
	protected Town plain;
	//the row it should be in
	protected int row;
	//the column it should be in
	protected int col;
	
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	/**
	 * Constructor for a cell in a town and assigned in a spot in the grid
	 * @param p the town the cell should be pointing to
	 * @param r row it is assigned to
	 * @param c column it is assigned to
	 */
	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}

	/**
	 * This private method just changes the state name to a number according to the static finals above
	 * @param a the state of the cell
	 * @return number that is correlated to its state
	 */
	private int StateChanger(State a){
		int num = 0;
		switch (a){
			case RESELLER:
				break;
			case EMPTY:
				num = 1;
				break;
			case CASUAL:
				num = 2;
				break;
			case OUTAGE:
				num = 3;
				break;
			case STREAMER:
				num = 4;
				break;
		}
		return num;
	}
	
	/**
	 * Censuses all cell types in the 3 X 3 neighborhood
	 * Use who() method to get who is present in the 
	 *  
	 * @param nCensus of all customers
	 */
	public void census(int[] nCensus) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0;

		//these arrays are for finding what are the neighbors for a cell (combinations)
		int[] rowNum = {-1, 0, -1, 1, 1, 0, 1, -1};
		int[] colNum = {-1, -1, 0, 1, 0, 1,-1, 1};
		//this loops through each index of the arrays to see if it can get the neighbor but skip the ones that are out of bounds
		for(int i = 0; i < rowNum.length; i++){
			try{
				nCensus[StateChanger(plain.grid[this.row+rowNum[i]][this.col+colNum[i]].who())]++;
			} catch (ArrayIndexOutOfBoundsException exception){}

		}

	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);

	/**
	 * purpose of this method is to test constructor in test class
	 * @return row of towncell
	 */
	public int getRow(){
		return row;
	}

	/**
	 * purpose of this method is to test constructor in test class
	 * @return col of towncell
	 */
	public int getCol(){
		return col;
	}

	/**
	 * purpose of this method is to test constructor in test class
	 * @return plain of towncell
	 */
	public Town getTown(){
		return plain;
	}
}
