package lastpencil;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        final String playerOne = "John";
        final String playerTwo = "Jack";

        Game game = new Game(playerOne, playerTwo);
        game.start();

    }
}

class Game {
    Scanner sc = new Scanner(System.in);

    private final String HUMAN_PLAYER;
    private final String BOT_PLAYER;
    private int pencilsCount;
    private int botPencilsToRemove;
    private String activePlayer;

    Game(String playerOne, String playerTwo) {
        this.HUMAN_PLAYER = playerOne;
        this.BOT_PLAYER = playerTwo;
    }

    void start() {
        init();
        run();
    }

    private void init() {
        setPencilsCount(inputInitialPencilsCount());
        choosePlayer();
    }

    private void run() {
        while (pencilsCount > 0) {
            printPencils();
            printPlayerOnMove();
            decreasePencilsCount(activePlayer);
            switchPlayer(activePlayer);
        }
        printWinner();
    }

    private void setPencilsCount(int count) {
        pencilsCount = count;
    }

    private int inputInitialPencilsCount() {
        int initialPencilsCount = 0;
        boolean isValidInput = false;

        System.out.println("How many pencils would you like to use:");

        while (! isValidInput) {
            try {
                initialPencilsCount = Integer.parseInt(sc.nextLine().trim());

                if (initialPencilsCount <= 0) {
                    throw new PencilCountNotPositiveException();
                } else {
                    isValidInput = true;
                }
            } catch (PencilCountNotPositiveException e ) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("The number of pencils should be numeric");
            }
        }
        return initialPencilsCount;
    }

    private void choosePlayer() {
        boolean isValidInput = false;

        System.out.printf("Who will be the first (%s, %s):\n", HUMAN_PLAYER, BOT_PLAYER);

        while (! isValidInput) {
            try {
                activePlayer = sc.next();
                if (!(activePlayer.equals(HUMAN_PLAYER) || activePlayer.equals(BOT_PLAYER))) {
                    throw new WrongNameException(HUMAN_PLAYER, BOT_PLAYER);
                }
                isValidInput = true;
            } catch (WrongNameException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printPencils() {
        for (int i = 0; i < pencilsCount; i++) {
            System.out.print("|");
        }
    }

    private void printPlayerOnMove() {
        System.out.println();
        System.out.println(activePlayer + "s turn!");
    }

    private int humanInputPencilCountToRemove() {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.println("The number of pencils should be numeric");
        }
        int pencilCountToRemove = sc.nextInt();
        while (pencilCountToRemove != 1 && pencilCountToRemove != 2 && pencilCountToRemove != 3) {
            System.out.println("Invalid input. Possible values: '1', '2' or '3'");
            while (!sc.hasNextInt()) {
                sc.next();
                System.out.println("Invalid input. Possible values: '1', '2' or '3'");
            }
            pencilCountToRemove = sc.nextInt();
        }
        while (pencilCountToRemove > pencilsCount) {
            System.out.println("Too many pencils were taken. Choose again 1, 2 or 3:");
            while (!sc.hasNextInt()) {
                sc.next();
                System.out.println("The number of pencils should be numeric");
            }
            pencilCountToRemove = sc.nextInt();
            while (pencilCountToRemove != 1 && pencilCountToRemove != 2 && pencilCountToRemove != 3) {
                System.out.println("Possible values: '1', '2', '3'");
                pencilCountToRemove = sc.nextInt();
            }
        }
        return pencilCountToRemove;
    }

    private int botInputPencilCountToRemove() {
                Random rand = new Random();

        // loosing position:
        if (pencilsCount == 1) {
            botPencilsToRemove = 1;
        } else if (pencilsCount % 4 == 1) {
            botPencilsToRemove = rand.nextInt(3) + 1;
            // winning position:
        } else if (pencilsCount % 4 == 0) {
            botPencilsToRemove = 3;
        } else if (pencilsCount % 4 == 3) {
            botPencilsToRemove = 2;
        } else if (pencilsCount % 4 == 2) {
            botPencilsToRemove = 1;
        }
        return botPencilsToRemove;
    }

    private void subDecreasePencilsCount(int pencilCountToRemove) {
        pencilsCount -= pencilCountToRemove;
    }

    private void decreasePencilsCount(String activePlayer) {
        if (activePlayer.equals(HUMAN_PLAYER)) {
            subDecreasePencilsCount(humanInputPencilCountToRemove());
        } else {
            subDecreasePencilsCount(botInputPencilCountToRemove());
            System.out.println(botPencilsToRemove);
        }
    }

    private void switchPlayer(String activePlayer) {
        this.activePlayer = activePlayer.equals(HUMAN_PLAYER) ? BOT_PLAYER : HUMAN_PLAYER;
    }

    private void printWinner() {
        System.out.println(activePlayer + " won!");
    }
}

class PencilCountNotPositiveException extends Exception {
    PencilCountNotPositiveException() {
        super("The number of pencils should be positive");
    }
}

class WrongNameException extends Exception {
    WrongNameException(String nameOne, String nameTwo) {
        super(String.format("Choose between %s and %s", nameOne, nameTwo));
    }
}
