package edu.iastate.cs228.hw1.tests;
import edu.iastate.cs228.hw1.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class CasualTest{

    public static Town makeTownTest() throws FileNotFoundException{
        return new Town("src/edu/iastate/cs228/hw1/tests/txtTest.txt");
    }

    @Test
    void testConstruct() throws FileNotFoundException{
        //makes new town and makes the top left a casual and checks to see if a casual was constructed there
        Town testTown = makeTownTest();

        Casual testCas =  new Casual(testTown, 0, 0);
        testTown.grid[0][0] = testCas;
        assertEquals(0, testCas.getRow());
        assertEquals(0, testCas.getCol());
        assertEquals(testTown, testCas.getTown());

    }

    @Test
    void who() throws FileNotFoundException{
        Town testTown = makeTownTest();
        assertEquals(State.CASUAL, new Casual(testTown, 0, 0).who());
    }

    @Test
    void next() throws FileNotFoundException{
        //expected to be outage in the next grid
        Town testTown = makeTownTest();
        assertTrue(testTown.grid[1][1].next(testTown) instanceof Outage);
        testTown = makeTownTest();
        assertTrue(testTown.grid[4][4].next(testTown) instanceof Streamer);

    }
}