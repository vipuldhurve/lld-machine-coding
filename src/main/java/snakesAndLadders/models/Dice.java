package snakesAndLadders.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;


@AllArgsConstructor
@Getter
public class Dice {
    private Integer noOfDices;

    public int rollDice() {
         return (int)(Math.random() * (6*noOfDices - 1*noOfDices + 1)) + (1*noOfDices);
//        Random rand = new Random();
        // nextInt(7) gives 0-6; nextInt(1, 7) gives 1-6;
//        return rand.nextInt(noOfDices, (6 * noOfDices) + 1);
    }

    public static void main(String[] args) {
        int noOfDices = 1;
        System.out.println((int)(Math.random() * (6*noOfDices - 1*noOfDices + 1)) + (1*noOfDices));
    }

}
