package model;

import java.util.Random;

public class GameBoard {

	private Slot head, tail;
	private int rows, columns;
	private Random random = new Random();

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
		addSnakes(snakes);
		addLadders(ladders);
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
			return ladderCeilValue(value, from, end);
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
			return "[ " + current.getSlotNumber() + " ]";
		return "[ " + current.getSlotNumber() + " ] " + printRow(current.getPrevious(), --fromIndex, toIndex);
	}

	public String printReverseRow(Slot current, int fromIndex, int toIndex) {
		if (fromIndex - 1 == toIndex)
			return "[ " + current.getSlotNumber() + " ]";
		return printReverseRow(current.getPrevious(), --fromIndex, toIndex) + " [ " + current.getSlotNumber() + " ]";

	}

	/**
	 * 
	 * @param player
	 * @param diceNumber
	 */
	public void movePlayer(Player player, int diceNumber) {
	}

	public void useSnake() {
	}

	public void useLadder() {
	}

}