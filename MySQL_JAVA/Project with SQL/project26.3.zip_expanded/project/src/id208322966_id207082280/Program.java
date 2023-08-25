//Snir Levi- 208322966
//Stav Harpaz- 207082280

//			 Different Subjects Support
//to support a number of subject we can create an array of QuestionStock
//and manage each cell as a different subject
package id208322966_id207082280;

import java.sql.*;
import java.util.*;
import Queries.*;



public class Program {
	static Scanner s = new Scanner(System.in);

	public static String notAvailablePractitioner = "";
	public static String notAvailableProfessor = "";
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "81y2G$sbk");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		
		Program p = new Program();
		
		
		
		try(Connection con = p.getConnection()){
			
			try {
				
				
				Staff member = getUser(con);
				
				boolean b = true;

				System.out.println("Please select an option:\n" 
				+ "1 - Show all questions\n"
				+ "2 - Add question and answer               " + notAvailablePractitioner + "\n"
				+ "3 - Update existing question text         " + notAvailablePractitioner + "\n"
				+ "4 - Update existing answer text           " + notAvailablePractitioner + "\n"
				+ "5 - Delete an answer (American Questions) " + notAvailablePractitioner + "\n"
				+ "6 - Create test                           " + notAvailableProfessor    + "\n"
				+ "7 - Automatic test generator              " + notAvailableProfessor    + "\n"
				+ "8 - Print Test\n"
				+ "9 – Log out");
				System.out.println("-----------------------------------------------------------------");

				int sel = s.nextInt();
				if (sel == 9)
					b = false;
				
				while (b) {
					System.out.println("-----------------------------------------------------------------");
					switch (sel) {

					case 1: // print question stock										
						showQuestionStock(con);
						break;

					case 2: // add question and answer
						if(member.getRole().equals("Practitioner")) {
							System.out.println("We told you this option is not Available..........");
						} else
							addQuestionAndAnswer(con);
						break;

					case 3: // update existing question text	
						if(member.getRole().equals("Practitioner")) {
							System.out.println("We told you this option is not Available..........");
						} else
						updateExistingQuestionText(con);
						break;

					case 4: // update existing answer text
						if(member.getRole().equals("Practitioner")) {
							System.out.println("We told you this option is not Available..........");
						} else
						updateExistingAnswerText(con);		
						break;

					case 5: // delete an answer		
						if(member.getRole().equals("Practitioner")) {
							System.out.println("We told you this option is not Available..........");
						} else
						deleteAnAnswer(con);				
						break;

					case 6: // create test	
						if(member.getRole().equals("Senior Professor")) {
							createTest(con);			
						} else
							System.out.println("We told you this option is not Available..........");		
						break;

					case 7: // automatic test generator
						if(member.getRole().equals("Senior Professor")) {
							automaticTestGenerator(con);			
						} else
							System.out.println("We told you this option is not Available..........");							
						break;

					case 8: // print existing tests
						printTest(con);
						break;
						
					}// switch

					System.out.println("-----------------------------------------------------------------");
					System.out.println("Please select an option:\n" 
							+ "1 - Show all questions\n"
							+ "2 - Add question and answer               " + notAvailablePractitioner + "\n"
							+ "3 - Update existing question text         " + notAvailablePractitioner + "\n"
							+ "4 - Update existing answer text           " + notAvailablePractitioner + "\n"
							+ "5 - Delete an answer (American Questions) " + notAvailablePractitioner + "\n"
							+ "6 - Create test                           " + notAvailableProfessor    + "\n"
							+ "7 - Automatic test generator              " + notAvailableProfessor    + "\n"
							+ "8 - Print Test\n"
							+ "9 – Log out");

					sel = s.nextInt();
					if (sel == 9)
						b = false;
				} // while

			} catch (AddAnswersException e) {
				System.out.println(e.getMessage());
			} catch (InputMismatchException e) {
				System.out.println("Input mismatch exception, please enter correct type!");
			} catch (SQLException e) {
				System.out.println(e.getLocalizedMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			System.out.println("Goodbye!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		s.close();
		
	} // main

	
	public static Staff getUser(Connection con) throws SQLException {
		Staff member;
		do {
		System.out.println("Enter username: ");
		String username = s.next();
		System.out.println("Enter password: ");
		String password = s.next();
		
		Query q = new Query();
		
		member = q.getUser(con, username, password);
		} while (member == null);
		
		if(member.getRole().equals("Practitioner")) {
			notAvailablePractitioner = " Not Available";
			notAvailableProfessor = " Not Available";
		} else if (member.getRole().equals("Professor")) {
			notAvailableProfessor = " Not Available";
		}		
		
		return member;
	}
	
	
	public static void printTest(Connection con) throws SQLException, AddAnswersException {
		
		Query q = new Query();
		Vector<Integer> testsIds = q.getTestIds(con);
				
		int choice = 0;
		do {
			System.out.println("Please choose a test ID:");
			System.out.println(testsIds);
			choice = s.nextInt();
		} while (!testsIds.contains(choice));
		
		QuestionStock test = q.getTest(con, choice);
		System.out.println("\t-------TEST-------\n");
		System.out.println(test);
	}
	
	public static QuestionStock getQuestionStock(Connection con) throws Exception {
		Query q = new Query();		
		QuestionStock qs = q.getQuestionStock(con);
		return qs;
	}
	
	public static void showQuestionStock(Connection con) throws Exception {
		QuestionStock qs = getQuestionStock(con);
		System.out.println(qs.toString());
	}
		
	public static void automaticTestGenerator(Connection con) throws AddAnswersException, InputMismatchException, SQLException, Exception {
		QuestionStock qs = getQuestionStock(con);
		System.out.println("Enter amount of questions: (<=" + qs.getNumOfQue() + ")");
		int queAmo = s.nextInt();
		while (queAmo > qs.getNumOfQue() || queAmo <= 0) {
			System.out.println(
					"Cannot create test with " + queAmo + " questions, please enter amount of questions again: (<=" + qs.getNumOfQue() + ")");
			queAmo = s.nextInt();
		}

		QuestionStock test = new QuestionStock();
		if (queAmo != 0) {
			if(!qs.canYouGenerateTestFromQuestions(queAmo)) {
				System.out.println("Cannot make legal test with " + queAmo + " questions, there are not enough wrong answers or not enough answers\nfor the american questions.");
				return;
			}
			int numOfQueAdded = 0;
			while (numOfQueAdded < queAmo) {
				int rand = (int) (1 + Math.random() * qs.getNumOfQue());
				if (test.getIndexByID(rand) == -1) { // if the question does not exists in test
					if (qs.isOpenQue(qs.getQuestions().get(rand - 1))) {
						test.addOpenQuestion(qs.getQuestions().get(rand - 1));
						numOfQueAdded++;
					} else if(qs.canYouGenerateLegalQuestion(qs.getQuestions().get(rand - 1))) {
						test.addAmericanQuestion(qs.getQuestions().get(rand - 1));
						int numOfAns = (test.getNumOfAnsAmericanQuestion(test.getQuestions().get(test.getIndexByID(rand))));
						if (numOfAns > 4) {
							Vector<Integer> vec = new Vector<>();	
							
							boolean correctAnsFlag = false;
							while (vec.size() < 4) {
								Integer randAns = (int) (1 + Math.random() * numOfAns);
								if (!vec.contains(randAns)) {
									if (!test.isAnswerCorrect(test.getQuestions().get(test.getIndexByID(rand)), randAns)) {
										vec.add(randAns);										
									} else if (!correctAnsFlag) {
										vec.add(randAns);										
										correctAnsFlag = true;
									}
								}
							}
							test.keepAnswers(rand, vec);
						}
						numOfQueAdded++;
					}				
				}
			}
		} 
		
		//test.sortQuestionsAlph();
		test.sortByAnsLength();
		Update up = new Update();
		up.updateAddTest(con, test);		
		System.out.println("\nYour test is ready: ");
		System.out.println(test);
	}

	public static void createTest(Connection con) throws AddAnswersException, InputMismatchException, SQLException, Exception {
		QuestionStock qs = getQuestionStock(con);
		System.out.println("These are the questions in stock: ");
		System.out.println(qs);

		System.out.println("Enter amount of questions: (<=" + qs.getNumOfQue() + ")");
		int queAmo = s.nextInt();
		while (queAmo > qs.getNumOfQue()) {
			System.out.println(
					"There are not enough questions in stock, enter amount of questions: (<=" + qs.getNumOfQue() + ")");
			queAmo = s.nextInt();
		}
		QuestionStock test = new QuestionStock();

		if (queAmo != qs.getNumOfQue()) {

			for (int i = 0; i < queAmo; i++) {
				System.out.println("Enter question " + (i + 1) + " ID: ");
				int id = s.nextInt();
				while (qs.getIndexByID(id) == -1) {
					System.out.println("This question id does not exist, enter another id:");
					id = s.nextInt();
				}
				if (qs.isOpenQue(qs.getQuestions().get(id - 1))) {
					test.addOpenQuestion(qs.getQuestions().get(id - 1));
				} else {
					test.addAmericanQuestion(qs.getQuestions().get(id - 1));
					System.out.println(qs.getQuestions().get(id - 1));
					int numOfAns = qs.getNumOfAnsAmericanQuestion(qs.getQuestions().get(id - 1));
					System.out.println("How many answers do you want to keep? at least one, no more than " + numOfAns);
					int size = s.nextInt();
					while (size < 1 || size > numOfAns) {
						System.out.println(
								"How many answers do you want to keep? at least one, no more than " + numOfAns);
						size = s.nextInt();
					}
					if (size != numOfAns) {
						Vector<Integer> ansToKeep = new Vector<>();		
						for (int j = 0; j < size; j++) {
							System.out.println("Enter answer number: between 1-" + numOfAns);
							int selection = s.nextInt();
							while (selection < 1 || selection > numOfAns) {
								System.out.println("Enter answer number: between 1-" + numOfAns);
								selection = s.nextInt();
							}
							ansToKeep.add(selection);
						}

						test.keepAnswers(id, ansToKeep);
					}
				}
			}
		} else {
			test = qs;
		}
		
		test.sortByAnsLength();
		Update up = new Update();
		up.updateAddTest(con, test);
		System.out.println("\nYour test is ready: ");
		System.out.println(test);
	}

	public static void deleteAnAnswer(Connection con) throws AddAnswersException, InputMismatchException, SQLException, Exception {
		QuestionStock qs = getQuestionStock(con);
		System.out.println("Enter the answer's question id: ");
		int id = s.nextInt();
		if (qs.isAmericanQue(qs.getQuestions().get(id - 1))) {
			System.out.println("enter an answer number to delete: ");
			int numAns = s.nextInt();
			if (!qs.deleteAnswer(id, numAns))
				System.out.println("Operation unsuccessful: answer number " + numAns + " does not exist.");
			else {
				Update up = new Update();
				up.deleteAnswer(con, id, numAns);
				System.out.println("Success!");
			}				
		} else
			System.out.println("This is an open question.");

	}

	public static void updateExistingAnswerText(Connection con) throws InputMismatchException, SQLException, Exception {
		QuestionStock qs = getQuestionStock(con);
		System.out.println("Enter the answer's question id: ");
		int id = s.nextInt();
		s.nextLine();
		System.out.println("Enter new answer text: ");
		String ansText = s.nextLine();
		if (qs.isAmericanQue(qs.getQuestions().get(id - 1))) {
			System.out.println("Enter the answer's number: ");
			int ansNum = s.nextInt();
			if (!qs.setAmericanAnswer(ansText, id, ansNum))
				System.out
						.println("Operation unsuccessful: either the question ID does not exist or that answer number "
								+ ansNum + " does not exist.");
			else {
				Update up = new Update();
				up.updateAmericanAnswerText(con, ansText, id, ansNum);
				System.out.println("Success!");
			}
				
		} else {
			if (!qs.setOpenAnswer(ansText, id))
				System.out.println("Operation unsuccessful: the question ID does not exist.");
			else {
				Update up = new Update();
				up.updateOpenAnswerText(con, ansText, id);
				System.out.println("Success!");
			}
				
		}
	}

	public static void updateExistingQuestionText(Connection con) throws InputMismatchException, SQLException, Exception {
		QuestionStock qs = getQuestionStock(con);	
		System.out.println("Enter the question's ID:");
		int id = s.nextInt();
		s.nextLine();
		System.out.println("Enter new question text: ");
		String queText = s.nextLine();

		if (!qs.setQuestion(queText, id)) {
			System.out.println(
					"Operation unsuccessful: either the question ID does not exist or that this question already exists.\n");
		} else {
			Update up = new Update();
			up.updateExistingQuestionText(con, id, queText);
			System.out.println("Success!\n");
		}
	}
	
	public static void addQuestionAndAnswer(Connection con) throws Exception {
		QuestionStock qs = getQuestionStock(con);	
		System.out.println("Enter the question (text):");
		s.nextLine();
		String queText = s.nextLine();
		System.out.println("Which type of question? 1- Open Question, 2- American Question");
		int type = s.nextInt();
		while (type != 1 && type != 2) {
			System.out.println("Invalid input!\nWhich type of question? 1- Open Question, 2- American Question");
			type = s.nextInt();
		}
		if(qs.isExists(queText, type)) {
			System.out.println("Question already exists.");
			return;
		}
		
		Update up = new Update();
		if (type == 1) {			
			System.out.println("Enter answer (text):");
			Answer ans = new Answer(true, s.next());
			OpenQuestion opQue = new OpenQuestion(queText, ans);
			//qs.addOpenQuestion(opQue);
			up.updateAddQuestion(con, opQue, type);			
			s.nextLine();
		} else {
			System.out.println("How many answers? (maximum 10)");
			int numOfans = s.nextInt();
			while (numOfans < 1 || numOfans > 10) {
				System.out.println("How many answers? (maximum 10)");
				numOfans = s.nextInt();
			}
			//Vector<String> ansText = new Vector<>();		//String[] ansText = new String[numOfans];
			//Vector<Boolean> isCorrect = new Vector<>();		//boolean[] isCorrect = new boolean[numOfans];
			
			Set<Answer> answers = new Set<>();
			
			for (int i = 0; i < numOfans; i++) {
				System.out.println("Enter answer (text):");
				s.nextLine();							
				String ansTxt = s.nextLine();				
				System.out.println("Is this answer correct? [true / false]");
				Boolean isCorrect = s.nextBoolean();
				
				Answer ans = new Answer(isCorrect, ansTxt);
				answers.add(ans);
			}
			
			AmericanQuestion ameQue = new AmericanQuestion(queText);
			ameQue.addAnswers(answers);			
			//qs.addAmericanQuestion(ameQue);
			up.updateAddQuestion(con, ameQue, type);
		}
	}

}// Class
