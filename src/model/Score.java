package model;

public class Score{

	private String name;
	private double score;
	private Score right;
	private Score left;

	/**
	 *
	 * @param name
	 * @param score
	 */
	public Score(String name, double score) {
		this.name = name;
		this.score = score;
		this.right = null;
		this.left = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
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

	public String toString(){
		return this.name +"  "+ this.score;
	}
}