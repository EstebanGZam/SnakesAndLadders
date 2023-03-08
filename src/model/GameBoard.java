package model;

import java.util.Random;

public class GameBoard {

	private Slot head, tail;
	private Player player1, player2, player3;
	private int rows, columns;
	private Random random = new Random();

	private int currentPlayer;

	/**
	 * 
	 * @param rows
	 * @param columns
	 * @param snakes
	 * @param ladders
	 */
	public GameBoard(int rows, int columns, int snakes, int ladders) {
		this.rows = rows;
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

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public String generateRandomSymbols() {
		int a = 1 + random.nextInt(9);
		int b = 1 + random.nextInt(9);
		int c = 1 + random.nextInt(9);
		if (a == b || b == c || c == a)
			return generateRandomSymbols();
		return a + "" + b + "" + c;
	}

	/**
	 * @param numberOfSlots
	 */
	public void addSlots(int numberOfSlots) {
		addSlots(0, numberOfSlots);
	}

	private void addSlots(int index, int numberOfSlots) {
		if (index < numberOfSlots) {
			insertLast(new Slot(index + 1));
			addSlots(++index, numberOfSlots);
		}
	}

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

	public Slot search(int value) {
		return search(this.head, value);
	}

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

	public void addSnakes(int snakes) {
		addSnakes(1, snakes);
	}

	private void addSnakes(int counter, int snakes) {
		if (snakes > 0) {
			int snakeTailValue = snakeTailValue(counter, this.tail.getSlotNumber());
			snakeHeadValue(counter, snakeTailValue, this.tail.getSlotNumber());
			addSnakes(++counter, --snakes);
		}
	}

	private int snakeTailValue(int value, int end) {
		int snakeTailValue = 1 + random.nextInt(end - 1);
		Slot snakeTail = search(snakeTailValue);
		if (snakeTail.getSnake() == null && snakeTail.getLadder() == null) {
			snakeTail.setSnake(value + "A");
			return snakeTailValue;
		} else
			return snakeTailValue(value, end);
	}

	private int snakeHeadValue(int value, int from, int end) {
		int snakeHeadValue = from + random.nextInt(end - from);
		Slot snakeHead = search(snakeHeadValue);
		if (snakeHead.getSnake() == null && snakeHead.getLadder() == null) {
			snakeHead.setSnake(value + "B");
			return snakeHeadValue;
		} else
			return snakeTailValue(value, end);
	}

	public void addLadders(int ladders) {
		addLadders('A', ladders);
	}

	private void addLadders(char counter, int ladders) {
		if (ladders > 0) {
			int ladderFloorValue = ladderFloorValue(counter, this.tail.getSlotNumber());
			ladderCeilValue(counter, ladderFloorValue, this.tail.getSlotNumber());
			addLadders(++counter, --ladders);
		}
	}

	private int ladderFloorValue(char value, int end) {
		int ladderFloorValue = 2 + random.nextInt(end - 3);
		Slot ladderFloor = search(ladderFloorValue);
		if (ladderFloor.getLadder() == null && ladderFloor.getSnake() == null) {
			ladderFloor.setLadder(value + "1");
			return ladderFloorValue;
		} else
			return ladderFloorValue(value, end);
	}

	private int ladderCeilValue(char value, int from, int end) {
		int ladderCeilValue = from + random.nextInt(end - from) + 1;
		Slot ladderCeil = search(ladderCeilValue);
		if (ladderCeil.getLadder() == null && ladderCeil.getSnake() == null) {
			ladderCeil.setLadder(value + "2");
			return ladderCeilValue;
		} else
			return ladderCeilValue(value, from, end); // corregir esta linea
	}

	public String print() {
		if (this.head == null)
			return "[]";
		int end = this.tail.getSlotNumber();
		return print(this.tail, rows, end, end - columns);
	}

	private String print(Slot current, int row, int fromIndex, int toIndex) {
		if (row == 0)
			return "";
		Slot slotNextRow = search(current.getSlotNumber() - columns);
		if (row % 2 != 0)
			return printReverseRow(current, fromIndex, toIndex) + "\n"
					+ print(slotNextRow, --row, fromIndex - columns, toIndex - columns);
		else
			return printRow(current, fromIndex, toIndex) + "\n"
					+ print(slotNextRow, --row, fromIndex - columns, toIndex - columns);
	}

	public String printRow(Slot current, int fromIndex, int toIndex) {
		if (fromIndex - 1 == toIndex)
			return "[ " + printSlot(current) + "]";
		return "[ " + printSlot(current) + "] " + printRow(current.getPrevious(), --fromIndex, toIndex);
	}

	public String printReverseRow(Slot current, int fromIndex, int toIndex) {
		if (fromIndex - 1 == toIndex)
			return "[ " + printSlot(current) + "]";
		return printReverseRow(current.getPrevious(), --fromIndex, toIndex) + " [ " + printSlot(current) + "]";
	}

	public String printSlot(Slot current) {
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



	public int rollDice(){
		int dice = 1+random.nextInt(6);
		if(this.currentPlayer == 1){
			movePlayer(player1, dice);
		}else if (this.currentPlayer == 2){
			System.out.println("Entre a mover al player 2");
			movePlayer(player2, dice);
		}else if(this.currentPlayer == 3){
			movePlayer(player3, dice);
		}

		if (this.currentPlayer == 3) {
			setCurrentPlayer(1);
		} else{
			setCurrentPlayer(this.currentPlayer + 1);
		}
		return dice;
	}

	/**
	 *
	 * @param player
	 * @param diceNumber
	 */

	public void movePlayer(Player player, int diceNumber) {
		if(diceNumber == 0){
			return;
		} else if (player.getSlot().getSlotNumber() >= this.tail.getSlotNumber()-6) {
			if (player.getSlot().getSlotNumber() + diceNumber > this.tail.getSlotNumber()) {
				return;
			}
		}
		player.setSlot(player.getSlot().getNext());
		diceNumber--;
		movePlayer(player, diceNumber);
		return;
	}

	/*public void movePlayer(Player player, int diceNumber) {
		int newNumberSlot = player.getSlot().getSlotNumber() + diceNumber;
		player.setSlot(search(newNumberSlot));
	}*/

	public void useSnake() {
	}

	public void useLadder() {
	}

}