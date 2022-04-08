-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: requestbase
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requests` (
  `request_name` varchar(128) NOT NULL,
  `request_amount` smallint NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`request_name`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requests`
--

LOCK TABLES `requests` WRITE;
/*!40000 ALTER TABLE `requests` DISABLE KEYS */;
INSERT INTO `requests` VALUES ('1',15,70),('12',2,46),('123',2,45),('a',5,69),('ab',18,41),('abc',60,1),('abcd',2,40),('abcde',679,2),('abcdef',1,44),('abcdefg',565,3),('abcdefghij',342,4),('abcdefghijkl',876,5),('abcdefghijklmno',648,6),('а',17,56),('аб',2,47),('абв',146,7),('абв где',251,8),('абв где ёжз',251,9),('абв где ёжз икл',545,10),('абв где ёжз икл мно',352,11),('абв где ёжз икл мно прс',445,12),('абв ека абв',27,71),('абвг',2,42),('абвгд',1,43),('американский футбол',4241,13),('американский футбол актеры',364,14),('американский футбол главный герой',544,15),('американский футбол режиссер',63,16),('американский футбол смотреть',4324,17),('американский футбол смотреть онлайн ',5352,18),('апв',1,80),('б',1,57),('бвг',1,53),('в',1,63),('вгд',1,54),('где',1,55),('икец313пц',1,72),('как',8,77),('как найти себя',1,76),('л',1,68),('люди в черном',3253,19),('мама',2,74),('мама мыла раму',1,73),('мама папа и я дружная семья',1,75),('мапа',1,79),('р',2,64),('рак',3,78),('рубашка',362,20),('рубашка белая',5352,21),('рубашка зеленая',452,22),('рубашка красная',433,23),('рубашка купить',3552,24),('рубашка поло',361,25),('рубашка серая',365,26),('рубашка фиолетовая',63,27),('рубашка черная',362,28),('т',1,67),('терминатор',3253,29),('терминатор смотреть',3552,30),('терминатор смотреть онлайн',2521,31),('убойный футбол',3434,32),('убойный футбол смотреть онлайн',5152,33),('человек паук',8776,34),('человек паук актеры',5355,35),('человек паук главный герой',5352,36),('человек паук история создания',361,37),('человек паук режиссер',541,38),('человек паук смотреть онлайн',7664,39);
/*!40000 ALTER TABLE `requests` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-08 16:25:16
