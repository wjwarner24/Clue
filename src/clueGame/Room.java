package clueGame;

//Room Class
//Author: William Warner
//Created March 5th, 2023
//No Collaboraters or outside sources

//Room class represents a room in the Clue game
//Each Room has a name, a char symbol, and a center and label cell

public class Room {
    private String name;
    private char symbol;
    private BoardCell labelCell;
    private BoardCell centerCell;
    private Room passageDestination;

    public Room(String n, char c) {
        name = n;
        symbol = c;
        labelCell = null;
        centerCell = null;
        passageDestination = null;
    }

    public boolean isRoom() {
        return true;
    }

    public String getName() {
        return name;
    }

    public BoardCell getCenterCell() {
        return centerCell;
    }

    public BoardCell getLabelCell() {
        return labelCell;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setlabelCell(BoardCell cell) {
        this.labelCell = cell;
    }

    public void setCenterCell(BoardCell cell) {
        this.centerCell = cell;
    }

    public void setPassageDestination(Room dest) {
        passageDestination = dest;
    }
    public Room getPassageDestination() {
        return passageDestination;
    }
}
