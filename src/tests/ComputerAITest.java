package tests;
import clueGame.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ComputerAITest {
    private static Board board;
    

    @BeforeEach
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("/Users/wjwarner24/desktop/Clue2/src/data/ClueLayout.csv", "/Users/wjwarner24/desktop/Clue2/src/data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}


    @Test
    public void testSelectTargetNoRooms() {
        //test if no rooms in list, select randomly
        ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayers().get(3);
        //player 4 starts on (18,23)
        //no rooms can be accessed from this location with a roll of 3


        BoardCell target = testPlayer.selectTarget(3);

        for (BoardCell bc : board.getTargets()) {
            assertFalse(bc.isRoom());
        }
        assertTrue(board.getTargets().contains(target));
        assertTrue(target.isWalkway());
        

    }

    @Test
    public void testSelectTargetUnseenRoom() {
        //test if room in list that has not been seen, select it
        ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayers().get(3);
        //player 4 starts on (18,23)
        //one room can be accessed from this location with a roll of 5



        BoardCell target = testPlayer.selectTarget(5);

        assertEquals(target.getRow(), 12);
        assertEquals(target.getCol(), 19);
        assertTrue(target.isRoomCenter());
    }

    @Test
    public void testSelectTargetSeenRooms() {
        //test if room in list that has been seen, each target (including room) selected randomly
        ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayers().get(3);

        testPlayer.addSeenRoom(board.getRoom('O'));
        BoardCell target = testPlayer.selectTarget(5);

        assertTrue(board.getTargets().contains(target));
    }

    @Test
    public void testSuggestionRoom() {
        //test room in suggestion matches current room

        ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayers().get(5);
        BoardCell target = testPlayer.selectTarget(5);
        testPlayer.move(target);
        ArrayList<Card> suggestion = testPlayer.createSuggestion();

        Card testRoomCard = null;
        for (Card c : suggestion) {
            if (c.getCardType() == CardType.ROOM) {
                testRoomCard = c;
                break;
            }
        }

        assertTrue(testPlayer.getRoom().getName().equals(testRoomCard.getName()));



    }

    @Test
    public void testSuggestionUnseenWeapon() {
        //tests that if there is only one unseen weapon it is selected
        ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayers().get(5);
        BoardCell target = testPlayer.selectTarget(5);
        testPlayer.move(target);

        testPlayer.addSeenCard(new Card("Glass Bottle", CardType.WEAPON));
        testPlayer.addSeenCard(new Card("Sharp Fingernail", CardType.WEAPON));
        testPlayer.addSeenCard(new Card("Katana", CardType.WEAPON));
        testPlayer.addSeenCard(new Card("Butter Knife", CardType.WEAPON));
        testPlayer.addSeenCard(new Card("Sharp Pencil", CardType.WEAPON));

        ArrayList<Card> suggestion = testPlayer.createSuggestion();

        Card weaponCard = null;
        for (Card c : suggestion) {
            if (c.getCardType() == CardType.WEAPON) {
                weaponCard = c;
                break;
            }
        }
        assertTrue(weaponCard.equals(new Card("Brass Knuckles", CardType.WEAPON)));

    }

    @Test
    public void testSuggestionUnseenPerson() {
        //tests that if there is only one unseen person it is selected

        ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayers().get(5);
        BoardCell target = testPlayer.selectTarget(5);
        testPlayer.move(target);

        testPlayer.addSeenCard(new Card("Clifford", CardType.PERSON));
        testPlayer.addSeenCard(new Card("Dory", CardType.PERSON));
        testPlayer.addSeenCard(new Card("Yoda", CardType.PERSON));
        testPlayer.addSeenCard(new Card("Velma", CardType.PERSON));
        testPlayer.addSeenCard(new Card("Pink Panther", CardType.PERSON));

        ArrayList<Card> suggestion = testPlayer.createSuggestion();

        Card personCard = null;
        for (Card c : suggestion) {
            if (c.getCardType() == CardType.PERSON) {
                personCard = c;
                break;
            }
        }
        assertTrue(personCard.equals(new Card("Mr. Clean", CardType.PERSON)));
    }

    @Test
    public void testSuggestionMultipleWeapons() {
        //tests that if there are multiple unseen weapons one is chosen at random

        ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayers().get(5);
        BoardCell target = testPlayer.selectTarget(5);
        testPlayer.move(target);

        testPlayer.addSeenCard(new Card("Glass Bottle", CardType.WEAPON));
        testPlayer.addSeenCard(new Card("Sharp Fingernail", CardType.WEAPON));
        testPlayer.addSeenCard(new Card("Katana", CardType.WEAPON));
        testPlayer.addSeenCard(new Card("Butter Knife", CardType.WEAPON));

        ArrayList<Card> suggestion = testPlayer.createSuggestion();

        Card weaponCard = null;
        for (Card c : suggestion) {
            if (c.getCardType() == CardType.WEAPON) {
                weaponCard = c;
                break;
            }
        }
        assertTrue(weaponCard.equals(new Card("Brass Knuckles", CardType.WEAPON)) || weaponCard.equals(new Card("Sharp Pencil", CardType.WEAPON)));
    }

    @Test
    public void testSuggestionMultiplePeople() {
        //tests that if there are multiple unseen people one is chosen at random

        ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayers().get(5);
        BoardCell target = testPlayer.selectTarget(5);
        testPlayer.move(target);

        testPlayer.addSeenCard(new Card("Clifford", CardType.PERSON));
        testPlayer.addSeenCard(new Card("Dory", CardType.PERSON));
        testPlayer.addSeenCard(new Card("Yoda", CardType.PERSON));
        testPlayer.addSeenCard(new Card("Velma", CardType.PERSON));

        ArrayList<Card> suggestion = testPlayer.createSuggestion();

        Card personCard = null;
        for (Card c : suggestion) {
            if (c.getCardType() == CardType.PERSON) {
                personCard = c;
                break;
            }
        }
        assertTrue(personCard.equals(new Card("Mr. Clean", CardType.PERSON)) || personCard.equals(new Card("Pink Panther", CardType.PERSON)));
    }

}
