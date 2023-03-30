package experiment;
import java.util.*;
public class TestBoard {

    final static int COLS = 4;
    final static int ROWS = 4;


    Set<TestBoardCell> targets;
    TestBoardCell[][] grid;

    ArrayList<TestBoardCell> visited;
    public TestBoard() {
        //creates board from csv file later in development
        //creates 4x4 empty board for now

        visited = new ArrayList<TestBoardCell>();
        targets = new HashSet<TestBoardCell>();
        grid = new TestBoardCell[ROWS][COLS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                grid[r][c] = new TestBoardCell(r,c);
                
            }
        }

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                
                if (r != 0) {
                    grid[r][c].addAdjacency(grid[r-1][c]);
                }
                if (r < ROWS - 1) {
                    grid[r][c].addAdjacency(grid[r+1][c]);
                }
                if (c != 0) {
                    grid[r][c].addAdjacency(grid[r][c-1]);
                }
                if (c < COLS - 1) {
                    grid[r][c].addAdjacency(grid[r][c+1]);
                }
                
            }
        }


        
    }

    public void calcTargets(TestBoardCell startCell, int pathLength, TestBoardCell initialCell) {
        
        visited.add(startCell);
        for (TestBoardCell t : startCell.adjList) {
            if (!visited.contains(t) && !t.isOccupied()) {
                visited.add(t);

                if (pathLength == 1) {
                    targets.add(t);
                }
                else if (t.isRoom()) {
                    targets.add(t);
                }
                else {
                    

                    calcTargets(t, pathLength - 1, initialCell);
                    
                }
                
            }
            
            visited.remove(t);
            

        }
        targets.remove(initialCell);
    }

    public TestBoardCell getCell(int row, int col) {
        return grid[row][col];
    }

    public Set<TestBoardCell> getTargets() {
        return targets;
    }

    public void clearTargets() {
        targets.clear();
        visited.clear();
    }

    public static void main(String[] args) {
        System.out.println("hello");
    }
}

