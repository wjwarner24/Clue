package clueGame;
import java.util.Random;
import java.util.ArrayList;

//HumanPlayer Class
//Author: William Warner
//Created April 2nd, 2023
//No Collaboraters or outside sources

//HumanPlayer represents a human player in the Clue Game
//HumanPlayer extends Player class and uses those methods
//further implementation for game functionality is needed

public class HumanPlayer extends Player {
    public HumanPlayer(String name, String color, int row, int col, boolean isHuman) {
        super(name, color, row, col, isHuman);
    }

    //handles a human player turn
    @Override
    public void handleTurn() {
        super.setFinished(false);
        Random rand = new Random();
        int diceRoll = rand.nextInt(6) + 1;
        GameControlPanel.getInstance().setTurn(this);
        GameControlPanel.getInstance().setDiceRoll(diceRoll);
        CardsPanel.getInstance().setHand(super.getCards());
        ArrayList<Card> seenCards = new ArrayList<Card>(super.getSeenCards());
        CardsPanel.getInstance().setSeenCards(seenCards);
        CardsPanel.getInstance().refresh();
        BoardCell currentCell = Board.getInstance().getCell(super.getRow(), super.getCol());
        Board.getInstance().calcTargets(currentCell, diceRoll, currentCell);
        for (BoardCell bc : Board.getInstance().getTargets()) {
            bc.highlight();
        }
        Board.getInstance().repaint();
        //waits until player selects a cell to move to
        //located in Board Class

    }
}
