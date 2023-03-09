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
	 * rollDice: Extracts the result of the launch and organizes it in a message.
	 * @return String msj: Message that inform to current player result of roll dice.
	 */
	public String rollDice() {
		int dice = gameBoard.rollDice();
		String msj = "";
		if (gameBoard.getCurrentPlayer() == 1) {
			msj = "Player 3 rolled: " + dice;
		}else{
			msj = "Player " + (gameBoard.getCurrentPlayer()-1) + " rolled: " + dice;
		}
		if (gameBoard.getIsWinner() == true) {
			msj += "\n Match finishes. This player is the winner.";
		}

		return msj;
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
