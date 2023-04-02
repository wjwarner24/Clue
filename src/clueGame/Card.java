package clueGame;

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
}
