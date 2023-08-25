package Queries;

import id208322966_id207082280.*;

import java.sql.*;
//import java.util.*;
import java.util.Vector;

public class Update {

	// Updates	
	private static final String UPDATE_ADD_ANSWERS = "insert into answers values (null, ";
	private static final String UPDATE_ADD_OPENQUE = "insert into openQuestions values (?)";
	private static final String UPDATE_ADD_AMEQUE = "insert into americanQuestions values (?)";	
	private static final String INSERT_COMMAND_QUESTION = "insert into questions values (?,?);";	
	private static final String UPDATE_QUESTION_TEXT = "update questions set questiontext = (?) where questionid = (?);";
	private static final String UPDATE_OPEN_ANSWER_TEXT = "update answers set answertext = (?) where questionid = (?);";
	private static final String GET_AMERICAN_ANSWER_ROW = "select * from (select * from answers where questionid = (?)) answers limit ?, 1;";
	private static final String UPDATE_AMERICAN_ANSWER_TEXT = "update answers set answertext = (?) where answerid = (?);";
	private static final String UPDATE_DELETE_AMERICAN_ANSWER = "delete from answers where answerid = (?);";
	private static final String UPDATE_ADD_TEST = "insert into tests values (null);";
	private static final String GET_TEST_ID = "select max(testid) as testid from tests group by testid;"; 
	private static final String UPDATE_ADD_TEST_QUESTION = "insert into test_question values (?,?);";
		
	public void updateAddTest(Connection con, QuestionStock test) throws SQLException{
		int testID = 0;
		try (Statement stmt = con.createStatement()){
			stmt.executeUpdate(UPDATE_ADD_TEST);
		}
		try(Statement stmt = con.createStatement()){
			try(ResultSet rs = stmt.executeQuery(GET_TEST_ID)){
				while(rs.next())
					testID = rs.getInt("testid");
			}
		}
		
		try (PreparedStatement stmt = con.prepareStatement(UPDATE_ADD_TEST_QUESTION)) {
			Vector<Question> questions = test.getQuestions();
			stmt.setInt(1, testID);
			for (int i = 0; i < test.getNumOfQue(); i++) {				
				stmt.setInt(2, questions.get(i).getId());
				stmt.execute();
			}
		}		
	}
		
	public int getAnswerID(Connection con, int questionID, int ansNum) throws SQLException{
		int answerID = 0;
		try(PreparedStatement stmt = con.prepareStatement(GET_AMERICAN_ANSWER_ROW)){
			stmt.setInt(1, questionID);
			stmt.setInt(2, ansNum - 1);
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next())
					answerID = rs.getInt("answerid");
			}
		}
		return answerID;
	}
	
	public void deleteAnswer(Connection con, int questionID, int ansNum) throws SQLException{
		
		int answerID = getAnswerID(con, questionID, ansNum);
		try (PreparedStatement stmt = con.prepareStatement(UPDATE_DELETE_AMERICAN_ANSWER)){	
			stmt.setInt(1, answerID);
			stmt.executeUpdate();
		}			
	}
	
	public void updateOpenAnswerText(Connection con, String text, int questionID) throws SQLException{
		try (PreparedStatement stmt = con.prepareStatement(UPDATE_OPEN_ANSWER_TEXT)){	
			stmt.setString(1, text);
			stmt.setInt(2, questionID);
			stmt.executeUpdate();
		}	
	}
	
	public void updateAmericanAnswerText(Connection con, String text, int questionID, int ansNum) throws SQLException{
		
		int answerID = getAnswerID(con, questionID, ansNum);
		try (PreparedStatement stmt = con.prepareStatement(UPDATE_AMERICAN_ANSWER_TEXT)){	
			stmt.setString(1, text);
			stmt.setInt(2, answerID);
			stmt.executeUpdate();
		}	
	}
	
	public void updateExistingQuestionText(Connection con, int id, String text) throws SQLException{
		
		try (PreparedStatement stmt = con.prepareStatement(UPDATE_QUESTION_TEXT)){	
			stmt.setString(1, text);
			stmt.setInt(2, id);
			stmt.executeUpdate();
		}		
	}
	
	public void updateAddQuestion(Connection con, Question que, int type) throws SQLException {
		
		try (PreparedStatement stmt = con.prepareStatement(INSERT_COMMAND_QUESTION)){	
			stmt.setInt	(1, que.getId());
			stmt.setString(2, que.getText());
			stmt.executeUpdate();
		}
		
		Set<Answer> ans = new Set<>();
		if(type == 1) {
			try (PreparedStatement stmt = con.prepareStatement(UPDATE_ADD_OPENQUE)) {
			stmt.setInt(1, que.getId());
			stmt.executeUpdate();
			ans.add(((OpenQuestion)que).getAnswer());		
			}
		} else {
			try (PreparedStatement stmt = con.prepareStatement(UPDATE_ADD_AMEQUE)) {
			stmt.setInt(1, que.getId());
			stmt.executeUpdate();
			ans = ((AmericanQuestion)que).getAnswers();	
			}
		}	
		updateAddAnswers(con, ans, que.getId());
	}
		
	public void updateAddAnswers(Connection con, Set<Answer> answers, int questionID) throws SQLException {
		
		try (Statement stmt = con.createStatement()){	
			
			for(int i = 0; i < answers.size(); i++) {
				String s = UPDATE_ADD_ANSWERS + "'" + answers.get(i).getText() + "', " + answers.get(i).isCorrect() + ", " + questionID + ");";
				stmt.executeUpdate(s);
			}
		}		
	}
		
}
