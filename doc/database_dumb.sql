-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: edts
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `event_id` bigint NOT NULL,
  `ticket_batch_id` bigint DEFAULT NULL,
  `quantity` int NOT NULL,
  `status` enum('PENDING','CONFIRMED','EXPIRED') DEFAULT 'PENDING',
  `reserved_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `event_id` (`event_id`),
  KEY `ticket_batch_id` (`ticket_batch_id`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`) ON DELETE CASCADE,
  CONSTRAINT `bookings_ibfk_3` FOREIGN KEY (`ticket_batch_id`) REFERENCES `ticket_batches` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (1,1,1,1,2,'CONFIRMED','2025-05-15 02:15:00'),(2,2,1,2,1,'CONFIRMED','2025-05-15 04:00:00'),(3,3,2,3,1,'CONFIRMED','2025-06-20 02:30:00'),(4,4,2,4,2,'CONFIRMED','2025-06-20 05:15:00'),(5,5,5,5,4,'PENDING','2025-08-27 03:00:00'),(8,4,1,2,2,'CONFIRMED','2025-04-19 08:30:47'),(9,3,1,1,1,'CONFIRMED','2025-04-19 13:47:53');
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `events` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  `location` varchar(255) DEFAULT NULL,
  `event_datetime` timestamp NOT NULL,
  `total_tickets` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `artist_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (1,'Rock Night','An electrifying rock concert','Stadium A','2025-05-15 12:00:00',5000,'2025-04-18 15:33:04',NULL),(2,'Jazz Fest','Smooth jazz evening','Jazz Club B','2025-06-20 13:00:00',3000,'2025-04-18 15:33:04',NULL),(3,'Pop Fiesta','Pop music extravaganza','Arena C','2025-07-10 11:30:00',6000,'2025-04-18 15:33:04',NULL),(4,'EDM Party','Dance all night long','Open Field D','2025-08-05 15:00:00',7000,'2025-04-18 15:33:04',NULL),(5,'Indie Vibes','Indie bands showcase','Indie Hall E','2025-09-01 10:00:00',2500,'2025-04-18 15:33:04',NULL);
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_batches`
--

DROP TABLE IF EXISTS `ticket_batches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket_batches` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_id` bigint NOT NULL,
  `batch_name` varchar(100) DEFAULT NULL,
  `reserved_quota` int NOT NULL,
  `sold_count` int DEFAULT '0',
  `price` double DEFAULT NULL,
  `start_time` timestamp NOT NULL,
  `end_time` timestamp NOT NULL,
  `batch_type` enum('LIMITED','REGULAR') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `event_id` (`event_id`),
  CONSTRAINT `ticket_batches_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_batches`
--

LOCK TABLES `ticket_batches` WRITE;
/*!40000 ALTER TABLE `ticket_batches` DISABLE KEYS */;
INSERT INTO `ticket_batches` VALUES (1,1,'Limited Sale',3000,1,50,'2025-05-15 02:00:00','2025-05-15 03:00:00','LIMITED'),(2,1,'Regular Sale',2000,5,40,'2025-04-15 03:01:00','2025-05-25 11:00:00','REGULAR'),(3,2,'Early Jazz',1000,0,60,'2025-06-20 02:00:00','2025-06-20 04:00:00','LIMITED'),(4,2,'Jazz Rush',2000,0,70,'2025-06-20 04:01:00','2025-06-20 12:00:00','REGULAR'),(5,5,'Batch Indie',2500,0,45,'2025-08-25 01:00:00','2025-09-01 09:00:00','LIMITED');
/*!40000 ALTER TABLE `ticket_batches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_rules`
--

DROP TABLE IF EXISTS `ticket_rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket_rules` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_id` bigint NOT NULL,
  `is_batched` tinyint(1) DEFAULT '0',
  `is_time_limited` tinyint(1) DEFAULT '0',
  `max_tickets_per_user` int DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `event_id` (`event_id`),
  CONSTRAINT `ticket_rules_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_rules`
--

LOCK TABLES `ticket_rules` WRITE;
/*!40000 ALTER TABLE `ticket_rules` DISABLE KEYS */;
INSERT INTO `ticket_rules` VALUES (1,1,1,1,2),(2,2,1,1,1),(3,3,0,0,5),(4,4,0,1,3),(5,5,1,0,4);
/*!40000 ALTER TABLE `ticket_rules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'john_dendev','john@example.com','2025-04-18 15:31:22'),(2,'jane_piotre','jane@example.com','2025-04-18 15:31:22'),(3,'alice_walker','alice@example.com','2025-04-18 15:31:22'),(4,'bob_builder','bob@example.com','2025-04-18 15:31:22'),(5,'sara_connor','sara@example.com','2025-04-18 15:31:22');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'edts'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-19 21:08:02
