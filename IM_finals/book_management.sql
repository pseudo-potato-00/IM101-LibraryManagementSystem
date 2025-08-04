-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 02, 2024 at 06:14 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `book_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `name`, `author`, `price`, `quantity`) VALUES
(2, 'Java', 'James Gosling', 50, 90),
(3, 'To Kill a Mockingbird', 'Harper Lee', 12.99, 50),
(4, '1984', 'George Orwell', 10.99, 30),
(5, 'Pride and Prejudice	', 'Jane Austen	', 9.99, 50),
(6, 'The Great Gatsby	', 'F. Scott Fitzgerald	', 14.99, 40),
(7, 'Moby Dick	', 'Herman Melville	', 11.5, 20),
(8, 'War and Peace	', 'Leo Tolstoy	', 19.99, 15),
(9, 'The Catcher in the Rye	', 'J.D. Salinger	', 8.99, 60),
(10, 'The Hobbit	', 'J.R.R. Tolkien	', 15.99, 35),
(11, 'Harry Potter and the Sorcerer’s Stone	', 'J.K. Rowling	', 16.99, 100),
(12, 'The Hunger Games	', 'Suzanne Collins	', 13.5, 70),
(13, 'The Road	', 'Cormac McCarthy	', 10.99, 25),
(14, 'The Alchemist	', 'Paulo Coelho	', 9.99, 80),
(15, 'Crime and Punishment	', 'Fyodor Dostoevsky	', 14.99, 20),
(16, 'The Girl on the Train	', 'Paula Hawkins	', 11.99, 45),
(17, 'The Kite Runner	', 'Khaled Hosseini	', 12.5, 30),
(18, 'Little Women	', 'Louisa May Alcott	', 10.99, 50),
(19, 'Brave New World	', 'Aldous Huxley	', 9.5, 40),
(20, 'Life of Pi	', 'Yann Martel	', 14.99, 35),
(21, 'The Fault in Our Stars	', 'John Green	', 12.99, 60),
(22, 'The Book Thief	', 'Markus Zusak	', 13.99, 55),
(23, 'Anna Karenina	', 'Leo Tolstoy	', 17.99, 20),
(24, 'Fahrenheit 451	', 'Ray Bradbury	', 10.99, 45),
(25, 'Dune', 'Frank Herbert	', 15.5, 30),
(26, 'Dracula', 'Bram Stoker	', 8.99, 70),
(27, 'The Shining	', 'Stephen King	', 14.5, 40),
(28, 'A Game of Thrones	', 'George R.R. Martin	', 18.99, 25),
(29, 'Percy Jackson: The Lightning Thief	', 'Rick Riordan	', 12.5, 80),
(30, 'The Giver	', 'Lois Lowry	', 9.99, 50),
(31, 'Frankenstein	', 'Mary Shelley	', 8.5, 35),
(32, 'Jane Eyre	', 'Charlotte Brontë	', 12.99, 30),
(33, 'Python', 'Guido van Rossum', 12, 50);

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `member_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `membership_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`member_id`, `name`, `email`, `phone`, `membership_date`) VALUES
(2, 'Alex', 'alex@gou', '521-579-632', '2024-12-01'),
(3, 'John Smith	', 'john.smith@example.com', '555-123-4567', '2023-01-15'),
(4, 'Mary Johnson	', 'mary.johnson@example.com', '555-234-5678', '2023-02-10'),
(5, 'James Brown', 'james.brown@example.com', '218-564-987', '2024-08-04'),
(6, 'Patricia Taylor	', 'patricia.taylor@example.com	', '555-456-7890', '2023-04-25'),
(7, 'Robert Miller	', 'robert@miller', '5165-894-6511', '2023-05-05'),
(8, 'Jennifer Davis	', 'jennifer.davis@example.com	', '555-678-9012	', '2023-06-15'),
(9, 'Michael Wilson	', 'michael.wilson@example.com', '555-789-0123	', '2023-07-12'),
(10, 'Linda Moore', 'linda.moore@example.com', '555-890-1234', '2023-08-18'),
(11, 'William Anderson', 'william.anderson@example.com', '555-901-2345	', '2023-09-03'),
(12, 'Elizabeth Thomas	', 'elizabeth.thomas@example.com	', '555-012-3456	', '2023-10-01'),
(13, 'David Jackson', 'david.jackson@example.com', '555-123-4568', '2023-10-15'),
(14, 'Barbara White	', 'barbara.white@example.com', '555-123-4568	', '2023-10-15'),
(15, 'Barbara White	', 'barbara.white@example.com', '555-234-5679	', '2023-11-05'),
(16, 'Richard Harris	', 'richard.harris@example.com', '555-345-6780	', '2023-12-01'),
(17, 'Susan Martin	', 'susan.martin@example.com	', '555-456-7891	', '2024-01-10'),
(18, 'Joseph Thompson	', 'joseph.thompson@example.com	', '555-567-8902	', '2024-02-14'),
(19, 'Karen Garcia	', 'karen.garcia@example.com	', '555-678-9013	', '2024-03-20'),
(20, 'Thomas Martinez	', 'thomas.martinez@example.com	', '555-789-0124	', '2024-04-05'),
(21, 'Sarah Robinson	', 'sarah.robinson@example.com	', '555-890-1235	', '2024-05-15'),
(22, 'Charles Clark	', 'charles.clark@example.com	', '555-901-2346	', '2024-06-25'),
(23, 'Nancy Rodriguez	', 'nancy.rodriguez@example.com	', '555-012-3457	', '2024-07-10'),
(24, 'Christopher Lewis	', 'christopher.lewis@example.com	', '555-123-4569', '2024-08-01'),
(25, 'Jessica Lee	', 'jessica.lee@example.com	', '555-234-5670	', '2024-09-05'),
(26, 'Daniel Walker	', 'daniel.walker@example.com	', '555-345-6781	', '2024-10-15'),
(27, 'Margaret Hall	', 'margaret.hall@example.com	', '555-456-7892	', '2024-11-10'),
(28, 'Paul Allen	', 'paul.allen@example.com	', '555-567-8903	', '2024-12-05'),
(29, 'Angela Young	', 'angela.young@example.com	', '555-678-9014	', '2024-12-15'),
(30, 'Mark King	', 'mark.king@example.com	', '555-789-0125	', '2025-01-01'),
(31, 'Sandra Wright	', 'sandra.wright@example.com	', '555-890-1236	', '2025-02-05');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `transaction_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `borrow_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `status` enum('Borrowed','Returned') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`transaction_id`, `member_id`, `id`, `borrow_date`, `return_date`, `status`) VALUES
(3, 2, 2, '2024-12-01', '2024-12-05', 'Returned'),
(4, 4, 8, '2024-01-05', '2024-01-12', 'Returned'),
(5, 7, 7, '2024-01-07', '2024-01-15', 'Returned'),
(6, 5, 7, '2024-12-01', '2024-12-18', 'Borrowed'),
(7, 2, 7, '2024-11-03', '2024-12-25', 'Borrowed'),
(8, 9, 21, '2024-02-05', '2024-02-12', 'Returned'),
(9, 10, 3, '2024-03-15', '2024-03-22', 'Returned'),
(10, 24, 11, '2024-06-10', '2024-06-17', 'Returned'),
(11, 10, 3, '2024-11-10', '2024-12-30', 'Borrowed'),
(12, 20, 10, '2024-06-01', '2024-06-08', 'Returned'),
(13, 15, 10, '2024-11-30', '2024-12-12', 'Borrowed'),
(14, 10, 6, '2024-11-15', '2024-09-12', 'Borrowed'),
(15, 13, 9, '2024-12-09', '2024-12-15', 'Borrowed'),
(16, 8, 8, '2024-11-17', '2025-01-10', 'Borrowed'),
(17, 15, 10, '2024-10-30', '2024-12-24', 'Borrowed'),
(19, 3, 10, '2024-11-30', '2024-12-05', 'Returned');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`member_id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `member_id` (`member_id`),
  ADD KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `members`
--
ALTER TABLE `members`
  MODIFY `member_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`),
  ADD CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`id`) REFERENCES `books` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

/*grree rawr rawr*/