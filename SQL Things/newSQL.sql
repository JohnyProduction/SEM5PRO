-- Create the database
CREATE DATABASE IF NOT EXISTS clubmanagement;

-- Use the database
USE clubmanagement;

-- Create tables without foreign keys
CREATE TABLE IF NOT EXISTS clubs (
  ClubID int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  club_name varchar(255) DEFAULT NULL,
  short_club_name  varchar(10) DEFAULT null,
  foundation_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  address varchar(255) DEFAULT NULL,
  contact varchar(255) DEFAULT NULL,
  ManagerID int(11) DEFAULT NULL,
  LeagueID int(11) DEFAULT NULL,
  INDEX idx_club_name (club_name)
);

CREATE TABLE IF NOT EXISTS finance (
  FinanceID int(11) PRIMARY KEY NOT NULL,
  ClubID int(11) DEFAULT NULL,
  Budget double DEFAULT NULL,
  Income double DEFAULT NULL,
  Expenses double DEFAULT NULL,
  INDEX idx_ClubID (ClubID)
);

CREATE TABLE IF NOT EXISTS league (
  LeagueID int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS matches (
  MatchID int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  ClubID1 int(11) DEFAULT NULL,
  ClubID2 int(11) DEFAULT NULL,
  Date timestamp DEFAULT CURRENT_TIMESTAMP,
  ResultID int(11) DEFAULT NULL,
  INDEX idx_ClubID1 (ClubID1),
  INDEX idx_ClubID2 (ClubID2),
  INDEX idx_Date (Date)
);
CREATE TABLE Results (
  ResultID int(11) primary key not null auto_increment,
  MatchID int(11) default null,
  result_home int(10) default null,
  result_guest int(10) default null
);
CREATE TABLE IF NOT EXISTS notification (
  NotificationID int(11) PRIMARY KEY NOT NULL,
  ClubID int(11) DEFAULT NULL,
  title varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  Date timestamp DEFAULT CURRENT_TIMESTAMP,
  IsVisible bool,
  SenderID int(11),
  INDEX idx_ClubID (ClubID),
  INDEX idx_SenderID (SenderID)
);

CREATE TABLE IF NOT EXISTS players (
  PlayerID int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  position int(11) DEFAULT NULL,
  number int(11) DEFAULT NULL,
  statisticsID int(11) DEFAULT NULL,
  UserID int(11) DEFAULT NULL,
  ClubID int(11) DEFAULT NULL,
  INDEX idx_UserID (UserID),
  INDEX idx_ClubID (ClubID),
  FOREIGN KEY (ClubID) REFERENCES clubs (ClubID)
);

CREATE TABLE IF NOT EXISTS roles (
  RoleID int(11) PRIMARY KEY NOT NULL,
  type varchar(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS statistics (
  StatisticsID int(11) PRIMARY KEY NOT NULL,
  attack double DEFAULT NULL,
  blocks double DEFAULT NULL,
  serves double DEFAULT NULL,
  MatchID int(11) DEFAULT NULL,
  INDEX idx_MatchID (MatchID)
);

CREATE TABLE IF NOT EXISTS users (
  UserID int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  username varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  surname varchar(255) DEFAULT NULL,
  birth_date timestamp DEFAULT CURRENT_TIMESTAMP,
  email varchar(255) DEFAULT NULL,
  roleID int(11) DEFAULT NULL,
  INDEX idx_username (username),
  INDEX idx_roleID (roleID),
  FOREIGN KEY (roleID) REFERENCES roles (RoleID)
);

-- Add foreign key constraints
ALTER TABLE finance ADD FOREIGN KEY (ClubID) REFERENCES clubs (ClubID);
ALTER TABLE notification ADD FOREIGN KEY (ClubID) REFERENCES clubs (ClubID);
ALTER TABLE players ADD FOREIGN KEY (ClubID) REFERENCES clubs (ClubID);
ALTER TABLE statistics ADD FOREIGN KEY (MatchID) REFERENCES matches (MatchID);
ALTER TABLE clubs add foreign key (LeagueID) references league(LeagueID);
ALTER TABLE clubs add FOREIGN KEY (ManagerID) references users(UserID);
ALTER table matches add foreign key (ClubID1) references clubs(ClubID);
ALTER table matches add foreign key (ClubID2) references clubs(ClubID);
ALTER table Results add foreign key (MatchID) references matches(MatchID);



-- Insert roles into the roles table
INSERT INTO clubmanagement.roles (RoleID, type) VALUES (1, 'MEMBER');
INSERT INTO clubmanagement.roles (RoleID, type) VALUES (2, 'MANAGER');
INSERT INTO clubmanagement.roles (RoleID, type) VALUES (3, 'FAN');


INSERT INTO clubmanagement.users (username, password, name, surname, birth_date, email, roleID)
VALUES ('test', 'test', 'Jan', 'Czajkowski', '1990-01-01', 'test@example.com', 1);
-- Insert user with role 1 (MEMBER) and username 'test'
INSERT INTO clubmanagement.users (username, password, name, surname, birth_date, email, roleID)
VALUES ('test2', 'test2', 'Daniel', 'Czajkowski', '1990-01-01', 'test@example.com', 2);


-- Insert volleyball leagues from Poland
INSERT INTO clubmanagement.league (name, description) VALUES ('PlusLiga', 'Top-tier Polish volleyball league');
INSERT INTO clubmanagement.league (name, description) VALUES ('I Liga', 'Second-tier Polish volleyball league');
INSERT INTO clubmanagement.league (name, description) VALUES ('II Liga', 'Third-tier Polish volleyball league');

-- Insert a team from the Polish PlusLiga volleyball league
INSERT INTO clubmanagement.clubs (club_name, short_club_name,foundation_date, address, contact, ManagerID, LeagueID)
VALUES ('Projekt Warszawa', 'PW','2024-01-10', 'Sample Address', 'Contact Info', 2, 1);
INSERT INTO clubs (club_name, short_club_name, foundation_date, address, contact, ManagerID, LeagueID)
VALUES ('ASSECO Resovia', 'AR', '2024-01-07', 'Address in Poland', 'Contact Information', 2, 1);

INSERT INTO clubmanagement.players (position, number, UserID, ClubID, statisticsID)
VALUES (4,7, 1, 1, NULL);

INSERT INTO matches (ClubID1, ClubID2, Date, ResultID) value ('1','2',CURRENT_TIMESTAMP,1);
INSERT INTO Results (MatchID, result_home, result_guest) values (1,3,1);
