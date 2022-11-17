-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hospitaldb
-- ------------------------------------------------------
-- Server version	8.0.30

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
  `userID` varchar(100) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(225) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `birthDate` date DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `nrc` varchar(60) DEFAULT NULL,
  `address` varchar(225) DEFAULT NULL,
  `phone` varchar(60) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `imageName` varchar(225) DEFAULT NULL,
  `isActive` tinyint DEFAULT NULL,
  `accountHistory` longtext,
  `status` varchar(65) DEFAULT NULL,
  `employDate` date DEFAULT NULL,
  `resignDate` date DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `senior` tinyint DEFAULT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `userID_UNIQUE` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('Admin-0001','admin','1000:7e4e7f64476baf612fbfc0b3eeeb0675:9b0d0fc5932b269eaa66b97c03d0d37f71f3f623bffbddfb2327305c592a0df431d2d9ee36c93c4d516549c5390f6270d0a25d43a7177348e362d4a3f626c472','Aung','Nyein',45,'2004-11-05','Male','9/','Yangon','0925','Admin','4c66f69f-6dbc-4c85-a3cb-59262b13da48.jpg',1,'createdBy-null@2022-11-16, modifiedBy-Admin-0001@2022-11-16,deletedBy-Admin-0001@2022-11-16','Working','2022-11-16','2022-11-16',300000,0),('Admin-0002','admin2','1000:1a250eec54624646a4b74af4452087b7:df26d3502aa456b8ba1c9c686a07da8b3d32eceeaee54adc4e43c9f66675ee966b80ff9de8a811686ed741eb2bdfa235387a0cc765eb968141a7bf37b93baddf','khant','zaw',55,'2001-10-31','Male','2/','Heaven','09797','Admin','141aca5f-4592-4e42-a564-a63ceb04a8a4.jpg',1,'createdBy-Admin-0001@2022-11-16,deletedBy-Admin-0001@2022-11-16','Working','2022-11-16','2022-11-16',300000,0),('Admin-0003','admin3','1000:688a2e24023cdc9f6f03241de79ac8f3:a9d2de09432a3dff059d3a4a859829de9f9a718324f25485400b8b24ab9195c90f573fd3a00204315c64ee2998fbf6c53853a531cf7b1d48a8144138fdce1ac6','X','Y',56,'1994-11-11','Female','5/','Yangon','09757','Admin','896f942c-c224-40c5-a74e-63af50c80242.jpg',1,'createdBy-Admin-0001@2022-11-16','Working','2022-11-17','2022-11-17',300000,1);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-17 16:15:38
