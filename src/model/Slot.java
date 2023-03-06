package model;

public class Slot {

	private Slot previous, next;
	private int slotNumber;
	private String snake;
	private String ladder;

	/**
	 * 
	 * @param slotNumber
	 */
	public Slot(int slotNumber) {

	}

	public Slot getPrevious() {
		return previous;
	}

	public void setPrevious(Slot previous) {
		this.previous = previous;
	}

	public Slot getNext() {
		return next;
	}

	public void setNext(Slot next) {
		this.next = next;
	}

	public int getSlotNumber() {
		return slotNumber;
	}

	public String getSnake() {
		return snake;
	}

	public void setSnake(String snake) {
		this.snake = snake;
	}

	public String getLadder() {
		return ladder;
	}

}