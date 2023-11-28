package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
    private static Board board;
	
	@BeforeEach
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("./src/data/ClueLayout.csv", "./src/data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// First, the Kitchen that only has a single door but a secret room to D
		Set<BoardCell> testList = board.getAdjList(19, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(3,20)));
		assertTrue(testList.contains(board.getCell(16, 3)));
		
		// now test the Lounge
		testList = board.getAdjList(12, 19);
		assertEquals(3, testList.size());
		// these are doorways to the Lounge 'O'
		assertTrue(testList.contains(board.getCell(8, 20)));
		assertTrue(testList.contains(board.getCell(12, 15)));
		assertTrue(testList.contains(board.getCell(17, 20)));
		
		// one more room, the Billiard Room 'R', contains passage to 'C'
		testList = board.getAdjList(21, 20);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(18, 18)));
		assertTrue(testList.contains(board.getCell(21, 15)));
		assertTrue(testList.contains(board.getCell(12, 3)));

		//tests cell that is not room center has no adjacencies
		testList = board.getAdjList(0,12);
		assertEquals(0, testList.size());
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor() {
		//door above Lounge
		Set<BoardCell> testList = board.getAdjList(8, 20);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(12, 19)));
		assertTrue(testList.contains(board.getCell(8, 19)));
		assertTrue(testList.contains(board.getCell(7, 20)));

		//door to Billiard Room
		testList = board.getAdjList(18, 18);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(21, 20)));
		assertTrue(testList.contains(board.getCell(18, 17)));
		assertTrue(testList.contains(board.getCell(17, 18)));
		assertTrue(testList.contains(board.getCell(18, 19)));
		
		//door to Ballroom
		testList = board.getAdjList(6, 12);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(3, 12)));
		assertTrue(testList.contains(board.getCell(6, 11)));
		assertTrue(testList.contains(board.getCell(6, 13)));
		assertTrue(testList.contains(board.getCell(7, 12)));

		
		
		
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		
		Set<BoardCell> testList = board.getAdjList(18, 4);

		// Test adjacent to walkways
		testList = board.getAdjList(14, 13);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(13, 13)));
		assertTrue(testList.contains(board.getCell(15, 13)));
		assertTrue(testList.contains(board.getCell(14, 12)));
		assertTrue(testList.contains(board.getCell(14, 14)));

		//tests walkway on edge of board
		testList = board.getAdjList(7, 23);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(7, 22)));

		//tests walkway that is next to room
		testList = board.getAdjList(10, 15);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(11, 15)));
		assertTrue(testList.contains(board.getCell(9, 15)));
		assertTrue(testList.contains(board.getCell(10, 14)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	//center of Lounge is (21,12)
	@Test
	public void testTargetsInLounge() {
		// test a roll of 1
		board.calcTargets(board.getCell(21,12), 1,board.getCell(21, 12));
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(16, 11)));
		assertTrue(targets.contains(board.getCell(16, 12)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(21,12), 3,board.getCell(21, 12));
		targets= board.getTargets();
		assertEquals(10, targets.size()); //may need to revisit
		assertTrue(targets.contains(board.getCell(16, 9)));
		assertTrue(targets.contains(board.getCell(15, 10)));	
		assertTrue(targets.contains(board.getCell(15, 13)));
		assertTrue(targets.contains(board.getCell(16, 14)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(21,12), 4,board.getCell(21, 12));
		targets= board.getTargets();
		assertEquals(21, targets.size()); //may need to revisit
		assertTrue(targets.contains(board.getCell(16, 8)));
		assertTrue(targets.contains(board.getCell(15, 9)));	
		assertTrue(targets.contains(board.getCell(13, 12)));
		assertTrue(targets.contains(board.getCell(16, 15)));	
	}
	
	//Kitchen center is (19,2)
	//Kitchen has secret passage to dining room
	@Test
	public void testTargetsInKitchen() {
		// test a roll of 1
		board.calcTargets(board.getCell(19, 2), 1,board.getCell(19, 2));
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(16, 3)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(19, 2), 3,board.getCell(19, 2));
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(15, 2)));	
		assertTrue(targets.contains(board.getCell(15, 4)));
		assertTrue(targets.contains(board.getCell(16, 5)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(19, 2), 4,board.getCell(19, 2));
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(15, 5)));
		assertTrue(targets.contains(board.getCell(16, 6)));	
		assertTrue(targets.contains(board.getCell(15, 1)));
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(17,20), 1,board.getCell(17, 20));
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(12, 19)));
		assertTrue(targets.contains(board.getCell(17, 19)));	
		assertTrue(targets.contains(board.getCell(17, 21)));	
		assertTrue(targets.contains(board.getCell(18, 20)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(17,20), 3,board.getCell(17, 20));
		targets= board.getTargets();
		assertEquals(8, targets.size());
		
		assertTrue(targets.contains(board.getCell(17, 17)));	
		assertTrue(targets.contains(board.getCell(17, 23)));
		assertTrue(targets.contains(board.getCell(12, 19)));
		assertTrue(targets.contains(board.getCell(18, 18)));

	
		
		// test a roll of 4
		board.calcTargets(board.getCell(17,20), 4,board.getCell(17, 20));
		targets= board.getTargets();
		assertEquals(9, targets.size());
	
		assertTrue(targets.contains(board.getCell(18, 23)));	
		assertTrue(targets.contains(board.getCell(21, 20)));
		assertTrue(targets.contains(board.getCell(12, 19)));
		assertTrue(targets.contains(board.getCell(21, 20)));

	}

	@Test
	public void testTargetsInWalkway() {
		// test a roll of 1
		board.calcTargets(board.getCell(15, 9), 1,board.getCell(15, 9));
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(15, 8)));
		assertTrue(targets.contains(board.getCell(14, 9)));
		assertTrue(targets.contains(board.getCell(16, 9)));
		assertTrue(targets.contains(board.getCell(15, 10)));
		
		
		
		// test a roll of 3
		board.calcTargets(board.getCell(15, 9), 3,board.getCell(15, 9));
		targets= board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(15, 6)));
		assertTrue(targets.contains(board.getCell(12, 9)));
		assertTrue(targets.contains(board.getCell(15, 12)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(15, 9), 4,board.getCell(15, 9));
		targets= board.getTargets();
		assertEquals(18, targets.size());
		assertTrue(targets.contains(board.getCell(15, 5)));
		assertTrue(targets.contains(board.getCell(12, 8)));
		assertTrue(targets.contains(board.getCell(16, 12)));	
	}

	//Testing from (6,17)
	//(7,17) is occupied
	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(7, 17).setOccupied(true);
		board.calcTargets(board.getCell(6, 17), 2,board.getCell(6, 17));
		board.getCell(7, 17).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(6, 15)));

	}
}
