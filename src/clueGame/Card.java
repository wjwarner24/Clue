package clueGame;

//Card Class
//Author: William Warner
//Created April 2nd, 2023
//No Collaboraters or outside sources

//Card represents a card in the Clue Game
//each card has a name and a type (Person, Weapon, or Room)
//also each card has a boolean indiciating whether it is part
//of the solution or not
//Contains standard getter/setter methods


public class Card {
    private String cardName;
    private CardType cardType;
    private boolean solution;

    public Card(String name, CardType type) {
        cardName = name;
        cardType = type;
        solution = false;
    }

    public boolean equals(Card card) {
        return cardName.equals(card.getName());
    }

    public String getName() {
        return cardName;
    }

    public CardType getCardType() {
        return cardType;
    }

    public boolean isSolution() {
        return solution;
    }

    public void setSolution(boolean b) {
        solution = b;
    }

    @Override
    public boolean equals(Object b) {
        return this.equals((Card) b);
    }
}
