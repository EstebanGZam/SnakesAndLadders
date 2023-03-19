package model;

public class ScoreBoard {

	private Score root;

	public ScoreBoard(){
		this.root = null;
	}

	public void addScore(Score newScore){
		if (root == null) {
			this.root = newScore;
		} else {
			addScore(newScore, this.root);
		}
		return;
	}

	private void addScore(Score newScore, Score current){

		if(newScore.getScore() < current.getScore()){
			if(current.getLeft() == null){
				current.setLeft(newScore);
				return;
			}
			else{
				addScore(newScore, current.getLeft());
			}
		}
		// Derecha
		else if(newScore.getScore() > current.getScore()){
			if(current.getRight() == null){
				current.setRight(newScore);
				return;
			}
			else{
				addScore(newScore, current.getRight());
			}
		}
		else{
			if(newScore.getName().equals(current.getName())){
				// Si el nick y el puntaje son iguales, no se agrega
				return;
			}
			else if(newScore.getName().compareTo(current.getName()) < 0){
				if(current.getLeft() == null){
					current.setLeft(newScore);
					return;
				}
				else{
					addScore(newScore, current.getLeft());
				}
			}
			else{
				if(current.getRight() == null){
					current.setRight(newScore);
					return;
				}
				else{
					addScore(newScore, current.getRight());
				}
			}
		}
	}

	public String inOrderString(){
		return "<<<<<<<<  SCORES  >>>>>>>>\n"+ "========================\n" + inOrderString(root) + "========================";
	}

	private String inOrderString(Score current){
		if(current == null){
			return "";
		}

		return inOrderString(current.getRight()) + "\n" + current.toString() + "\n" + inOrderString(current.getLeft()) +  "\n";
	}

}