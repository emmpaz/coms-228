package edu.iastate.cs228.hw1;

/**
 * @author emmanuelpaz
 */
public class Outage extends TownCell{

    /**
     * this constructor constructs a new outage cell in its dedicated town and position
     * @param p town assigned to
     * @param r a row it shall be placed in
     * @param c a column it shall be placed in
     */
    public Outage(Town p, int r, int c)
    {
        super(p, r, c);
    }

    /**
     * This method returns the state of the a outage cell
     * @return  the outage state
     */
    @Override
    public State who() {
        return State.OUTAGE;
    }

    /**
     * This method determines the type of cell it will be in the next cycle
     * @param tNew: town of the next cycle
     * @return a new cell in the town of the next cycle
     */
    @Override
    public TownCell next(Town tNew)
    {
        return new Empty(tNew, this.row, this.col);
    }

}
