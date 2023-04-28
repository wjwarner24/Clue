package clueGame;

import java.util.*;
import java.awt.Color;
import java.awt.Graphics;

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
    private boolean isHighlighted = false;
    public static final int DOOR_SIZE = 5;

    public BoardCell(int row, int col, String n) {
        this.row = row;
        this.col = col;
        this.name = n;
        this.adjList = new HashSet<BoardCell>();
        this.secretPassage = ' ';

    }
    //adds cell to the adjacnency list of this cell
    public void addAdjacency(BoardCell cell) {
        adjList.add(cell);
    }
    //returns the adj list for this cell
    public Set<BoardCell> getAdjList() {
        return adjList;
    }
    //sets the room that this cell is in
    public void setRoom(Room r) {
        this.room = r;
    }
    //returns whether this cell is in a room
    public boolean isRoom() {
        if (this.name.charAt(0) == 'X' || this.name.charAt(0) == 'W') {
            return false;
        }
        return true;
    }
    //returns the room that this cell is in
    public Room getRoom() {
        return room;
    }
    //sets whether this cell is occupied
    public void setOccupied(boolean b) {
        occupied = b;
    }
    //returns whether this cell is occupied
    public boolean isOccupied() {
        return occupied;
    }
    //returns whether this cell is a doorway
    public boolean isDoorway() {
        if (direction == DoorDirection.NONE) {
            return false;
        }
        return true;
    }
    //returns whether this cell is a walkway
    public boolean isWalkway() {
        if (name.charAt(0) == 'W') {
            return true;
        }
        return false;
    }
    //gets door direction
    public DoorDirection getDoorDirection() {
        return direction;
    }
    //returns this cell's name
    public String getName() {
        return name;
    }
    //returns whether this cell is a room center
    public boolean isRoomCenter() {
        if (name.contains("*")) {
            return true;
        }
        return false;
    }
    //returns whether this cell is a label cell
    public boolean isLabel() {
        if (name.contains("#")) {
            return true;
        }
        return false;
    }
    //gets secret passage
    public char getSecretPassage() {
        return secretPassage;
    }

    //sets door direction
    public void setDoorDirection(DoorDirection d) {
        this.direction = d;
    }
    //gets row
    public int getRow() {
        return row;
    }
    //gets col
    public int getCol() {
        return col;
    }
    //sets secret passage
    public void setSecretPassage(char c) {
        this.secretPassage = c;
    }
    //clears adj list
    public void clearAdjList() {
        adjList.clear();
    }

    //draws the BoardCell
    public void draw(Graphics g, int cellWidth, int cellHeight) {
       

        int leftBorder = col * cellWidth;
        int topBorder = row * cellHeight;
        if (isWalkway()) {
            g.setColor(Color.black);
            g.drawRect(leftBorder, topBorder, cellWidth, cellHeight);
            g.fillRect(leftBorder, topBorder, cellWidth, cellHeight);
            g.setColor(Color.yellow);
            g.drawRect(leftBorder + 1, topBorder + 1, cellWidth - 2, cellHeight - 2);
            g.fillRect(leftBorder + 1, topBorder + 1, cellWidth - 2, cellHeight - 2);
            if (isHighlighted) {
                g.setColor(Color.CYAN);
                g.drawRect(leftBorder + 1, topBorder + 1, cellWidth - 2, cellHeight - 2);
                g.fillRect(leftBorder + 1, topBorder + 1, cellWidth - 2, cellHeight - 2);
            }

            return;
            
        }
        else if (isRoom()) {
            if (getRoom().getCenterCell().isHighlighted) {
                g.setColor(Color.cyan);
                g.drawRect(leftBorder, topBorder, cellWidth, cellHeight);
                g.fillRect(leftBorder, topBorder, cellWidth, cellHeight);
                return;
            }
            else if (secretPassage != ' ') {
                return;
            }
            g.setColor(Color.gray);
            g.drawRect(leftBorder, topBorder, cellWidth, cellHeight);
            g.fillRect(leftBorder, topBorder, cellWidth, cellHeight);
    
            return;
        }
        else {
            g.setColor(Color.black);
            g.drawRect(leftBorder, topBorder, cellWidth, cellHeight);
            g.fillRect(leftBorder, topBorder, cellWidth, cellHeight);
            return;
        }

        

    }
    //draws the door
    public void drawDoor(Graphics g, int cellWidth, int cellHeight) {

        int leftBorder = col * cellWidth;
        int topBorder = row * cellHeight;
        if (isDoorway()) {
            g.setColor(Color.BLUE);
            int doorwayLeftBorder;
            int doorwayTopBorder;
            if (direction == DoorDirection.LEFT) {
                doorwayLeftBorder = leftBorder - DOOR_SIZE;
                g.drawRect(doorwayLeftBorder, topBorder, DOOR_SIZE, cellHeight);
                g.fillRect(doorwayLeftBorder, topBorder, DOOR_SIZE, cellHeight);
            }
            else if (direction == DoorDirection.RIGHT) {
                doorwayLeftBorder = leftBorder + cellWidth;
                g.drawRect(doorwayLeftBorder, topBorder, DOOR_SIZE, cellHeight);
                g.fillRect(doorwayLeftBorder, topBorder, DOOR_SIZE, cellHeight);
            }
            else if (direction == DoorDirection.UP) {
                doorwayTopBorder = topBorder - DOOR_SIZE;
                g.drawRect(leftBorder, doorwayTopBorder, cellWidth, DOOR_SIZE);
                g.fillRect(leftBorder, doorwayTopBorder, cellWidth, DOOR_SIZE);
            }
            else if (direction == DoorDirection.DOWN) {
                doorwayTopBorder = topBorder + cellHeight;
                g.drawRect(leftBorder, doorwayTopBorder, cellWidth, DOOR_SIZE);
                g.fillRect(leftBorder, doorwayTopBorder, cellWidth, DOOR_SIZE);
            }
        }
    }
    //draws the room name
    public void drawRoomName(Graphics g, int cellWidth, int cellHeight) {
        int leftBorder = col * cellWidth;
        int topBorder = row * cellHeight;
        if (isRoomCenter()) {
            g.setColor(Color.black);
            g.drawString(getRoom().getName(), leftBorder, topBorder);
        }
        return;
    
    }
   
    public void highlight() {
        isHighlighted = true;
    }
    public void unhighlight() {
        isHighlighted = false;
    }
    //returns status of 
    public boolean highlighted() {
        return isHighlighted;
    }
    //returns true if given x,y coords are within cell
    //returns false if not
    public boolean containsClick(int x, int y, int cellWidth, int cellHeight) {
        int leftX = col * cellWidth;
        int rightX = leftX + cellWidth;
        int topY = row * cellHeight;
        int bottomY = topY + cellHeight;
        
        return (x >= leftX && x < rightX && y >= topY && y < bottomY);
    }
}