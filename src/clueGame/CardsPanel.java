package clueGame;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;

//CardsPanel Class
//Author: William Warner
//Created April 12th, 2023
//No Collaboraters or outside sources

//This is the side panel that displays the known cards

public class CardsPanel extends JPanel{
    private ArrayList<Card> hand = new ArrayList<Card>();
    private ArrayList<Card> seenCards = new ArrayList<Card>();

    private static CardsPanel theInstance = new CardsPanel();

    public static CardsPanel getInstance() {
        return theInstance;
    }

    public CardsPanel() {
        
        setLayout(new GridLayout(3,0));
        setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
        JPanel peoplePanel = createPeoplePanel();
        JPanel weaponPanel = createWeaponPanel();
        JPanel roomPanel = createRoomPanel();
    
        add(peoplePanel);
        add(weaponPanel);
        add(roomPanel);
    }

    private JPanel createPeoplePanel() {
        JPanel peoplePanel = new JPanel();
        peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
        int rows = getPeopleInHand().size() + getSeenPersonCards().size() + 2;
        peoplePanel.setLayout(new GridLayout(rows,0));
        JLabel inHand = new JLabel("In Hand:");
        peoplePanel.add(inHand);
        ArrayList<Card> peopleInHand = getPeopleInHand();
        for (Card c : peopleInHand) {
            JTextField personInHand = new JTextField(c.getName());
            personInHand.setEditable(false);
            peoplePanel.add(personInHand);
        }



        JLabel seen = new JLabel("Seen:");
        peoplePanel.add(seen);
        ArrayList<Card> seenPeople = getSeenPersonCards();
        for (Card c : seenPeople) {
            JTextField seenPerson = new JTextField(c.getName());
            seenPerson.setEditable(false);
            peoplePanel.add(seenPerson);
        }


        return peoplePanel;

    }

    private JPanel createWeaponPanel() {
        JPanel weaponPanel = new JPanel();
        weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
        int rows = getWeaponsInHand().size() + getSeenWeaponCards().size() + 2;
        weaponPanel.setLayout(new GridLayout(rows,0));
        JLabel inHand = new JLabel("In Hand:");
        weaponPanel.add(inHand);
        ArrayList<Card> weaponsInHand = getWeaponsInHand();
        for (Card c : weaponsInHand) {
            JTextField weaponInHand = new JTextField(c.getName());
            weaponInHand.setEditable(false);
            weaponPanel.add(weaponInHand);
        }



        JLabel seen = new JLabel("Seen:");
        weaponPanel.add(seen);
        ArrayList<Card> seenWeapons = getSeenWeaponCards();
        for (Card c : seenWeapons) {
            JTextField seenWeapon = new JTextField(c.getName());
            seenWeapon.setEditable(false);
            weaponPanel.add(seenWeapon);
        }


        return weaponPanel;

    }

    private JPanel createRoomPanel() {
        JPanel roomPanel = new JPanel();
        roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
        int rows = getRoomsInHand().size() + getSeenRoomCards().size() + 2;
        roomPanel.setLayout(new GridLayout(rows,0));
        JLabel inHand = new JLabel("In Hand:");
        roomPanel.add(inHand);
        ArrayList<Card> roomsInHand = getRoomsInHand();
        for (Card c : roomsInHand) {
            JTextField roomInHand = new JTextField(c.getName());
            roomInHand.setEditable(false);
            roomPanel.add(roomInHand);
        }



        JLabel seen = new JLabel("Seen:");
        roomPanel.add(seen);
        ArrayList<Card> seenRooms = getSeenRoomCards();
        for (Card c : seenRooms) {
            JTextField seenRoom = new JTextField(c.getName());
            seenRoom.setEditable(false);
            roomPanel.add(seenRoom);
        }


        return roomPanel;
    }
    
    public static void main(String[] args) {
		CardsPanel panel = new CardsPanel();  // create the panel
        
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(200, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		

	}

    private ArrayList<Card> getPeopleInHand() {
        ArrayList<Card> people = new ArrayList<Card>();
        for (Card c : hand) {
            if (c.getCardType() == CardType.PERSON) {
                people.add(c);
            }
        }
        return people;
    }

    private ArrayList<Card> getWeaponsInHand() {
        ArrayList<Card> weapons = new ArrayList<Card>();
        for (Card c : hand) {
            if (c.getCardType() == CardType.WEAPON) {
                weapons.add(c);
            }
        }
        return weapons;
    }

    private ArrayList<Card> getRoomsInHand() {
        ArrayList<Card> rooms = new ArrayList<Card>();
        for (Card c : hand) {
            if (c.getCardType() == CardType.ROOM) {
                rooms.add(c);
            }
        }
        return rooms;
    }

    private ArrayList<Card> getSeenPersonCards() {
        ArrayList<Card> cards = new  ArrayList<Card>();
        for (Card c : seenCards) {
            if (c.getCardType() == CardType.PERSON) {
                cards.add(c);
            }
        }
        return cards;
    }
    private ArrayList<Card> getSeenWeaponCards() {
        ArrayList<Card> cards = new  ArrayList<Card>();
        for (Card c : seenCards) {
            if (c.getCardType() == CardType.WEAPON) {
                cards.add(c);
            }
        }
        return cards;
        
    }
    private ArrayList<Card> getSeenRoomCards() {
        ArrayList<Card> cards = new  ArrayList<Card>();
        for (Card c : seenCards) {
            if (c.getCardType() == CardType.ROOM) {
                cards.add(c);
            }
        }
        return cards;
    }

    public void setHand(ArrayList<Card> h) {
        hand = h;
        refresh();
    }
    public void setSeenCards(ArrayList<Card> s) {
        seenCards = s;
        refresh();
    }
    public void seeCard(Card c) {
        seenCards.add(c);
        refresh();
    }

    public void refresh() {
        removeAll();
        add(createPeoplePanel());
        add(createWeaponPanel());
        add(createRoomPanel());
    }
}
