package edu.iastate.cs228.hw1;

/**
 * @author emmanuelpaz
 */
public class Empty extends TownCell{


    /**
     * this constructor constructs a new empty cell in its dedicated town and position
     * @param p town assigned to
     * @param r a row it shall be placed in
     * @param c a column it shall be placed in
     */
    public Empty(Town p, int r, int c)
    {
        super(p, r, c);
    }

    /**
     * This method returns the state of the a empty cell
     * @return  the empty state
     */
    @Override
    public State who() {
        return State.EMPTY;
    }

    /**
     * This method determines the type of cell it will be in the next cycle
     * @param tNew: town of the next cycle
     * @return a new cell in the town of the next cycle
     */
    @Override
    public TownCell next(Town tNew)
    {
        this.census(nCensus);

        if (nCensus[EMPTY] + nCensus[OUTAGE] <= 1)
            return new Reseller(tNew, this.row, this.col);

        else
            return new Casual(tNew, this.row, this.col);
    }

}
