-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: daliy_planner_db
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'Administrator','User','admin','yMSYp9bI3CPguVez9Z0xtA==');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `rating` int NOT NULL,
  PRIMARY KEY (`feedback_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,1,'Great app, very helpful!',5),(2,3,'I love using this daily.',5),(3,5,'Excellent features and easy to use.',5),(4,7,'GOOD !',5),(5,9,'Nice',5),(6,13,'I love it',4),(7,16,'good 100%',5),(8,19,'ee',5);
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `task_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `completed` tinyint(1) NOT NULL DEFAULT '0',
  `datetime` datetime DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `description` text,
  `location` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `task_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,1,'Finish homework',0,'2025-11-01 10:00:00','Study','Math exercises','Home'),(2,1,'Submit report',1,'2025-11-02 15:00:00','Work','Monthly report','Office'),(3,1,'Grocery shopping',0,'2025-11-05 12:00:00','Personal','Buy groceries','Supermarket'),(4,2,'Yoga class',1,'2025-11-02 07:00:00','Health','Morning session','Gym'),(5,2,'Call mom',0,'2025-12-31 00:30:00','Work','Weekly call','Home'),(7,3,'Read book',1,'2025-11-01 20:00:00','Leisure','Finish novel','Home'),(8,3,'Pay bills',0,'2025-11-04 17:00:00','Finance','Electricity and water','Home'),(9,3,'Plan trip',0,'2025-11-05 14:00:00','Personal','Weekend getaway','Online'),(10,4,'Doctor appointment',0,'2025-11-03 10:30:00','Health','Routine checkup','Clinic'),(11,4,'Laundry',1,'2025-11-02 12:00:00','Home','Wash clothes','Home'),(12,4,'Team meeting',0,'2025-11-06 11:00:00','Work','Project discussion','Office'),(13,5,'Cook dinner',1,'2025-11-02 18:00:00','Home','Family dinner','Home'),(14,5,'Online course',0,'2025-11-04 20:00:00','Study','Finish module','Home'),(15,5,'Clean room',0,'2025-11-05 09:00:00','Home','Organize stuff','Home'),(16,6,'Visit friend',0,'2025-11-03 16:00:00','Personal','Catch up','Friend\'s house'),(17,6,'Write blog',1,'2025-11-02 13:00:00','Leisure','New article','Home'),(18,6,'Exercise',0,'2025-11-06 07:00:00','Health','Morning workout','Gym'),(19,7,'Math',1,'2025-12-04 11:30:00','Study','Chapter 1',''),(21,9,'Study Math',1,'2025-12-22 10:00:00','Study','Monday',''),(23,13,'Math Quiz',1,'2025-12-24 12:30:00','Study','Sunday','C6'),(25,14,'Project IT and math',1,'2025-12-31 18:00:00','Work','Abc','zoom'),(27,16,'Hw',1,'2025-12-26 03:00:00','Study','abc','abc'),(29,16,'Hw OS',1,'2025-12-31 12:00:00','Work','','C1'),(31,19,'h.w',1,'2025-12-30 03:30:00','Study','jjj','oo');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Taghreed','Alharbi','taghreed','viyW9uSJ4cIWW96QiLIuGA=='),(2,'Sarah','Alsubaie','sarah','viyW9uSJ4cIWW96QiLIuGA=='),(3,'Razan','Alzahrani','razan','viyW9uSJ4cIWW96QiLIuGA=='),(4,'Maha','Aldhwaihi','maha','viyW9uSJ4cIWW96QiLIuGA=='),(5,'Khadijah','Baaqeel','khadijah','viyW9uSJ4cIWW96QiLIuGA=='),(6,'Elan','Alfowzan','elan','viyW9uSJ4cIWW96QiLIuGA=='),(7,'Maha','Ail','MahaAil','VrPmlZRg3h1FjavMJQDZ1g=='),(8,'Smai','Saad','SmaiSaad','RiWNj2mERCO5rVpzZj6isw=='),(9,'Asma','Fahad','AsmaFahad','EHe/+pYEJIkpm2ONHu0iOQ=='),(13,'Alanoud','Badr','Alanoud04','d4Qw0URvei0pYxgtL9XqbQ=='),(14,'Jana','Ahmad','Jana2025','4r8+hIbswduGZv8ifGmsjA=='),(16,'lama','ail','lama','OMxyyvtoqFTEYnmsIYOsEg=='),(19,'sarah','alsubaie','sarah101','XJ+KmDV9X8A24lmDERaWsg=='),(20,'elan2','n','elan2','1qmznDMOY4RYdf1FjTrqzQ==');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-19 13:13:44
