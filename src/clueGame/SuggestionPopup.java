package clueGame;
import javax.swing.*;
import java.awt.*;

//SuggestionPopup Class
//Author: William Warner
//Created April 22nd, 2023
//No Collaboraters or outside sources

//SuggestionPopup is the popup that shows when you make an suggestion
//asks for a room and a weapon
//the suggestion will attempt to be disproved by the other players
//the suggestion and the result of the suggestion will be displayed
//in the control panel


public class SuggestionPopup extends JPanel {
    private String selectedPersonCard;
    private String selectedWeaponCard;
    public SuggestionPopup(Room startRoom) {
        setLayout(new GridLayout(3,2));
        add(new JLabel("Current room"));
        add(new JLabel(startRoom.getName()));
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
                        break;
        }
    }

    public String getPersonCard() {
        return selectedPersonCard;
    }
    public String getWeaponCard() {
        return selectedWeaponCard;
    }
}
