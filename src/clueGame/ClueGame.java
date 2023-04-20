package clueGame;

import java.awt.*;

import javax.swing.*;

//ClueGame Class
//Author: William Warner
//Created April 12th, 2023
//No Collaboraters or outside sources

//This is the entry point for the clue game

public class ClueGame extends JFrame {
    

    CardsPanel cardsPanel = CardsPanel.getInstance();
    GameControlPanel gameControlPanel = GameControlPanel.getInstance();
    Board board = Board.getInstance();

    private ClueGame() {
        //Board board = Board.getInstance();
        board.setSize(550,750);
        board.setConfigFiles("/Users/wjwarner24/desktop/Clue2/src/data/ClueLayout.csv", "/Users/wjwarner24/desktop/Clue2/src/data/ClueSetup.txt");		
		board.initialize();
        //CardsPanel cardsPanel = CardsPanel.getInstance();
        cardsPanel.setSize(300,750);
        //GameControlPanel gameControlPanel = GameControlPanel.getInstance();
        gameControlPanel.setSize(850,180);
        add(board, BorderLayout.CENTER);
        add(cardsPanel, BorderLayout.EAST);
        add(gameControlPanel, BorderLayout.SOUTH);
        

    }
    



    public static void main(String[] args) {
        ClueGame game = new ClueGame();
        
        game.setSize(750,930);
        //frame.setContentPane(game);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		game.setVisible(true);
        JOptionPane.showMessageDialog(null, "You are Miss Scarlett.\nCan you find the solution\nbefore the computer players?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

        Board.getInstance().startGame();
        //CardsPanel.getInstance().refresh();



    }
}
