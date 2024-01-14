-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sty 14, 2024 at 02:35 PM
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
  `club_name` varchar(255) DEFAULT NULL,
  `short_club_name` varchar(10) DEFAULT NULL,
  `foundation_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `address` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `ManagerID` int(11) DEFAULT NULL,
  `LeagueID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `clubs`
--

INSERT INTO `clubs` (`ClubID`, `club_name`, `short_club_name`, `foundation_date`, `address`, `contact`, `ManagerID`, `LeagueID`) VALUES
(1, 'Projekt Warszawa', 'PW', '2024-01-09 23:00:00', 'Sample Address', 'Contact Info', 2, 1),
(2, 'ASSECO Resovia', 'AR', '2024-01-06 23:00:00', 'Address in Poland', 'Contact Information', 3, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `fans`
--

CREATE TABLE `fans` (
  `FanID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `ClubID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fans`
--

INSERT INTO `fans` (`FanID`, `UserID`, `ClubID`) VALUES
(2, 4, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `finance`
--

CREATE TABLE `finance` (
  `FinanceID` int(11) NOT NULL,
  `ClubID` int(11) DEFAULT NULL,
  `Budget` double DEFAULT NULL,
  `Income` double DEFAULT NULL,
  `Expenses` double DEFAULT NULL,
  `date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `finance`
--

INSERT INTO `finance` (`FinanceID`, `ClubID`, `Budget`, `Income`, `Expenses`, `date`) VALUES
(1, 1, 9000, 650, 150, '2023-12-12 00:00:00'),
(2, 1, 1000, 500, 0, '2024-01-12 00:00:00');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `league`
--

CREATE TABLE `league` (
  `LeagueID` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `league`
--

INSERT INTO `league` (`LeagueID`, `name`, `description`) VALUES
(1, 'PlusLiga', 'Top-tier Polish volleyball league'),
(2, 'I Liga', 'Second-tier Polish volleyball league'),
(3, 'II Liga', 'Third-tier Polish volleyball league');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `matches`
--

CREATE TABLE `matches` (
  `MatchID` int(11) NOT NULL,
  `ClubID1` int(11) DEFAULT NULL,
  `ClubID2` int(11) DEFAULT NULL,
  `Date` timestamp NOT NULL DEFAULT current_timestamp(),
  `ResultID` int(11) DEFAULT NULL,
  `isPlayed` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `matches`
--

INSERT INTO `matches` (`MatchID`, `ClubID1`, `ClubID2`, `Date`, `ResultID`, `isPlayed`) VALUES
(1, 1, 2, '2024-01-13 11:37:56', 1, 1),
(2, 1, 2, '2024-01-08 23:00:00', 2, 1),
(3, 1, 2, '2024-01-07 23:00:00', 3, 1),
(4, 1, 2, '2024-01-16 12:08:30', NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `messages`
--

CREATE TABLE `messages` (
  `MessageID` int(11) NOT NULL,
  `MessageText` varchar(255) DEFAULT NULL,
  `SenderID` int(11) DEFAULT NULL,
  `ReceiverRoleID` int(11) DEFAULT NULL,
  `Date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`MessageID`, `MessageText`, `SenderID`, `ReceiverRoleID`, `Date`) VALUES
(1, 'Witajcie, drużyna! Mamy dzisiaj ważny mecz o godzinie 18:00. Bądźcie gotowi!', 2, 1, '2024-01-13 11:38:36'),
(2, 'Dupa1!', 2, 1, '2024-01-13 11:38:36'),
(3, 'Dupa2!', 2, 1, '2024-01-13 11:38:36'),
(4, 'Zmiana planów treningowych. Proszę wszystkich o dostosowanie się.', 2, 2, '2024-01-13 11:38:36'),
(5, 'Dziękujemy fanom za wsparcie podczas ostatniego meczu. Jesteście niesamowici!', 2, 3, '2024-01-13 11:38:36'),
(6, 'Testowa wiadomosc do menadzera', 3, 2, '2024-01-13 17:59:21'),
(8, 'test dupa', 2, 3, '2024-01-13 18:21:49'),
(9, 'kotki słodzkie kotki', 2, 1, '2024-01-13 18:23:34');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `players`
--

CREATE TABLE `players` (
  `PlayerID` int(11) NOT NULL,
  `position` int(11) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `statisticsID` int(11) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `ClubID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`PlayerID`, `position`, `number`, `statisticsID`, `UserID`, `ClubID`) VALUES
(1, 4, 7, NULL, 1, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `results`
--

CREATE TABLE `results` (
  `ResultID` int(11) NOT NULL,
  `MatchID` int(11) DEFAULT NULL,
  `result_home` int(10) DEFAULT NULL,
  `result_guest` int(10) DEFAULT NULL,
  `WinnerClubID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `results`
--

INSERT INTO `results` (`ResultID`, `MatchID`, `result_home`, `result_guest`, `WinnerClubID`) VALUES
(1, 1, 3, 1, 1),
(2, 2, 0, 3, 2),
(3, 3, 3, 2, 1);

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
(2, 'MANAGER'),
(3, 'FAN');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `statistics`
--

CREATE TABLE `statistics` (
  `StatisticsID` int(11) NOT NULL,
  `attack` double DEFAULT NULL,
  `blocks` double DEFAULT NULL,
  `serves` double DEFAULT NULL,
  `MatchID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tickets`
--

CREATE TABLE `tickets` (
  `TicketID` int(11) NOT NULL,
  `MatchID` int(11) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `Price` double DEFAULT NULL,
  `IsPurchased` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tickets`
--

INSERT INTO `tickets` (`TicketID`, `MatchID`, `UserID`, `Price`, `IsPurchased`) VALUES
(1, 1, 4, 25, 1),
(2, 4, 4, 50, 1),
(3, 4, 4, 50, 1);

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
  `birth_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `email` varchar(255) DEFAULT NULL,
  `roleID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `username`, `password`, `name`, `surname`, `birth_date`, `email`, `roleID`) VALUES
(1, 'test', 'test', 'Jan', 'Czajkowski', '1989-12-31 23:00:00', 'test@example.com', 1),
(2, 'test2', 'test2', 'Daniel', 'Czajkowski', '1989-12-31 23:00:00', 'test@example.com', 2),
(3, 'test5', 'test5', 'test', 'test', '2024-01-13 11:37:55', 'test', 2),
(4, 'test3', 'test3', 'Grzegorz', 'Czajkowski', '1989-12-31 23:00:00', 'test@example.com', 3),
(5, '1', '1', 'jakis', 'jakis', '2024-01-13 18:44:48', 'jaksi', 3);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `clubs`
--
ALTER TABLE `clubs`
  ADD PRIMARY KEY (`ClubID`),
  ADD KEY `idx_club_name` (`club_name`),
  ADD KEY `LeagueID` (`LeagueID`),
  ADD KEY `ManagerID` (`ManagerID`);

--
-- Indeksy dla tabeli `fans`
--
ALTER TABLE `fans`
  ADD PRIMARY KEY (`FanID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `ClubID` (`ClubID`);

--
-- Indeksy dla tabeli `finance`
--
ALTER TABLE `finance`
  ADD PRIMARY KEY (`FinanceID`),
  ADD KEY `idx_ClubID` (`ClubID`);

--
-- Indeksy dla tabeli `league`
--
ALTER TABLE `league`
  ADD PRIMARY KEY (`LeagueID`);

--
-- Indeksy dla tabeli `matches`
--
ALTER TABLE `matches`
  ADD PRIMARY KEY (`MatchID`),
  ADD KEY `idx_ClubID1` (`ClubID1`),
  ADD KEY `idx_ClubID2` (`ClubID2`),
  ADD KEY `idx_Date` (`Date`);

--
-- Indeksy dla tabeli `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`MessageID`),
  ADD KEY `SenderID` (`SenderID`),
  ADD KEY `ReceiverRoleID` (`ReceiverRoleID`);

--
-- Indeksy dla tabeli `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`PlayerID`),
  ADD KEY `idx_UserID` (`UserID`),
  ADD KEY `idx_ClubID` (`ClubID`);

--
-- Indeksy dla tabeli `results`
--
ALTER TABLE `results`
  ADD PRIMARY KEY (`ResultID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `WinnerClubID` (`WinnerClubID`);

--
-- Indeksy dla tabeli `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`RoleID`);

--
-- Indeksy dla tabeli `statistics`
--
ALTER TABLE `statistics`
  ADD PRIMARY KEY (`StatisticsID`),
  ADD KEY `idx_MatchID` (`MatchID`);

--
-- Indeksy dla tabeli `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`TicketID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserID`),
  ADD KEY `idx_username` (`username`),
  ADD KEY `idx_roleID` (`roleID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `clubs`
--
ALTER TABLE `clubs`
  MODIFY `ClubID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `fans`
--
ALTER TABLE `fans`
  MODIFY `FanID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `league`
--
ALTER TABLE `league`
  MODIFY `LeagueID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `matches`
--
ALTER TABLE `matches`
  MODIFY `MatchID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `MessageID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `players`
--
ALTER TABLE `players`
  MODIFY `PlayerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `results`
--
ALTER TABLE `results`
  MODIFY `ResultID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tickets`
--
ALTER TABLE `tickets`
  MODIFY `TicketID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `clubs`
--
ALTER TABLE `clubs`
  ADD CONSTRAINT `clubs_ibfk_1` FOREIGN KEY (`LeagueID`) REFERENCES `league` (`LeagueID`),
  ADD CONSTRAINT `clubs_ibfk_2` FOREIGN KEY (`ManagerID`) REFERENCES `users` (`UserID`);

--
-- Constraints for table `fans`
--
ALTER TABLE `fans`
  ADD CONSTRAINT `fans_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`),
  ADD CONSTRAINT `fans_ibfk_2` FOREIGN KEY (`ClubID`) REFERENCES `clubs` (`ClubID`);

--
-- Constraints for table `finance`
--
ALTER TABLE `finance`
  ADD CONSTRAINT `finance_ibfk_1` FOREIGN KEY (`ClubID`) REFERENCES `clubs` (`ClubID`);

--
-- Constraints for table `matches`
--
ALTER TABLE `matches`
  ADD CONSTRAINT `matches_ibfk_1` FOREIGN KEY (`ClubID1`) REFERENCES `clubs` (`ClubID`),
  ADD CONSTRAINT `matches_ibfk_2` FOREIGN KEY (`ClubID2`) REFERENCES `clubs` (`ClubID`);

--
-- Constraints for table `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`SenderID`) REFERENCES `clubs` (`ManagerID`),
  ADD CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`ReceiverRoleID`) REFERENCES `roles` (`RoleID`);

--
-- Constraints for table `players`
--
ALTER TABLE `players`
  ADD CONSTRAINT `players_ibfk_1` FOREIGN KEY (`ClubID`) REFERENCES `clubs` (`ClubID`),
  ADD CONSTRAINT `players_ibfk_2` FOREIGN KEY (`ClubID`) REFERENCES `clubs` (`ClubID`),
  ADD CONSTRAINT `players_ibfk_3` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);

--
-- Constraints for table `results`
--
ALTER TABLE `results`
  ADD CONSTRAINT `results_ibfk_1` FOREIGN KEY (`MatchID`) REFERENCES `matches` (`MatchID`),
  ADD CONSTRAINT `results_ibfk_2` FOREIGN KEY (`WinnerClubID`) REFERENCES `clubs` (`ClubID`);

--
-- Constraints for table `statistics`
--
ALTER TABLE `statistics`
  ADD CONSTRAINT `statistics_ibfk_1` FOREIGN KEY (`MatchID`) REFERENCES `matches` (`MatchID`);

--
-- Constraints for table `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`MatchID`) REFERENCES `matches` (`MatchID`),
  ADD CONSTRAINT `tickets_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`roleID`) REFERENCES `roles` (`RoleID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
