package id208322966_id207082280;

public abstract class  Question  {
	protected static int serialNumber = 1;
	protected String text;
	protected int id;
	
	public Question(String text) {
		this.text = text;
	}
	
	public void setID(int newID) {
		this.id = newID;
	}
	
	public int getId() {
		return id;
	}

	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		Question.serialNumber = serialNumber;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public boolean equals(Object obj) {
		if(!(obj instanceof Question))
			return false;
		Question q = (Question) obj;
		return (q.getText().equals(this.getText()) && q.getSerialNumber() == this.getSerialNumber());
	}
	
	public String toString() {
		return "Question text: " + text;
	}
}
