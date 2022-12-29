package pokerHands.model;

public class Card implements Comparable<Card> {

    private Integer value;

    private String suit;

    public Card(Integer value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return "Value=" + value + ", Suit = " + suit;
    }

    @Override
    public int compareTo(Card card) {
        return card.getValue() - this.value;
    }
}
