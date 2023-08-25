package id208322966_id207082280;

public class OpenQuestion extends Question{
	private Answer answer;
	
	public OpenQuestion(String queText, Answer answer) {
		super(queText);
		this.id = serialNumber++;
		this.answer = answer;
	}

	public int getId() {
		return id;
	}

	public Answer getAnswer() {
		return answer;
	}

	public boolean setAnswer(Answer answer) {
		this.answer = answer;
		return true;
	}

	public String toString() {
		return super.toString() + "\nID: " + id + "\n"+ answer.toString() + "\n";
	}
	
	public boolean equals(Object o) {
		if(!super.equals(o))
			return false;
		if(!(o instanceof OpenQuestion))
			return false;
		OpenQuestion oq = (OpenQuestion) o;
		if(!(oq.answer.equals(this.answer)))
			return false;
		return true;
	}
}
