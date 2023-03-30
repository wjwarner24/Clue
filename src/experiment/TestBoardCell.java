package experiment;
import java.util.*;

public class TestBoardCell {
    public int row;
    public int col;
    Set<TestBoardCell> adjList;
    boolean room = false;
    boolean occupied = false;

    public TestBoardCell(int row, int col) {
        this.row = row;
        this.col = col;
        this.adjList = new HashSet<TestBoardCell>();


    }

    public void addAdjacency(TestBoardCell cell) {
        adjList.add(cell);
    }
    public Set<TestBoardCell> getAdjList() {

        return adjList;
    }

    public void setRoom(boolean b) {
        room = b;
    }

    public boolean isRoom() {
        return room;
    }

    public void setOccupied(boolean b) {
        occupied = b;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void print() {
        System.out.print("[" + row + "," + col + "] adj: ");
        if (adjList.isEmpty()) {
            System.out.println("none");
        }
        for (TestBoardCell t : adjList) {
            System.out.print("[" + t.row + "," + t.col + "] ");
        }
        System.out.println();
    }

    
}