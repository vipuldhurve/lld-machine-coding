package snakesAndLadders;

import snakesAndLadders.models.*;
import java.util.List;

import static snakesAndLadders.models.JumperType.SNAKE;
import static snakesAndLadders.models.JumperType.LADDER;


public class PlaySnakesAndLadders {
    public static void main(String[] args) {
        Dice dice = new Dice(1);
        List<Player> players = List.of(
                new Player("Manas", 1),
                new Player("Swapnil", 2));
        List<Jumper> snakes = List.of(
                new Jumper(10, 2, SNAKE),
                new Jumper(67, 35, SNAKE),
                new Jumper(99, 12));
        List<Jumper> ladders = List.of(
                new Jumper(5, 25, LADDER),
                new Jumper(65, 80, LADDER),
                new Jumper(40, 89));

        GameBoard snakesAndLadders = new GameBoard(100,
                dice,
                snakes,
                ladders,
                players);
        snakesAndLadders.play();
    }
}
