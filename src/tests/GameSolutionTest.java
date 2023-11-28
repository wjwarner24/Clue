package tests;
import clueGame.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameSolutionTest {
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

    @Test
    public void testAccusationCorrect() {
        //test an accusation that is correct

        Solution testSolution = new Solution();
        testSolution.setPersonCard(new Card("Name1", CardType.PERSON));
        testSolution.setWeaponCard(new Card("Katana", CardType.WEAPON));
        testSolution.setRoomCard(new Card("Kitchen", CardType.ROOM));

        board.setSolution(testSolution);
        assertTrue(board.checkAccusation(testSolution));

        //testSolution.clear();
        testSolution.setPersonCard(new Card("Name5", CardType.PERSON));
        testSolution.setWeaponCard(new Card("Sharp Fingernail", CardType.WEAPON));
        testSolution.setRoomCard(new Card("Conservatory", CardType.ROOM));

        board.setSolution(testSolution);
        assertTrue(board.checkAccusation(testSolution));
    }

    @Test
    public void testAccusationWrongPerson() {
        //test accusation with wrong person

        Solution testSolution = new Solution();
        testSolution.setPersonCard(new Card("Name1", CardType.PERSON));
        testSolution.setWeaponCard(new Card("Katana", CardType.WEAPON));
        testSolution.setRoomCard(new Card("Kitchen", CardType.ROOM));

        board.setSolution(testSolution);
        
        Solution testAccusation = new Solution();
        testAccusation.setPersonCard(new Card("Name2", CardType.PERSON));
        testAccusation.setWeaponCard(new Card("Katana", CardType.WEAPON));
        testAccusation.setRoomCard(new Card("Kitchen", CardType.ROOM));
        assertFalse(board.checkAccusation(testAccusation));
    }

    @Test
    public void testAccusationWrongWeapon() {
        //test accusation with wrong weapon
        Solution testSolution = new Solution();
        testSolution.setPersonCard(new Card("Name1", CardType.PERSON));
        testSolution.setWeaponCard(new Card("Katana", CardType.WEAPON));
        testSolution.setRoomCard(new Card("Kitchen", CardType.ROOM));

        board.setSolution(testSolution);
        Solution testAccusation = new Solution();
        testAccusation.setPersonCard(new Card("Name1", CardType.PERSON));
        testAccusation.setWeaponCard(new Card("Brass Knuckles", CardType.WEAPON));
        testAccusation.setRoomCard(new Card("Kitchen", CardType.ROOM));
        assertFalse(board.checkAccusation(testAccusation));
    }

    @Test
    public void testAccusationWrongRoom() {
        //test accusation with wrong room
        Solution testSolution = new Solution();
        testSolution.setPersonCard(new Card("Name1", CardType.PERSON));
        testSolution.setWeaponCard(new Card("Katana", CardType.WEAPON));
        testSolution.setRoomCard(new Card("Kitchen", CardType.ROOM));

        board.setSolution(testSolution);
        Solution testAccusation = new Solution();
        testAccusation.setPersonCard(new Card("Name1", CardType.PERSON));
        testAccusation.setWeaponCard(new Card("Katana", CardType.WEAPON));
        testAccusation.setRoomCard(new Card("Lounge", CardType.ROOM));
        assertFalse(board.checkAccusation(testAccusation));
    }

    @Test
    public void testDisproveSuggestionOneCard() {

        //test one available card to disprove

        ArrayList<Card> tempHand = new ArrayList<Card>();
        tempHand.add(new Card("Name1", CardType.PERSON));
        tempHand.add(new Card("Katana", CardType.WEAPON));
        tempHand.add(new Card("Kitchen", CardType.ROOM));

        ArrayList<Card> testSuggestion = new ArrayList<Card>();
        testSuggestion.add(new Card("Name4", CardType.PERSON));
        testSuggestion.add(new Card("Butter Knife", CardType.WEAPON));
        testSuggestion.add(new Card("Kitchen", CardType.ROOM));

        board.getPlayers().get(5).setCards(tempHand);
        
        assertTrue(board.getPlayers().get(5).disproveSuggestion(testSuggestion).equals(new Card("Kitchen", CardType.ROOM)));
    
    
    
    }

    @Test
    public void testDisproveSuggestionMultipleCards() {
        //player has multiple cards to disprove, one is chosen randomly
        ArrayList<Card> tempHand = new ArrayList<Card>();
        tempHand.add(new Card("Name1", CardType.PERSON));
        tempHand.add(new Card("Katana", CardType.WEAPON));
        tempHand.add(new Card("Kitchen", CardType.ROOM));

        ArrayList<Card> testSuggestion = new ArrayList<Card>();
        testSuggestion.add(new Card("Name1", CardType.PERSON));
        testSuggestion.add(new Card("Butter Knife", CardType.WEAPON));
        testSuggestion.add(new Card("Kitchen", CardType.ROOM));

        board.getPlayers().get(5).setCards(tempHand);
        
        Card solution1 = new Card("Name1", CardType.PERSON);
        Card solution2 = new Card("Kitchen", CardType.ROOM);
        assertTrue(board.getPlayers().get(5).disproveSuggestion(testSuggestion).equals(solution1) || board.getPlayers().get(5).disproveSuggestion(testSuggestion).equals(solution2));
    }

    @Test
    public void testDisproveSuggestionNoCards() {
        //player has no cards to disprove, null is returned

        ArrayList<Card> tempHand = new ArrayList<Card>();
        tempHand.add(new Card("Name1", CardType.PERSON));
        tempHand.add(new Card("Katana", CardType.WEAPON));
        tempHand.add(new Card("Kitchen", CardType.ROOM));

        ArrayList<Card> testSuggestion = new ArrayList<Card>();
        testSuggestion.add(new Card("Name4", CardType.PERSON));
        testSuggestion.add(new Card("Butter Knife", CardType.WEAPON));
        testSuggestion.add(new Card("Lounge", CardType.ROOM));

        board.getPlayers().get(5).setCards(tempHand);
        
        assertEquals(board.getPlayers().get(5).disproveSuggestion(testSuggestion), null);
    }

    @Test
    public void testHandleSuggestionNobodyCanDisprove() {
        //suggestion that nobody can disprove returns null
        ArrayList<Card> testSuggestion = new ArrayList<Card>();
        testSuggestion.add(new Card("Name4", CardType.PERSON));
        testSuggestion.add(new Card("Butter Knife", CardType.WEAPON));
        testSuggestion.add(new Card("Lounge", CardType.ROOM));

        ArrayList<Card> tempHand0 = new ArrayList<Card>();
        tempHand0.add(new Card("Name6", CardType.PERSON));
        tempHand0.add(new Card("Katana", CardType.WEAPON));
        tempHand0.add(new Card("Kitchen", CardType.ROOM));

        ArrayList<Card> tempHand1 = new ArrayList<Card>();
        tempHand1.add(new Card("Name1", CardType.PERSON));
        tempHand1.add(new Card("Brass Knuckles", CardType.WEAPON));
        tempHand1.add(new Card("Conservatory", CardType.ROOM));

        ArrayList<Card> tempHand2 = new ArrayList<Card>();
        tempHand2.add(new Card("Name2", CardType.PERSON));
        tempHand2.add(new Card("Sharp Pencil", CardType.WEAPON));
        tempHand2.add(new Card("Dining Room", CardType.ROOM));

        ArrayList<Card> tempHand3 = new ArrayList<Card>();
        tempHand3.add(new Card("Name3", CardType.PERSON));
        tempHand3.add(new Card("Sharp Fingernail", CardType.WEAPON));
        tempHand3.add(new Card("Ballroom", CardType.ROOM));

        ArrayList<Card> tempHand4 = new ArrayList<Card>();
        tempHand4.add(new Card("Name5", CardType.PERSON));
        tempHand4.add(new Card("Glass Bottle", CardType.WEAPON));
        tempHand4.add(new Card("Study", CardType.ROOM));

        ArrayList<Card> tempHand5 = new ArrayList<Card>();
        tempHand5.add(new Card("Conservatory", CardType.ROOM));
        tempHand5.add(new Card("Billiard Room", CardType.ROOM));
        tempHand5.add(new Card("Library", CardType.ROOM));

        board.getPlayers().get(0).setCards(tempHand0);
        board.getPlayers().get(1).setCards(tempHand1);
        board.getPlayers().get(2).setCards(tempHand2);
        board.getPlayers().get(3).setCards(tempHand3);
        board.getPlayers().get(4).setCards(tempHand4);
        board.getPlayers().get(5).setCards(tempHand5);

        assertEquals(board.handleSuggestion(testSuggestion, 0), null);

    }

    @Test
    public void testHandleSuggestionSuggestingDisproves() {
        //suggestion only suggesting player can disprove returns null
        ArrayList<Card> testSuggestion = new ArrayList<Card>();
        testSuggestion.add(new Card("Name4", CardType.PERSON));
        testSuggestion.add(new Card("Butter Knife", CardType.WEAPON));
        testSuggestion.add(new Card("Lounge", CardType.ROOM));

        ArrayList<Card> tempHand0 = new ArrayList<Card>();
        tempHand0.add(new Card("Name4", CardType.PERSON));
        tempHand0.add(new Card("Katana", CardType.WEAPON));
        tempHand0.add(new Card("Kitchen", CardType.ROOM));

        ArrayList<Card> tempHand1 = new ArrayList<Card>();
        tempHand1.add(new Card("Name1", CardType.PERSON));
        tempHand1.add(new Card("Brass Knuckles", CardType.WEAPON));
        tempHand1.add(new Card("Conservatory", CardType.ROOM));

        ArrayList<Card> tempHand2 = new ArrayList<Card>();
        tempHand2.add(new Card("Name2", CardType.PERSON));
        tempHand2.add(new Card("Sharp Pencil", CardType.WEAPON));
        tempHand2.add(new Card("Dining Room", CardType.ROOM));

        ArrayList<Card> tempHand3 = new ArrayList<Card>();
        tempHand3.add(new Card("Name3", CardType.PERSON));
        tempHand3.add(new Card("Sharp Fingernail", CardType.WEAPON));
        tempHand3.add(new Card("Ballroom", CardType.ROOM));

        ArrayList<Card> tempHand4 = new ArrayList<Card>();
        tempHand4.add(new Card("Name5", CardType.PERSON));
        tempHand4.add(new Card("Glass Bottle", CardType.WEAPON));
        tempHand4.add(new Card("Study", CardType.ROOM));

        ArrayList<Card> tempHand5 = new ArrayList<Card>();
        tempHand5.add(new Card("Conservatory", CardType.ROOM));
        tempHand5.add(new Card("Billiard Room", CardType.ROOM));
        tempHand5.add(new Card("Library", CardType.ROOM));

        board.getPlayers().get(0).setCards(tempHand0);
        board.getPlayers().get(1).setCards(tempHand1);
        board.getPlayers().get(2).setCards(tempHand2);
        board.getPlayers().get(3).setCards(tempHand3);
        board.getPlayers().get(4).setCards(tempHand4);
        board.getPlayers().get(5).setCards(tempHand5);
        //String testName = board.handleSuggestion(testSuggestion, 0).getName();
        assertEquals(board.handleSuggestion(testSuggestion, 0), null);
    }

    @Test
    public void testHandleSuggestionHumanDisproves() {
        //suggestion only human can disprove returns answer

        ArrayList<Card> testSuggestion = new ArrayList<Card>();
        testSuggestion.add(new Card("Name4", CardType.PERSON));
        testSuggestion.add(new Card("Butter Knife", CardType.WEAPON));
        testSuggestion.add(new Card("Lounge", CardType.ROOM));

        ArrayList<Card> tempHand0 = new ArrayList<Card>();
        tempHand0.add(new Card("Name4", CardType.PERSON));
        tempHand0.add(new Card("Katana", CardType.WEAPON));
        tempHand0.add(new Card("Kitchen", CardType.ROOM));

        ArrayList<Card> tempHand1 = new ArrayList<Card>();
        tempHand1.add(new Card("Name1", CardType.PERSON));
        tempHand1.add(new Card("Brass Knuckles", CardType.WEAPON));
        tempHand1.add(new Card("Conservatory", CardType.ROOM));

        ArrayList<Card> tempHand2 = new ArrayList<Card>();
        tempHand2.add(new Card("Name2", CardType.PERSON));
        tempHand2.add(new Card("Sharp Pencil", CardType.WEAPON));
        tempHand2.add(new Card("Dining Room", CardType.ROOM));

        ArrayList<Card> tempHand3 = new ArrayList<Card>();
        tempHand3.add(new Card("Name3", CardType.PERSON));
        tempHand3.add(new Card("Sharp Fingernail", CardType.WEAPON));
        tempHand3.add(new Card("Ballroom", CardType.ROOM));

        ArrayList<Card> tempHand4 = new ArrayList<Card>();
        tempHand4.add(new Card("Name5", CardType.PERSON));
        tempHand4.add(new Card("Glass Bottle", CardType.WEAPON));
        tempHand4.add(new Card("Study", CardType.ROOM));

        ArrayList<Card> tempHand5 = new ArrayList<Card>();
        tempHand5.add(new Card("Conservatory", CardType.ROOM));
        tempHand5.add(new Card("Billiard Room", CardType.ROOM));
        tempHand5.add(new Card("Library", CardType.ROOM));

        board.getPlayers().get(0).setCards(tempHand0);
        board.getPlayers().get(1).setCards(tempHand1);
        board.getPlayers().get(2).setCards(tempHand2);
        board.getPlayers().get(3).setCards(tempHand3);
        board.getPlayers().get(4).setCards(tempHand4);
        board.getPlayers().get(5).setCards(tempHand5);

        assertTrue(board.handleSuggestion(testSuggestion, 2).equals(new Card("Name4", CardType.PERSON)));
    }

    @Test
    public void testHandleSuggestionTwoPlayersDisprove() {
        //suggestion that two players can disprove, correct player (next in order) returns answer

        ArrayList<Card> testSuggestion = new ArrayList<Card>();
        testSuggestion.add(new Card("Name4", CardType.PERSON));
        testSuggestion.add(new Card("Butter Knife", CardType.WEAPON));
        testSuggestion.add(new Card("Lounge", CardType.ROOM));

        ArrayList<Card> tempHand0 = new ArrayList<Card>();
        tempHand0.add(new Card("Name4", CardType.PERSON));
        tempHand0.add(new Card("Katana", CardType.WEAPON));
        tempHand0.add(new Card("Kitchen", CardType.ROOM));

        ArrayList<Card> tempHand1 = new ArrayList<Card>();
        tempHand1.add(new Card("Name1", CardType.PERSON));
        tempHand1.add(new Card("Brass Knuckles", CardType.WEAPON));
        tempHand1.add(new Card("Lounge", CardType.ROOM));

        ArrayList<Card> tempHand2 = new ArrayList<Card>();
        tempHand2.add(new Card("Name2", CardType.PERSON));
        tempHand2.add(new Card("Sharp Pencil", CardType.WEAPON));
        tempHand2.add(new Card("Dining Room", CardType.ROOM));

        ArrayList<Card> tempHand3 = new ArrayList<Card>();
        tempHand3.add(new Card("Name3", CardType.PERSON));
        tempHand3.add(new Card("Sharp Fingernail", CardType.WEAPON));
        tempHand3.add(new Card("Ballroom", CardType.ROOM));

        ArrayList<Card> tempHand4 = new ArrayList<Card>();
        tempHand4.add(new Card("Name5", CardType.PERSON));
        tempHand4.add(new Card("Glass Bottle", CardType.WEAPON));
        tempHand4.add(new Card("Study", CardType.ROOM));

        ArrayList<Card> tempHand5 = new ArrayList<Card>();
        tempHand5.add(new Card("Conservatory", CardType.ROOM));
        tempHand5.add(new Card("Billiard Room", CardType.ROOM));
        tempHand5.add(new Card("Library", CardType.ROOM));

        board.getPlayers().get(0).setCards(tempHand0);
        board.getPlayers().get(1).setCards(tempHand1);
        board.getPlayers().get(2).setCards(tempHand2);
        board.getPlayers().get(3).setCards(tempHand3);
        board.getPlayers().get(4).setCards(tempHand4);
        board.getPlayers().get(5).setCards(tempHand5);

        assertTrue(board.handleSuggestion(testSuggestion, 2).equals(new Card("Name4", CardType.PERSON)));
    }

}
