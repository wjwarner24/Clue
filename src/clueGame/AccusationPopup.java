package clueGame;
import java.awt.*;
import javax.swing.*;

//AccusationPopup Class
//Author: William Warner
//Created April 22nd, 2023
//No Collaboraters or outside sources

//AccusationPopup is the popup that shows when you make an accusation
//asks for a person, room, and weapon
//if you guess the wrong solution, you lose the game
//if you guess the correct solution, you win the game

public class AccusationPopup extends JPanel {
    private String selectedPersonCard;
    private String selectedWeaponCard;
    private String selectedRoomCard;
    public AccusationPopup() {
        setLayout(new GridLayout(3,2));
        add(new JLabel("Room"));
        

        DefaultComboBoxModel roomSelect = new DefaultComboBoxModel();
            
            for (Card c : Board.getInstance().getRoomCards()) {
                roomSelect.addElement(c.getName());
            }
        JComboBox roomDropdown = new JComboBox(roomSelect);
        add(roomDropdown);



        add(new JLabel("Person"));
        DefaultComboBoxModel personSelect = new DefaultComboBoxModel();
            
            for (Player p : Board.getInstance().getPlayers()) {
                personSelect.addElement(p.getName());
            }
        JComboBox personDropdown = new JComboBox(personSelect);
        add(personDropdown);
        add(new JLabel("Weapon"));
        DefaultComboBoxModel weaponSelect = new DefaultComboBoxModel();
            for (Card c : Board.getInstance().getWeaponCards()) {
                weaponSelect.addElement(c.getName());
            }
        JComboBox weaponDropdown = new JComboBox(weaponSelect);
        add(weaponDropdown);
        int result = JOptionPane.showConfirmDialog(null, this, "Make a Suggestion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (result) {
            case JOptionPane.OK_OPTION:
                        selectedPersonCard = personDropdown.getSelectedItem().toString();
                        selectedWeaponCard = weaponDropdown.getSelectedItem().toString();
                        selectedRoomCard = roomDropdown.getSelectedItem().toString();
                        break;
        }
    }

    public String getRoomCard() {
        return selectedRoomCard;
    }
    public String getWeaponCard() {
        return selectedWeaponCard;
    }
    public String getPersonCard() {
        return selectedPersonCard;
    }

    public Solution getSolution() {
        Solution sol = new Solution();
        for (Card c : Board.getInstance().getCards()) {
            if (c.getName().equals(selectedRoomCard)) {
                sol.setRoomCard(c);
            }
            if (c.getName().equals(selectedWeaponCard)) {
                sol.setWeaponCard(c);
            }
            if (c.getName().equals(selectedPersonCard)) {
                sol.setPersonCard(c);
            }
        }
        return sol;
    }
}
