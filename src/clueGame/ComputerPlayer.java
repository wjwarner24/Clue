package clueGame;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Collections;

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
        Set<BoardCell> targets = Board.getInstance().getTargets();
        BoardCell[] targetArray = targets.toArray(new BoardCell[targets.size()]);
        int size = targetArray.length;
        Random random = new Random();
        int rand = random.nextInt(size);
        return targetArray[rand];

    }

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
}
