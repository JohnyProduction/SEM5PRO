-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sty 03, 2024 at 07:06 PM
-- Wersja serwera: 10.4.28-MariaDB
-- Wersja PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `clubmanagement`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `clubs`
--

CREATE TABLE `clubs` (
  `ClubID` int(11) NOT NULL,
  `club_name` int(11) DEFAULT NULL,
  `league` int(11) DEFAULT NULL,
  `foundation_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `address` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `ManagerID` int(11) DEFAULT NULL,
  `SponsorID` int(11) DEFAULT NULL,
  `LeagueID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `fans`
--

CREATE TABLE `fans` (
  `FanID` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Lastname` varchar(255) DEFAULT NULL,
  `PhoneNumber` int(11) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `TicketID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `finance`
--

CREATE TABLE `finance` (
  `FinanceID` int(11) NOT NULL,
  `ClubID` int(11) DEFAULT NULL,
  `Budget` double DEFAULT NULL,
  `Income` double DEFAULT NULL,
  `Expenses` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `league`
--

CREATE TABLE `league` (
  `LeagueID` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `match`
--

CREATE TABLE `match` (
  `MatchID` int(11) NOT NULL,
  `ClubID1` int(11) DEFAULT NULL,
  `ClubID2` int(11) DEFAULT NULL,
  `Date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `Result` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `notification`
--

CREATE TABLE `notification` (
  `NotificationID` int(11) NOT NULL,
  `ClubID` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `Date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `players`
--

CREATE TABLE `players` (
  `playerID` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `birth_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `position` int(11) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `statisticsID` int(11) DEFAULT NULL,
  `ClubID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `roles`
--

CREATE TABLE `roles` (
  `RoleID` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`RoleID`, `type`) VALUES
(1, 'MEMBER'),
(2, 'MENAGER'),
(3, 'FAN');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `sponsors`
--

CREATE TABLE `sponsors` (
  `SponsorID` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `budget` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `statistics`
--

CREATE TABLE `statistics` (
  `statisticsID` int(11) NOT NULL,
  `attack` double DEFAULT NULL,
  `blocks` double DEFAULT NULL,
  `serves` double DEFAULT NULL,
  `MatchID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ticketmatch`
--

CREATE TABLE `ticketmatch` (
  `MatchID` int(11) NOT NULL,
  `TicketID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tickets`
--

CREATE TABLE `tickets` (
  `TicketID` int(11) NOT NULL,
  `price` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `buyDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `MatchID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `UserID` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `roleID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `username`, `password`, `name`, `surname`, `email`, `roleID`) VALUES
(2, 'test', 'test', 'Jan', 'Czajkowski', 'test\r\n', 1),
(3, 'test2', 'test2', 'Daniel', 'Czajkowski', 'test', 2),
(4, 'test3', 'test3', 'Sławomir', 'Czajkowski', 'test', 3);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `clubs`
--
ALTER TABLE `clubs`
  ADD PRIMARY KEY (`ClubID`),
  ADD KEY `FK_ManagerID` (`ManagerID`),
  ADD KEY `FK_SponsorID` (`SponsorID`),
  ADD KEY `FK_LeagueID` (`LeagueID`);

--
-- Indeksy dla tabeli `fans`
--
ALTER TABLE `fans`
  ADD PRIMARY KEY (`FanID`),
  ADD KEY `FK_TicketID_Fans` (`TicketID`);

--
-- Indeksy dla tabeli `finance`
--
ALTER TABLE `finance`
  ADD PRIMARY KEY (`FinanceID`),
  ADD KEY `FK_ClubID_Finance` (`ClubID`);

--
-- Indeksy dla tabeli `league`
--
ALTER TABLE `league`
  ADD PRIMARY KEY (`LeagueID`);

--
-- Indeksy dla tabeli `match`
--
ALTER TABLE `match`
  ADD PRIMARY KEY (`MatchID`),
  ADD KEY `FK_ClubID1` (`ClubID1`),
  ADD KEY `FK_ClubID2` (`ClubID2`);

--
-- Indeksy dla tabeli `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`NotificationID`),
  ADD KEY `FK_ClubID_Notification` (`ClubID`);

--
-- Indeksy dla tabeli `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`playerID`),
  ADD KEY `FK_ClubID_Players` (`ClubID`);

--
-- Indeksy dla tabeli `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`RoleID`);

--
-- Indeksy dla tabeli `sponsors`
--
ALTER TABLE `sponsors`
  ADD PRIMARY KEY (`SponsorID`);

--
-- Indeksy dla tabeli `statistics`
--
ALTER TABLE `statistics`
  ADD PRIMARY KEY (`statisticsID`),
  ADD KEY `FK_MatchID_Statistics` (`MatchID`);

--
-- Indeksy dla tabeli `ticketmatch`
--
ALTER TABLE `ticketmatch`
  ADD PRIMARY KEY (`MatchID`,`TicketID`),
  ADD KEY `FK_TicketID_TicketMatch` (`TicketID`);

--
-- Indeksy dla tabeli `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`TicketID`),
  ADD KEY `FK_MatchID_Tickets` (`MatchID`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserID`),
  ADD KEY `FK_RoleID` (`roleID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `clubs`
--
ALTER TABLE `clubs`
  MODIFY `ClubID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `fans`
--
ALTER TABLE `fans`
  MODIFY `FanID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `finance`
--
ALTER TABLE `finance`
  MODIFY `FinanceID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `match`
--
ALTER TABLE `match`
  MODIFY `MatchID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `NotificationID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sponsors`
--
ALTER TABLE `sponsors`
  MODIFY `SponsorID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `statistics`
--
ALTER TABLE `statistics`
  MODIFY `statisticsID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `clubs`
--
ALTER TABLE `clubs`
  ADD CONSTRAINT `FK_LeagueID` FOREIGN KEY (`LeagueID`) REFERENCES `league` (`LeagueID`),
  ADD CONSTRAINT `FK_ManagerID` FOREIGN KEY (`ManagerID`) REFERENCES `users` (`UserID`),
  ADD CONSTRAINT `FK_SponsorID` FOREIGN KEY (`SponsorID`) REFERENCES `sponsors` (`SponsorID`);

--
-- Constraints for table `fans`
--
ALTER TABLE `fans`
  ADD CONSTRAINT `FK_TicketID_Fans` FOREIGN KEY (`TicketID`) REFERENCES `tickets` (`TicketID`);

--
-- Constraints for table `finance`
--
ALTER TABLE `finance`
  ADD CONSTRAINT `FK_ClubID_Finance` FOREIGN KEY (`ClubID`) REFERENCES `clubs` (`ClubID`);

--
-- Constraints for table `match`
--
ALTER TABLE `match`
  ADD CONSTRAINT `FK_ClubID1` FOREIGN KEY (`ClubID1`) REFERENCES `clubs` (`ClubID`),
  ADD CONSTRAINT `FK_ClubID2` FOREIGN KEY (`ClubID2`) REFERENCES `clubs` (`ClubID`);

--
-- Constraints for table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `FK_ClubID_Notification` FOREIGN KEY (`ClubID`) REFERENCES `clubs` (`ClubID`);

--
-- Constraints for table `players`
--
ALTER TABLE `players`
  ADD CONSTRAINT `FK_ClubID_Players` FOREIGN KEY (`ClubID`) REFERENCES `clubs` (`ClubID`);

--
-- Constraints for table `statistics`
--
ALTER TABLE `statistics`
  ADD CONSTRAINT `FK_MatchID_Statistics` FOREIGN KEY (`MatchID`) REFERENCES `match` (`MatchID`);

--
-- Constraints for table `ticketmatch`
--
ALTER TABLE `ticketmatch`
  ADD CONSTRAINT `FK_MatchID_TicketMatch` FOREIGN KEY (`MatchID`) REFERENCES `match` (`MatchID`),
  ADD CONSTRAINT `FK_TicketID_TicketMatch` FOREIGN KEY (`TicketID`) REFERENCES `tickets` (`TicketID`);

--
-- Constraints for table `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `FK_MatchID_Tickets` FOREIGN KEY (`MatchID`) REFERENCES `match` (`MatchID`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FK_RoleID` FOREIGN KEY (`roleID`) REFERENCES `roles` (`RoleID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
