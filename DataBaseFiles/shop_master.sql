-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 14, 2020 at 07:40 AM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `salesrecord`
--

-- --------------------------------------------------------

--
-- Table structure for table `shop_master`
--

CREATE TABLE `shop_master` (
  `comp_id` int(6) NOT NULL,
  `shop_id` int(6) NOT NULL,
  `shop_name` varchar(35) NOT NULL,
  `shop_locLat` varchar(40) NOT NULL,
  `shop_locLong` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shop_master`
--

INSERT INTO `shop_master` (`comp_id`, `shop_id`, `shop_name`, `shop_locLat`, `shop_locLong`) VALUES
(100042, 200001, 'Mayuri Mobile', '37.421998333', '-122.0840000'),
(100042, 200002, 'Shiv Mobiles', '37.421998333', '-122.0840000');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `shop_master`
--
ALTER TABLE `shop_master`
  ADD PRIMARY KEY (`shop_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `shop_master`
--
ALTER TABLE `shop_master`
  MODIFY `shop_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=200003;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
