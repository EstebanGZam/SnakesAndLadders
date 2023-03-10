package model;

import java.util.Random;

public class GameBoard {

	private Slot head, tail;
	private Player player1, player2, player3;
	private int rows, columns;
	private Random random = new Random();
	private int currentPlayer;
	private boolean isWinner;

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
		this.isWinner = false;
		this.columns = columns;
		int numberOfSlots = rows * columns;
		addSlots(numberOfSlots);
		String symbols = generateRandomSymbols();
		player1 = new Player(this.head, Character.getNumericValue(symbols.charAt(0)));
		player2 = new Player(this.head, Character.getNumericValue(symbols.charAt(1)));
		player3 = new Player(this.head, Character.getNumericValue(symbols.charAt(2)));
		currentPlayer = 1;
		addSnakes(snakes);
		addLadders(ladders);
	}

	public boolean getIsWinner(){
		return this.isWinner;
	}
	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
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
		int snakeTailValue = 1 + random.nextInt(end - 1);
		Slot snakeTail = search(snakeTailValue);
		if (snakeTail.getSnake() == null && snakeTail.getLadder() == null) {
			snakeTail.setSnake(value + "A");
			return snakeTailValue;
		} else
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
	private int snakeHeadValue(int value, int from, int end) {
		int snakeHeadValue = from + random.nextInt(end - from);
		Slot snakeHead = search(snakeHeadValue);
		if (snakeHead.getSnake() == null && snakeHead.getLadder() == null) {
			snakeHead.setSnake(value + "B");
			return snakeHeadValue;
		} else
			return snakeTailValue(value, end);
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
	private int ladderFloorValue(char character, int end) {
		int ladderFloorValue = 2 + random.nextInt(end - 3);
		Slot ladderFloor = search(ladderFloorValue);
		if (ladderFloor.getLadder() == null && ladderFloor.getSnake() == null) {
			ladderFloor.setLadder(character + "1");
			return ladderFloorValue;
		} else
			return ladderFloorValue(character, end);
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
	 * rollDice: Select random number, call movePlayer method to current player and refresh the current player
	 * @return int dice: Represent dice number
	 */

	public int rollDice(){
		int dice = 1+random.nextInt(6);
		if(this.currentPlayer == 1){
			this.isWinner = movePlayer(player1, dice);
		}else if (this.currentPlayer == 2){
			this.isWinner = movePlayer(player2, dice);
		}else if(this.currentPlayer == 3){
			this.isWinner = movePlayer(player3, dice);
		}

		if (this.currentPlayer == 3) {
			setCurrentPlayer(1);
		} else{
			setCurrentPlayer(this.currentPlayer + 1);
		}
		return dice;
	}

	/**
	 * movePLayer: Change the aim of the current player slot by slot to the new position
	 * @param player
	 * @param diceNumber
	 */

	public boolean movePlayer(Player player, int diceNumber) {
		if(player.getSlot().getSlotNumber() == this.tail.getSlotNumber() && diceNumber == 0){
			return true;
		} else if (player.getSlot().getSlotNumber() == this.tail.getSlotNumber() && diceNumber != 0) {
			bouncePlayer(player, diceNumber);
			return false;
		} else if(diceNumber == 0){
			return false;
		}
		player.setSlot(player.getSlot().getNext());
		diceNumber--;
		return movePlayer(player, diceNumber);

	}

	private void bouncePlayer(Player player, int diceNumber){
		if(diceNumber == 0){
			return;
		}
		player.setSlot(player.getSlot().getPrevious());
		diceNumber--;
		bouncePlayer(player, diceNumber);
		return;
	}

	public void useSnake() {
	}

	public void useLadder() {
	}

}