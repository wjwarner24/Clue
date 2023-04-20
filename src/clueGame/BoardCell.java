package clueGame;

import java.util.*;
import java.awt.Color;
import java.awt.Font;
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

    public boolean isWalkway() {
        if (name.charAt(0) == 'W') {
            return true;
        }
        return false;
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

    //parameters are Graphics, cellWidth, cellHeight, BoardCell
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

    public void drawRoomName(Graphics g, int cellWidth, int cellHeight) {
        int leftBorder = col * cellWidth;
        int topBorder = row * cellHeight;
        if (isRoomCenter()) {
            g.setColor(Color.black);
            g.drawString(getRoom().getName(), leftBorder, topBorder);
        }
        return;
    
    }

    public void highlightCell(Graphics g, int cellWidth, int cellHeight) {
        g.setColor(Color.blue);
        int leftBorder = col * cellWidth;
        int topBorder = row * cellHeight;
        g.drawRect(leftBorder, topBorder, cellWidth, cellHeight);
        g.fillRect(leftBorder, topBorder, cellWidth, cellHeight);

    }

    public void highlight() {
        isHighlighted = true;
    }
    public void unhighlight() {
        isHighlighted = false;
    }

    public boolean highlighted() {
        return isHighlighted;
    }

    public boolean containsClick(int x, int y, int cellWidth, int cellHeight) {
        int leftX = col * cellWidth;
        int rightX = leftX + cellWidth;
        int topY = row * cellHeight;
        int bottomY = topY + cellHeight;
        
        return (x >= leftX && x < rightX && y >= topY && y < bottomY);
    }

}
