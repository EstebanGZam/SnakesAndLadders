package ui;

import java.util.Scanner;
import model.Controller;
import exception.*;
import java.util.InputMismatchException;

public class Game {

	private static Controller controller = new Controller();
	private Scanner reader;
	private GameExceptions gameExcept;
	private static Game game = new Game();

	public Game() {
		controller = new Controller();
		reader = new Scanner(System.in);
		gameExcept = new GameExceptions();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		controller.generateGameBoard(10, 10, 0, 0);
		System.out.println(controller.printGameBoard());
		game.displayMenu();
	}

	public void displayMenu() {
		int option = getOptionShowMenu();
		if (option == 0){
			executeOption(option);
			return;
		}
		executeOption(option);
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
				movePlayer();
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
		int rows = 0;
		int columns = 0;
		int snakes = 0, ladders = 0;

		System.out.print("Columns: ");
		columns = validateInteger();
		
		System.out.print("Rows: ");
		rows = validateInteger();



		System.out.print("Snakes: ");
		snakes = validateInteger();;

		System.out.print("Ladders: ");
		ladders = validateInteger();

		controller.generateGameBoard(rows, columns, snakes, ladders);

	}

	public void play() {
	}

	public void printGameBoard() {
		System.out.println(controller.printGameBoard());
	}

	public void showSnakesAndLadders() {
	}

	public void movePlayer(){
		System.out.println(controller.rollDice());
		System.out.println(controller.printGameBoard());
	}

	public int validateInteger() {
		int value = 0;

			try {
				value = reader.nextInt();
				reader.nextLine();
			} catch (InputMismatchException ie) {

				System.out.println("Input must be a int");
				reader.next();
				value = validateInteger();

			}
		return value;
	}


}