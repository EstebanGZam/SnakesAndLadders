package ui;

import java.util.Scanner;
import model.Controller;
import model.GameExceptions;

import java.util.InputMismatchException;

public class Game {

	private static Controller controller;
	private static GameExceptions exc = new GameExceptions();
	private Scanner reader;
	private static Game game = new Game();

	public Game() {
		controller = new Controller();
		reader = new Scanner(System.in);
		exc = new GameExceptions();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// controller.generateGameBoard(10, 10, 0, 0);
		// System.out.println(controller.printGameBoard())
		game.displayMenu();
	}

	public void displayMenu() {
		executeOption(getOptionShowMenu());
		displayMenu();
	}

	public int getOptionShowMenu() {
		int option = 0;
		System.out.println("<<<<< Snakes and Ladders >>>>>");
		System.out.println(
				"1. Start Game \n" +
						"2.  \n" +
						"3.  \n" +
						"4.  \n" +
						"0. Exit. ");
		option = validateInteger();
		return option;
	}

	public void executeOption(int option) {
		switch (option) {
			case 1:
				generateBoard();
				break;
			case 2:
				// printGameBoard();
				break;
			case 3:
				break;
			case 4:
				break;
			case 0:
				System.out.println("Exit program.");
				break;
			default:
				System.out.println("Invalid Option");
				break;
		}
	}

	public void generateBoard() {

		System.out.print("Columns: ");
		int columns = validateInteger();

		System.out.print("Rows: ");
		int rows = validateInteger();

		System.out.print("Snakes: ");
		int snakes = validateInteger();


		System.out.print("Ladders: ");
		int ladders = validateInteger();
		controller.generateGameBoard(rows, columns, snakes, ladders);
	}

	public void play() {
	}

	public void printGameBoard() {
		System.out.println(controller.printGameBoard());
	}

	public void showSnakesAndLadders() {
	}

	public int checkExceptions(int option, boolean rep) {
		int value = 0;
		rep = true;
		if (!rep && option == 0){
			try {

				value = reader.nextInt();
				reader.nextLine();
				exc.checkNonExceptions(value,option);


			} catch (Exception exceptions) {

				System.out.println(exceptions);
				reader.next();
				checkExceptions(0,false);
			}
		}
		return value;
	}
	public int validateInteger() {
		int value = 0;
		while (true) {
			try {
				value = reader.nextInt();
				reader.nextLine();
				break;
			} catch (InputMismatchException ie) {
				System.out.println("Invalid input!. Try again fool");
				reader.next();
				continue;
			}
		}

		return value;
	}

}