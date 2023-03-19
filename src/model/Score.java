package model;

public class Score {

	private String names;
	private double score;
	private Score right;
	private Score left;

	/**
	 *
	 * @param names
	 * @param score
	 */
	public Score(String names, double score) {
		this.names = names;
		this.score = score;
		this.right = null;
		this.left = null;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public double getScore() {
		return score;
	}

	public Score getRight() {
		return right;
	}

	public void setRight(Score right) {
		this.right = right;
	}

	public Score getLeft() {
		return left;
	}

	public void setLeft(Score left) {
		this.left = left;
	}

	@Override
	public String toString() {
		if (this.names.split("\n").length > 1) {
			return toString(this.names.split("\n"), 0);
		}
		return this.score + " " + this.names + "\n";
	}

	public String toString(String[] records, int index) {
		if (index == records.length)
			return "";
		return this.score + " " + records[index] + "\n" + toString(records, ++index);
	}

}