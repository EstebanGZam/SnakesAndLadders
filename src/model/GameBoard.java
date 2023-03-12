package model;

import java.util.Random;

public class GameBoard {

	private Slot head, tail;
	private Player player1, player2, player3;
	private int rows, columns, currentPlayer;
	private Random random = new Random();
	private boolean winner;
	private long time, matchScore = 0;

	/**
	 * <b>Name:</b> GameBoard <br>
	 * <b>Description:</b> Constructor method of GameBoard class. <br>
	 *
	 * @param rows    Number of rows on the game board.
	 * @param columns Number of columns on the game board.
	 * @param snakes  Number of snakes on the game board.
	 * @param ladders Number of ladders on the game board.s
	 */
	public GameBoard(int rows, int columns, int snakes, int ladders) {
		this.rows = rows;
		this.columns = columns;
		int numberOfSlots = rows * columns;
		this.winner = false;
		addSlots(numberOfSlots);
		String symbols = generateRandomSymbols();
		player1 = new Player(this.head, Character.getNumericValue(symbols.charAt(0)));
		player2 = new Player(this.head, Character.getNumericValue(symbols.charAt(1)));
		player3 = new Player(this.head, Character.getNumericValue(symbols.charAt(2)));
		currentPlayer = 1;
		addSnakes(snakes);
		addLadders(ladders);
		startMatch();
	}

	public void startMatch() {
		this.time = System.currentTimeMillis();
	}

	public void stopMatch() {
		long currentTime = System.currentTimeMillis();
		time = (currentTime - this.time) / 1000;
	}

	/**
	 * <b>Name:</b> generateRandomSymbols <br>
	 * <b>Description:</b> Method used to generate the symbols of the players (take
	 * into account that no two symbols can be the same). <br>
	 *
	 * @return Message with the numbers corresponding to the player's symbol.
	 */
	public String generateRandomSymbols() {
		int a = 1 + random.nextInt(9);
		int b = 1 + random.nextInt(9);
		int c = 1 + random.nextInt(9);
		if (a == b || b == c || c == a)
			return generateRandomSymbols();
		return a + "" + b + "" + c;
	}

	/**
	 * <b>Name:</b> addSlots <br>
	 * <b>Description:</b> Trigger method used to add the slots to the game board.
	 * <br>
	 * <b><i>pre:</i></b> Total slots to be added must be greater than 1. <br>
	 * <b><i>pos:</i></b> The squares are added to the game board. <br>
	 *
	 * @param numberOfSlots Total number of squares on the game board.
	 */
	public void addSlots(int numberOfSlots) {
		addSlots(0, numberOfSlots);
	}

	/**
	 * <b>Name:</b> addSlots <br>
	 * <b>Description:</b> Recursive method used to add the slots to the game board.
	 * <br>
	 *
	 * <b><i>pre:</i></b> Total slots must be greater than 1. <br>
	 * <b><i>pos:</i></b> The slots are added to the game board. <br>
	 *
	 * @param index         Slot number being added.
	 * @param numberOfSlots Total number of slots to be added.
	 */
	private void addSlots(int index, int numberOfSlots) {
		if (index < numberOfSlots) {
			insertLast(new Slot(index + 1));
			addSlots(++index, numberOfSlots);
		}
	}

	/**
	 * <b>Name:</b> insertLast <br>
	 * <b>Description:</b> Method used to insert a slot to the list of slots in the
	 * gameBoard. <br>
	 * <b><i>pos:</i></b> The slot is added to the game board. <br>
	 *
	 * @param slot Slot to be added.
	 */
	public void insertLast(Slot slot) {
		if (this.head == null) {
			this.head = slot;
			this.tail = slot;
			this.head.setNext(slot);
			this.tail.setPrevious(slot);
		} else {
			this.tail.setNext(slot);
			slot.setPrevious(this.tail);
			this.tail = slot;
		}
	}

	/**
	 * <b>Name:</b> search <br>
	 * <b>Description:</b> Trigger method used to search a slot in the game board.
	 * <br>
	 *
	 * @param value Number identifier of the searched slot.
	 * @return Slot found.
	 */
	public Slot search(int value) {
		return search(this.head, value);
	}

	/**
	 * <b>Name:</b> search <br>
	 * <b>Description:</b> Recursive method used to search a slot in the game board.
	 * <br>
	 *
	 * @param slotNumber Value of the searched slot.
	 * @return Slot found.
	 */
	public Slot search(Slot current, int slotNumber) {
		if (this.head == null) {
			return null;
		} else if (current != null && current.getSlotNumber() == slotNumber) {
			return current;
		} else if (current != null && current.equals(this.tail)) {
			return null;
		}
		return search(current.getNext(), slotNumber);
	}

	/**
	 * <b>Name:</b> addSnakes <br>
	 * <b>Description:</b> Trigger method used to add the snakes to the game board.
	 * <br>
	 * <b><i>pre:</i></b> The game board must be created in advice. <br>
	 * <b><i>pos:</i></b> The snakes are added to the game board. <br>
	 *
	 * @param snakes Amount of snakes to be added.
	 */
	public void addSnakes(int snakes) {
		addSnakes(1, snakes);
	}

	/**
	 * <b>Name:</b> addSnakes <br>
	 * <b>Description:</b> Recursive method used to add the snakes to the game
	 * board. <br>
	 * <b><i>pre:</i></b> The game board must be created in advice. <br>
	 * <b><i>pos:</i></b> The snakes are added to the game board. <br>
	 *
	 * @param snakes Amount of snakes to be added.
	 */
	private void addSnakes(int counter, int snakes) {
		if (snakes > 0) {
			int snakeTailValue = snakeTailValue(counter, this.tail.getSlotNumber());
			snakeHeadValue(counter, snakeTailValue, this.tail.getSlotNumber());
			addSnakes(++counter, --snakes);
		}
	}

	/**
	 * <b>Name:</b> snakeTailValue <br>
	 * <b>Description:</b> Method used to add the tail of a snake to the game
	 * board. <br>
	 *
	 * @param value Number identifier of the snake.
	 * @param end   Final value of the game board.
	 * @return Number of the square in which the snake was located.
	 */
	private int snakeTailValue(int value, int end) {
		int snakeTailValue = 1 + random.nextInt(end - 2);
		Slot snakeTail = search(snakeTailValue);
		if (checkNextSlotsAvailability(snakeTail, true)) {
			if (snakeTail.getSnake() == null && snakeTail.getLadder() == null) {
				snakeTail.setSnake(value + "A");
				return snakeTailValue;
			}
		}
		return snakeTailValue(value, end);
	}

	/**
	 * <b>Name:</b> snakeHeadValue <br>
	 * <b>Description:</b> Method used to add the head of a snake to the game
	 * board.<br>
	 *
	 * @param value Number identifier of the snake.
	 * @param from  Value of the slot in which the tail of the snake is located.
	 * @param end   Final value of the last slot in the game board.
	 * @return Number of the square in which the snake was located.
	 */
	/*private int snakeHeadValue(int value, int from, int end) {
		int snakeHeadValue = from + random.nextInt(end - from);
		Slot snakeHead = search(snakeHeadValue);
		if (snakeHead.getSnake() == null && snakeHead.getLadder() == null) {
			snakeHead.setSnake(value + "B");
			return snakeHeadValue;
		} else
			return snakeHeadValue(value, from, end);
	}*/
	private int snakeHeadValue(int value, int from, int end) {
		int snakeHeadValue = from + random.nextInt(end - from);
		try {
			Slot snakeHead = search(snakeHeadValue);
			if (snakeHead.getSnake() == null && snakeHead.getLadder() == null) {
				snakeHead.setSnake(value + "B");
				return snakeHeadValue;
			} else {
				return snakeHeadValue(value, from, end);
			}
		} catch (StackOverflowError e) {
			System.out.println("Wait a minute...");
			return -1;
		}
	}

	/**
	 * <b>Name:</b> addLadders <br>
	 * <b>Description:</b> Trigger method used to add the ladders to the
	 * game board.<br>
	 *
	 * <b><i>pre:</i></b> The game board must be created in advice. <br>
	 * <b><i>pos:</i></b> The ladders are added to the game board. <br>
	 *
	 * @param ladders Amount of ladders to be added.
	 */
	public void addLadders(int ladders) {
		addLadders('A', ladders);
	}

	/**
	 * <b>Name:</b> addLadders <br>
	 * <b>Description:</b> Recursive method used to add the ladders to the
	 * game board.<br>
	 *
	 * <b><i>pre:</i></b> The game board must be created in advice. <br>
	 * <b><i>pos:</i></b> The ladders are added to the game board. <br>
	 *
	 * @param character Character that identifies the stair being added.
	 * @param ladders   Amount of ladders to be added.
	 */
	private void addLadders(char character, int ladders) {
		if (ladders > 0) {
			int ladderFloorValue = ladderFloorValue(character, this.tail.getSlotNumber());
			ladderCeilValue(character, ladderFloorValue, this.tail.getSlotNumber());
			addLadders(++character, --ladders);
		}
	}

	/**
	 * <b>Name:</b> ladderFloorValue <br>
	 * <b>Description:</b> Method used to add the floor of a ladder to the game
	 * board. <br>
	 *
	 * @param character Character that identifies the stair being added.
	 * @param end       Value of the last slot in the game board.
	 * @return Number of the slot in which the ladder floor is located
	 */
	/*private int ladderFloorValue(char character, int end) {
		int ladderFloorValue = 2 + random.nextInt(end - 3);
		Slot ladderFloor = search(ladderFloorValue);
		if (checkNextSlotsAvailability(ladderFloor.getNext(), false)) {
			if (ladderFloor.getLadder() == null && ladderFloor.getSnake() == null) {
				ladderFloor.setLadder(character + "1");
				return ladderFloorValue;
			}
		}
		return ladderFloorValue(character, end);
	}*/
	private int ladderFloorValue(char character, int end) {
		int ladderFloorValue = 2 + random.nextInt(end - 3);
		try {
			Slot ladderFloor = search(ladderFloorValue);
			if (checkNextSlotsAvailability(ladderFloor.getNext(), false)) {
				if (ladderFloor.getLadder() == null && ladderFloor.getSnake() == null) {
					ladderFloor.setLadder(character + "1");
					return ladderFloorValue;
				}
			}
			return ladderFloorValue(character, end);
		} catch (StackOverflowError e) {
			System.out.println("Wait a minute...");
			return -1;
		}
	}

	/**
	 * <b>Name:</b> ladderCeilValue <br>
	 * <b>Description:</b> Method used to add the ceil of a ladder to the game
	 * board. <br>
	 *
	 * @param character Character that identifies the stair being added.
	 * @param from      Value of the slot in which the floor of the ladder is
	 *                  located.
	 * @param end       Value of the last slot in the game board.
	 * @return Number of the slot in which the ladder ceil is located
	 */
	private int ladderCeilValue(char character, int from, int end) {
		int ladderCeilValue = from + random.nextInt(end - from) + 1;
		Slot ladderCeil = search(ladderCeilValue);
		if (ladderCeil.getLadder() == null && ladderCeil.getSnake() == null) {
			ladderCeil.setLadder(character + "2");
			return ladderCeilValue;
		} else
			return ladderCeilValue(character, from, end);
	}

	private boolean checkNextSlotsAvailability(Slot current, boolean snakes) {
		if (current != null) {
			if (current.equals(this.tail) && snakes)
				return false;
			if (current.getSnake() == null && current.getLadder() == null) {
				return true;
			}
			return checkNextSlotsAvailability(current.getNext(), snakes);

		}
		return false;
	}

	public String printSlots() {
		if (this.head == null)
			return "[]";
		int end = this.tail.getSlotNumber();
		return print(this.tail, rows, end, end - columns, false);
	}

	public String printSnakesAndLadders() {
		if (this.head == null)
			return "[]";
		int end = this.tail.getSlotNumber();
		return print(this.tail, rows, end, end - columns, true);
	}

	private String print(Slot current, int row, int fromIndex, int toIndex, boolean snakesAndLadders) {
		if (row == 0)
			return "";
		Slot slotNextRow = search(current.getSlotNumber() - columns);
		if (row % 2 != 0) {
			if (snakesAndLadders)
				return printReverseRowSnakesAndLadders(current, fromIndex, toIndex) + "\n"
						+ print(slotNextRow, --row, fromIndex - columns, toIndex - columns, snakesAndLadders);
			else
				return printReverseRowSlots(current, fromIndex, toIndex) + "\n"
						+ print(slotNextRow, --row, fromIndex - columns, toIndex - columns, snakesAndLadders);
		} else {
			if (snakesAndLadders)
				return printRowSnakesAndLadders(current, fromIndex, toIndex) + "\n"
						+ print(slotNextRow, --row, fromIndex - columns, toIndex - columns, snakesAndLadders);
			else
				return printRowSlots(current, fromIndex, toIndex) + "\n"
						+ print(slotNextRow, --row, fromIndex - columns, toIndex - columns, snakesAndLadders);
		}
	}

	private String printRowSlots(Slot current, int fromIndex, int toIndex) {
		if (fromIndex - 1 == toIndex)
			return "[ " + printSlot(current) + "]";
		return "[ " + printSlot(current) + "] " + printRowSlots(current.getPrevious(), --fromIndex, toIndex);
	}

	private String printReverseRowSlots(Slot current, int fromIndex, int toIndex) {
		if (fromIndex - 1 == toIndex)
			return "[ " + printSlot(current) + "]";
		return printReverseRowSlots(current.getPrevious(), --fromIndex, toIndex) + " [ " + printSlot(current) + "]";
	}

	private String printRowSnakesAndLadders(Slot current, int fromIndex, int toIndex) {
		String msg = "  ";
		String snake = current.getSnake();
		String ladder = current.getLadder();
		if (snake != null)
			msg = snake.substring(0, snake.length() - 1);
		else if (ladder != null)
			msg = ladder.substring(0, ladder.length() - 1);
		if (!msg.equals("  "))
			msg += " ";
		if (fromIndex - 1 == toIndex)
			return "[ " + msg + "]";
		else {
			return "[ " + msg + "] "
					+ printRowSnakesAndLadders(current.getPrevious(), --fromIndex, toIndex);
		}
	}

	private String printReverseRowSnakesAndLadders(Slot current, int fromIndex, int toIndex) {
		String msg = "  ";
		String snake = current.getSnake();
		String ladder = current.getLadder();
		if (snake != null)
			msg = snake.substring(0, snake.length() - 1);
		else if (ladder != null)
			msg = ladder.substring(0, ladder.length() - 1);
		if (!msg.equals("  "))
			msg += " ";
		if (fromIndex - 1 == toIndex)
			return "[ " + msg + "]";
		else {
			return printReverseRowSnakesAndLadders(current.getPrevious(), --fromIndex, toIndex) + " [ "
					+ msg + "]";
		}
	}

	private String printSlot(Slot current) {
		String msg = current.getSlotNumber() + " ";
		if (player1.getSlot().equals(current)) {
			msg += player1.getSymbol();
		}
		if (player2.getSlot().equals(current)) {
			msg += player2.getSymbol();
		}
		if (player3.getSlot().equals(current)) {
			msg += player3.getSymbol();
		}
		if (!msg.equals(current.getSlotNumber() + " ")) {
			msg += " ";
		}
		return msg;
	}

	/**
	 * <b>Name:</b> rollDice <br>
	 * <b>Description:</b> Select random number, call movePlayer method to current
	 * player and
	 * refresh the current player. <br>
	 *
	 * @return int dice: Represent a message with dice number, current player and
	 *         if player uses a snake or ladder.
	 */

	public String rollDice() {
		int dice = 1 + random.nextInt(6);
		Player player = getCurrentPlayer();
		movePlayer(player, dice);
		String msg = "\nPlayer " + player.getSymbol() + " rolled: " + dice + checkPlayerSlot(player);
		if (!winner) {
			this.currentPlayer = nextPlayer();
		} else {
			msg += "\n" + printSlots();
			msg += "\nMatch finishes. The player " + player.getSymbol() + " is the winner.";
			this.matchScore = calculateScore();
			msg += "\nPlayer score: " + matchScore;
		}
		return msg;
	}

	public Player getCurrentPlayer() {
		switch (currentPlayer) {
			case 1:
				return player1;
			case 2:
				return player2;
			case 3:
				return player3;
			default:
				return null;
		}
	}

	private int nextPlayer() {
		int nextPlayer = 0;
		if (this.currentPlayer == 1) {
			nextPlayer = 2;
		}
		if (this.currentPlayer == 2) {
			nextPlayer = 3;
		}
		if (this.currentPlayer == 3) {
			nextPlayer = 1;
		}
		return nextPlayer;
	}

	/**
	 * <b>Name:</b> movePLayer <br>
	 * <b>Description:</b> Change the aim of the current player slot by slot to the
	 * new
	 * position. <br>
	 *
	 *
	 * @param player
	 * @param diceNumber
	 */

	public void movePlayer(Player player, int diceNumber) {
		if (player.getSlot() == this.tail && diceNumber == 0) {
			winner = true;
			return;
		}
		if (diceNumber > 0) {
			if (player.getSlot() == this.tail) {
				bouncePlayer(player, diceNumber);
				return;
			}
			player.setSlot(player.getSlot().getNext());
			movePlayer(player, --diceNumber);
		}
	}

	private void bouncePlayer(Player player, int diceNumber) {
		if (diceNumber != 0) {
			player.setSlot(player.getSlot().getPrevious());
			bouncePlayer(player, --diceNumber);
		}
	}

	public long calculateScore() {
		stopMatch();
		return (600 - time) / 6;
	}

	/**
	 * <b>Name:</b> checkPlayerSlot <br>
	 * <b>Description:</b> Verify if slot of current player is a snake or ladder,
	 * also
	 * if is true, check that player can do it from that snake head or ladder floor.
	 * <br>
	 *
	 *
	 * @param player
	 * @return status
	 */

	public String checkPlayerSlot(Player player) {
		String status = "\n";
		String id = "";
		if (player.checkSlot() == 1) {
			id = player.getSlot().getLadder();
			if (id.charAt(id.length() - 1) == '1') {
				useLadder(searchLadderCeil(player.getSlot().getNext(), id.charAt(0)), player); // Esto falla si hay más
				// de 10 escaleras
				status += "How lucky! You went up the stairs!\n";
			}
		} else if (player.checkSlot() == 2) {
			id = player.getSlot().getSnake();
			if (id.charAt(id.length() - 1) == 'B') {
				useSnake(searchSnakeTail(player.getSlot().getPrevious(), id.substring(0, id.length() - 1)), player);
				status += "Bad luck. You got eaten by snake!\n";
			}
		}
		return status;
	}

	/**
	 * <b>Name:</b> useSnake <br>
	 * <b>Description:</b> Set player from snake head to snake tail. <br>
	 *
	 *
	 * @param snakeTail
	 * @param player
	 */

	public void useSnake(Slot snakeTail, Player player) {
		player.setSlot(snakeTail);
		return;
	}

	/**
	 * <b>Name:</b> useLadder <br>
	 * <b>Description:</b> Set player from ladder floor to ladder ceil. <br>
	 *
	 *
	 * @param ceilLeader
	 * @param player
	 */

	public void useLadder(Slot ceilLeader, Player player) {
		player.setSlot(ceilLeader);
		if (player.getSlot() == this.tail) {
			winner = true;
		}
	}

	/**
	 * <b>Name:</b> searchLadderCeil <br>
	 * <b>Description:</b> Search the ladder ceil, and return it. <br>
	 *
	 *
	 * @param current
	 * @param id
	 * @return ladderCeil
	 */

	private Slot searchLadderCeil(Slot current, char id) {
		if (current.getLadder() != null) {
			if (id == current.getLadder().charAt(0)) {
				return current;
			}
		}
		return searchLadderCeil(current.getNext(), id);
	}

	/**
	 * <b>Name:</b> searchSnakeTail <br>
	 * <b>Description:</b> Search the snake tail, and return it. <br>
	 *
	 * @param current
	 * @param id
	 * @return snakeTail
	 */

	private Slot searchSnakeTail(Slot current, String id) {
		String snake = current.getSnake();
		if (snake != null) {
			if (id.equals(snake.substring(0, snake.length() - 1))) {
				return current;
			}
		}
		return searchSnakeTail(current.getPrevious(), id);
	}

	public long getMatchScore() {
		return matchScore;
	}

}