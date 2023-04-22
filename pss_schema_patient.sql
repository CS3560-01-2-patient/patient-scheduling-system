-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: pss_schema
-- ------------------------------------------------------
-- Server version	8.0.32


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `patient`
--
LOCK TABLES `patient` WRITE;


DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `patient_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `dateOfBirth` varchar(45) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--
-- Adding sample patients to the table
LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (10,'John Wick','wicked@gmail.com','babayaga','parabellum','911','2008-04-02','Male'),(11,'Brandon','bro@gmail.com','dude','lame','90394-33425','2023-04-06','Male'),(12,'spongebob squarepants','squarepants@gmail.com','Spongebob','krustykrab','818-4859-3914','2023-04-05','Other');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */; -- Test patients to make sure that the tables are being filled properly
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

LOCK TABLES `physician` WRITE;

DROP TABLE IF EXISTS `physician`;
CREATE TABLE `physician` (
  `physician_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `specialty` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`physician_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Adding sample physicians to the table
LOCK TABLES `physician` WRITE;
/*!40000 ALTER TABLE `physician` DISABLE KEYS */;
INSERT INTO `physician` VALUES (10,'John Smith', 'john.smith@example.com', '555-1234', 'Male', 'Cardiology'), (11, 'Emily Nguyen', 'emily.nguyen@example.com', '555-5678', 'Female', 'Neurology'),(12, 'David Kim', 'david.kim@example.com', '555-9012', 'Male', 'Dermatology');
/*!40000 ALTER TABLE `physician` ENABLE KEYS */; -- Test patients to make sure that the tables are being filled properly
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

LOCK TABLES `appointment` WRITE;

DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
  `appointment_id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int DEFAULT NULL,
  `physician_id` int DEFAULT NULL,
  `appointment_date` varchar(45) DEFAULT NULL, 
  `appontment_time` varchar(45) DEFAULT NULL, 
  `treatment` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`appointment_id`),
  FOREIGN KEY (`patient_id`) REFERENCES `patient`(`patient_id`), -- In addition to having a unique identifier the appointment needs to keep track of which patiend and physician it refers to.
  FOREIGN KEY (`physician_id`) REFERENCES `physician`(`physician_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Adding sample physicians to the table
LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1001, 11, 10, '2023-02-03', '09:15:00', 'General Checkup'), (1002, 10, 11, '2023-02-03', '09:15:00', 'General Checkup'), (1003, 12, 12, '2023-02-03', '09:15:00', 'General Checkup');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */; -- Test patients to make sure that the tables are being filled properly
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-17 19:50:57
