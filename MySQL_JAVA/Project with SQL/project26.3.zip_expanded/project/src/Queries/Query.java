package Queries;

import id208322966_id207082280.*;
import id208322966_id207082280.Set;

import java.sql.*;
import java.util.*;

public class Query {	
	// Queries	
	private static final String QUERY_OPEN_QUESTIONS = "Select questionid, questiontext "
			+ "from openquestions JOIN questions ON openquestions.openquestionid = questions.questionid;";	
	private static final String QUERY_AMERICAN_QUESTIONS = "Select questionid, questiontext "
			+ "from americanquestions JOIN questions ON americanquestions.americanquestionid = questions.questionid;";	
	private static final String QUERY_ANSWERS = "Select * "
			+ "from answers WHERE answers.questionid" ;	
	private static final String QUERY_TEST_IDS = "select * from tests;";
	private static final String QUERY_QUESTION = "select * from questions where questionid = (?)";
	private static final String QUERY_GET_AMERICAN_IDS = "select * from americanquestions;";
	private static final String QUERY_GET_TEST_QUESTION_IDS = "select questionid from test_question where testid = (?);";
	private static final String QUERY_GET_USERS = "select * from coursestaff;";
	
	
	public Staff getUser(Connection con, String username, String password) throws SQLException{
		
		try (Statement stmt = con.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(QUERY_GET_USERS)) {
				while(rs.next()) {
					if(username.equals(rs.getString("staffusername"))) {
						if(password.equals(rs.getString("staffpassword"))) {						
							Staff s = new Staff(rs.getInt("staffid"), rs.getString("firstname") + " " + rs.getString("lastname"), rs.getString("staffrole"));
							return s;
						}
						return null;
					}
				}
			}
		}
		return null;
	}
	
	public QuestionStock getTest(Connection con, int testID) throws SQLException, AddAnswersException {
		QuestionStock test = new QuestionStock();
		Vector<Integer> questionsIDs = new Vector<>();
		try(PreparedStatement stmt = con.prepareStatement(QUERY_GET_TEST_QUESTION_IDS)){
			stmt.setInt(1, testID);
			try (ResultSet rs = stmt.executeQuery())	{
				while(rs.next()) {
					questionsIDs.add(rs.getInt("questionid"));
				}
			}			
		}
		
		Vector<Question> questions = getQuestionsById(con, questionsIDs);
		
		for (int i = 0; i < questions.size(); i++) {
			test.addQuestion(questions.get(i));
		}
		return test;
	}
	
	public Vector<Integer> getTestIds(Connection con) throws SQLException {
		
		Vector<Integer> v = new Vector<>();
		
		try (Statement stmt = con.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(QUERY_TEST_IDS)) {
				while(rs.next()) {
					v.add(rs.getInt("testid"));
				}
			}
		}
		
		return v;
	}
	
	public QuestionStock getQuestionStock(Connection con) throws SQLException, Exception {
		
		QuestionStock qs = new QuestionStock();
		
		try (Statement stmt = con.createStatement()){
			List<OpenQuestion> opQue = getOpenQuestions(con); 			
			for(int i = 0; i < opQue.size(); i++) {
				qs.addOpenQuestion(opQue.get(i));
			}
			List<AmericanQuestion> amQue = getAmericanQuestions(con); 
			for(int i = 0; i < amQue.size(); i++) {
				qs.addAmericanQuestion(amQue.get(i));
			}			
		} 		
		return qs;
	}
	
	public List<OpenQuestion> getOpenQuestions(Connection con) throws SQLException, AddAnswersException {
		
		List<OpenQuestion> rv = new ArrayList<>();
		
		try (Statement stmt = con.createStatement()){
			try (ResultSet rs = stmt.executeQuery(QUERY_OPEN_QUESTIONS)){
				OpenQuestion tmp;
				while (rs.next()) {
					tmp = new OpenQuestion(rs.getString("questionText"), null);
					tmp.setID(rs.getInt("questionID"));
					Set<Answer> ans = getAnswerToQuestion(con, tmp.getId());
					tmp.setAnswer(ans.get(0));
					rv.add(tmp);
				}
			}
		}
		return rv;
	}
	
	public List<AmericanQuestion> getAmericanQuestions(Connection con) throws SQLException, Exception {
		
		List<AmericanQuestion> rv = new ArrayList<>();
		
		try (Statement stmt = con.createStatement()){
			try (ResultSet rs = stmt.executeQuery(QUERY_AMERICAN_QUESTIONS)){
				AmericanQuestion tmp;
				while (rs.next()) {
					tmp = new AmericanQuestion(rs.getString("questionText"));
					tmp.setID(rs.getInt("questionID"));
					Set<Answer> ans = getAnswerToQuestion(con, tmp.getId());
					tmp.addAnswers(ans);
					rv.add(tmp);
				}
			}
		}		
		return rv;
	}
	
	public Vector<Question> getQuestionsById(Connection con, Vector<Integer> ids) throws SQLException, AddAnswersException {
	
		Vector<Question> questions = new Vector<>();
		
		for (int i = 0; i < ids.size(); i++) {
			
			try (PreparedStatement stmt = con.prepareStatement(QUERY_QUESTION)) {
				stmt.setInt(1, ids.get(i));
				
				try (ResultSet rs = stmt.executeQuery()) { 
					while(rs.next()) {
						if(isAmerican(con, ids.get(i)))
						{
							AmericanQuestion ameQue = new AmericanQuestion(rs.getString("questiontext"));
							ameQue.setID(rs.getInt("questionID"));
							Set<Answer> ans = getAnswerToQuestion(con, ameQue.getId());
							ameQue.addAnswers(ans);
							questions.add(ameQue);
						} else {
							OpenQuestion opQue = new OpenQuestion(rs.getString("questiontext"), null);
							opQue.setID(rs.getInt("questionID"));
							Set<Answer> ans = getAnswerToQuestion(con, opQue.getId());
							opQue.setAnswer(ans.get(0));
							questions.add(opQue);
						}
					}
				}
				
			}
			
		}
			
		return questions;
	}
	
	public boolean isAmerican(Connection con, int id) throws SQLException {
		try (Statement stmt = con.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(QUERY_GET_AMERICAN_IDS)) {
				while (rs.next()) {
					if(rs.getInt("americanquestionid") == id)
						return true;
				}
			}
		}
		return false;
	}
	
	public Set<Answer> getAnswerToQuestion(Connection con, int id) throws SQLException {
		
		Set<Answer> rv = new Set<>();
		
		try (Statement stmt = con.createStatement()){
			try (ResultSet rs = stmt.executeQuery(QUERY_ANSWERS + " =" + id)){
				Answer tmp;
				while (rs.next()) {
					tmp = new Answer(rs.getBoolean("isCorrect"), rs.getString("AnswerText"));
					rv.add(tmp);
				}
			}
		}		
		return rv;
	}
	
}
