package edu.iastate.cs228.hw1.tests;

import org.junit.jupiter.api.Test;
import edu.iastate.cs228.hw1.*;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TownTest {

    @Test
    void testConstruct(){
        //This test is constructing a Town but not from a file
        Town testTown = new Town(3, 4);
        assertEquals(3, testTown.getLength());
        assertEquals(4, testTown.getWidth());
        assertNotNull(testTown.grid);
    }

    @Test
    void testConstructFile() throws FileNotFoundException{
        //this test is constructing a Town from a file
        Town testTown = new Town("src/edu/iastate/cs228/hw1/tests/txtTest.txt");
        assertEquals(5, testTown.getLength());
        assertEquals(5, testTown.getWidth());
        assertNotNull(testTown.grid);

    }

    @Test
    void getWidth() throws FileNotFoundException {
        Town testTown = CasualTest.makeTownTest();
        assertEquals(5, testTown.getWidth());
    }

    @Test
    void getLength() throws FileNotFoundException{
        Town testTown = CasualTest.makeTownTest();
        assertEquals(5, testTown.getLength());
    }

    @Test
    void randomInit() {
        Town testTown = new Town(4, 4);
        testTown.randomInit(10);
        State[][] testGrid = {
                {State.OUTAGE , State.RESELLER, State.OUTAGE, State.RESELLER},
                { State.EMPTY, State.EMPTY, State.CASUAL, State.OUTAGE },
                {State.EMPTY, State.STREAMER, State.OUTAGE, State.STREAMER},
                {State.EMPTY, State.OUTAGE, State.RESELLER, State.RESELLER}
        };
        for(int i = 0; i<4; i++){
            for(int j = 0; j<4; j++){
                assertEquals(testGrid[i][j], testTown.grid[i][j].who());
            }
        }

    }

    @Test
    void testToString() {
        Town testTown = new Town(4, 4);
        testTown.randomInit(11);
        String testString = "O O E R \nO C R O \nO C S S \nC S C O ";
        assertEquals(testString, testTown.toString());
    }
}