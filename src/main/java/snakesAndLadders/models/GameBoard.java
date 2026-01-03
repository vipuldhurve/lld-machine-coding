package snakesAndLadders.models;

import java.util.*;
import java.util.stream.Collectors;

public class GameBoard {
    private Integer boardSize;
    private Dice dice;
    private Queue<Player> nextTurn;
    private List<Jumper> snakes;
    private List<Jumper> ladders;
    Map<Integer, Integer> playersCurrentPosition;

    public GameBoard(Integer boardSize,
                     Dice dice,
                     List<Jumper> snakes,
                     List<Jumper> ladders,
                     List<Player> players) {
        this.boardSize = boardSize;
        this.dice = dice;
        this.nextTurn = new LinkedList<>(players);
        // Validate Snakes and Ladders
        validateJumpers(snakes, JumperType.SNAKE);
        validateJumpers(ladders, JumperType.LADDER);
        // Validate Overlapping snakes and ladders
        validateNoOverlappingStarts(snakes, ladders);
        this.snakes = snakes;
        this.ladders = ladders;
        // Initialize player positions
        this.playersCurrentPosition = initializePlayerPositions(players);
    }

    public void play() {
        displayGameStart();
        while (nextTurn.size() > 1) {
            Player player = nextTurn.poll();
            int currentPosition = playersCurrentPosition.get(player.getId());

            int diceValue = dice.rollDice();
            displayRoll(player, diceValue);
            int nextCell = currentPosition + diceValue;

            if (isWinner(nextCell)) {
                displayWinner(player);
            } else if (nextCell > boardSize) {
                displayTryAgainMessage(player, currentPosition, boardSize);
                displayCurrentPosition(player, currentPosition);
                nextTurn.offer(player);
            } else {
                nextCell = checkSnake(player, nextCell);
                nextCell = checkLadder(player, nextCell);
                if (isWinner(nextCell)) {
                    displayWinner(player);
                } else {
                    playersCurrentPosition.put(player.getId(), nextCell);
                    displayCurrentPosition(player, nextCell);
                    nextTurn.offer(player);
                }
            }
        }
    }

    private boolean isWinner(int currentPosition) {
        return currentPosition == boardSize;
    }

    private void displayRoll(Player player, int diceValue) {
        System.out.println("🎲 " + player.getName() + " rolled a " + diceValue);
    }

    private void displayTryAgainMessage(Player player,
                                        int currentPosition,
                                        int boardSize) {
        int requiredRoll = boardSize - currentPosition;
        System.out.println("⚠️  " + player.getName() +
                " needs a roll of exactly " + requiredRoll + " to win. Try again next turn!");
    }

    private void displayWinner(Player player) {
        System.out.println("🎊 " + player.getName().toUpperCase()
                + " WON THE GAME!!! 🎊");
    }

    private void displayCurrentPosition(Player player, int currentPosition) {
        System.out.println("📍 " + player.getName() + " is now at square " + currentPosition);
        System.out.println("---------------------------------");
    }

    private int checkSnake(Player player, int currentPosition) {
        for (Jumper s : snakes) {
            if (s.getStart().equals(currentPosition)) {
                System.out.println("🐍 Oh no! " + player.getName()
                        + " was bitten by a snake at " + s.getStart()
                        + " and slid down to " + s.getEnd() + "!");
                return s.getEnd();
            }
        }
        return currentPosition;
    }

    private int checkLadder(Player player, int currentPosition) {
        for (Jumper l : ladders) {
            if (l.getStart().equals(currentPosition)) {
                System.out.println("🪜 Yay! " + player.getName()
                        + " found a ladder at " + l.getStart()
                        + " and climbed up to " + l.getEnd() + "!");
                return l.getEnd();
            }
        }
        return currentPosition;
    }

    /* Validates that all snakes/ladders
    are within the board boundaries. */
    private void validateJumpers(List<Jumper> jumpers, JumperType type) {
        for (Jumper j : jumpers) {
            if (j.getStart() < 1 || j.getStart() > boardSize ||
                    j.getEnd() < 1 || j.getEnd() > boardSize) {
                throw new IllegalArgumentException(
                        String.format("Invalid %s position: [%d -> %d]. Must be between 1 and %d.",
                                type, j.getStart(), j.getEnd(), boardSize)
                );
            }
        }
    }

    private void validateNoOverlappingStarts(List<Jumper> snakes, List<Jumper> ladders) {
        Set<Integer> startPositions = new HashSet<>();

        // Check all snakes
        for (Jumper s : snakes) {
            if (!startPositions.add(s.getStart())) {
                throw new IllegalArgumentException("Multiple jumpers starting at square: " + s.getStart());
            }
        }

        // Check all ladders
        for (Jumper l : ladders) {
            if (!startPositions.add(l.getStart())) {
                throw new IllegalArgumentException("Multiple jumpers starting at square: " + l.getStart());
            }
        }
    }

    private Map<Integer, Integer> initializePlayerPositions(List<Player> players) {
        return players.stream()
                .collect(Collectors.toMap(
                        Player::getId,
                        p -> 1
                ));
    }

    private void displayGameStart() {
        System.out.println("==================================================");
        System.out.println("🎮 WELCOME TO SNAKES AND LADDERS 🎮");
        System.out.println("==================================================");
        System.out.println("📍 Board Size: " + boardSize);
        System.out.println("🎯 Goal: Reach square " + boardSize + " exactly!");
        System.out.println("🎲 Dice: " + dice.getNoOfDices() + " dice in play.");
        System.out.println("--------------------------------------------------");

        System.out.println("👥 PLAYER ROSTER:");
        nextTurn.forEach(player -> {
            int startPos = playersCurrentPosition.get(player.getId());
            System.out.printf("   - [ID: %d] %s (Starting at square %d)%n",
                    player.getId(), player.getName(), startPos);
        });

        System.out.println("--------------------------------------------------");
        System.out.println("🚀 Let the game begin!");
        System.out.println("==================================================\n");
    }

}

