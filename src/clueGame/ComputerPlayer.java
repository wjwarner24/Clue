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
            
        }
        else {
            
            Random rand = new Random();
            int randInt = rand.nextInt(possibleWeapons.size());
            weaponSuggestion = possibleWeapons.get(randInt);

        }
        
        if (possiblePeople.size() == 0) {
            //choose random from personCards
            
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
        Random rand = new Random();
        int diceRoll = rand.nextInt(6) + 1;
        GameControlPanel.getInstance().setTurn(this);
        GameControlPanel.getInstance().setDiceRoll(diceRoll);
        BoardCell destination = this.selectTarget(diceRoll);
        if (destination != null) {
            super.move(destination);
        }
        super.setFinished(true);
    }
}