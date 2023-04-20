package clueGame;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.Class;
import java.awt.event.*;
import java.lang.Object;

//ClueGame Class
//Author: William Warner
//Created April 12th, 2023
//No Collaboraters or outside sources

//this is the control panel which diplays whose turn it is
//and other game functions

public class GameControlPanel extends JPanel {
    private Player playerTurn = new ComputerPlayer("Colonel Mustard", "yellow", 0, 0, false);
    private int turnNumber;
    private String guess = "I have no guess!";
    private String guessResult = "So you have nothing?";
    private int diceRoll = 0;
    JButton makeAccusationButton = new JButton("Make Accusation");
    JButton nextButton = new JButton("Next");
    JTextField player = new JTextField(playerTurn.getName());
    JTextField rollNum = new JTextField("" + diceRoll);

	private static GameControlPanel theInstance = new GameControlPanel();

	public GameControlPanel()  {
		setLayout(new GridLayout(2,0));
        JPanel upperPanel = createUpperPanel();
        JPanel lowerPanel = createLowerPanel();
        add(upperPanel);
        add(lowerPanel);
        nextButton.addActionListener(new NextButtonListener());
	}

    public static GameControlPanel getInstance() {
        return theInstance;
    }

    private JPanel createUpperPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,4));


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2,0));
        JLabel whoseTurn = new JLabel("Whose Turn?");
        leftPanel.add(whoseTurn);
        //JTextField player = new JTextField(playerTurn.getName());

        // Color color;
        // try {
        // Field field = Class.forName("java.awt.Color").getField(playerTurn.getColor());
        // color = (Color)field.get(null);
        // }
        // catch (Exception e) {
        //     color = null;
        // }
        // player.setBackground(color);
        // player.setEditable(false);
        leftPanel.add(player);
        panel.add(leftPanel);

        JPanel rightPanel = new JPanel();
        
        JLabel roll = new JLabel("Roll");
        rightPanel.add(roll);
        //JTextField rollNum = new JTextField("" + diceRoll);
        rollNum.setEditable(false);
        rightPanel.add(rollNum);

        panel.add(rightPanel);

        JButton makeAccusationButton = new JButton("Make Accusation");
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new NextButtonListener());
        panel.add(makeAccusationButton);
        panel.add(nextButton);






        return panel;
    }

    private JPanel createLowerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,2));

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();



        leftPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
        rightPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));

        JTextField leftText = new JTextField(guess);
        leftText.setEditable(false);
        JTextField rightText = new JTextField(guessResult);
        rightText.setEditable(false);
        leftPanel.add(leftText);
        rightPanel.add(rightText);


        leftPanel.setLayout(new GridLayout(1,0));
        rightPanel.setLayout(new GridLayout(1,0));



        panel.add(leftPanel);
        panel.add(rightPanel);

        return panel;

    }
	
	public void setTurn(Player person) {
        playerTurn = person;
        //turnNumber = num;
        player.setText(playerTurn.getName());
        Color color;
        try {
            Field field = Class.forName("java.awt.Color").getField(playerTurn.getColor());
            color = (Color)field.get(null);
        }
        catch (Exception e) {
            color = null;
        }
        player.setBackground(color);
        player.setEditable(false);

    }

    public void setGuess(String str) {
        guess = str;
    }

    public void setGuessResult(String str) {
        guessResult = str;
    }

    public void setDiceRoll(int roll) {
        diceRoll = roll;
        rollNum.setText("" + diceRoll);
    }
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		// panel.setTurn(new ComputerPlayer( "Col. Mustard", "Orange", 0, 0, false), 5);
		// panel.setGuess( "I have no guess!");
		// panel.setGuessResult( "So you have nothing?");
	}

    class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CardsPanel.getInstance().refresh();
            Board.getInstance().nextButtonPressed();

        }
    }
}

// private class NextButtonListener implements ActionListener {

//  }
