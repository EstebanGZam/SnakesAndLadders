package ui;

import java.util.Scanner;

import model.Controller;
import exception.*;
import java.util.InputMismatchException;

public class Game {
    private static Game game = new Game();
    private static Controller controller;
    private static Scanner reader;

    public Game() {
        controller = new Controller();
        reader = new Scanner(System.in);
    }

    public static void main(String[] args) {
        System.out.println("<<<<< Welcome to snakes and ladders >>>>>");
        game.displayStartMenu();
    }

    public void displayStartMenu() {
        System.out.print("1) Play\n2) Exit\nSelected: ");
        int option = validateInteger();
        executeOption(option);
    }

    public int validateInteger() {
        int value = 0;
        try {
            value = reader.nextInt();
            reader.nextLine();
            if (value <= 0) {
                throw new ZeroInputException("The input value must be greater than zero.");
            }
        } catch (InputMismatchException ime) {
            reader.nextLine();
            System.out.print("Invalid input. Type a valid integer number: ");
            value = validateInteger();
        } catch (ZeroInputException zie) {
            System.out.println(zie.getMessage());
            reader.nextLine();
            System.out.print("Type a valid integer number: ");
            value = validateInteger();
        }
        return value;
    }

    public void executeOption(int option) {
        switch (option) {
            case 1:
                generateBoard();
                play();
                break;
            case 2:
                System.out.println("\nThanks for using the program. Until next time!");
                break;
            default:
                System.out.println("Error. Type a valid option.\n");
                break;
        }
        if (option != 2)
            displayStartMenu();
    }

    public void generateBoard() {
        try {
            System.out.print("\nColumns: ");
            int columns = validateInteger();
            System.out.print("Rows: ");
            int rows = validateInteger();
            if (rows * columns < 4)
                throw new IllegalArgumentException("The game board must have at least 4 slots.");
            validateBoard(rows, columns);
            System.out.print("Generating the board...");
            Thread.sleep(2000);
            System.out.println("\nLet the game begin!\n");
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
            generateBoard();
        } catch (InterruptedException e) {
        }
    }

    public void validateBoard(int rows, int columns) {
        System.out.print("Snakes: ");
        int snakes = validateInteger();
        System.out.print("Ladders: ");
        int ladders = validateInteger();
        generateBoard(rows, columns, snakes, ladders);
    }

    public void generateBoard(int rows, int columns, int snakes, int ladders) {
        try {
            if ((snakes + ladders) * 2 > rows * columns)
                throw new InputOutOfBoardException(
                        "The sum of ladders and snakes cannot be greater than the size of the board.");
            controller.generateGameBoard(rows, columns, snakes, ladders);
        } catch (InputOutOfBoardException ioe) {
            System.out.println(ioe.getMessage());
            validateBoard(rows, columns);
        } catch (StackOverflowError e) {
            generateBoard(rows, columns, snakes, ladders);
        }

    }

    public void displaySecondaryMenu() {
        System.out.print(controller.displaySecondaryMethod());
    }

    public void printGameBoard() {
        System.out.println(controller.printGameBoard());
    }

    public void play() {
        play(1);
    }

    public void play(int option) {
        if (option == 1)
            printGameBoard();
        displaySecondaryMenu();
        option = validateInteger();
        switch (option) {
            case 1:
                System.out.println(controller.rollDice());
                if (controller.matchFinished())
                    winnerRegister();
                break;
            case 2:
                System.out.println("\n" + controller.printSnakesAndLadders());
                break;
            default:
                System.out.println("Error. Type a valid option.\n");
                break;
        }
        if (!controller.matchFinished())
            play(option);
    }

    public void winnerRegister() {
        System.out.print("\nWinner nickname: ");
        String winnerNick = reader.nextLine();
        System.out.println(controller.addScoreRegistry(winnerNick));
    }
}