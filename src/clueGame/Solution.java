package clueGame;

public class Solution {
    private Card weaponCard;
    private Card personCard;
    private Card roomCard;

    public Solution() {

    }

    public void setWeaponCard(Card c) {
        weaponCard = c;
    }
    public void setPersonCard(Card c) {
        personCard = c;
    }
    public void setRoomCard(Card c) {
        roomCard = c;
    }

    public Card getWeaponCard() {
        return weaponCard;
    }

    public Card getPersonCard() {
        return personCard;
    }
    public Card getRoomCard() {
        return roomCard;
    }

    public boolean equals(Solution arg) {
        boolean weapon = weaponCard.equals(arg.getWeaponCard());
        boolean room = roomCard.equals(arg.getRoomCard());
        boolean person = personCard.equals(arg.getPersonCard());

        return (weapon && room && person);
    }

    public void clear() {
        personCard = null;
        roomCard = null;
        weaponCard = null;
    }
}
