package ui;

import java.util.Scanner;
import model.Controller;
import java.util.InputMismatchException;

public class Game {

	private static Controller controller;
	private Scanner reader;

	public Game() {
		controller = new Controller();
		reader = new Scanner(System.in);

	}

	public static void main(String[] args) {

		Game m = new Game();
		int a = m.validateInteger();
		int option = 0;

		do {

			option = m.getOptionShowMenu();
			m.executeOption(option);

		} while (option != 0);

		m.getReader().close();
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
				;
				break;

			case 3:
				;
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
		System.out.println("Type number of game board columns:");
		int columns = validateInteger();
		System.out.println("Type number of game board rows:");
		int rows = validateInteger();
		System.out.println("Type number of snakes to game:");
		int snakes = validateInteger();
		System.out.println("Type number of ladders to game:");
		int ladders = validateInteger();
		controller.generateGameBoard(rows, columns, snakes, ladders);
	}

	public void play() {
	}

	public void printGameBoard() {
	}

	public void showSnakesAndLadders() {
	}

	public int validateInteger(){
		int value = 0;
		while (true){
			try{
				value = reader.nextInt();
				break;
			}
			catch (InputMismatchException ie){
				System.out.println("Invalid input!. Try again fool");
				reader.next();
				continue;
			}
		}

		return value;
	}
	public Scanner getReader(){
		return reader;
	}

}