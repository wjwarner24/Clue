package clueGame;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
