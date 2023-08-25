package id208322966_id207082280;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class QuestionStock {
	private Vector<Question> questions;  // changed

	public QuestionStock() {
		this.questions = new Vector<Question>();   // changed
	}

	public Vector<Question> getQuestions() { // changed
		return questions;
	}

	public int getNumOfQue() {
		return questions.size();  // changed
	}

	public int howManyCorrectAnsAmericanQue(Question q) {
		AmericanQuestion aq = (AmericanQuestion) q;
		return aq.howManyCorrectAnswers();
	}
	
	public void sortByAnsLength() {
		Map<Integer, Question> m = new TreeMap<Integer, Question>();
		for (int i = 0; i < questions.size(); i++) {
			int charAmo = getAnswerLengthInIndex(i);
			if(m.containsKey(charAmo))
				charAmo++;
			m.put(charAmo, questions.get(i));
		}
		int index = 0;
		for( Integer i : m.keySet()) {
			questions.set(index++, m.get(i));
		}
	}  
	
	private int getAnswerLengthInIndex(int index) {
		int ret = 0;
		if(questions.get(index) instanceof OpenQuestion) {
			OpenQuestion oq = (OpenQuestion) questions.get(index);
			ret = oq.getAnswer().getCharAmount();
		}
		else if(questions.get(index) instanceof AmericanQuestion) {
			AmericanQuestion aq = (AmericanQuestion) questions.get(index);
			int numOfAns = aq.getNumOfAns();
			for (int i = 0; i < numOfAns; i++) {
				ret += aq.getAnswers().get(i).getCharAmount();
			}
		}
		return ret;
	}

	public void sortQuestionsAlph() {

		for (int i = questions.size() - 1; i > 0; i--) { // bubble sort
			for (int j = 0; j < i; j++) {
				if (questions.get(j).getText().compareToIgnoreCase(questions.get(j + 1).getText()) > 0) {
					Question tmp = questions.get(j);
					questions.set(j, questions.get(j + 1));
					questions.set(j + 1, tmp);
				}
			}
		}
	}

	public boolean canYouGenerateTestFromQuestions(int num) {
		int[] ameQueInd = getAmericanQuestionsIndices();
		if (num == questions.size()) {
			for (int i = 0; i < ameQueInd.length; i++) {
				if (getNumOfAnsAmericanQuestion(questions.get(ameQueInd[i]))
						- howManyCorrectAnsAmericanQue(questions.get(ameQueInd[i])) < 3)
					return false;
			}
			return true;
		}
		int minAmericanQuestions = num - howManyOpenQuestions();
		if (minAmericanQuestions <= 0)
			return true;
		for (int i = 0; i < ameQueInd.length; i++) {
			if (getNumOfAnsAmericanQuestion(questions.get(ameQueInd[i]))
					- howManyCorrectAnsAmericanQue(questions.get(ameQueInd[i])) >= 3)
				minAmericanQuestions--;
			if(minAmericanQuestions == 0)
				return true;
		}		
		return false;
	}
	
	public boolean canYouGenerateLegalQuestion(Question q) {
		AmericanQuestion aq = (AmericanQuestion)q;
		return (getNumOfAnsAmericanQuestion(aq)
					- howManyCorrectAnsAmericanQue(aq) >= 3);
	}

	public int howManyOpenQuestions() {
		int cnt = 0;
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i) instanceof OpenQuestion)
				cnt++;
		}
		return cnt;
	}

	public int[] getAmericanQuestionsIndices() {
		int len = 0;
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i) instanceof AmericanQuestion)
				len++;
		}
		int[] ret = new int[len];
		int index = 0;
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i) instanceof AmericanQuestion)
				ret[index++] = i;
		}
		return ret;
	}

	public boolean isAnswerCorrect(Question q, int ansNum) {
		return ((AmericanQuestion) q).isAnswerCorrect(ansNum);
	}

	public int getNumOfAnsAmericanQuestion(Question q) {
		
		return ((AmericanQuestion) q).getNumOfAns();
	}

	public boolean keepAnswers(int id, Vector<Integer> ansNumArr) throws AddAnswersException {
		return ((AmericanQuestion) questions.get(getIndexByID(id))).keepAnswers(ansNumArr);
	} 

	public boolean deleteAnswer(int id, int ansNum) throws AddAnswersException {
		int index = getIndexByID(id);
		if (index == -1)
			return false;
		AmericanQuestion aq = (AmericanQuestion) questions.get(id - 1);
		if (ansNum > aq.getNumOfAns() || ansNum < 1)
			return false;
		aq.deleteAnswer(aq, ansNum);
		return true;
	}

	public boolean setOpenAnswer(String ansText, int id) {
		int index = getIndexByID(id);
		if (index == -1)
			return false;

		OpenQuestion oq = (OpenQuestion) questions.get(index);
		oq.setAnswer(new Answer(true, ansText));
		questions.set(index, oq);

		return true;
	}

	public boolean setAmericanAnswer(String ansText, int id, int ansNum) {
		int index = getIndexByID(id);
		if (index == -1)
			return false;

		AmericanQuestion aq = (AmericanQuestion) questions.get(id - 1);
		if (ansNum > aq.getNumOfAns() || ansNum < 1)
			return false;

		Answer newAns = new Answer(aq.getAnswers().get(ansNum - 1).isCorrect(), ansText);
		aq.getAnswers().set(ansNum - 1, newAns);
		questions.set(id - 1, aq);

		return true;
	}

	public boolean setQuestion(String queText, int id) {
		int index = getIndexByID(id);
		if (index == -1)
			return false;
		if (questions.get(index) instanceof OpenQuestion) {
			OpenQuestion que = new OpenQuestion(queText, null);
			if (isExists(que))
				return false;
		} else {
			AmericanQuestion que = new AmericanQuestion(queText);
			if (isExists(que))
				return false;
		}
		questions.get(index).setText(queText);
		return true;
	}

	public int getIndexByID(int id) {
		int index = -1;
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).getId() == id)
				index = i;
		}
		return index;
	}

	public String toString() {
		String ret = "";
		for (int i = 0; i < questions.size(); i++) {
			ret += questions.get(i).toString() + "\n";
		}
		return ret;
	}

	public boolean addAmericanQuestion(String queText, Vector<String> answers, Vector<Boolean> isCorrect)
			throws AddAnswersException {
		Set<Answer> ansSet = new Set<Answer>(); 	
		for (int i = 0; i < answers.size(); i++) {
			ansSet.add(new Answer(isCorrect.get(i), answers.get(i)));
		}

		AmericanQuestion aq = new AmericanQuestion(queText);
		aq.addAnswers(ansSet);

		return addQuestion(aq);
	}

	public boolean addOpenQuestion(String queText, String answer) {
		Answer a = new Answer(true, answer);
		OpenQuestion oq = new OpenQuestion(queText, a);

		return addQuestion(oq);
	}

	public boolean addOpenQuestion(Question q) {
		OpenQuestion oq = (OpenQuestion) q;
		return addQuestion(oq);
	}

	public boolean addAmericanQuestion(Question q) {
		AmericanQuestion aq = (AmericanQuestion) q;
		return addQuestion(new AmericanQuestion(aq));
	}

	public boolean addQuestion(Question q) {
		if (isExists(q))
			return false;

		this.questions.add(q);
		return true;
	}

	public boolean isExists(String txt, int type) {
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i) instanceof OpenQuestion) {
				if (type == 1 && txt.equals(this.questions.get(i).getText())) {
					return true;
				}
			} else if (type == 2 && txt.equals(this.questions.get(i).getText())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isExists(Question q) {
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i) instanceof OpenQuestion) {
				if (q instanceof OpenQuestion && q.getText().equals(this.questions.get(i).getText())) {
					return true;
				}
			} else if (q instanceof AmericanQuestion && q.getText().equals(this.questions.get(i).getText())) {
				return true;
			}
		}
		return false;
	}

	public boolean isAmericanQue(Question q) {
		return (q instanceof AmericanQuestion);
	}

	public boolean isOpenQue(Question q) {
		return (q instanceof OpenQuestion);
	}

	public boolean equals(Object o) {
		if(!(o instanceof QuestionStock))
			return false;
		QuestionStock qs = (QuestionStock)o;
		return questions.equals(qs.questions);
	}

}
