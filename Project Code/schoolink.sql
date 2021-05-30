drop database if exists schoolink;
create database schooLink;
use schoolink;

-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Φιλοξενητής: 127.0.0.1
-- Χρόνος δημιουργίας: 25 Μάη 2021 στις 18:28:37
-- Έκδοση διακομιστή: 10.4.14-MariaDB
-- Έκδοση PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `schoolink`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `classes`
--

CREATE TABLE `classes` (
  `id` int(11) NOT NULL,
  `cname` varchar(255) NOT NULL COMMENT 'Όνομα',
  `comments` varchar(255) DEFAULT NULL COMMENT 'Σχόλια',
  `fees` float DEFAULT NULL COMMENT 'Δίδακτρα'
) ENGINE=InnoDB DEFAULT CHARSET=greek;

--
-- Άδειασμα δεδομένων του πίνακα `classes`
--

INSERT INTO `classes` (`id`, `cname`, `comments`, `fees`) VALUES
(-1, '-', NULL, NULL),
(1, 'Α', 'Α  τάξη σχολείου', 1000),
(2, 'Β', 'Β τάξη σχολείου111', 2000),
(3, 'Γ', 'Γ τάξη σχολείου', 3220);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `courses`
--

CREATE TABLE `courses` (
  `id` int(11) NOT NULL,
  `cname` varchar(255) NOT NULL,
  `class_id` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=greek;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `expenses`
--

CREATE TABLE `expenses` (
  `id` int(11) NOT NULL,
  `expense_type` varchar(100) NOT NULL COMMENT 'Είδος Λειτουργικών Εξόδων',
  `amount` int(11) NOT NULL COMMENT 'Ποσό',
  `payment_method` enum("Μετρητά","Κάρτα","Τραπεζικό Έμβασμα") NOT NULL COMMENT 'Μέθοδος Πληρωμής',
  `edate` date NOT NULL COMMENT 'Ημερομηνία Πληρωμής',
  `comments` varchar(500) NOT NULL COMMENT 'Σημειώσεις'
) ENGINE=InnoDB DEFAULT CHARSET=greek;

--
-- Άδειασμα δεδομένων του πίνακα `expenses`
--

INSERT INTO `expenses` (`id`, `expense_type`, `amount`, `payment_method`, `edate`, `comments`) VALUES
(1, "ΔΕΗ", 400, "Τραπεζικό Έμβασμα", '2021-03-01', "Πληρωμή ρεύματος στη ΔΕΗ"),
(2, "VODAFONE", 80, "Κάρτα", '2021-03-01', "Πληρωμή λογαριασμού τηλεφωνίας");
/*(1, '2021-05-10 19:20:13', 150, 'Είδη SM', 3),
(2, '2021-05-03 19:20:20', 100, 'Επισκευή', 5);*/

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `feedback`
--

CREATE TABLE `feedback` (
  `id` int(11) NOT NULL,
  `grade` tinyint(4) NOT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `fdate` date NOT NULL,
  `school_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=greek;

--
-- Άδειασμα δεδομένων του πίνακα `feedback`
--

INSERT INTO `feedback` (`id`, `grade`, `comment`, `fdate`, `school_id`, `user_id`) VALUES
(9, 6, '', '2021-05-25', 1789, 2),
(10, 7, '', '2021-05-25', 1789, 4),
(11, 7, '', '2021-05-25', 1789, 4),
(12, 7, '', '2021-05-25', 1789, 4);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `grades`
--

CREATE TABLE `grades` (
  `id` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  `pupil_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `description` varchar(100) NOT NULL,
  `grade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=greek;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `groups`
--

CREATE TABLE `groups` (
  `id` int(11) NOT NULL,
  `gname` varchar(255) NOT NULL COMMENT 'Όνομα',
  `teacher_id` int(11) DEFAULT NULL COMMENT 'Υπευθ. Καθηγητής',
  `class_id` int(11) DEFAULT NULL COMMENT 'Τάξη',
  `comments` varchar(255) DEFAULT NULL COMMENT 'Σχόλια',
  `sub_class` tinyint(1) NOT NULL COMMENT 'Τμήμα μαθητών'
) ENGINE=InnoDB DEFAULT CHARSET=greek;

--
-- Άδειασμα δεδομένων του πίνακα `groups`
--

INSERT INTO `groups` (`id`, `gname`, `teacher_id`, `class_id`, `comments`, `sub_class`) VALUES
(55, 'A1', 57, 1, 'A1a1', 1),
(56, 'A2', 5, 1, 'aaaa', 1),
(57, 'Β1', 10, 2, 'β1aaaa', 1),
(58, 'B2', 6, 2, 'b2aa', 1),
(59, 'Γ1', 10, 3, 'γ1γ1aa', 1),
(60, 'Γ2', 35, 3, 'ΑΑΣΔΑΔaaaaaaaa', 1),
(61, 'ΓονείςΑ', NULL, NULL, 'Γονείς Α Γυμνασίουa', 0),
(62, 'Γονείς Β', 1, 1, 'Γονείς Β', 0);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `msgs`
--

CREATE TABLE `msgs` (
  `id` int(11) NOT NULL,
  `msg_date` datetime NOT NULL,
  `msg_subject` varchar(255) NOT NULL,
  `cloud_id` varchar(150) DEFAULT NULL,
  `deadline` datetime DEFAULT NULL COMMENT 'Προθεσμία',
  `parent_msg_id` int(11) DEFAULT NULL,
  `kind` tinyint(4) NOT NULL COMMENT '0=msg, 1=εργασία, 2=ανακοίνωση'
) ENGINE=InnoDB DEFAULT CHARSET=greek;

--
-- Άδειασμα δεδομένων του πίνακα `msgs`
--

INSERT INTO `msgs` (`id`, `msg_date`, `msg_subject`, `cloud_id`, `deadline`, `parent_msg_id`, `kind`) VALUES
(47, '2021-05-07 13:29:49', '1η Εργασία', '1VOlZwbx7BAAAAAAAABP-g', '2021-05-18 13:30:27', NULL, 1),
(48, '2021-05-07 13:37:57', 'Απ. Εργασία1', '1VOlZwbx7BAAAAAAAABP-w', NULL, 47, 0),
(49, '2021-05-07 13:38:48', '1η Ανακοίνωση', '1VOlZwbx7BAAAAAAAABP_A', NULL, 47, 2),
(50, '2021-05-14 17:32:57', 'test1', '1VOlZwbx7BAAAAAAAABQIA', NULL, NULL, 0),
(51, '2021-05-14 17:42:39', 'test2', '1VOlZwbx7BAAAAAAAABQIQ', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `msgs_details`
--

CREATE TABLE `msgs_details` (
  `msg_id` int(11) NOT NULL,
  `from_user_id` int(11) NOT NULL,
  `to_user_id` int(11) NOT NULL,
  `to_or_cc` tinyint(1) NOT NULL,
  `grade` float DEFAULT NULL COMMENT 'Βαθμός'
) ENGINE=InnoDB DEFAULT CHARSET=greek;

--
-- Άδειασμα δεδομένων του πίνακα `msgs_details`
--

INSERT INTO `msgs_details` (`msg_id`, `from_user_id`, `to_user_id`, `to_or_cc`, `grade`) VALUES
(47, 2, 27, 1, NULL),
(47, 4, 2, 1, NULL),
(47, 4, 20, 1, NULL),
(47, 4, 26, 1, NULL),
(47, 4, 53, 1, NULL),
(48, 24, 4, 1, NULL),
(49, 2, 4, 1, NULL),
(50, 2, 3, 1, NULL),
(50, 2, 9, 1, NULL),
(51, 4, 2, 1, NULL);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `participates`
--

CREATE TABLE `participates` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=greek;

--
-- Άδειασμα δεδομένων του πίνακα `participates`
--

INSERT INTO `participates` (`user_id`, `group_id`) VALUES
(48, 56),
(40, 56),
(65, 56),
(16, 58),
(73, 58),
(20, 58),
(53, 58),
(45, 58),
(53, 57),
(45, 57),
(47, 57),
(96, 57),
(101, 57),
(75, 57),
(5, 59),
(99, 59),
(23, 59),
(63, 59),
(93, 60),
(28, 60),
(34, 60),
(35, 60),
(37, 55),
(74, 55),
(2, 55),
(74, 61),
(2, 61),
(48, 61),
(40, 61),
(25, 61),
(94, 61),
(26, 57);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `payments`
--

CREATE TABLE `payments` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL COMMENT 'Χρήστης',
  `amount` float NOT NULL COMMENT 'Ποσό',
  `pdate` datetime NOT NULL COMMENT 'Ημερ. πληρωμής',
  `comments` varchar(100) DEFAULT NULL COMMENT 'Σχόλια'
) ENGINE=InnoDB DEFAULT CHARSET=greek;

--
-- Άδειασμα δεδομένων του πίνακα `payments`
--

INSERT INTO `payments` (`id`, `user_id`, `amount`, `pdate`, `comments`) VALUES
(1, 68, 200, '2021-05-14 00:00:00', NULL),
(2, 98, 150, '2021-05-05 00:00:00', NULL),
(3, 68, 230, '2021-04-05 00:00:00', NULL);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `roles`
--

CREATE TABLE `roles` (
  `id` tinyint(4) NOT NULL,
  `description` varchar(50) NOT NULL,
  `comments` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=greek;

--
-- Άδειασμα δεδομένων του πίνακα `roles`
--

INSERT INTO `roles` (`id`, `description`, `comments`) VALUES
(1, 'Διευθυντής', NULL),
(2, 'Γραμματεία', NULL),
(3, 'Εκπαιδευτικός', NULL),
(4, 'Μαθητής', NULL),
(5, 'Κηδεμόνας', NULL);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `school_params`
--

CREATE TABLE `school_params` (
  `school_name` varchar(50) NOT NULL,
  `school_title` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `fax` varchar(50) NOT NULL,
  `web` varchar(50) NOT NULL,
  `director_id` int(11) NOT NULL,
  `school_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=greek;

--
-- Άδειασμα δεδομένων του πίνακα `school_params`
--

INSERT INTO `school_params` (`school_name`, `school_title`, `address`, `email`, `phone`, `fax`, `web`, `director_id`, `school_id`) VALUES
('178ο Γυμνάσιο Πάτρας', '', 'Κανακάρη 77', 'mail_178gym_patras@gmail.com', '697815287146', '697815287146', 'www.178gympatras.gr', 1, 1789);

-- --------------------------------------------------------


--
-- Δομή πίνακα για τον πίνακα `teach`
--

CREATE TABLE `teach` (
  `teacher_id` int(11) NOT NULL COMMENT 'Εκπαιδετυικός',
  `group_id` int(11) NOT NULL COMMENT 'Τμήμα*',
  `course_id` int(11) NOT NULL COMMENT 'Μάθημα',
  `comments` varchar(100) DEFAULT NULL COMMENT 'Σχόλια'
) ENGINE=InnoDB DEFAULT CHARSET=greek;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `tests`
--

CREATE TABLE `tests` (
  `id` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `comments` varchar(255) NOT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `description` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=greek;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `surname` varchar(255) NOT NULL COMMENT 'Επώνυμο',
  `firstname` varchar(50) NOT NULL COMMENT 'Όνομα',
  `gender` bit(1) NOT NULL COMMENT 'Φύλο',
  `fathername` varchar(40) DEFAULT NULL COMMENT 'Πατρώνυμο',
  `mothername` varchar(40) DEFAULT NULL COMMENT 'Μητρώνυμο',
  `email` varchar(40) NOT NULL COMMENT 'email',
  `username` varchar(40) NOT NULL COMMENT 'Username',
  `password` varchar(20) NOT NULL COMMENT 'Password',
  `birthdate` date DEFAULT NULL COMMENT ' Ημερ. Γέννησης',
  `role_id` tinyint(11) DEFAULT NULL COMMENT 'Ρόλος',
  `parent_id` int(11) DEFAULT NULL COMMENT 'Κηδεμόνας',
  `discount` int(11) DEFAULT NULL COMMENT 'Έκπτωση',
  `teaching` smallint(4) DEFAULT NULL COMMENT 'Διδασκαλία',
  `salary` float DEFAULT NULL COMMENT 'Μισθός',
  `photo` varchar(40) DEFAULT NULL COMMENT 'photo'
) ENGINE=InnoDB DEFAULT CHARSET=greek;

--
-- Άδειασμα δεδομένων του πίνακα `users`
--

INSERT INTO `users` (`id`, `surname`, `firstname`, `gender`, `fathername`, `mothername`, `email`, `username`, `password`, `birthdate`, `role_id`, `parent_id`, `discount`, `teaching`, `salary`, `photo`) VALUES
(0, '-', '-', b'1', '-', '-', '0@gmail.com', '0', '0', NULL, 1, NULL, 0, NULL, NULL, NULL),
(1, 'ΖΑΧΟY', 'ΑΙΚΑΤΕΡΙΝΗ', b'1', 'ΓΙΩΡΓΟΣ', 'ΜΑΡΙΑ', 'fi0@gmail.com', 'fi0', '0', NULL, 1, NULL, 0, NULL, 2000, NULL),
(2, 'ΑΛΕΞΑΝΔΡΙΔΗ', 'ΕΥΑΓΓΕΛΙΑ', b'1', 'ΓΙΑΝΝΗΣ', 'ΕΛΕΝΗ', 'ae1@gmail.com', 'ae1', '1', NULL, 2, NULL, 0, NULL, 1000, NULL),
(3, 'ΖΑΦΕΙΡΟΠΟΥΛΟY', 'ΙΩΑΝΝΑ', b'1', 'ΚΩΣΤΑΣ', 'ΚΑΤΕΡΙΝΑ', 'fi1@gmail.com', 'fi1', '1', NULL, 3, NULL, 0, NULL, 1500, NULL),
(4, 'ΜΟΣΧΟΣ', 'ΕΛΕΥΘΕΡΙΟΣ', b'0', 'ΒΑΓΓΕΛΗΣ', 'ΚΩΝΣΤΑΝΤΙΝΑ', 'le4@gmail.com', 'le4', '4', NULL, 3, NULL, 0, NULL, NULL, NULL),
(5, 'ΚΑΛΑΡΗΣ', 'ΗΛΙΑΣ', b'0', 'ΘΟΔΩΡΟΣ', 'ΑΝΑΣΤΑΣΙΑ', 'jg5@gmail.com', 'jg5', '5', NULL, 3, NULL, 0, NULL, NULL, NULL),
(6, 'ΜΗΤΖΟΥ', 'ΘΩΜΑΗ', b'1', 'ΝΙΚΟΣ', 'ΒΑΣΙΛΙΚΗ', 'lh5@gmail.com', 'lh5', '5', NULL, 3, NULL, 0, NULL, 1500, NULL),
(7, 'ΕΛΕΥΘΕΡΟΠΟΥΛΟΣ', 'ΓΕΩΡΓΙΟΣ', b'0', 'ΑΝΑΣΤΑΣΙΟΣ', 'ΠΑΡΑΣΚΕΥΗ', 'ec7@gmail.com', 'ec7', '7', NULL, 3, NULL, 0, NULL, NULL, NULL),
(8, 'ΤΣΙΩΛΗ', 'ΑΝΤΙΓΟΝΗ', b'1', 'ΔΗΜΗΤΡΗΣ', 'ΠΑΝΑΓΙΩΤΑ', 'ta9@gmail.com', 'ta9', '9', NULL, 3, NULL, 0, NULL, 1500, NULL),
(9, 'ΣΑΚΕΛΛΑΡΙΟΥ', 'ΚΩΝΣΤΑΝΤΙΑ', b'1', 'ΠΑΝΑΓΙΩΤΗΣ', 'ΣΟΦΙΑ', 'sj10@gmail.com', 'sj10', '10', NULL, 3, NULL, 0, NULL, 1500, NULL),
(10, 'ΣΕΡΠΕΤΖΟΓΛΟΥ', 'ΣΟΥΛΤΑΝΑ', b'1', 'ΒΑΣΙΛΗΣ', 'ΑΓΓΕΛΙΚΗ', 'ss12@gmail.com', 'ss12', '12', NULL, 3, NULL, 0, NULL, 1500, NULL),
(11, 'ΜΗΤΣΑΚΗ', 'ΣΩΤΗΡΙΑ', b'1', 'ΧΡΗΣΤΟΣ', 'ΓΕΩΡΓΙΑ', 'ls13@gmail.com', 'ls13', '13', NULL, 3, NULL, 0, NULL, 1500, NULL),
(12, 'ΜΑΡΚΟΠΟΥΛΟΣ', 'ΣΩΤΗΡΙΟΣ', b'0', 'ΜΑΝΟΛΗΣ', 'ΧΡΙΣΤΙΝΑ', 'ls14@gmail.com', 'ls14', '14', NULL, 3, NULL, 0, NULL, NULL, NULL),
(13, 'ΣΑΜΑΡΑ', 'ΝΙΚΟΛΕΤΤΑ', b'1', 'ΘΑΝΑΣΗΣ', 'ΕΥΑΓΓΕΛΙΑ', 'sm15@gmail.com', 'sm15', '15', NULL, 3, NULL, 0, NULL, 1500, NULL),
(14, 'ΣΙΔΕΡΗ', 'ΑΜΑΛΙΑ', b'1', 'ΜΙΧΑΛΗΣ', 'ΕΙΡΗΝΗ', 'sa15@gmail.com', 'sa15', '15', NULL, 3, NULL, 0, NULL, 1500, NULL),
(15, 'ΧΡΗΣΤΟΠΟΥΛΟΣ', 'ΑΘΑΝΑΣΙΟΣ', b'0', 'ΧΑΡΑΛΑΜΠΟΣ', 'ΣΤΑΥΡΟΥΛΑ', 'wa15@gmail.com', 'wa15', '15', NULL, 3, NULL, 0, NULL, NULL, NULL),
(16, 'ΒΛΑΧΟΠΟΥΛΟY', 'ΑΙΚΑΤΕΡΙΝΗ', b'1', 'ΑΝΔΡΕΑΣ', 'ΔΗΜΗΤΡΑ', 'ba19@gmail.com', 'ba19', '19', NULL, 3, NULL, 0, NULL, 1500, NULL),
(17, 'ΚΑΒΑΛΟΥΡΗ', 'ΖΩΗ', b'1', 'ΑΝΤΩΝΗΣ', 'ΑΝΝΑ', 'jf19@gmail.com', 'jf19', '19', NULL, 3, NULL, 0, NULL, 1500, NULL),
(18, 'ΦΑΝΟΥΡΑΚΗ', 'ΑΡΕΤΗ', b'1', 'ΣΠΥΡΟΣ', 'ΙΩΑΝΝΑ', 'va19@gmail.com', 'va19', '19', NULL, 3, NULL, 0, NULL, 1500, NULL),
(20, 'ΓΙΑΛΑΜΑΣ', 'ΚΩΝΣΤΑΝΤΙΝΟΣ', b'0', 'ΑΝΤΩΝΗΣ', 'ΠΑΝΑΓΙΩΤΑ', 'cj21@gmail.com', 'cj21', '21', NULL, 4, 62, 0, NULL, NULL, NULL),
(21, 'ΘΕΟΔΩΡΙΔΗ', 'ΚΑΛΛΙΟΠΗ', b'1', 'ΗΛΙΑΣ', 'ΔΕΣΠΟΙΝΑ', 'hj21@gmail.com', 'hj21', '21', NULL, 4, 63, 0, NULL, NULL, NULL),
(22, 'ΝΙΚΟΛΑΪΔΗΣ', 'ΦΩΤΙΟΣ', b'0', 'ΣΠΥΡΟΣ', 'ΣΟΦΙΑ', 'mv23@gmail.com', 'mv23', '23', NULL, 4, 64, 0, NULL, NULL, NULL),
(23, 'ΛΑΜΠΑΚΗ', 'ΕΥΓΕΝΙΑ', b'1', 'ΑΛΕΞΑΝΔΡΟΣ', 'ΚΑΛΛΙΟΠΗ', 'ke24@gmail.com', 'ke24', '24', NULL, 4, 65, 0, NULL, NULL, NULL),
(24, 'ΤΣΙΠΑΡΗΣ', 'ΔΙΟΝΥΣΙΟΣ', b'0', 'ΒΑΓΓΕΛΗΣ', 'ΑΓΓΕΛΙΚΗ', 'td26@gmail.com', 'td26', '26', NULL, 4, 66, 0, NULL, NULL, NULL),
(25, 'ΑΛΕΞΟΠΟΥΛΟY', 'ΒΑΣΙΛΙΚΗ', b'1', 'ΠΕΤΡΟΣ', 'ΦΩΤΕΙΝΗ', 'ab27@gmail.com', 'ab27', '27', NULL, 4, 67, 0, NULL, NULL, NULL),
(26, 'ΜΙΑΟΥΛΗ', 'ΜΑΡΓΑΡΙΤΑ', b'1', 'ΣΤΕΛΙΟΣ', 'ΑΛΕΞΑΝΔΡΑ', 'll27@gmail.com', 'll27', '27', NULL, 4, 68, 0, NULL, NULL, NULL),
(27, 'ΝΙΚΟΛΑΚΟΣ', 'ΓΡΗΓΟΡΙΟΣ', b'0', 'ΘΟΔΩΡΟΣ', 'ΓΕΩΡΓΙΑ', 'mc27@gmail.com', 'mc27', '27', NULL, 4, 69, 0, NULL, NULL, NULL),
(28, 'ΧΑΡΙΤΑΚΗ', 'ΑΣΗΜΙΝΑ', b'1', 'ΣΤΑΥΡΟΣ', 'ΧΡΥΣΑ', 'wa28@gmail.com', 'wa28', '28', NULL, 4, 70, 0, NULL, NULL, NULL),
(29, 'ΘΕΟΔΟΣΙΟΥ', 'ΕΥΑΓΓΕΛΟΣ', b'0', 'ΑΝΑΣΤΑΣΙΟΣ', 'ΕΥΑΓΓΕΛΙΑ', 'he29@gmail.com', 'he29', '29', NULL, 4, 71, 0, NULL, NULL, NULL),
(30, 'ΔΙΔΑΣΚΑΛΟΥ', 'ΑΠΟΣΤΟΛIA', b'1', 'ΑΠΟΣΤΟΛΟΣ', 'ΑΘΗΝΑ', 'da32@gmail.com', 'da32', '32', NULL, 4, 72, 0, NULL, NULL, NULL),
(31, 'ΛΕΙΒΑΔΑ', 'ΧΑΡΙΚΛΕΙΑ', b'1', 'ΛΕΥΤΕΡΗΣ', 'ΘΕΟΔΩΡΑ', 'kw32@gmail.com', 'kw32', '32', NULL, 4, 73, 0, NULL, NULL, NULL),
(32, 'ΜΑΡΙΝΑΚΗΣ', 'ΣΤΥΛΙΑΝΟΣ', b'0', 'ΜΑΝΟΛΗΣ', 'ΕΙΡΗΝΗ', 'ls32@gmail.com', 'ls32', '32', NULL, 4, 74, 0, NULL, NULL, NULL),
(33, 'ΝΙΚΟΛΑΟΥ', 'ΕΥΣΤΑΘΙΑ', b'1', 'ΣΩΤΗΡΗΣ', 'ΕΥΓΕΝΙΑ', 'me32@gmail.com', 'me32', '32', NULL, 4, 75, 0, NULL, NULL, NULL),
(34, 'ΧΑΤΖΗΙΩΑΝΝΟΥ', 'ΙΟΥΛΙΑ', b'1', 'ΣΤΕΦΑΝΟΣ', 'ΕΛΕΥΘΕΡΙΑ', 'wi34@gmail.com', 'wi34', '34', NULL, 4, 76, 0, NULL, NULL, NULL),
(35, 'ΧΑΤΖΗΠΑΥΛΟΥ', 'ΕΥΔΟΞΙΑ', b'1', 'ΓΡΗΓΟΡΗΣ', 'ΚΥΡΙΑΚΗ', 'we34@gmail.com', 'we34', '34', NULL, 4, 77, 0, NULL, NULL, NULL),
(36, 'ΕΛΕΥΘΕΡΙΑΔΗΣ', 'ΕΥΑΓΓΕΛΟΣ', b'0', 'ΧΑΡΑΛΑΜΠΟΣ', 'ΔΗΜΗΤΡΑ', 'ee35@gmail.com', 'ee35', '35', NULL, 4, 78, 0, NULL, NULL, NULL),
(37, 'ΑΛΕΒΙΖΟΠΟΥΛΟΣ', 'ΔΗΜΗΤΡΙΟΣ', b'0', 'ΗΛΙΑΣ', 'ΑΝΝΑ', 'ad36@gmail.com', 'ad36', '36', NULL, 4, 79, 0, NULL, NULL, '1VOlZwbx7BAAAAAAAABPZA'),
(38, 'ΖΕΡΒΟΥ', 'ΧΡΥΣΟΥΛΑ', b'1', 'ΛΕΩΝΙΔΑΣ', 'ΖΩΗ', 'fw36@gmail.com', 'fw36', '36', NULL, 4, 80, 0, NULL, NULL, NULL),
(39, 'ΤΣΑΚΩΝΑ', 'ΣΤΕΛΛΑ', b'1', 'ΑΓΓΕΛΟΣ', 'ΣΤΥΛΙΑΝΗ-ΣΤΕΛΛΑ', 'ts36@gmail.com', 'ts36', '36', NULL, 4, 81, 0, NULL, NULL, NULL),
(40, 'ΑΛΕΞΙΑΔΗΣ', 'ΠΑΝΑΓΙΩΤΗΣ', b'0', 'ΑΛΕΞΑΝΔΡΟΣ', 'ΙΩΑΝΝΑ', 'ap37@gmail.com', 'ap37', '37', NULL, 4, 82, 0, NULL, NULL, NULL),
(41, 'ΜΑΝΟΥΣΗ', 'ΝΙΚΗ', b'1', 'ΓΙΩΡΓΟΣ', 'ΣΤΑΜΑΤΙΑ', 'lm38@gmail.com', 'lm38', '38', NULL, 4, 83, 0, NULL, NULL, NULL),
(42, 'ΜΟΣΧΟΒΑΚΗ', 'ΝΙΚΟΛΕΤΑ', b'1', 'ΓΙΑΝΝΗΣ', 'ΑΘΑΝΑΣΙΑ', 'lm40@gmail.com', 'lm40', '40', NULL, 4, 84, 0, NULL, NULL, NULL),
(43, 'ΣΙΔΕΡΗΣ', 'ΑΓΓΕΛΟΣ', b'0', 'ΠΕΤΡΟΣ', 'ΚΩΝΣΤΑΝΤΙΝΑ', 'sa40@gmail.com', 'sa40', '40', NULL, 4, 85, 0, NULL, NULL, NULL),
(44, 'ΚΑΖΑΝΤΖΗ', 'ΟΛΓΑ', b'1', 'ΚΩΣΤΑΣ', 'ΟΛΓΑ', 'jo41@gmail.com', 'jo41', '41', NULL, 4, 86, 0, NULL, NULL, NULL),
(45, 'ΔΙΑΜΑΝΤΗΣ', 'ΓΕΩΡΓΙΟΣ', b'0', 'ΣΤΕΛΙΟΣ', 'ΑΝΑΣΤΑΣΙΑ', 'dc43@gmail.com', 'dc43', '43', NULL, 4, 87, 0, NULL, NULL, NULL),
(46, 'ΖΕΡΒΑ', 'ΣΤΑΥΡΟΥΛΑ', b'1', 'ΝΙΚΟΣ', 'ΧΑΡΙΚΛΕΙΑ', 'fs43@gmail.com', 'fs43', '43', NULL, 4, 88, 0, NULL, NULL, NULL),
(47, 'ΔΟΞΑΡΑΣ', 'ΧΑΡΑΛΑΜΠΟΣ', b'0', 'ΣΤΑΥΡΟΣ', 'ΠΑΡΑΣΚΕΥΗ', 'dw71@gmail.com', 'dw71', '71', NULL, 4, 89, 0, NULL, NULL, NULL),
(48, 'ΑΛΕΞΑΝΔΡΟΥ', 'ΧΡΗΣΤΟΣ', b'0', 'ΑΠΟΣΤΟΛΟΣ', 'ΧΡΙΣΤΙΝΑ', 'aw72@gmail.com', 'aw72', '72', NULL, 4, 90, 0, NULL, NULL, NULL),
(49, 'ΠΑΠΑΝΤΩΝΙΟΥ', 'ΣΤΕΦΑΝΟΣ', b'0', 'ΛΕΥΤΕΡΗΣ', 'ΣΤΑΥΡΟΥΛΑ', 'ps73@gmail.com', 'ps73', '73', NULL, 4, 91, 0, NULL, NULL, NULL),
(50, 'ΕΥΘΥΜΙΑΔΗΣ', 'ΑΝΑΣΤΑΣΙΟΣ', b'0', 'ΣΩΤΗΡΗΣ', 'ΔΕΣΠΟΙΝΑ', 'ea74@gmail.com', 'ea74', '74', NULL, 4, 92, 0, NULL, NULL, NULL),
(51, 'ΟΙΚΟΝΟΜΑΚΟΣ', 'ΑΝΑΣΤΑΣΙΟΣ', b'0', 'ΣΤΕΦΑΝΟΣ', 'ΚΑΛΛΙΟΠΗ', 'oa76@gmail.com', 'oa76', '76', NULL, 4, 93, 0, NULL, NULL, NULL),
(52, 'ΔΙΑΜΑΝΤΟΠΟΥΛΟY', 'ΠΑΝΑΓΙΩΤΑ', b'1', 'ΔΗΜΗΤΡΗΣ', 'ΜΑΡΓΑΡΙΤΑ', 'dp77@gmail.com', 'dp77', '77', NULL, 4, 94, 0, NULL, NULL, NULL),
(53, 'ΔΗΜΗΤΡΙΟΥ', 'ΙΩΑΝΝΗΣ', b'0', 'ΓΡΗΓΟΡΗΣ', 'ΦΩΤΕΙΝΗ', 'di78@gmail.com', 'di78', '78', NULL, 4, 95, 0, NULL, NULL, NULL),
(54, 'ΡΑΠΤΗ', 'ΒΑΡΒΑΡΑ', b'1', 'ΠΑΝΑΓΙΩΤΗΣ', 'ΑΡΓΥΡΩ', 'qb78@gmail.com', 'qb78', '78', NULL, 4, 96, 0, NULL, NULL, NULL),
(55, 'ΠΑΠΑΔΟΠΟΥΛΟΥ ', 'ΧΡΥΣΑΝΘΗ', b'1', 'ΒΑΣΙΛΗΣ', 'ΑΝΤΩΝΙΑ', 'pw80@gmail.com', 'pw80', '80', NULL, 4, 97, 0, NULL, NULL, NULL),
(56, 'ΔΗΜΑΡΑ', 'ΙΩΑΝΝΑ', b'1', 'ΧΡΗΣΤΟΣ', 'ΜΑΡΙΑ', 'di81@gmail.com', 'di81', '81', NULL, 4, 98, 0, NULL, NULL, NULL),
(57, 'ΝΑΣΤΟΥ', 'ΦΑΝΗ', b'1', 'ΘΑΝΑΣΗΣ', 'ΕΛΕΝΗ', 'mv89@gmail.com', 'mv89', '89', NULL, 4, 99, 0, NULL, NULL, NULL),
(58, 'ΓΙΑΝΝΑΚΟΠΟΥΛΟΣ', 'ΑΘΑΝΑΣΙΟΣ', b'0', 'ΛΕΩΝΙΔΑΣ', 'ΑΛΕΞΑΝΔΡΑ', 'ca90@gmail.com', 'ca90', '90', NULL, 4, 100, 0, NULL, NULL, NULL),
(59, 'ΔΗΜΗΤΡΑΚΟΠΟΥΛΟΣ', 'ΑΝΑΣΤΑΣΙΟΣ', b'0', 'ΑΓΓΕΛΟΣ', 'ΧΡΥΣΑ', 'da92@gmail.com', 'da92', '92', NULL, 4, 101, 0, NULL, NULL, NULL),
(60, 'ΡΑΜΦΟΥ', 'ΠΟΛΥΞΕΝΗ', b'1', 'ΜΙΧΑΛΗΣ', 'ΚΑΤΕΡΙΝΑ', 'qp92@gmail.com', 'qp92', '92', NULL, 4, 102, 0, NULL, NULL, NULL),
(61, 'ΤΣΑΜΗ', 'ΧΡΥΣΗ', b'1', 'ΑΝΔΡΕΑΣ', 'ΒΑΣΙΛΙΚΗ', 'tw98@gmail.com', 'tw98', '98', NULL, 4, 103, 0, NULL, NULL, NULL),
(62, 'ΜΑΝΩΛΑ', 'ΑΝΤΩΝΙΑ', b'1', 'ΓΙΩΡΓΟΣ', 'ΑΘΗΝΑ', 'la20@gmail.com', 'la20', '20', NULL, 5, NULL, 0, NULL, NULL, NULL),
(63, 'ΛΑΜΠΡΟΠΟΥΛΟΥ', 'ΕΥΘΥΜΙΑ', b'1', 'ΓΙΑΝΝΗΣ', 'ΘΕΟΔΩΡΑ', 'ke42@gmail.com', 'ke42', '42', NULL, 5, NULL, 0, NULL, NULL, NULL),
(64, 'ΓΙΑΝΝΑΡΗ', 'ΑΝΑΣΤΑΣΙΑ', b'1', 'ΚΩΣΤΑΣ', 'ΕΥΓΕΝΙΑ', 'ca43@gmail.com', 'ca43', '43', NULL, 5, NULL, 0, NULL, NULL, NULL),
(65, 'ΑΛΕΞΙΟΥ', 'ΓΕΩΡΓΙΑ', b'1', 'ΝΙΚΟΣ', 'ΕΛΕΥΘΕΡΙΑ', 'ac45@gmail.com', 'ac45', '45', NULL, 5, NULL, 0, NULL, NULL, NULL),
(66, 'ΤΣΑΓΑΝΕΑ', 'ΑΣΠΑΣΙΑ', b'1', 'ΔΗΜΗΤΡΗΣ', 'ΚΥΡΙΑΚΗ', 'ta45@gmail.com', 'ta45', '45', NULL, 5, NULL, 0, NULL, NULL, NULL),
(67, 'ΚΑΖΑΚΗΣ', 'ΘΕΟΔΩΡΟΣ', b'0', 'ΣΤΕΦΑΝΟΣ', 'ΙΩΑΝΝΑ', 'jh47@gmail.com', 'jh47', '47', NULL, 5, NULL, 0, NULL, NULL, NULL),
(68, 'ΡΗΓΟΠΟΥΛΟΥ', 'ΣΤΑΜΑΤΙΑ', b'1', 'ΠΑΝΑΓΙΩΤΗΣ', 'ΖΩΗ', 'qs47@gmail.com', 'qs47', '47', NULL, 5, NULL, 0, NULL, NULL, NULL),
(69, 'ΓΙΑΝΝΟΠΟΥΛΟY', 'ΑΝΝΑ', b'1', 'ΒΑΣΙΛΗΣ', 'ΣΤΥΛΙΑΝΗ-ΣΤΕΛΛΑ', 'ca51@gmail.com', 'ca51', '51', NULL, 5, NULL, 0, NULL, NULL, NULL),
(70, 'ΜΑΥΡΙΔΗ', 'ΑΦΡΟΔΙΤΗ', b'1', 'ΧΡΗΣΤΟΣ', 'ΣΤΑΜΑΤΙΑ', 'la51@gmail.com', 'la51', '51', NULL, 5, NULL, 0, NULL, NULL, NULL),
(71, 'ΜΑΡΗ', 'ΟΥΡΑΝΙΑ', b'1', 'ΘΑΝΑΣΗΣ', 'ΑΘΑΝΑΣΙΑ', 'lo52@gmail.com', 'lo52', '52', NULL, 5, NULL, 0, NULL, NULL, NULL),
(72, 'ΤΣΑΚΑΛΩΤΟΥ', 'ΜΑΡΘΑ', b'1', 'ΜΙΧΑΛΗΣ', 'ΟΛΓΑ', 'tl52@gmail.com', 'tl52', '52', NULL, 5, NULL, 0, NULL, NULL, NULL),
(73, 'ΒΟΥΛΓΑΡΗΣ', 'ΒΑΣΙΛΕΙΟΣ', b'0', 'ΓΡΗΓΟΡΗΣ', 'ΚΩΝΣΤΑΝΤΙΝΑ', 'bb53@gmail.com', 'bb53', '53', NULL, 5, NULL, 0, NULL, NULL, NULL),
(74, 'ΑΛΕΞΑΚΗΣ', 'ΝΙΚΟΛΑΟΣ', b'0', 'ΛΕΩΝΙΔΑΣ', 'ΑΝΑΣΤΑΣΙΑ', 'am54@gmail.com', 'am54', '54', NULL, 5, NULL, 0, NULL, NULL, NULL),
(75, 'ΜΟΛΦΕΤΑ', 'ΛΑΜΠΡΙΝΗ', b'1', 'ΑΝΔΡΕΑΣ', 'ΧΑΡΙΚΛΕΙΑ', 'lk54@gmail.com', 'lk54', '54', NULL, 5, NULL, 0, NULL, NULL, NULL),
(76, 'ΦΙΛΙΠΠΙΔΗ', 'ΚΩΝΣΤΑΝΤΙΝΙΑ', b'1', 'ΑΝΤΩΝΗΣ', 'ΜΑΡΓΑΡΙΤΑ', 'vj54@gmail.com', 'vj54', '54', NULL, 5, NULL, 0, NULL, NULL, NULL),
(77, 'ΛΕΙΒΑΔΙΤΗΣ', 'ΣΤΑΥΡΟΣ', b'0', 'ΑΓΓΕΛΟΣ', 'ΠΑΡΑΣΚΕΥΗ', 'ks55@gmail.com', 'ks55', '55', NULL, 5, NULL, 0, NULL, NULL, NULL),
(78, 'ΓΙΑΝΝΑΚΗ', 'ΣΟΦΙΑ', b'1', 'ΣΠΥΡΟΣ', 'ΑΡΓΥΡΩ', 'cs56@gmail.com', 'cs56', '56', NULL, 5, NULL, 0, NULL, NULL, NULL),
(79, 'ΕΛΕΥΘΕΡΙΟΥ', 'ΚΩΝΣΤΑΝΤΙΝΑ', b'1', 'ΒΑΓΓΕΛΗΣ', 'ΑΝΤΩΝΙΑ', 'ej57@gmail.com', 'ej57', '57', NULL, 5, NULL, 0, NULL, NULL, NULL),
(80, 'ΖΑΧΑΡΙΟΥ', 'ΘΕΟΔΩΡΑ', b'1', 'ΘΟΔΩΡΟΣ', 'ΜΑΡΙΑ', 'fh57@gmail.com', 'fh57', '57', NULL, 5, NULL, 0, NULL, NULL, NULL),
(81, 'ΘΑΝΟΥ', 'ΑΓΓΕΛΙΚΗ', b'1', 'ΑΝΑΣΤΑΣΙΟΣ', 'ΕΛΕΝΗ', 'ha57@gmail.com', 'ha57', '57', NULL, 5, NULL, 0, NULL, NULL, NULL),
(82, 'ΟΙΚΟΝΟΜΙΔΗΣ', 'ΣΠΥΡΙΔΩΝ', b'0', 'ΓΙΩΡΓΟΣ', 'ΧΡΙΣΤΙΝΑ', 'os57@gmail.com', 'os57', '57', NULL, 5, NULL, 0, NULL, NULL, NULL),
(83, 'ΠΑΠΑΝΙΚΟΛΑΟΥ', 'ΠΗΝΕΛΟΠΗ', b'1', 'ΜΑΝΟΛΗΣ', 'ΚΑΤΕΡΙΝΑ', 'pp57@gmail.com', 'pp57', '57', NULL, 5, NULL, 0, NULL, NULL, NULL),
(84, 'ΘΕΟΤΟΚΗΣ', 'ΜΙΧΑΗΛ', b'0', 'ΓΙΑΝΝΗΣ', 'ΣΤΑΥΡΟΥΛΑ', 'hl58@gmail.com', 'hl58', '58', NULL, 5, NULL, 0, NULL, NULL, NULL),
(85, 'ΚΑΚΡΙΔΗΣ', 'ΕΜΜΑΝΟΥΗΛ', b'0', 'ΚΩΣΤΑΣ', 'ΔΕΣΠΟΙΝΑ', 'je59@gmail.com', 'je59', '59', NULL, 5, NULL, 0, NULL, NULL, NULL),
(86, 'ΖΑΡΚΟΣ', 'ΕΥΑΓΓΕΛΟΣ', b'0', 'ΝΙΚΟΣ', 'ΚΑΛΛΙΟΠΗ', 'fe60@gmail.com', 'fe60', '60', NULL, 5, NULL, 0, NULL, NULL, NULL),
(87, 'ΠΑΠΑΜΙΧΑΗΛ', 'ΑΝΑΣΤΑΣΙΟΣ', b'0', 'ΔΗΜΗΤΡΗΣ', 'ΦΩΤΕΙΝΗ', 'pa60@gmail.com', 'pa60', '60', NULL, 5, NULL, 0, NULL, NULL, NULL),
(88, 'ΜΗΤΡΑΣ', 'ΣΠΥΡΙΔΩΝ', b'0', 'ΠΑΝΑΓΙΩΤΗΣ', 'ΑΛΕΞΑΝΔΡΑ', 'ls63@gmail.com', 'ls63', '63', NULL, 5, NULL, 0, NULL, NULL, NULL),
(89, 'ΧΡΙΣΤΟΠΟΥΛΟΥ', 'ΔΙΟΝΥΣΙΑ', b'1', 'ΧΑΡΑΛΑΜΠΟΣ', 'ΒΑΣΙΛΙΚΗ', 'wd63@gmail.com', 'wd63', '63', NULL, 5, NULL, 0, NULL, NULL, NULL),
(90, 'ΤΣΑΤΣΟΥ', 'ΕΥΔΟΚΙΑ', b'1', 'ΗΛΙΑΣ', 'ΠΑΝΑΓΙΩΤΑ', 'te65@gmail.com', 'te65', '65', NULL, 5, NULL, 0, NULL, NULL, NULL),
(91, 'ΠΑΠΑΔΗΜΟΥ', 'ΜΑΓΔΑΛΗΝΗ', b'1', 'ΑΛΕΞΑΝΔΡΟΣ', 'ΣΟΦΙΑ', 'pl66@gmail.com', 'pl66', '66', NULL, 5, NULL, 0, NULL, NULL, NULL),
(92, 'ΚΑΖΑΚΟΥ', 'ΕΛΕΥΘΕΡΙΑ', b'1', 'ΠΕΤΡΟΣ', 'ΑΓΓΕΛΙΚΗ', 'je67@gmail.com', 'je67', '67', NULL, 5, NULL, 0, NULL, NULL, NULL),
(93, 'ΦΛΕΣΣΑΣ', 'ΠΑΝΤΕΛΗΣ', b'0', 'ΒΑΣΙΛΗΣ', 'ΧΡΥΣΑ', 'vp69@gmail.com', 'vp69', '69', NULL, 5, NULL, 0, NULL, NULL, NULL),
(94, 'ΒΛΑΒΙΑΝΟΣ', 'ΓΕΩΡΓΙΟΣ', b'0', 'ΧΡΗΣΤΟΣ', 'ΑΘΗΝΑ', 'bc82@gmail.com', 'bc82', '82', NULL, 5, NULL, 0, NULL, NULL, NULL),
(95, 'ΜΑΡΑΓΚΟΣ', 'ΠΕΤΡΟΣ', b'0', 'ΘΑΝΑΣΗΣ', 'ΘΕΟΔΩΡΑ', 'lp82@gmail.com', 'lp82', '82', NULL, 5, NULL, 0, NULL, NULL, NULL),
(96, 'ΜΙΧΑΗΛΙΔΗ', 'ΒΑΪΑ', b'1', 'ΣΤΕΛΙΟΣ', 'ΓΕΩΡΓΙΑ', 'lb82@gmail.com', 'lb82', '82', NULL, 5, NULL, 0, NULL, NULL, NULL),
(97, 'ΦΑΤΜΕΛΗ', 'ΠΑΥΛΟΣ', b'0', 'ΜΙΧΑΛΗΣ', 'ΕΥΓΕΝΙΑ', 'vp82@gmail.com', 'vp82', '82', NULL, 5, NULL, 0, NULL, NULL, NULL),
(98, 'ΜΗΤΡΟΠΟΥΛΟΥ', 'ΑΡΓΥΡΩ', b'1', 'ΣΤΑΥΡΟΣ', 'ΕΥΑΓΓΕΛΙΑ', 'la83@gmail.com', 'la83', '83', NULL, 5, NULL, 0, NULL, NULL, NULL),
(99, 'ΛΑΜΕΡΑΣ', 'ΑΝΔΡΕΑΣ', b'0', 'ΑΝΔΡΕΑΣ', 'ΕΛΕΥΘΕΡΙΑ', 'ka86@gmail.com', 'ka86', '86', NULL, 5, NULL, 0, NULL, NULL, NULL),
(100, 'ΠΑΠΑΔΑΚΗΣ', 'ΑΝΤΩΝΙΟΣ', b'0', 'ΑΝΤΩΝΗΣ', 'ΚΥΡΙΑΚΗ', 'pa87@gmail.com', 'pa87', '87', NULL, 5, NULL, 0, NULL, NULL, NULL),
(101, 'ΜΙΧΑΛΑΚΟΥ', 'ΕΥΑΝΘΙΑ', b'1', 'ΑΠΟΣΤΟΛΟΣ', 'ΕΙΡΗΝΗ', 'le95@gmail.com', 'le95', '95', NULL, 5, NULL, 0, NULL, NULL, NULL),
(102, 'ΠΑΠΑΝΔΡΕΟΥ', 'ΕΥΤΥΧΙΑ', b'1', 'ΛΕΥΤΕΡΗΣ', 'ΔΗΜΗΤΡΑ', 'pe97@gmail.com', 'pe97', '97', NULL, 5, NULL, 0, NULL, NULL, NULL),
(103, 'ΠΑΠΑΔΗΜΑ', 'ΕΥΦΡΟΣΥΝΗ', b'1', 'ΣΩΤΗΡΗΣ', 'ΑΝΝΑ', 'pe98@gmail.com', 'pe98', '98', NULL, 5, NULL, 0, NULL, NULL, NULL),
(104, 'SMITH', 'JOHN', b'0', NULL, NULL, 'doctorwho@tardis.co.uk', 'test', '1234', NULL, 2, NULL, 0, NULL, 1000, NULL);

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `classes`
--
ALTER TABLE `classes`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `class_id` (`class_id`);

--
-- Ευρετήρια για πίνακα `expenses`
--
ALTER TABLE `expenses`
  ADD PRIMARY KEY (`id`);
  /*ADD KEY `supplier_id` (`supplier_id`);*/

--
-- Ευρετήρια για πίνακα `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Ευρετήρια για πίνακα `grades`
--
ALTER TABLE `grades`
  ADD PRIMARY KEY (`id`),
  ADD KEY `course_id` (`course_id`),
  ADD KEY `pupil_id` (`pupil_id`),
  ADD KEY `teacher_id` (`teacher_id`);

--
-- Ευρετήρια για πίνακα `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `gname` (`gname`),
  ADD KEY `teacher_id` (`teacher_id`),
  ADD KEY `class_id` (`class_id`);

--
-- Ευρετήρια για πίνακα `msgs`
--
ALTER TABLE `msgs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `parent_msg_id` (`parent_msg_id`);

--
-- Ευρετήρια για πίνακα `msgs_details`
--
ALTER TABLE `msgs_details`
  ADD PRIMARY KEY (`msg_id`,`from_user_id`,`to_user_id`,`to_or_cc`),
  ADD KEY `to_user_id` (`to_user_id`),
  ADD KEY `from_user_id` (`from_user_id`);

--
-- Ευρετήρια για πίνακα `participates`
--
ALTER TABLE `participates`
  ADD KEY `group_id` (`group_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Ευρετήρια για πίνακα `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Ευρετήρια για πίνακα `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `teach`
--
ALTER TABLE `teach`
  ADD PRIMARY KEY (`teacher_id`,`group_id`,`course_id`),
  ADD KEY `course_id` (`course_id`),
  ADD KEY `group_id` (`group_id`);

--
-- Ευρετήρια για πίνακα `tests`
--
ALTER TABLE `tests`
  ADD PRIMARY KEY (`id`),
  ADD KEY `group_id` (`group_id`),
  ADD KEY `teacher_id` (`teacher_id`);

--
-- Ευρετήρια για πίνακα `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `parent_id` (`parent_id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `classes`
--
ALTER TABLE `classes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT για πίνακα `courses`
--
ALTER TABLE `courses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `expenses`
--
ALTER TABLE `expenses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT για πίνακα `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT για πίνακα `groups`
--
ALTER TABLE `groups`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT για πίνακα `msgs`
--
ALTER TABLE `msgs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT για πίνακα `payments`
--
ALTER TABLE `payments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT για πίνακα `roles`
--
ALTER TABLE `roles`
  MODIFY `id` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT για πίνακα `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=104;

--
-- Περιορισμοί για άχρηστους πίνακες
--

--
-- Περιορισμοί για πίνακα `courses`
--
ALTER TABLE `courses`
  ADD CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`);

--
-- Περιορισμοί για πίνακα `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Περιορισμοί για πίνακα `grades`
--
ALTER TABLE `grades`
  ADD CONSTRAINT `grades_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  ADD CONSTRAINT `grades_ibfk_2` FOREIGN KEY (`pupil_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `grades_ibfk_3` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`id`);

--
-- Περιορισμοί για πίνακα `groups`
--
ALTER TABLE `groups`
  ADD CONSTRAINT `groups_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`),
  ADD CONSTRAINT `groups_ibfk_3` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`id`);

--
-- Περιορισμοί για πίνακα `msgs`
--
ALTER TABLE `msgs`
  ADD CONSTRAINT `msgs_ibfk_1` FOREIGN KEY (`parent_msg_id`) REFERENCES `msgs` (`id`);

--
-- Περιορισμοί για πίνακα `msgs_details`
--
ALTER TABLE `msgs_details`
  ADD CONSTRAINT `msgs_details_ibfk_1` FOREIGN KEY (`msg_id`) REFERENCES `msgs` (`id`),
  ADD CONSTRAINT `msgs_details_ibfk_2` FOREIGN KEY (`to_user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `msgs_details_ibfk_3` FOREIGN KEY (`from_user_id`) REFERENCES `users` (`id`);

--
-- Περιορισμοί για πίνακα `participates`
--
ALTER TABLE `participates`
  ADD CONSTRAINT `participates_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  ADD CONSTRAINT `participates_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Περιορισμοί για πίνακα `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Περιορισμοί για πίνακα `teach`
--
ALTER TABLE `teach`
  ADD CONSTRAINT `teach_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  ADD CONSTRAINT `teach_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `teach_ibfk_3` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`);

--
-- Περιορισμοί για πίνακα `tests`
--
ALTER TABLE `tests`
  ADD CONSTRAINT `tests_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  ADD CONSTRAINT `tests_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`id`);

--
-- Περιορισμοί για πίνακα `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `users_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
