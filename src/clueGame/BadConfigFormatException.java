package clueGame;
import java.io.*;

//BadConfigFormatException Class
//Author: William Warner
//Created March 5th, 2023
//No Collaboraters or outside sources

//BadConfigFormatException gets thrown by Board.java when it detects that
//the setup or layout files are formatted incorrectly

//such cases include; when the layout.csv file does not have the same number of collumns in each row,
//when the setup.txt file does not specify that a room is a room, and when the layout.csv file contains
//a cell that has a label that does not occur in the setup.txt file

//BadConfigFormatException writes the errors to errorLog.txt when it is thrown
public class BadConfigFormatException extends Exception{
    public BadConfigFormatException() {
        super();
        writeToFile("Unspecified BadConfigFormatException");
    }

    public BadConfigFormatException(String s) {
        super(s);
        writeToFile("BadConfigFormatException: " + s);
    }

    private void writeToFile(String s) {

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("/Users/wjwarner24/desktop/Software Engineering/Clue/errorLog.txt"));
            writer.write(s);
            writer.close();
        }
        catch (IOException e) {

        }
    }
}
