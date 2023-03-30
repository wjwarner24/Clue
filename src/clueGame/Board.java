package clueGame;

import java.util.*;
import java.io.*;


//Board Class
//Author: William Warner
//Created March 5th, 2023
//No Collaboraters or outside sources

//Board represents a Board in the Clue game
//Contains all BoardCells and Rooms in the game
//Handles the setup of the board from data files
//Initializes Each BoardCell based on data files
//Calculates targets given a starting cell and dice roll



public class Board {
    private int cols;
    private int rows;
    private BoardCell[][] board;
    private Set<BoardCell> targets;
    private Set<BoardCell> visited;
    private File setupFile;
    private File layoutFile;
    private ArrayList<Room> rooms;

    private static Board theInstance = new Board();

    //private constructor so can only be created once, directly above this comment
    private Board() {
        super();
        visited = new HashSet<BoardCell>();
        rooms = new ArrayList<Room>();
        targets = new HashSet<BoardCell>();
    }

    //returns this instance
    public static Board getInstance() {
        return theInstance;
    }
    
    //calculates BoardCell targets given a starting cell and dice roll
    public void calcTargets(BoardCell startCell, int pathLength, BoardCell initialCell) {
        if (startCell == initialCell) {
            clearTargets();
        }
        visited.add(startCell);
        for (BoardCell t : startCell.getAdjList()) {
            if (!visited.contains(t)) {
                
                if (t.isRoom()) {
                    targets.add(t);
                }
                else if (t.isOccupied()) {

                }
                else if (pathLength == 1) {
                    targets.add(t);
                }
                else {
                    calcTargets(t, pathLength - 1, initialCell);
                    visited.remove(t);
                } 
            }
        }
    }

    //returns a BoardCell based on the given row and collumn
    public BoardCell getCell(int row, int col) {
        return board[row][col];
    }

    //returns targets (must calculate targets first)
    public Set<BoardCell> getTargets() {
        return targets;
    }

    public void setConfigFiles(String layoutF, String setupF) {
        this.layoutFile = new File(layoutF);
        this.setupFile = new File(setupF);
    }

    //parses the setup file to initialize rooms
    public void loadSetupConfig() throws BadConfigFormatException {
        rooms.clear();
        Scanner scanner;

        try {
            String line = "";
            scanner = new Scanner(this.setupFile);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (!line.contains("//")) {
                    String[] arr = line.split(", ");
                    //setup file must be in format: Room, Name, 'N'
                    if (!(arr[0].equals("Room") || arr[0].equals("Space"))) {
                        scanner.close();
                        throw new BadConfigFormatException("Bad format for Setup file");
                    }
                    rooms.add(new Room(arr[1], arr[2].charAt(0)));
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Setup file not found");
            System.exit(1);
        }
    }

    //parses layout file, initializes each BoardCell from that file
    //adds info for each BoardCell based on layout file
    public void loadLayoutConfig() throws BadConfigFormatException {

        Scanner scanner;
        try {
            scanner = new Scanner(this.layoutFile);
            int tempRows = 0;

            String line = "";
            while (scanner.hasNextLine()) {
                tempRows++;
                line = scanner.nextLine();

            }
            String[] chars = line.split(",");

            this.cols = chars.length;
            this.rows = tempRows;
            scanner.close();

            board = new BoardCell[rows][cols];

            // reads the info of each cell
            scanner = new Scanner(layoutFile);
            for (int r = 0; r < rows; r++) {
                line = scanner.nextLine();
                chars = line.split(",");
                for (int c = 0; c < cols; c++) {
                    // Throws exception if each collumn is not expected length
                    if (cols != chars.length) {
                        scanner.close();
                        System.err.println("Collumns are not consistent among each row");
                        throw new BadConfigFormatException("Collumns are not consistent among each row");
                    }

                    board[r][c] = new BoardCell(r, c, chars[c]);
                    if (chars[c].contains(">")) {
                        board[r][c].setDoorDirection(DoorDirection.RIGHT);
                    } else if (chars[c].contains("<")) {
                        board[r][c].setDoorDirection(DoorDirection.LEFT);
                    } else if (chars[c].contains("^")) {
                        board[r][c].setDoorDirection(DoorDirection.UP);
                    } else if (chars[c].contains("v")) {
                        board[r][c].setDoorDirection(DoorDirection.DOWN);
                    } else {
                        board[r][c].setDoorDirection(DoorDirection.NONE);
                    }

                    Room tempRoom = this.getRoom(chars[c].charAt(0));
                    if (tempRoom.getName().equals("error")) {
                        scanner.close();
                        throw new BadConfigFormatException("BoardCell contains room not specified in setup file [" + r + ", " + c + "]");
                    }
                    board[r][c].setRoom(tempRoom);
                    if (board[r][c].isLabel()) {
                        board[r][c].getRoom().setlabelCell(board[r][c]);
                    }
                    if (board[r][c].isRoomCenter()) {
                        board[r][c].getRoom().setCenterCell(board[r][c]);
                    }
                    if (board[r][c].getName().length() > 1 && Character.isAlphabetic(board[r][c].getName().charAt(1))) {
                        board[r][c].setSecretPassage(board[r][c].getName().charAt(1));
                        board[r][c].getRoom().setPassageDestination(this.getRoom(board[r][c].getName().charAt(1)));
                    }

                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Layout File not found");
            System.exit(1);
        }

    }
    //calls functions to load setup and layout configs
    //adds adjacencies to Each BoardCell
    public void initialize() {
        targets.clear();
        try {
            this.loadSetupConfig();
        } catch (BadConfigFormatException e) {
            System.err.println("Bad Configuration for setup file");
        }
        try {
            this.loadLayoutConfig();
        } catch (BadConfigFormatException e) {
            System.err.println("Bad Configuration for layout file");
        }
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c].getName().charAt(0) == 'W') {

                    if (r != 0 && board[r-1][c].getName().charAt(0) == 'W') {
                        board[r][c].addAdjacency(board[r - 1][c]);
                    }
                    if (r < rows - 1 && board[r+1][c].getName().charAt(0) == 'W') {
                        board[r][c].addAdjacency(board[r + 1][c]);
                    }
                    if (c != 0 && board[r][c-1].getName().charAt(0) == 'W') {
                        board[r][c].addAdjacency(board[r][c - 1]);
                    }
                    if (c < cols - 1 && board[r][c+1].getName().charAt(0) == 'W') {
                        board[r][c].addAdjacency(board[r][c + 1]);
                    }

                    if (board[r][c].isDoorway()) {
                        DoorDirection tempDirection = board[r][c].getDoorDirection();
                        Room tempRoom = null;
                        if (tempDirection == DoorDirection.UP) {
                            tempRoom = this.getRoom(board[r-1][c].getName().charAt(0));
                        }
                        else if (tempDirection == DoorDirection.DOWN) {
                            tempRoom = this.getRoom(board[r+1][c].getName().charAt(0));
                        }
                        else if (tempDirection == DoorDirection.LEFT) {
                            tempRoom = this.getRoom(board[r][c-1].getName().charAt(0));
                        }
                        else if (tempDirection == DoorDirection.RIGHT) {
                            tempRoom = this.getRoom(board[r][c+1].getName().charAt(0));
                        }
                        else {
                            System.err.print("door direction error");
                        }
                        if (tempRoom.getCenterCell() == null) {
                            System.err.println(tempRoom.getName());
                            System.err.println("(" + r + ", " + c + ")");
                            System.err.println();
                        }
        
                        BoardCell tempCenter = tempRoom.getCenterCell();
                        
                        board[r][c].addAdjacency(tempCenter);
                        tempCenter.addAdjacency(board[r][c]);
                    }

                }

                if (board[r][c].isRoomCenter() && board[r][c].getRoom().getPassageDestination() != null) {
                    board[r][c].addAdjacency(board[r][c].getRoom().getPassageDestination().getCenterCell());
                }

            }
        }

    }

    public int getNumRows() {
        return this.rows;
    }

    public int getNumColumns() {
        return this.cols;
    }

    public Room getRoom(char c) {
        for (Room r : rooms) {
            if (r.getSymbol() == c) {
                return r;
            }
        }
        return new Room("error", 'e');
    }

    public Room getRoom(BoardCell bc) {
        return bc.getRoom();
    }

    public Set<BoardCell> getAdjList(int r, int c) {
        return board[r][c].getAdjList();
    }

    public void clearTargets() {
        targets.clear();
        visited.clear();
    }
}