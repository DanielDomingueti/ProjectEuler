package pokerHands;

import pokerHands.component.DefineHandCounts;

public class Execute54 {
    public static void main(String[] args) {
        DefineHandCounts defineHandCounts = new DefineHandCounts();
        String winner = defineHandCounts.execute();
        System.out.println(winner);
    }
}
