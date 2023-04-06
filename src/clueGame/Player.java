package clueGame;

import java.util.*;

//Player Class
//Author: William Warner
//Created April 2nd, 2023
//No Collaboraters or outside sources

//Player represents a player in the Clue game
//This is an abstract class that is a parent to HumanPlayer and ComnputerPlayer
//contains info on the player as shown in the member variables below


public abstract class Player {
    private String name;
    private String color;
    private int row;
    private int col;
    private ArrayList<Card> cards;
    private Set<Card> seenCards;
    private boolean isHuman;
    private int number;

    public Player() {

    }

    public Player(String name, String color, int row, int col, boolean isHuman) {
        this.name = name;
        this.color = color;
        this.row = row;
        this.col = col;
        this.isHuman = isHuman;
        cards = new ArrayList<Card>();
        seenCards = new HashSet<Card>();
    }

    public void setNumber(int num) {
        number = num;
    }

    public int getNumber() {
        return number;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public void clearHand() {
        cards.clear();
    }

    //if player has a card in the suggestion, it must show that card
    //if not, return null
    public Card disproveSuggestion(ArrayList<Card> suggestion) {
        for (Card c : suggestion) {
            if (cards.get(0).equals(c)) {
                return cards.get(0);
            }
            if (cards.get(1).equals(c)) {
                return cards.get(1);
            }
            if (cards.get(2).equals(c)) {
                return cards.get(2);
            }
        }
        return null;
    }

    public void addSeenCard(Card card) {
        seenCards.add(card);
    }

    //allows us to set the cards for testing
    public void setCards(ArrayList<Card> hand) {
        cards = hand;
    }

    public void move(BoardCell dest) {
        row = dest.getRow();
        col = dest.getCol();
        dest.setOccupied(true);
    }

    public BoardCell getCell() {
        return Board.getInstance().getCell(getRow(), getCol());
    }

    public Room getRoom() {
        return Board.getInstance().getCell(getRow(), getCol()).getRoom();
    }

    public Set<Card> getSeenCards() {
        return seenCards;
    }
}
