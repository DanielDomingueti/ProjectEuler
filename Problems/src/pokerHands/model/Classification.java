package pokerHands.model;

import java.util.ArrayList;
import java.util.List;

public class Classification {

    private Integer rankValue;
    private List<List<Hand.Value>> rankCards = new ArrayList<>();

    public Classification(Integer rankValue, List<List<Hand.Value>> rankCards) {
        this.rankValue = rankValue;
        this.rankCards = rankCards;
    }

    public Integer getRankValue() {
        return rankValue;
    }

    public List<List<Hand.Value>> getRankCards() {
        return rankCards;
    }
}
