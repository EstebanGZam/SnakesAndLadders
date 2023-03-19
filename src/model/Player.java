package model;

public class Player {

	private Slot slot;
	private char symbol;

	public Player(Slot slot, int number) {
		this.slot = slot;
		setRandomSymbol(number);
	}
	
	/** 
	 * @return Slot
	 */
	public Slot getSlot() {
		return slot;
	}
	
	/** 
	 * @return char
	 */
	public char getSymbol() {
		return this.symbol;
	}

	public void setRandomSymbol(int number) {
		switch (number) {
			case 1:
				this.symbol = '*';
				break;
			case 2:
				this.symbol = '!';
				break;
			case 3:
				this.symbol = 'O';
				break;
			case 4:
				this.symbol = 'X';
				break;
			case 5:
				this.symbol = '%';
				break;
			case 6:
				this.symbol = '$';
				break;
			case 7:
				this.symbol = '#';
				break;
			case 8:
				this.symbol = '+';
				break;
			case 9:
				this.symbol = '&';
				break;
			default:
				this.symbol = '^';
				break;
		}
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

	public int checkSlot(){
		if (this.slot.getLadder() != null) {
			return 1;
		}else if(this.slot.getSnake() != null){
			return 2;
		}
		return 0;
	}
}