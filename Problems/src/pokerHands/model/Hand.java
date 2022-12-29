package pokerHands.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Value> values = new ArrayList<>();

    private Integer numSuits;

    public Hand(List<Value> values, Integer numSuits) {
        this.values = values;
        this.numSuits = numSuits;
    }

    public Hand() {
    }

    public List<Value> getValues() {
        return values;
    }

    public Integer getNumSuits() {
        return numSuits;
    }

    public void setNumSuits(Integer numSuits) {
        this.numSuits = numSuits;
    }


    public static class Value implements Comparable<Value> {
        private Integer value;
        private Integer count;

        public Value(Integer value, Integer count) {
            this.value = value;
            this.count = count;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        @Override
        public int compareTo(Value value) {
            return value.getValue() - this.getValue();
        }
    }
}
