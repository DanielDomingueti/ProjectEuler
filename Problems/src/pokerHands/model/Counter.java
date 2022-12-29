package pokerHands.model;

public class Counter {
    private Integer firstCounter;
    private Integer secondCounter;

    public Counter(Integer firstCounter, Integer secondCounter) {
        this.firstCounter = firstCounter;
        this.secondCounter = secondCounter;
    }

    public Counter() {}

    public Integer getFirstCounter() {
        return firstCounter;
    }

    public void setFirstCounter(Integer firstCounter) {
        this.firstCounter = firstCounter;
    }

    public Integer getSecondCounter() {
        return secondCounter;
    }

    public void setSecondCounter(Integer secondCounter) {
        this.secondCounter = secondCounter;
    }
}
