package clueGame;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;

//ClueGame Class
//Author: William Warner
//Created April 12th, 2023
//No Collaboraters or outside sources

//this is the control panel which diplays whose turn it is
//and other game functions

public class GameControlPanel extends JPanel {
    private Player playerTurn = new ComputerPlayer("testPlayer", "yellow",3,3,false);
    private String guess = "";
    private String guessResult = "";
    private int diceRoll = 0;
    // private JButton accusationButton = new JButton("Make Accusation");
    // private JButton nextButton = new JButton("Next");
    private JTextField player = new JTextField(playerTurn.getName());
    private JTextField rollNum = new JTextField("" + diceRoll);

	private static GameControlPanel theInstance = new GameControlPanel();

	private GameControlPanel()  {

        
        // nextButton.addActionListener(new NextButtonListener());
        // accusationButton.addActionListener(new AccusationButtonListener());

		setLayout(new GridLayout(2,0));
        JPanel upperPanel = createUpperPanel();
        JPanel lowerPanel = createLowerPanel();
        add(upperPanel);
        add(lowerPanel);
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
        leftPanel.add(player);
        panel.add(leftPanel);

        JPanel rightPanel = new JPanel();
        
        JLabel roll = new JLabel("Roll");
        rightPanel.add(roll);
        rollNum.setEditable(false);
        rightPanel.add(rollNum);

        panel.add(rightPanel);

        JButton accusationButton = new JButton("Make Accusation");
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new NextButtonListener());
        accusationButton.addActionListener(new AccusationButtonListener());
        
        panel.add(accusationButton);
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
        player.setText(playerTurn.getName());
        player.setBackground(playerTurn.getTrueColor());
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

    public void refresh() {
        removeAll();
        setLayout(new GridLayout(2,0));
        add(createUpperPanel());
        add(createLowerPanel());
        //nextButton.addActionListener(new NextButtonListener());
        //accusationButton.addActionListener(new AccusationButtonListener());
        repaint();
    }
	

	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}

    class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Board.getInstance().nextButtonPressed();

        }
    }

    class AccusationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Board.getInstance().accusationButtonPressed();
        }
    }
}