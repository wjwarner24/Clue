package tests;
import org.junit.*;

import experiment.TestBoard;
import experiment.TestBoardCell;

import java.util.*;

//BoardTestsExp Class
//Author: William Warner
//Created March 5th, 2023
//No Collaboraters or outside sources

//Tests the functionality of the experiment package
//Tests functionality of adjacency lists for BoardCells
//Tests functionality of getting targets from a startCell and specific dice roll
//Target tests include testing when some cells are Rooms, and some are occupied
public class BoardTestsExp {
    TestBoard board;

    @Before
    public void setUp() {
        board = new TestBoard();
    }

    @Test
    public void testAdjacency() {
        TestBoardCell cell = board.getCell(0,0);
        Set<TestBoardCell> testList = cell.getAdjList();
        Assert.assertTrue(testList.contains(board.getCell(1,0)));
        Assert.assertTrue(testList.contains(board.getCell(0,1)));
        Assert.assertEquals(2, testList.size());

        cell = board.getCell(3,3);
        testList = cell.getAdjList();
        Assert.assertTrue(testList.contains(board.getCell(2,3)));
        Assert.assertTrue(testList.contains(board.getCell(3,2)));
        Assert.assertEquals(2, testList.size());

        cell = board.getCell(1,3);
        testList = cell.getAdjList();
        Assert.assertTrue(testList.contains(board.getCell(0,3)));
        Assert.assertTrue(testList.contains(board.getCell(1,2)));
        Assert.assertTrue(testList.contains(board.getCell(2,3)));
        Assert.assertEquals(3, testList.size());

        cell = board.getCell(3,0);
        testList = cell.getAdjList();
        Assert.assertTrue(testList.contains(board.getCell(2,0)));
        Assert.assertTrue(testList.contains(board.getCell(3,1)));
        Assert.assertEquals(2, testList.size());

        cell = board.getCell(2,2);
        testList = cell.getAdjList();
        Assert.assertTrue(testList.contains(board.getCell(1,2)));
        Assert.assertTrue(testList.contains(board.getCell(2,1)));
        Assert.assertTrue(testList.contains(board.getCell(2,3)));
        Assert.assertTrue(testList.contains(board.getCell(3,2)));
        Assert.assertEquals(4, testList.size());
        
    }

    @Test
    public void testTargetsNormal() {
        board.clearTargets();
        TestBoardCell cell = board.getCell(0,0);
        board.calcTargets(cell, 3, cell);
        Set<TestBoardCell> targets = board.getTargets();
        Assert.assertEquals(6, targets.size());
        Assert.assertTrue(targets.contains(board.getCell(3, 0)));
        Assert.assertTrue(targets.contains(board.getCell(2, 1)));
        Assert.assertTrue(targets.contains(board.getCell(0, 1)));
        Assert.assertTrue(targets.contains(board.getCell(1, 2)));
        Assert.assertTrue(targets.contains(board.getCell(0, 3)));
        Assert.assertTrue(targets.contains(board.getCell(1, 0)));

        board.clearTargets();
        cell = board.getCell(0,1);
        board.calcTargets(cell, 6, cell);
        targets = board.getTargets();
        
        Assert.assertEquals(7, targets.size());
        Assert.assertTrue(targets.contains(board.getCell(0, 3)));
        Assert.assertTrue(targets.contains(board.getCell(1, 0)));
        Assert.assertTrue(targets.contains(board.getCell(1, 2)));
        Assert.assertTrue(targets.contains(board.getCell(2, 1)));
        Assert.assertTrue(targets.contains(board.getCell(2, 3)));
        Assert.assertTrue(targets.contains(board.getCell(3, 0)));
        Assert.assertTrue(targets.contains(board.getCell(3, 2)));

       

        board.clearTargets();
        cell = board.getCell(3,1);
        board.calcTargets(cell, 1, cell);
        targets = board.getTargets();
        Assert.assertEquals(3, targets.size());
        Assert.assertTrue(targets.contains(board.getCell(2, 1)));
        Assert.assertTrue(targets.contains(board.getCell(3, 0)));
        Assert.assertTrue(targets.contains(board.getCell(3, 2)));

        board.clearTargets();
        cell = board.getCell(0,3);
        board.calcTargets(cell, 2, cell);
        targets = board.getTargets();
        Assert.assertEquals(3, targets.size());
        Assert.assertTrue(targets.contains(board.getCell(0, 1)));
        Assert.assertTrue(targets.contains(board.getCell(1, 2)));
        Assert.assertTrue(targets.contains(board.getCell(2, 3)));
        


        board.clearTargets();
        cell = board.getCell(3,2);
        board.calcTargets(cell, 4, cell);
        targets = board.getTargets();
        Assert.assertEquals(7, targets.size());
        Assert.assertTrue(targets.contains(board.getCell(0, 1)));
        Assert.assertTrue(targets.contains(board.getCell(0, 3)));
        Assert.assertTrue(targets.contains(board.getCell(1, 0)));
        Assert.assertTrue(targets.contains(board.getCell(1, 2)));
        Assert.assertTrue(targets.contains(board.getCell(2, 1)));
        Assert.assertTrue(targets.contains(board.getCell(2, 3)));
        Assert.assertTrue(targets.contains(board.getCell(3, 0)));
        


        
        board.clearTargets();
        cell = board.getCell(3,1);
        board.calcTargets(cell, 5, cell);
        targets = board.getTargets();
        Assert.assertEquals(8, targets.size());
        Assert.assertTrue(targets.contains(board.getCell(0, 1)));
        Assert.assertTrue(targets.contains(board.getCell(0, 3)));
        Assert.assertTrue(targets.contains(board.getCell(1, 0)));
        Assert.assertTrue(targets.contains(board.getCell(1, 2)));
        Assert.assertTrue(targets.contains(board.getCell(2, 1)));
        Assert.assertTrue(targets.contains(board.getCell(2, 3)));
        Assert.assertTrue(targets.contains(board.getCell(3, 0)));
        Assert.assertTrue(targets.contains(board.getCell(3, 2)));

    }

    @Test
    public void testTargetsMixed() {
        board = new TestBoard();
        board.clearTargets();
        board.getCell(0, 2).setOccupied(true);
        board.getCell(1, 2).setRoom(true);
        TestBoardCell cell =  board.getCell(0, 3);
        board.calcTargets(cell,3,cell);
        Set<TestBoardCell> targets = board.getTargets();
        Assert.assertEquals(3, targets.size());
        Assert.assertTrue(targets.contains(board.getCell(1,2)));
        Assert.assertTrue(targets.contains(board.getCell(2,2)));
        Assert.assertTrue(targets.contains(board.getCell(3,3)));
        

        board.clearTargets();
        board.getCell(0, 2).setOccupied(false);
        board.getCell(1, 2).setRoom(false);

        board.getCell(1, 0).setOccupied(true);
        board.getCell(2, 1).setRoom(true);

        cell =  board.getCell(1, 2);
        board.calcTargets(cell,2, cell);
        targets = board.getTargets();
        Assert.assertEquals(5, targets.size());
        Assert.assertTrue(targets.contains(board.getCell(0,1)));
        Assert.assertTrue(targets.contains(board.getCell(0,3)));
        Assert.assertTrue(targets.contains(board.getCell(2,1)));
        Assert.assertTrue(targets.contains(board.getCell(2,3)));
        Assert.assertTrue(targets.contains(board.getCell(3,2)));
        
    }

    @Test
    public void testTargetsRoom() {
        board = new TestBoard();
        board.clearTargets();
        board.getCell(0,2).setRoom(true);
        TestBoardCell cell = board.getCell(1,3);
        board.calcTargets(cell, 3, cell);
        Set<TestBoardCell> targets = board.getTargets();
        Assert.assertEquals(7, targets.size());
        Assert.assertTrue(targets.contains(board.getCell(0,1)));
        Assert.assertTrue(targets.contains(board.getCell(0,2)));
        
        Assert.assertTrue(targets.contains(board.getCell(1,2)));
        
        Assert.assertTrue(targets.contains(board.getCell(2,1)));
        Assert.assertTrue(targets.contains(board.getCell(2,3)));
        Assert.assertTrue(targets.contains(board.getCell(3,2)));
        Assert.assertTrue(targets.contains(board.getCell(1,0)));
        

        board.clearTargets();
        board.getCell(0,2).setRoom(false);

        board.getCell(1,1).setRoom(true);
        cell = board.getCell(2,1);
        board.calcTargets(cell, 1, cell);
        targets = board.getTargets();
        Assert.assertEquals(4, targets.size());
        Assert.assertTrue(targets.contains(board.getCell(1,1)));
        Assert.assertTrue(targets.contains(board.getCell(2,0)));
        Assert.assertTrue(targets.contains(board.getCell(2,2)));
        Assert.assertTrue(targets.contains(board.getCell(3,1)));

    }

    @Test
    public void testTargetsOccupied() {
        board.clearTargets();
        board.getCell(1,2).setOccupied(true);
        TestBoardCell cell = board.getCell(2,2);
        board.calcTargets(cell, 2, cell);
        Set<TestBoardCell> targets = board.getTargets();
        Assert.assertEquals(5, targets.size());
        Assert.assertTrue(targets.contains(board.getCell(1,1)));
        Assert.assertTrue(targets.contains(board.getCell(1,3)));
        Assert.assertTrue(targets.contains(board.getCell(2,0)));
        Assert.assertTrue(targets.contains(board.getCell(3,1)));
        Assert.assertTrue(targets.contains(board.getCell(3,3)));
        
        board.clearTargets();
        board.getCell(1,2).setOccupied(false);

        board.getCell(1,2).setOccupied(true);
        cell = board.getCell(1,1);
        board.calcTargets(cell, 1, cell);
        targets = board.getTargets();
        Assert.assertEquals(3, targets.size());
        Assert.assertTrue(targets.contains(board.getCell(0,1)));
        Assert.assertTrue(targets.contains(board.getCell(1,0)));
        Assert.assertTrue(targets.contains(board.getCell(2,1)));
        
    }

    

}
