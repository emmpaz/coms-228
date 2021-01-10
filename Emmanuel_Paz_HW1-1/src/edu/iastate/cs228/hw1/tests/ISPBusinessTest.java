package edu.iastate.cs228.hw1.tests;

import edu.iastate.cs228.hw1.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ISPBusinessTest {

    @Test
    void updatePlain() {
        //making a town and populating it with seed 11, then updating it
        Town testTown = new Town(3, 3);
        testTown.randomInit(11);
        testTown = ISPBusiness.updatePlain(testTown);
        //this is what the plain should update to
        State[][] testUpdate = {
                {State.EMPTY, State.EMPTY, State.CASUAL},
                {State.EMPTY, State.EMPTY, State.CASUAL},
                {State.EMPTY, State.EMPTY, State.EMPTY}
        };
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                assertEquals(testUpdate[i][j], testTown.grid[i][j].who());
            }
        }
    }

    @Test
    void getProfit() {
        //
        Town testTown = new Town(4, 4);
        testTown.randomInit(10);
        assertEquals(1, ISPBusiness.getProfit(testTown));
    }

}