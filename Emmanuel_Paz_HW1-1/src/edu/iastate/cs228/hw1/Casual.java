package edu.iastate.cs228.hw1;

/**
 * @author emmanuelpaz
 */
public class Casual extends TownCell{


    /**
     * this constructor constructs a new casual cell in its dedicated town and position
     * @param p town assigned to
     * @param r a row it shall be placed in
     * @param c a column it shall be placed in
     */
    public Casual(Town p, int r, int c)
    {
        super(p, r, c);
    }

    /**
     * This method returns the state of the a casual cell
     * @return  the casual state
     */
    @Override
    public State who()
    {

        return State.CASUAL;
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

        else if (nCensus[RESELLER] > 0)
            return new Outage(tNew, this.row, this.col);

        else if (nCensus[STREAMER] > 0)
            return new Streamer(tNew, this.row, this.col);

        else if (nCensus[CASUAL] >= 5)
            return new Streamer(tNew, this.row, this.col);

        else
            return new Casual(tNew, this.row, this.col);
    }

}
