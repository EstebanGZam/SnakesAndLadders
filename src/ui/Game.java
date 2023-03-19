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

    public static void main(String[] args){
        System.out.println("<<<<< Welcome to snakes and ladders >>>>>");
        game.displayStartMenu();
    }

    public void displayStartMenu(){
        System.out.print("1) Play\n2) Exit\nSelected: ");
        int option = validateInteger();
        executeOption(option);
    }

    public int validateInteger(){
        int value = 0;
        try {
            value = reader.nextInt();
            if (value <= 0) {
                throw new ZeroInputException("The input value must be greater than zero.");
            }
        } catch (InputMismatchException ime) {
            reader.nextLine();
            System.out.print("Invalid input. Type a valid integer number: ");
            value = validateInteger();
        } catch (ZeroInputException zie) {
            System.out.println(zie.getMessage());
            System.out.print("Type a valid integer number: ");
            value = validateInteger();
        }
        return value;
    }


    public void executeOption(int option){
        switch (option) {
            case 1 -> {
                generateBoard();
                play();
            }
            case 2 -> System.out.println("\nThanks for using the program. Until next time!");
            default -> System.out.println("Error. Type a valid option.\n");
        }
        if (option != 2)
            displayStartMenu();
    }

    public void generateBoard(){
        System.out.print("\nColumns: ");
        int columns = validateInteger();

        System.out.print("Rows: ");
        int rows = validateInteger();

        validateBoard(rows, columns);

        try {
            System.out.print("Generating the board ");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\nLet the game begin!\n");
    }

    public void validateBoard(int rows, int columns){
        int max = rows * columns;
        int snakes = 0, ladders = 0;
        try{
            System.out.print("Snakes: ");
                snakes = validateInteger();
            System.out.print("Ladders: ");
                ladders = validateInteger();
                controller.generateGameBoard(rows, columns, snakes, ladders);
                if ((snakes + ladders) >= max){
                 throw new InputOutOfBoardException("Ladders and snakes cannot be equal to or greater than half the board");
                }

        }catch (InputOutOfBoardException | StackOverflowError iobe){
            System.out.println(iobe.getMessage());
            System.out.print("Type valid values for snakes and ladders: (Less Than " + max /2 + ")");
            
            validateBoard(rows, columns);
        }

    }

    public void displaySecondaryMenu() {
        System.out.print(controller.displaySecondaryMethod());
    }

    public void printGameBoard() {
        System.out.println(controller.printGameBoard());
    }

    public void play(){
        play(1);
    }

    public void play(int option) {
        if (option == 1)
            printGameBoard();
        displaySecondaryMenu();
        option = validateInteger();
        switch (option) {
            case 1 -> {
                System.out.println(controller.rollDice());
                if (controller.matchFinished()) {
                    winnerRegister();
                }
            }
            case 2 -> System.out.println(controller.printSnakesAndLadders());
            default -> System.out.println("Error. Type a valid option.\n");
        }
        if (!controller.matchFinished())
            play(option);
    }

    public void winnerRegister() {
        System.out.println("\n Type winner nick name: ");
        String winnerNick = reader.next();
        System.out.println(controller.addScoreRegistry(winnerNick));
    }
}