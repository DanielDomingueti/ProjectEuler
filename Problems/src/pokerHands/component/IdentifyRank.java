package pokerHands.component;

import pokerHands.model.Classification;
import pokerHands.model.Counter;
import pokerHands.model.Hand;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IdentifyRank {
    private final static Integer ROYAL_FLUSH_RANK = 10;
    private final static Integer STRAIGHT_FLUSH_RANK = 9;
    private final static Integer FOUR_OF_KIND_RANK = 8;
    private final static Integer FULL_HOUSE_RANK = 7;
    private final static Integer FLUSH_RANK = 6;
    private final static Integer STRAIGHT_RANK = 5;
    private final static Integer THREE_OF_KIND_RANK = 4;
    private final static Integer TWO_PAIRS_RANK = 3;
    private final static Integer ONE_PAIR_RANK = 2;
    private final static Integer HIGH_CARD_RANK = 1;

    public Counter execute(Hand firstHand, Hand secondHand) {
        //sort cards by value DESC
        Collections.sort(firstHand.getValues());
        Collections.sort(secondHand.getValues());

        Counter counter = new Counter(0, 0);
        Classification firstHandRank = identifyRank(firstHand);
        Classification secondHandRank = identifyRank(secondHand);

        if (firstHandRank.getRankValue().equals(secondHandRank.getRankValue())) {
            //find highest card of the first rank classification
            Integer firstCardRankValue1 = firstHandRank.getRankCards().get(0).get(0).getValue();
            Integer secondCardRankValue1 = secondHandRank.getRankCards().get(0).get(0).getValue();
            if (firstCardRankValue1 > secondCardRankValue1) {
                counter.setFirstCounter(counter.getFirstCounter() + 1);
            }
            else if(firstCardRankValue1 < secondCardRankValue1) {
                counter.setSecondCounter(counter.getSecondCounter() + 1);
            }

            //find highest card of the second rank classification
            if (firstCardRankValue1.equals(secondCardRankValue1)) {
                //not every hand has more than one rank so we need to check if it's != than null
                if (firstHandRank.getRankCards().size() == 2
                        && secondHandRank.getRankCards().size() == 2) {
                    Integer firstCardRankValue2 = firstHandRank.getRankCards().get(1).get(0).getValue();
                    Integer secondCardRankValue2 = secondHandRank.getRankCards().get(1).get(0).getValue();
                    if (firstCardRankValue2 > secondCardRankValue2) {
                        counter.setFirstCounter(counter.getFirstCounter() + 1);
                    }
                    else if(firstCardRankValue2 < secondCardRankValue2) {
                        counter.setSecondCounter(counter.getSecondCounter() + 1);
                    }
                    else {
                        //if second rank classification are equals, we compare the highest card of the hand
                        findHighestHandRemainingCard(firstHand, secondHand, counter, 0);
                    }

                } else {
                    //find highest remaining card of the deck if there's only one equal rank
                    findHighestHandRemainingCard(firstHand, secondHand, counter, 0);
                }
            }

            //find highest remaining card of the deck
        }
        else if(firstHandRank.getRankValue() > secondHandRank.getRankValue()) {
            counter.setFirstCounter(counter.getFirstCounter() + 1);
        } else {
            counter.setSecondCounter(counter.getSecondCounter() + 1);
        }

        return counter;
    }

    private void findHighestHandRemainingCard(Hand firstHand, Hand secondHand, Counter counter, int counterNum) {
        Integer firstHandHighestValue = firstHand.getValues().get(counterNum).getValue();
        Integer secondHandHighestValue = secondHand.getValues().get(counterNum).getValue();
        if (firstHandHighestValue > secondHandHighestValue) {
            counter.setFirstCounter(counter.getFirstCounter() + 1);
        }
        else if (firstHandHighestValue < secondHandHighestValue) {
            counter.setSecondCounter(counter.getSecondCounter() + 1);
        }
        else {
            //avoid index out of bounds
            if (++counterNum < firstHand.getValues().size()) {
                findHighestHandRemainingCard(firstHand, secondHand, counter, ++counterNum);
            }
        }
    }

    private Classification identifyRank(Hand hand) {
        Classification classification;
        switch(hand.getNumSuits()) {
            case 1: //royal flush, straight flush and flush
                classification = rankHandOneSuit(hand);
                break;
            default:
                classification = rankHandManySuits(hand);
                break;
        }
        return classification;
    }

    private Classification rankHandManySuits(Hand hand) {
        if (hand.getValues().size() == 2) {
            List<Hand.Value> fourOfKind = hand.getValues().stream().filter(v -> v.getCount() == 4).collect(Collectors.toList());
            List<Hand.Value> oneValue = hand.getValues().stream().filter(v -> v.getCount() == 1).collect(Collectors.toList());
            if (fourOfKind.size() == 1 && oneValue.size() == 1) {
                return new Classification(FOUR_OF_KIND_RANK, List.of(fourOfKind, oneValue));
            }

            List<Hand.Value> threeOfKind = hand.getValues().stream().filter(v -> v.getCount() == 3).collect(Collectors.toList());
            List<Hand.Value> onePair = hand.getValues().stream().filter(v -> v.getCount() == 2).collect(Collectors.toList());
            if (threeOfKind.size() == 1 && onePair.size() == 1) {
                return new Classification(FULL_HOUSE_RANK, List.of(threeOfKind, onePair));
            }
        }

        if (hand.getValues().size() == 5) {
            if (handIsStraight(hand)) {
                return new Classification(STRAIGHT_RANK, List.of(hand.getValues()));
            }
        }

        if (hand.getValues().size() == 3) {
            List<Hand.Value> threeOfKind = hand.getValues().stream().filter(v -> v.getCount() == 3).collect(Collectors.toList());
            if (threeOfKind.size() == 1) {
                return new Classification(THREE_OF_KIND_RANK, List.of(threeOfKind));
            }
        }

        List<Hand.Value> twoPairs = hand.getValues().stream().filter(v -> v.getCount() == 2).collect(Collectors.toList());
        if (twoPairs.size() == 2) {
            return new Classification(TWO_PAIRS_RANK, List.of(twoPairs));
        }
        else if(twoPairs.size() == 1) {
            return new Classification(ONE_PAIR_RANK, List.of(twoPairs));
        }

        //If there's no rank, just return the highest card value.
        return new Classification(HIGH_CARD_RANK, List.of(hand.getValues()));

    }

    private Classification rankHandOneSuit(Hand hand) {
        if (!handIsStraight(hand)) {
            //Non consecutive values of the same suit
            return new Classification(FLUSH_RANK, List.of(hand.getValues()));
        }

        //Consecutive values starting with Ace of the same suit
        if (hand.getValues().get(0).getValue() == 14) {
            return new Classification(ROYAL_FLUSH_RANK, List.of(hand.getValues()));
        }

        //consecutive values with no order of the same suit
        return new Classification(STRAIGHT_FLUSH_RANK, List.of(hand.getValues()));
    }

    private boolean handIsStraight(Hand hand) {
        return hand.getValues().size() == 5 && hand.getValues().get(0).getValue() - hand.getValues().get(4).getValue() == 4;
    }
}
