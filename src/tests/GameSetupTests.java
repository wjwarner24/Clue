package tests;
import clueGame.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameSetupTests {
    private static Board board;
    private final int NUM_PLAYERS = 6;
    private final int NUM_WEAPONS = 6;
    private final int NUM_ROOMS = 9;
	private final int NUM_CARDS = 21;

	@BeforeEach
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("/Users/wjwarner24/desktop/Clue2/src/data/ClueLayout.csv", "/Users/wjwarner24/desktop/Clue2/src/data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

    //tests there is the correct number of players and the info for players 1 and 6
    @Test
	public void testPlayers() {
		assertEquals(NUM_PLAYERS, board.getPlayers().size());

        assertTrue(board.getPlayers().get(0).getName().equals("Clifford"));
        assertTrue(board.getPlayers().get(0).getColor().equals("red"));
        assertTrue(board.getPlayers().get(0).isHuman());
        assertEquals(board.getPlayers().get(0).getRow(), 0);
        assertEquals(board.getPlayers().get(0).getCol(), 7);

        assertTrue(board.getPlayers().get(5).getName().equals("Mr. Clean"));
        assertTrue(board.getPlayers().get(5).getColor().equals("white"));
        assertFalse(board.getPlayers().get(5).isHuman());
        assertEquals(board.getPlayers().get(5).getRow(), 25);
        assertEquals(board.getPlayers().get(5).getCol(), 10);

	}


    @Test
    public void testCards() {
        //tests that there are the correct amount of cards and the solution has 3 cards
        assertEquals(board.getCards().size(), NUM_CARDS);
        
        int numWeapons = 0;
        int numPlayers = 0;
        int numRooms = 0;

        


        //tests that the cards have the correct number of each type of card
        for (Card c : board.getCards()) {
            if (c.getCardType() == CardType.PERSON) {
                numPlayers++;
            }
            if (c.getCardType() == CardType.WEAPON) {
                numWeapons++;
            }
            if (c.getCardType() == CardType.ROOM) {
                numRooms++;
            }
        }

        assertEquals(numWeapons, NUM_WEAPONS);
        assertEquals(numPlayers, NUM_PLAYERS);
        assertEquals(numRooms, NUM_ROOMS);

        //tests that none of the solution cards are dealt to each player
        for (Player p : board.getPlayers()) {
            assertFalse(p.getCards().contains(board.getSolution().getWeaponCard()));
            assertFalse(p.getCards().contains(board.getSolution().getRoomCard()));
            assertFalse(p.getCards().contains(board.getSolution().getPersonCard()));
        }

    }

    @Test
    public void testPlayerHands() {
        //tests that each player has 3 cards
        for (Player p : board.getPlayers()) {
            assertEquals(p.getCards().size(), 3);
        }
        ArrayList<Card> player1Hand = board.getPlayers().get(0).getCards();
        ArrayList<Card> player2Hand = board.getPlayers().get(1).getCards();
        ArrayList<Card> player3Hand = board.getPlayers().get(2).getCards();
        //tests that each player has unique cards
        for (Card c : player1Hand) {
            assertFalse(player2Hand.contains(c));
            assertFalse(player3Hand.contains(c));
        }
        for (Card c : player2Hand) {
            assertFalse(player1Hand.contains(c));
            assertFalse(player3Hand.contains(c));
        }
        for (Card c : player3Hand) {
            assertFalse(player1Hand.contains(c));
            assertFalse(player2Hand.contains(c));
        }

    }
    

}
