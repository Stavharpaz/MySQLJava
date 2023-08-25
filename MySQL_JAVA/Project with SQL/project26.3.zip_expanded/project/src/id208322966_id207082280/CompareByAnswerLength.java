package id208322966_id207082280;

import java.util.Comparator;

public class CompareByAnswerLength implements Comparator<Answer>{

	@Override
	public int compare(Answer o1, Answer o2) {
		if(o1.getCharAmount() < o2.getCharAmount())
			return -1;
		else if(o1.getCharAmount() > o2.getCharAmount())
			return 1;
		return 0;
	}

}
