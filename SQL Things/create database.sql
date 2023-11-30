CREATE DATABASE ClubManagement;
USE  clubmanagement;
CREATE TABLE `Clubs` (
  `ClubID` integer PRIMARY KEY,
  `club_name` integer,
  `league` integer,
  `foundation_date` timestamp,
  `address` varchar(100),
  `contact` varchar(100),
  `ManagerID` integer,
  `SponsorID` integer,
  `LeagueID` integer
);

CREATE TABLE `League` (
  `LeagueID` integer PRIMARY KEY,
  `name` varchar(30),
  `description` varchar(255)
);

CREATE TABLE `Users` (
  `UserID` integer PRIMARY KEY,
  `username` varchar(70),
  `password` varchar(70),
  `name` varchar(50),
  `surname` varchar(50),
  `email` varchar(60),
  `roleID` integer
);

CREATE TABLE `Match` (
  `MatchID` integer PRIMARY KEY,
  `ClubID1` integer,
  `ClubID2` integer,
  `Date` timestamp,
  `Result` varchar(10)
);

CREATE TABLE `Notification` (
  `NotificationID` integer PRIMARY KEY,
  `ClubID` integer,
  `title` varchar(100),
  `description` varchar(255),
  `Date` timestamp
);

CREATE TABLE `Finance` (
  `FinanceID` integer PRIMARY KEY,
  `ClubID` integer,
  `Budget` double,
  `Income` double,
  `Expenses` double
);

CREATE TABLE `Roles` (
  `RoleID` integer PRIMARY KEY,
  `type` varchar(50)
);

CREATE TABLE `Players` (
  `playerID` integer PRIMARY KEY,
  `name` varchar(70),
  `surname` varchar(70),
  `birth_date` timestamp,
  `position` integer,
  `number` integer,
  `statisticsID` integer,
  `ClubID` integer
);

CREATE TABLE `Statistics` (
  `statisticsID` integer PRIMARY KEY,
  `attack` double,
  `blocks` double,
  `serves` double,
  `MatchID` integer
);

CREATE TABLE `Sponsors` (
  `SponsorID` integer PRIMARY KEY,
  `name` varchar(60),
  `budget` double
);

CREATE TABLE `Fans` (
  `FanID` integer PRIMARY KEY,
  `Name` varchar(70),
  `Lastname` varchar(70),
  `PhoneNumber` integer,
  `TicketID` integer
);

CREATE TABLE `Tickets` (
  `TicketID` integer PRIMARY KEY,
  `price` integer,
  `type` integer,
  `buyDate` timestamp,
  `MatchID` integer
);

CREATE TABLE `TicketMatch` (
  `MatchID` integer,
  `TicketID` integer,
  PRIMARY KEY (`MatchID`, `TicketID`)
);

ALTER TABLE `Players` ADD FOREIGN KEY (`ClubID`) REFERENCES `Clubs` (`ClubID`);
ALTER TABLE `Match` ADD FOREIGN KEY (`ClubID1`) REFERENCES `Clubs` (`ClubID`);
ALTER TABLE `Match` ADD FOREIGN KEY (`ClubID2`) REFERENCES `Clubs` (`ClubID`);
ALTER TABLE `Statistics` ADD INDEX (`MatchID`);
ALTER TABLE `Match` ADD FOREIGN KEY (`MatchID`) REFERENCES `Statistics` (`MatchID`);
ALTER TABLE `Clubs` ADD INDEX (`ManagerID`);
ALTER TABLE `Users` ADD FOREIGN KEY (`UserID`) REFERENCES `Clubs` (`ManagerID`);
ALTER TABLE `Clubs` ADD INDEX (`ClubID`);
ALTER TABLE `Notification` ADD FOREIGN KEY (`ClubID`) REFERENCES `Clubs` (`ClubID`);
ALTER TABLE `Finance` ADD INDEX (`ClubID`);
ALTER TABLE `Clubs` ADD FOREIGN KEY (`ClubID`) REFERENCES `Finance` (`ClubID`);
ALTER TABLE `Sponsors` ADD FOREIGN KEY (`SponsorID`) REFERENCES `Clubs` (`ClubID`);
ALTER TABLE `Roles` ADD INDEX (`RoleID`);
ALTER TABLE `Users` ADD FOREIGN KEY (`roleID`) REFERENCES `Roles` (`RoleID`);
ALTER TABLE `Fans`ADD INDEX (`TicketID`);
ALTER TABLE `Tickets` ADD FOREIGN KEY (`TicketID`) REFERENCES `Fans` (`TicketID`);
ALTER TABLE `TicketMatch` ADD INDEX (`TicketID`);
ALTER TABLE `Tickets` ADD FOREIGN KEY (`TicketID`) REFERENCES `TicketMatch` (`TicketID`);
ALTER TABLE `TicketMatch` ADD FOREIGN KEY (`MatchID`) REFERENCES `Match` (`MatchID`);
ALTER TABLE `League` ADD INDEX (`LeagueID`);
ALTER TABLE `Clubs` ADD FOREIGN KEY (`LeagueID`) REFERENCES `League` (`LeagueID`);
