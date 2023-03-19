package model;

public class ScoreBoard {

	private Score root;

	public ScoreBoard() {
		this.root = null;
	}

	public void addScore(Score newScore) {
		if (root == null) {
			this.root = newScore;
		} else {
			addScore(newScore, this.root);
		}
	}

	private void addScore(Score newScore, Score current) {
		if (newScore.getScore() < current.getScore()) {
			if (current.getLeft() == null) {
				current.setLeft(newScore);
			} else {
				addScore(newScore, current.getLeft());
			}
		} else if (newScore.getScore() > current.getScore()) {
			if (current.getRight() == null) {
				current.setRight(newScore);
			} else {
				addScore(newScore, current.getRight());
			}
		} else {
			current.setNames(current.getNames() + "\n" + newScore.getNames());
		}
	}

	public String printGameBoard() {
		return "\n<<<<<<<<  SCORE BOARD  >>>>>>>>\n" + "===============================\n\nPoints    Nickname\n"
				+ postOrderString(root)
				+ "\n===============================\n";
	}

	private String postOrderString(Score current) {
		if (current == null) {
			return "";
		}
		return postOrderString(current.getRight()) + current.toString() + postOrderString(current.getLeft());
	}

}