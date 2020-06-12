-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 12, 2020 at 03:50 PM
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
-- Table structure for table `attendance_management`
--

CREATE TABLE `attendance_management` (
  `a_id` int(5) NOT NULL,
  `emp_id` int(6) NOT NULL,
  `p_date` varchar(10) DEFAULT NULL,
  `pi_time` varchar(5) DEFAULT NULL,
  `po_time` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `attendance_management`
--

INSERT INTO `attendance_management` (`a_id`, `emp_id`, `p_date`, `pi_time`, `po_time`) VALUES
(57, 300010, '12-06-2020', '06:55', '06:55');

-- --------------------------------------------------------

--
-- Table structure for table `company_master`
--

CREATE TABLE `company_master` (
  `comp_id` int(6) NOT NULL,
  `comp_name` varchar(15) NOT NULL,
  `comp_password` varchar(15) NOT NULL,
  `comp_email` varchar(30) NOT NULL,
  `comp_city` varchar(30) NOT NULL,
  `comp_pincode` int(6) NOT NULL,
  `comp_mob1` varchar(11) NOT NULL,
  `comp_mob2` varchar(11) DEFAULT NULL,
  `comp_person` varchar(25) NOT NULL,
  `comp_licno` varchar(21) NOT NULL,
  `comp_gstno` varchar(15) DEFAULT NULL,
  `comp_website` varchar(15) DEFAULT NULL,
  `comp_status` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `company_master`
--

INSERT INTO `company_master` (`comp_id`, `comp_name`, `comp_password`, `comp_email`, `comp_city`, `comp_pincode`, `comp_mob1`, `comp_mob2`, `comp_person`, `comp_licno`, `comp_gstno`, `comp_website`, `comp_status`) VALUES
(100058, 'shiv Computers', 'abc123', 'shiv.01@gmail.com', 'Bharuch', 333931, '1234056789', NULL, 'Meet Patel', '98569856', '12345678', 'asbc.com', 'Active'),
(100059, 'ABC Ltd.', 'abc1234', 'abcltd@gmail.com', 'vadodara', 356339, '6325417980', NULL, 'Vivek Jhaa', '66388524', '12345678', 'asbc.com', 'Active'),
(100060, 'Asian Tech', 'abc123', 'aTech@gmail.com', 'Surat', 352236, '1234567890', NULL, 'Ashok Uja', '12345688', '12345678', 'asbc.com', 'Active'),
(100061, 'Htmm Ltd', 'abc123', 'htmm@gmail.com', 'Anand', 123565, '9632589632', NULL, 'Anand Shah', '09963854', '12345678', 'asbc.com', 'Deactivate'),
(100062, 'Tupper Dare', 'abc1234', 'tupper@gmail.com', 'Surat', 325565, '1234567890', NULL, 'Ashok Rao', '12345678', '12345678', 'asbc.com', 'Active');

-- --------------------------------------------------------

--
-- Table structure for table `employee_master`
--

CREATE TABLE `employee_master` (
  `comp_id` int(6) NOT NULL,
  `emp_id` int(6) NOT NULL,
  `emp_password` varchar(15) NOT NULL,
  `emp_name` varchar(30) NOT NULL,
  `emp_gen` char(1) NOT NULL,
  `emp_dob` varchar(10) NOT NULL,
  `emp_aadharno` varchar(12) NOT NULL,
  `emp_mob1` varchar(11) NOT NULL,
  `emp_mob2` varchar(11) DEFAULT NULL,
  `emp_email` varchar(50) NOT NULL,
  `emp_address` varchar(70) NOT NULL,
  `emp_pincode` int(6) NOT NULL,
  `emp_city` varchar(30) NOT NULL,
  `emp_state` varchar(20) NOT NULL,
  `emp_status` varchar(12) NOT NULL,
  `shop_id` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `employee_master`
--

INSERT INTO `employee_master` (`comp_id`, `emp_id`, `emp_password`, `emp_name`, `emp_gen`, `emp_dob`, `emp_aadharno`, `emp_mob1`, `emp_mob2`, `emp_email`, `emp_address`, `emp_pincode`, `emp_city`, `emp_state`, `emp_status`, `shop_id`) VALUES
(100058, 300010, 'abc123', 'Meet Patel', 'F', '1999-10-30', '111222111222', '1234567890', '', 'meetpatel99@gmail.com', 'umalla', 393120, 'bharuch', 'gujrat', 'Active', 200009),
(100058, 300011, 'abc123', 'Kartavya Vadera', 'M', '1999-5-12', '123456789552', '5315353535', '', 'kt@gmail.com', 'nadiad', 325562, 'Nadiad', 'Gujarat', 'Active', 200009),
(100058, 300012, 'abc123', 'Meet Patel', 'M', '1999-8-30', '123456789558', '5353535353', '', 'meet@gmail.com', 'Umalla', 393120, 'Bharuch', 'Gujarat', 'Deactive', 200009),
(100058, 300013, 'abc123', 'Jeet Rana', 'M', '1994-5-12', '123456789528', '1234846154', '', 'jeet@gmail.com', 'Nadiad', 325523, 'Nadiad', 'Gujarat', 'Active', 200009);

-- --------------------------------------------------------

--
-- Table structure for table `emp_attendance`
--

CREATE TABLE `emp_attendance` (
  `emp_id` int(6) NOT NULL,
  `att_id` int(6) NOT NULL,
  `pi_date` varchar(10) NOT NULL,
  `pi_time` varchar(5) NOT NULL,
  `pi_locLat` varchar(40) NOT NULL,
  `pi_locLong` varchar(40) NOT NULL,
  `po_time` varchar(5) NOT NULL,
  `po_locLat` varchar(40) NOT NULL,
  `po_locLong` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `emp_ptarget`
--

CREATE TABLE `emp_ptarget` (
  `ep_id` int(6) NOT NULL,
  `emp_id` int(6) NOT NULL,
  `p_target` int(6) DEFAULT NULL,
  `p_month` varchar(8) DEFAULT NULL,
  `comp_id` int(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `emp_ptarget`
--

INSERT INTO `emp_ptarget` (`ep_id`, `emp_id`, `p_target`, `p_month`, `comp_id`) VALUES
(119, 300010, 2988, '06-2020', 100058),
(120, 300011, 3000, '06-2020', 100058);

-- --------------------------------------------------------

--
-- Table structure for table `emp_routinetask`
--

CREATE TABLE `emp_routinetask` (
  `rt_id` int(6) NOT NULL,
  `emp_id` int(6) NOT NULL,
  `rt_date` varchar(11) NOT NULL,
  `rt_unit` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `emp_routinetask`
--

INSERT INTO `emp_routinetask` (`rt_id`, `emp_id`, `rt_date`, `rt_unit`) VALUES
(8, 300010, '12-06-2020', 12),
(9, 300010, '12-06-2020', 12);

-- --------------------------------------------------------

--
-- Table structure for table `emp_sal`
--

CREATE TABLE `emp_sal` (
  `es_id` int(6) NOT NULL,
  `emp_id` int(6) NOT NULL,
  `emp_bsal` int(5) NOT NULL,
  `emp_inc` int(5) NOT NULL,
  `emp_totsal` int(7) NOT NULL,
  `emp_month` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `emp_sal`
--

INSERT INTO `emp_sal` (`es_id`, `emp_id`, `emp_bsal`, `emp_inc`, `emp_totsal`, `emp_month`) VALUES
(136, 300010, 10000, 0, 10000, '06-2020'),
(137, 300011, 10000, 0, 10000, '06-2020');

-- --------------------------------------------------------

--
-- Table structure for table `emp_target`
--

CREATE TABLE `emp_target` (
  `et_id` int(6) NOT NULL,
  `comp_id` int(6) NOT NULL,
  `atargetmonth` varchar(8) NOT NULL,
  `atarget` int(5) NOT NULL,
  `p_target` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `emp_target`
--

INSERT INTO `emp_target` (`et_id`, `comp_id`, `atargetmonth`, `atarget`, `p_target`) VALUES
(36, 100058, '06-2020', 11988, 3000);

-- --------------------------------------------------------

--
-- Table structure for table `leave_management`
--

CREATE TABLE `leave_management` (
  `leave_id` int(5) NOT NULL,
  `emp_id` int(6) NOT NULL,
  `currDate` varchar(10) NOT NULL,
  `fromDate` varchar(10) NOT NULL,
  `toDate` varchar(10) NOT NULL,
  `type1` varchar(10) NOT NULL,
  `type2` varchar(10) NOT NULL,
  `reason` varchar(50) NOT NULL,
  `status` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `leave_management`
--

INSERT INTO `leave_management` (`leave_id`, `emp_id`, `currDate`, `fromDate`, `toDate`, `type1`, `type2`, `reason`, `status`) VALUES
(17, 300010, '12-06-2020', '27-6-2020', '30-6-2020', 'Full Day', 'Full Day', 'Care for Ailing Family Member', 'Approved'),
(25, 300010, '12-06-2020', '20-6-2020', '21-6-2020', 'Half Day', 'Full Day', 'Education', 'Approve'),
(26, 300010, '12-06-2020', '28-6-2020', '1-7-2020', 'Full Day', 'Full Day', 'Care for Ailing Family Member', 'Reject'),
(27, 300010, '12-06-2020', '2-7-2020', '7-6-2020', 'Full Day', 'Full Day', 'Health Reasons', 'Approve'),
(28, 300010, '12-06-2020', '15-6-2020', '23-6-2020', 'Full Day', 'Full Day', 'Care for Ailing Family Member', 'Pending');

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
(100058, 200009, 'SMS', '21.7947247', '73.2987012'),
(100058, 200010, 'Raj Mobile', '21.7947264', '73.2987011'),
(100058, 200011, 'Jay Mobile', '21.7947245', '73.2987012');

-- --------------------------------------------------------

--
-- Table structure for table `super_admin`
--

CREATE TABLE `super_admin` (
  `username` varchar(6) NOT NULL,
  `password` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `super_admin`
--

INSERT INTO `super_admin` (`username`, `password`) VALUES
('999999', 'adm123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `attendance_management`
--
ALTER TABLE `attendance_management`
  ADD PRIMARY KEY (`a_id`);

--
-- Indexes for table `company_master`
--
ALTER TABLE `company_master`
  ADD PRIMARY KEY (`comp_id`);

--
-- Indexes for table `employee_master`
--
ALTER TABLE `employee_master`
  ADD PRIMARY KEY (`emp_id`),
  ADD KEY `comp_id` (`comp_id`);

--
-- Indexes for table `emp_attendance`
--
ALTER TABLE `emp_attendance`
  ADD PRIMARY KEY (`att_id`),
  ADD KEY `emp_id` (`emp_id`);

--
-- Indexes for table `emp_ptarget`
--
ALTER TABLE `emp_ptarget`
  ADD PRIMARY KEY (`ep_id`);

--
-- Indexes for table `emp_routinetask`
--
ALTER TABLE `emp_routinetask`
  ADD PRIMARY KEY (`rt_id`);

--
-- Indexes for table `emp_sal`
--
ALTER TABLE `emp_sal`
  ADD PRIMARY KEY (`es_id`);

--
-- Indexes for table `emp_target`
--
ALTER TABLE `emp_target`
  ADD PRIMARY KEY (`et_id`);

--
-- Indexes for table `leave_management`
--
ALTER TABLE `leave_management`
  ADD PRIMARY KEY (`leave_id`),
  ADD KEY `emp_id` (`emp_id`);

--
-- Indexes for table `shop_master`
--
ALTER TABLE `shop_master`
  ADD PRIMARY KEY (`shop_id`);

--
-- Indexes for table `super_admin`
--
ALTER TABLE `super_admin`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `attendance_management`
--
ALTER TABLE `attendance_management`
  MODIFY `a_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT for table `company_master`
--
ALTER TABLE `company_master`
  MODIFY `comp_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100063;

--
-- AUTO_INCREMENT for table `employee_master`
--
ALTER TABLE `employee_master`
  MODIFY `emp_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=300014;

--
-- AUTO_INCREMENT for table `emp_attendance`
--
ALTER TABLE `emp_attendance`
  MODIFY `att_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1009;

--
-- AUTO_INCREMENT for table `emp_ptarget`
--
ALTER TABLE `emp_ptarget`
  MODIFY `ep_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=121;

--
-- AUTO_INCREMENT for table `emp_routinetask`
--
ALTER TABLE `emp_routinetask`
  MODIFY `rt_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `emp_sal`
--
ALTER TABLE `emp_sal`
  MODIFY `es_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=138;

--
-- AUTO_INCREMENT for table `emp_target`
--
ALTER TABLE `emp_target`
  MODIFY `et_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT for table `leave_management`
--
ALTER TABLE `leave_management`
  MODIFY `leave_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `shop_master`
--
ALTER TABLE `shop_master`
  MODIFY `shop_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=200012;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee_master`
--
ALTER TABLE `employee_master`
  ADD CONSTRAINT `employee_master_ibfk_1` FOREIGN KEY (`comp_id`) REFERENCES `company_master` (`comp_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `emp_attendance`
--
ALTER TABLE `emp_attendance`
  ADD CONSTRAINT `emp_attendance_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employee_master` (`emp_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `leave_management`
--
ALTER TABLE `leave_management`
  ADD CONSTRAINT `leave_management_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employee_master` (`emp_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
