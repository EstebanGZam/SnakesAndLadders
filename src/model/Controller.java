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

	/**
	 * @return String
	 */
	public String printGameBoard() {
		return gameBoard.printSlots();
	}

	/**
	 * @return String
	 */
	public String printSnakesAndLadders() {
		return gameBoard.printSnakesAndLadders();
	}

	/**
	 * rollDice: Extracts the result of the launch and organizes it in a message.
	 *
	 * @return String msj: Message that inform to current player result of roll
	 *         dice.
	 */
	public String rollDice() {
		String msg = gameBoard.rollDice();
		if (matchFinished()) {
			addScoreRegistry(gameBoard.getMatchScore());
			msg += printScoreBoard();
		}
		return msg;
	}

	public String displaySecondaryMethod() {
		return "Player " + gameBoard.getCurrentPlayer().getSymbol()
				+ ", is your turn.\n1) Roll dice.\n2) See ladders and snakes\nOption: ";
	}

	public boolean matchFinished() {
		if (gameBoard.getMatchScore() != 0)
			return true;
		return false;
	}

	private void addScoreRegistry(long matchScore) {
	}

	private String printScoreBoard() {
		return null;
	}

}
