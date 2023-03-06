package model;

import java.util.Random;

public class GameBoard {

	private Slot head;
	private Slot tail;
	private Random random = new Random();

	/**
	 * 
	 * @param rows
	 * @param columns
	 * @param snakes
	 * @param ladders
	 */
	public GameBoard(int rows, int columns, int snakes, int ladders) {
		int numberOfSlots = rows * columns;
		addSlots(numberOfSlots);
		addSnakes(snakes);
		addLadders(ladders);
	}

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

	public int snakeTailValue(int value, int end) {
		int snakeTailValue = random.nextInt(end);
		Slot snakeTail = search(snakeTailValue);
		if (snakeTail.getSnake() == null) {
			snakeTail.setSnake(snakeTailValue + "A");
			return snakeTailValue;
		} else
			return snakeTailValue(value, end);
	}

	public int snakeHeadValue(int value, int from, int end) {
		int snakeHeadValue = end + random.nextInt(end - from);
		Slot snakeHead = search(snakeHeadValue);
		if (snakeHead.getSnake() == null) {
			snakeHead.setSnake(snakeHeadValue + "B");
			return snakeHeadValue;
		} else
			return snakeTailValue(value, end);
	}

	public void addLadders(int ladders) {
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