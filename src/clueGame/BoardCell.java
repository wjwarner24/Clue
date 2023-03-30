package clueGame;

import java.util.*;

//BoardCell Class
//Author: William Warner
//Created March 5th, 2023
//No Collaboraters or outside sources

//BoardCell represents one cell in the Clue game
//Each BoardCell knows its row, collumn, what room it is in
//Each BoardCell also knows whether it is the center or label of a room
//BoardCells can be occupied
//Each BoardCell contains a list of all the BoardCells it is adjacent to
//Each BoardCell also knows whether it is a door, and which direction that door is

public class BoardCell {
    private int row;
    private int col;
    private Set<BoardCell> adjList;

    private boolean occupied;
    private String name;
    private DoorDirection direction;
    private Room room;
    private char secretPassage;

    public BoardCell(int row, int col, String n) {
        this.row = row;
        this.col = col;
        this.name = n;
        this.adjList = new HashSet<BoardCell>();
        this.secretPassage = ' ';

    }

    public void addAdjacency(BoardCell cell) {
        adjList.add(cell);
    }

    public Set<BoardCell> getAdjList() {
        return adjList;
    }

    public void setRoom(Room r) {
        this.room = r;
    }

    public boolean isRoom() {
        if (this.name.charAt(0) == 'X' || this.name.charAt(0) == 'W') {
            return false;
        }
        return true;
    }

    public Room getRoom() {
        return room;
    }

    public void setOccupied(boolean b) {
        occupied = b;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean isDoorway() {
        if (direction == DoorDirection.NONE) {
            return false;
        }
        return true;
    }

    public DoorDirection getDoorDirection() {
        return direction;
    }

    public String getName() {
        return name;
    }

    public boolean isRoomCenter() {
        if (name.contains("*")) {
            return true;
        }
        return false;
    }

    public boolean isLabel() {
        if (name.contains("#")) {
            return true;
        }
        return false;
    }

    public char getSecretPassage() {
        return secretPassage;
    }

    public void setDoorDirection(DoorDirection d) {
        this.direction = d;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setSecretPassage(char c) {
        this.secretPassage = c;
    }
    public void clearAdjList() {
        adjList.clear();
    }

}
