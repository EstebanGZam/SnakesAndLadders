package model;

public class Controller {

	private GameBoard gameBoard;

	/**
	 * 
	 * @param rows
	 * @param columns
	 * @param snakes
	 * @param ladders
	 */
	public void generateGameBoard(int rows, int columns, int snakes, int ladders) {
		gameBoard = new GameBoard(rows, columns, snakes, ladders);
	}

	public String printGameBoard() {
		return gameBoard.print();
	}

	/**
	 * @return int
	 */
	public int rollDice() {
		return 0;
	}

	public void addLadders() {
	}

	public void addSnakes() {
	}

	public void movePlayer() {
	}

	public String refreshGameBoard() {
		return null;
	}

	public String showSnakesAndLadders() {
		return null;
	}

	public double calculateScore() {
		return 0.0;
	}

}