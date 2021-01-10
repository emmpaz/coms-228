package edu.iastate.cs228.hw1.tests;

import org.junit.jupiter.api.Test;
import edu.iastate.cs228.hw1.*;
import java.io.FileNotFoundException;


import static org.junit.jupiter.api.Assertions.*;

class OutageTest {

    @Test
    void testConstruct() throws FileNotFoundException{
        //makes new town and makes the top left a casual and checks to see if a casual was constructed there
        Town testTown = CasualTest.makeTownTest();

        Outage testCas =  new Outage(testTown, 0, 0);
        testTown.grid[0][0] = testCas;
        assertEquals(0, testCas.getRow());
        assertEquals(0, testCas.getCol());
        assertEquals(testTown, testCas.getTown());

    }

    @Test
    void who() throws FileNotFoundException {
        Town testTown = CasualTest.makeTownTest();
        assertEquals(State.OUTAGE, testTown.grid[0][0].who());
    }

    @Test
    void next() throws FileNotFoundException {
        Town testTown = CasualTest.makeTownTest();
        assertTrue(testTown.grid[0][0].next(testTown) instanceof Empty);
    }
}