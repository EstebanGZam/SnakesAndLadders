package ui;

import java.util.Scanner;
import model.Controller;
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
		} catch (InputMismatchException ime) {
			reader.nextLine();
			System.out.print("Invalid input. Type a valid integer number: ");
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
		System.out.print("\nColumns: ");
		int columns = validateInteger();
		System.out.print("Rows: ");
		int rows = validateInteger();
		System.out.print("Snakes: ");
		int snakes = validateInteger();
		System.out.print("Ladders: ");
		int ladders = validateInteger();
		controller.generateGameBoard(rows, columns, snakes, ladders);
		System.out.println("\nLet the game begin!\n");
	}

	public void displaySecondaryMenu() {
		System.out.print(controller.displaySecondaryMethod());
	}

	public void printGameBoard() {
		System.out.println(controller.printGameBoard());
	}

	public void showSnakesAndLadders() {
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

}