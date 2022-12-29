package pokerHands.component;

import pokerHands.model.Card;
import pokerHands.model.Counter;
import pokerHands.model.Hand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class DefineHandCounts {
    public DefineHandCounts() {}

    public String execute() {
        IdentifyRank identifyRank = new IdentifyRank();
        Integer firstHandCounter = 0;
        Integer secondHandCounter = 0;

        List<Card> firstHandCards = new ArrayList<>();
        List<Card> secondHandCards = new ArrayList<>();

        try {

            URL url = new URL("https://projecteuler.net/project/resources/p054_poker.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = bufferedReader.readLine();

            while (line != null) {
                String cards[] = line.split(" ");

                int i = 0;
                for(String card: cards) {
                    String valueAsString = String.valueOf(card.charAt(0));
                    //convert T, J, Q, K and A to ascending numbers
                    Integer valueAsNumber = convertLetterToNumber(valueAsString);
                    String suit = String.valueOf(card.charAt(1));

                    Card cardObj = new Card(valueAsNumber, suit);

                    if (i < 5) {
                        firstHandCards.add(cardObj);
                        i++;
                    } else {
                        secondHandCards.add(cardObj);
                    }
                }

                //Identify which hand has won in each line
                Counter counter = identifyRank.execute(mapCardsToHand(firstHandCards), mapCardsToHand(secondHandCards));
                firstHandCounter += counter.getFirstCounter();
                secondHandCounter += counter.getSecondCounter();

                firstHandCards.clear();
                secondHandCards.clear();

                line = bufferedReader.readLine();
            }
            bufferedReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "The 1st player has won " + firstHandCounter + " times, whereas the 2nd player has won " + secondHandCounter + " times.";
    }

    private Hand mapCardsToHand(List<Card> cards) {
        Set<String> suitsFound = new HashSet<>();
        Hand hand = new Hand();

        //transfer cards to hand values.
        cards.forEach(card -> {
            hand.getValues().add(new Hand.Value(card.getValue(), 1));
            suitsFound.add(card.getSuit());
        });

        //if there's a repeated value, remove it and add up the conter
        Map<Integer, Integer> resultMap = new HashMap<>();
        hand.getValues().forEach(value -> {
            resultMap.put(value.getValue(), resultMap.getOrDefault(value.getValue(), 0) + 1);
        });

        hand.getValues().clear();
        resultMap.forEach((value, count) -> {
            hand.getValues().add(new Hand.Value(value, count));
        });
        hand.setNumSuits(suitsFound.size());
        return hand;
    }

    private Integer convertLetterToNumber(String valueAsString) {
        Integer value = 0;
        switch (valueAsString) {
            case "T":
                value = 10;
                break;
            case "J":
                value = 11;
                break;
            case "Q":
                value = 12;
                break;
            case "K":
                value = 13;
                break;
            case "A":
                value = 14;
                break;
            default:
                value = Integer.parseInt(valueAsString);
                break;
        }
        return value;
    }
}
