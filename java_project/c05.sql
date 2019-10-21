-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 17, 2018 at 10:45 PM
-- Server version: 10.1.28-MariaDB
-- PHP Version: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `c05`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `bookid` varchar(8) NOT NULL,
  `bookTitle` varchar(20) NOT NULL,
  `authorName` varchar(15) NOT NULL,
  `publicationYear` int(8) NOT NULL,
  `availiableQuantity` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`bookid`, `bookTitle`, `authorName`, `publicationYear`, `availiableQuantity`) VALUES
('b1', 'c++', 'saif', 2012, 11),
('b2', 'java', 'mamu', 1983, 10),
('b3', 'c', 'chor', 2000, 2),
('b4', 'python', 'niloy', 2015, 2);

-- --------------------------------------------------------

--
-- Table structure for table `borrowinfo`
--

CREATE TABLE `borrowinfo` (
  `borrowId` varchar(8) NOT NULL,
  `bookId` varchar(8) NOT NULL,
  `userId` varchar(8) NOT NULL,
  `borrowDate` varchar(8) NOT NULL,
  `returnDate` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `borrowinfo`
--

INSERT INTO `borrowinfo` (`borrowId`, `bookId`, `userId`, `borrowDate`, `returnDate`) VALUES
('123', 'b1', 's3', '20181218', '20181220'),
('1234', 'b2', 's2', '20181217', '20181222'),
('12345', 'b4', 's1', '20181212', '20181215'),
('123456', 'b3', 's2', '20181012', '20181015'),
('1234567', 'b4', 's2', '20181010', '20181012');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `userid` varchar(8) NOT NULL,
  `customerName` varchar(20) NOT NULL,
  `phoneNumber` varchar(15) NOT NULL,
  `address` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`userid`, `customerName`, `phoneNumber`, `address`) VALUES
('s1', 'abdal', '1293819242', 'badda'),
('s2', 'abdalasd', '129381924', 'asg'),
('s3', 'aladin', '1234567', 'nikunja');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `userid` varchar(8) NOT NULL,
  `employeeName` varchar(20) NOT NULL,
  `phoneNumber` int(15) NOT NULL,
  `role` varchar(15) NOT NULL,
  `salary` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`userid`, `employeeName`, `phoneNumber`, `role`, `salary`) VALUES
('e1', 'nasib', 123456789, 'manager', 100000.00),
('e2', 'smarok', 21391242, 'employee', 2020.00);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `userid` varchar(8) NOT NULL,
  `Password` varchar(8) NOT NULL,
  `status` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`userid`, `Password`, `status`) VALUES
('e1', 'e1', 0),
('e2', 'e2', 0),
('s1', 's1', 1),
('s2', 's2', 1),
('s3', 's3', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`bookid`);

--
-- Indexes for table `borrowinfo`
--
ALTER TABLE `borrowinfo`
  ADD PRIMARY KEY (`borrowId`),
  ADD KEY `bookId` (`bookId`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`userid`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`userid`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD UNIQUE KEY `un` (`userid`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `borrowinfo`
--
ALTER TABLE `borrowinfo`
  ADD CONSTRAINT `borrowinfo_ibfk_1` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookid`),
  ADD CONSTRAINT `borrowinfo_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `customer` (`userid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
