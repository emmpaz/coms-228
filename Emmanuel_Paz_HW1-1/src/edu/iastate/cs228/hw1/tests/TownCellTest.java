package edu.iastate.cs228.hw1.tests;

import org.junit.jupiter.api.Test;
import edu.iastate.cs228.hw1.*;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TownCellTest {

    @Test
    void census() throws FileNotFoundException {
        Town testTown = CasualTest.makeTownTest();
        testTown.grid[0][0].census(TownCell.nCensus);
        assertArrayEquals(new int[]{1, 1, 1, 0, 0}, testTown.grid[0][0].nCensus);
        testTown.grid[1][1].census(TownCell.nCensus);
        assertArrayEquals(new int[]{1, 4, 1, 2, 0}, testTown.grid[1][1].nCensus);
    }

}