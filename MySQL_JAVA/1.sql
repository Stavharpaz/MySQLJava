use testdb;

-- Creating tables

create table Questions
( QuestionID int not null,
QuestionText varchar(100) not null, 
primary key (QuestionID) ) 
engine = InnoDB;

create table OpenQuestions 
( OpenQuestionID int not null,
primary key (OpenQuestionID),
foreign key (OpenQuestionID) references questions (questionid))
engine = InnoDB;

create table AmericanQuestions 
( AmericanQuestionID int not null,
primary key (AmericanQuestionID),
foreign key (AmericanQuestionID) references questions (questionid))
engine = InnoDB;

create table Answers
( AnswerID int not null auto_increment,
AnswerText varchar(100) not null,
isCorrect bool not null,
QuestionID int not null,
primary key (AnswerID, QuestionID),
foreign key (QuestionID) references questions (questionid) )
engine = InnoDB;

create table Tests 
( TestID int not null auto_increment,
primary key (TestID) )
engine = InnoDB;

create table Test_Question
( TestID int not null,
QuestionID int not null,
primary key (TestID, QuestionID),
foreign key (TestID) references test(testid),
foreign key (QuestionID) references questions(QuestionID) )
engine = InnoDB;

create table courseStaff
( StaffID int not null auto_increment,
firstName varchar(20) not null,
lastName varchar(20) not null,
staffRole varchar(20) not null,
staffUsername varchar(20) not null,
staffPassword varchar(20) not null,
primary key (staffID) )
engine = InnoDB;

 -- Inserting questions and answers
 
insert into questions values 
( 1, "What is the capital of Bolivia?" ),
( 2, "Is sushi tasty?" ),
( 3, "Who was the first prime minister of Israel?" ),
( 4, "How many continents are there?" ),
( 5, "When was Google founded?");

insert into openquestion values
(6);

insert into questions values 
( 6, "stav?");

delete from questions where questionid = 6;

delete from answers where questionid = 6;

insert into answers values 
(null, "Sucre.", TRUE, 1 ),
(null, "Yes.", TRUE, 2 ),
(null,"David Ben Gurion.", TRUE, 3 ),
(null,"7 continents- Asia, Africa, North America, South America, Antartica, Europe and Australia.", TRUE, 4 ),
(null,"1988", FALSE, 5),
(null,"1998", TRUE, 5),
(null,"1995", FALSE, 5),
(null,"2000", FALSE, 5);

insert into openquestion values
(1),(2),(3),(4);

insert into americanquestion values
(5);

-- Inserting staff

insert into courseStaff values 
(null, 'Ido', 'Hod', 'Practitioner', 'ido_hod', '81097'),
(null, 'Snir', 'Levi', 'Professor', 'kingoftheworld', 'takemyhandjack'),
(null, 'Stav', 'Harpaz', 'Senior Professor', 'queenoftheuniverse', 'takemyhand');




