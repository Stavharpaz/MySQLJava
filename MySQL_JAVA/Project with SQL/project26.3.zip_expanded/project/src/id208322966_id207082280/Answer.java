package id208322966_id207082280;

public class Answer implements Comparable<Answer>{
	private boolean isCorrect;
	private String text;
	
	public Answer(boolean isCorrect, String text) {
		this.isCorrect = isCorrect;
		this.text = text;
	}
	
	public Answer(Answer other) {
		this.isCorrect = other.isCorrect;
		this.text = other.text;
	}
	
	public int getCharAmount() {
		return text.length();
	}
	
	public String toString() {
		return "Answer text: " + text;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public String getText() {
		return text;
	}

	public boolean equals(Object o) {
		if(!(o instanceof Answer))
			return false;
		Answer a = (Answer)o;
		return (a.isCorrect == this.isCorrect && a.text.equals(this.text));	
	}

	@Override
	public int compareTo(Answer a) {
		if(a.getCharAmount() < this.getCharAmount())
			return -1;
		else if(a.getCharAmount() > this.getCharAmount())
			return 1;
		return 0;
	}
}
