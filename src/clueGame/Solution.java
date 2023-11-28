package clueGame;
import java.util.ArrayList;

//Solution Class
//Author: William Warner
//Created March 15th, 2023
//No Collaboraters or outside sources

//Solution represents a solution in the ClueGame
//a solution consists of a person card, a room card, and a weapon card
public class Solution {
    private Card weaponCard;
    private Card personCard;
    private Card roomCard;

    public Solution() {

    }
    public Solution(ArrayList<Card> list) {
        for (Card c : list) {
            if (c.getCardType() == CardType.WEAPON) {
                weaponCard = c;
            }
            if (c.getCardType() == CardType.PERSON) {
                personCard = c;
            }
            if (c.getCardType() == CardType.ROOM) {
                roomCard = c;
            }
        }
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
