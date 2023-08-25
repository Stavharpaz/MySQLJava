package id208322966_id207082280;

import java.util.Vector;

public class AmericanQuestion extends Question  {
	private Set<Answer> answers;  
	private final int MAX_ANSWERS = 10;
	
	public AmericanQuestion(String queText) {
		super(queText);
		this.id = serialNumber++;
		this.answers = new Set<Answer>();
	}
	
	public AmericanQuestion(AmericanQuestion other) {
		super(other.text);
		this.id = other.id;
		this.answers = new Set<Answer>();
		for (int i = 0; i < other.answers.size(); i++) {
			this.answers.add(new Answer(other.answers.get(i)));
		}
	}
	
	public boolean isAnswerCorrect(int ansNum) {
		return answers.get(ansNum-1).isCorrect();
	}
	
	public int howManyCorrectAnswers() {
		int cnt = 0;
		for (int i = 0; i < answers.size(); i++) {
			if(answers.get(i).isCorrect())
				cnt++;
		}
		return cnt;
	}
	
	public boolean keepAnswers(Vector<Integer> ansNumArr) {
		Set<Answer> ansArr = new Set<Answer>();		
		for (int i = 0; i < ansNumArr.size(); i++) {
			ansArr.add(this.answers.get(ansNumArr.get(i)-1));
		}
		this.answers = ansArr;
		return true;
	}
	
	public boolean deleteAnswer(AmericanQuestion aq, int ansNum) throws AddAnswersException {
		if (ansNum > MAX_ANSWERS || ansNum < 1)
			return false;
		if (aq.getAnswers().size() - 1 <= 0)
			return false;

		Set<Answer> ansArr = new Set<Answer>(); 
		for (int i = 0; i < aq.getAnswers().size(); i++) {
			if (i + 1 != ansNum)
				ansArr.add(aq.getAnswers().get(i));
		}
		aq.answers = ansArr;
		return true;
	}

	public void addAnswers(Set<Answer> answers) throws AddAnswersException {
		if(answers.size() > MAX_ANSWERS - this.answers.size()) {
			throw new AddAnswersException();
		}
		for(int i = 0; i < answers.size(); i++) {
			this.answers.add(answers.get(i));
		}
	}
	
	public Set<Answer> getAnswers() {
		return answers;
	}

	public int getNumOfAns() {
		return answers.size();
	}

	public int getId() {
		return id;
	}
	
	public String toString() {
		String str = super.toString() + "\nID: " + id + "\n";
		
		for (int i = 0; i < answers.size(); i++) {
			str += (i+1) + ") " + answers.get(i).toString() + ((answers.get(i).isCorrect())?"\tCorrect":"\tWrong") + "\n";
		}	
		
		int howManyCorrectAnswers = this.howManyCorrectAnswers();
		if(howManyCorrectAnswers > 1)
			str += "--Has more than one correct answer.\n";
		else if(howManyCorrectAnswers == 1)
			str += "--Has one correct answer.\n";
		else
			str += "--Has no correct answers.\n";
		return str;
	}

	public boolean equals(Object o) {
		if(!super.equals(o))
			return false;
		if(!(o instanceof AmericanQuestion))
			return false;
		AmericanQuestion aq = (AmericanQuestion) o;
		if(!(aq.answers.size() == this.answers.size()))
			return false;
		for (int i = 0; i < answers.size(); i++) {
			if(!(aq.answers.get(i).equals(this.answers.get(i))))
				return false;
		}
		return true;
	}
		
}
