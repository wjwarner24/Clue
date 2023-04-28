package clueGame;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

//ComputerPlayer Class
//Author: William Warner
//Created April 2nd, 2023
//No Collaboraters or outside sources

//ComputerPlayer represents a computer player in the Clue Game
//ComputerPlayer extends Player class and uses those methods
//further implementation for game functionality is needed

public class ComputerPlayer extends Player {

    Set<Room> seenRooms = new HashSet<Room>();
    boolean makeAccusation = false;
    ArrayList<Card> pendingAccusation;

    public ComputerPlayer(String name, String color, int row, int col, boolean isHuman) {
        super(name, color, row, col, isHuman);
    }

    //selects a target given a diceRoll
    //if it has an opportunity to visit a room it has not visited yet, it will do that
    //otherwise it is random
    public BoardCell selectTarget(int diceRoll) {
        
        BoardCell position = Board.getInstance().getCell(super.getRow(), super.getCol());
        if (position.isRoom()) {
            seenRooms.add(position.getRoom());
        }
        Board.getInstance().calcTargets(position, diceRoll, position);

        for (BoardCell bc : Board.getInstance().getTargets()) {
            if (bc.isRoom() && !seenRooms.contains(bc.getRoom())) {
                return bc;
            }
        }
        
        ArrayList<BoardCell> targetArrayList = new ArrayList<BoardCell>(Board.getInstance().getTargets());
        
        int size = targetArrayList.size();
        if (size == 0) {
            return null; //special case where there are no targets
        }
        Random random = new Random();
        int rand = random.nextInt(size);
        return targetArrayList.get(rand);

    }

    //creates a suggestion that does not include any cards that it has seen before
    public ArrayList<Card> createSuggestion() {

        BoardCell position = Board.getInstance().getCell(super.getRow(), super.getCol());
        String roomName = position.getRoom().getName();
        Card roomSuggestion = new Card(roomName, CardType.ROOM);
        
        Card weaponSuggestion = null;
        Card personSuggestion = null;

        ArrayList<Card> possibleWeapons = new ArrayList<Card>();
        
        for (Card c : Board.getInstance().getWeaponCards()) {
            boolean contains = false;
            for (Card p : getSeenCards()) {
                if (c.equals(p)) {
                    contains = true;
                }
            }
            if (!contains) {
                possibleWeapons.add(c);
            }
        }

        ArrayList<Card> possiblePeople = new ArrayList<Card>();

        for (Card c : Board.getInstance().getPersonCards()) {
            boolean contains = false;
            for (Card p : getSeenCards()) {
                if (c.equals(p)) {
                    contains = true;
                }
            }
            if (!contains) {
                possiblePeople.add(c);
            }
        }

       
        if (possibleWeapons.size() == 0) {
            //choose random from weaponcards
            Random rand = new Random();
            ArrayList<Card> allWeapons = new ArrayList<Card>(Board.getInstance().getWeaponCards());
            int randInt = rand.nextInt(allWeapons.size());
            weaponSuggestion = allWeapons.get(randInt);
            
        }
        else {
            
            Random rand = new Random();
            int randInt = rand.nextInt(possibleWeapons.size());
            weaponSuggestion = possibleWeapons.get(randInt);

        }
        
        if (possiblePeople.size() == 0) {
            //choose random from personCards
            Random rand = new Random();
            ArrayList<Card> allPersons = new ArrayList<Card>(Board.getInstance().getPersonCards());
            int randInt = rand.nextInt(allPersons.size());
            personSuggestion = allPersons.get(randInt);
            
        }
        else {
            
            Random rand = new Random();
            int randInt = rand.nextInt(possiblePeople.size());
            personSuggestion = possiblePeople.get(randInt);

        }
        

        ArrayList<Card> suggestion = new ArrayList<Card>();
        suggestion.add(roomSuggestion);
        suggestion.add(weaponSuggestion);
        suggestion.add(personSuggestion);
        return suggestion;
    }

    //allows us to set a room as seen for testing
    public void addSeenRoom(Room room) {
        seenRooms.add(room);
    }

    //handles a computer player's turn
    @Override
    public void handleTurn() {
        super.setFinished(false);

        if (makeAccusation) {
            //make an accusation
            Solution solution = new Solution(pendingAccusation);
            boolean correctAccusation = Board.getInstance().checkAccusation(solution);
            if (correctAccusation) {
                //computer accusation is correct, display popup and end game
                Board.getInstance().computerWinGame(this, solution);
            }
            else {
                //computer accusation is wrong, this should never occur
                System.out.println("ERROR: " + this.getName() + " accusation was wrong");
            }
        }

        Random rand = new Random();
        int diceRoll = rand.nextInt(6) + 1;
        GameControlPanel.getInstance().setTurn(this);
        GameControlPanel.getInstance().setDiceRoll(diceRoll);
        BoardCell destination = this.selectTarget(diceRoll);
        if (destination != null) {
            super.move(destination);
        }
        if (destination.isRoomCenter()) {
            //handle computer suggestion
            ArrayList<Card> tempSuggestion = this.createSuggestion();

            //move suggested player into room
            Card personCard = null;
            for (Card c : tempSuggestion) {
                if (c.getCardType() == CardType.PERSON) {
                    personCard = c;
                    break;
                }
            }
            for (Player p : Board.getInstance().getPlayers()) {
                if (p.getName().equals(personCard.getName())) {
                    //BoardCell dest = Board.players.get(turnNum).getRoom().getCenterCell();
                    p.move(destination);
                    break;
                }
            }
            //end moving suggested player into room

            String guess = tempSuggestion.get(0).getName() + ", " + tempSuggestion.get(1).getName() + ", " + tempSuggestion.get(2).getName();
            GameControlPanel.getInstance().setGuess(guess);
            int turnNum = Board.getInstance().getTurnNum();
            Card disprovedCard = Board.getInstance().handleSuggestion(tempSuggestion, turnNum);
            if (disprovedCard == null) {
                //suggestion could not be disproved
                GameControlPanel.getInstance().setGuessResult("Could not be disproved!");
                this.makeAccusation = true; // makes accusation on next turn
                pendingAccusation = tempSuggestion;
            }
            else {
                //suggestion was disproved, show in gameControlPanel
                //if human player, show disproved card and who disproved it
                //if computer, display that it was disproved or not and by who. do not show card
                Player disprovingPlayer = null;
                for (Player p : Board.getInstance().getPlayers()) {
                    if (p.getCards().contains(disprovedCard)) {
                        disprovingPlayer = p;
                        break;
                    }
                }
                GameControlPanel.getInstance().setGuessResult("Guess was disproved by " + disprovingPlayer.getName());
                Board.getInstance().getPlayers().get(turnNum).addSeenCard(disprovedCard); //make player see disproved card
            }
            GameControlPanel.getInstance().refresh(); //TODO: make this not bug out the game control panel

        }
        super.setFinished(true);
    }

    public boolean getAccusationStatus() {
        return makeAccusation;
    }

    public void setAccusationStatus(boolean b) {
        makeAccusation = b;
    }
}